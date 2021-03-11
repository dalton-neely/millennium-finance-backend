package millenniumfinance.backend.utilities;

import java.math.BigInteger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

public class HMAC {
  public static final int SIGNATURE_NUMBER = 1;
  public static final String FORMAT_STRING = "%032x";
  
  static public String calculate(String secretKey, String message) {
    byte[] secret = secretKey.getBytes(UTF_8);
    byte[] payload = message.getBytes(UTF_8);
    byte[] response = calcHmacSha256(secret, payload);
    return format(FORMAT_STRING, new BigInteger(SIGNATURE_NUMBER, response));
  }
  
  static public byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
    byte[] hmacSha256 = null;
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
      mac.init(secretKeySpec);
      hmacSha256 = mac.doFinal(message);
    } catch (Exception e) {
      throw new RuntimeException("Failed to calculate hmac-sha256", e);
    }
    return hmacSha256;
  }
}
