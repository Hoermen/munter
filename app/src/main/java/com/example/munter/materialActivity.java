package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdfview.PDFView;

import java.io.File;

public class materialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        Intent i = getIntent();
        final String material = i.getStringExtra("material");
        ImageView imageView = findViewById(R.id.ImageView);
        imageView.setImageURI(Uri.parse(material));
    }
}