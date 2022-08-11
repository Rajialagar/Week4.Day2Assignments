package week4.day2Assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {

		/*
		 	1) Go to https://www.nykaa.com/
			2) Mouseover on Brands and Search L'Oreal Paris
			3) Click L'Oreal Paris
			4) Check the title contains L'Oreal Paris(Hint-GetTitle)
			5) Click sort By and select customer top rated
			6) Click Category and click Hair->Click haircare->Shampoo
			7) Click->Concern->Color Protection
			8)check whether the Filter is applied with Shampoo
			9) Click on L'Oreal Paris Colour Protect Shampoo
			10) GO to the new window and select size as 175ml
			11) Print the MRP of the product
			12) Click on ADD to BAG
			13) Go to Shopping Bag 
			14) Print the Grand Total amount
			15) Click Proceed
			16) Click on Continue as Guest
			17) Check if this grand total is the same in step 14
			18) Close all windows
		 */
		//Launching Browser
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		//Control move to mouse action
		Actions builder = new Actions(driver);

		//Mouse over to Brands
		builder.moveToElement(driver.findElement(By.xpath("//a[text()='brands']"))).perform();

		//Search L'Oreal Paris
		builder.moveToElement(driver.findElement(By.xpath("//a[@href='/brands/loreal-paris/c/595?eq=desktop']"))).click().perform();

		// Title verification
		if(driver.getTitle().contains("L'Oreal Paris")){

			System.out.println("Title verification Success!!!");

		}
		//Select Customer Top Rated in Sort by option
		driver.findElement(By.xpath("//span[contains(text(), 'Sort By')]")).click();
		driver.findElement(By.xpath("//span[text()='customer top rated']")).click();
		driver.findElement(By.xpath("//span[text()='Category']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Hair']")).click();
		driver.findElement(By.xpath("//span[text()='Hair Care']")).click();
		driver.findElement(By.xpath("//span[text()='Shampoo']")).click();

		builder.scrollToElement(driver.findElement(By.xpath("//span[text()='Concern']"))).perform();
		driver.findElement(By.xpath("//span[text()='Concern']")).click();
		builder.scrollToElement(driver.findElement(By.xpath("//span[text()='Color Protection']"))).perform();
		driver.findElement(By.xpath("//span[text()='Color Protection']")).click();

		//Verify the filter is applied

		List<WebElement> filter = driver.findElements(By.xpath("//span[@class='filter-value']"));

		for (int i = 0; i < filter.size(); i++) {

			WebElement s1 = filter.get(i);
			String s2 =	s1.getText(); //WebElement to String conversion

			if(s2.equals("Shampoo")){
				System.out.println("Filter applied for Shampoo");			
			} else if(s2.equals("Color Protection")){
				System.out.println("Filter applied for Color Protection");
			}
		}
		// select L'Oreal Paris Colour Protect Shampoo
		driver.findElement(By.xpath("//div[@class='css-xrzmfa']")).click();

		//Get all new tab IDs in Set and change to List
		Set<String> allTabs = driver.getWindowHandles();
		List<String> allTabList = new ArrayList<String>(allTabs);

		//Get windowHandle ID for the sub window
		String subTab = allTabList.get(1);	
		Thread.sleep(2000);

		//Switch to the the newTab opened
		driver.switchTo().window(subTab);

		//Select dropdown list by using selectByIndex
		WebElement dropdown = driver.findElement(By.xpath("//select[@title='SIZE']"));
		Select select = new Select(dropdown);
		select.selectByValue("0");

		//Get MRP price for the 175ml
		String price = driver.findElement(By.xpath("//span[@class='css-1jczs19']")).getText();
		System.out.println("MRP price: "+ price);

		driver.findElement(By.xpath("//span[text()='Add to Bag']")).click();

		// explicit wait 
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement cart = driver.findElement(By.xpath("//span[@class='cart-count']"));
		wait.until(ExpectedConditions.elementToBeClickable(cart));

		driver.findElement(By.xpath("//span[@class='cart-count']")).click();
		Thread.sleep(2000);
		//Switch to the frame
		driver.switchTo().frame(0);

		String str1 = driver.findElement(By.xpath("(//span[text()='Grand Total'])[1]/following::div[1]")).getText();
		System.out.println("The Grand Total amount is :" + str1);

		//Click to proceed
		driver.findElement(By.xpath("//span[text()='Proceed']")).click();

		//Get all new tab IDs in Set and change to List
		Set<String> allTabs1 = driver.getWindowHandles();
		List<String> allTabList1 = new ArrayList<String>(allTabs1);

		//Get windowHandle ID for the sub window
		String subTab1 = allTabList1.get(1);	
		Thread.sleep(2000);
		
		//Switch to the the newTab opened
		driver.switchTo().window(subTab1);

		driver.findElement(By.xpath("//button[text()='CONTINUE AS GUEST']")).click();
		//Get all new tab IDs in Set and change to List
		Set<String> allTabs2 = driver.getWindowHandles();
		List<String> allTabList2 = new ArrayList<String>(allTabs2);

		//Get windowHandle ID for the sub window
		String subTab2 = allTabList1.get(1);	
		Thread.sleep(2000);

		//Switch to the the newTab opened
		driver.switchTo().window(subTab2);

		String str2 = driver.findElement(By.xpath("(//div[@class='value'])[2]")).getText();

		//Check the grand total from previous screen
		if (str1.equals(str2)) {
			System.out.println("Grand Total matches with the previous screen");
		}
		driver.quit();
		
	}
}


