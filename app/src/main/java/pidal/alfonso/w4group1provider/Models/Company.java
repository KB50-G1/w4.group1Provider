package pidal.alfonso.w4group1provider.Models;

/**
 * Created by Sjoerd Thijsse on 12/3/2014.
 */
public class Company {

    private int companyID;
    private String name;
    private String website;

    public Company() {
    }

    public Company(int companyID, String name, String website) {
        this.companyID = companyID;
        this.name = name;
        this.website = website;
    }

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyID=" + companyID +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
