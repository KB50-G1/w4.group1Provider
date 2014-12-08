package pidal.alfonso.w4group1provider;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainBlankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blank);
<<<<<<< HEAD
=======


        this.addCompany();
        this.getCompany("1");

        this.updateCompany("1");

        this.getCompany("1");

        this.getAllCompanies();

        this.addOffice();
        this.getOffice("1");
        this.updateOffice("1");
        this.getOffice("1");

        this.getAllOffices();

        this.deleteOffice("1");
        this.deleteCompany("1");

    }

    private void addCompany() {
        ContentValues values = new ContentValues();
        values.put(Provider.CompanyColumns.KEY_COMPANY_ID, 1);
        values.put(Provider.CompanyColumns.KEY_NAME, "Albert Hijn");
        values.put(Provider.CompanyColumns.KEY_WEBSITE, "http://google.com");

        Uri uri = getContentResolver().insert(
                Provider.CompanyColumns.CONTENT_URI, values);
    }

    private void addOffice() {
        ContentValues values = new ContentValues();
        values.put(Provider.OfficeColumns.KEY_OFFICE_ID, 1);
        values.put(Provider.OfficeColumns.KEY_ADDRESS, "Test");
        values.put(Provider.OfficeColumns.KEY_PHONE_NUMBER, 1234);
        values.put(Provider.OfficeColumns.KEY_OFFICE_TYPE, "XL");

        Uri uri = getContentResolver().insert(
                Provider.OfficeColumns.CONTENT_URI, values);
    }

    private void updateCompany(String id) {
        ContentValues editedValues = new ContentValues();
        editedValues.put(Provider.CompanyColumns.KEY_NAME, "This was updated!");
        getContentResolver().update(Uri.parse("content://pidal.alfonso.w4group1provider.CompanyProvider/companies/" + id),
                editedValues,
                null,
                null);
    }

    private void updateOffice(String id) {
        ContentValues editedValues = new ContentValues();
        editedValues.put(Provider.OfficeColumns.KEY_ADDRESS, "This was also updated!");
        getContentResolver().update(Uri.parse("content://pidal.alfonso.w4group1provider.OfficeProvider/offices/" + id),
                editedValues,
                null,
                null);
    }

    private void deleteCompany(String id) {
        getContentResolver().delete(Uri.parse("content://pidal.alfonso.w4group1provider.CompanyProvider/companies/" + id),
                null, null);

    }

    private void deleteOffice(String id) {
        getContentResolver().delete(Uri.parse("content://pidal.alfonso.w4group1provider.OfficeProvider/offices/" + id),
                null, null);

    }

    private void getAllCompanies() {
        Uri allCompanies = Uri.parse("content://pidal.alfonso.w4group1provider.CompanyProvider/companies/");

        Cursor c;

        if (android.os.Build.VERSION.SDK_INT < 11) {
            c = managedQuery(allCompanies, null, null, null,
                    "name desc");
        } else {
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allCompanies, null, null, null,
                    "name desc");
            c = cursorLoader.loadInBackground();
        }


        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                Provider.CompanyColumns.KEY_COMPANY_ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.CompanyColumns.KEY_NAME)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.CompanyColumns.KEY_WEBSITE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    private void getAllOffices() {
        Uri allOffices = Uri.parse("content://pidal.alfonso.w4group1provider.OfficeProvider/offices/");

        Cursor c;

        if (android.os.Build.VERSION.SDK_INT < 11) {
            c = managedQuery(allOffices, null, null, null,
                    "address desc");
        } else {
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allOffices, null, null, null,
                    "address desc");
            c = cursorLoader.loadInBackground();
        }


        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                Provider.OfficeColumns.KEY_OFFICE_ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.OfficeColumns.KEY_ADDRESS)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.OfficeColumns.KEY_PHONE_NUMBER)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.OfficeColumns.KEY_OFFICE_TYPE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    private void getCompany(String id) {
        Uri allCompanies = Uri.parse("content://pidal.alfonso.w4group1provider.CompanyProvider/companies/" + id);

        Cursor c;

        if (android.os.Build.VERSION.SDK_INT < 11) {
            c = managedQuery(allCompanies, null, null, null,
                    "name desc");
        } else {
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allCompanies, null, null, null,
                    "name desc");
            c = cursorLoader.loadInBackground();
        }


        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                Provider.CompanyColumns.KEY_COMPANY_ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.CompanyColumns.KEY_NAME)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.CompanyColumns.KEY_WEBSITE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    private void getOffice(String id) {
        Uri allOffices = Uri.parse("content://pidal.alfonso.w4group1provider.OfficeProvider/offices/" + id);

        Cursor c;

        if (android.os.Build.VERSION.SDK_INT < 11) {
            c = managedQuery(allOffices, null, null, null,
                    "address desc");
        } else {
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allOffices, null, null, null,
                    "address desc");
            c = cursorLoader.loadInBackground();
        }


        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                Provider.OfficeColumns.KEY_OFFICE_ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.OfficeColumns.KEY_PHONE_NUMBER)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.OfficeColumns.KEY_ADDRESS)) + ", " +
                                c.getString(c.getColumnIndex(
                                        Provider.OfficeColumns.KEY_OFFICE_TYPE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
>>>>>>> origin/feature_sqlite
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_blank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
