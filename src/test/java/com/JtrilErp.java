package com;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Keys;

import util.InitData;
import util.Logger;
import util.MainInitData;
import util.ScriptsMgmt;
import configuration.LoginlInitData;

public class JtrilErp {


    public static WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    protected static LoginlInitData loginData = null;
    private MainInitData loginInitdata;
    private ScriptsMgmt scriptMgmt;
    private Logger logger;

    @Before
    public void setUp() {
//		System.setProperty("webdriver.gecko.driver","E:/ERAINFOTECH/Software/geckodriver-v0.31.0-win64/geckodriver.exe");
//	    driver = new FirefoxDriver();
//	    js = (JavascriptExecutor) driver;
//	    vars = new HashMap<String, Object>();
//
       // System.setProperty("webdriver.chrome.driver","D:\\DDrive\\chromedriver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//		String url= "https://seleniumpractise.blogspot.com/2016/08/how-to-automate-radio-button-in.html";
//		driver.get(url);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login () throws Exception{

        try {

            // Initialize data
            MainInitData.preCheckExec();
            loginInitdata = MainInitData.getInstance();
            LoginlInitData.preCheckExec();
            loginData = LoginlInitData.getInstance();
            scriptMgmt = ScriptsMgmt.getInstance();
            logger = Logger.getInstance();
            InitData.setScriptName("valencia.scripts.Valencia");
            scriptMgmt.logHeader(InitData.scriptName);

            // Execute login test case
            loginToJTRIL();
            navigationToInventoryManagement();
            createMaterialIssueRequisition();
            //logOut();



        }catch (Exception e)
        {
            e.printStackTrace();
            //scriptMgmt.logFail_stop_sendmail(InitData.scriptName,e);
        }


    }//login


    /**
     * Function Name: loginToJTRIL
     * Description: Login to JTRIL with legal value
     *
     */
    public void loginToJTRIL () throws Exception{
        String app_server [] = LoginlInitData.jtrilapp_server.split(",");
        String loginInput [] = LoginlInitData.jtrillogin_password.split(",");

        driver.get(app_server[0]);
        driver.findElement(By.id("P9999_USERNAME")).click();
        driver.findElement(By.id("P9999_USERNAME")).sendKeys(loginInput[0]);
        driver.findElement(By.id("P9999_PASSWORD")).sendKeys(loginInput[1]);
        driver.findElement(By.id("LOGIN")).click();
        Thread.sleep(5000);
//		    driver.findElement(By.linkText("LOGOUT: JAMUNA")).click();
//		    assertThat(driver.switchTo().alert().getText(), is("You are about to exit. Are you sure to continue..?"));
//		    driver.switchTo().alert().accept();
//		    Thread.sleep(5000);
        writeLog("JTRIL Login Test Case ", true,false);

    }//Login


