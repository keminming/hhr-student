package org.hhr.client.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LanguageContentProvider extends ContentProvider {
    public static final String AUTHORITY = "org.hhr.client.provider.LanguageContentProvider";

    public static final Uri LANGUAGE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LanguageTableColumns.TABLE_NAME);

    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + UserTableColumns.TABLE_NAME);


    public class LanguageDbHelper extends SQLiteOpenHelper {

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "hhr.db";

        private final String SQL_CREATE_LANGUAGE_TABLE =
                "CREATE TABLE " + LanguageTableColumns.TABLE_NAME + " (" +
                        LanguageTableColumns._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                        LanguageTableColumns.COLUMN_NAME_CODE + TEXT_TYPE + COMMA_SEP +
                        LanguageTableColumns.COLUMN_NAME_PATH + TEXT_TYPE + COMMA_SEP +
                        LanguageTableColumns.COLUMN_NAME_INSTALLED + TEXT_TYPE + COMMA_SEP +
                        SearchManager.SUGGEST_COLUMN_TEXT_1  + TEXT_TYPE +
                        " )";

        private final String SQL_CREATE_USER_TABLE =
                "CREATE TABLE " + UserTableColumns.TABLE_NAME + " (" +
                        UserTableColumns._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                        UserTableColumns.USER_NAME + TEXT_TYPE + COMMA_SEP +
                        UserTableColumns.PASS_WORD + TEXT_TYPE +
                        " )";

        private final String SQL_INDEX = "CREATE INDEX 'code' ON '" + LanguageTableColumns.TABLE_NAME + "' ('" + LanguageTableColumns.COLUMN_NAME_CODE + "' ASC);";

        private static final String SQL_DELETE_LANGUAGE_TABLE =
                "DROP TABLE IF EXISTS " + LanguageTableColumns.TABLE_NAME;

        public LanguageDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private void initLanguageTable(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_LANGUAGE_TABLE);
            db.execSQL(SQL_INDEX);
            try {
                InputStream ins = getContext().getResources().openRawResource(
                        getContext().getResources().getIdentifier("raw/ios_639_2_utf_8",
                                "raw", getContext().getPackageName()));
                BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
                String line = null;
                while((line = bf.readLine()) != null)
                {
                    String token = line.substring(0,line.indexOf('|'));
                    String sql = "INSERT INTO language (" + LanguageTableColumns.COLUMN_NAME_CODE + ", " +
                            LanguageTableColumns.COLUMN_NAME_PATH + ", " + LanguageTableColumns.COLUMN_NAME_INSTALLED + ", " +
                            SearchManager.SUGGEST_COLUMN_TEXT_1 + ") VALUES ('" + token + "', 'null', 'null', '" + token + "-language')";

                    db.execSQL(sql);
                }
            }catch(IOException e){
                Log.e(e.getMessage(),e.getMessage());
            }
        }

        private void initUserTable(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_USER_TABLE);
        }

        public void onCreate(SQLiteDatabase db ) {
            initLanguageTable(db);
            initUserTable(db);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private LanguageDbHelper mDbHelper;

    // Holds the database object
    private SQLiteDatabase db;

    public boolean onCreate() {

        mDbHelper = new LanguageDbHelper(getContext());
        db = mDbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = uri.getLastPathSegment();
        long id = db.insert(table,null,values);
        return Uri.parse(uri.toString() + "/" + Long.toString(id));
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String table = uri.getLastPathSegment();

        builder.setTables(table);
        Cursor cursor = builder.query(db,
                projection, selection, selectionArgs, null, null, null);

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        return 0;
    }

    @Override
    public String getType(Uri uri){
        return "";
    }
}