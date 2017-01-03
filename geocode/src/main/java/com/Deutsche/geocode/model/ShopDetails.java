/**
 * 
 */
package com.Deutsche.geocode.model;

import java.io.Serializable;

/**
 * Model for shop details 
 * @author jangid_m
 */
public class ShopDetails implements Serializable {

	private static final long serialVersionUID = 1L;
private String shopName;
private int shopNumber ;
private int postalCode;

public void setShopName(String shopName) {
	this.shopName = shopName;
}
public int getShopNumber() {
	return shopNumber;
}
public void setShopNumber(int shopNumber) {
	this.shopNumber = shopNumber;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + postalCode;
	result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
	result = prime * result + shopNumber;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ShopDetails other = (ShopDetails) obj;
	if (postalCode != other.postalCode)
		return false;
	if (shopName == null) {
		if (other.shopName != null)
			return false;
	} else if (!shopName.equals(other.shopName))
		return false;
	if (shopNumber != other.shopNumber)
		return false;
	return true;
}
public int getPostalCode() {
	return postalCode;
}
public void setPostalCode(int postalCode) {
	this.postalCode = postalCode;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
private double longitude;
private double latitude ;

@Override
public String toString() {
	return "ShopDetails [shopName=" + shopName + ", shopNumber=" + shopNumber + ", postalCode=" + postalCode
			+ ", longitude=" + longitude + ", latitude=" + latitude + "]";
}
}
