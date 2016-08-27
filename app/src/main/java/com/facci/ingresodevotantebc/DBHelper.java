package com.facci.ingresodevotantebc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrescevallos on 26/8/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NOMBRE = "CNE_DATP.db";
    public static final String TABLA_NOMBRE = "VOTANTES_DATP";

    public static final String Col_1= "ID";
    public static final String Col_2= "NOMBRE";
    public static final String Col_3= "APELLIDO";
    public static final String Col_4= "RECINTOELECTORAL";
    public static final String Col_5= "ANONACIMIENTO";


    public DBHelper(Context context) {
        super(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s(ID INTEGER PRIMARY KEY AUTOINCREMENT,%s Text, %s Text,%s Text, %s INTEGER)",TABLA_NOMBRE,Col_2,Col_3,Col_4,Col_5));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLA_NOMBRE));
        onCreate(db);

    }

    public boolean insertar (String nombre, String apellido, String recintoelectoral, Integer anonaciemiento ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,nombre);
        contentValues.put(Col_3,apellido);
        contentValues.put(Col_4,recintoelectoral);
        contentValues.put(Col_5,anonaciemiento);
        long resultado = db.insert(TABLA_NOMBRE,null,contentValues);

        if(resultado == 1)
            return false;
        else
            return true;
    }

    public Cursor selectBuscarTodo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(String.format("select * from %s",TABLA_NOMBRE),null);
        return res;
    }

    public boolean modificarRegistro(String id, String nombre, String apellido, String recintoelectoral, int anonacimiento ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,id);
        contentValues.put(Col_2,nombre);
        contentValues.put(Col_3,apellido);
        contentValues.put(Col_4,recintoelectoral);
        contentValues.put(Col_5,anonacimiento);
        db.update(TABLA_NOMBRE,contentValues,"id = ?",new String[]{id});
        return true;
    }

    public Integer eliminarRegistro(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLA_NOMBRE,"id = ?",new String[]{id});
    }
}
