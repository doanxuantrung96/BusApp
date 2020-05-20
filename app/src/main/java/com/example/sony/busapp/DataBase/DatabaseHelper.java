package com.example.sony.busapp.DataBase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sony.busapp.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static String DB_NAME = "bus.db";
    private static SQLiteDatabase db = null;
    //table
    public static final String table_Bus = "buss";
    public static final String col_id = "bus_id";
    public static final String col_name = "bus_name";
    public static final String col_start = "bus_start";
    public static final String col_end = "bus_end";
    Context context;


    private static final String Create_table_Bus = "CREATE TABLE " + table_Bus + "(" + col_id + " INTEGER PRIMARY KEY, "
            + col_name + " nvarchar(1000), "
            + col_start + " nvarchar(1000), "
            + col_end + " nvarchar(1000))";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_table_Bus);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Bus);
        onCreate(db);
    }

    public boolean addBus(Bus bus) {
        //Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col_id, bus.getBus_id());
        values.put(col_name, bus.getBus_name());
        values.put(col_start, bus.getBus_start());
        values.put(col_end, bus.getBus_end());
        // Trèn một dòng dữ liệu vào bảng.


        long rowId = db.insert(table_Bus, null, values);
        db.close();
        if (rowId != -1)
            return true;
        return false;
        // Đóng kết nối database.

    }


    public List<Bus> getAllBus() {
        //Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Bus> busList = new ArrayList<Bus>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + table_Bus;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Bus bus = new Bus();
                bus.setBus_id(cursor.getInt(0));
                bus.setBus_name(cursor.getString(1));
                bus.setBus_start(cursor.getString(2));
                bus.setBus_end(cursor.getString(3));

                // Thêm vào danh sách.
                busList.add(bus);
            } while (cursor.moveToNext());
        }

        // return note list
        return busList;
    }

    public int getJobsCount() {
        //Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + table_Bus;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

}
