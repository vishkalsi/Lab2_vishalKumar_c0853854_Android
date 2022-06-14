package com.cortexitsolutions.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.cortexitsolutions.lab2.database.model.Product;
import com.cortexitsolutions.lab2.model.Products;

public class AddProductActivity extends BaseActivity {
    private EditText productName, productPrice, productDescription;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_product );
        productName = (EditText) findViewById( R.id.product_name );
        productDescription = (EditText) findViewById( R.id.description );
        productPrice = (EditText) findViewById( R.id.product_price );
        Button submit = (Button) findViewById( R.id.submit );
        AppCompatImageView back = (AppCompatImageView) findViewById( R.id.back );
        product = (Product) getIntent().getSerializableExtra( "data" );
        if(product != null){
            setData(product);
        }

        submit.setOnClickListener( view -> checkValidation() );

        back.setOnClickListener( view -> finish() );
    }

    private void setData(Product product) {
        productName.setText(product.getProductName());
        productDescription.setText( product.getProductDescription() );
        productPrice.setText( ""+product.getProductPrice() );

    }

    private void checkValidation() {
        String pdName, pdDescritpion, pdPrice;
        pdName = productName.getText().toString();
        pdDescritpion = productDescription.getText().toString();
        pdPrice = productPrice.getText().toString();

        if(pdName.isEmpty()){
            productName.setError( "Please enter Product Name" );
            return;
        }

        if(pdDescritpion.isEmpty()){
            productName.setError( "Please enter Product Name" );
            return;
        }
        if(pdPrice.isEmpty()){
            productName.setError( "Please enter Product Name" );
            return;
        }


        if(product!=null && product.getProductId()!=0){
            Products products = new Products( product.getProductId(),pdName,pdDescritpion,Integer.parseInt( pdPrice ));
            Intent data = new Intent();
            data.putExtra( "product",products );
            setResult( Activity.RESULT_FIRST_USER, data);
        }else {
            Products products = new Products( pdName,pdDescritpion,Integer.parseInt( pdPrice ));
            Intent data = new Intent();
            data.putExtra( "products",products );
            setResult( Activity.RESULT_OK, data);
        }
        finish();
    }
}