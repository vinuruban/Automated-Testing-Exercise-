package com.qa.PassionateTeaCount;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.qa.PassionateTeaCount.ExcelUtils;
import com.qa.PassionateTeaCount.Constants;
import com.qa.PassionateTeaCount.TeaCheckOutPage;
import com.qa.PassionateTeaCount.TeaHomePage;
import com.qa.PassionateTeaCount.TeaMenuPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.*;
import cucumber.api.java.en.*;

public class TeaSteps {
	
	WebDriver driver = null;
	public static ExtentReports report;
	public ExtentTest test; 
		
	@Before 
	public void setup() {
		ExcelUtils.setExcelFile(Constants.pathTestData + Constants.fileTestData, 0);
		System.setProperty(Constants.driverType, Constants.driverLocation);
		report = TeaRunner.report;
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@Given("^the correct web address$")
	public void the_correct_web_address() {
	    // Write code here that turns the phrase above into concrete actions
		Constants.count++;
		test = report.startTest("Scenario" + Constants.count);
		driver.get(Constants.websiteURL);
		test.log(LogStatus.INFO, "Tea Homepage Opened"); //new
	}

	@When("^I navigate to the 'Menu' page$")
	public void i_navigate_to_the_Menu_page() {
	    // Write code here that turns the phrase above into concrete actions
		TeaHomePage homePage = PageFactory.initElements(driver, TeaHomePage.class);
		homePage.clickMenu();
		test.log(LogStatus.INFO, "Navigation to Menu page");
	}

	@Then("^I can browse a list of the available products\\.$")
	public void i_can_browse_a_list_of_the_available_products() throws InterruptedException {
		
		TeaMenuPage menuPage = PageFactory.initElements(driver, TeaMenuPage.class);
		
		String teaOption = menuPage.getMenuText().getText();
		String gt = "Green Tea";
		if(teaOption.equals(gt)) { //new
			test.log(LogStatus.PASS, "Available product exists"); 
			ExcelUtils.setCellData("Pass", 1, Constants.count);
		}
		else { 
			test.log(LogStatus.FAIL, "Available product doesn't exist"); //new
			ExcelUtils.setCellData("Fail", 1, Constants.count);
		}
		assertEquals(gt, teaOption);
		Thread.sleep(1000);
	}

	@When("^I click the checkout button$")
	public void i_click_the_checkout_button() {
	    // Write code here that turns the phrase above into concrete actions
		TeaHomePage homePage = PageFactory.initElements(driver, TeaHomePage.class);
		homePage.clickCheckOut();
		test.log(LogStatus.INFO, "Navigation to Checkout page");
	}

	@Then("^I am taken to the checkout page$")
	public void i_am_taken_to_the_checkout_page() throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
	TeaCheckOutPage checkOutPage = PageFactory.initElements(driver, TeaCheckOutPage.class);
		
		String checkText = checkOutPage.getCheckOutText().getText();
		String txt = "Enter your billing information";
		if(checkText.equals(txt)) {
			test.log(LogStatus.PASS, "Successfully taken to the checkoutpage");
			ExcelUtils.setCellData("Pass", 1, Constants.count);
		}
		else { 
			test.log(LogStatus.FAIL, "Cannot browse to the checkoutpage"); //new
			ExcelUtils.setCellData("Fail", 1, Constants.count);
		}
		assertEquals(txt, checkText);
		Thread.sleep(1000);
	}
	
	@After
	public void tearDown() throws InterruptedException {
		Thread.sleep(3000);
		report.endTest(test);
		driver.quit();
		report.flush();
	}
	
}
