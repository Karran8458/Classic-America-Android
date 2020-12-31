package com.hvac.classicamerice.ui.ProductSpecs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hvac.classicamerice.Adapter.ProductsSpecDetailsRecyclerViewAdapter;
import com.hvac.classicamerice.POJOClass.StockDetails;
import com.hvac.classicamerice.R;
import com.hvac.classicamerice.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecsFragment extends Fragment {

    private String TAG = ProductSpecsFragment.class.getSimpleName();

    private List<ProductSpecsViewModel> productsSpecsViewModelList = new ArrayList<>();
    private EditText editText;
    private RecyclerView recyclerView;
    ProductsSpecDetailsRecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_products_specs, container, false);

        editText        =   root.findViewById(R.id.product_spec_editText_search);
        recyclerView    =   root.findViewById(R.id.products_specs_page_rv);

        //================================Search in EdiText Coding Part=============================
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(recyclerViewAdapter!=null)
                {
                    recyclerViewAdapter.getFilter().filter(s);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //=====================================End of code==========================================

        //Getting category wise Only Main Products Array result json
        try {

            String jsonFileString = Utils.getJsonFromAssets(getActivity(), "data.json");
            Gson gson = new Gson();

            StockDetails resultStock = gson.fromJson(jsonFileString, StockDetails.class);

            List<StockDetails.CategoriesBean> categories = resultStock.getCategories();
            for (int i = 0; i < categories.size(); i++)
            {
                //getting Inside Category Inner product Details
                if(categories.get(i).getProducts().size()>0)
                {
                    Log.e(TAG, "onCreateView: Size is :"+ categories.get(i).getProducts().size());
                    List<StockDetails.CategoriesBean.ProductsBean> InnerCat = categories.get(i).getProducts();

                    if(InnerCat.size()>0) {

                        for (int j = 0; j < InnerCat.size(); j++) {

                            ProductSpecsViewModel detailsPOJO = new ProductSpecsViewModel();

                            detailsPOJO.setProductName(InnerCat.get(j).getName());
                            detailsPOJO.setProductImage(InnerCat.get(j).getImage());
                            detailsPOJO.setProductDesc(InnerCat.get(j).getDescription());
                            detailsPOJO.setProductDiagram(InnerCat.get(j).getDiagram_url());
                            detailsPOJO.setProductSpecs(InnerCat.get(j).getSpecs_url());
                            detailsPOJO.setProductURL(InnerCat.get(j).getUrl());

                            productsSpecsViewModelList.add(detailsPOJO);

                        }
                    }


                }

            }

        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        //Load product Inner Details to RecyclerView Adapter
        if(productsSpecsViewModelList.size()>0 )
        {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerViewAdapter =  new ProductsSpecDetailsRecyclerViewAdapter(getActivity(),productsSpecsViewModelList);
            recyclerView.setAdapter(recyclerViewAdapter);
        }


        return root;
    }
}