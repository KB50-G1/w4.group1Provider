package pidal.alfonso.w4group1provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Sjoerd Thijsse on 12/5/2014.
 */
public class GroupOneProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "pidal.alfonso.w4group1provider.GroupOneProvider";

    private SQLiteDatabase db;

    // Table names.
    private static final String TABLE_COMPANY_NAME = "companies";
    private static final String TABLE_OFFICE_NAME = "offices";
    private static final String TABLE_LANGUAGE_NAME = "languages";
    private static final String TABLE_GALLERY_NAME = "galleries";

    // Column names for company table.
    private static final String KEY_COMPANY_ID = "company_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_WEBSITE = "website";

    // Column names for offices table.
    private static final String KEY_OFFICE_ID = "office_id";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_OFFICE_TYPE = "office_type";

    // Column names for languages table.
    private static final String KEY_LANGUAGE_ID = "language_id";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_DESCRIPTION = "description";

    // Column names for galleries table.
    private static final String KEY_GALLERY_ID = "gallery_id";
    private static final String KEY_URL = "url";

    // URI ID's for CRUD
    private static final int COMPANIES = 1;
    private static final int COMPANY_ID = 2;
    
    private static final int OFFICES = 3;
    private static final int OFFICE_ID = 4;

    private static final int GALLERIES = 5;
    private static final int GALLERY_ID = 6;

    private static final int LANGUAGES = 7;
    private static final int LANGUAGE_ID = 8;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_COMPANY_NAME, COMPANIES);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_COMPANY_NAME + "/#", COMPANY_ID);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_OFFICE_NAME, OFFICES);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_OFFICE_NAME + "/#", OFFICE_ID);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_GALLERY_NAME, GALLERIES);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_GALLERY_NAME + "/#", GALLERY_ID);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_LANGUAGE_NAME, LANGUAGES);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_LANGUAGE_NAME + "/#", LANGUAGE_ID);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "test.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMPANY_NAME + "("
                    + KEY_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                    + KEY_WEBSITE + " TEXT" + ");";

            String CREATE_OFFICE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_OFFICE_NAME + "("
                    + KEY_OFFICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHONE_NUMBER + " INTEGER,"
                    + KEY_ADDRESS + " TEXT," + KEY_OFFICE_TYPE + " TEXT," + KEY_COMPANY_ID + " INTEGER,"
                    + " FOREIGN KEY(" + KEY_COMPANY_ID + ") REFERENCES "
                    + TABLE_COMPANY_NAME + "(" + KEY_COMPANY_ID + "));";

            String CREATE_GALLERY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GALLERY_NAME + "("
                    + KEY_GALLERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_URL + " TEXT,"
                    + KEY_COMPANY_ID + " INTEGER, FOREIGN KEY(" + KEY_COMPANY_ID + ") REFERENCES "
                    + TABLE_COMPANY_NAME + "(" + KEY_COMPANY_ID + "));";

            String CREATE_LANGUAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LANGUAGE_NAME + "("
                    + KEY_LANGUAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DESCRIPTION + " TEXT,"
                    + KEY_LANGUAGE + " TEXT," + KEY_COMPANY_ID + " INTEGER, FOREIGN KEY(" + KEY_COMPANY_ID + ") REFERENCES "
                    + TABLE_COMPANY_NAME + "(" + KEY_COMPANY_ID + "));";

            String CREATE_COMPANY = "INSERT INTO " + TABLE_COMPANY_NAME + "("
                    + KEY_COMPANY_ID + ", " + KEY_NAME + ", " + KEY_WEBSITE + ") "
                    + "VALUES(1, 'Albert Heijn', 'https://www.ah.nl');";


            db.execSQL(CREATE_COMPANY_TABLE);
            db.execSQL(CREATE_OFFICE_TABLE);
            db.execSQL(CREATE_GALLERY_TABLE);
            db.execSQL(CREATE_LANGUAGE_TABLE);
            db.execSQL(CREATE_COMPANY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFICE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERY_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE_NAME + ";");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = this.getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID;

        switch (uriMatcher.match(uri)) {
            case COMPANIES:
                rowID = db.insert(TABLE_COMPANY_NAME, null, values);
                break;
            case OFFICES:
                rowID = db.insert(TABLE_OFFICE_NAME, null, values);
                break;
            case GALLERIES:
                rowID = db.insert(TABLE_GALLERY_NAME, null, values);
                break;
            case LANGUAGES:
                rowID = db.insert(TABLE_LANGUAGE_NAME, null, values);
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            this.getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case COMPANY_ID:
                queryBuilder.setTables(TABLE_COMPANY_NAME);
                queryBuilder.appendWhere(KEY_COMPANY_ID + " = " + uri.getPathSegments().get(1));

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_NAME;
                }

                break;
            case COMPANIES:
                queryBuilder.setTables(TABLE_COMPANY_NAME);

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_NAME;
                }

                break;
            case OFFICE_ID:
                queryBuilder.setTables(TABLE_OFFICE_NAME);
                queryBuilder.appendWhere(KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1));

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_ADDRESS;
                }

                break;
            case OFFICES:
                queryBuilder.setTables(TABLE_OFFICE_NAME);

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_ADDRESS;
                }

                break;
            case GALLERY_ID:
                queryBuilder.setTables(TABLE_GALLERY_NAME);
                queryBuilder.appendWhere(KEY_GALLERY_ID + " = " + uri.getPathSegments().get(1));

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_URL;
                }

                break;
            case GALLERIES:
                queryBuilder.setTables(TABLE_GALLERY_NAME);

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_URL;
                }

                break;
            case LANGUAGE_ID:
                queryBuilder.setTables(TABLE_LANGUAGE_NAME);
                queryBuilder.appendWhere(KEY_LANGUAGE_ID + " = " + uri.getPathSegments().get(1));

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_LANGUAGE;
                }

                break;
            case LANGUAGES:
                queryBuilder.setTables(TABLE_LANGUAGE_NAME);

                if (sortOrder == null || sortOrder.equals("")) {
                    sortOrder = KEY_LANGUAGE;
                }

                break;
        }

        Cursor c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(this.getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case COMPANIES:
                return "vnd.android.cursor.dir/vnd.w4group1provider.companies";
            case COMPANY_ID:
                return "vnd.android.cursor.item/vnd.w4group1provider.companies";
            case OFFICES:
                return "vnd.android.cursor.dir/vnd.w4group1provider.offices";
            case OFFICE_ID:
                return "vnd.android.cursor.item/vnd.w4group1provider.offices";
            case GALLERIES:
                return "vnd.android.cursor.dir/vnd.w4group1provider.galleries";
            case GALLERY_ID:
                return "vnd.android.cursor.item/vnd.w4group1provider.galleries";
            case LANGUAGES:
                return "vnd.android.cursor.dir/vnd.w4group1provider.languages";
            case LANGUAGE_ID:
                return "vnd.android.cursor.item/vnd.w4group1provider.languages";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case COMPANIES:
                count = db.update(TABLE_COMPANY_NAME, values, selection, selectionArgs);
                break;
            case COMPANY_ID:
                count = db.update(TABLE_COMPANY_NAME, values,
                        KEY_COMPANY_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            case OFFICES:
                count = db.update(TABLE_OFFICE_NAME, values, selection, selectionArgs);
                break;
            case OFFICE_ID:
                count = db.update(TABLE_OFFICE_NAME, values,
                        KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            case GALLERIES:
                count = db.update(TABLE_GALLERY_NAME, values, selection, selectionArgs);
                break;
            case GALLERY_ID:
                count = db.update(TABLE_GALLERY_NAME, values,
                        KEY_GALLERY_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            case LANGUAGES:
                count = db.update(TABLE_LANGUAGE_NAME, values, selection, selectionArgs);
                break;
            case LANGUAGE_ID:
                count = db.update(TABLE_LANGUAGE_NAME, values,
                        KEY_LANGUAGE_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case COMPANIES:
                count = db.delete(TABLE_COMPANY_NAME, selection, selectionArgs);
                break;
            case COMPANY_ID:
                count = db.delete(TABLE_COMPANY_NAME,
                        KEY_COMPANY_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            case OFFICES:
                count = db.delete(TABLE_OFFICE_NAME, selection, selectionArgs);
                break;
            case OFFICE_ID:
                count = db.delete(TABLE_OFFICE_NAME,
                        KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            case GALLERIES:
                count = db.delete(TABLE_GALLERY_NAME, selection, selectionArgs);
                break;
            case GALLERY_ID:
                count = db.delete(TABLE_GALLERY_NAME,
                        KEY_GALLERY_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            case LANGUAGES:
                count = db.delete(TABLE_LANGUAGE_NAME, selection, selectionArgs);
                break;
            case LANGUAGE_ID:
                count = db.delete(TABLE_LANGUAGE_NAME,
                        KEY_LANGUAGE_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +
                                        selection + ")" : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
