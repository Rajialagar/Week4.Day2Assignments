package week4.day2Assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
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

public class SnapDeal {

	public static void main(String[] args) throws InterruptedException {

		/*
		 *  1. Launch https://www.snapdeal.com/
			2. Go to Mens Fashion
			3. Go to Sports Shoes
			4. Get the count of the sports shoes
			5. Click Training shoes
			6. Sort by Low to High
			7. Check if the items displayed are sorted correctly
			8.Select the price range (900-1200)
			9.Filter with color Navy 
			10 verify the all applied filters 
			11. Mouse Hover on first resulting Training shoes
			12. click QuickView button
			13. Print the cost and the discount percentage
			14. Take the snapshot of the shoes.
			15. Close the current window
			16. Close the main window
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
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();

		//Go to Men's Fashion by using mouse over action - find webelement
		driver.findElement(By.xpath("//span[contains(text(),'Men')]")).click();

		//Control move to mouse action
		Actions builder = new Actions(driver);

		//Mouse over to Sports Shoes and click
		builder.moveToElement(driver.findElement(By.xpath("(//span[text()='Sports Shoes'])[1]"))).click().perform();

		//Print the count of Sports Shoes
		String str = driver.findElement(By.xpath("//span[@class='category-name category-count']")).getText();
		System.out.println("Count of Sports shoes :" + str.replace('(', ' ').replace(')', ' '));


		//Go to Traning shoes
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();
		builder.moveToElement(driver.findElement(By.xpath("//span[text()='Sort by:']"))).click().perform();

		// explicit wait 
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		WebElement sort = driver.findElement(By.xpath("//ul[@class='sort-value']//li[2]"));
		wait.until(ExpectedConditions.elementToBeClickable(sort));

		//Sort by low to high				
		driver.findElement(By.xpath("//ul[@class='sort-value']//li[2]")).click();
		String s = driver.findElement(By.xpath("//span[@class='lfloat product-price']")).getText();
		System.out.println(s);
		Thread.sleep(2000);

		
		//Check if the items displayed are sorted correctly
		List<WebElement> price = driver.findElements(By.xpath("//SPAN[@CLASS=\"lfloat product-price\"]"));
		for (int i = 0; i < price.size()-1; i++) {

			String Price = price.get(i).getAttribute("data-price");
			String secPrice = price.get(i+1).getAttribute("data-price");
			int price1 = Integer.parseInt(Price);
			int price2 = Integer.parseInt(secPrice);
			if(price1 <= price2) {

			} else {
				System.out.println("Sort by Price Low - High is failed....Please refer the screenshot");
				//find element for mouse action
				WebElement scrollAction = driver.findElement(By.xpath("//span[text()='Yes']"));

				//Mouse action for scroll to element
				builder.scrollToElement(scrollAction).perform();
				//Step 1 - take a snapshot -local memory file
				File source = driver.getScreenshotAs(OutputType.FILE);

				//Step 2 - Save it to our local disc.
				File dest = new File("SortingFailed.png");
				try {
					FileUtils.copyFile(source, dest);
				} catch (IOException e) {

					e.printStackTrace();
				}
				break;
			}

		} 		

		//Select the price range (900-1200)
		builder.moveToElement(driver.findElement(By.xpath("//input[@name='fromVal']"))).click().perform();
		driver.findElement(By.xpath("//input[@name='fromVal']")).clear();
		driver.findElement(By.xpath("//input[@name='fromVal']")).sendKeys("500");
		driver.findElement(By.xpath("//input[@name='toVal']")).clear();
		driver.findElement(By.xpath("//input[@name='toVal']")).sendKeys("1200");
		driver.findElement(By.xpath("(//div[@class='price-text-box'])[2]/following::div")).click();
		Thread.sleep(2000);
		//	9.Filter with color Navy 
		builder.scrollToElement(driver.findElement(By.xpath("(//div[@class='filter-type-name lfloat'])[4]"))).perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='Color_s-Navy']/following-sibling::label")).click();

		//	10 verify the all applied filters
		List<WebElement> filters = driver.findElements(By.xpath("//div[@class=\"filters\"]//div"));
		System.out.println("Filters applied:  ");
		
		for (int i = 0; i < filters.size(); i++) {

			WebElement s1 = filters.get(i);
			String s2 =	s1.getText(); //WebElement to String conversion
			System.out.println(s2);
		
		}
		// Scroll to the bottom screen
		Thread.sleep(2000);
		WebElement scrollAction = driver.findElement(By.xpath("//span[text()='Yes']"));
		builder.scrollToElement(scrollAction).perform();

		//Mouse Hover on first resulting Training shoes
		WebElement firstProduct = driver.findElement(By.xpath("(//a[@class=\"dp-widget-link hashAdded\"])[1]"));
		builder.moveToElement(firstProduct).perform();
		Thread.sleep(2000);

		//click QuickView button
		driver.findElement(By.xpath("//div[@class='clearfix row-disc']/div")).click();
		Thread.sleep(2000);


		//13. Print the cost and the discount percentage
		String shoPrice = driver.findElement(By.xpath("//span[@class='payBlkBig']")).getText();
		String shoDis = driver.findElement(By.xpath("//div[@class='product-price pdp-e-i-PAY-l clearfix']//span[2]")).getText();
		System.out.println("Shoe Price: " + shoPrice);
		System.out.println("Discount: " + shoDis);

		//14. Take the snapshot of the shoes.
		//Step 1 - take a snapshot -local memory file
		File source1 = driver.getScreenshotAs(OutputType.FILE);

		//Step 2 - Save it to our local disc.
		File dest1 = new File("QuickView.png");
		try {
			FileUtils.copyFile(source1, dest1);

		} catch (IOException e) {

			e.printStackTrace();
		}
		//15. Close the current window
		driver.findElement(By.xpath("//div[@class='close close1 marR10']/i")).click();

		//16. Close the main window
		driver.close();	
	}

}
