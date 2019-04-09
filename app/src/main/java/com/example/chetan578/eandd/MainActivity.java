package com.example.chetan578.eandd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    String outputString;
    EditText input, password;
    Button encryptbtn, decryptbtn;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        outputText =findViewById(R.id.outputText);
        password = findViewById(R.id.password);
        encryptbtn = findViewById(R.id.encrypt);
        decryptbtn = findViewById(R.id.decrypt);
        encryptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputString = encrypt(input.getText().toString(), password.getText().toString());
                    outputText.setText(outputString);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        decryptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputString=decrypt(outputString,password.getText().toString());
                    outputText.setText(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String decrypt(String outputString, String password)throws Exception {
        SecretKeySpec key=generateKey(password);
        Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue=new String(decValue);
        return  decryptedValue;
    }

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c =Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(Data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[]key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}
