package com.Deutsche.geocode.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.Deutsche.geocode.model.ShopDetails;

public class GeoControllerServiceTest {
	@Autowired
	IGeoControllerService geoControllerService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testAddShopDetail() {

boolean addShopDetail = geoControllerService.addShopDetail("Vishakarma", 205, 342001);
    List<ShopDetails> getallShopData = geoControllerService.getallShopData();
     assertEquals(1, getallShopData.size());
	}

	@Test
	public void testGetallShopData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNearestShop() {
		fail("Not yet implemented");
	}

}
