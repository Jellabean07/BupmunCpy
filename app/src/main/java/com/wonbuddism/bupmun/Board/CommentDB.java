package com.wonbuddism.bupmun.Board;

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
 * Created by user on 2016-01-19.
 */
public class CommentDB {
    private static final String TAG = "COMMENT_DB";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;


    // Contacts Table Columns names
    private static String NO  = "no";
    private static String ID = "id";
    private static String NAME = "name";
    private static String CONTENT = "content";
    private static String DATE = "date";
    private static String INDEX = "idx";


    /**
     *
     * Database creation sql statement
     */

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "commentManager";

    // Contacts table name
    private static final String TABLE_NAME = "comment";
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + NO + " INTEGER,"
                    + ID + " TEXT,"
                    + NAME + " TEXT,"
                    + CONTENT + " TEXT,"
                    + DATE + " TEXT,"
                    + INDEX + " INTEGER PRIMARY KEY autoincrement" + ")";

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

    public CommentDB(Context ctx) {
        this.mCtx = ctx;
    }

    public CommentDB open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(Comment dto) {
        ContentValues values = new ContentValues();
        values.put(NO, dto.getNo());
        values.put(ID, dto.getId());
        values.put(NAME, dto.getName());
        values.put(CONTENT, dto.getContent());
        values.put(DATE, dto.getDate());
        return mDb.insert(TABLE_NAME, null, values);
    }



    // 모든 댓글 정보 가져오기
    public List<Comment> getAll(int no) {
        List<Comment> item = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+NO+ " = '"+no+"'";
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Comment dto = new Comment();
                dto.setNo(cursor.getInt(0));
                dto.setId(cursor.getString(1));
                dto.setName(cursor.getString(2));
                dto.setContent(cursor.getString(3));
                dto.setDate(cursor.getString(4));
                dto.setIndex(cursor.getInt(5));

                // Adding contact to list
                item.add(dto);
            } while (cursor.moveToNext());
        }

        // return contact list
        return item;
    }



    // 해당글의 모든 댓글 삭제하기 -> 글삭제용
    public void deleteALL(int no) {
        mDb.delete(TABLE_NAME, NO + " = ?",
                new String[]{no+""});

    }

    // 개인삭제용
    public void deleteOne(Comment dto) {
        mDb.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+ NO +" = '"+dto.getNo()+"' AND "+INDEX+" = '"+dto.getIndex()+"'");
    }


    // 정보 숫자
    public int getCount(int no) {
        String countQuery = "SELECT * FROM " + TABLE_NAME +" WHERE "+ NO +" "+no;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        // cursor.close();
        // return count
        return cursor.getCount();
    }
}
