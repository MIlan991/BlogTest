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
import cubes.webpages.postCategories.PostCategoriesFormPage;
import cubes.webpages.postCategories.PostCategoriesListPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPostCategories {
	
	private static ChromeDriver driver;

	private static LoginPage loginPage;
	private static PostCategoriesListPage postCategoriesListPage;
	private static PostCategoriesFormPage postCategoriesFormPage;
	
	private static String postCategoryName;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Users/chile91/Desktop/webdriver/chromedriver");
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		
		loginPage = new LoginPage(driver);
		postCategoriesListPage = new PostCategoriesListPage(driver, wait);
		postCategoriesFormPage = new PostCategoriesFormPage(driver, wait);
		
		loginPage.loginSuccess();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		postCategoriesListPage.openPage(driver);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tc01AddEmptyPostCategory() {
		postCategoriesListPage.clickOnAddNewCategory();
		postCategoriesFormPage.inputPostCategoryStringName("");
		postCategoriesFormPage.inputPostCategoryStringDescription("");
		postCategoriesFormPage.clickSave();
		
		String errorMessageName = postCategoriesFormPage.getNameErrorMessage();
		String errorMessageDescription = postCategoriesFormPage.getDescriptionErrorMessage();
		
		assertEquals("This field is required.", errorMessageName);
		assertEquals("This field is required.", errorMessageDescription);
	}

	@Test
	public void tc02AddOnlyPostCategoryName() {
		postCategoriesListPage.clickOnAddNewCategory();
		postCategoriesFormPage.addNewRandomPostCategoryName();
		postCategoriesFormPage.inputPostCategoryStringDescription("");
		postCategoriesFormPage.clickSave();
		
		String errorMessageDescription = postCategoriesFormPage.getDescriptionErrorMessage();
		assertEquals("This field is required.", errorMessageDescription);
	}

	@Test
	public void tc03AddOnlyPostCategoryDescription() {
		postCategoriesListPage.clickOnAddNewCategory();
		postCategoriesFormPage.inputPostCategoryStringName("");
		postCategoriesFormPage.addNewRandomPostCategoryDescription(50);
		postCategoriesFormPage.clickSave();

		String errorMessageName = postCategoriesFormPage.getNameErrorMessage();
		assertEquals("This field is required.", errorMessageName);
	}

	@Test
	public void tc04AddPostCategoryDescriptionLessThan50Characters() {
		postCategoriesListPage.clickOnAddNewCategory();
		postCategoriesFormPage.addNewRandomPostCategoryName();
		postCategoriesFormPage.addNewRandomPostCategoryDescription(49);
		postCategoriesFormPage.clickSave();

		String errorMessage = postCategoriesFormPage.getErrorMessage("The description must be at least 50 characters.");
		
		assertEquals("The description must be at least 50 characters.", errorMessage);
	}

	@Test
	public void tc05CancelAddPostCategory() {
		postCategoriesListPage.clickOnAddNewCategory();
		postCategoriesFormPage.addNewRandomPostCategoryName();
		postCategoriesFormPage.addNewRandomPostCategoryDescription(50);
		postCategoriesFormPage.clickCancel();
		
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories");
	}

	@Test
	public void tc06AddPostCategorySuccess() {
		postCategoriesListPage.clickOnAddNewCategory();
		postCategoryName = postCategoriesFormPage.addNewRandomPostCategoryName();
		postCategoriesFormPage.addNewRandomPostCategoryDescription(50);
		postCategoriesFormPage.clickSave();
		
		postCategoriesListPage.openPage(driver);
		
		String expectedPostCategoryName = postCategoriesListPage.checkPostCategory(postCategoryName);
		assertEquals(postCategoryName, expectedPostCategoryName);
	}

	@Test
	public void tc07CancelUpdatePostCategoryName() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory(postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringName(postCategoryName + " Update");
		postCategoriesFormPage.clickCancel();
		
		boolean checkPostCategory = postCategoriesListPage.isPostCategoryInList(postCategoryName);
		assertEquals(true, checkPostCategory);
	}

	@Test
	public void tc08EmptyUpdatePostCategory() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory(postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringName("");
		postCategoriesFormPage.clickSave();
		
		String errorMessage = postCategoriesFormPage.getNameErrorMessage();
		assertEquals("This field is required.", errorMessage);
	}

	@Test
	public void tc09UpdateExistingNameUpdatePostCategory() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory(postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringName("Default Post Category NE BRISATI");
		postCategoriesFormPage.clickSave();
		
		String errorMessage = postCategoriesFormPage.getErrorMessage("The name has already been taken.");
		assertEquals("The name has already been taken.", errorMessage);
	}

	@Test
	public void tc10UpdatePostCategoryNameSuccess() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory(postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringName("UPDATED " + postCategoryName);
		postCategoriesFormPage.clickSave();

		boolean checkPostCategory = postCategoriesListPage.isPostCategoryInList("UPDATED " + postCategoryName);
		assertEquals(true, checkPostCategory);
	}

	@Test
	public void tc11UpdatePostCategoryEmptyDescription() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory("UPDATED " + postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringDescription("");
		postCategoriesFormPage.clickSave();
		
		String errorMessageDescription = postCategoriesFormPage.getDescriptionErrorMessage();
		assertEquals("This field is required.", errorMessageDescription);
	}

	@Test
	public void tc12UpdatePostCategoryLessThen50CaractersDescription() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory("UPDATED " + postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringDescription("");
		postCategoriesFormPage.addNewRandomPostCategoryDescription(49);
		postCategoriesFormPage.clickSave();

		String errorMessage = postCategoriesFormPage.getErrorMessage("The description must be at least 50 characters.");
		
		assertEquals("The description must be at least 50 characters.", errorMessage);
	}

	@Test
	public void tc13CancelUpdatePostCategoryDescription() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory("UPDATED " + postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringDescription("");
		String checkPostCategoryDesc = "UPDATE " + postCategoriesFormPage.addNewRandomPostCategoryDescription(50);
		postCategoriesFormPage.inputPostCategoryStringDescription(checkPostCategoryDesc);
		postCategoriesFormPage.clickCancel();

	    boolean checkDescriptionUpdate = checkPostCategoryDesc.isEmpty();

		assertEquals(false, checkDescriptionUpdate);
	}

	@Test
	public void tc14UpdatePostCategoryDescriptionSuccess() throws InterruptedException {
		postCategoriesListPage.clickOnUpdatePostCategory("UPDATED " + postCategoryName);
		postCategoriesFormPage.inputPostCategoryStringDescription("");
		String checkPostCategoryDesc = "UPDATE " + postCategoriesFormPage.addNewRandomPostCategoryDescription(50);
		postCategoriesFormPage.inputPostCategoryStringDescription(checkPostCategoryDesc);
		postCategoriesFormPage.clickSave();

	    boolean checkDescriptionUpdate = checkPostCategoryDesc.equals(checkPostCategoryDesc);
		assertEquals(true, checkDescriptionUpdate);
	}
	@Test
	public void tc15CancelDeletePostCategory() throws InterruptedException {
		postCategoriesListPage.clickOnDeletePostCategory("UPDATED " + postCategoryName);
		postCategoriesListPage.clickOnCancleDeletePostCategory();
		
		boolean checkPostCategory = postCategoriesListPage.isPostCategoryInList("UPDATED " + postCategoryName);
		assertEquals(true, checkPostCategory);
	}

	@Test
	public void tc16DeletePostCategory() throws InterruptedException {
		postCategoriesListPage.clickOnDeletePostCategory("UPDATED " + postCategoryName);
		postCategoriesListPage.clickOnDangerDeletePostCategory();
		
		boolean checkPostCategory = postCategoriesListPage.isPostCategoryInList("UPDATED " + postCategoryName);
		String deleteMessage = "UPDATED " + postCategoryName + " post category has been deleted";

		assertEquals(false, checkPostCategory);
		assertEquals("UPDATED " + postCategoryName + " post category has been deleted", deleteMessage);
	}
	
	@Test
	public void tc17TestLinkNavigationMenu() throws InterruptedException {
		postCategoriesListPage.checkNavigationLink("Home", "https://testblog.kurs-qa.cubes.edu.rs/admin");
		postCategoriesFormPage.openPage(driver);
		postCategoriesFormPage.checkNavigationLink("Home", "https://testblog.kurs-qa.cubes.edu.rs/admin");
		postCategoriesFormPage.openPage(driver);
		postCategoriesFormPage.checkNavigationLink("Post Categories", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories");
	}
	
	@Test
	public void tc18TestLinksFromMenu() {
		postCategoriesListPage.checkMenuLink("Sliders list", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders");
		postCategoriesListPage.checkMenuLink("Add Slider", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders/add");
		postCategoriesListPage.checkMenuLink("Post Categories list", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories");
		postCategoriesListPage.checkMenuLink("Add Post Category", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories/add");
		postCategoriesListPage.checkMenuLink("Tags list", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags");
		postCategoriesListPage.checkMenuLink("Add Tag", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags/add");
		postCategoriesListPage.checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		postCategoriesListPage.checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
		postCategoriesListPage.checkMenuLink("Comments List", "https://testblog.kurs-qa.cubes.edu.rs/admin/comments");
		postCategoriesListPage.checkMenuLink("Users List", "https://testblog.kurs-qa.cubes.edu.rs/admin/users");
		postCategoriesListPage.checkMenuLink("Add User", "https://testblog.kurs-qa.cubes.edu.rs/admin/users/add");
	}
	
	@Test
	public void tc19TestLogout() {
		postCategoriesListPage.clickProfile();
		postCategoriesListPage.clickLogout();
		
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/login");
	}
	
}
