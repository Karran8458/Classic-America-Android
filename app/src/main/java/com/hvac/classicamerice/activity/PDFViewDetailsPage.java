package com.hvac.classicamerice.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.textfield.TextInputLayout;
import com.hvac.classicamerice.R;
import com.hvac.classicamerice.Utils.FileDownloader;
import com.hvac.classicamerice.Utils.PDFTools;
import com.hvac.classicamerice.Utils.PermissionUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class PDFViewDetailsPage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 11;
    String TAG = PDFViewDetailsPage.class.getSimpleName();

    ProgressBar progressBar;
    String stringName,stringProductRequest,stringDesc,stringSpecs,stringDiagramURL;
    String pdfFileName;
    Integer pageNumber = 0;
    Button buttonDown,buttonView;
    PDFView pdfView;
    String viewDOCURL;

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    PermissionUtils permissionUtils;
    TextInputLayout urlTextInputLayout;
    private static final String googleDocsUrl = "http://docs.google.com/gview?embedded=true&url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdf_view_details);

        permissionUtils = new PermissionUtils();

        Intent iin      = getIntent();
        Bundle extras   = iin.getExtras();
        if (extras != null) {
            stringName          = (String) extras.get("ProductName");
            stringDiagramURL    = (String) extras.get("ProductDiagram");
            stringSpecs         = (String) extras.get("ProductSpec");

            stringProductRequest    = (String) extras.get("ProductRequest");

            Log.e(TAG, "onCreate: Product Page name :" + stringName+"\t Specs:"+stringSpecs
                    + "\n productRequest fromm user :" + stringProductRequest+"\t Diagram :"+stringDiagramURL);
        }

        pdfView     =   findViewById(R.id.pdfView);
        progressBar =   findViewById(R.id.progressBar);

        buttonDown  =   findViewById(R.id.button1);
        buttonView  =   findViewById(R.id.button2);

        if (ActivityCompat.checkSelfPermission(PDFViewDetailsPage.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(PDFViewDetailsPage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PDFViewDetailsPage.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
           // downloadFile();
        }


        if(stringSpecs!=null)
        {
            Log.e(TAG, "onCreate: product Specification :"+ stringSpecs.toString().trim());

            String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";

            if(stringSpecs.contains("https://www."))
            {
                String ff = stringSpecs.replace("https://www.","");
                // pdfWebViewClient.loadPdfUrl(ff.toString().trim());

                Log.e(TAG, "onCreate: today url :"+ "https://drive.google.com/viewerng/viewer?embedded=true&url="
                        + ff.toString().trim());

                pdfView.fromUri(Uri.parse(googleDocsUrl+ff))
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .defaultPage(0)
                        // .onLoad((OnLoadCompleteListener) PDFViewDetailsPage.this) // called after document is loaded and starts to be rendered
                        //.onPageChange((OnPageChangeListener) this)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        //.onFileDownload(this)
                        .load();

                pdfView.loadPages();

            }


        }
        else if(stringDiagramURL!= null)
        {
            Log.e(TAG, "onCreate: Diagram View:"+ stringDiagramURL.toString().trim());

            pdfView.fromUri(Uri.parse(googleDocsUrl+stringDiagramURL.toString().trim()))
            .load();
            pdfView.loadPages();
            ///pdfWebViewClient.loadPdfUrl(stringDiagramURL.toString().trim());

            // webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+stringDiagramURL.toString().trim());
        }


        else {
            Log.e(TAG, "onCreate: ");
            //webView.setVisibility(View.VISIBLE);
            //linearLayout.setVisibility(View.GONE);
        }


    }


    public void download(View v)
    {
        if(stringSpecs!=null)
        {
            new DownloadFile().execute(stringSpecs, "maven.pdf");
        }

    }

    public void view(View v)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stringSpecs.toString().trim())));
//        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/CLASSIC America/" + "maven.pdf");  // -> filename = maven.pdf
//        Uri path = Uri.fromFile(pdfFile);
//        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//        pdfIntent.setDataAndType(path, "application/pdf");
//        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        try{
//            startActivity(pdfIntent);
//        }catch(ActivityNotFoundException e){
//            Toast.makeText(PDFViewDetailsPage.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//        }


    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "CLASSIC America");

            folder.getParentFile().mkdirs();

          //  folder.mkdir();
            //If File is not present create directory
//            if (!folder.exists()) {
//                folder.createNewFile();
//                Log.e(TAG, "Directory Created.");
//            }

            File pdfFile = new File(folder, fileName);

            try{

                if (!pdfFile.exists())
                    pdfFile.createNewFile();

            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
               // downloadFile();
                break;
        }
    }






}
