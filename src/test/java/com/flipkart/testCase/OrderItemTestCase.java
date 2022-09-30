package com.flipkart.testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.flipkart.base.BaseClass;
import com.flipkart.pageObjects.OrderItemPage;

public class OrderItemTestCase extends BaseClass{
OrderItemPage order;

@Test(priority=1)
public void positiveTestCase() throws InterruptedException {
	order = new OrderItemPage(driver);
	order.LoginPage();
	order.OrderProduct();
	String actual = "REDMI 10 (Caribbean Green, 64 GB)";
	Assert.assertEquals(order.validateOrder(), actual);
	order.logout();
}
}


