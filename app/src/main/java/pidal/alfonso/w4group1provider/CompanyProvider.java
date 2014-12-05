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
public class CompanyProvider extends ContentProvider {

    static final String PROVIDER_NAME = "pidal.alfonso.w4group1provider.CompanyProvider";

    public static Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/companies");

    static final int COMPANIES = 1;
    static final int COMPANY_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "companies", COMPANIES);
        uriMatcher.addURI(PROVIDER_NAME, "companies/#", COMPANY_ID);
    }

    public static final String TABLE_COMPANY_NAME = "companies";

    // Column names for company table.
    public static final String KEY_COMPANY_ID = "company_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_WEBSITE = "website";

    private SQLiteDatabase db;

    static final String DATABASE_NAME = "Companies";
    static final int DATABASE_VERSION = 1;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMPANY_NAME + "("
                    + KEY_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                    + KEY_WEBSITE + " TEXT" + ");";

            db.execSQL(CREATE_COMPANY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_NAME + ";");

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

        long rowID = db.insert(TABLE_COMPANY_NAME, null, values);

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
        queryBuilder.setTables(TABLE_COMPANY_NAME);

        if (uriMatcher.match(uri) == COMPANY_ID) {
            queryBuilder.appendWhere(KEY_COMPANY_ID + " = " + uri.getPathSegments().get(1));
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = KEY_NAME;
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
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
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
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
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
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }


}
