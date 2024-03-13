package org.flimwip.design;

import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

public class Cryptographer {


    public SecretKey key;
    private boolean first_login = false;

    private boolean verification_good = true;
    private MyLogger logger = new MyLogger(Cryptographer.class);
    private static String CREED_SEC = "H:\\PLAT\\Data\\common\\certs_sec";

    public Cryptographer(){
        logger.set_Level(LoggingLevels.FINE);
        startup();
    }


    private void startup(){
        first_login = !new File(CREED_SEC).exists();
        if(first_login){
            generate_key();
        }
    }

    public boolean get_first_login(){
        return first_login;
    }

    public void start_authentication(String pw){
        if(first_login){
            saving_key(pw, this.key);
        }else{
            logger.log(LoggingLevels.DEBUG, "Trying to load Key");
            retreiving_key(pw);
        }
    }

    public void generate_key() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.key = keyGenerator.generateKey();
    }

    public String encrypt(String message) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String message) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(message));
            return new String(decrypted, StandardCharsets.UTF_8);
    }

    public boolean verification_success(){
        return verification_good;
    }

    public static void main(String[] args) throws Exception{


        Cryptographer c = new Cryptographer();
        c.start_authentication("Werterzu900!");

        String encryptedBase64 = c.encrypt("Das ist eine geheime Nachricht ...");
        System.out.println("Encrypted data: " + encryptedBase64);
        String decryptedText = c.decrypt(encryptedBase64);
        System.out.println("Decrypted data: " + decryptedText);
    }

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

    public void retreiving_key(String pw) {
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
