package stqa.addressbook;

public class ContactData {

    private final String firstname;
    private final String lastname;
    private final String company;
    private final String address;
    private final String home;
    private final String email;

    public ContactData(String firstname, String lastname, String company, String address, String home, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
        this.home = home;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHome() {
        return home;
    }

    public String getEmail() {
        return email;
    }

}
