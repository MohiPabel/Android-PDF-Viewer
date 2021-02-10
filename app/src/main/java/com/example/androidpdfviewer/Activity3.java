package com.example.androidpdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;

public class Activity3 extends AppCompatActivity {

    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        pdfView = (PDFView) findViewById(R.id.pdf_viewer);

        if(getIntent() != null)
        {
            String viewType = getIntent().getStringExtra("ViewType");
            if(viewType.equals("storage"))
            {
                Uri pdfFile = Uri.parse(getIntent().getStringExtra("FileUri"));

                pdfView.fromUri(pdfFile)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(Activity3.this, "Error while opening page"+page, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onTap(new OnTapListener() {
                            @Override
                            public boolean onTap(MotionEvent e) {
                                return true;
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                pdfView.fitToWidth();//Fixed screen size
                            }
                        })
                        .enableAnnotationRendering(true) //// render all contents of pdf file
                        .invalidPageColor(Color.WHITE)
                        .load();
            }
        }
    }
}
