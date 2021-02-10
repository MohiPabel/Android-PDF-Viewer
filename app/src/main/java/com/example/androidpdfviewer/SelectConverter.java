package com.example.androidpdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectConverter extends AppCompatActivity {

    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_converter);

        button1 = (Button) findViewById(R.id.btnitp);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageToPdf();
            }
        });
    }

    private void openImageToPdf() {

        Intent intent = new Intent(this, ImageToPdf.class);
        startActivity(intent);
    }
}
