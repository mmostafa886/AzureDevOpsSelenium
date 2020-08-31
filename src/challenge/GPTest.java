package challenge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class GPTest {

	public WebDriver driver;

	// =======================================================
	@BeforeTest
	public void beforeTest() throws Exception {
		
		System.out.println("@BeforeTest:Test Initialization");
		
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("user-data-dir=Libs/User Data");/*provide the User profile directory	(To start the session with Gmail account saved to the browser)*/
		//options.addArguments("no-sandbox");
		
		System.setProperty("webdriver.chrome.driver", "Libs/chromedriver.exe");//setting "Chrome Driver" path
		
		driver = new ChromeDriver();//Starting an instance from Chrome driver

		driver.get("https://play.google.com/");//Navigate to the google play store URL
		Thread.sleep(100);
	}

	// =======================================================
	@AfterTest
	public void afterTest() {
		System.out.println("@AfterTest:Test Teardown");
		driver.quit();//Killing the used driver instance
	}
	// =======================================================
	@Test(priority = 1)
	public void WihshListAdding()  {//Selecting the First & Last images and them to the WishList 
		System.out.println("Test1:Adding Games To WishList");

	}
		// =======================================================
	@Test(priority = 2  )
	public void InstallFromWishList() throws Exception {//Installing one of the games in the wishlist
		System.out.println("Test2:Install a Game from The WishList");

	}
//---------------------------------------------------


	// =======================================================
	@Test(priority = 3)
	public void ManipulateReview() throws Exception { // Adding & Deleting a review for the just installed game
		System.out.println("Test3:Review Manipulation");

	}

}
