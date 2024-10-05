package cubes.webpages.postCategories;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostCategoriesListPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private static final String URL_PAGE = "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories";
	
	//WebElements
	@FindBy(xpath="//a[@class='btn btn-success']")
	private WebElement weAddNewCategory;
	@FindBy(xpath="//button[@class='btn btn-default']")
	private WebElement weCancleDeletePostCategory;
	@FindBy(xpath="//button[@class='btn btn-danger']")
	private WebElement weDeleteDangerPostCategory;

	@FindBy(xpath="//i[@class='far fa-user']")
	private WebElement weProfileButton;
	@FindBy(xpath="//i[@class='fas fa-sign-out-alt']")
	private WebElement weLogoutButton;
	
	public PostCategoriesListPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.driver.get(URL_PAGE);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public String checkPostCategory(String postCategoryName) {
		WebElement webElementStrong = driver.findElement(By.xpath("//strong[text()='"+postCategoryName+"']"));
		return webElementStrong.getText();
	}
	
	public boolean isPostCategoryInList(String postCategoryName) {
		boolean isInList = driver.findElements(By.xpath("//strong[text()='"+postCategoryName+"']")).size() > 0;
		
		return isInList;
	}

	public void openPage(WebDriver driver) {
		driver.get(URL_PAGE);
	}
	
	public void clickOnAddNewCategory() {
		weAddNewCategory.click();
	}
	
	public void clickOnDeletePostCategory(String postCategoryName) throws InterruptedException {
		WebElement weDeleteButton = driver.findElement(By.xpath("//strong[text()='"+postCategoryName+"']//ancestor::tr//td[6]//div//button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weDeleteButton);
		Thread.sleep(1000); // wait to scroll
		weDeleteButton.click();
	}
	
	public void clickOnDangerDeletePostCategory() {
		wait.until(ExpectedConditions.visibilityOf(weDeleteDangerPostCategory));
		weDeleteDangerPostCategory.click();
	}
	
	public void clickOnCancleDeletePostCategory() {
		wait.until(ExpectedConditions.visibilityOf(weCancleDeletePostCategory));
		weCancleDeletePostCategory.click();
	}
	
	public void clickOnUpdatePostCategory(String postCategoryName) throws InterruptedException {
		WebElement weUpdateButton = driver.findElement(By.xpath("//strong[text()='"+postCategoryName+"']//ancestor::tr//td[6]//div//a[2]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weUpdateButton);
		Thread.sleep(1000); // wait to scroll
		weUpdateButton.click();
	}
	
	public void checkNavigationLink(String title, String url) {
		WebElement weLink = driver.findElement(By.xpath("//a[text()='"+title+"']"));
		weLink.click();
		
		assertEquals(driver.getCurrentUrl(), url, "Bad URL for: " + title);
		driver.get(URL_PAGE);
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
	
	public void clickProfile() {
		weProfileButton.click();
	}
	
	public void clickLogout() {
		weLogoutButton.click();
	}
}
