package pidal.alfonso.w4group1provider.Models;

/**
 * Created by Sjoerd Thijsse on 12/3/2014.
 */
public class Office {

    private int officeID;
    private int phoneNumber;
    private String address;
    private OfficeType officeType;
    private Company company;

    public Office() {
    }

    public Office(int officeID, int phoneNumber, String address, OfficeType officeType, Company company) {
        this.officeID = officeID;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.officeType = officeType;
        this.company = company;
    }

    public Office(int phoneNumber, String address, OfficeType officeType, Company company) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.officeType = officeType;
        this.company = company;
    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OfficeType getOfficeType() {
        return officeType;
    }

    public void setOfficeType(OfficeType officeType) {
        this.officeType = officeType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Office{" +
                "officeID=" + officeID +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", officeType=" + officeType +
                ", company=" + company.toString() +
                '}';
    }
}
