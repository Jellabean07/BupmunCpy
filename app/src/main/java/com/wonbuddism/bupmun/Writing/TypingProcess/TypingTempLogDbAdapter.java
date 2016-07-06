package com.wonbuddism.bupmun.Writing.TypingProcess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class TypingTempLogDbAdapter {
    private static final String TAG = "TypingTempLogDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Contacts Table Columns names
    private static String TYPING_CNT = "typing_cnt" ; //	사경횟수	SMALLINT
    private static String TYPING_ID = "typing_id"; //	사경아이디	CHARACTER
    private static String BUPMUNINDEX = "bupmunindex"; //	법문인덱스키	VARCHAR
    private static String PARAGRAPH_NO = "paragraph_no"; //	문단번호	SMALLINT
    private static String CHNS_YN = "chns_yn"; //	한자포함여부	CHARACTER
    private static String REGIST_TIME = "regist_time"; //	사경시간	CHARACTER
    private static String RMRK = "rmrk"; //	사경시간	CHARACT


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BUPMUN_TEMP";

    // Contacts table name
    private static final String TABLE_NAME = "TYPING_HIST_IMSI";

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
                    + REGIST_TIME + " TEXT,"
                    + RMRK + " TEXT" + ")";
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

    public TypingTempLogDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public TypingTempLogDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(TypingTempLog tempLog) {
        ContentValues values = new ContentValues();
        values.put(TYPING_CNT, tempLog.getTYPING_CNT());
        values.put(TYPING_ID, tempLog.getTYPING_ID());
        values.put(BUPMUNINDEX, tempLog.getBUPMUNINDEX());
        values.put(PARAGRAPH_NO, tempLog.getPARAGRAPH_NO());
        values.put(CHNS_YN, tempLog.getCHNS_YN());
        values.put(REGIST_TIME, tempLog.getREGIST_TIME());
        values.put(RMRK, tempLog.getRMRK());


        return mDb.insert(TABLE_NAME, null, values);
    }

    // bupmunindex 에 해당하는 객체 가져오기
    public TypingTempLog getItem(String bupmunindex) {
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{TYPING_CNT,TYPING_ID, BUPMUNINDEX, PARAGRAPH_NO, CHNS_YN, REGIST_TIME, RMRK},
                                                            BUPMUNINDEX + "=?",
                new String[]{bupmunindex}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TypingTempLog tempLog = new TypingTempLog(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getInt(3),cursor.getString(4),
                cursor.getString(5), cursor.getString(6));
        // return contact
        return tempLog;
    }

    public ArrayList<TypingTempLog> getTempLogs(){
        ArrayList<TypingTempLog> tempLogs = new ArrayList<>();
        TypingTempLog tempLog= null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tempLog = new TypingTempLog(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3),cursor.getString(4),
                        cursor.getString(5), cursor.getString(6));

                tempLogs.add(tempLog);

            } while (cursor.moveToNext());
        }

        return tempLogs;
    }


    public ArrayList<TypingTempLog> getTempLogs(String bupmunindex){
        ArrayList<TypingTempLog> tempLogs = new ArrayList<>();
        TypingTempLog tempLog= null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "+BUPMUNINDEX+" = '"+bupmunindex+"'";

        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tempLog = new TypingTempLog(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3),cursor.getString(4),
                        cursor.getString(5), cursor.getString(6));

                tempLogs.add(tempLog);

            } while (cursor.moveToNext());
        }

        return tempLogs;
    }

    // 새로작성하기
    public void delete(String bupmunindex, String paragraph_no) {
        mDb.delete(TABLE_NAME, BUPMUNINDEX + " = ? AND " + PARAGRAPH_NO + " = ?",
                new String[]{bupmunindex, paragraph_no});

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
