package com.ephoenix.lmsportal.util;


import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AttributeEncryptor implements javax.persistence.AttributeConverter<String,String> {

	private static final String AES = "AES";
	
	
	private String isck="abcdefghijkl1234";
    
    private  Key key;
    private  Cipher cipher;
    
	public AttributeEncryptor() throws Exception {
		
        key = new SecretKeySpec(isck.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
		
    }
	
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
		try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public static void main(String[] args) {
		
		try {
			AttributeEncryptor attributeEncryptor = new AttributeEncryptor();
			System.out.println(attributeEncryptor.convertToDatabaseColumn("Welcome@1234"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
