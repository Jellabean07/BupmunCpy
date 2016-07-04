package com.wonbuddism.bupmun.Database.Typing;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {

    private static final String TAG = "BupmunDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BUPMUN";

    // table name
    private static final String TABLE_NAME_1 = "BUPMUN_TYPING_INDEX";
    private static final String TABLE_NAME_2 = "TYPING_HIST";
    private static final String TABLE_NAME_3 = "TYPING_HIST_LOCAL";


    // BUPMUN_TYPING_INDEX Table Columns names
    private static String NO = "no";
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
    private static String CONTENTS_KOR = "contents_kor";

    //TYPING_HIST and TYPING_HIST_LOCAL Table Columns names
    private static String TYPING_CNT = "typing_cnt" ; //	사경횟수	SMALLINT
    private static String TYPING_ID = "typing_id"; //	사경아이디	CHARACTER
    //private static String BUPMUNINDEX = "bupmunindex"; //	법문인덱스키	VARCHAR  -> 중복
    private static String PARAGRAPH_NO = "paragraph_no"; //	문단번호	SMALLINT
    private static String CHNS_YN = "chns_yn"; //	한자포함여부	CHARACTER
    private static String TASU = "tasu"; //	타수	SMALLINT
    private static String REGIST_DATE = "regist_date" ; //	사경일	CHARACTER
    private static String REGIST_TIME = "regist_time"; //	사경시간	CHARACT


    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE_1 = "CREATE TABLE " + TABLE_NAME_1 + "("
                    + NO + " INTEGER PRIMARY KEY autoincrement,"
                    + BUPMUNINDEX + " TEXT,"
                    + TITLE1 + " TEXT," + TITLE2 + " TEXT,"
                    + TITLE3 + " TEXT," + TITLE4 + " TEXT,"
                    + BUPMUNSORT + " TEXT," + CONTENTS + " TEXT,"
                    + SHORTTITLE + " TEXT,"  + PARAGRAPH_CNT + " INTEGER,"
                    + CHINESE_HELP + " TEXT," + CONTENTS_KOR + " TEXT" + ")";

            String CREATE_CONTACTS_TABLE_2 = "CREATE TABLE " + TABLE_NAME_2 + "("
                    + TYPING_CNT + " INTEGER,"
                    + TYPING_ID + " TEXT,"
                    + BUPMUNINDEX + " TEXT,"
                    + PARAGRAPH_NO + " INTEGER,"
                    + CHNS_YN + " TEXT,"
                    + TASU + " INTEGER,"
                    + REGIST_DATE + " TEXT,"
                    + REGIST_TIME + " TEXT" + ")";

            String CREATE_CONTACTS_TABLE_3 = "CREATE TABLE " + TABLE_NAME_3 + "("
                    + TYPING_CNT + " INTEGER,"
                    + TYPING_ID + " TEXT,"
                    + BUPMUNINDEX + " TEXT,"
                    + PARAGRAPH_NO + " INTEGER,"
                    + CHNS_YN + " TEXT,"
                    + TASU + " INTEGER,"
                    + REGIST_DATE + " TEXT,"
                    + REGIST_TIME + " TEXT" + ")";


            db.execSQL(CREATE_CONTACTS_TABLE_1);
            db.execSQL(CREATE_CONTACTS_TABLE_2);
            db.execSQL(CREATE_CONTACTS_TABLE_3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
            onCreate(db);
        }
        ////////////////

    }
    public void deleteAll_Typing_hist(){
        mDb.delete(TABLE_NAME_2, null, null);

    }

    public void deleteAll_Typing_hist_local(){
        mDb.delete(TABLE_NAME_3, null, null);

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

    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public void bulk_insert_typing_hist(ArrayList<TYPING_HIST> typing_hists){

        mDb.beginTransaction();
        try{
            for(TYPING_HIST dto : typing_hists){

                ContentValues values = new ContentValues();
                values.put(TYPING_CNT, dto.getTYPING_CNT());
                values.put(TYPING_ID, dto.getTYPING_ID());
                values.put(BUPMUNINDEX, dto.getBUPMUNINDEX());
                values.put(PARAGRAPH_NO, dto.getPARAGRAPH_NO());
                values.put(CHNS_YN, dto.getCHNS_YN());
                values.put(TASU, dto.getTASU());
                values.put(REGIST_DATE, dto.getREGIST_DATE());
                values.put(REGIST_TIME, dto.getREGIST_TIME());

                Log.e("대용량인서트",dto.toString());
                mDb.insert(TABLE_NAME_2, null, values);
            }
            mDb.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            mDb.endTransaction();
        }

        return;
    }

    //BUPMUN_TYPING_INDEX
    public long createNote_bupmun_typing_index(BUPMUN_TYPING_INDEX dto) {
        ContentValues values = new ContentValues();
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
        values.put(CONTENTS_KOR, dto.getCONTENTS_KOR()); // 인덱스

        return mDb.insert(TABLE_NAME_1, null, values);
    }

    //TYPING_HIST
    public long create_typing_hist(TYPING_HIST dto) {
        ContentValues values = new ContentValues();
        values.put(TYPING_CNT, dto.getTYPING_CNT());
        values.put(TYPING_ID, dto.getTYPING_ID());
        values.put(BUPMUNINDEX, dto.getBUPMUNINDEX());
        values.put(PARAGRAPH_NO, dto.getPARAGRAPH_NO());
        values.put(CHNS_YN, dto.getCHNS_YN());
        values.put(TASU, dto.getTASU());
        values.put(REGIST_DATE, dto.getREGIST_DATE());
        values.put(REGIST_TIME, dto.getREGIST_TIME());


        return mDb.insert(TABLE_NAME_2, null, values);
    }

    //TYPING_HIST_LOCAL
    public long create_typing_hist_local(TYPING_HIST dto) {
        ContentValues values = new ContentValues();
        values.put(TYPING_CNT, dto.getTYPING_CNT());
        values.put(TYPING_ID, dto.getTYPING_ID());
        values.put(BUPMUNINDEX, dto.getBUPMUNINDEX());
        values.put(PARAGRAPH_NO, dto.getPARAGRAPH_NO());
        values.put(CHNS_YN, dto.getCHNS_YN());
        values.put(TASU, dto.getTASU());
        values.put(REGIST_DATE, dto.getREGIST_DATE());
        values.put(REGIST_TIME, dto.getREGIST_TIME());

        return mDb.insert(TABLE_NAME_3, null, values);
    }


    /*
    법문인덱스
   함수내용*/

    // bupmunindex 에 해당하는 객체 가져오기
    public BUPMUN_TYPING_INDEX getBupmunItem(String bupmunindex) {
        Cursor cursor = mDb.query(TABLE_NAME_1, new String[]{NO,BUPMUNINDEX, TITLE1, TITLE2, TITLE3, TITLE4,
                        BUPMUNSORT, CONTENTS, SHORTTITLE, PARAGRAPH_CNT, CHINESE_HELP, CONTENTS_KOR}, BUPMUNINDEX + "=?",
                new String[]{bupmunindex}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BUPMUN_TYPING_INDEX dto = new BUPMUN_TYPING_INDEX(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),cursor.getString(5),
                cursor.getString(6), cursor.getString(7),cursor.getString(8),
                cursor.getInt(9), cursor.getString(10),cursor.getString(11));
        // return contact
        return dto;
    }


    // no 에 해당하는 객체가져오기
    public BUPMUN_TYPING_INDEX getBupmunItem(int no){
        BUPMUN_TYPING_INDEX dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME_1 + " WHERE "+NO+ " = '"+no+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new BUPMUN_TYPING_INDEX(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5),
                        cursor.getString(6), cursor.getString(7),cursor.getString(8),
                        cursor.getInt(9), cursor.getString(10),cursor.getString(11));

            } while (cursor.moveToNext());
        }

        return dto;
    }


    // 모든 객체 가져오기
    public List<BUPMUN_TYPING_INDEX> getAll() {
        List<BUPMUN_TYPING_INDEX> bupmunList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_1;

        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BUPMUN_TYPING_INDEX dto = new BUPMUN_TYPING_INDEX();
                dto.setNO(cursor.getInt(0));
                dto.setBUPMUNINDEX(cursor.getString(1));
                dto.setTITLE1(cursor.getString(2));
                dto.setTITLE2(cursor.getString(3));
                dto.setTITLE3(cursor.getString(4));
                dto.setTITLE4(cursor.getString(5));
                dto.setBUPMUNSORT(cursor.getString(6));
                dto.setCONTENTS(cursor.getString(7));
                dto.setPARAGRAPH_CNT(cursor.getInt(8));
                dto.setSHORTTITLE(cursor.getString(9));
                dto.setCHINESE_HELP(cursor.getString(10));
                dto.setCONTENTS_KOR(cursor.getString(11));
                bupmunList.add(dto);

            } while (cursor.moveToNext());
        }
        return bupmunList;
    }

    // 모든 객체 가져오기
    public ArrayList<TYPING_HIST> getAllLocal() {
        ArrayList<TYPING_HIST> typing_hist_locals = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_3;

        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TYPING_HIST dto = new TYPING_HIST();
                dto.setTYPING_CNT(cursor.getInt(0));
                dto.setTYPING_ID(cursor.getString(1));
                dto.setBUPMUNINDEX(cursor.getString(2));
                dto.setPARAGRAPH_NO(cursor.getInt(3));
                dto.setCHNS_YN(cursor.getString(4));
                dto.setTASU(cursor.getInt(5));
                dto.setREGIST_DATE(cursor.getString(6));
                dto.setREGIST_TIME(cursor.getString(7));
                typing_hist_locals.add(dto);
            } while (cursor.moveToNext());
        }
        return typing_hist_locals;
    }

    // Contact 정보 삭제하기
    public void delete(BUPMUN_TYPING_INDEX dto) {
        mDb.delete(TABLE_NAME_1, BUPMUNINDEX + " = ?",
                new String[]{dto.getBUPMUNINDEX()});

    }


    public ArrayList<String> getAllTitle1(){
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT "+TITLE1+" FROM " + TABLE_NAME_1;
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
        String selectQuery = "SELECT DISTINCT "+TITLE2+" FROM " + TABLE_NAME_1 + " WHERE "+TITLE1 + " = '"+title1+"'";
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
        String selectQuery = "SELECT DISTINCT "+TITLE3+" FROM " + TABLE_NAME_1 + " WHERE "+TITLE1+ " = '"+title1+"' AND "
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
        String selectQuery = "SELECT DISTINCT "+TITLE4+" FROM " + TABLE_NAME_1 + " WHERE "+ TITLE1+ " = '"+title1+"' AND "
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

    public BUPMUN_TYPING_INDEX getContent(String title1, String title2, String title3, String title4){
        BUPMUN_TYPING_INDEX dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME_1 + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"' AND "
                +TITLE4 + " = '"+title4+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new BUPMUN_TYPING_INDEX(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5),
                        cursor.getString(6), cursor.getString(7),cursor.getString(8),
                        cursor.getInt(9), cursor.getString(10),cursor.getString(11));

            } while (cursor.moveToNext());
        }

        return dto;
    }

    public BUPMUN_TYPING_INDEX getContent(String title1, String title2, String title3){
        BUPMUN_TYPING_INDEX dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME_1 + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"' AND "
                +TITLE3 + " = '"+title3+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new BUPMUN_TYPING_INDEX(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5),
                        cursor.getString(6), cursor.getString(7),cursor.getString(8),
                        cursor.getInt(9), cursor.getString(10),cursor.getString(11));

            } while (cursor.moveToNext());
        }

        return dto;
    }

    public BUPMUN_TYPING_INDEX getContent(String title1, String title2){
        BUPMUN_TYPING_INDEX dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME_1 + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                +TITLE2 + " = '"+title2+"'"; //title1, title2 같이 검색해야하는지 테스트
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new BUPMUN_TYPING_INDEX(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5),
                        cursor.getString(6), cursor.getString(7),cursor.getString(8),
                        cursor.getInt(9), cursor.getString(10),cursor.getString(11));

            } while (cursor.moveToNext());
        }

        return dto;
    }


    // 객체개수 정보 숫자
    public int getBupmunCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME_1;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }

    // 객체개수 정보 숫자
    public int getCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME_2;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }

    public int getBupmunParagraphCount(){
        String countQuery = "SELECT "+PARAGRAPH_CNT+" FROM " + TABLE_NAME_1;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        int cnt = 0;
        if (cursor.moveToFirst()) {
            do {
                cnt += Integer.parseInt(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        // cursor.close();
        // return count
        return cnt;
    }


    /* 법문쓰기 */

    public List<TYPING_HIST> getTypingItem(String bupmunindex) {
        List<TYPING_HIST> bupmunList = new ArrayList<>();
        TYPING_HIST dto = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_2 + " WHERE "+BUPMUNINDEX+ " = '"+bupmunindex+"'";

        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new TYPING_HIST(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3),cursor.getString(4),
                        cursor.getInt(5), cursor.getString(6),cursor.getString(7));

                bupmunList.add(dto);

            } while (cursor.moveToNext());
        }
        return bupmunList;
    }



    public int getTypingPargarphCount(String bupmunindex) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_2 + " WHERE "+BUPMUNINDEX+ " = '"+bupmunindex+"'";
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }


    // 새로작성하기
    public void delete(TYPING_HIST dto) {
        mDb.delete(TABLE_NAME_2, BUPMUNINDEX + " = ?",
                new String[]{dto.getBUPMUNINDEX()});

    }

    //임시저장 - 불러오기
    public void update(TYPING_HIST dto){
        ContentValues updateContent = new ContentValues();
        updateContent.put(TASU ,dto.getTASU());
        mDb.update(TABLE_NAME_2 , updateContent, BUPMUNINDEX+" = ? and "+PARAGRAPH_NO+" = ?" , new String[] {dto.getBUPMUNINDEX() , dto.getPARAGRAPH_NO()+""});

    }

    /*임시저장*/



    /*사경진도표 메인 리스트*/



}
