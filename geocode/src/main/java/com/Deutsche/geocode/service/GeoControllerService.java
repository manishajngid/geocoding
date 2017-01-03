package com.Deutsche.geocode.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.Deutsche.geocode.controller.CustomerPositionReq;
import com.Deutsche.geocode.exceptions.NoRegisteredShopException;
import com.Deutsche.geocode.latlang.ILatLang;
import com.Deutsche.geocode.model.ShopDetails;

/**
 * Geo controller Service cLASS
 * 
 * @author jangid_m
 *
 */
@Service("geoControllerService")
public class GeoControllerService implements IGeoControllerService {
	private transient static final Logger LOG = Logger.getLogger(GeoControllerService.class);

	@Autowired
	@Qualifier("latlang")
	ILatLang latlang;

	/**
	 * in memory list to hold registered shops details
	 */
	private List<ShopDetails> shopDetailsList = new LinkedList<ShopDetails>();

	/**
	 * To avoid, adding shop details every time after restart.this method is
	 * written It loads shop details data from serialized object if available on
	 * mentioned path during initialization
	 */
	@PostConstruct
	private void getDataFromDisk() {
		LOG.info("GeoControllerService: initialization: getting shopdetails data from disk");
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./shopList"))) {
			shopDetailsList = (List<ShopDetails>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LOG.info("Not able to load shop detail list from disk");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.Deutsche.geocode.service.IGeoControllerService#addShopDetail(java.
	 * lang.String, int, int)
	 */
	public boolean addShopDetail(String shopName, int shopNum, int postCode) {
		boolean status = true;
		LOG.info("ShopName == " + shopName + "Shop Number == " + shopNum + " postalcode == " + postCode);
		ShopDetails shopDetails = new ShopDetails();
		shopDetails.setShopName(shopName);
		shopDetails.setShopNumber(shopNum);
		shopDetails.setPostalCode(postCode);
		if (!shopDetailsList.contains(shopDetails)) {
			double[] latlangFromPostCode = latlang.getLatlangFromPostCode(postCode);
			shopDetails.setLatitude(latlangFromPostCode[0]);
			shopDetails.setLongitude(latlangFromPostCode[1]);
			shopDetailsList.add(shopDetails);
			LOG.info("Following shop added into list : " + shopDetails);
			storeIntoDisk();
		}
		return status;
	}

	/**
	 * Serializes shop details list
	 * 
	 * @param shopDetailsList2
	 */

	private void storeIntoDisk() {
		LOG.info("GeoControllerService: Storing shopdetails data into disk");
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./shopList"))) {
			;
			objectOutputStream.writeObject(shopDetailsList);
		} catch (IOException e) {
			LOG.info("Not able to write shop detail list to disk" + e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.Deutsche.geocode.service.IGeoControllerService#getallShopData()
	 */
	@Override
	public List<ShopDetails> getallShopData() {
		return shopDetailsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.Deutsche.geocode.service.IGeoControllerService#getNearestShop(com.
	 * Deutsche.geocode.controller.CustomerPositionReq)
	 */
	@Override
	public ShopDetails getNearestShop(CustomerPositionReq customerPosition) {
		if (null == shopDetailsList || shopDetailsList.size() == 0) {
			LOG.warn("Empty shop details list throwing NoRegisteredShopException ");
			throw new NoRegisteredShopException();
		}
		int currIndex = 0;
		double smallestDistence = Integer.MAX_VALUE;
		int smallestindex = 0;
		for (ShopDetails shopDetails : shopDetailsList) {
			double delx = shopDetails.getLatitude() - customerPosition.getLatitiude();
			double dely = shopDetails.getLongitude() - customerPosition.getLongitude();
			double dist = delx * delx + dely * dely;
			LOG.debug(shopDetails + " dist ==" + dist + "smallest distence == " + smallestDistence + "curr index == "
					+ currIndex);
			if (dist < smallestDistence) {
				smallestDistence = dist;
				smallestindex = currIndex;
			}
			currIndex++;
		}
		return shopDetailsList.get(smallestindex);
	}

}
