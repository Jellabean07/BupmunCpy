package com.wonbuddism.bupmun.Writing.TypingProcess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TypingLogDbAdapter {

    private static final String TAG = "TypingLogDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Contacts Table Columns names
    private static String TYPING_CNT = "typing_cnt" ; //	사경횟수	SMALLINT
    private static String TYPING_ID = "typing_id"; //	사경아이디	CHARACTER
    private static String BUPMUNINDEX = "bupmunindex"; //	법문인덱스키	VARCHAR
    private static String PARAGRAPH_NO = "paragraph_no"; //	문단번호	SMALLINT
    private static String CHNS_YN = "chns_yn"; //	한자포함여부	CHARACTER
    private static String TASU = "tasu"; //	타수	SMALLINT
    private static String REGIST_DATE = "regist_date" ; //	사경일	CHARACTER
    private static String REGIST_TIME = "regist_time"; //	사경시간	CHARACT



    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BUPMUN";

    // Contacts table name
    private static final String TABLE_NAME = "TYPING_HIST";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + TYPING_CNT + " INTEGER,"
                    + TYPING_ID + " TEXT,"
                    + BUPMUNINDEX + " TEXT,"
                    + PARAGRAPH_NO + " INTEGER,"
                    + CHNS_YN + " TEXT,"
                    + TASU + " INTEGER,"
                    + REGIST_DATE + " TEXT,"
                    + REGIST_TIME + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
        ////////////////

    }

    public  void beginTransaction(){
        mDb.beginTransaction();
    }

    public void setTransaction(){
        mDb.setTransactionSuccessful();
    }

    public void endTransaction(){
        mDb.endTransaction();
    }

    public TypingLogDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public TypingLogDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(TypingLog dto) {
        ContentValues values = new ContentValues();
        values.put(TYPING_CNT, dto.getTYPING_CNT());
        values.put(TYPING_ID, dto.getTYPING_ID());
        values.put(BUPMUNINDEX, dto.getBUPMUNINDEX());
        values.put(PARAGRAPH_NO, dto.getPARAGRAPH_NO());
        values.put(CHNS_YN, dto.getCHNS_YN());
        values.put(TASU, dto.getTASU());
        values.put(REGIST_DATE, dto.getREGIST_DATE());
        values.put(REGIST_TIME, dto.getREGIST_TIME());


        return mDb.insert(TABLE_NAME, null, values);
    }

    public List<TypingLog> getItem(String bupmunindex) {
        List<TypingLog> bupmunList = new ArrayList<>();
        TypingLog dto = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "+BUPMUNINDEX+ " = '"+bupmunindex+"'";

        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new TypingLog(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3),cursor.getString(4),
                        cursor.getInt(5), cursor.getString(6),cursor.getString(7));

                bupmunList.add(dto);

            } while (cursor.moveToNext());
        }
        return bupmunList;
    }

    public int getPargarphCount(String bupmunindex) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "+BUPMUNINDEX+ " = '"+bupmunindex+"'";
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }


    // 새로작성하기
    public void delete(TypingLog dto) {
        mDb.delete(TABLE_NAME, BUPMUNINDEX + " = ?",
                new String[]{dto.getBUPMUNINDEX()});

    }

    //임시저장 - 불러오기
    public void update(TypingLog dto){
        ContentValues updateContent = new ContentValues();
        updateContent.put(TASU ,dto.getTASU());
        mDb.update(TABLE_NAME , updateContent, BUPMUNINDEX+" = ? and "+PARAGRAPH_NO+" = ?" , new String[] {dto.getBUPMUNINDEX() , dto.getPARAGRAPH_NO()+""});

    }

    // 객체개수 정보 숫자
    public int getCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }
}
