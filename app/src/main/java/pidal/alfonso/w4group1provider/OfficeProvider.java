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
public class OfficeProvider extends ContentProvider {

    static final String PROVIDER_NAME = "pidal.alfonso.w4group1provider.OfficeProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/offices");

    static final int OFFICES = 1;
    static final int OFFICE_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "offices", OFFICES);
        uriMatcher.addURI(PROVIDER_NAME, "offices/#", OFFICE_ID);
    }

    public static final String TABLE_OFFICE_NAME = "offices";

    // Column names for offices table.
    protected static final String KEY_OFFICE_ID = "office_id";
    protected static final String KEY_PHONE_NUMBER = "phone_number";
    protected static final String KEY_ADDRESS = "address";
    protected static final String KEY_OFFICE_TYPE = "office_type";

    private SQLiteDatabase db;

    static final String DATABASE_NAME = "Companies";
    static final int DATABASE_VERSION = 1;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_OFFICE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_OFFICE_NAME + "("
                    + KEY_OFFICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHONE_NUMBER + " INTEGER,"
                    + KEY_ADDRESS + " TEXT," + KEY_OFFICE_TYPE + " TEXT," + CompanyProvider.KEY_COMPANY_ID + " INTEGER,"
                    + " FOREIGN KEY(" + CompanyProvider.KEY_COMPANY_ID + ") REFERENCES "
                    + CompanyProvider.TABLE_COMPANY_NAME + "(" + CompanyProvider.KEY_COMPANY_ID + "));";

            db.execSQL(CREATE_OFFICE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFICE_NAME + ";");

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
        long rowID = db.insert(TABLE_OFFICE_NAME, null, values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            this.getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_OFFICE_NAME);

        if (uriMatcher.match(uri) == OFFICE_ID) {
            queryBuilder.appendWhere(KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1));
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = KEY_ADDRESS;
        }

        Cursor c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(this.getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case OFFICES:
                return "vnd.android.cursor.dir/vnd.w4group1provider.offices";
            case OFFICE_ID:
                return "vnd.android.cursor.item/vnd.w4group1provider.offices";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
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
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
