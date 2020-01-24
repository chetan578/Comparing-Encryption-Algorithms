package com.example.chetan578.eandd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static  String outputString;
    static  EditText input, password;
    Button encryptbtn, decryptbtn;
    static  String myString;
    String algoSelected;
    Spinner spinner;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner =findViewById(R.id.spinner);
        input = findViewById(R.id.input);
        myString= input.getText().toString();
        password = findViewById(R.id.password);
        encryptbtn = findViewById(R.id.encrypt);
        decryptbtn = findViewById(R.id.decrypt);
        spinner.setOnItemSelectedListener(this);

        encryptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputString = encrypt(input.getText().toString(), password.getText().toString());
                    intent=new Intent(MainActivity.this, Encrypt.class);
                    myString=input.getText().toString();
                    intent.putExtra("algo",algoSelected);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
   /* String st="Hello";  byte str[]=st.getBytes(); Cipher c=Cipher.getInstance("DES");
    KeyGenerator kg=KeyGenerator.getInstance("DES");
    SecretKey sk=kg.generateKey();
    c.init(Cipher.ENCRYPT_MODE,sk); byte ct[]=c.doFinal(str); ("ENCRYPT_MODE DATA : "+ new String(ct));
    c.init(Cipher.DECRYPT_MODE,sk); byte ct1[]=c.doFinal(ct); ("DECRYPT_MODE DATA : "+ new String(ct1));
    */
    private String encrypt(String Data, String password) throws Exception {
        if (algoSelected.equals("DES")) {
            byte str[] = Data.getBytes();
            Cipher c = Cipher.getInstance("DES");
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            SecretKey sk = kg.generateKey();
            c.init(Cipher.ENCRYPT_MODE, sk);
            byte ct[] = c.doFinal(str);
            String  encryptedValue = Base64.encodeToString(ct, Base64.DEFAULT);
            return (encryptedValue);
        } else {
            SecretKeySpec key = generateKey(password);
            Cipher c = Cipher.getInstance(algoSelected);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(Data.getBytes());
            //String encryptedValue= new String(encVal);
            String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
            return encryptedValue;
        }
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[]key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,algoSelected);
        return secretKeySpec;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        algoSelected=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
