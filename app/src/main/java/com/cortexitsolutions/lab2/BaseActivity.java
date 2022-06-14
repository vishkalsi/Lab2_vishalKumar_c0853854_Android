package com.cortexitsolutions.lab2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cortexitsolutions.lab2.model.Products;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    public List<Products> mProductList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        View decorView =getWindow().getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        addDefaultProducts();
    }

    private void addDefaultProducts() {
        mProductList.add( new Products( "POCO C31","Royal Blue, 64 GB  (4 GB RAM)",425 ) );
        mProductList.add( new Products( "SAMSUNG Galaxy F22","(Denim Blue, 64 GB)  (4 GB RAM)",525 ) );
        mProductList.add( new Products( "REDMI Note 10S","(Frost White, 64 GB)  (6 GB RAM)",385 ) );
        mProductList.add( new Products( "Infinix HOT 12 Play","(Horizon Blue, 64 GB)  (4 GB RAM)",925 ) );
        mProductList.add( new Products( "realme 9 5G","(Meteor Black, 64 GB)  (4 GB RAM)",605 ) );
        mProductList.add( new Products( "POCO M3 Pro 5G","(Yellow, 128 GB)  (6 GB RAM)",775 ) );
        mProductList.add( new Products( "IAIR D40"," (Dark Red)",125 ) );
        mProductList.add( new Products( "REDMI 9i Sport","(Metallic Blue, 64 GB)  (4 GB RAM)",225 ) );
        mProductList.add( new Products( "vivo T1 44W","(Ice Dawn, 128 GB)  (6 GB RAM)",345 ) );
        mProductList.add( new Products( "APPLE iPhone 11","(White, 128 GB)",790 ) );




    }
}
