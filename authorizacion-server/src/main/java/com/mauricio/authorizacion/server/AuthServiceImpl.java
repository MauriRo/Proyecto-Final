package com.mauricio.authorizacion.server;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mauricio.authorizacion.models.Usuario;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioServiceImpl usuarioService;
    private final RSAKey rsaKey;

    public AuthServiceImpl(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo generar la clave RSA", e);
        }
    }

    @Override
    public String authenticate(String username, String password) throws Exception {
        Usuario user = usuarioService.getUsuario(username);
        if (user == null || !usuarioService.checkPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        Instant now = Instant.now();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("http://localhost:9000")
                .subject(user.getUsername())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(3600)))
                .jwtID(UUID.randomUUID().toString())
                .claim("roles", user.getRoles().stream().map(r -> r.getNombre()).toList())
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(rsaKey.getKeyID()).build(), claims);

        JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
