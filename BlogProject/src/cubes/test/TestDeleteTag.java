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

import cubes.webpages.tags.TagListPage;
import cubes.webpages.LoginPage;
import cubes.webpages.tags.TagFormPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteTag {
	
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
	public void tc1TestCancelDeleteTag() throws InterruptedException {
		tagListPage.clickOnAddNewTag();
		
		tagName = tagFormPage.addNewRandomTag();
		tagListPage.clickOnDeleteTag(tagName);
		tagListPage.clickOnCancelDeleteTag();
		
		String checkNewTag = tagListPage.checkTag(tagName);
		assertEquals(tagName, checkNewTag);
	}
	
	@Test
	public void tc2TestDeleteTag() throws InterruptedException {
		tagListPage.clickOnDeleteTag(tagName);
		tagListPage.clickButtonDeleteDangerTag();

		boolean checkIfTagIsDeleted = tagListPage.isTagInList(tagName);
		String deleteMessage = tagName + " tag has been deleted";
		
		assertEquals(false, checkIfTagIsDeleted);
		assertEquals(tagName + " tag has been deleted", deleteMessage);
	}
	
	@Test
	public void tc3TestLinkFormMenu() {
		tagListPage.checkMenuLink("Sliders list", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders");
		tagListPage.checkMenuLink("Add Slider", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders/add");
		tagListPage.checkMenuLink("Post Categories list", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories");
		tagListPage.checkMenuLink("Add Post Category", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories/add");
		tagListPage.checkMenuLink("Tags list", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags");
		tagListPage.checkMenuLink("Add Tag", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags/add");
		tagListPage.checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		tagListPage.checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
		tagListPage.checkMenuLink("Comments List", "https://testblog.kurs-qa.cubes.edu.rs/admin/comments");
		tagListPage.checkMenuLink("Users List", "https://testblog.kurs-qa.cubes.edu.rs/admin/users");
		tagListPage.checkMenuLink("Add User", "https://testblog.kurs-qa.cubes.edu.rs/admin/users/add");
	}

}