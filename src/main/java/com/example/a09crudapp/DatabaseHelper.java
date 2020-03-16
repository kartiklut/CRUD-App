package com.example.a09crudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME="crud.db";
    private final static String TABLE_NAME="crud_table";
    private final static String Col_1="ID";
    private final static String Col_2="NAME";
    private final static String Col_3="EMAIL";
    private final static String Col_4="COURSE_COUNT";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT ,EMAIL TEXT,COURSE_COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String email,String course_count)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(Col_2,name);
        contentValues.put(Col_3,email);
        contentValues.put(Col_4,course_count);

       long result= db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean updateData(String id,String name,String email,String course_count){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(Col_1,id);
        contentValues.put(Col_2,name);
        contentValues.put(Col_3,email);
        contentValues.put(Col_4,course_count);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }

    public Cursor getData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE ID='"+id+"'";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return  cursor;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});

    }
}
