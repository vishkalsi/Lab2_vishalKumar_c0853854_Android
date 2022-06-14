package com.cortexitsolutions.lab2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.cortexitsolutions.lab2.MainActivity;
import com.cortexitsolutions.lab2.R;
import com.cortexitsolutions.lab2.database.model.Product;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public ProductAdapter(Context context, List<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context =  context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate( R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mData.get(position);
        holder.productId.setText( ""+product.getProductId() );
        holder.productName.setText( product.getProductName() );
        holder.productDescription.setText( product.getProductDescription() );
        holder.productPrice.setText( "$"+product.getProductPrice()+"/-" );
        holder.edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).startAddProductActivity(product);
            }
        } );

        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(context)
                        .setMessage("Are you sure want to Delete this product?")
                        .setCancelable(false)
                        .setPositiveButton( Html.fromHtml("<font color='#0096ff'>Yes</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((MainActivity)context).deleteProduct(product);
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#0096ff'>NO</font>"), null)
                        .show();
            }
        } );
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<Product> mList) {
        this.mData = mList;
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView productId, productName, productDescription,productPrice;
        AppCompatImageView edit, delete;

        ViewHolder(View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.product_id);
            productName= itemView.findViewById( R.id.product_name );
            productDescription= itemView.findViewById( R.id.description );
            productPrice= itemView.findViewById( R.id.price );
            edit = itemView.findViewById( R.id.edit );
            delete = itemView.findViewById( R.id.delete );
        }
    }

    // convenience method for getting data at click position
    Product getItem(int id) {
        return mData.get(id);
    }


}