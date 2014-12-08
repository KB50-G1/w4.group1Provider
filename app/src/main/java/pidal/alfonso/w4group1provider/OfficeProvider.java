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


    static final int OFFICES = 1;
    static final int OFFICE_ID = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Provider.OfficeColumns.PROVIDER_NAME, "offices", OFFICES);
        uriMatcher.addURI(Provider.OfficeColumns.PROVIDER_NAME, "offices/#", OFFICE_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = this.getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(Provider.OfficeColumns.TABLE_NAME, null, values);

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
        queryBuilder.setTables(Provider.OfficeColumns.TABLE_NAME);

        if (uriMatcher.match(uri) == OFFICE_ID) {
            queryBuilder.appendWhere(Provider.OfficeColumns.KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1));
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = Provider.OfficeColumns.KEY_ADDRESS;
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
                count = db.update(Provider.OfficeColumns.TABLE_NAME, values, selection, selectionArgs);
                break;
            case OFFICE_ID:
                count = db.update(Provider.OfficeColumns.TABLE_NAME, values,
                        Provider.OfficeColumns.KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1) +
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
                count = db.delete(Provider.OfficeColumns.TABLE_NAME, selection, selectionArgs);
                break;
            case OFFICE_ID:
                count = db.delete(Provider.OfficeColumns.TABLE_NAME,
                        Provider.OfficeColumns.KEY_OFFICE_ID + " = " + uri.getPathSegments().get(1) +
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
