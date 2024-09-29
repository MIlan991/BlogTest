package cubes.webpages.tags;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TagListPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private static final String URL_PAGE = "https://testblog.kurs-qa.cubes.edu.rs/admin/tags";
	
	//Webelements
	@FindBy(xpath="//a[@class='btn btn-success']")
	private WebElement weAddNewTag;
	@FindBy(xpath="//button[@class='btn btn-danger']")
	private WebElement weButtonDeleteDangerTag;
	@FindBy(xpath="//button[@class='btn btn-default']")
	private WebElement weCancelDeleteTag;
	@FindBy(name="name")
	private WebElement weUpdateNameTag;
	@FindBy(xpath="//button[@class='btn btn-primary']")
	private WebElement weSaveUpdateTag;
	@FindBy(xpath="//a[@class='btn btn-outline-secondary']")
	private WebElement weCancelUpdateTag;

	public TagListPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.driver.get(URL_PAGE);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void openPage(WebDriver driver) {
		driver.get(URL_PAGE);
	}
	
	public void deleteTag(String tagName) throws InterruptedException {
		WebElement weDeleteButton = driver.findElement(By.xpath("//strong[text()='"+tagName+"']//ancestor::tr//td[5]//div//button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weDeleteButton);
		Thread.sleep(1000); // wait to scroll
		weDeleteButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn-danger']")));
		driver.findElement(By.xpath("//button[@class='btn btn-danger']")).click();
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
	
	public String checkTag(String tagName) {
		WebElement webElementStrong = driver.findElement(By.xpath("//strong[text()='"+tagName+"']"));
		return webElementStrong.getText();
	}
	
	public boolean isTagInList(String tagName) {
		boolean isInList = driver.findElements(By.xpath("//strong[text()='"+tagName+"']")).size() > 0;
		
		return isInList;
	}
	
	public void clickOnAddNewTag() {
		weAddNewTag.click();
	}
	
	public void clickOnDeleteTag(String tagName) throws InterruptedException {
		WebElement weDeleteButton = driver.findElement(By.xpath("//strong[text()='"+tagName+"']//ancestor::tr//td[5]//div//button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weDeleteButton);
		Thread.sleep(1000); // wait to scroll
		weDeleteButton.click();
	}
	
	public void clickOnUpdateTag(String tagName) throws InterruptedException {
		WebElement weUpdateButton = driver.findElement(By.xpath("//strong[text()='"+tagName+"']//ancestor::tr//td[5]//div//a[2]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weUpdateButton);
		Thread.sleep(1000); // wait to scroll
		weUpdateButton.click();
	}
	
	public void clickOnCancelDeleteTag() {
		wait.until(ExpectedConditions.visibilityOf(weCancelDeleteTag));
		weCancelDeleteTag.click();
	}
	
	public void clickButtonDeleteDangerTag() {
		wait.until(ExpectedConditions.visibilityOf(weButtonDeleteDangerTag));
		weButtonDeleteDangerTag.click();
	}
	
	public void clickOnSaveUpdateTag() {
		wait.until(ExpectedConditions.visibilityOf(weSaveUpdateTag));
		weSaveUpdateTag.click();
	}
	
	public void clickOnCancelUpdateTag() {
		wait.until(ExpectedConditions.visibilityOf(weCancelUpdateTag));
		weCancelUpdateTag.click();
	}
}