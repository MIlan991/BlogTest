package cubes.test;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import cubes.webpages.tags.TagListPage;
import cubes.webpages.LoginPage;
import cubes.webpages.tags.TagFormPage;

public class TestLoginPage {
	
	private static ChromeDriver driver;
	private static WebDriverWait wait;
	
	private static LoginPage loginPage;
	private static TagFormPage tagFormPage;
	private static TagListPage tagListPage;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Users/chile91/Desktop/webdriver/chromedriver");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		
		loginPage = new LoginPage(driver);
		tagFormPage = new TagFormPage(driver);
		tagListPage = new TagListPage(driver, wait);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddAndDeleteTag() throws InterruptedException {
		loginPage.loginSuccess();
		tagFormPage.openPage(driver);
		tagFormPage.addNewTag("Test Tag Selenium");
		tagListPage.deleteTag("Test Tag Selenium");
	}

}
 