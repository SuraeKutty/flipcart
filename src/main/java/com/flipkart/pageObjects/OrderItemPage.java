package com.flipkart.pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class OrderItemPage {
WebDriver driver;

public OrderItemPage(WebDriver ldriver) {
   this.driver = ldriver;
   PageFactory.initElements(driver, this);
}
@FindBy(xpath = "//a[@href='/account/login?ret=/']")
WebElement loginUrl;
@FindBy(xpath = "(//input[@type='text'])[2]")
WebElement username_loginPage;
@FindBy(xpath = "//input[@type='password']")
WebElement passWord_loginPage;
@FindBy(xpath = "//span[text()='Login']/ancestor::button")
WebElement loginButton;
@FindBy(xpath = "(//input[@type='text'])[1]")
WebElement search_homePage;
@FindBy(xpath = "//div[2]/div/div/div/a/div[2]/div[1]/div[1]")
WebElement clickProduct;
@FindBy(xpath = "//div[2]/div/ul/li[1]/button")
WebElement addToCart;
@FindBy(xpath = "//a[@href='/viewcart?exploreMode=true&preference=FLIPKART']")
WebElement cartButton;
@FindBy(xpath = "//div/div[4]/div/div[2]/div[2]/div[2]")
WebElement removeFromCart;
@FindBy(xpath = "(//div[3]/div/div[2])[1]")
WebElement removeButton;
@FindBy(xpath= "//div[1]/div[2]/div[3]/div/div/div[1]/div")
WebElement logout;
@FindBy(xpath= "//a[@href='#']")
WebElement logoutHoverButton;


public void loginUrl() {
	loginUrl.click();
}

public void LoginPage() {
	//Login To Flipkart
	WebDriverWait wait =new WebDriverWait(driver, 10);
	WebElement username =wait.until(ExpectedConditions.visibilityOf(username_loginPage));
	username.sendKeys("8667837876");
	WebElement password =wait.until(ExpectedConditions.visibilityOf(passWord_loginPage));
	password.sendKeys("Secret@123");
	WebElement loginBtn =wait.until(ExpectedConditions.elementToBeClickable(loginButton));
	loginBtn.click();
}

public void OrderProduct() throws InterruptedException {
	//In search bar, look for HP laptop
	WebDriverWait wait =new WebDriverWait(driver, 20);
	Thread.sleep(4000);
	WebElement search =wait.until(ExpectedConditions.visibilityOf(search_homePage));
	search.sendKeys("HP Laptop");
	search.sendKeys(Keys.ENTER);
	
	//Once the search items are displayed, pick any one item from the list
	Thread.sleep(1500);
	WebElement product =wait.until(ExpectedConditions.elementToBeClickable(clickProduct));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);

// Handle the child window
	ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
	System.out.println("size of winows"+windowHandles.size());
	driver.switchTo().window(windowHandles.get(1));
	String hpLaptop_productName = driver.findElement(By.xpath("//h1/span")).getText();
	System.out.println(hpLaptop_productName);
	
	//Add that item to cart
	WebElement addcart =wait.until(ExpectedConditions.elementToBeClickable(addToCart));
	addcart.click();
	
	
	//Close the child window / Get back to parent window
	Thread.sleep(1500);
	driver.close();
	driver.switchTo().window(windowHandles.get(0));
	
	//Clear search bar
	WebElement search_home =wait.until(ExpectedConditions.visibilityOf(search_homePage));
	search_home.sendKeys(Keys.CONTROL + "a");
	search_home.sendKeys(Keys.DELETE);
	search_home.sendKeys("Redmi Mobile");
	search_home.sendKeys(Keys.ENTER);
	
	////Search another item - any mobile
	Thread.sleep(3000);
	product.click();
	Thread.sleep(1500);
	ArrayList<String> windowHandles1 = new ArrayList<String>(driver.getWindowHandles());
	driver.switchTo().window(windowHandles1.get(1));
	String mobile_productName = driver.findElement(By.xpath("//h1/span")).getText();
	System.out.println(mobile_productName);
	Thread.sleep(1000);
	
	
	//Once the search items are displayed, pick any one random item from the list
	addcart.click();
	driver.close();
	
	//Handle the child window
	Thread.sleep(1000);
	driver.switchTo().window(windowHandles1.get(0));
	
	//Add that item to cart
	WebElement cart =wait.until(ExpectedConditions.elementToBeClickable(cartButton));
	((JavascriptExecutor) driver).executeScript("arguments[0].click();", cart);
	
	driver.navigate().refresh();
	Thread.sleep(1000);
	List<WebElement> cartItemSize= driver.findElements(By.xpath("//div/div//a//div//img"));
	int size= cartItemSize.size();
	System.out.println("size :"+size);
	
	
	//Remove the old item(hp laptop) from the cart
	Thread.sleep(3000);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("window.scrollBy(0,400)", "");
	Thread.sleep(500);
	WebElement removeCart =wait.until(ExpectedConditions.elementToBeClickable(removeFromCart));
	removeCart.click();
	System.out.println("Remove Cart from hp laptop");
	Thread.sleep(2000);
	WebElement removeButtonInPopUp =wait.until(ExpectedConditions.elementToBeClickable(removeButton));
	removeButtonInPopUp.click();
	
}

public String validateOrder() throws InterruptedException {
//Validate if the last added item is in the cart	
	Thread.sleep(2000);
	String getTextFromCart = driver.findElement(By.xpath("//div/div[3]/div/div[1]/div[1]/div[1]/a")).getText();
    String[] text = getTextFromCart.split(" ");
    String validatetext = text[0];
	return validatetext;
	
}


public void logout() throws InterruptedException {
	
	//Logout
	Thread.sleep(2000);
	WebDriverWait wait =new WebDriverWait(driver, 10);
	WebElement logoutButton =wait.until(ExpectedConditions.visibilityOf(logout));
	Actions action = new Actions(driver);
	action.moveToElement(logoutButton).build().perform();
	WebElement logoutHoverBtn =wait.until(ExpectedConditions.visibilityOf(logoutHoverButton));
	action.moveToElement(logoutHoverBtn).click().build().perform();
}
}
