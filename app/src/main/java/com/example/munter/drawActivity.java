package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class drawActivity extends AppCompatActivity {
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        final SignaturePad mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        final SignaturePad signaturePad = findViewById(R.id.signature_pad);

        try {
            FileInputStream fIn = openFileInput ("draw.bmp") ;
            bitmap = BitmapFactory.decodeStream(fIn);
            fIn.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        if (bitmap != null) {
            signaturePad.setSignatureBitmap(bitmap);
        }


        final Button b = findViewById(R.id.drawBack);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = signaturePad.getSignatureBitmap();
               try {
                   File f = new File(drawActivity.this.getFilesDir(), "draw.bmp");
                   f.createNewFile();

                    //Convert bitmap to byte array
                   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                   byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                   FileOutputStream fos = new FileOutputStream(f);
                   fos.write(bitmapdata);
                   fos.flush();
                   fos.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
                drawActivity.super.onBackPressed();
            }
        });
    }
}