package pidal.alfonso.w4group1provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Yi-An on 2014/12/5.
 */
public class Provider {

    //Company
    public static final class CompanyColumns implements BaseColumns {
        public static final String PROVIDER_NAME = "pidal.alfonso.w4group1provider.CompanyProvider";
        public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/companies");
        public static final String TABLE_NAME = "companies";

        // Column names for company table.
        public static final String KEY_COMPANY_ID = "company_id";
        public static final String KEY_NAME = "name";
        public static final String KEY_WEBSITE = "website";
    }

    //Office
    public static final class OfficeColumns implements BaseColumns {
        public static final String PROVIDER_NAME = "pidal.alfonso.w4group1provider.OfficeProvider";
        public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/offices");
        public static final String TABLE_NAME = "offices";

        // Column names for offices table.
        protected static final String KEY_OFFICE_ID = "office_id";
        protected static final String KEY_PHONE_NUMBER = "phone_number";
        protected static final String KEY_ADDRESS = "address";
        protected static final String KEY_OFFICE_TYPE = "office_type";
    }
}
