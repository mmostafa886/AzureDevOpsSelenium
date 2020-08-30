package challenge;

import org.testng.annotations.Test;

import au.com.bytecode.opencsv.CSVReader;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import java.io.FileReader;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;

public class GPTest {

	public WebDriver driver;

	// =======================================================
	@BeforeTest
	public void beforeTest() throws Exception {
		
		System.out.println("@BeforeTest:Test Initialization");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=Libs/User Data");/*provide the User profile directory 
		(To start the session with Gmail account saved to the browser)*/
		options.addArguments("no-sandbox");
		
		System.setProperty("webdriver.chrome.driver", "Libs/chromedriver.exe");//setting "Chrome Driver" path
		
		driver = new ChromeDriver(options);//Starting an instance from Chrome driver

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
	public void WihshListAdding() throws Exception {//Selecting the First & Last images and them to the WishList 
		System.out.println("Test1:Adding Games To WishList");
		GPLogic GPL = new GPLogic(driver);
		GPL.AddGamesToWishList();
	}
		// =======================================================
	@Test(priority = 2  , dataProvider = "PWordDataProvider")
	public void InstallFromWishList(String pass) throws Exception {//Installing one of the games in the wishlist
		System.out.println("Test2:Install a Game from The WishList");
		GPLogic GPL = new GPLogic(driver);
		GPL.WishListInstall(pass);
	}
//---------------------------------------------------
	@DataProvider(name = "PWordDataProvider")/*Provide the Password (that is required to be confirmed during 
											the game installation procedure) from a CSV file*/
	 public Object[][] PWordDataProvider() throws Exception
	 {
		CSVReader readPass = new CSVReader(new FileReader("Libs/PWord.csv"));
		/*Provide the directory to the CSV file in which the password is saved
		 * The file will be empty & the user has to fill it with used password
		 	*/
		List<String[]> list = readPass.readAll();
		String[][] array = new String[list.size()][];
		for(int i=0;i<list.size();i++)
		{
		array[i] = list.get(i);
			}
		return array;
	 }


	// =======================================================
	@Test(priority = 3)
	public void ManipulateReview() throws Exception { // Adding & Deleting a review for the just installed game
		System.out.println("Test3:Review Manipulation");
		GPLogic GPL = new GPLogic(driver);
		GPL.ReviewManipulate();

	}

}
