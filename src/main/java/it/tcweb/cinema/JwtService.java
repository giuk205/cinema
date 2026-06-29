package it.tcweb.cinema;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/*
Classe che deve fare 3 cose :
1) generare un token a partire da uno username
2) estrarre lo username da un token esistente
3) controllare che un token sia valido(firma giusta e non scaduto)
 */
@Service
public class JwtService {

    // @Value serve a affidare a una variabile il valore specificato nell'application.properties

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // 1. Genera un token a partire dallo username e dal clienteId (salvato come claim "id")
    public String generaToken(String username, Integer clienteId) {
        return Jwts.builder()
                .subject(username)
                .claim("id", clienteId)  // il frontend legge questo campo per sapere quale cliente è loggato
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getChiave())
                .compact();
    }

    // 2. Estrae lo username dal token
    public String estraiUsername(String token) {
        return Jwts.parser()
                .verifyWith(getChiave()).build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    // 3. Verifica che il token sia valido (firma ok, non scaduto)
    public boolean tokenValido(String token) {
        try {
            Jwts.parser().verifyWith(getChiave()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private SecretKey getChiave(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
