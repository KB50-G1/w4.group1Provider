package pidal.alfonso.w4group1provider.Models;

/**
 * Created by Sjoerd Thijsse on 12/3/2014.
 */
public class Language {

    private int languageID;
    private String language;
    private String description;
    private Company company;

    public Language() {
    }

    public Language(int languageID, String language, String description, Company company) {
        this.languageID = languageID;
        this.language = language;
        this.description = description;
        this.company = company;
    }

    public Language(String language, String description, Company company) {
        this.language = language;
        this.description = description;
        this.company = company;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Language{" +
                "languageID=" + languageID +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                ", company=" + company.toString() +
                '}';
    }
}
