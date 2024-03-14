import com.sun.javafx.util.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.Utilities;

import static com.sun.javafx.util.Utils.*;

public class TestCryptics {

    public static void main(String[] args) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableEntryException {

        KeyPairGenerator keyPairGen = null; //Creating KeyPair generator object
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGen.initialize(2048);                                        //Initializing the key pair generator
        try {
            saving_private_key("Werterzu900!");
            System.out.println("Saving successfull");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        try {
            SecretKey mysecretKey = retreiving_private_key("Werterzu900!");

            System.out.println(print(mysecretKey.getEncoded()));
            System.out.println("Private Key is: " + new String(mysecretKey.getEncoded(), "UTF-8"));
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableEntryException e) {
            throw new RuntimeException(e);
        }
        /*//Creating the KeyStore object
        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        //Loading the the KeyStore object
        char[] password = "Werterzu900!".toCharArray();
        //java.io.FileInputStream fis = new FileInputStream("H:\\PLAT\\Data\\certs.crt");

        keyStore.load(null, password);

        //Creating the KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        //Creating SecretKey object
        SecretKey mySecretKey = new SecretKeySpec("Werterzu900!".getBytes(), "DSA");
        //KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //SecretKey mySecondSecretKey = new SecretKeySpec(keyPairGen.genKeyPair().getPrivate().getEncoded(), "DSA");

        //Creating SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);

        //Storing the KeyStore object
        java.io.FileOutputStream fos = null;
        fos = new java.io.FileOutputStream("H:\\PLAT\\Data\\certs");
        keyStore.store(fos, password);

        //Creating the KeyStore.SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEnt = (KeyStore.SecretKeyEntry)keyStore.getEntry("secretKeyAlias", protectionParam);

        //Creating SecretKey object
        SecretKey mysecretKey = secretKeyEnt.getSecretKey();
        System.out.println(print(mysecretKey.getEncoded()));
        System.out.println(new String(mysecretKey.getEncoded(), "UTF-8"));

        System.out.println("Algorithm used to generate key : "+mysecretKey.getAlgorithm());
        System.out.println("Format used for the key: "+mysecretKey.getFormat());*/

        /*//Creating the KeyStore object
        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        //Loading the KeyStore object
        char[] password = "Werterzu900!".toCharArray();
        String path = "H:\\PLAT\\Data\\certs";
        java.io.FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);

        //Creating the KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        //Creating SecretKey object
        SecretKey mySecretKey = new SecretKeySpec("Werterzu900!".getBytes(), "DSA");

        //Creating SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);

        //Storing the KeyStore object
        java.io.FileOutputStream fos = null;
        fos = new java.io.FileOutputStream("newKeyStoreName");
        keyStore.store(fos, password);
        System.out.println("data stored");*/

        /*//Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generating the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //Initializing a Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());

        //Adding data to the cipher
        byte[] input = "M6kUVm3T".getBytes();
        cipher.update(input);

        //encrypting the data
        byte[] cipherText = cipher.doFinal();
        System.out.println(print(cipherText));


        //Initializing the same cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

        //Decrypting the text
        byte[] decipheredText = cipher.doFinal(cipherText);
        System.out.println(new String(decipheredText));*/









        /*//Creating the KeyStore object
        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        //Loading the the KeyStore object
        char[] password = "Werterzu900!".toCharArray();
        //java.io.FileInputStream fis = new FileInputStream("H:\\PLAT\\Data\\certs.crt");

        keyStore.load(null, password);

        //Creating the KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        //Creating SecretKey object
        SecretKey mySecretKey = new SecretKeySpec("Werterzu900!".getBytes(), "DSA");
        //KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //SecretKey mySecondSecretKey = new SecretKeySpec(keyPairGen.genKeyPair().getPrivate().getEncoded(), "DSA");

        //Creating SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);

        //Storing the KeyStore object
        java.io.FileOutputStream fos = null;
        fos = new java.io.FileOutputStream("H:\\PLAT\\Data\\certs");
        keyStore.store(fos, password);

        //Creating the KeyStore.SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEnt = (KeyStore.SecretKeyEntry)keyStore.getEntry("secretKeyAlias", protectionParam);

        //Creating SecretKey object
        SecretKey mysecretKey = secretKeyEnt.getSecretKey();
        System.out.println(print(mysecretKey.getEncoded()));
        System.out.println(new String(mysecretKey.getEncoded(), "UTF-8"));

        System.out.println("Algorithm used to generate key : "+mysecretKey.getAlgorithm());
        System.out.println("Format used for the key: "+mysecretKey.getFormat());*/
    }

    /*public static void main(String[] args) {
        //Symetic encryption
    }*/

    public static String print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }


    public static void saving_private_key(String pw) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        //standart path for file
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        String private_key_path = "H:\\PLAT\\Data\\certs_prv";
        char[] password = pw.toCharArray();
        keyStore.load(null, password);
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
        SecretKey mySecretKey = new SecretKeySpec(pw.getBytes(), "DSA");
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);
        FileOutputStream fos = new FileOutputStream(private_key_path);
        keyStore.store(fos, password);
    }

    public static SecretKey retreiving_private_key(String pw) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        char[] password = "Werterzu900!".toCharArray();
        FileInputStream fis = new FileInputStream("H:\\PLAT\\Data\\certs_prv");
        keyStore.load(fis, password);
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
        KeyStore.SecretKeyEntry secretKeyEnt = (KeyStore.SecretKeyEntry)keyStore.getEntry("secretKeyAlias", protectionParam);
        SecretKey mysecretKey = secretKeyEnt.getSecretKey();
        return mysecretKey;
    }
}