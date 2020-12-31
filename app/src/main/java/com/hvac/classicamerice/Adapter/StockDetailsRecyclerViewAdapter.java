package com.hvac.classicamerice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hvac.classicamerice.POJOClass.StockDetails;
import com.hvac.classicamerice.R;
import com.hvac.classicamerice.activity.FirstPageInnerCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StockDetailsRecyclerViewAdapter extends RecyclerView.Adapter<StockDetailsRecyclerViewAdapter.ViewHolder> {

    private String TAG = StockDetailsRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private List<StockDetails.CategoriesBean> categoriesArrayList;

    public StockDetailsRecyclerViewAdapter(Context context, List<StockDetails.CategoriesBean> categoriesArrayList)
    {
        this.context                =   context;
        this.categoriesArrayList    =   categoriesArrayList;
    }

    @NonNull
    @Override
    public StockDetailsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_details_items_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockDetailsRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mItem = categoriesArrayList.get(position);

        Log.e(TAG, "onBindViewHolder: Names :"+categoriesArrayList.get(position).getName()
                +"\n Images :"+categoriesArrayList.get(position).getImage());

//        Log.e(TAG, "onBindViewHolder: Sub Category name:"+
//                categoriesArrayList.get(position).getCategories().get(position).getProducts().get(position).getName()
//        +"\n Description :"+categoriesArrayList.get(position).getCategories().get(position).getProducts().get(position).getDescription());

        holder.textViewName.setText(categoriesArrayList.get(position).getName());
        holder.textViewStockCount.setText(categoriesArrayList.get(position).getStockInnerCategoryCount());

       // int[] image_array = new int[] { R.drawable.ic_control_board_cat, R.drawable.ic_fan_thermostat_cat};

        //{
        //Default to show in app Logo

        if(categoriesArrayList.get(position).getName().contains("Mini Splits"))
        {
            Picasso.with(context).load(R.drawable.ic_control_board_cat)
                    .placeholder(R.mipmap.ic_launcher_ca_round)
                    .resize(200, 200)
                    .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                    .into(holder.imageView);

        }
        else if(categoriesArrayList.get(position).getName().contains("Remotes"))
        {

            Picasso.with(context).load(R.drawable.ic_mini_split_controls)
                    .placeholder(R.mipmap.ic_launcher_ca_round)
                    .resize(200, 200)
                    .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                    .into(holder.imageView);
        }
        else
        {
        Picasso.with(context).load(R.drawable.app_logo)
                .placeholder(R.mipmap.ic_launcher_ca_round)
                .resize(200, 200)
                .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                .into(holder.imageView);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                // Log.d("TAG", "onClick: inside-click : "+adapterPosition);
                StockDetails.CategoriesBean categoriesBeanX = categoriesArrayList.get(adapterPosition);

                    Intent intent = new Intent(context, FirstPageInnerCategory.class);
                    intent.putExtra("CatName",categoriesBeanX.getName());
                    intent.putExtra("CatDesc",categoriesBeanX.getDescription());
                    context.startActivity(intent);
            }
        });

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final ImageView imageView;
        private final TextView textViewName;
        // private final AppCompatTextView textViewName;
        private final TextView textViewStockCount;
        StockDetails.CategoriesBean mItem;

        private ViewHolder(View view) {
            super(view);

            mView = view;
            imageView                      = view.findViewById(R.id.stock_image);
            textViewName                   = view.findViewById(R.id.textViewName);
            textViewStockCount             = view.findViewById(R.id.textViewStockCount);

        }

    }

}
