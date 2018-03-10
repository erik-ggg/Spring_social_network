package com.snetwork;

import com.snetwork.pageobjects.PO_HomeView;
import com.snetwork.pageobjects.PO_RegisterView;
import com.snetwork.pageobjects.PO_View;
import com.snetwork.utils.Constants;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SnetworkApplicationTests {
    static String  PathFirefox = "C:\\Users\\ERIK\\Documents\\University\\SDI\\Selenium\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
    static WebDriver driver = getDriver(PathFirefox);

    private static WebDriver getDriver(String path) {
        System.setProperty("webdriver.gecko.driver", "F:\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        return driver;
    }

    static String URL = "http://localhost:8090";

    @Before
    public void setUp() throws Exception {
        driver.navigate().to(URL);
    }

    @After
    public void tearDown() throws Exception {
        driver.manage().deleteAllCookies();
    }

    @BeforeClass
    static public void begin() {

    }

    @AfterClass
    static public void end() {
        driver.quit();
    }

    @Test
    public void PR01() {
        PO_View.checkElement(driver, Constants.TEXT_TYPE, "Social network");
    }

    @Test
    public void PR02() {
        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
        PO_RegisterView.fillForm(driver, "Pedro", "pedro@gmail.com", "123123", "123123");
        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
        PO_RegisterView.fillForm(driver, "Juan", "juan@gmail.com", "123123", "123123");
        //PO_View.checkElement(driver, Constants.TEXT_TYPE, "Social network");
    }
}
