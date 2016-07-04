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
 * Created by user on 2016-01-28.
 */
public class BupmunDbAdapter {

        private static final String TAG = "BupmunDbAdapter";
        private DatabaseHelper mDbHelper;
        private SQLiteDatabase mDb;

        // Contacts Table Columns names
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



        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "BupmunManager";

        // Contacts table name
        private static final String TABLE_NAME = "bupmun";

        private final Context mCtx;

        private static class DatabaseHelper extends SQLiteOpenHelper {

            DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                        + NO + " INTEGER PRIMARY KEY autoincrement,"
                        + BUPMUNINDEX + " TEXT,"
                        + TITLE1 + " TEXT," + TITLE2 + " TEXT,"
                        + TITLE3 + " TEXT," + TITLE4 + " TEXT,"
                        + BUPMUNSORT + " TEXT," + CONTENTS + " TEXT,"
                        + SHORTTITLE + " TEXT,"  + PARAGRAPH_CNT + " INTEGER,"
                        + CHINESE_HELP + " TEXT," + CONTENTS_KOR + " TEXT" + ")";
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

        public BupmunDbAdapter(Context ctx) {
            this.mCtx = ctx;
        }

        public BupmunDbAdapter open() throws SQLException {
            mDbHelper = new DatabaseHelper(mCtx);
            mDb = mDbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            mDbHelper.close();
        }

        public long createNote(BupmunItem dto) {
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

            return mDb.insert(TABLE_NAME, null, values);
        }


        // bupmunindex 에 해당하는 객체 가져오기
        public BupmunItem getItem(String bupmunindex) {
            Cursor cursor = mDb.query(TABLE_NAME, new String[]{NO,BUPMUNINDEX, TITLE1, TITLE2, TITLE3, TITLE4,
                            BUPMUNSORT, CONTENTS, SHORTTITLE, PARAGRAPH_CNT, CHINESE_HELP, CONTENTS_KOR}, BUPMUNINDEX + "=?",
                    new String[]{bupmunindex}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            BupmunItem dto = new BupmunItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                                            cursor.getString(3), cursor.getString(4),cursor.getString(5),
                                            cursor.getString(6), cursor.getString(7),cursor.getString(8),
                                            cursor.getInt(9), cursor.getString(10),cursor.getString(11));
            // return contact
            return dto;
        }


        // no 에 해당하는 객체가져오기
        public BupmunItem getItem(int no){
            BupmunItem dto=null;
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+NO+ " = '"+no+"'";
            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    dto = new BupmunItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),cursor.getString(5),
                            cursor.getString(6), cursor.getString(7),cursor.getString(8),
                            cursor.getInt(9), cursor.getString(10),cursor.getString(11));

                } while (cursor.moveToNext());
            }

            return dto;
        }


        // 모든 객체 가져오기
        public List<BupmunItem> getAll() {
            List<BupmunItem> bupmunList = new ArrayList<>();
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    BupmunItem dto = new BupmunItem();
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



        // Contact 정보 삭제하기
        public void delete(BupmunItem dto) {
            mDb.delete(TABLE_NAME, BUPMUNINDEX + " = ?",
                    new String[]{dto.getBUPMUNINDEX()});

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

        public BupmunItem getContent(String title1, String title2, String title3, String title4){
            BupmunItem dto=null;
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                    +TITLE2 + " = '"+title2+"' AND "
                    +TITLE3 + " = '"+title3+"' AND "
                    +TITLE4 + " = '"+title4+"'"; //title1, title2 같이 검색해야하는지 테스트
            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    dto = new BupmunItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),cursor.getString(5),
                            cursor.getString(6), cursor.getString(7),cursor.getString(8),
                            cursor.getInt(9), cursor.getString(10),cursor.getString(11));

                } while (cursor.moveToNext());
            }

            return dto;
        }

        public BupmunItem getContent(String title1, String title2, String title3){
            BupmunItem dto=null;
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                    +TITLE2 + " = '"+title2+"' AND "
                    +TITLE3 + " = '"+title3+"'"; //title1, title2 같이 검색해야하는지 테스트
            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    dto = new BupmunItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),cursor.getString(5),
                            cursor.getString(6), cursor.getString(7),cursor.getString(8),
                            cursor.getInt(9), cursor.getString(10),cursor.getString(11));

                } while (cursor.moveToNext());
            }

            return dto;
        }

        public BupmunItem getContent(String title1, String title2){
            BupmunItem dto=null;
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+TITLE1+ " = '"+title1+"' AND "
                    +TITLE2 + " = '"+title2+"'"; //title1, title2 같이 검색해야하는지 테스트
            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    dto = new BupmunItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),cursor.getString(5),
                            cursor.getString(6), cursor.getString(7),cursor.getString(8),
                            cursor.getInt(9), cursor.getString(10),cursor.getString(11));

                } while (cursor.moveToNext());
            }

            return dto;
        }


        // 객체개수 정보 숫자
        public int getCount() {
            String countQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = mDb.rawQuery(countQuery, null);
            // cursor.close();
            // return count
            return cursor.getCount();
        }

        public int getParagraphCount(){
            String countQuery = "SELECT "+PARAGRAPH_CNT+" FROM " + TABLE_NAME;
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


    }
