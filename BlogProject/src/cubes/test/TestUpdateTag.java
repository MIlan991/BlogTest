package cubes.test;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import cubes.webpages.LoginPage;
import cubes.webpages.tags.TagFormPage;
import cubes.webpages.tags.TagListPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUpdateTag {
	
	private static ChromeDriver driver;
	private static LoginPage loginPage;
	private static TagFormPage tagFormPage;
	private static TagListPage tagListPage;
	
	private static String tagName;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Users/chile91/Desktop/webdriver/chromedriver");
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		
		loginPage = new LoginPage(driver);
		tagFormPage = new TagFormPage(driver);
		tagListPage = new TagListPage(driver, wait);

		loginPage.loginSuccess();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		tagListPage.openPage(driver);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void tc1UpdateEmptyTag() throws InterruptedException {
		tagListPage.clickOnAddNewTag();
		
		tagName = tagFormPage.addNewRandomTag();
		tagListPage.clickOnUpdateTag(tagName);
		tagFormPage.inputTagString("");
		tagListPage.clickOnSaveUpdateTag();

		String errorMessage = tagFormPage.getErrorMessage();
		assertEquals("This field is required.", errorMessage);
	}
	
	@Test
	public void tc2CheckCanceledTag() throws InterruptedException {
		tagListPage.clickOnUpdateTag(tagName);
		tagListPage.clickOnCancelUpdateTag();
		String checkCanceledTag = tagListPage.checkTag(tagName);

		assertEquals(true, tagListPage.isTagInList(checkCanceledTag));
	}
	
	@Test
	public void tc3UpdateTagWithExistingName() throws InterruptedException {
		tagListPage.clickOnUpdateTag(tagName);
		tagFormPage.inputTagString("Default TAG NE BRISATI");
		tagListPage.clickOnSaveUpdateTag();
		
		String errorMessage = tagFormPage.getErrorMessage("The name has already been taken.");
		assertEquals("The name has already been taken.", errorMessage);
	}
	
	@Test
	public void tc4ChangeUpdateWithExistingTag() throws InterruptedException {
		tagListPage.clickOnUpdateTag(tagName);
		tagFormPage.inputTagString(tagName + " Updated");
		
		tagListPage.clickOnSaveUpdateTag();
		
		String newUpdatedTag = tagName + " Updated";
		assertEquals(true, tagListPage.isTagInList(newUpdatedTag));
	}

}
