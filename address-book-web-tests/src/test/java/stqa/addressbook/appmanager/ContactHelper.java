package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;

import java.util.List;
import java.util.Properties;

public class ContactHelper extends HelperBase {

    private Properties properties;

    public ContactHelper(WebDriver wd) {
        super(wd);
        properties = new Properties();
    }

    public void initContactAdding() {
        click(By.linkText("add new"));
        wd.get(properties.getProperty("web.edit"));
    }

    public void initContactModification() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    private void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void fillContactForm(ContactData contactData, boolean create) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("work"), contactData.getWork());
        type(By.name("phone2"), contactData.getHome2());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        attach(By.name("photo"), contactData.getPhoto());

        if (create) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")), "Element is present");
        }
    }

    public void submitContactForm() {
        click(By.cssSelector("input[value='Enter']"));
    }

    public void submitContactModification() {
        click(By.cssSelector("input[value='Update']"));
    }

    public void create(ContactData contactData) {
        initContactAdding();
        fillContactForm(contactData,true);
        submitContactForm();
    }

    public void delete(ContactData contact) {
        selectById(contact.getId());
        click(By.cssSelector("input[value='Delete']"));
    }

    public void modify(ContactData contact) {
        selectById(contact.getId());
        initContactModification();
        fillContactForm(contact, false);
        submitContactModification();
    }

    private void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public boolean exists() {
        return isElementPresent(By.name("selected[]"));
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String home2 = wd.findElement(By.name("phone2")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withFirstName(firstname).withLastName(lastname).withAddress(address)
                .withHome(home).withMobile(mobile).withWork(work).withHome2(home2).withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String lastName = element.findElement(By.xpath("./td[2]")).getText();
            String firstName = element.findElement(By.xpath("./td[3]")).getText();
            String address = element.findElement(By.xpath("./td[4]")).getText();
            String allPhones = element.findElement(By.xpath("./td[6]")).getText();
            String allEmails = element.findElement(By.xpath("./td[5]")).getText();
            ContactData contact = new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withAddress(address)
                    .withAllPhones(allPhones).withAllEmails(allEmails);
            contacts.add(contact);
        }
        return contacts;
    }

}
