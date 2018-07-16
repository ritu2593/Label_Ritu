package com.ads.formprocessor.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FordMixedMasterLabel {
	
	@XmlElement(name = "ActualDeliveryQuantity")
	String ActualDeliveryQuantity;
	@XmlElement(name = "DeliveryQuantityUnit")
	String DeliveryQuantityUnit;
	@XmlElement(name = "ItemGrossWeight")
	String ItemGrossWeight;
	@XmlElement(name = "LoadingDate")
	String LoadingDate;
	@XmlElement(name = "Batch")
	String Batch;
	@XmlElement(name = "MaterialbyCustomer")
	String MaterialbyCustomer;
	@XmlElement(name = "DeliveryDocument")
	String DeliveryDocument;
	@XmlElement(name = "ShipToParty")
	String ShipToParty;
	
	public String getActualDeliveryQuantity() {
		return ActualDeliveryQuantity;
	}
	public void setActualDeliveryQuantity(String actualDeliveryQuantity) {
		ActualDeliveryQuantity = actualDeliveryQuantity;
	}
	public String getDeliveryQuantityUnit() {
		return DeliveryQuantityUnit;
	}
	public void setDeliveryQuantityUnit(String deliveryQuantityUnit) {
		DeliveryQuantityUnit = deliveryQuantityUnit;
	}
	public String getItemGrossWeight() {
		return ItemGrossWeight;
	}
	public void setItemGrossWeight(String itemGrossWeight) {
		ItemGrossWeight = itemGrossWeight;
	}
	public String getLoadingDate() {
		return LoadingDate;
	}
	public void setLoadingDate(String loadingDate) {
		LoadingDate = loadingDate;
	}
	public String getBatch() {
		return Batch;
	}
	public void setBatch(String batch) {
		Batch = batch;
	}
	public String getMaterialbyCustomer() {
		return MaterialbyCustomer;
	}
	public void setMaterialbyCustomer(String materialbyCustomer) {
		MaterialbyCustomer = materialbyCustomer;
	}
	public String getDeliveryDocument() {
		return DeliveryDocument;
	}
	public void setDeliveryDocument(String deliveryDocument) {
		DeliveryDocument = deliveryDocument;
	}
	public String getShipToParty() {
		return ShipToParty;
	}
	public void setShipToParty(String shipToParty) {
		ShipToParty = shipToParty;
	}
	
	
	
	
}
