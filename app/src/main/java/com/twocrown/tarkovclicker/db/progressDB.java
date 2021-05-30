package com.twocrown.tarkovclicker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class progressDB extends SQLiteOpenHelper {
    // Данные базы данных и таблиц
    public static final String DATABASE_NAME = "progress.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "progressDB";

    // Название столбцов
    public static final String COLUMN_ENEMYHP = "EnemyHP";
    public static final String COLUMN_ENEMYMONEYGAIN = "EnemyMG";
    public static final String COLUMN_ENEMYLVL = "EnemyLVL";
    public static final String COLUMN_ENEMYPODLVL = "EnemyPODLVL";
    public static final String COLUMN_PEDPS = "PEDPS";
    public static final String COLUMN_PEDPC = "PEDPC";
    public static final String COLUMN_PEMONEY = "PEMONEY";
    public static final String COLUMN_PRAPORBUY = "ПрапорBUYED";
    public static final String COLUMN_DILERBUY = "ДилерBUYED";
    public static final String COLUMN_LIZHIBUY = "ЛыжникBUYED";
    public static final String COLUMN_MIROTBUY = "МиротворецBUYED";
    public static final String COLUMN_OXOTNBUY = "ОхотникBUYED";
    public static final String COLUMN_PRAPODMG = "PraporDMG";
    public static final String COLUMN_DILERDMG = "ДилерDMG";
    public static final String COLUMN_LIZHIDMG = "ЛыжникDMG";
    public static final String COLUMN_MIROTDMG = "МиротворецDMG";
    public static final String COLUMN_OXOTNDMG = "ОхотникDMG";
    public static final String COLUMN_PRAPORPRICE = "ПрапорPRICE";
    public static final String COLUMN_DILERPRICE = "ДилерPRICE";
    public static final String COLUMN_LIZHIPRICE = "ЛыжникPRICE";
    public static final String COLUMN_MIROTPRICE = "МиротворецPRICE";
    public static final String COLUMN_OXOTNPRICE = "Охотникprice";
    public static final String COLUMN_GLOCK18BUY = "Glock-18BUYED";
    public static final String COLUMN_P228BUY = "P228BUYED";
    public static final String COLUMN_MP9PBUY = "MP9BUYED";
    public static final String COLUMN_MP5KBUY = "MP5KBUYED";
    public static final String COLUMN_AK74BUY = "AK74BUYED";
    public static final String COLUMN_GLOCK18DMG = "Glock-18DMG";
    public static final String COLUMN_P228DMG = "P228DMG";
    public static final String COLUMN_MP9PDMG = "MP9DMG";
    public static final String COLUMN_MP5KDMG = "MP5KDMG";
    public static final String COLUMN_AK74DMG = "AK74DMG";
    public static final String COLUMN_GLOCK18PRICE = "Glock-18PRICE";
    public static final String COLUMN_P228PRICE = "P228PRICE";
    public static final String COLUMN_MP9PPRICE = "MP9PRICE";
    public static final String COLUMN_MP5KPRICE = "MP5KPRICE";
    public static final String COLUMN_AK74PRICE = "AK74price";



    public long insert(Integer enemyhp ,Integer enemyMoneyGain ,Integer EnemyLVL ,
                       Integer PODLVL ,Integer PEDPS, Integer PEDPC, Integer PEMONEY){

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COLUMN_ENEMYHP , enemyhp);
        cv.put(COLUMN_ENEMYMONEYGAIN , enemyMoneyGain);
        cv.put(COLUMN_ENEMYLVL , EnemyLVL);
        cv.put(COLUMN_ENEMYPODLVL ,PODLVL);
        cv.put(COLUMN_PEDPC , PEDPC);
        cv.put(COLUMN_PEDPS , PEDPS);
        cv.put(COLUMN_PEMONEY , PEMONEY);
        db.execSQL("DELETE FROM progressDB");
        return db.insert(TABLE_NAME , null ,cv);
    }
    public progressDB(@Nullable Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase progressDB) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ENEMYHP + " INTEGER, " +
                COLUMN_ENEMYMONEYGAIN + " INTEGER, " +
                COLUMN_ENEMYLVL + " INTEGER, " +
                COLUMN_ENEMYPODLVL + " INTEGER, " +
                COLUMN_PRAPORBUY + " INTEGER, " +
                COLUMN_DILERBUY  + " INTEGER, " +
                COLUMN_LIZHIBUY  + " INTEGER, " +
                COLUMN_MIROTBUY  + " INTEGER, " +
                COLUMN_OXOTNBUY  + " INTEGER, " +
                COLUMN_PRAPODMG  + " INTEGER, " +
                COLUMN_DILERDMG  + " INTEGER, " +
                COLUMN_LIZHIDMG  + " INTEGER, " +
                COLUMN_MIROTDMG  + " INTEGER, " +
                COLUMN_OXOTNDMG  + " INTEGER, " +
                COLUMN_PRAPORPRICE + " INTEGER, " +
                COLUMN_DILERPRICE  + " INTEGER, " +
                COLUMN_LIZHIPRICE  + " INTEGER, " +
                COLUMN_MIROTPRICE  + " INTEGER, " +
                COLUMN_GLOCK18BUY  + " INTEGER, " +
                COLUMN_P228BUY  + " INTEGER, " +
                COLUMN_MP9PBUY + " INTEGER, " +
                COLUMN_MP5KBUY + " INTEGER, " +
                COLUMN_AK74BUY + " INTEGER, " +
                COLUMN_GLOCK18DMG  + " INTEGER, " +
                COLUMN_P228DMG  + " INTEGER, " +
                COLUMN_MP9PDMG  + " INTEGER, " +
                COLUMN_MP5KDMG + " INTEGER, " +
                COLUMN_AK74DMG  + " INTEGER, " +
                COLUMN_GLOCK18PRICE  + " INTEGER, " +
                COLUMN_P228PRICE  + " INTEGER, " +
                COLUMN_MP9PPRICE  + " INTEGER, " +
                COLUMN_MP5KPRICE   + " INTEGER, " +
                COLUMN_AK74PRICE    + " INTEGER, " +
                COLUMN_PEMONEY + " INTEGER);";
        progressDB.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
