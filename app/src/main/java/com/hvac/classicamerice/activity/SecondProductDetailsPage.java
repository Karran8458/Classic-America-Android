package com.hvac.classicamerice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hvac.classicamerice.R;
import com.squareup.picasso.Picasso;

public class SecondProductDetailsPage extends AppCompatActivity {

    String TAG = SecondProductDetailsPage.class.getSimpleName();

    String stringName,stringImage,stringDesc,stringSpecs,stringDiagramURL;
    ImageView imageView;
    TextView textViewProductName, textViewDesc;
    LinearLayout linearLayoutFirst,linearLayoutSecond,linearLayoutThird;
    TextView mTitleTextView;
    ImageView imageViewBackPressed;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_page_product_details);

        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar!=null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView        =   mInflater.inflate(R.layout.custom_actionbar, null);
        mTitleTextView          =   (TextView) mCustomView.findViewById(R.id.title_text);
        imageViewBackPressed    =   (ImageView)mCustomView.findViewById(R.id.imageView_back_icon);

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


        imageView           =   findViewById(R.id.second_page_product_image);
        textViewProductName =   findViewById(R.id.second_page_textViewProductName);
        textViewDesc        =   findViewById(R.id.second_page_textViewDesc);

        linearLayoutFirst   =   findViewById(R.id.ll_product_spec);
        linearLayoutSecond  =   findViewById(R.id.ll_product_diagram);
        linearLayoutThird   =   findViewById(R.id.ll_product_buy);


        Intent iin = getIntent();
        Bundle extras = iin.getExtras();

        if (extras != null) {
            stringName          = (String) extras.get("ProductName");
            stringImage         = (String) extras.get("ProductImageURL");
            stringDiagramURL    = (String) extras.get("ProductDiagram");
            stringDesc          = (String) extras.get("ProductDesc");
            stringSpecs         = (String) extras.get("ProductSpec");

            Log.e(TAG, "onCreate: Product Page name :" + stringName+"\t Desc :"+stringDesc
                    +"\n URL :"+stringImage);

            Log.e(TAG, "onCreate: Today result:"+stringDiagramURL+"\t second:"+stringSpecs );


            preferences = PreferenceManager.getDefaultSharedPreferences(SecondProductDetailsPage.this);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("tempProductName",stringName);
            editor.putString("tempProductImageURL",stringImage);
            editor.putString("tempProductDiagram",stringDiagramURL);
            editor.putString("tempProductDesc",stringDesc);
            editor.putString("tempProductSpec",stringSpecs);
            // Save the changes in SharedPreferences
            editor.apply(); // commit changes

            //Apply Product name in heading
            mTitleTextView.setText(stringName);


            //Apply url and image name string in picasso imageView
            String itemImage = "https://www.acr4sale.com/product_images/uploaded_images/"+ stringImage;

            Log.d(TAG, "onCreate: page on -->"+stringImage);
            Picasso.with(SecondProductDetailsPage.this).load(stringImage)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .resize(500,500) // Width and Height
                    .centerCrop() // Image scaling type
                    .onlyScaleDown() // Only resize image when it is larger than provided dimension
                    //.fit() // fil cannot be use with resize and onlyScaleDown
                    .into(imageView);

            textViewProductName.setText(stringName);
            textViewDesc.setText(stringDesc);

        }

        linearLayoutFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent(SecondProductDetailsPage.this,PDFViewDetailsPage.class);
                Intent intent = new Intent(SecondProductDetailsPage.this,DocumentsViewPageActivity.class);
                intent.putExtra("ProductName",stringName);
                intent.putExtra("ProductSpec",stringSpecs);
                startActivity(intent);
            }
        });

        linearLayoutSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent(SecondProductDetailsPage.this,PDFViewDetailsPage.class);
                Intent intent = new Intent(SecondProductDetailsPage.this,DocumentsViewPageActivity.class);
                intent.putExtra("ProductName",stringName);
                intent.putExtra("ProductDiagram",stringDiagramURL);
                startActivity(intent);
            }
        });

        linearLayoutThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondProductDetailsPage.this,DocumentsViewPageActivity.class);
                intent.putExtra("ProductName",stringName);
                intent.putExtra("ProductRequest","click");
                startActivity(intent);
               // finish();
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();

        preferences             =   PreferenceManager.getDefaultSharedPreferences(SecondProductDetailsPage.this);
        stringName              =   preferences.getString("tempProductName", "");
        stringImage             =   preferences.getString("tempProductImageURL", "");
        stringDiagramURL        =    preferences.getString("tempProductDiagram","");
        stringDesc              =    preferences.getString("tempProductDesc","");
        stringSpecs             =    preferences.getString("tempProductSpec","");

        //Custom Actionbar Title using Apply Product name in heading
        mTitleTextView.setText(stringName);

        Picasso.with(SecondProductDetailsPage.this).load(stringImage)
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(500,500) // Width and Height
                .centerCrop() // Image scaling type
                .onlyScaleDown() // Only resize image when it is larger than provided dimension
                .into(imageView);
        textViewProductName.setText(stringName);
        textViewDesc.setText(stringDesc);

    }

}
