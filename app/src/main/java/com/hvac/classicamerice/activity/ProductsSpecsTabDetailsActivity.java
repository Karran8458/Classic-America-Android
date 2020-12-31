package com.hvac.classicamerice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hvac.classicamerice.R;
import com.hvac.classicamerice.Utils.FileDownloader;

import java.io.File;
import java.io.IOException;

public class ProductsSpecsTabDetailsActivity extends AppCompatActivity {

    private String TAG = ProductsSpecsTabDetailsActivity.class.getSimpleName();

    String stringName,stringSpecs,stringDiagramURL;
    WebView webView;
    private ProgressDialog pDialog;
    TextView mTitleTextView,textViewDownloadPDF,textViewBuyOption;
    ImageView imageViewBackPressed;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_specs_view_page_details);

        pDialog = new ProgressDialog(ProductsSpecsTabDetailsActivity.this);
        pDialog.setCancelable(false);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView        =   mInflater.inflate(R.layout.custom_actionbar, null);
        mTitleTextView          =   mCustomView.findViewById(R.id.title_text);
        imageViewBackPressed    =   mCustomView.findViewById(R.id.imageView_back_icon);

        imageViewBackPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (mActionBar != null) {
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        webView                 =   findViewById(R.id.webView_documents_spec);
        textViewDownloadPDF     =   findViewById(R.id.download_pdf_specs);
        textViewBuyOption       =   findViewById(R.id.user_choose_buy_txt);

        Intent iin      = getIntent();
        Bundle extras   = iin.getExtras();
        if (extras != null) {
            stringName          = (String) extras.get("ProductName");
            stringSpecs         = (String) extras.get("ProductSpec");

            mTitleTextView.setText(stringName);

            Log.e(TAG, "onCreate: Product Page name :" + stringName+"\t Specs:"+stringSpecs);

            preferences = PreferenceManager.getDefaultSharedPreferences(ProductsSpecsTabDetailsActivity.this);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("tempProductName",stringName);
            editor.putString("tempProductSpec",stringSpecs);
            // Save the changes in SharedPreferences
            editor.apply(); // commit changes
        }

        // For API level below 18 (This method was deprecated in API level 18)
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialog();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(TAG, "onReceivedError: Error:"+error.toString() );
                hideDialog();
            }
        });


        if(stringSpecs!=null)
        {
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +stringSpecs);

        }


        textViewDownloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownloadFile().execute(stringSpecs, stringName+".pdf");
            }
        });

        textViewBuyOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductsSpecsTabDetailsActivity.this,DocumentsViewPageActivity.class);

                intent.putExtra("ProductName",stringName);
                intent.putExtra("ProductRequest","click");
                startActivity(intent);

            }
        });



    }


    private void showDialog() {
        pDialog.setMessage("Please wait Loading in...");
        pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    //  https://androidknowledgeblog.wordpress.com/2016/04/02/download-pdf-from-server-android/


    private class DownloadFile extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
// -> https://letuscsolutions.files.wordpress.com/2015/07/five-point-someone-chetan-bhagat_ebook.pdf
            String fileName = strings[1];
// ->five-point-someone-chetan-bhagat_ebook.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Vtronix/PDF");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideDialog();
            Toast.makeText(getApplicationContext(), "Download PDf successfully", Toast.LENGTH_SHORT).show();

            Log.d("Download complete", "----------");
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        preferences     =   PreferenceManager.getDefaultSharedPreferences(ProductsSpecsTabDetailsActivity.this);
        stringName      =   preferences.getString("tempProductName", "");
        stringSpecs     =   preferences.getString("tempProductSpec","");

        mTitleTextView.setText(stringName);

    }
}
