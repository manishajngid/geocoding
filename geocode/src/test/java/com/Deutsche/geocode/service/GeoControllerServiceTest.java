package com.Deutsche.geocode.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.Deutsche.geocode.controller.CustomerPositionReq;
import com.Deutsche.geocode.exceptions.NoRegisteredShopException;
import com.Deutsche.geocode.latlang.ILatLang;
import com.Deutsche.geocode.model.ShopDetails;

public class GeoControllerServiceTest {
	@Autowired
	IGeoControllerService geoControllerService;
	ILatLang latlangMock;
	double[] coordinates1 = new double[] { 1, 1 };
	double[] coordinates2 = new double[] { 2, 2 };
	double[] coordinates3 = new double[] { 3, 3 };
	double[] coordinates4 = new double[] { 4, 4 };

	@Before
	public void setUp() throws Exception {
		latlangMock = Mockito.mock(ILatLang.class);
		geoControllerService = new GeoControllerService(latlangMock);
		Mockito.when(latlangMock.getLatlangFromPostCode(342001, new Boolean(true))).thenReturn(coordinates1);
		Mockito.when(latlangMock.getLatlangFromPostCode(411045, new Boolean(true))).thenReturn(coordinates2);
		Mockito.when(latlangMock.getLatlangFromPostCode(411021, new Boolean(true))).thenReturn(coordinates3);
		Mockito.when(latlangMock.getLatlangFromPostCode(110001, new Boolean(true))).thenReturn(coordinates4);

		geoControllerService.addShopDetail("Vishakarma", 205, 342001);
		geoControllerService.addShopDetail("krishna", 93, 411021);
		geoControllerService.addShopDetail("D-Mart", 205, 411045);
		geoControllerService.addShopDetail("bikaner sweets", 101, 110001);
	}

	@After
	public void tearDown() throws Exception {
		geoControllerService.getallShopData().clear();
	}

	@Test
	public void testAddShopDetail() {
		geoControllerService.getallShopData().clear();
		boolean addShopDetail = geoControllerService.addShopDetail("Vishakarma", 205, 342001);
		ShopDetails expected = new ShopDetails("Vishakarma", 205, 342001, 5, 5);
		assertEquals(addShopDetail, true);
		List<ShopDetails> getallShopData = geoControllerService.getallShopData();
		assertEquals(1, getallShopData.size());
		assertEquals(expected, getallShopData.get(0));
	}

	@Test
	public void testGetallShopData() {

		List<ShopDetails> getallShopData = geoControllerService.getallShopData();
		assertEquals(4, getallShopData.size());

	}

	@Test
	public void testGetNearestShop() {
		CustomerPositionReq customerPositionReq = new CustomerPositionReq();
		customerPositionReq.setLatitiude(7);
		customerPositionReq.setLongitude(7);
		ShopDetails expected = new ShopDetails("bikaner sweets", 101, 110001, 4, 4);
		ShopDetails nearestShop = geoControllerService.getNearestShop(customerPositionReq);
		assertEquals(expected, nearestShop);
	}

	@Test(expected = NoRegisteredShopException.class)
	public void testGetNearestShopWithEmptyShopList() {
		CustomerPositionReq customerPositionReq = new CustomerPositionReq();
		customerPositionReq.setLatitiude(7);
		customerPositionReq.setLongitude(7);
		ShopDetails expected = new ShopDetails("bikaner sweets", 101, 110001, 4, 4);
		geoControllerService.getallShopData().clear();
		ShopDetails nearestShop = geoControllerService.getNearestShop(customerPositionReq);
		assertEquals(expected, nearestShop);
	}
}
