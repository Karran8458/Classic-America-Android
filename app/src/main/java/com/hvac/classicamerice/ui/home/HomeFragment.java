package com.hvac.classicamerice.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hvac.classicamerice.Adapter.StockDetailsRecyclerViewAdapter;
import com.hvac.classicamerice.POJOClass.StockDetails;
import com.hvac.classicamerice.R;
import com.hvac.classicamerice.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

   // private HomeViewModel homeViewModel;
   private String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private StockDetailsRecyclerViewAdapter recyclerViewAdapter;

   private List<StockDetails.CategoriesBean> categoriesArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       // homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView            =   root.findViewById(R.id.recyclerview_cat);

//        final TextView textView = root.findViewById(R.id.text_home);
//
//
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        try {

            String jsonFileString = Utils.getJsonFromAssets(getActivity(), "data.json");
            //Log.e("data -->", jsonFileString);
            Gson gson = new Gson();
            StockDetails resultStock = gson.fromJson(jsonFileString, StockDetails.class);

            if(resultStock!= null )
            {
                categoriesArrayList.clear();
                List<StockDetails.CategoriesBean> categories = resultStock.getCategories();
                for (int i = 0; i < categories.size(); i++)
                {
//                    Log.d(TAG, "onCreate:Categories Names :"+categories.get(i).getName()
//                            +"\n Image:"+categories.get(i).getImage());
                    StockDetails.CategoriesBean stockDetails = new StockDetails.CategoriesBean();
                    stockDetails.setCategories(categories.get(i).getCategories());
                    stockDetails.setName(categories.get(i).getName());
                    stockDetails.setImage(categories.get(i).getImage());
                    stockDetails.setDescription(categories.get(i).getDescription());

                    if(categories.get(i).getCategories().size()>0) {
                        stockDetails.setStockInnerCategoryCount(categories.get(i).getCategories().size() + " Categories");
                    }
                    else
                    {
                        stockDetails.setStockInnerCategoryCount(categories.get(i).getProducts().size() + " Products");
                    }

                    categoriesArrayList.add(stockDetails);
                }

                if(categoriesArrayList.size()>0)
                {
                    int numberOfColumns = 2;
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                    recyclerViewAdapter =  new StockDetailsRecyclerViewAdapter(getActivity(),categoriesArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);

                }
                else
                {
                    Log.d(TAG, "onCreateView: No Array");
                }
            }

        }
        catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return root;
    }
}