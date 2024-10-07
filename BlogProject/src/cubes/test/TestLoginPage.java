package cubes.test;

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
public class TestLoginPage {
	
	private static ChromeDriver driver;
	private static WebDriverWait wait;
	
	private static LoginPage loginPage;
	
	private final String EMAIL_VALID = "kursqa@cubes.edu.rs";
	private final String EMAIL_INVALID = "example@email123.rs";
	private final String EMAIL_INCORECT_FORMAT = "abcabc";
	private final String PASSWORD_VALID = "cubesqa";
	private final String PASSWORD_INVALID = "abcabc";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Users/chile91/Desktop/webdriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		
		loginPage = new LoginPage(driver);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		loginPage.openPage();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tc03LoginWithEmptyFields() throws InterruptedException {
		loginPage.inputStringEmail("");
		loginPage.inputStringPassword("");
		loginPage.clickOnSignIn();
		
		String emailErrorMessage = loginPage.getEmailInputError();
		String passwordErrorMessage = loginPage.getPasswordInputError();
		
		assertEquals(emailErrorMessage, "Email* is required field");
		assertEquals(passwordErrorMessage, "Password* is required field");
	}

	@Test
	public void tc04LoginWithIncorectEmailFormat() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_INCORECT_FORMAT);
		loginPage.inputStringPassword("");
		loginPage.clickOnSignIn();
		
		String emailErrorMessage = loginPage.getEmailInputError();
		String passwordErrorMessage = loginPage.getPasswordInputError();
		
		assertEquals(emailErrorMessage, "Please, enter the valid Email address");
		assertEquals(passwordErrorMessage, "Password* is required field");
	}

	@Test
	public void tc05LoginWithInvalidEmailFormat() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_INVALID);
		loginPage.inputStringPassword("");
		loginPage.clickOnSignIn();
		
		String passwordErrorMessage = loginPage.getPasswordInputError();

		assertEquals(passwordErrorMessage, "Password* is required field");
	}

	@Test
	public void tc06LoginWithValidEmailFormat() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_VALID);
		loginPage.inputStringPassword("");
		loginPage.clickOnSignIn();
		
		String passwordErrorMessage = loginPage.getPasswordInputError();

		assertEquals(passwordErrorMessage, "Password* is required field");
	}

	@Test
	public void tc07LoginWithEmptyEmailAndInvalidPassword() throws InterruptedException {
		loginPage.inputStringEmail("");
		loginPage.inputStringPassword(PASSWORD_INVALID);
		loginPage.clickOnSignIn();
		
		String emailErrorMessage = loginPage.getEmailInputError();
		
		assertEquals(emailErrorMessage, "Email* is required field");
	}

	@Test
	public void tc08LoginWithEmptyEmailAndCorectPassword() throws InterruptedException {
		loginPage.inputStringEmail("");
		loginPage.inputStringPassword(PASSWORD_VALID);
		loginPage.clickOnSignIn();
		
		String emailErrorMessage = loginPage.getEmailInputError();
		
		assertEquals(emailErrorMessage, "Email* is required field");
	}

	@Test
	public void tc09LoginWithIncorectEmailFormatAndInvalidPassword() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_INCORECT_FORMAT);
		loginPage.inputStringPassword(PASSWORD_INVALID);
		loginPage.clickOnSignIn();
		
		String emailErrorMessage = loginPage.getEmailInputError();
		
		assertEquals(emailErrorMessage, "Please, enter the valid Email address");
	}

	@Test
	public void tc10LoginWithInvalidEmailFormatAndInvalidPassword() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_INVALID);
		loginPage.inputStringPassword(PASSWORD_INVALID);
		loginPage.clickOnSignIn();
		
		String invalidErrorMessage = loginPage.getInvalidError();
		
		assertEquals(invalidErrorMessage, "These credentials do not match our records.");
	}

	@Test
	public void tc11LoginWithValidEmailFormatAndInvalidPassword() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_VALID);
		loginPage.inputStringPassword(PASSWORD_INVALID);
		loginPage.clickOnSignIn();
		
		String invalidErrorMessage = loginPage.getInvalidError();
		
		assertEquals(invalidErrorMessage, "These credentials do not match our records.");
	}

	@Test
	public void tc12LoginWithIncorectEmailFormatAndInvalidPassword() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_INCORECT_FORMAT);
		loginPage.inputStringPassword(PASSWORD_VALID);
		loginPage.clickOnSignIn();
		
		String emailErrorMessage = loginPage.getEmailInputError();
		
		assertEquals(emailErrorMessage, "Please, enter the valid Email address");
	}

	@Test
	public void tc13LoginWithInvalidEmailFormatAndValidPassword() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_INVALID);
		loginPage.inputStringPassword(PASSWORD_VALID);
		loginPage.clickOnSignIn();
		
		String invalidErrorMessage = loginPage.getInvalidError();
		
		assertEquals(invalidErrorMessage, "These credentials do not match our records.");
	}

	@Test
	public void tc14LoginWithValidEmailFormatAndValidPassword() throws InterruptedException {
		loginPage.inputStringEmail(EMAIL_VALID);
		loginPage.inputStringPassword(PASSWORD_VALID);
		loginPage.clickOnSignIn();
		
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin");
	}

}
 