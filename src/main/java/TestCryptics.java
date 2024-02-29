import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.flimwip.design.Encryption;

public class TestCryptics {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generating the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();
        KeyPair pair2 = keyPairGen.genKeyPair();

        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //Initializing a Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());

        //Adding data to the cipher
        byte[] input = "M6kUVm3T".getBytes();
        cipher.update(input);

        //encrypting the data
        byte[] cipherText = cipher.doFinal();
        System.out.println(new String(cipherText, StandardCharsets.UTF_16));


        //Initializing the same cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

        System.out.println("Private key");
        System.out.println(pair2.getPrivate().toString());

        //Decrypting the text
        byte[] decipheredText = cipher.doFinal(cipherText);
        System.out.println(new String(decipheredText));
    }
}