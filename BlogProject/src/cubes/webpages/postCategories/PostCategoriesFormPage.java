package cubes.webpages.postCategories;

import static org.testng.Assert.assertEquals;

import java.security.SecureRandom;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostCategoriesFormPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private static final String URL_PAGE = "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories/add";
	
	//WebElements
	@FindBy(xpath="//input[@name='name']")
	private WebElement wePostCategoryName;
	@FindBy(xpath="//textarea[@name='description']")
	private WebElement wePostCategoryDescription;
	@FindBy(xpath="//button[@class='btn btn-primary']")
	private WebElement weButtonSavePostCategory;
	@FindBy(xpath="//a[@class='btn btn-outline-secondary']")
	private WebElement weButtonCancelPostCategory;
	
	public PostCategoriesFormPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.driver.get(URL_PAGE);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public void openPage(WebDriver driver) {
		driver.get(URL_PAGE);
	}
	
	public void inputPostCategoryStringName(String postCategoryName) {
		wePostCategoryName.clear();
		wePostCategoryName.sendKeys(postCategoryName);
	}
	
	public String addNewRandomPostCategoryName() {
		Random random = new Random();
		String postCategoryName = "Post Category Name " + random.nextInt(1000);
		wePostCategoryName.sendKeys(postCategoryName);
		return postCategoryName;
	}
	
	public void inputPostCategoryStringDescription(String postCategoryDescription) {
		wePostCategoryDescription.clear();
		wePostCategoryDescription.sendKeys(postCategoryDescription);
	}
	
	public String addNewRandomPostCategoryDescription(int length) {
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(length);
	    
	    for (int i = 0; i < length; i++) {
	        int index = random.nextInt(characters.length());
	        sb.append(characters.charAt(index));
	    }
	    
	    String postCategoryDescription = sb.toString();
	    wePostCategoryDescription.sendKeys(postCategoryDescription);
	    return postCategoryDescription;
	}
	
	public void clickSave() {
		weButtonSavePostCategory.click();
	}
	
	public void clickCancel() {
		weButtonCancelPostCategory.click();
	}
	
	public String getNameErrorMessage() {
		WebElement we = driver.findElement(By.id("name-error"));
		return we.getText();
	}
	
	public String getDescriptionErrorMessage() {
		WebElement we = driver.findElement(By.id("description-error"));
		return we.getText();
	}
	
	public String getErrorMessage(String error) {
		WebElement we = driver.findElement(By.xpath("//div[text()='"+error+"']"));
		return we.getText();
	}
	
	public void checkNavigationLink(String title, String url) {
		WebElement weLink = driver.findElement(By.xpath("//a[text()='"+title+"']"));
		weLink.click();
		
		assertEquals(driver.getCurrentUrl(), url, "Bad URL for: " + title);
		driver.get(URL_PAGE);
	}
}
