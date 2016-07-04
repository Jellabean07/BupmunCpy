package com.wonbuddism.bupmun.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2015-12-03.
 */
public class DbAdapter2 {
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "DbAdapter2";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;


    // Contacts Table Columns names
    private static String BUPMUNINDEX  = "bupmunindex";
    private static String TITLE1 = "title1";
    private static String TITLE2 = "title2";
    private static String TITLE3 = "title3";
    private static String TITLE4 = "title4";
    private static String BUPMUNSORT  = "bupmunsort";
    private static String CONTENTS = "contents";
    private static String SHORTTITLE = "shorttitle";
    private static String PARAGRAPH_CNT = "paragraph_cnt";
    private static String CHINESE_HELP = "chinese_help";
    private static String IDX = "idx";
    private static String TYPING_CONTENT = "typing_content";
    private static String BUPMUN_CONFIRM = "bupmunconfirm";

    /**
     *
     * Database creation sql statement
     */

    private static final String DATABASE_CREATE = "create table notes (_id integer primary key autoincrement, "
            + "title text not null, body text not null);";

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "scriptureManager";

    // Contacts table name
    private static final String TABLE_NAME = "scripture";
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + IDX + " TEXT,"
                    + BUPMUNINDEX + " TEXT PRIMARY KEY,"
                    + TITLE1 + " TEXT," + TITLE2 + " TEXT,"
                    + TITLE3 + " TEXT," + TITLE4 + " TEXT,"
                    + BUPMUNSORT + " TEXT," + CONTENTS + " TEXT,"
                    + SHORTTITLE + " TEXT,"  + PARAGRAPH_CNT + " TEXT,"
                    + CHINESE_HELP + " TEXT," + TYPING_CONTENT + " TEXT,"
                    + BUPMUN_CONFIRM + " TEXT" + ")";
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

    public DbAdapter2(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter2 open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(ScriptureDTO dto) {
        ContentValues values = new ContentValues();
        values.put(IDX, dto.getIDX()); // 인덱스
        values.put(BUPMUNINDEX, dto.getBUPMUNINDEX()); // 인덱스
        values.put(TITLE1, dto.getTITLE1()); // 인덱스
        values.put(TITLE2, dto.getTITLE2()); // 인덱스
        values.put(TITLE3, dto.getTITLE3()); // 인덱스
        values.put(TITLE4, dto.getTITLE4()); // 인덱스
        values.put(BUPMUNSORT, dto.getBUPMUNSORT()); // 정렬번호
        values.put(CONTENTS, dto.getCONTENTS()); // 내용
        values.put(SHORTTITLE, dto.getSHORTTITLE()); // 타이틀
        values.put(PARAGRAPH_CNT, dto.getPARAGRAPH_CNT()); // 내용문단 개수
        values.put(CHINESE_HELP, dto.getCHINESE_HELP()); // 인덱스
        values.put(TYPING_CONTENT, dto.getTYPING_CONTENT()); // 인덱스
        values.put(BUPMUN_CONFIRM, dto.getBUPMUN_CONFIRM()); // 인덱스





        return mDb.insert(TABLE_NAME, null, values);
    }


    //사용안함


    // id 에 해당하는 Contact 객체 가져오기
    public ScriptureDTO getItem(String id) {
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{IDX,BUPMUNINDEX, TITLE1, TITLE2, TITLE3, TITLE4,
                        BUPMUNSORT, CONTENTS, SHORTTITLE, PARAGRAPH_CNT, CHINESE_HELP, TYPING_CONTENT, BUPMUN_CONFIRM}, BUPMUNINDEX + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ScriptureDTO dto = new ScriptureDTO(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6),
                cursor.getString(7),cursor.getString(8),cursor.getString(9),
                cursor.getString(10),cursor.getString(11),cursor.getString(12));
        // return contact
        return dto;
    }

