package com.ads.formprocessor.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FordMixedMasterLoadLabel {

	@XmlElement(name = "DeliveryDocument")
	String DeliveryDocument;
	@XmlElement(name = "MaterialbyCustomer")
	String MaterialbyCustomer;
	@XmlElement(name = "ActualDeliveryQuantity")
	String ActualDeliveryQuantity;
	@XmlElement(name = "MaterialbyCustomerA")
	String MaterialbyCustomerA;
	@XmlElement(name = "ActualDeliveryQuantityA")
	String ActualDeliveryQuantityA;
	
	public String getDeliveryDocument() {
		return DeliveryDocument;
	}
	public void setDeliveryDocument(String deliveryDocument) {
		DeliveryDocument = deliveryDocument;
	}
	public String getMaterialbyCustomer() {
		return MaterialbyCustomer;
	}
	public void setMaterialbyCustomer(String materialbyCustomer) {
		MaterialbyCustomer = materialbyCustomer;
	}
	public String getActualDeliveryQuantity() {
		return ActualDeliveryQuantity;
	}
	public void setActualDeliveryQuantity(String actualDeliveryQuantity) {
		ActualDeliveryQuantity = actualDeliveryQuantity;
	}
	public String getMaterialbyCustomerA() {
		return MaterialbyCustomerA;
	}
	public void setMaterialbyCustomerA(String materialbyCustomerA) {
		MaterialbyCustomerA = materialbyCustomerA;
	}
	public String getActualDeliveryQuantityA() {
		return ActualDeliveryQuantityA;
	}
	public void setActualDeliveryQuantityA(String actualDeliveryQuantityA) {
		ActualDeliveryQuantityA = actualDeliveryQuantityA;
	}
	
	
	
		
	
	
	
}
