
//ENCRYPT.JAVA FILE
package com.example.chetan578.eandd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static com.example.chetan578.eandd.MainActivity.myString;
import static com.example.chetan578.eandd.MainActivity.outputString;
import static com.example.chetan578.eandd.MainActivity.password;
import static javax.crypto.Cipher.DECRYPT_MODE;


public class Encrypt extends AppCompatActivity {
    TextView outputText;
    TextView relativeSpeed,keyLength,cipherTextType,noOfRounds,securityLevel,blockSize;
    String algoSelected;
    Button decryptbtn;
Intent intent;
public  void setParams(String speed,String keylength,String blocksize,String cipherText,String rounds,String security)
{
    relativeSpeed.setText(speed);
    keyLength.setText(keylength);
    blockSize.setText(blocksize);
    cipherTextType.setText(cipherText);
    noOfRounds.setText(rounds);
    securityLevel.setText(security);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encypt);
        decryptbtn=findViewById(R.id.decrypt);
     outputText=findViewById(R.id.outputText);

     relativeSpeed=findViewById(R.id.relativeSpeed);
     keyLength=findViewById(R.id.keyLength);
     cipherTextType=findViewById(R.id.cipherTextType);
     noOfRounds=findViewById(R.id.noOfRounds);
     blockSize=findViewById(R.id.blockSize);
     securityLevel=findViewById(R.id.securityLevel);
     intent = getIntent();
     algoSelected=intent.getStringExtra("algo");

     if(algoSelected.equals("AES")) {
         setParams("Fast","128, 192, or 256 bits","128 bits","Symmetric Block Cipher","10-128 bits,12-192 bits,14-256 bits","Excellent Security");
     }
     else if(algoSelected.equals("DES")) {
         setParams("Slow", "56 bits", "64 bits", "Symmetric Block Cipher", "16", "Poor Security");
     }
     else{
      setParams("Fast","32 bits up to 448 bits","64 bits","Symmetric Block Cipher","16","Good Security");
     }

     outputText.setText(outputString);

        decryptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputString=decrypt(outputString,password.getText().toString());
                    Log.i("out",outputString);
                    outputText.setText(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private String decrypt(String outputString, String password)throws Exception {
        if(algoSelected.equals("DES"))
        {
            outputText.setText(myString);
        }
        SecretKeySpec key=generateKey(password);
        Cipher c=Cipher.getInstance(algoSelected);
        c.init(DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue=new String(decValue);
        return  decryptedValue;
    }
    private SecretKeySpec generateKey(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[]key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,algoSelected);
        return secretKeySpec;
    }
}
