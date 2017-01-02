package com.Deutsche.geocode.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Deutsche.geocode.GeocodeApplication;
import com.Deutsche.geocode.exceptions.InvalidDataException;
import com.Deutsche.geocode.model.ShopDetails;
import com.Deutsche.geocode.service.IGeoControllerService;

@RestController
@RequestMapping("/geocode")
public class GeocodeController {// implements IGeocodeController {
	private transient static final Logger LOG = Logger.getLogger(GeocodeApplication.class);

	@Autowired
	private IGeoControllerService geoControllerService;

	@RequestMapping(value = "/", method = { RequestMethod.GET })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ShopDetails> index() {
		LOG.debug("Greetings from Spring Boot!");
		return geoControllerService.getallShopData();
	}

	@RequestMapping(value = "/addShopDetail", method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ShopDetailRes addShopData(@RequestBody ShopDetailReq shopDetailreq) {
		LOG.info("Input value === " + shopDetailreq);
		validateShopDetailInput(shopDetailreq);
		boolean addShopDetail = geoControllerService.addShopDetail(shopDetailreq.getName(), shopDetailreq.getShopAddress().getNumber(),
				shopDetailreq.getShopAddress().getPostCode());
		String msg = null;
		if(addShopDetail){
			msg = "Shop details added successfgully";
		}else{
			msg = "Shop details not added. Please try again ";
		}
		return new ShopDetailRes(msg);
	}
	
	



	private void validateShopDetailInput(ShopDetailReq shopDetailreq) {
		String errorMsg = null;
		if(shopDetailreq.getName() == null || shopDetailreq.getName().length() ==0){
			errorMsg = "::::: Invalid input ::::: Shop name is either null or empty";
		}
		if(shopDetailreq.getShopAddress() == null){
			errorMsg+= errorMsg == null ?"Shop Address is not provided":" And Shop Address is not provided";
		}else {
		if(shopDetailreq.getShopAddress().getNumber() == 0){
			errorMsg+= errorMsg == null ?"Shop number is not provided":" And Shop number is not provided";
		}
		if(shopDetailreq.getShopAddress().getPostCode() == 0){
			errorMsg+= errorMsg == null ?"Shop postalcode is not provided or invalid":" And Shop postalcode is not provided or invalid";
		}
		}
		
		if(null !=errorMsg){
			throw new InvalidDataException(errorMsg);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDataException.class)
	public ShopDetailRes error(HttpServletRequest req, InvalidDataException ex) {
		
		return new ShopDetailRes(ex.getMessage());
	}

}
