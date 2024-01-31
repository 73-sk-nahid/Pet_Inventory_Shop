package com.example.pet_inventory.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pet_inventory.models.SupplierModel;

import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String  DATABASE_NAME="petInventory_db";
    private static final String TABLE_NAME="suppliers";
    private static final String ID="id";
    private static final String name="name";
    private static final String email="email";
    private static final String address="address";
    private static final String phone="phone";
    private static final String created_at="created_at";


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME+
                "("+
                ID+" INTEGER PRIMARY KEY,"+
                name+" TEXT ,"+
                email+" TEXT ,"+
                address+ " TEXT ,"+
                phone+" TEXT ,"+
                created_at+ " TEXT "+
                ")";
        db.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void AddSupplier(SupplierModel supplierModel){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(name,supplierModel.getName());
        contentValues.put(email,supplierModel.getEmail());
        contentValues.put(phone,supplierModel.getPhone());
        contentValues.put(address,supplierModel.getAddress());
        contentValues.put(created_at,supplierModel.getCreated_at());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public SupplierModel getSupplier(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,name,email,phone,address,created_at},ID+" = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        SupplierModel SupplierModel=new SupplierModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),cursor.getString(5));
        db.close();
        return SupplierModel;
    }

    public List<String> getAllSupplierNames() {
        List<String> supplierNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + name + " FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                supplierNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return supplierNames;
    }

    public List<SupplierModel> getAllSuppliers(){
        List<SupplierModel> supplierModelList=new ArrayList<>();
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                SupplierModel SupplierModel=new SupplierModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(4),cursor.getString(3),cursor.getString(5));
                supplierModelList.add(SupplierModel);
            }
            while (cursor.moveToNext());

        }
        cursor.close(); // Close the cursor to avoid potential memory leaks
        db.close();
        return supplierModelList;
    }


    public int updateSupplier(SupplierModel supplierModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(name,supplierModel.getName());
        contentValues.put(email,supplierModel.getEmail());
        contentValues.put(phone,supplierModel.getPhone());
        contentValues.put(address,supplierModel.getAddress());
        return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(supplierModel.getId())});

    }

    public void deleteSuppliers(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?",new String[]{id});
        db.close();
    }

    public int getTotalCount(){
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }
}
