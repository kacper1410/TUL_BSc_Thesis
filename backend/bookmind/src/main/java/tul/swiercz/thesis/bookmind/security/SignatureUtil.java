package tul.swiercz.thesis.bookmind.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;

import static com.nimbusds.jose.JWSAlgorithm.HS256;

public class SignatureUtil {

    private final static String SECRET = "_yIfhIoIbxqmTt5zeN8R505CYY_UmWbx0sc9P7wz_hvpiq3LESck4DSVD3AdjE7KHKxor0HshXb3EVuRsYOFT122a8g6wUZR4MoTq1HiiUo0qX-YIKoAIl3KYcJhyTGcVWXpUdLHdOuW_OidXJ-Bx_Bxs4pF-PTC-w8krgPT_mU";

    public static String calcSignature(Long version) {
        try {
            JWSSigner signerJWS = new MACSigner(SECRET);
            Payload payload = new Payload(String.valueOf(version));
            JWSObject objectJWS = new JWSObject(new JWSHeader(HS256), payload);
            objectJWS.sign(signerJWS);
            return objectJWS.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
