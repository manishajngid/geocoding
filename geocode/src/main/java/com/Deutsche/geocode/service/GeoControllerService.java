package com.Deutsche.geocode.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.Deutsche.geocode.latlang.ILatLang;
import com.Deutsche.geocode.model.ShopDetails;

@Service("geoControllerService")
public class GeoControllerService implements IGeoControllerService {
	private transient static final Logger LOG = Logger.getLogger(GeoControllerService.class);

	@Autowired
	@Qualifier("latlang")
	ILatLang latlang;

	@PostConstruct
	private void getDataFromDisk() {
		LOG.info("GeoControllerService: initialization: getting shopdetails data from disk");
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./shopList"))) {
			shopDetailsList = (List<ShopDetails>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LOG.info("Not able to load shop detail list from disk");
		}
	}

	private List<ShopDetails> shopDetailsList = new LinkedList<ShopDetails>();

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
			storeIntoDisk(shopDetailsList);
		}
		return status;
	}

	private void storeIntoDisk(List<ShopDetails> shopDetailsList2) {
		LOG.info("GeoControllerService: Storing shopdetails data into disk");
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./shopList"))) {
			;
			objectOutputStream.writeObject(shopDetailsList);
		} catch (IOException e) {
			LOG.info("Not able to write shop detail list to disk" + e);
		}
	}

	@Override
	public List<ShopDetails> getallShopData() {
		// TODO Auto-generated method stub
		return shopDetailsList;
	}

}