    public ScriptureDTO getItem(String index, boolean temp){
        ScriptureDTO dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+IDX+ " = '"+index+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new ScriptureDTO(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12));

            } while (cursor.moveToNext());
        }

        return dto;
    }


    // 모든 Contact 정보 가져오기
    public List<ScriptureDTO> getAll() {
        List<ScriptureDTO> scriptureList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ScriptureDTO dto = new ScriptureDTO();
                dto.setIDX(cursor.getString(0));
                dto.setBUPMUNINDEX(cursor.getString(1));
                dto.setTITLE1(cursor.getString(2));
                dto.setTITLE2(cursor.getString(3));
                dto.setTITLE3(cursor.getString(4));
                dto.setTITLE4(cursor.getString(5));
                dto.setBUPMUNSORT(cursor.getString(6));
                dto.setCONTENTS(cursor.getString(7));
                dto.setPARAGRAPH_CNT(cursor.getString(8));
                dto.setSHORTTITLE(cursor.getString(9));
                dto.setCHINESE_HELP(cursor.getString(10));
                dto.setTYPING_CONTENT(cursor.getString(11));
                dto.setBUPMUN_CONFIRM(cursor.getString(12));


                // Adding contact to list
                scriptureList.add(dto);
            } while (cursor.moveToNext());
        }

        // return contact list
        return scriptureList;
    }



    // Contact 정보 삭제하기
    public void delete(ScriptureDTO dto) {
        mDb.delete(TABLE_NAME, BUPMUNINDEX + " = ?",
                new String[]{dto.getBUPMUNINDEX()});

    }

    //Contact 정보 업데이트
    public int updateScripture(String bupmunindex, String typing, String confirm) {
        ContentValues values = new ContentValues();
        values.put(TYPING_CONTENT, typing); // 내용
        values.put(BUPMUN_CONFIRM, confirm); // 체크


        // updating row
        return mDb.update(TABLE_NAME, values, BUPMUNINDEX + " = ?",
                new String[]{bupmunindex});
    }


    public ArrayList<String> getAllTitle1(){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT "+TITLE1+" FROM " + TABLE_NAME;
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String title1 = cursor.getString(0);
                list.add(title1);
            } while (cursor.moveToNext());
        }

        return list;
    }



    public ArrayList<String> getAllTitle2(String title1){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT "+TITLE2+" FROM " + TABLE_NAME + " WHERE "+TITLE1 + " = '"+title1+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String title2 = cursor.getString(0);
                list.add(title2);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<String> getAllTitle3(String title1, String title2){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT "+TITLE3+" FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String title3 = cursor.getString(0);
                list.add(title3);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<String> getAllTitle4(String title1, String title2, String title3){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT "+TITLE4+" FROM " + TABLE_NAME + " WHERE "+ TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String title4 = cursor.getString(0);
                list.add(title4);
            } while (cursor.moveToNext());
        }

        return list;
    }

  /*  public ArrayList<ScriptureDTO> getContent(String title1, String title2, String title3, String title4){
        ArrayList<ScriptureDTO> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                                                                            +TITLE2 + " = '"+title2+"' AND "
                                                                            +TITLE3 + " = '"+title3+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ScriptureDTO  dto = new ScriptureDTO(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12));
                list.add(dto);
            } while (cursor.moveToNext());
        }

        return list;
    }*/

    public ScriptureDTO getContent(String title1, String title2, String title3, String title4){
        ScriptureDTO dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"' AND "
                +TITLE4 + " = '"+title4+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new ScriptureDTO(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12));

            } while (cursor.moveToNext());
        }

        return dto;
    }

    public ScriptureDTO getContent(String title1, String title2, String title3){
        ScriptureDTO dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new ScriptureDTO(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12));

            } while (cursor.moveToNext());
        }

        return dto;
    }

    public ScriptureDTO getContent(String title1, String title2){
        ScriptureDTO dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new ScriptureDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12));

            } while (cursor.moveToNext());
        }

        return dto;
    }

    /*
    법문사경 작성했는지 안했는지 체크

     */
    public ArrayList<String> getComplete(String title1){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT "+BUPMUN_CONFIRM +" FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String confirm = cursor.getString(0);
                list.add(confirm);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<String> getComplete(String title1, String title2){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT "+BUPMUN_CONFIRM +" FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String confirm = cursor.getString(0);
                list.add(confirm);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<String> getComplete(String title1, String title2, String title3){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT "+BUPMUN_CONFIRM +" FROM " + TABLE_NAME +  " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String confirm = cursor.getString(0);
                list.add(confirm);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<String> getComplete(String title1, String title2, String title3, String title4){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT "+BUPMUN_CONFIRM +" FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"' AND "
                +TITLE4 + " = '"+title4+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String confirm = cursor.getString(0);
                list.add(confirm);

            } while (cursor.moveToNext());
        }

        return list;
    }

    //쓴것만 개수가져오기
    public int getWritingCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME +" WHERE "+BUPMUN_CONFIRM + " = '1'";
        Cursor cursor = mDb.rawQuery(countQuery, null);
        //  cursor.close();
        // return count
        return cursor.getCount();
    }

    // Contact 정보 숫자
    public int getCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }
}

