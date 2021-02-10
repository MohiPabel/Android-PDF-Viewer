package com.example.androidpdfviewer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_PDF_CODE = 1001;
    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request Read & Write External Storage
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener(){
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permission, token);
                    }
                }).check();

            button1 = (Button) findViewById(R.id.BtCP);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openActivity2();
                }
            });


            button2 = (Button) findViewById(R.id.BtVP);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent browsePDF = new Intent(Intent.ACTION_GET_CONTENT);
                    browsePDF.setType("application/pdf");
                    browsePDF.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(browsePDF, "Select PDF"),PICK_PDF_CODE);
                }
            });
            button3 = (Button) findViewById(R.id.BtnCnv);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSelectConverter();
                }
            });

            button4 = (Button) findViewById(R.id.BtnExit);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });

    }

    private void openSelectConverter() {
        Intent intent = new Intent(this, SelectConverter.class);
        startActivity(intent);
    }

    //open PDF Creator Activity(Activity 2)
    private void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    //Activity3
    //ctrl+o
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedPDF = data.getData();
            Intent intent = new Intent(MainActivity.this,Activity3.class);
            intent.putExtra("ViewType", "storage");
            intent.putExtra("FileUri",selectedPDF.toString());
            startActivity(intent);
        }
    }
}
