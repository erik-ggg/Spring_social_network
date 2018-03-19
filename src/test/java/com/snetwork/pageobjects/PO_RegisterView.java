package com.snetwork.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RegisterView extends PO_NavView {
    static public void fillForm(WebDriver driver, String namep, String emailp, String passwordp
        , String passwordconfp) {
//        SeleniumUtils.esperarSegundos(driver, 1);
        WebElement name = driver.findElement(By.name("name"));
        name.click();
        name.clear();
        name.sendKeys(namep);
//        SeleniumUtils.esperarSegundos(driver, 1);
        WebElement email = driver.findElement(By.name("email"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
//        SeleniumUtils.esperarSegundos(driver, 1);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
//        SeleniumUtils.esperarSegundos(driver, 1);
        WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
        passwordConfirm.click();
        passwordConfirm.clear();
        passwordConfirm.sendKeys(passwordconfp);
//        SeleniumUtils.esperarSegundos(driver, 1);

        By button = By.className("btn");
        driver.findElement(button).click();
    }
}
