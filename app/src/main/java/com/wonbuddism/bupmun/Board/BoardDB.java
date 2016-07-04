package com.wonbuddism.bupmun.Board;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wonbuddism.bupmun.Database.ScriptureDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016-01-18.
 */
public class BoardDB {

    private static final String TAG = "BOARD_DB";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Contacts Table Columns names
    private static String NO  = "no";
    private static String CATEGORY = "category";
    private static String ID = "id";
    private static String NAME = "name";
    private static String TITLE = "title";
    private static String CONTENT = "content";
    private static String DATE = "date";
    private static String HIT = "hit";
    private static String COMMENT_COUNT = "comment_count";

    /**
     *
     * Database creation sql statement
     */

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbManager";

    // Contacts table name
    private static final String TABLE_NAME = "board";
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + NO + " INTEGER PRIMARY KEY autoincrement,"
                    + CATEGORY + " TEXT,"
                    + ID + " TEXT,"
                    + NAME + " TEXT,"
                    + TITLE + " TEXT,"
                    + CONTENT + " TEXT,"
                    + DATE + " TEXT,"
                    + HIT + " INTEGER,"
                    + COMMENT_COUNT + " INTEGER" + ")";
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

    public BoardDB(Context ctx) {
        this.mCtx = ctx;
    }

    public BoardDB open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(BoardItem dto) {
        ContentValues values = new ContentValues();
        //values.put(NO, dto.getNo());
        values.put(CATEGORY, dto.getCategory());
        values.put(ID, dto.getId());
        values.put(NAME, dto.getName());
        values.put(TITLE, dto.getTitle());
        values.put(CONTENT, dto.getContent());
        values.put(DATE, dto.getDate());
        values.put(HIT, dto.getHit());
        values.put(COMMENT_COUNT, dto.getComent_count());
        return mDb.insert(TABLE_NAME, null, values);
    }


    //사용안함


    // no 에 해당하는 Contact 객체 가져오기
    public BoardItem getItem(int no){
        BoardItem dto=null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+NO+ " = '"+no+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dto = new BoardItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getInt(7),cursor.getInt(8));

            } while (cursor.moveToNext());
        }

        return dto;
    }


    // 모든 Contact 정보 가져오기
    public ArrayList<BoardItem> getAll() {
        ArrayList<BoardItem> item = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BoardItem dto = new BoardItem();
                dto.setNo(cursor.getInt(0));
                dto.setCategory(cursor.getString(1));
                dto.setId(cursor.getString(2));
                dto.setName(cursor.getString(3));
                dto.setTitle(cursor.getString(4));
                dto.setContent(cursor.getString(5));
                dto.setDate(cursor.getString(6));
                dto.setHit(cursor.getInt(7));
                dto.setComent_count(cursor.getInt(8));

                // Adding contact to list
                item.add(dto);
            } while (cursor.moveToNext());
        }

        // return contact list
        return item;
    }



    // Contact 정보 삭제하기
    public void delete(BoardItem dto) {
        mDb.delete(TABLE_NAME, NO + " = ?",
                new String[]{dto.getNo()+""});

    }
    // 정보 업데이트
    public int updateHIT(int no, int hit) {
        ContentValues values = new ContentValues();
        values.put(HIT, hit);

        // updating row
        return mDb.update(TABLE_NAME, values, NO + " = ?",
                new String[]{no+""});
    }

    // 정보 업데이트
    public int updateComment(int no, int comment_count) {
        ContentValues values = new ContentValues();
        values.put(COMMENT_COUNT, comment_count);

        // updating row
        return mDb.update(TABLE_NAME, values, NO + " = ?",
                new String[]{no+""});
    }

    // 정보 숫자
    public int getCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }
}
