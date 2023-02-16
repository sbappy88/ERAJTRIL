package com;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.*;
import java.util.NoSuchElementException;
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
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
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
            InitData.setScriptName("com.JtrilErp");
            scriptMgmt.logHeader(InitData.scriptName);

            // Execute all test case
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
        Thread.sleep(3000);

        driver.findElement(By.cssSelector("#cicon > .tooltiptext")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("#cicon > .tooltiptext"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(3000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(3000);
        }
        driver.switchTo().frame(0);
        driver.findElement(By.id("P413_REQDDATE|input")).sendKeys("27-Feb-2023");
        Thread.sleep(2000);
        driver.findElement(By.id("P413_REQDDATE|input")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#P413_WARECODE_lov_btn > .a-Icon")).click();
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".a-PopupLOV-search")).clear();
        driver.findElement(By.cssSelector(".a-PopupLOV-search")).sendKeys("PR_SH1 - Production Shed 01");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".a-PopupLOV-search")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("#P413_SRCWARECODE_lov_btn > .a-Icon")).click();
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#PopupLov_413_P413_SRCWARECODE_dlg .a-PopupLOV-search")).sendKeys("DW1001 - Dhaka Warehouse");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#PopupLov_413_P413_SRCWARECODE_dlg .a-PopupLOV-search")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("#NEXT > .t-Button-label")).click();
        System.out.println("Document Information Entered Successfully!!");
        Thread.sleep(5000);

        // **************************//3rd screen//*********************************
        //Enter product information in table row
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.id("prod_info_ig_grid_vc_cur") );
        act.moveToElement(ele);
        Thread.sleep(2000);
        act.doubleClick(ele).perform(); //table row double click for product code enable
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#C524831731703802929_lov_btn > .a-Icon")).click();
        Thread.sleep(3000);

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/input")).sendKeys("RM-PRD-0001 - Rubber");
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/input")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
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
        Thread.sleep(3000);
        driver.findElement(By.id("C524832991221802942")).sendKeys("1");
        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("C476187776190156703"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("C476187776190156703")).sendKeys("test1");
        Thread.sleep(2000);

        //Submit Yes/No product information in table row
        Actions act2 = new Actions(driver);
        WebElement ele2 = driver.findElement(By.xpath("/html/body/form/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div/div/div[3]/div[3]/div[1]/div[3]/div[4]/table/tbody/tr/td[9]") );
        act2.moveToElement(ele2);
        Thread.sleep(3000);
        act2.click(ele2).perform();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("span.apex-item-option:nth-child(1) > label:nth-child(2)")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(".apex-item-option--yes > .a-Button")).click(); // Yes option choose
        Thread.sleep(3000);
        //***End 1st row data entry

        //Start 2nd row product information
        Actions act3 = new Actions(driver);
        WebElement ele3 = driver.findElement(By.xpath("/html/body/form/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div/div/div[3]/div[3]/div[1]/div[3]/div[4]/table/tbody/tr/td[1]/button/span") );
        act3.moveToElement(ele3);
        Thread.sleep(3000);
        act3.click(ele3).perform();
        Thread.sleep(3000);
        driver.findElement(By.id("prod_info_ig_row_actions_menu_2i")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#C524831731703802929_lov_btn > .a-Icon")).click();
        Thread.sleep(3000);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/input")).clear();
        driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/input")).sendKeys("RM-PRD-0002 - CARBON BLACK");
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/input")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);

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
        driver.findElement(By.id("C524832991221802942")).sendKeys("1");

        {
            WebElement element = driver.findElement(By.id("prod_info_ig_grid_vc_cur"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("C476187776190156703"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("prod_info_ig_grid_vc_cur")).click();
        driver.findElement(By.id("C476187776190156703")).sendKeys("remarks");

        //Submit Yes/No product information in table row
        Actions act4 = new Actions(driver);
        WebElement ele4 = driver.findElement(By.xpath("/html/body/form/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div/div/div[3]/div[3]/div[1]/div[3]/div[4]/table/tbody/tr[2]/td[9]") );
        act3.moveToElement(ele4);
        Thread.sleep(3000);
        act4.click(ele4).perform();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("span.apex-item-option:nth-child(1) > label:nth-child(2)")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(".apex-item-option--yes > .a-Button")).click(); // Yes option choose
        Thread.sleep(3000);
        driver.findElement(By.id("R525102492243922821_heading")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#NEXT > .t-Button-label")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#SUBMIT > .t-Button-label")).click();
        Thread.sleep(3000);

        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".js-confirmBtn")).click();
        Thread.sleep(3000);

        driver.switchTo().frame(0);
        String text2 =driver.findElement(By.cssSelector("#t_Alert_Success .t-Alert-title")).getText();
        //System.out.println("Alert text: "+text2);
        String[] elements = text2.split("No. :");
        elements[1] = elements[1].replaceAll("\\s", "");
        //System.out.println("Alert text1: " + elements[0]);
        System.out.println("Alert text2: " + elements[1]);
        driver.findElement(By.cssSelector(".t-Button--noUI")).click();
        Thread.sleep(2000);

        //driver.switchTo().defaultContent();
        driver.findElement(By.id("ok-btn")).click();
        Thread.sleep(55000);
        //End 2nd row product information


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

    public static void Scroll_Down_Loop(int x, String css) throws InterruptedException {
        Actions action = new Actions(driver);
        FindElementByCssSelector_Click(css);
        for(int i = 0; i < x; i++){
            action.sendKeys(Keys.ARROW_DOWN).build().perform();
        }
    }
    public static void FindElementByCssSelector_Click(String cssSelector){
        driver.findElement(By.cssSelector(cssSelector)).click();
    }
    public static void doubleClick(WebElement element) {
        try {
            Actions action = new Actions(driver).doubleClick(element);
            action.build().perform();

            System.out.println("Double clicked the element");
        } catch (StaleElementReferenceException e) {
            System.out.println("Element is not attached to the page document "
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            System.out.println("Element " + element + " was not found in DOM "
                    + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Element " + element + " was not clickable "
                    + e.getStackTrace());
        }
    }

}
