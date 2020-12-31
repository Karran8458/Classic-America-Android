package com.hvac.classicamerice.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hvac.classicamerice.R;

public class DocumentsViewPageActivity extends AppCompatActivity {

    String TAG = DocumentsViewPageActivity.class.getSimpleName();

    private static final String googleDocsUrl = "http://docs.google.com/gview?embedded=true&url=";

    String stringName,stringProductRequest,stringDesc,stringSpecs,stringDiagramURL;
    private String stringPostName,stringPostContact,stringPostEmail,stringPostMessgae;
    WebView webView;
    private ProgressDialog pDialog;
    ProgressBar progressBar;
    TextView mTitleTextView;
    ImageView imageViewBackPressed;
    //RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    public TextView textViewProductChosen,textViewProductSubmitOption;
    EditText editTextName,editTextEmail,editTextContact,editTextOptionalMessage;
    Button buttonSubmit;
    private int ViewSize = 0;

    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_documents_view_page_details);

        pDialog = new ProgressDialog(DocumentsViewPageActivity.this);
        pDialog.setCancelable(false);

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

        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar!=null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView            =   mInflater.inflate(R.layout.custom_actionbar_final_page, null);
        mTitleTextView              =   mCustomView.findViewById(R.id.title_text_inner_doc_page);
        imageViewBackPressed        =   mCustomView.findViewById(R.id.imageView_back_icon_inner_doc);
        textViewProductSubmitOption =   mCustomView.findViewById(R.id.imageButton_inner_submit);


        imageViewBackPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(mActionBar!=null)
        {
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        progressBar             =   findViewById(R.id.progressBar);
        webView                 =   findViewById(R.id.webView_documents);
        linearLayout            =   findViewById(R.id.user_filled_product_page);

        editTextName            =   findViewById(R.id.user_editText_name);
        editTextContact         =   findViewById(R.id.user_editText_contact);
        editTextEmail           =   findViewById(R.id.user_editText_email);
        editTextOptionalMessage =   findViewById(R.id.user_editText_message);

        textViewProductChosen   =    findViewById(R.id.user_chosen_product);

        // buttonSubmit            =    findViewById(R.id.product_submit);

        if(stringProductRequest!=null)
        {
            linearLayout.setVisibility(View.VISIBLE);
            mTitleTextView.setText("Request Product");
            textViewProductChosen.setText(stringName);

        }
        else
        {
            linearLayout.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(stringName);
        }


        ///////////////////
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//
//        if (Build.VERSION.SDK_INT >= 19) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        }
//        else {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
//
//        if (18 < Build.VERSION.SDK_INT ){
//            //18 = JellyBean MR2, KITKAT=19
//            webView.getSettings().setSupportZoom(true);
//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            webView.getSettings().setAllowFileAccessFromFileURLs(true);
//            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.getSettings().setAllowFileAccess(true);
//        }
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

       // webView.getSettings().setPluginState(WebSettings.PluginState.ON);
     //   webView.setWebViewClient(new HelloWebViewClient());

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
                //  setProgressBarVisibility(View.GONE);
                hideDialog();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.e(TAG, "onReceivedError: Error:"+error.getDescription().toString()+"\n Code:"+error.getErrorCode() );
                }
                Toast.makeText(DocumentsViewPageActivity.this, "Failure on loading web page", Toast.LENGTH_SHORT).show();
            }
        });

       // PdfWebViewClient pdfWebViewClient = new PdfWebViewClient(this, webView);

        ///////////////////////

        if(stringSpecs!=null)
        {
            showDialog();

            linearLayout.setVisibility(View.GONE);
            textViewProductSubmitOption.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            Log.e(TAG, "onCreate: product Specification :"+ stringSpecs.toString().trim());

            String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
            if(stringSpecs.contains("https://www."))
            {
                String ff = stringSpecs.replace("https://www.","");
               // pdfWebViewClient.loadPdfUrl(ff.toString().trim());

                Log.e(TAG, "onCreate: today url :"+ "https://drive.google.com/viewerng/viewer?embedded=true&url="
                        + ff.toString().trim());

                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +ff);
            }

           // pdfWebViewClient.loadPdfUrl(pdf);
           // pdfWebViewClient.loadPdfUrl(stringSpecs.toString().trim());

           // webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +stringSpecs.toString().trim());

        }
        else if(stringDiagramURL!= null)
        {
            showDialog();
            linearLayout.setVisibility(View.GONE);
            textViewProductSubmitOption.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            Log.e(TAG, "onCreate: Diagram View:"+ stringDiagramURL.toString().trim());

            //pdfWebViewClient.loadPdfUrl(stringDiagramURL.toString().trim());

            if(stringDiagramURL.contains("https://www."))
            {
                String ff = stringDiagramURL.replace("https://www.","");
                // pdfWebViewClient.loadPdfUrl(ff.toString().trim());
                Log.e(TAG, "onCreate: today Diagram-url :"+ "https://drive.google.com/viewerng/viewer?embedded=true&url="
                        + ff.toString().trim());

                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +ff);
            }
            //webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+stringDiagramURL.toString().trim());
        }
        else if(stringProductRequest!= null && stringProductRequest.equals("click"))
        {
            linearLayout.setVisibility(View.GONE);
            textViewProductSubmitOption.setVisibility(View.GONE);
            Log.e(TAG, "onCreate: Chosen is:"+stringName);
            webView.setVisibility(View.GONE);
                finish();

            String url = "https://www.acr4sale.com/";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setPackage("com.android.chrome");
            try {
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is probably not installed
                // Try with the default browser
                i.setPackage(null);
                startActivity(i);
            }

            // Toast.makeText(this, "Enter Request", Toast.LENGTH_SHORT).show();
        }

        else {
            webView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }

        //Click to send product Request for particular E-mail Address
        textViewProductSubmitOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringPostName      =   editTextName.getText().toString().trim();
                stringPostContact   =   editTextContact.getText().toString().trim();
                stringPostEmail     =   editTextEmail.getText().toString().trim();
                stringPostMessgae   =   editTextOptionalMessage.getText().toString().trim();

                if(isEmpty(editTextName)){
                    editTextName.setError("Required UserName");
                }
                else if(isEmail(editTextContact))
                {
                    editTextContact.setError("Required Contact Number");
                }
                else if(!isEmail(editTextEmail) && editTextEmail.getText().toString().trim().contains("@"))
                {
                    editTextEmail.setError("Enter valid Email...!");
                }
                else
                {
                    Log.e(TAG, "onClick: All-Fields" );
                    PostProductPurchase(stringPostName,stringPostContact,stringPostEmail,stringName,stringPostMessgae);
                }


            }
        });

    }

    private void PostProductPurchase(String Name, String Contact, String Email, String ProductName, String MSG) {

        //Mail ID: sales@vtronix.com (request mail)

        //Refer URL:    https://stackoverflow.com/questions/54115452/how-to-send-mail-on-button-clicked-android-studio/54115640

//        String stringMailContent =  "Hi,\n "
//                                    +"User Name              : "+Name+", \n"
//                                    +"User Contact Number    : "+Contact+", \n"
//                                    +"User E-mail Address    : "+Email+", \n"
//                                    +"Chosen Product         : "+ProductName+", \n"
//                                    +"Message                : "+MSG;

// Refer :  https://www.w3schools.com/html/tryit.asp?filename=tryhtml_table_intro
        String sendMSG = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Product Request Details</h2>\n" +
                "\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <td>User Name</td>\n" +
                "    <td>Contact Number</td>\n" +
                "    <td>Email Address</td>\n" +
                "    <td>Chosen Product</td>\n" +
                "    <td>Message</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>"+Name+"</td>\n" +
                "    <td>"+Contact+"</td>\n" +
                "    <td>"+Email+"</td>\n" +
                "    <td>"+ProductName+"</td>\n" +
                "    <td>"+MSG+"</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n"
                ;

        String mailto = "mailto:info.growtechnologies@gmail.com" +
                "?cc=" +
                "&subject=" + Uri.encode("Product Request") +
                "&body=" + Uri.encode(sendMSG);
        //"&body=" + Html.fromHtml(stringMailContent);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);
            Toast.makeText(this, "Mail Sent SuccessFully", Toast.LENGTH_SHORT).show();
            finish();

        } catch (ActivityNotFoundException e) {
            Toast.makeText(DocumentsViewPageActivity.this, "Error to open email app", Toast.LENGTH_SHORT).show();
        }


    }

    boolean isEmail(EditText text)
    {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text)
    {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

//    boolean isEmail(AppCompatEditText text)
//    {
//        CharSequence email = text.getText().toString();
//        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
//    }
//
//    boolean isEmpty(AppCompatEditText text)
//    {
//        CharSequence str = text.getText().toString();
//        return TextUtils.isEmpty(str);
//    }

    private void showDialog() {
        pDialog.setMessage("Please wait Loading in...");
        pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    private class HelloWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (progressBar.isAnimating()) {
                    progressBar.clearAnimation();
                }
            }
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);

            // relativeLayout.setVisibility(View.VISIBLE);
            // imageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    private class PdfWebViewClient extends WebViewClient
    {
        private static final String TAG = "PdfWebViewClient";
        private static final String PDF_EXTENSION = ".pdf";
        private static final String PDF_VIEWER_URL = "http://docs.google.com/gview?embedded=true&url=";

        private Context mContext;
        private WebView mWebView;
        private ProgressDialog mProgressDialog;
        private boolean isLoadingPdfUrl;

        public PdfWebViewClient(Context context, WebView webView)
        {
            mContext = context;
            mWebView = webView;
            mWebView.setWebViewClient(this);
        }

        public void loadPdfUrl(String url)
        {
            mWebView.stopLoading();

            if (!TextUtils.isEmpty(url))
            {
                isLoadingPdfUrl = isPdfUrl(url);
                if (isLoadingPdfUrl)
                {
                    mWebView.clearHistory();
                }

                showProgressDialog();
            }

            mWebView.loadUrl(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url)
        {
           // return shouldOverrideUrlLoading(url);
            String cleanUrl = url;
            if (url.contains("?")) {
                // remove the query string
                cleanUrl = url.substring(0,url.indexOf("?"));
            }

            if (cleanUrl.endsWith("pdf")) {

                try {
                    Uri uriUrl = Uri.parse(cleanUrl);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(intentUrl);
                    return true;

                } catch (Exception e) {
                    System.out.println("PDF Error :"+e.toString());
                    e.printStackTrace();
                    Toast.makeText(DocumentsViewPageActivity.this,"No PDF Viewer Installed", Toast.LENGTH_LONG).show();
                }
            }

            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl)
        {
            handleError(errorCode, description.toString(), failingUrl);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request)
        {
            final Uri uri = request.getUrl();
            return shouldOverrideUrlLoading(webView, uri.toString());
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public void onReceivedError(final WebView webView, final WebResourceRequest request, final WebResourceError error)
        {
            final Uri uri = request.getUrl();
            handleError(error.getErrorCode(), error.getDescription().toString(), uri.toString());
        }

        @Override
        public void onPageFinished(final WebView view, final String url)
        {
            Log.i(TAG, "Finished loading. URL : " + url);
            dismissProgressDialog();
        }

        private boolean shouldOverrideUrlLoading(final String url)
        {
            Log.i(TAG, "shouldOverrideUrlLoading() URL : " + url);

            if (!isLoadingPdfUrl && isPdfUrl(url))
            {
                mWebView.stopLoading();

                final String pdfUrl = PDF_VIEWER_URL + url;

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loadPdfUrl(pdfUrl);
                        //loadPdfUrl(pdfUrl);
                    }
                }, 300);

                return true;
            }

            return false; // Load url in the webView itself
        }

        private void handleError(final int errorCode, final String description, final String failingUrl)
        {
            Log.e(TAG, "Error : " + errorCode + ", " + description + " URL : " + failingUrl);
        }

        private void showProgressDialog()
        {
            dismissProgressDialog();
            mProgressDialog = ProgressDialog.show(DocumentsViewPageActivity.this, "", "Loading...");
        }

        private void dismissProgressDialog()
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }

        private boolean isPdfUrl(String url)
        {
            if (!TextUtils.isEmpty(url))
            {
                url = url.trim();
                int lastIndex = url.toLowerCase().lastIndexOf(PDF_EXTENSION);
                if (lastIndex != -1)
                {
                    return url.substring(lastIndex).equalsIgnoreCase(PDF_EXTENSION);
                }
            }
            return false;
        }
    }

    ///////////////////////////////////////
}
