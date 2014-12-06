package pidal.alfonso.w4group1provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yi-An on 2014/12/5.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "content.provide.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Company
        String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS " + Provider.CompanyColumns.TABLE_NAME + "("
                + Provider.CompanyColumns.KEY_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Provider.CompanyColumns.KEY_NAME + " TEXT,"
                + Provider.CompanyColumns.KEY_WEBSITE + " TEXT" + ");";
        db.execSQL(CREATE_COMPANY_TABLE);

        //Office
        String CREATE_OFFICE_TABLE = "CREATE TABLE IF NOT EXISTS " + Provider.OfficeColumns.TABLE_NAME + "("
                + Provider.OfficeColumns.KEY_OFFICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Provider.OfficeColumns.KEY_PHONE_NUMBER + " INTEGER,"
                + Provider.OfficeColumns.KEY_ADDRESS + " TEXT," + Provider.OfficeColumns.KEY_OFFICE_TYPE + " TEXT," + Provider.CompanyColumns.KEY_COMPANY_ID + " INTEGER,"
                + " FOREIGN KEY(" + Provider.CompanyColumns.KEY_COMPANY_ID + ") REFERENCES "
                + Provider.CompanyColumns.TABLE_NAME + "(" + Provider.CompanyColumns.KEY_COMPANY_ID + "));";
        db.execSQL(CREATE_OFFICE_TABLE);
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Provider.CompanyColumns.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Provider.OfficeColumns.TABLE_NAME + ";");
        onCreate(db);
    }
}
