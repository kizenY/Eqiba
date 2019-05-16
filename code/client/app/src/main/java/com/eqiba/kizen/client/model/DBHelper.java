package com.eqiba.kizen.client.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.eqiba.kizen.client.bean.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper instance = null;

    private boolean hasInit = false;
    private static final String DBNAME="eqiba_db";
    private static final int VERSION = 1;
    public static final String TABLE_NAME_USER ="user";
    private final String CREATE_USER_TABLE=
            "CREATE TABLE IF NOT EXISTS " +
                    "  `"+TABLE_NAME_USER+"`  (" +
                    "  `id` int(11)  PRIMARY KEY NOT NULL ," +
                    "  `data` BLOB NOT NULL,"+
                    "  PRIMARY KEY (`id`)" +
                    ") ;";

    public void init(Context context)
    {
        if (instance==null)
            instance=new DBHelper(context);
        hasInit=true;
    }

    private DBHelper(Context context) {
        super(context,DBNAME,null,VERSION);
        getWritableDatabase().execSQL(CREATE_USER_TABLE);
    }

    public static byte[] getDataFromObject(Object o) {

        byte[] data = null;
        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            data = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return data;
    }

    public static List getObjectFromDatabase(String sql,String[] selectionArgs)
    {
        List list=new ArrayList<>();
        Cursor cursor = DBHelper.instance.getReadableDatabase().rawQuery(sql, selectionArgs);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex("data"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    list.add(inputStream.readObject());
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
