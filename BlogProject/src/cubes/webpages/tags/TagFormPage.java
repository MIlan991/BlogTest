package cubes.webpages.tags;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TagFormPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private static final String URL_PAGE = "https://testblog.kurs-qa.cubes.edu.rs/admin/tags/add";

	//WebElements
	@FindBy(name="name")
	private WebElement weTagName;
	@FindBy(xpath="//button[@type='submit']")
	private WebElement weButtonSaveTag;
	@FindBy(xpath="//a[text()='Cancel']")
	private WebElement weButtonCancel;
	@FindBy(xpath="//i[@class='far fa-user']")
	private WebElement weButtonProfile;

	public TagFormPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get(URL_PAGE);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void openPage(WebDriver driver) {
		driver.get(URL_PAGE);
	}
	
	public void addNewTag(String tagName) {
		weTagName.sendKeys(tagName);
		weButtonSaveTag.click();
	}
	
	public String addNewRandomTag() {
		Random random = new Random();
		String tagName = "Tag Name " + random.nextInt(1000);
		weTagName.sendKeys(tagName);
		weButtonSaveTag.click();
		return tagName;
	}
	
	public void checkMenuLink(String title, String url) {
		WebElement weMenu = driver.findElement(By.xpath("//p[text()='"+title+"']//ancestor::li[2]"));
		
		if(!weMenu.getAttribute("class").toString().equalsIgnoreCase("nav-item has-treeview menu-open")) {
			weMenu.click();
		}
		
		WebElement weLink = driver.findElement(By.xpath("//p[text()='"+title+"']"));
		wait.until(ExpectedConditions.visibilityOf(weLink));
		weLink.click();
		
		assertEquals(driver.getCurrentUrl(), url, "Bad URL for: " + title);
		driver.get(URL_PAGE);
	}
	
	public void checkNavigationLink(String title, String url) {
		WebElement weLink = driver.findElement(By.xpath("//a[text()='"+title+"']"));
		weLink.click();
		
		assertEquals(driver.getCurrentUrl(), url, "Bad URL for: " + title);
		driver.get(URL_PAGE);
	}
	
	public void inputTagString(String tagName) {
		weTagName.clear();
		weTagName.sendKeys(tagName);
	}
	
	public void clickSave() {
		weButtonSaveTag.click();
	}
	
	public void clickCancel() {
		weButtonCancel.click();
	}
	
	public void clickProfile() {
		weButtonProfile.click();
	}
	
	public void clickLogout() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='fas fa-sign-out-alt']")));
		driver.findElement(By.xpath("//i[@class='fas fa-sign-out-alt']")).click();
	}
	
	public String getErrorMessage() {
		WebElement we = driver.findElement(By.id("name-error"));
		return we.getText();
	}
	
	public String getErrorMessage(String error) {
		WebElement we = driver.findElement(By.xpath("//div[text()='"+error+"']"));
		return we.getText();
	}
	
	public void openPage() {
		driver.get(URL_PAGE);
	}
}