package week4.day2Assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;



public class AdministratorCertifications {

	public static void main(String[] args) throws InterruptedException, IOException {

		/*
		 * 	1. Launch Salesforce application https://login.salesforce.com/
			2. Login with username as "ramkumar.ramaiah@testleaf.com " and password as "Password$123"
			3. Click on Learn More link in Mobile Publisher
			4. Click confirm on Confirm redirect
			5. Click Resources and mouse hover on Learning On Trailhead
			6. Clilck on Salesforce Certifications
			6. Click on Ceritification Administrator
			7. Navigate to Certification - Administrator Overview window
			8. Verify the Certifications available for Administrator Certifications should be displayed
		 */

		//Launching Browser
		WebDriverManager.chromedriver().setup();


		// Handle Browser notifications
		ChromeOptions options = new ChromeOptions();

		// Notfications
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
		//	String window = driver.getWindowHandle();

		//Click learn more options from Mobile publisher
		driver.findElement(By.xpath("//span[text()='Learn More']")).click();

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
		WebElement shaElement = dom.findElementByXPath("//span[text()='Learning']");
		shaElement.click();

		//Find element for mouse action
		WebElement operator = dom.findElementByXPath("//span[text()='Learning on Trailhead']");

		//Control move to mouse action
		Actions builder = new Actions(driver);

		//Mouse over
		builder.moveToElement(operator).click().perform();

		//find shadow element
		WebElement salCert = dom.findElementByXPath("//a[text()='Salesforce Certification']");

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

		//find element for mouse action
		WebElement mouseAdmin = driver.findElement(By.xpath("//a[text()='Administrator']"));

		//Mouse action - move to element
		builder.moveToElement(mouseAdmin).click().perform();

		//Get all new tab IDs in Set and change to List
		Set<String> allTabs3 = driver.getWindowHandles();
		List<String> allTabList3 = new ArrayList<String>(allTabs3);

		//Get windowHandle ID for the sub window
		String subTab3 = allTabList3.get(1);	
		Thread.sleep(2000);

		//Switch to the the newTab opened
		driver.switchTo().window(subTab3);

		//find element for mouse action
		WebElement scrollAction = driver.findElement(By.xpath("//div[@class='trailMix-card-content']"));

		//Mouse action for scroll to element
		builder.scrollToElement(scrollAction).perform();

		//Step 1 - take a snapshot -local memory file
		File source = driver.getScreenshotAs(OutputType.FILE);

		//Step 2 - Save it to our local disc.
		File dest = new File("certification.png");
		try {
			FileUtils.copyFile(source, dest);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
