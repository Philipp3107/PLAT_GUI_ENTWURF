package org.flimwip.design;

import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import org.flimwip.design.Documentationhandler.*;

@ServiceC(desc="This class is for encrypting and decrypting Userdata and for the authentication on login.")
public class Cryptographer {

    @ServiceATT(desc="SecretKey used to encrypt and decrypt Data that can only be loaded with the correct Password of the User.",
                type="SecretKey")
    public SecretKey key;
    
    @ServiceATT(desc="stores if this is the first login of the User",
                type="boolean")
    private boolean first_login = false;

    @ServiceATT(desc="If the User enters the wrong password and the Application cant fetch the SecretKey, this gets switched to false.",
                type="boolean")
    private boolean verification_good = true;
    @ServiceATT(desc="Logger to print Messages to the Console",
                type="PKLogger")
    private PKLogger logger = new PKLogger(Cryptographer.class);
    
    @ServiceATT(desc="Holds the Location of the Secretkey on Windows",
                type="String")
    private static String CREED_SEC = "H:\\PLAT\\Data\\common\\certs_sec";

    /*@ServiceATT(desc="Holds the Location of the Secretkey on MacOS",
                type="String")
    private static String CREED_SEC = "/Users/philippkotte/Desktop/certs_sec";*/

    @ServiceCR(desc="Constructor of the Cryptographer Class",
               params={"None"})
    public Cryptographer(){
        logger.set_Level(LoggingLevels.FINE);
        startup();
    }


    @ServiceM(desc="Starts this class. If this is the first login a key will be generated.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void startup(){
        first_login = !new File(CREED_SEC).exists();
        if(first_login){
            generate_key();
        }
    }
    @ServiceM(desc="Returns if this login is the first login of the User.",
              category="Method",
              params={"None"},
              returns="boolean -> true if this is the first login of the User.",
              thrown={"None"})
    public boolean get_first_login(){
        return first_login;
    }
    
    @ServiceM(desc="manages the Authentication. If this is the first login it stores the Key, if not, it tries to laod the key using the provided Password.",
              category="Method",
              params={"pw: String -> Password the User entered."},
              returns="void",
              thrown={"None"})
    public void start_authentication(String pw){
        if(first_login){
            saving_key(pw, this.key);
        }else{
            logger.log(LoggingLevels.DEBUG, "Trying to load Key");
            retreiving_key(pw);
        }
    }

    @ServiceM(desc="Generates a Key using the AES-algorithm.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void generate_key() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            logger.log_exception(e);
        }
        this.key = keyGenerator.generateKey();
    }
    
    @ServiceM(desc="Encrypts the provided message using the generated Key.",
              category="Method",
              params={"message: String -> the provided message to encrypt"},
              returns="String -> encrypted message",
              thrown={"InvalidKeyException -> If the used Key is invalid",
                      "NoSuchPaddingException -> if the encryption padding mismatches",
                      "NoSuchAlgorthmException -> If the used Algorithm to encrypt the Message does not exist.",
                      "IllegalBlockSizeException -> If the size of the to encrypt message mismatches",
                      "BadPaddingException -> If the Padding of the message mismatches with the Algorithm"})
    public String encrypt(String message) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    @ServiceM(desc="Decrypts the provided message using the generated Key.",
              category="Method",
              params={"message: String -> the provided message to decrypt"},
              returns="String -> decrypted message",
              thrown={"InvalidKeyException -> If the used Key is invalid",
                      "NoSuchPaddingException -> if the encryption padding mismatches",
                      "NoSuchAlgorthmException -> If the used Algorithm to encrypt the Message does not exist.",
                      "IllegalBlockSizeException -> If the size of the to encrypt message mismatches",
                      "BadPaddingException -> If the Padding of the message mismatches with the Algorithm"})
    public String decrypt(String message) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(message));
            return new String(decrypted, StandardCharsets.UTF_8);
    }
    
    @ServiceM(desc="Returns if the verifivation was a success or not",
              category="Method",
              params={"None"},
              returns="boolean -> true if the verification was successful",
              thrown={"None"})
    public boolean verification_success(){
        return verification_good;
    }

    @ServiceM(desc="Testmethod for this class",
              category="Method",
              params={"args: String[] -> Arguments"},
              returns="void",
              thrown={"Exception -> if the encryption or decryption method throws am Exception"})
    public static void main(String[] args) throws Exception{
        Cryptographer c = new Cryptographer();
        c.start_authentication("Werterzu900!");

        String encryptedBase64 = c.encrypt("Das ist eine geheime Nachricht ...");
        System.out.println("Encrypted data: " + encryptedBase64);
        String decryptedText = c.decrypt(encryptedBase64);
        System.out.println("Decrypted data: " + decryptedText);
    }
    
    @ServiceM(desc="Saves the Key in a KeyStorage with the given Password and Key",
              category="Method",
              params={"pw: String -> Password given by the user", "key: Key -> generated Key to save"},
              returns="boolean -> true if saving was successful",
              thrown={"None"})
    public boolean saving_key(String pw, Key key){
        //standart path for file
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JCEKS");
        } catch (KeyStoreException e) {
            logger.log_exception(e);
        }
        char[] password = pw.toCharArray();
        if(!new File(CREED_SEC).exists()){
            try {
                keyStore.load(null, password);
                logger.log(LoggingLevels.DEBUG, "Loading new KeyStore for " + CREED_SEC);
            } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
                logger.log_exception(e);
            }
        }
        else{
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(CREED_SEC);
            } catch (FileNotFoundException e) {
                logger.log_exception(e);
            }
            try {
                keyStore.load(fis, password);
            } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
                logger.log_exception(e);
            }
        }

        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
        SecretKey mySecretKey = new SecretKeySpec(key.getEncoded(), "AES");
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        try {
            keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);
            //weiter optionen um den Key hier zu speichern, ohne Password und ProtectionParameter, sondern mit Certificate
        } catch (KeyStoreException e) {
            logger.log_exception(e);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(CREED_SEC);
            try {
                keyStore.store(fos, password);
            } catch (NoSuchAlgorithmException | CertificateException e) {
                logger.log_exception(e);
            }
            return true;
        } catch (IOException | KeyStoreException e) {
            logger.log_exception(e);
            return false;
        }
    }

    @ServiceM(desc="Loads the Key from the KeyStorage and puts it into this class",
              category="Method",
              params={"pw: String -> the Password provided to read the key"},
              returns="void",
              thrown={"None"})
    public void retreiving_key(String pw) {
        System.out.println(STR."RETRIEVING_KEY, PW: \{pw}");
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JCEKS");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        char[] password = pw.toCharArray();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(CREED_SEC);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            keyStore.load(fis, password);

        } catch(NoSuchAlgorithmException e){
            logger.log_exception(e);
        }catch(CertificateException e){
            logger.log_exception(e);
        }catch (IOException e) {
            logger.log_exception(e);
            if(e.getCause().getLocalizedMessage().equals("Password verification failed")){
                this.verification_good = false;
                return;
            }
        }
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
        KeyStore.SecretKeyEntry secretKeyEnt = null;
        try {
            secretKeyEnt = (KeyStore.SecretKeyEntry)keyStore.getEntry("secretKeyAlias", protectionParam);
        } catch (NoSuchAlgorithmException| UnrecoverableEntryException | KeyStoreException e) {
            logger.log_exception(e);
        }
        SecretKey mysecretKey = secretKeyEnt.getSecretKey();

        this.key = mysecretKey;
    }
}
