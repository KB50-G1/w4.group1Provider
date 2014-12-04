package pidal.alfonso.w4group1provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pidal.alfonso.w4group1provider.Models.Company;
import pidal.alfonso.w4group1provider.Models.Gallery;
import pidal.alfonso.w4group1provider.Models.Language;
import pidal.alfonso.w4group1provider.Models.Office;
import pidal.alfonso.w4group1provider.Models.OfficeType;

/**
 * Created by Sjoerd Thijsse on 12/4/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Strings for the constructor.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "test.db";

    // Table names
    private static final String TABLE_COMPANY_NAME = "companies";
    private static final String TABLE_OFFICE_NAME = "offices";
    private static final String TABLE_GALLERY_NAME = "galleries";
    private static final String TABLE_LANGUAGE_NAME = "languages";

    // Column names for company table.
    private static final String KEY_COMPANY_ID = "company_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_WEBSITE = "website";

    // Column names for offices table.
    private static final String KEY_OFFICE_ID = "office_id";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_OFFICE_TYPE = "office_type";

    // Column names for galleries table.
    private static final String KEY_GALLERY_ID = "gallery_id";
    private static final String KEY_URL = "url";

    // Column names for languages table.
    private static final String KEY_LANGUAGE_ID = "language_id";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_DESCRIPTION = "description";

    protected SQLiteDatabase db;

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

        db.execSQL(CREATE_COMPANY_TABLE);
        db.execSQL(CREATE_OFFICE_TABLE);
        db.execSQL(CREATE_GALLERY_TABLE);
        db.execSQL(CREATE_LANGUAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFICE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERY_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE_NAME + ";");

        onCreate(db);
    }

    // Adds a company instance.
    public void addCompany(Company company) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_COMPANY_ID, company.getCompanyID());
        initialValues.put(KEY_NAME, company.getName());
        initialValues.put(KEY_WEBSITE, company.getWebsite());

        db.insert(TABLE_COMPANY_NAME, null, initialValues);
        db.close();
    }

    // Retrieves a company instance.
    public Company getCompany(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMPANY_NAME,
                new String[]{KEY_COMPANY_ID, KEY_NAME, KEY_WEBSITE},
                KEY_COMPANY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Company company = null;

        if (cursor.moveToFirst()) {

            company = new Company(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
            cursor.close();
        }

        return company;
    }

    // Retrieves all company instances.
    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<Company>();

        String selectQuery = "SELECT * FROM " + TABLE_COMPANY_NAME;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setCompanyID(Integer.parseInt(cursor.getString(0)));
                company.setName(cursor.getString(1));
                company.setWebsite(cursor.getString(2));

                companyList.add(company);
            } while (cursor.moveToNext());
        }

        return companyList;
    }

    // Updates a company instance.
    public int updateCompany(Company company) {
        db = this.getWritableDatabase();

        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, company.getName());
        updateValues.put(KEY_WEBSITE, company.getWebsite());

        return db.update(TABLE_COMPANY_NAME, updateValues, KEY_COMPANY_ID + " = ?",
                new String[]{String.valueOf(company.getCompanyID())});
    }

    // Deletes a company instance.
    public void deleteCompany(Company company) {
        db = this.getWritableDatabase();
        db.delete(TABLE_COMPANY_NAME, KEY_COMPANY_ID + " = ?",
                new String[]{String.valueOf(company.getCompanyID())});
        db.close();
    }

    // Adds an office instance.
    public void addOffice(Office office) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PHONE_NUMBER, office.getPhoneNumber());
        initialValues.put(KEY_ADDRESS, office.getAddress());
        initialValues.put(KEY_OFFICE_TYPE, office.getOfficeType().toString());
        initialValues.put(KEY_COMPANY_ID, office.getCompany().getCompanyID());

        db.insert(TABLE_OFFICE_NAME, null, initialValues);
        db.close();
    }

    // Retrieves a office instance.
    public Office getOffice(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OFFICE_NAME,
                new String[]{KEY_OFFICE_ID, KEY_PHONE_NUMBER, KEY_ADDRESS, KEY_OFFICE_TYPE, KEY_COMPANY_ID},
                KEY_OFFICE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Office office = null;

        if (cursor.moveToFirst()) {
            Company company = this.getCompany(Integer.parseInt(cursor.getString(4)));
            office = new Office(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), cursor.getString(2),
                    OfficeType.valueOf(cursor.getString(3)), company);
            cursor.close();
        }

        return office;
    }

    // Retrieves all office instances.
    public List<Office> getAllOffices() {
        List<Office> officeList = new ArrayList<Office>();

        String selectQuery = "SELECT * FROM " + TABLE_OFFICE_NAME + ";";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Office office = new Office();
                office.setOfficeID(Integer.parseInt(cursor.getString(0)));
                office.setPhoneNumber(Integer.parseInt(cursor.getString(1)));
                office.setAddress(cursor.getString(2));
                office.setOfficeType(OfficeType.valueOf(cursor.getString(3)));
                office.setCompany(this.getCompany(Integer.parseInt(cursor.getString(4))));

                officeList.add(office);

            } while (cursor.moveToNext());
        }

        return officeList;
    }

    // Updates an office instance.
    public int updateOffice(Office office) {
        db = this.getWritableDatabase();

        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_PHONE_NUMBER, office.getPhoneNumber());
        updateValues.put(KEY_ADDRESS, office.getAddress());
        updateValues.put(KEY_OFFICE_TYPE, office.getOfficeType().toString());
        updateValues.put(KEY_COMPANY_ID, office.getCompany().getCompanyID());

        return db.update(TABLE_OFFICE_NAME, updateValues, KEY_OFFICE_ID + " = ?",
                new String[]{String.valueOf(office.getOfficeID())});
    }

    // Deletes an office instance.
    public void deleteOffice(Office office) {
        db = this.getWritableDatabase();
        db.delete(TABLE_OFFICE_NAME, KEY_OFFICE_ID + " = ?",
                new String[]{String.valueOf(office.getOfficeID())});
        db.close();
    }

    // Adds a gallery instance.
    public void addGallery(Gallery gallery) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GALLERY_ID, gallery.getGalleryID());
        initialValues.put(KEY_URL, gallery.getURL());
        initialValues.put(KEY_COMPANY_ID, gallery.getCompany().getCompanyID());

        db.insert(TABLE_GALLERY_NAME, null, initialValues);
        db.close();
    }

    // Retrieves a gallery instance.
    public Gallery getGallery(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GALLERY_NAME,
                new String[]{KEY_GALLERY_ID, KEY_URL, KEY_COMPANY_ID},
                KEY_GALLERY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Gallery gallery = null;

        if (cursor.moveToFirst()) {
            Company company = this.getCompany(Integer.parseInt(cursor.getString(2)));
            gallery = new Gallery(Integer.parseInt(cursor.getString(0)), cursor.getString(1), company);
            cursor.close();
        }

        return gallery;
    }

    // Retrieves all gallery instances.
    public List<Gallery> getAllGalleries() {
        List<Gallery> galleryList = new ArrayList<Gallery>();

        String selectQuery = "SELECT * FROM " + TABLE_GALLERY_NAME + ";";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Gallery gallery = new Gallery();
                gallery.setGalleryID(Integer.parseInt(cursor.getString(0)));
                gallery.setURL(cursor.getString(1));
                gallery.setCompany(this.getCompany(Integer.parseInt(cursor.getString(2))));

                galleryList.add(gallery);

            } while (cursor.moveToNext());
        }

        return galleryList;
    }

    // Updates a gallery instance.
    public int updateGallery(Gallery gallery) {
        db = this.getWritableDatabase();

        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_GALLERY_ID, gallery.getGalleryID());
        updateValues.put(KEY_URL, gallery.getURL());
        updateValues.put(KEY_COMPANY_ID, gallery.getCompany().getCompanyID());

        return db.update(TABLE_GALLERY_NAME, updateValues, KEY_COMPANY_ID + " = ?",
                new String[]{String.valueOf(gallery.getGalleryID())});
    }

    // Deletes a gallery instance.
    public void deleteGallery(Gallery gallery) {
        db = this.getWritableDatabase();
        db.delete(TABLE_GALLERY_NAME, KEY_COMPANY_ID + " = ?",
                new String[]{String.valueOf(gallery.getGalleryID())});
        db.close();
    }

    // Adds a language instance.
    public void addLanguage(Language language) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LANGUAGE_ID, language.getLanguageID());
        initialValues.put(KEY_LANGUAGE, language.getLanguage());
        initialValues.put(KEY_DESCRIPTION, language.getDescription());
        initialValues.put(KEY_COMPANY_ID, language.getCompany().getCompanyID());

        db.insert(TABLE_LANGUAGE_NAME, null, initialValues);
        db.close();
    }

    // Retrieves a language instance.
    public Language getLanguage(int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LANGUAGE_NAME,
                new String[]{KEY_LANGUAGE_ID, KEY_LANGUAGE, KEY_DESCRIPTION, KEY_COMPANY_ID},
                KEY_LANGUAGE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

       Language language = null;

        if (cursor.moveToFirst()) {
            Company company = this.getCompany(Integer.parseInt(cursor.getString(3)));
            language = new Language(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), company);
            cursor.close();
        }

        return language;
    }

    // Retrieves all language instances.
    public List<Language> getAllLanguages() {
        List<Language> languageList = new ArrayList<Language>();

        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGE_NAME + ";";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Language language = new Language();
                language.setLanguageID(Integer.parseInt(cursor.getString(0)));
                language.setLanguage(cursor.getString(1));
                language.setDescription(cursor.getString(2));
                language.setCompany(this.getCompany(Integer.parseInt(cursor.getString(3))));

                languageList.add(language);

            } while (cursor.moveToNext());
        }

        return languageList;
    }

    // Updates a language instance.
    public int updateLanguage(Language language) {
        db = this.getWritableDatabase();

        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_LANGUAGE, language.getLanguage());
        updateValues.put(KEY_DESCRIPTION, language.getDescription());
        updateValues.put(KEY_COMPANY_ID, language.getCompany().getCompanyID());

        return db.update(TABLE_LANGUAGE_NAME, updateValues, KEY_LANGUAGE_ID + " = ?",
                new String[]{String.valueOf(language.getLanguageID())});
    }

    // Deletes a language instance.
    public void deleteLanguage(Language language) {
        db = this.getWritableDatabase();
        db.delete(TABLE_LANGUAGE_NAME, KEY_LANGUAGE_ID + " = ?",
                new String[]{String.valueOf(language.getLanguageID())});
        db.close();
    }
}

