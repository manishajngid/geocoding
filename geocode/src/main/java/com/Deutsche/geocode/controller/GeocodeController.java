package com.Deutsche.geocode.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Deutsche.geocode.GeocodeApplication;
import com.Deutsche.geocode.exceptions.InvalidDataException;
import com.Deutsche.geocode.exceptions.NoRegisteredShopException;
import com.Deutsche.geocode.exceptions.ServiceInvocationException;
import com.Deutsche.geocode.model.ShopDetails;
import com.Deutsche.geocode.service.IGeoControllerService;

/**
 * @author jangid_m Controller for geocode application
 */
@RestController
@RequestMapping("/geocode")
public class GeocodeController {
	private transient static final Logger LOG = Logger.getLogger(GeocodeApplication.class);

	@Autowired
	private IGeoControllerService geoControllerService;

	/**
	 * Rest service which returns list of all added shop details.
	 * 
	 * @return list <ShopDetails>
	 */
	@RequestMapping(value = "/", method = { RequestMethod.GET })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ShopDetails> index() {
		LOG.debug("Returning all shop data ");
		return geoControllerService.getallShopData();
	}

	/**
	 * Rest call to add shop details
	 * 
	 * @path /geocode/addShopDetail
	 * @requesttype post input json for reference {"shopName":"Delhi-shop",
	 *              "shopAddress":{ "number":44,"postCode": 110092 }} returns
	 *              status message/error message
	 * @return
	 */
	@RequestMapping(value = "/addShopDetail", method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<ShopDetailRes>  addShopData(@RequestBody ShopDetailReq shopDetailreq) {
		LOG.info("Input value === " + shopDetailreq);
		validateShopDetailInput(shopDetailreq);
		boolean addShopDetail = geoControllerService.addShopDetail(shopDetailreq.getName(),
				shopDetailreq.getShopAddress().getNumber(), shopDetailreq.getShopAddress().getPostCode());
		String msg = null;
		if (addShopDetail) {
			msg = "Shop details added successfgully";
		} else {
			msg = "Shop details not added. Please try again ";
		}
		return new ResponseEntity<ShopDetailRes> (new ShopDetailRes(msg),HttpStatus.OK) ;
	}
	

	/**
	 * Rest call to get nearest shop
	 * 
	 * @path /geocode/getNearestShop
	 * @requesttype post input json for
	 *              reference{"longitude":74.77,"latitude":18.76} returns status
	 *             returns empty shopDetails object if no shop is registered yet.
	 * @return
	 */
	@RequestMapping(value = "/getNearestShop", method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE, }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public  ResponseEntity<ShopDetails> getNearestShop(@RequestBody CustomerPositionReq customerPosition) {
		LOG.info("Input value === " + customerPosition);
		validateCustomerData(customerPosition);
		ShopDetails nearestShop = geoControllerService.getNearestShop(customerPosition);
		return new ResponseEntity<ShopDetails> (nearestShop,HttpStatus.OK);
	}

	/*
	 * Mandatory fields :- ShopName, shop Number , post code null check & empty
	 * check throws InvalidDataException with proper error message if any
	 * validation fails
	 */
	private void validateShopDetailInput(ShopDetailReq shopDetailreq) {
		String errorMsg = "";
		if (shopDetailreq.getName() == null || shopDetailreq.getName().length() == 0) {
			errorMsg = "::::: Invalid input ::::: Shop name is either null or empty";
		}
		if (shopDetailreq.getShopAddress() == null) {
			errorMsg += errorMsg.length() == 0 ? "::::: Invalid input ::::: Shop Address is not provided" : " And Shop Address is not provided";
		} else {
			if (shopDetailreq.getShopAddress().getNumber() == 0) {
				errorMsg += errorMsg.length() == 0 ? "::::: Invalid input ::::: Shop number is not provided" : " And Shop number is not provided";
			}
			if (shopDetailreq.getShopAddress().getPostCode() == 0) {
				errorMsg += errorMsg.length() == 0 ? "::::: Invalid input ::::: Shop postalcode is not provided or invalid"
						: " And Shop postalcode is not provided or invalid";
			}
		}

		if (errorMsg.length() != 0) {
			throw new InvalidDataException(errorMsg);
		}
	}

	/*
	 * Mandatory fields :- latitude, longitude null check & empty check throws
	 * InvalidDataException with proper error message if any validation fails
	 */
	private void validateCustomerData(CustomerPositionReq customerPosition) {
		String errorMsg = "";
		if (null == customerPosition) {
			errorMsg = "::::: Invalid input ::::: No customer data in request ";
		} else {
			if (customerPosition.getLatitiude() == 0) {
				errorMsg += errorMsg == null ? "::::: Invalid input ::::: Latitude data not valid" : " And Latitude data not valid";
			}
			if (customerPosition.getLongitude() == 0) {
				errorMsg += errorMsg == null ? "::::: Invalid input ::::: Longitude data not valid" : " And Longitude data not valid";
			}
		}

		if (errorMsg.length() != 0) {
			throw new InvalidDataException(errorMsg);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDataException.class)
	public ShopDetailRes error(HttpServletRequest req, InvalidDataException ex) {

		return new ShopDetailRes(ex.getMessage());
	}

	@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
	@ExceptionHandler(ServiceInvocationException.class)
	public ShopDetailRes error(HttpServletRequest req, ServiceInvocationException ex) {

		return new ShopDetailRes(ex.getMsg());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoRegisteredShopException.class)
	public ResponseEntity<ShopDetails> error(HttpServletRequest req, NoRegisteredShopException ex) {

		return new ResponseEntity<ShopDetails>(HttpStatus.NOT_FOUND);
	}


}
