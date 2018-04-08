package com.kompa.pelleg.mymeals;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pelleg on 3/28/2018.
 */

public class helper_sqlite_FOOD extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FOOD.db";
    public static final String TABLE_NAME = "food_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "food";
    public static final String COL_3 = "calorie";

    public helper_sqlite_FOOD( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,FOOD TEXT,CALORIE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean CheckifEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
       String count ="SELECT count(*) FROM "+TABLE_NAME;
       Cursor mcursor = db.rawQuery(count,null);
       mcursor.moveToFirst();
       int icount = mcursor.getInt(0);
       if (icount >0)
           return false;
       else
           return true;

    }


    public boolean insertData(String food,String calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,food);
        contentValues.put(COL_3,calories);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

//    public boolean updateData(String id,String name,String surname,String marks) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,name);
//
//        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public Boolean deleteAllData () {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete= "delete from ";
         db.execSQL(delete + TABLE_NAME);
         return true;
    }
}