    /**
     * Function Name: loginToJTRIL
     * Description: Navigation to Inventory Management
     *
     */
    public void navigationToInventoryManagement() throws Exception{

        Thread.sleep(3000);
        driver.findElement(By.id("t_Button_navControl")).click();
        driver.findElement(By.cssSelector("#t_TreeNav_1 > .a-TreeView-toggle")).click();
        driver.findElement(By.cssSelector("#t_TreeNav_4 > .a-TreeView-toggle")).click();
        driver.findElement(By.cssSelector("#t_TreeNav_5 > .a-TreeView-toggle")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("#t_TreeNav_6 .a-TreeView-label"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        {
            WebElement element = driver.findElement(By.linkText("Material Issue Requisition (MI-2)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.linkText("Material Issue Requisition (MI-2)")).click();
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }

        Thread.sleep(3000);
        writeLog("JTRIL Navigation to Material Issue Requisition Test Case ", true, false);
    }

    /**
     * Function Name: log out to system
     * Description: Navigation to Inventory Management
     *
     */
    public void createMaterialIssueRequisition() throws Exception{

        String loginInput [] = LoginlInitData.jtrillogin_password.split(",");
        Thread.sleep(5000);

        driver.findElement(By.cssSelector("#cicon > .tooltiptext")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("#cicon > .tooltiptext"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(5000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(5000);
        }
        driver.switchTo().frame(0);
        //driver.findElement(By.id("P413_REQDDATE|input")).click();
        driver.findElement(By.id("P413_REQDDATE|input")).sendKeys("11-Feb-2023");
        Thread.sleep(5000);
        //driver.findElement(By.linkText("9")).click();
        driver.findElement(By.id("P413_REQDDATE|input")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        //driver.findElement(By.className("link"));
        driver.findElement(By.cssSelector("#P413_WARECODE_lov_btn > .a-Icon")).click();
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector(".a-IconList-item")).click();
        Thread.sleep(5000);
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("#P413_SRCWARECODE_lov_btn > .a-Icon")).click();
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".a-IconList-item:nth-child(2)")).click();
        Thread.sleep(5000);
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("#NEXT > .t-Button-label")).click();
        Thread.sleep(5000);

        //************************//2nd screen//****************************************

        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("C524831731703802929"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#C524831731703802929_lov_btn > .a-Icon")).click();
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".a-IconList-item:nth-child(3)")).click();
        Thread.sleep(5000);
        driver.switchTo().frame(0);
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("C524832991221802942"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("C524832991221802942")).sendKeys("50");
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector(".apex-item-option--no > .a-Button"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector(".apex-item-option--yes > .a-Button")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#prod_info_ig_grid_vc_cur .a-Icon")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("prod_info_ig_row_actions_menu_2i")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#C524831731703802929_lov_btn > .a-Icon")).click();
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".a-IconList-item:nth-child(4)")).click();
        Thread.sleep(5000);
        driver.switchTo().frame(0);
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("C524832991221802942"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("C524832991221802942")).sendKeys("10");
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector(".apex-item-option--no > .a-Button"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector(".apex-item-option--yes > .a-Button")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#NEXT > .t-Button-label")).click();
        Thread.sleep(5000);

        // **************************//3rd screen//*********************************

        driver.switchTo().defaultContent();
//		      driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
//		      driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Thread.sleep(5000);
            Actions builder = new Actions(driver);
            Thread.sleep(5000);
            builder.moveToElement(element);
            builder.doubleClick();
            builder.perform();
            Thread.sleep(5000);
            //builder.doubleClick(element).perform();
        }

        Thread.sleep(5000);

        //****************************************************
        driver.findElement(By.cssSelector("#C524831731703802929_lov_btn > .a-Icon")).click();
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".a-IconList-item:nth-child(3)")).click();
        Thread.sleep(5000);
        driver.switchTo().frame(0);
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("C524832991221802942"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("C524832991221802942")).click();
        Thread.sleep(5000);
        {
            WebElement element = driver.findElement(By.id("C524832991221802942"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.id("C524832991221802942")).sendKeys("50");
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        Thread.sleep(5000);
        {
            WebElement element = driver.findElement(By.cssSelector(".apex-item-option--yes > .a-Button"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }


        writeLog("Material Issue Requisition (MI-2) generate successfully and go for Approval ", false, false);



    }

    /**
     * Function Name: log out to system
     * Description: Navigation to Inventory Management
     *
     */
    public void logOut() throws Exception{

        String loginInput [] = LoginlInitData.jtrillogin_password.split(",");
        Thread.sleep(5000);
        driver.findElement(By.linkText("LOGOUT: "+loginInput[0])).click();
        assertThat(driver.switchTo().alert().getText(), is("You are about to exit. Are you sure to continue..?"));
        driver.switchTo().alert().accept();
        Thread.sleep(3000);
        writeLog("JTRIL Log out Test Case ", false, true);



    }




    /**
     * Function Name: writeLog
     * Description: Write TestCases log into text file
     *
     * @since  2012/10/02
     * @author Abu Salahuddin
     */
    private void writeLog(String caption, boolean isPassed, boolean isMail)
    {
        if (isPassed) // if the operation is performed successfully.
            scriptMgmt.logPass(caption);
        else // if the operation is not performed successfully.
            scriptMgmt.logFail(caption);
        // scriptMgmt.logFail_stop_sendmail(caption);

        scriptMgmt.logFooter(caption, isMail);
    }//writeLog

}
