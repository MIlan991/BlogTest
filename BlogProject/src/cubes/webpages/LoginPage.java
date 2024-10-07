package cubes.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	private static final String URL_PAGE = "http://testblog.kurs-qa.cubes.edu.rs/login";
	//WebElements
	@FindBy(name="email")
	private WebElement weEmail;
	@FindBy(name="password")
	private WebElement wePassword;
	@FindBy(xpath="//button[@type='submit']")
	private WebElement weButton;
	@FindBy(xpath="//p[@id='email-error']")
	private WebElement weEmailError;
	@FindBy(xpath="//p[@id='description-error']")
	private WebElement wePasswordError;
	@FindBy(xpath="//div[@class='invalid-feedback']//child::strong")
	private WebElement weInvalidError;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get(URL_PAGE);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void openPage() {
		driver.get(URL_PAGE);
	}
	
	public void loginSuccess() {
		inputStringEmail("kursqa@cubes.edu.rs");
		inputStringPassword("cubesqa");
		clickOnSignIn();
	}
	
	public void login(String email, String password) {
		inputStringEmail(email);
		inputStringPassword(password);
		clickOnSignIn();
	}
	
	public void inputStringEmail(String email) {
		weEmail.clear();
		weEmail.sendKeys(email);
	}
	
	public void inputStringPassword(String password) {
		wePassword.clear();
		wePassword.sendKeys(password);
	}
	
	public void clickOnSignIn() {
		weButton.click();
	}
	
	public String getEmailInputError() {
		return weEmailError.getText();
	}
	
	public String getPasswordInputError() {
		return wePasswordError.getText();
	}
	
	public String getInvalidError() {
		return weInvalidError.getText();
	}
}