package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class HelperBase {


    public WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
                String currentText = wd.findElement(locator).getAttribute("value");
            if (!text.equals(currentText)) {
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    public void acceptAlert() {
        wd.switchTo().alert().accept();
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    public boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

}
