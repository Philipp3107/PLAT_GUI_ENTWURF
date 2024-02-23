import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;

import org.flimwip.design.Encryption;

public class TestCryptics {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        System.out.println("Testing Cryptics");
        String user_password = "Werterzu900!";
        String password = "M6kUVm3T";


        byte[] salt = new String("694201111").getBytes();

        int iterationCounte = 69420;
        int keyLength = 128;
        SecretKeySpec key = null;
        try{
            key = Encryption.createSecretKey(user_password.toCharArray(), salt, iterationCounte, keyLength);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Original is: " + password);
        String encrypted = Encryption.encrypt(password, key);
        System.out.println("Encrypted is: " + encrypted);
        String decrypted = Encryption.decrypt(encrypted, key);
        System.out.println("Decrypted is: " + decrypted);

        String decripling = Encryption.decrypt("vbLrtqwlnukBK1N1Lz1KxA==:GjtU80ZJlpp3l/D9RQoSgA==", key);
        System.out.println(decripling);
    }
}