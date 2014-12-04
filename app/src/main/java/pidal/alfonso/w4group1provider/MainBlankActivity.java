package pidal.alfonso.w4group1provider;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pidal.alfonso.w4group1provider.Models.Company;
import pidal.alfonso.w4group1provider.Models.Gallery;
import pidal.alfonso.w4group1provider.Models.Language;
import pidal.alfonso.w4group1provider.Models.Office;
import pidal.alfonso.w4group1provider.Models.OfficeType;


public class MainBlankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blank);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Company testCompany = new Company("Test Company 1", "http://google.com");
        Office testOffice = new Office(1, "Test Office 1", OfficeType.REGULAR, testCompany);
        Gallery testGallery = new Gallery("http://www.youtube.com", testCompany);
        Language testLanguage = new Language("English", "This is a test.", testCompany);

        dbHelper.addCompany(testCompany);
        dbHelper.addOffice(testOffice);
        dbHelper.addLanguage(testLanguage);
        dbHelper.addGallery(testGallery);

        Company returnCompany = dbHelper.getCompany(1);
        Log.d("Reading company", returnCompany.toString());

        List<Company> companyList = dbHelper.getAllCompanies();

        for (Company aCompanyList : companyList) {
            Log.d("Reading all companies", aCompanyList.toString());
        }

        returnCompany.setName("This was changed!");
        int updatedCompany = dbHelper.updateCompany(returnCompany);
        Log.d("Updating company", dbHelper.getCompany(updatedCompany).toString());

        Office returnOffice = dbHelper.getOffice(1);
        Log.d("Reading office", returnOffice.toString());

        List<Office> officeList = dbHelper.getAllOffices();

        for (Office anOfficeList : officeList) {
            Log.d("Reading all offices", anOfficeList.toString());
        }

        returnOffice.setAddress("This was changed!");
        int updatedOffice = dbHelper.updateOffice(returnOffice);
        Log.d("Updating office", dbHelper.getOffice(updatedOffice).toString());

        //dbHelper.deleteOffice(returnOffice);
        //Log.d("Deleting office", "deleted");

        Gallery returnGallery = dbHelper.getGallery(1);
        Log.d("Reading gallery", returnGallery.toString());

        List<Gallery> galleryList = dbHelper.getAllGalleries();

        for (Gallery aGalleryList : galleryList) {
            Log.d("Reading all galleries", aGalleryList.toString());
        }

        returnGallery.setURL("This was changed!");
        int updatedGallery = dbHelper.updateGallery(returnGallery);
        Log.d("Updating gallery", dbHelper.getGallery(updatedGallery).toString());

        //dbHelper.deleteGallery(returnGallery);
        //Log.d("Deleting gallery", "deleted");

        Language returnLanguage = dbHelper.getLanguage(1);
        Log.d("Reading language", returnLanguage.toString());

        List<Language> languageList = dbHelper.getAllLanguages();

        for (Language aLanguageList : languageList)
            Log.d("Reading all languages", aLanguageList.toString());

        returnLanguage.setDescription("This was changed!");
        int updatedLanguage = dbHelper.updateLanguage(returnLanguage);
        Log.d("Updating language", dbHelper.getLanguage(updatedLanguage).toString());

        //dbHelper.deleteLanguage(returnLanguage);
        //Log.d("Deleting language", "deleted");

        //dbHelper.deleteCompany(returnCompany);
        //Log.d("Deleting company", "deleted");
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
