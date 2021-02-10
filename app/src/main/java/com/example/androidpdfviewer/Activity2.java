package com.example.androidpdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Activity2 extends AppCompatActivity {

    private static final int STORAGE_CODE = 1000;
    //declaring views
    EditText mTextEt;
    Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //initializing views(activity_main.xml)
        mTextEt = findViewById(R.id.textEt);
        mSaveBtn = findViewById(R.id.saveBtn);

        //handle button click
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle runtime permission in AndroidManifest.xml
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    //Check if permission is allowed or not
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        //request for permission
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    } else {
                        //permission granted
                        savePdf();
                    }
                } else {
                    //This OS version does not require storage permission
                    savePdf();
                }
            }
        });
    }

    private void savePdf() {
        //Create object of document class
        Document mDoc = new Document();
        //pdf file name
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {
            //create instance of Pdfwriter class
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));

            //open the document file for writing
            mDoc.open();

            //get text from EditText i.e. mTextEt
            String mText = mTextEt.getText().toString();

            //add paragraph to the document
            mDoc.add(new Paragraph(mText));

            //close the document
            mDoc.close();

            //Show the message that the file is saved
            Toast.makeText(this, mFileName+".pdf\nis saved to\n"+mFilePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //if anything goes wrong causing exception, get and show this message
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //handle permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted, call savePdf nethod
                    savePdf();
                }
                else {
                    //permission denied from popup, show error message
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}