package br.com.notajuris.notajuris.infra.bean;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;

@Component
public class AesEncryptionConfig implements AttributeConverter<String, String>{

    @Value("${security.crypto.key}")
    private String encryptionKey;
    private final String encryptionCipher = "AES";

    private static Key key;
    private static Cipher cipher;

    private Key getKey(){
        if(key == null)
            key = new SecretKeySpec(encryptionKey.getBytes(), encryptionCipher);
        return key;
    }

    private Cipher getCipher() throws GeneralSecurityException{
        if (cipher == null) 
            cipher = Cipher.getInstance(encryptionCipher);
        return cipher;
    }

    private void initCipher(int encryptMode) throws GeneralSecurityException{
        
        getCipher().init(encryptMode, getKey());
    }

    @Override
    public String convertToDatabaseColumn(String attribute){
        if(attribute == null)
            return null;
        try {
            initCipher(Cipher.ENCRYPT_MODE);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        byte[] bytes = attribute.getBytes();
        String encoded = "";
        try {
            encoded = Base64.getEncoder().encodeToString(getCipher().doFinal(bytes));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return encoded;
        
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;

        byte[] bytes = new byte[0];
        try {
            initCipher(Cipher.DECRYPT_MODE);
            bytes = getCipher().doFinal(Base64.getDecoder().decode(dbData));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        
        return new String(bytes);
    }
    
}
