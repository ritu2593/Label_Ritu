package com.ads.formprocessor.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FordMixedLabel {
	
	@XmlElement(name = "HeaderGrossWeight")
	String HeaderGrossWeight;
	@XmlElement(name = "LoadingDate")
	String LoadingDate;
	@XmlElement(name = "ShipToParty")
	String ShipToParty;
	
	public String getHeaderGrossWeight() {
		return HeaderGrossWeight;
	}
	public void setHeaderGrossWeight(String headerGrossWeight) {
		HeaderGrossWeight = headerGrossWeight;
	}
	public String getLoadingDate() {
		return LoadingDate;
	}
	public void setLoadingDate(String loadingDate) {
		LoadingDate = loadingDate;
	}
	public String getShipToParty() {
		return ShipToParty;
	}
	public void setShipToParty(String shipToParty) {
		ShipToParty = shipToParty;
	}
	
	
	
	
}
