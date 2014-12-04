package pidal.alfonso.w4group1provider.Models;

/**
 * Created by Sjoerd Thijsse on 12/3/2014.
 */
public class Gallery {

    private int galleryID;
    private String URL;
    private Company company;

    public Gallery() {
    }

    public Gallery(int galleryID, String URL, Company company) {
        this.galleryID = galleryID;
        this.URL = URL;
        this.company = company;
    }

    public Gallery(String URL, Company company) {
        this.URL = URL;
        this.company = company;
    }

    public int getGalleryID() {
        return galleryID;
    }

    public void setGalleryID(int galleryID) {
        this.galleryID = galleryID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "galleryID=" + galleryID +
                ", URL='" + URL + '\'' +
                ", company=" + company.toString() +
                '}';
    }
}
