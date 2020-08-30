package challenge;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class GPLogic {

	// =========Declarations=========
	public WebDriver driver;
	public WebDriverWait wait;
	public List<WebElement> Games; /*Will be used later for holding the total
									 * number of games in the games page (to select the first & Last)
									 */
	// =========Constructor=========
	public GPLogic(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 30, 100);
	}

	// =========Locators=========
	/*
	 * Define all the locators that will be used all over the test here
	 * */
	By AppsTab = By.xpath("//span[@class='title' and text() = ' Apps ']");
	By GamesTab = By.xpath("//a[@class='sub-nav-link id-no-menu-change' and text() = ' Games ']");
	By _GameLocator = By.xpath("//span[@class='preview-overlay-container']");
	By _1stGame = By.xpath("//*[@id='body-content']/div/div/div[1]/div[2]/div[1]/div/div[2]/div[1]/div/a");
	By LastGame = By.xpath("//*[@id='body-content']/div/div/div[1]/div[2]/div[1]/div/div[2]/div[6]/div/a");
	By OverLay = By.xpath("//div[@class='body-content-loading-overlay']");
	By PreRegBtn = By.xpath("//button[contains(. , 'Pre-register')]");
	By AddToWList = By.xpath("//span[@class='lmqfOc nSYH7e' and text() = 'Add to Wishlist']");
	By AddedToWL = By.xpath("//span[@class='lmqfOc VmgRE' and text() = 'Added to Wishlist']");
	By WLRemove = By.xpath("//span[@class='lmqfOc oSB8de' and text() = 'Remove']");
	By GamesTab2 = By.xpath("//a[text() = 'Games']");
	By MyWishList = By.xpath("//*[@id='wrapper']/div[1]/div/div/ul/li[5]/a");
	By WLstGame = By.xpath("//div[1]/div[@class='card-content id-track-click id-track-impression']");
	By InstallBtn = By.xpath("//button[text() = 'Install']");
	By InstalledBtn = By.xpath("//button[text() = 'Installed']");
	By GID_Pwd = By.xpath("//input[@type='password']");
	By GID_NxtBtn = By.xpath("//div[@id='passwordNext']");
	By GID_VTC = By.xpath("//div[@class='ck6P8']");
	By DeviceSelect = By.xpath("//div[@id='device-selector-container']");
	By stDevice = By.xpath("//div[@class='device-selector-dropdown-children']");
	By DeviceList = By.xpath("//button[contains(@class,'device-selector-dropdown')]");
	By PurchaseOkBtn = By.id("purchase-ok-button");
	By InstallClsBtn = By.xpath("//button[@id='close-dialog-button']");
	By WriteReviewBtn = By.xpath("//span[@class='G4lal']");
	By ReviewStar5 = By.xpath(
			"//div[@class='review-row star-rating-row-desktop']/div/div/div/div/span[@class='fifth-star star-common']");
	By ReviewText = By.tagName("textarea");
	By ReviewSubmtBtn = By.xpath("//button[@class='id-submit-review play-button apps']");
	By MyReview = By.xpath("//*[@class ='Rm6Gwb' and contains(.,'My review')]");
	By ReviewDelBtn = By.xpath("//div[@data-tooltip='Delete review']");
	By PurchaseDetails = By.xpath("//div[@class='purchase-details']");
	By PurchaseFram = By.xpath("//*/div[not(@id='gbsfw')]/iframe");
	By ReviewFram = By.xpath("//div[@id='glass-content']/iframe");
	By ReviewDetails = By.xpath("//div[@class='review-main']");
	By PurchaseCancelBtn = By.xpath("//button[@id='purchase-cancel-button']");

	// ===========================
	/*
	 * It is repeated more than once to have an overlay (loading form)on top of the page
	 * It may or may not be displayed (based on the loading speed)
	 * so I have created a single method that can be used wherever we need in the Test
	 * */
	public void overlayWait() throws Exception {
		if (!driver.findElements(OverLay).isEmpty()) {
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(OverLay)));
		}

		else {
			Thread.sleep(500);
		}
	}
	// ===========================

	/*This Method Conatins:
	 * Navigating to the "Games" Page
	 * Add the First & Last Games to the WishList*/
	public void AddGamesToWishList() throws Exception {
		
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		
		wait.until(ExpectedConditions.elementToBeClickable(AppsTab));
		driver.findElement(AppsTab).click();//Navigate to Apps Tab

		this.overlayWait();
		wait.until(ExpectedConditions.elementToBeClickable(GamesTab));
		driver.findElement(GamesTab).click();//Navigate to Games Tab

		this.overlayWait();
		//Telling the Webdriver instance to wait until all the games are loaded in the page 
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(_GameLocator));
		Games = driver.findElements(_GameLocator);

		int Length = Games.size();//Getting the total number of games in the page
		int StImgNum = 0;
		WebElement stimg = Games.get(StImgNum);//Getting the first game WebElement
		System.out.println("The Page Conatins: " + Length + " Games");

		jsExecutor.executeScript("arguments[0].click();", stimg); //Click on the game element to open it

		while (driver.findElements(AddToWList).isEmpty()) {
			/*At some cases, the "Add To Wishlist" link is not displayed
			 * in this block we iterate until reaching the first game having it so that it can be used
			 * */
			driver.navigate().back();
			this.overlayWait();
			StImgNum++;
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(_GameLocator));
			Games = driver.findElements(_GameLocator);
			WebElement ndimg = Games.get(StImgNum);
			jsExecutor.executeScript("arguments[0].click();", ndimg);
		}

		wait.until(ExpectedConditions.elementToBeClickable(AddToWList));
		driver.findElement(AddToWList).click();//Add the game to the Wishlist
		/* The next 3 lines are used to make sure that
		 * ,The selected game was added to the Wishlist */
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(WLRemove)));
		jsExecutor.executeScript("window.scrollBy(0 , 100)");
		Assert.assertTrue(!driver.findElements(AddedToWL).isEmpty());

		Thread.sleep(100);
		driver.navigate().back();//Back to the Games Page

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(_GameLocator));
		Games = driver.findElements(_GameLocator);
		int Length2 = Games.size();
		int ImNum = Length2 - 1;
		WebElement lstimg = Games.get(ImNum);//Getting the last game element in the games page
		System.out.println("The Page Conatins: " + Length2 + " Games");
		jsExecutor.executeScript("arguments[0].click();", lstimg);

		while (driver.findElements(AddToWList).isEmpty()) {
			//Iteration to add the last game having "Add To Wishlist" to the wishlist
			driver.navigate().back();
			this.overlayWait();
			ImNum--;
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(_GameLocator));
			Games = driver.findElements(_GameLocator);
			WebElement b4lstimg = Games.get(ImNum);
			jsExecutor.executeScript("arguments[0].click();", b4lstimg);
		}

		wait.until(ExpectedConditions.elementToBeClickable(AddToWList));
		driver.findElement(AddToWList).click();//add to the Wishlist
		/* The next 3 lines are used to make sure that
		 * ,The selected game was added to the Wishlist */
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(WLRemove)));
		jsExecutor.executeScript("window.scrollBy(0 , 100)");
		Assert.assertTrue(!driver.findElements(AddedToWL).isEmpty());
		
		driver.navigate().back();
		Thread.sleep(200);
	}

	// ===========================
	/*This method Contains:
	 * Navigate to the WishList Page
	 * Select the First Game
	 * Continue in the installation of the selected game until being installed
	 *  */
	public void WishListInstall(String Pword) throws Exception {
		
		wait.until(ExpectedConditions.elementToBeClickable(MyWishList));
		driver.findElement(MyWishList).click();//Navigate to the WishList Page
		this.overlayWait();

		wait.until(ExpectedConditions.elementToBeClickable(WLstGame));
		driver.findElement(WLstGame).click();//Select the first game in WishList
		this.overlayWait();

		/*In some cases, the game is displaying "Install" or "Installed" at the game installation button
		 * This block of code is supposed to handle both cases*/
		WebElement InstBtn;
		if (!driver.findElements(InstalledBtn).isEmpty()) {
			InstBtn = driver.findElement(InstalledBtn);
		} else {
			InstBtn = driver.findElement(InstallBtn);
			Thread.sleep(100);
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(InstBtn));
		InstBtn.click();//Click on the "Install" or "Installed" button (According to what is displayed in our case)

		this.overlayWait();
		/*This block is for handling the Password confirmation dialogue by the playstore*/
		wait.until(ExpectedConditions.elementToBeClickable(GID_Pwd));
		driver.findElement(GID_Pwd).click();
		driver.findElement(GID_Pwd).clear();
		driver.findElement(GID_Pwd).sendKeys(Pword);
		wait.until(ExpectedConditions.elementToBeClickable(GID_NxtBtn));
		driver.findElement(GID_NxtBtn).click();
		this.overlayWait();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(GID_VTC)));
		this.overlayWait();
		Thread.sleep(100);

		/*Thsi block is for handling WebElements lies in frames other than the default one*/
		int size = driver.findElements(By.tagName("iframe")).size();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));
		System.out.println("Total Frames --" + size);
		driver.switchTo().frame(driver.findElement(PurchaseFram));

		wait.until(ExpectedConditions.elementToBeClickable(PurchaseDetails));
		driver.findElement(PurchaseDetails).click();//Click on "Purchase Details" modal to activate it
		Thread.sleep(100);
		
		//Selecting the first device from the devices' list
		wait.until(ExpectedConditions.elementToBeClickable(DeviceSelect));
		driver.findElement(DeviceSelect).click();
		Thread.sleep(100);
		wait.until(ExpectedConditions.elementToBeClickable(stDevice));
		driver.findElement(stDevice).click();
		
		//Click on the Install button
		wait.until(ExpectedConditions.elementToBeClickable(PurchaseOkBtn));
		driver.findElement(PurchaseOkBtn).click();
		
		//Click on the installation completed button
		wait.until(ExpectedConditions.elementToBeClickable(InstallClsBtn));
		driver.findElement(InstallClsBtn).click();

		Thread.sleep(200);
	}

	// ===========================
	/*This Method Contains:
	 * Writing a review for the installed game
	 * Delete the added Review*/
	public void ReviewManipulate() throws Exception {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		driver.navigate().refresh();//The "Write Review" button is not displayed until the page being refreshed
		this.overlayWait();
		Thread.sleep(100);
		// this.waitForpageLoad(driver);

		int size = driver.findElements(By.tagName("iframe")).size();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));//Wait untill loaing all the farmes
		System.out.println("Total Frames --" + size);
		driver.switchTo().frame(driver.findElement(PurchaseFram));

		/*This Block was used for frames tracing
		 * JavascriptExecutor jsExecutor = (JavascriptExecutor)driver; Object
		 * currentFrame = jsExecutor.executeScript("return self.name");
		 * System.out.println(currentFrame);
		 * 
		 * size = driver.findElements(By.tagName("iframe")).size();
		 */
		// --------------------
		/*The purchase details modal is displayed once the game page is refreshed
		 * This block is for canceling this modal*/
		wait.until(ExpectedConditions.elementToBeClickable(PurchaseDetails));
		driver.findElement(PurchaseDetails).click();
		Thread.sleep(100);
		wait.until(ExpectedConditions.elementToBeClickable(PurchaseCancelBtn));
		driver.findElement(PurchaseCancelBtn).click();
		// --------------------
		wait.until(ExpectedConditions.elementToBeClickable(WriteReviewBtn));
		driver.findElement(WriteReviewBtn).click();//Click on the "Write Review" button
		this.overlayWait();
		Thread.sleep(100);
		// --------------------
		/*Activating the review modal in order to start providing a review*/
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));
		driver.switchTo().frame(driver.findElement(ReviewFram));
		wait.until(ExpectedConditions.elementToBeClickable(ReviewDetails));
		driver.findElement(ReviewDetails).click();
		Thread.sleep(100);
		// --------------------
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(ReviewStar5));//Provide 5 starts ranking

		wait.until(ExpectedConditions.elementToBeClickable(ReviewText));
		driver.findElement(ReviewText).sendKeys("Test");//Provide text of "Test" in the review text area

		wait.until(ExpectedConditions.elementToBeClickable(ReviewSubmtBtn));
		driver.findElement(ReviewSubmtBtn).click();//Submitting the review
		
		/*This  block is used for deleting the review & making sure it is already deleted*/
		wait.until(ExpectedConditions.presenceOfElementLocated(MyReview));
		driver.findElement(ReviewDelBtn).click();
		//wait.until(ExpectedConditions.presenceOfElementLocated(WriteReviewBtn));
		Assert.assertFalse(driver.findElements(WriteReviewBtn).isEmpty());
		Thread.sleep(1500);

	}
}
