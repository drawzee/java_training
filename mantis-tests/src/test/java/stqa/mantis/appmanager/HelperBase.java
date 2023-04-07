package stqa.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class HelperBase {

    protected AppManager app;
    public WebDriver wd;

    public HelperBase(AppManager app) throws IOException {
        this.app = app;
        this.wd = app.getDriver();
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

    protected void attach(By locator, File file) {
        if (file != null) {
            wd.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    public void acceptAlert() {
        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    public boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException ex) {
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
