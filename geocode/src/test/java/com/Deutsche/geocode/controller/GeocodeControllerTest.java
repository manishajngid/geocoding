package com.Deutsche.geocode.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Geocode controller test class
 * 
 * @author jangid_m
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeocodeControllerTest {
	final String BASE_URL = "http://localhost:8080/geocode";
	private MockMvc mockMvc;

	@Autowired
	private GeocodeController geocodeController;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(geocodeController)
				.build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddShopData() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
								.post(BASE_URL + "/addShopDetail")
								.contentType(MediaType.APPLICATION_JSON)
								.content(getShopDataAsJsonString())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).

				andExpect(status().isOk());
	}

	@Test
	public void testAddShopDataInvalid() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
								.post(BASE_URL + "/addShopDetail")
								.contentType(MediaType.APPLICATION_JSON)
								.content(getShopDataAsJsonStringInvalid())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).

				andExpect(status().isBadRequest());
	}

	@Test
	public void testGetNearestShopInvalid() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
								.post(BASE_URL + "/getNearestShop")
								.contentType(MediaType.APPLICATION_JSON)
								.content(getCustomerDataAsJsonStringInvalid())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void testGetNearestShop() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
								.post(BASE_URL + "/getNearestShop")
								.contentType(MediaType.APPLICATION_JSON)
								.content(getCustomerDataAsJsonString())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	private static String getShopDataAsJsonString() {
		return "{\"shopName\":\"Delhi-shop\", \"shopAddress\":{\"number\":44,\"postCode\":	110092 }}";

	}

	private static String getCustomerDataAsJsonString() {
		return "{\"longitude\":74.77,\"latitiude\":18.76}";

	}

	private static String getShopDataAsJsonStringInvalid() {
		return "{\"shopName\":\"Delhi-shop\", \"shopAddress\":{\"number\":44,\"postCode\":	0 }}";

	}

	private static String getCustomerDataAsJsonStringInvalid() {
		return "{\"longitude\":74.77,\"latitiude\":0}";

	}
}
