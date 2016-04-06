package com.app.branchhealth.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;

import com.app.branchhealth.R;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@SuppressLint("InlinedApi")
public class EncryptionUtils{
    @SuppressWarnings("unused")
    private static final String DEFAULT_CHAR_SET = "utf-8";
    @SuppressWarnings("unused")
    private static final String ENCRYPTION_KEY_TYPE = "DESede";
    @SuppressWarnings("unused")
    private static final String ENCRYPTION_ALGORITHM = "DESede/ECB/PKCS5Padding";

    private static String DEFAULT_KEY_IN_STR;

    public static void prepareKeys(Context context){
        final InputStream _inStream = context.getResources().openRawResource(R.raw.default_key);

        try{
            byte[] DEFAULT_KEY_IN_BYTE = IOUtils.toByteArray(_inStream);
            _inStream.close();
            DEFAULT_KEY_IN_STR = Base64.encodeToString(DEFAULT_KEY_IN_BYTE, Base64.DEFAULT).substring(2651, 2675);
        }catch(Exception e1){
            DEFAULT_KEY_IN_STR = "";
        }
    }

    public static String encrypt(String message, Context context){
        if(DEFAULT_KEY_IN_STR == null){
            prepareKeys(context);
        }
        //return encrypt(message);
        return message;
    }

    public static String encrypt(String message){
        String convert = "";
        if(!DEFAULT_KEY_IN_STR.equals("")){
            try{
                convert = SHAConverter.sha(DEFAULT_KEY_IN_STR);
                final byte[] digestOfPassword = convert.getBytes(DEFAULT_CHAR_SET);
                byte[] keyBytes = new byte[24];
                System.arraycopy(digestOfPassword, 0, keyBytes, 0, 24);

                final SecretKey key = new SecretKeySpec(keyBytes, ENCRYPTION_KEY_TYPE);
                final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key);

                final byte[] plainTextBytes = message.getBytes(DEFAULT_CHAR_SET);
                final byte[] cipherText = cipher.doFinal(plainTextBytes);

                return Base64.encodeToString(cipherText, Base64.DEFAULT);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return message;
    }

    public static String decrypt(String message) throws Exception{
        String convert = "";
        if(!DEFAULT_KEY_IN_STR.equals("")){
            try{
                convert = SHAConverter.sha(DEFAULT_KEY_IN_STR);
                final byte[] digestOfPassword = convert.getBytes("utf-8");
                byte[] keyBytes = new byte[24];
                System.arraycopy(digestOfPassword, 0, keyBytes, 0, 24);

                final SecretKey key = new SecretKeySpec(keyBytes, ENCRYPTION_KEY_TYPE);
                final Cipher decipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
                decipher.init(Cipher.DECRYPT_MODE, key);

                byte[] decipherText = Base64.decode(message, Base64.DEFAULT);

                final byte[] plainTextBytes = decipher.doFinal(decipherText);

                return new String(plainTextBytes, "UTF-8");
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return message;
    }

}
