package org.flimwip.design;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CrpytoStuff implements Serializable{

    //private static String CREED_PRIVATE = "H:\\PLAT\\Data\\certs_prv";

    //private static String CREED_PUBLIC = "H:\\PLAT\\Data\\certs_pub";

    private static String CREED_PUBLIC = "/Users/philippkotte/PLAT/Data/certs_pub";

    private static String CREED_PRIVATE = "/Users/philippkotte/PLAT/Data/certs_prv";

    private KeyPair pair;

    private boolean first_login = true;

    public CrpytoStuff(){
        try {
            start_up();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private void start_up() throws NoSuchAlgorithmException {
        //check if first login
        if(!new File(CREED_PRIVATE).exists() && !new File(CREED_PUBLIC).exists()){
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA"); //Creating KeyPair generator object
            keyPairGen.initialize(2048);                                        //Initializing the key pair generator
            this.pair = keyPairGen.generateKeyPair();
        }else{
            first_login = true;
        }
    }

    public boolean get_first_login(){
        return this.first_login;
    }

    /**
     * Loads and returns a private key using the specified password.
     *
     * @param password the password to use for retrieving the private key
     * @return the loaded private key
     * @throws NoSuchAlgorithmException if the specified key algorithm is not available
     * @throws InvalidKeySpecException if the provided key specification is invalid
     */
    private PrivateKey load_private_key(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey key = null;
        try {
            key = retreiving_key(password, CREED_PRIVATE);
        } catch (KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException | CertificateException |
                 IOException e) {
            throw new RuntimeException(e);
        }

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(new PKCS8EncodedKeySpec(key.getEncoded()));
    }

    /**
     * Loads and returns a public key using the specified password.
     *
     * @param password the password to use for retrieving the public key
     * @return the loaded public key
     * @throws NoSuchAlgorithmException if the specified key algorithm is not available
     * @throws InvalidKeySpecException  if the provided key specification is invalid
     */
    private PublicKey load_public_key(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey key = null;
        try {
            key = retreiving_key(password, CREED_PUBLIC);
        } catch (KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException | CertificateException |
                 IOException e) {
            throw new RuntimeException(e);
        }

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(new X509EncodedKeySpec(key.getEncoded()));
    }

    /**
     * Saves the public key using the specified password.
     *
     * @param password the password to use for saving the public key
     * @param key the public key to be saved
     * @return true if the public key is saved successfully, false otherwise
     */
    private boolean save_public_key(String password, PublicKey key){
        try {
            return saving_key(password, key, CREED_PUBLIC);
        }  catch (CertificateException | NoSuchAlgorithmException e) {
            System.out.println("Unexpected Exception " + e.getClass().getName());
            return false;
        }
    }

    /**
     * Saves the private key using the specified password.
     *
     * @param password the password to use for saving the private key
     * @param key the private key to be saved
     * @return true if the private key is saved successfully, false otherwise
     * @throws CertificateException if there is an error with the certificate
     * @throws NoSuchAlgorithmException if the specified key algorithm is not available
     */
    private boolean save_private_key(String password, PrivateKey key){
        try {
            return saving_key(password, key, CREED_PRIVATE);
        }  catch (CertificateException | NoSuchAlgorithmException e) {
            System.out.println("Unexpected Exception " + e.getClass().getName());
            return false;
        }
    }



    /**
     * Encrypts the given input using the RSA encryption algorithm with ECB mode and PKCS1 padding.
     *
     * @param input the input to be encrypted
     * @return the encrypted text
     * @throws NoSuchPaddingException     if the specified padding scheme is not available
     * @throws NoSuchAlgorithmException if the specified encryption algorithm is not available
     * @throws InvalidKeyException       if the provided key is invalid
     * @throws IllegalBlockSizeException if the input length is incorrect for the given cipher
     * @throws BadPaddingException       if there is an error in the padding
     */
    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
        cipher.update(input.getBytes());
        byte[] encipheredText = cipher.doFinal();
        String temp = print(encipheredText);
        System.out.println(temp);
        return temp;
    }

    /**
     * Decrypts the given input using the RSA encryption algorithm with ECB mode and PKCS1 padding.
     *
     * @param input the input to be decrypted
     * @return the decrypted text
     * @throws NoSuchPaddingException     if the specified padding scheme is not available
     * @throws NoSuchAlgorithmException if the specified encryption algorithm is not available
     * @throws InvalidKeyException       if the provided key is invalid
     * @throws IllegalBlockSizeException if the input length is incorrect for the given cipher
     * @throws BadPaddingException       if there is an error in the padding
     */
    public String decrypting(String input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
        cipher.update(input.getBytes());
        byte[] decipheredText = cipher.doFinal();
        String temp = print(decipheredText);
        System.out.println(temp);
        return temp;
    }



    public void ciphering() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA"); //Creating KeyPair generator object
        keyPairGen.initialize(2048);                                        //Initializing the key pair generator
        KeyPair pair = keyPairGen.generateKeyPair();                                //Generating the pair of keys



        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //Initializing a Cipher object
        //cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());

        //Adding data to the cipher
        //byte[] input = "M6kUVm3T".getBytes();
        //cipher.update(input);
        //encrypting the data
        //byte[] cipherText = cipher.doFinal();
        //System.out.println(print(cipherText));


        //Initializing the same cipher for decryption
        //cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());


        //Decrypting the text
        //byte[] decipheredText = cipher.doFinal(cipherText);
        //System.out.println(new String(decipheredText));

        //KeyFactory kf = KeyFactory.getInstance("RSA");
        //kf.generatePrivate(new PKCS8EncodedKeySpec(/*private key bytes*/));
    }


    public static boolean saving_key(String pw, Key key, String path) throws CertificateException, NoSuchAlgorithmException {
        //standart path for file
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JCEKS");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        char[] password = pw.toCharArray();
        if(!new File(path).exists()){
            try {
                keyStore.load(null, password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                keyStore.load(fis, password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
        SecretKey mySecretKey = new SecretKeySpec(key.getEncoded(), "DSA");
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        try {
            keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);
            //weiter optionen um den Key hier zu speichern, ohne Password und ProtectionParameter, sondern mit Certificate
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(CREED_PRIVATE);
            keyStore.store(fos, password);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Coldnt store key");
            return false;
        } catch (IOException e) {
            System.out.println("Unexpected Runtime Exception");
            return false;
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }



    public static SecretKey retreiving_key(String pw, String path) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        char[] password = pw.toCharArray();
        FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
        KeyStore.SecretKeyEntry secretKeyEnt = (KeyStore.SecretKeyEntry)keyStore.getEntry("secretKeyAlias", protectionParam);
        SecretKey mysecretKey = secretKeyEnt.getSecretKey();

        return mysecretKey;

    }





}
