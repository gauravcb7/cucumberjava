package stepDef;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class commonStepDef {

	public static Logger logger = Logger.getLogger(commonStepDef.class);
	protected WebDriver driver;
	private String purchaseId, purchaseAmount = null;
			
	
	 @Before
	    public void setup() {
		 //for log4j
		 DOMConfigurator.configure("log4j.xml");	        
	}
	 @Given("^Customer open URL \"([^\"]*)\"$")
	 public void customer_open_URL(String webUrl) throws Throwable {
		//Execution only in chrome browser
		 logger.info("Initilize the driver and invoke browser");
		 System.setProperty("webdriver.chrome.driver","C:\\Users\\BYO\\eclipse-workspace\\cucumberjava\\chromedriver.exe");
		 driver =new ChromeDriver();
		//Set implicit wait of 5 seconds
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.get(webUrl);
			logger.info("URL requested : " + webUrl);
			driver.manage().window().maximize();
	 }

	 @When("^Customer clicks on \"([^\"]*)\" by locator \"([^\"]*)\" and value \"([^\"]*)\"$")
	 public void customer_navigates_to_product_categories_by_locator_and_value(String name, String locatorType, String locatorValue) {
	     try {
	    	 //WebElement searchButton = driver.findElement(By.xpath(locatorValue));
	 		WebElement product = getElementByLocatorType(locatorType,locatorValue);
	 		product.click();
	 		System.out.println("Click operation performed on "+ name + " using locator type " + locatorType + " having value " + locatorValue);
	 		logger.info("Click operation performed on "+ name + " using locator type " + locatorType + " having value " + locatorValue);
	 		Thread.sleep(2000);
	     }catch(Exception e) {
	    	 logger.info("Failed to run this step due to exception : " + e.getStackTrace());
	     }
	 }
		
	 @Then("^Customer accept pop up confirmation$")
	 public void customer_accept_pop_up_confirmation() {
		 try {
			 logger.debug("Pop up window open with text" + driver.switchTo().alert().getText());
			 driver.switchTo().alert().accept();
		 }catch(Exception e) {
			 logger.info("Failed to run this step due to exception : " + e.getStackTrace());
		 }
	 }
	 
	 @Then("^Customer deletes item \"([^\"]*)\" from cart$")
	 public void customer_deletes_item_from_cart(String itemName) {
		 try {
			 String defaultXpath = "//table/tbody/tr/td[contains(text(),'toBeReplaced')]/parent::tr/td/a[contains(text(),'Delete')]";
			 String finalXpath = defaultXpath.replaceAll("toBeReplaced", itemName);
			 WebElement productToDelete = driver.findElement(By.xpath(finalXpath));
			 productToDelete.click();
			 
			Thread.sleep(2000);
			logger.info("Asset deleted from cart : " + itemName);
		 }catch(Exception e) {
			 logger.info("Failed to run this step due to exception : " + e.getStackTrace());
		 }
	 }
	 
	 @Then("^Customer fills web form with details name as \"([^\"]*)\" country as \"([^\"]*)\" city as \"([^\"]*)\" credit card as \"([^\"]*)\" month as \"([^\"]*)\" year as \"([^\"]*)\"$")
	 public void customer_fills_web_form_with_details_name_as_country_as_city_as_credit_card_as_month_as_year_as(String name, String country, String city, String credit, String mm, String yy) {
		 try {
		 //Set the name
		 WebElement eleName = driver.findElement(By.id("name"));
		 if(eleName.isEnabled())
			 eleName.sendKeys(name);
		 else
			 logger.warn("Not able to enter name " + name);
		 
		 //set the country
		 WebElement eleCountry = driver.findElement(By.id("country"));
		 if(eleCountry.isEnabled())
			 eleCountry.sendKeys(country);
		 else
			 logger.warn("Not able to enter country " + country);
		 
		//set the city
		 WebElement eleCity = driver.findElement(By.id("city"));
		 if(eleCity.isEnabled())
			 eleCity.sendKeys(city);
		 else
			 logger.warn("Not able to enter country " + city);
		 
		//set the credit card number
		 WebElement eleCredit = driver.findElement(By.id("card"));
		 if(eleCredit.isEnabled())
			 eleCredit.sendKeys(credit);
		 else
			 logger.warn("Not able to enter country " + credit);
		 
		//set the month
		 WebElement eleMonth = driver.findElement(By.id("month"));
		 if(eleMonth.isEnabled())
			 eleMonth.sendKeys(mm);
		 else
			 logger.warn("Not able to enter country " + mm);
		 
		//set the year
		 WebElement eleYear = driver.findElement(By.id("year"));
		 if(eleYear.isEnabled())
			 eleYear.sendKeys(yy);
		 else
			 logger.warn("Not able to enter country " + yy);
		 
		 Thread.sleep(1000);
		 }catch(Exception e) {
			 logger.info("Error while fillinf web form : " +  e.getStackTrace());
		 }
	 }
	 
	 
	 @Then("^Customer logs purchase infromation$")
	 public void customer_logs_purchase_infromation() {
		 try {
			 WebElement eleText = driver.findElement(By.xpath("//p[contains(text(),'Id')]"));
			 String infoText = eleText.getText();
			 logger.info("Purchase confirmation message displayed : \n" + infoText);
			 //Split based on next line
			 String[] purID = infoText.split("\\r?\\n");
			 System.out.println("Purchase ID information : "+purID[0]);
			 System.out.println("Purchase amount information : "+purID[1]);
			 logger.info("Purchase ID information : "+purID[0]);
			 logger.info("Purchase amount information : "+purID[1]);
			 //Assign to variables
			 purchaseId = purID[0];
			 purchaseAmount = purID[1];
			 
			 
		 }catch(Exception e) {
			 logger.info(" Exception occur "+ e.getStackTrace());
		 }
	 }
	 
	 @Then("^Customer verify the \"([^\"]*)\" should be equals to \"([^\"]*)\"$")
	 public void customer_verify_the_should_be_equals_to(String purAmt, String expectedAmt) {
		 logger.info("Purchase details : " + purchaseAmount);		 
		 if(purchaseAmount.contains(expectedAmt))
			 Assert.assertTrue("Purchase Value matched", true);
		 else
			 Assert.assertTrue("Purchase Value not matched", false);
	 }
	 
	 
	 
	public WebElement getElementByLocatorType(String locatorType, String locatorValue) {
		WebElement element = null;
		try {
			switch(locatorType) {
			case "xpath" : element = driver.findElement(By.xpath(locatorValue));
					break;
			case "id" : element = driver.findElement(By.id(locatorValue));
			break;
			case "css" : element = driver.findElement(By.cssSelector(locatorValue));
			break;
			case "name" : element = driver.findElement(By.name(locatorValue));
			break;
			case "className" : element = driver.findElement(By.className(locatorValue));
			break;
			default: 
                logger.error("no match: given: " + locatorType); 		
			}
			
		}catch(Exception e) {
			logger.info("Error while geting web element" + e.getStackTrace());
		}
		
		return 	element;
	}
	 @After
	 public void tearDown(Scenario scenario) {
		    if (scenario.isFailed()) {
		            final byte[] screenshot = ((TakesScreenshot) driver)
		                        .getScreenshotAs(OutputType.BYTES);
		            scenario.embed(screenshot, "image/png"); //stick it in the report
		    }
		  driver.close();
		}
	
}
