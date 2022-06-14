package com.cortexitsolutions.lab2;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cortexitsolutions.lab2.adapter.ProductAdapter;
import com.cortexitsolutions.lab2.database.DatabaseHelper;
import com.cortexitsolutions.lab2.database.model.Product;
import com.cortexitsolutions.lab2.model.Products;
import com.cortexitsolutions.lab2.sharedpreference.SharedPreferenceUtility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private static final int REQ_CODE = 1;
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private FloatingActionButton fabButton;
    private ProductAdapter adapter;
    private List<Product> dbProductList = new ArrayList<>();
    public SharedPreferenceUtility mSharedPreferenceUtility;
    private AppCompatEditText searchUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mSharedPreferenceUtility = new SharedPreferenceUtility(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabButton = (FloatingActionButton)findViewById( R.id.fab_btn );
        searchUser = (AppCompatEditText) findViewById( R.id.search_user );
        db = new DatabaseHelper(mContext);
        if(!mSharedPreferenceUtility.getListUpdated()){
            db.addListItem(mProductList);
            mSharedPreferenceUtility.setListUpdated(true);
        }
        dbProductList.addAll(db.getAllProducts());
        adapter = new ProductAdapter(this, dbProductList );
        recyclerView.setAdapter(adapter);

        fabButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 startAddProductActivity(null);
            }
        } );

        searchUser.setText( "" );

        searchUser.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        } );

    }

    public void startAddProductActivity(Product products) {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra( "data", products );
        startActivityForResult(intent, REQ_CODE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == REQ_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Products product = (Products) data.getExtras().getSerializable("products");
                db.insertProdct(product.getProductName(),product.getProductDescription(),product.getProductPrice());
                dbProductList.clear();
                dbProductList = db.getAllProducts();
                adapter.setList( dbProductList );
            }else if (resultCode  == Activity.RESULT_FIRST_USER){
                Products products = (Products) data.getExtras().getSerializable("product");
                db.updateProduct( products);
                dbProductList.clear();
                dbProductList = db.getAllProducts();
                adapter.setList( dbProductList );
                Toast.makeText( mContext, "Updated", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    public void deleteProduct(Product product) {
        db.deleteProduct( product );
        dbProductList.clear();
        dbProductList = db.getAllProducts();
        adapter.setList( dbProductList );
        Toast.makeText( mContext, "Deleted", Toast.LENGTH_SHORT ).show();
    }

    void filter(String text){
        List<Product> temp = new ArrayList();
        for(Product d: dbProductList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getProductName().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.setList( temp );
    }
}