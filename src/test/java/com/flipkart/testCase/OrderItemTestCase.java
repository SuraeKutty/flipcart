package com.flipkart.testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.flipkart.base.BaseClass;
import com.flipkart.pageObjects.OrderItemPage;

public class OrderItemTestCase extends BaseClass{
OrderItemPage order;

@Test(priority=0)
public void positiveTestCase() throws InterruptedException {
	log = reports.createTest("Validate Phone is present in the Cart Page");
	order = new OrderItemPage(driver);
	order.LoginPage();
	order.OrderProduct();
//	Thread.sleep(2000);
	String actual = "REDMI";
	Assert.assertEquals(order.validateOrder(), actual);
	order.logout();
}
}


