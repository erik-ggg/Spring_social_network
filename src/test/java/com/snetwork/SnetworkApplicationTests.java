package com.snetwork;
//
//import com.snetwork.pageobjects.*;
//import com.utils.Constants;
//import com.utils.SeleniumUtils;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SnetworkApplicationTests {
//    static String  PathFirefox = "C:\\Users\\ERIK\\Documents\\University\\SDI\\Selenium\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
//    static WebDriver driver = getDriver(PathFirefox);
//
//    private static WebDriver getDriver(String path) {
//        System.setProperty("webdriver.gecko.driver", "F:\\geckodriver.exe");
//        WebDriver driver = new FirefoxDriver();
//        return driver;
//    }
//
//    static String URL = "http://localhost:8090";
//
//    @Before
//    public void setUp() throws Exception {
//        driver.navigate().to(URL);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        driver.manage().deleteAllCookies();
//    }
//
//    @BeforeClass
//    static public void begin() {
//
//    }
//
//    @AfterClass
//    static public void end() {
//        driver.quit();
//    }
//
////    @Test
////    public void PR01() {
////        PO_View.checkElement(driver, Constants.TEXT_TYPE, "Bienvenidos a la pagina principal");
////    }
//
//    @Test
//    public void PR01_1() {
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Pedro", "pedro@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Juanito", "juan@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Melanito", "melanito@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Jorge", "jorge@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Saul23", "saul@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Erik93", "erik@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Doraemon", "doraemon@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Silvia", "silvia@gmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Maria", "maria@hotmail.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Antonia", "antonia@yahoo.com", "123123", "123123");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        //PO_View.checkElement(driver, Constants.TEXT_TYPE, "Social network");
//    }
//
//    @Test
//    public void PR01_2() {
//        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_RegisterView.fillForm(driver, "Pedro", "pedro@gmail.com", "123", "123");
//        PO_View.checkElement(driver, "text", "La contrasena debe tener entre 5 y 24 caracteres.");
//    }
//
//    @Test
//    public void PR02_1() {
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        PO_View.checkElement(driver, "text", "Amigos");
//        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//    }
//
//    @Test
//    public void PR02_2() {
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123");
//        PO_View.checkElement(driver, "text", "Email o contraseña incorrectos");
//    }
//
//    @Test
//    public void PR03_1() {
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        driver.get("http://localhost:8090/admin/list");
//        PO_View.checkElement(driver, "text", "Forbidden");
//    }
//
//    @Test
//    public void PR03_2() {
////        PO_HomeView.clickOption(driver, Constants.LOGOUT_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
////        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        driver.get("http://localhost:8090/home");
//        PO_View.checkElement(driver, "text", "Identifícate");
//    }
//
//    @Test
//    public void PR04_1() {
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        PO_HomeView.findUser(driver, "ju");
//        PO_View.checkElement(driver, "text", "juan@gmail.com");
//    }
//
//    @Test
//    public void PR04_2() {
//        driver.get("http://localhost:8090/home/search?searchText=ju");
//        PO_View.checkElement(driver, "text", "Identifícate");
//    }
//
//
//    @Test
//    public void PR09() {
//        // Comprueba amigos
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        List<WebElement> elementList = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friends')]");
//        elementList.get(0).click();
//        PO_View.checkElement(driver, Constants.TEXT_TYPE, "juan@gmail.com");
//    }
//
//    @Test
//    public void PR10() {
//        // Comprueba peticiones recibidas
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        List<WebElement> elementList = PO_View.checkElement(driver, "free", "//a[contains(@href, 'requests/list')]");
//        elementList.get(0).click();
//        PO_View.checkElement(driver, Constants.TEXT_TYPE, "antonia@yahoo.com");
//    }
//
//    @Test
//    public void PR11() {
//        // Comprueba nuevas publicaciones
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        List<WebElement> elementList = PO_View.checkElement(driver,"free","//li[contains(@id, 'users-menu')]/a");
//        elementList.get(0).click();
//        elementList = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publications/add')]");
//        elementList.get(0).click();
//        PO_PublicationsAddView.fillForm(driver, "Test", "Contenido");
//    }
//
//    @Test
//    public void PR12() {
//        // Comprueba publicaciones tuyas
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "pedro@gmail.com", "123123");
//        List<WebElement> elementList = PO_View.checkElement(driver,"free","//li[contains(@id, 'users-menu')]/a");
//        elementList.get(0).click();
//        elementList = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publications/list')]");
//        elementList.get(0).click();
//        PO_View.checkElement(driver, Constants.TEXT_TYPE, "Test");
//        PO_View.checkElement(driver, Constants.TEXT_TYPE, "Contenido");
//    }
//
//    @Test
//    public void PR13() {
//        // Comprueba publicaciones de amigos
//        PO_HomeView.clickOption(driver, Constants.LOGIN_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
//        PO_LoginView.fillForm(driver, "juan@gmail.com", "123123");
//        List<WebElement> elementList = PO_View.checkElement(driver,"free","//li[contains(@id, 'users-menu')]/a");
//        elementList.get(0).click();
//        elementList = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publications/friends')]");
//        elementList.get(0).click();
//        PO_View.checkElement(driver, Constants.TEXT_TYPE, "Test");
//        PO_View.checkElement(driver, Constants.TEXT_TYPE, "Contenido");
//    }
}
