package com.metacoders.dailyearn.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.metacoders.dailyearn.models.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {


    private static final String DB_NAME = "Db.db";
    private static  final int DB_VER = 1 ;


    public Database(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }

    public List<Order> getcarts() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ID","ProductName", "Quantity", "Price"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);

        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final List<Order> result = new ArrayList<>();


        if (c.moveToFirst()) {
            do {
                result.add(new Order(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price"))

                ));
            } while (c.moveToNext());


        }
        return result;

    }

    public  void  addToCart(Order order ){

        SQLiteDatabase db = getReadableDatabase() ;
        String query = String.format("INSERT INTO OrderDetail(ProductName,Quantity,Price) VALUES ('%s','%s','%s');",
        order.getProductName() ,
                order.getQuantity() ,
                order.getPrice());

        db.execSQL(query);


    }
    public  void  cleanCart( ){

        SQLiteDatabase db = getReadableDatabase() ;
        String query = String.format("DELETE FROM OrderDetail");

        db.execSQL(query);


    }

    public  void  updatetoCart(Order order){

        SQLiteDatabase db = getReadableDatabase() ;
        String query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ID = %d",order.getQuantity(),order.getID());
        db.execSQL(query);

    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase() ;
        Cursor res = db.rawQuery("select * from "+"OrderDetail",null);
        return res ;




    }


    public  int getCountCarts (){

        int count = 0 ;
        SQLiteDatabase db = getReadableDatabase() ;
        String query = String.format("SELECT  COUNT (*) FROM OrderDetail");
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    count = cursor.getInt(0);
                }
                while(cursor.moveToNext());
                }


                return count ;
            }


    public void deleteMenu(Order order) {

        SQLiteDatabase db = getReadableDatabase() ;
        String query = String.format("DELETE FROM OrderDetail WHERE ID = %d",order.getID());
        db.execSQL(query);





    }
}



