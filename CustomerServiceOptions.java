package week4.day2Assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;

public class CustomerServiceOptions {

	public static void main(String[] args) throws InterruptedException {

		/*
		1. Launch Salesforce application https://login.salesforce.com/
		2. Login with Provided Credentials
		3. Click on Learn More link in Mobile Publisher
		4. Clilck on Products and Mousehover on Service 
		5. Click on Customer Services
		6. Get the names Of Services Available 
		7. Verify the title	
		 */
		//Launching Browser
		WebDriverManager.chromedriver().setup();

		// Handle Browser notifications
		ChromeOptions options = new ChromeOptions();

		// Disable Notfications
		options.addArguments("--disable-notifications");

		// Launch the browser (chrome)
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://login.salesforce.com/");
		driver.manage().window().maximize();


		//Login by using the given username and pwd
		driver.findElement(By.id("username")).sendKeys("ramkumar.ramaiah@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Password#123");
		driver.findElement(By.id("Login")).click();

		// explicit wait 
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement learnMore = driver.findElement(By.xpath("//span[text()='Learn More']"));
		wait.until(ExpectedConditions.elementToBeClickable(learnMore));

		//Get parent windowHandle ID
		//String window = driver.getWindowHandle();

		//Click learn more options from Mobile publisher
		learnMore.click();

		//Get all new tab IDs in Set and change to List
		Set<String> allTabs = driver.getWindowHandles();
		List<String> allTabList = new ArrayList<String>(allTabs);

		//Get windowHandle ID for the sub window
		String subTab = allTabList.get(1);

		//Switch to the the newTab opened
		driver.switchTo().window(subTab);
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		//Get all new tab IDs in Set and change to List
		Set<String> allTabs1 = driver.getWindowHandles();
		List<String> allTabList1 = new ArrayList<String>(allTabs1);

		//Get windowHandle ID for the sub window
		String subTab1 = allTabList1.get(1);	
		Thread.sleep(2000);

		//Switch to the the newTab opened
		driver.switchTo().window(subTab1);

		Shadow dom = new Shadow(driver);
		WebElement shaElement = dom.findElementByXPath("//span[text()='Products']");
		shaElement.click();
		

		//Find element for mouse action
		WebElement operator = dom.findElementByXPath("//span[text()='Service']");

		//Control move to mouse action
		Actions builder = new Actions(driver);

		//Mouse over
		builder.moveToElement(operator).click().perform();


		//find shadow element
		WebElement salCert = dom.findElementByXPath("//a[text()='Customer Service']");

		//Mouse action - scroll to element
		builder.moveToElement(salCert).click().perform();

		Thread.sleep(2000);

		//Get all new tab IDs in Set and change to List
		Set<String> allTabs2 = driver.getWindowHandles();
		List<String> allTabList2 = new ArrayList<String>(allTabs2);

		//Get windowHandle ID for the sub window
		String subTab2 = allTabList2.get(1);	
		Thread.sleep(2000);

		//Switch to the the newTab opened
		driver.switchTo().window(subTab2);


		//Mouse Action - Find element
		WebElement custServ = driver.findElement(By.xpath("//a[text()='Customer Service']"));

		//Action move to Customer Service Details
		builder.moveToElement(custServ).perform();

		//Getting the List of services available under Customer services
		List <WebElement> lt = driver.findElements(By.xpath("//a[@class='page-list-item ']"));
		List <String> cDetails = new ArrayList<String>();

		for (int i = 0; i < lt.size(); i++) {

			WebElement s = lt.get(i);
			String s1 =	s.getText(); //WebElement to String conversion
			cDetails.add(s1);

		}
		System.out.println("CUSTOMER SERVICE DETAILS");
		cDetails.forEach(System.out::println);
		System.out.println();
		
		//verify the title of page
		String str = driver.getTitle();

		if (str.contains("Customer Service")) {
			System.out.println("Hope you are viewing the different services provided by us in this screen!!!");
		}


	}

}
