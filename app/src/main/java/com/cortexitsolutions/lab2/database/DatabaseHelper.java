package com.cortexitsolutions.lab2.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cortexitsolutions.lab2.database.model.Product;
import com.cortexitsolutions.lab2.model.Products;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "product_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create quiz table
        db.execSQL( Product.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
   public void addListItem(List<Products> listItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i < listItem.size(); i++) {

//            values.put(Product.COLUMN_PRODUCT_ID, listItem.get(i).getProductId());
            values.put(Product.COLUMN_PRODUCT_NAME, listItem.get(i).getProductName());
            values.put(Product.COLUMN_PRODUCT_DESCRIPTION, listItem.get(i).getProductDescription());
            values.put(Product.COLUMN_PRODUCT_PRICE, listItem.get(i).getProductPrice());
            db.insert(Product.TABLE_NAME, null, values);

        }

        db.close(); // Closing database connection
    }

    @SuppressLint("Range")
    public List<Product> getAllProducts() {
        List<Product> quizList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Product.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProductId(cursor.getInt(cursor.getColumnIndex(Product.COLUMN_PRODUCT_ID)));
                product.setProductName( cursor.getString( cursor.getColumnIndex( Product.COLUMN_PRODUCT_NAME ) ) );
                product.setProductDescription( cursor.getString( cursor.getColumnIndex( Product.COLUMN_PRODUCT_DESCRIPTION) ) );
                product.setProductPrice( cursor.getInt( cursor.getColumnIndex( Product.COLUMN_PRODUCT_PRICE ) ) );

                quizList.add(product);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return quiz list
        return quizList;
    }

    public long insertProdct(String prodcuctName, String productDescription, int productPrice) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Product.COLUMN_PRODUCT_NAME, prodcuctName);
        values.put(Product.COLUMN_PRODUCT_DESCRIPTION, productDescription);
        values.put(Product.COLUMN_PRODUCT_PRICE, productPrice);

        // insert row
        long id = db.insert(Product.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public int updateProduct(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put( Product.COLUMN_PRODUCT_DESCRIPTION, product.getProductDescription() );
        values.put( Product.COLUMN_PRODUCT_PRICE, product.getProductPrice() );

        // updating row
        return db.update(Product.TABLE_NAME, values, Product.COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getProductId())});
    }



    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Product.TABLE_NAME, Product.COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getProductId())});
        db.close();
    }

}