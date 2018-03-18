package com.snetwork.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PublicationsAddView {
    static public void fillForm(WebDriver driver, String titlep, String contentp) {
        WebElement title = driver.findElement(By.name("title"));
        title.click();
        title.clear();
        title.sendKeys(titlep);
        WebElement content = driver.findElement(By.name("content"));
        content.click();
        content.clear();
        content.sendKeys(contentp);
        By button = By.className("btn");
        driver.findElement(button).click();
    }
}
