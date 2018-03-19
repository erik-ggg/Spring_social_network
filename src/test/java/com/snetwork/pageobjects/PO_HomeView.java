package com.snetwork.pageobjects;

import com.snetwork.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.snetwork.pageobjects.PO_View.getTimeout;

public class PO_HomeView extends PO_NavView {
    static public void checkWelcome(WebDriver driver, int language) {
        if (language == 0) SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message"
                , PO_Properties.SPANISH), getTimeout());
        if (language == 1) SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message"
                , PO_Properties.ENGLISH), getTimeout());
    }

    static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2) {
        PO_HomeView.checkWelcome(driver, locale1);
        PO_HomeView.changeIdiom(driver, textIdiom2);
        PO_HomeView.checkWelcome(driver, locale2);
        SeleniumUtils.esperarSegundos(driver, 1);
        PO_HomeView.changeIdiom(driver, textIdiom1);
        PO_HomeView.checkWelcome(driver, locale1);
        SeleniumUtils.esperarSegundos(driver, 1);
    }

    static public void findUser(WebDriver driver, String searchText) {
        WebElement element = driver.findElement(By.name("searchText"));
        element.click();
        element.clear();
        element.sendKeys(searchText);
        By button = By.className("btn");
        driver.findElement(button).click();
    }

}
