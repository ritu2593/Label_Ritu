package com.ads.formprocessor.rest;

import java.util.List;

/**
 * @author jdhabarw
 *
 */
public class OutBoundDeliveryDU {
	
	String DeliveryDocument;
	String DocumentDate;
	String HeaderGrossWeight;
	String HeaderWeightUnit;
	String OverallPackingStatus;
	String OverallPickingStatus;
	String OverallWarehouseActivityStatus;
	String PickingDate;
	String ReceivingPlant;
	String ShippingPoint;
	String ShipToParty;
	String WarehouseGate;
	String versionIdentifier;
	List<OutBoundDeliveryItemDU> oOutBDelItem;
	
	public String getDeliveryDocument() {
		return DeliveryDocument;
	}
	public void setDeliveryDocument(String deliveryDocument) {
		DeliveryDocument = deliveryDocument;
	}
	public String getDocumentDate() {
		return DocumentDate;
	}
	public void setDocumentDate(String documentDate) {
		DocumentDate = documentDate;
	}
	public String getHeaderGrossWeight() {
		return HeaderGrossWeight;
	}
	public void setHeaderGrossWeight(String headerGrossWeight) {
		HeaderGrossWeight = headerGrossWeight;
	}
	public String getHeaderWeightUnit() {
		return HeaderWeightUnit;
	}
	public void setHeaderWeightUnit(String headerWeightUnit) {
		HeaderWeightUnit = headerWeightUnit;
	}
	public String getOverallPackingStatus() {
		return OverallPackingStatus;
	}
	public void setOverallPackingStatus(String overallPackingStatus) {
		OverallPackingStatus = overallPackingStatus;
	}
	public String getOverallPickingStatus() {
		return OverallPickingStatus;
	}
	public void setOverallPickingStatus(String overallPickingStatus) {
		OverallPickingStatus = overallPickingStatus;
	}
	public String getOverallWarehouseActivityStatus() {
		return OverallWarehouseActivityStatus;
	}
	public void setOverallWarehouseActivityStatus(String overallWarehouseActivityStatus) {
		OverallWarehouseActivityStatus = overallWarehouseActivityStatus;
	}
	public String getPickingDate() {
		return PickingDate;
	}
	public void setPickingDate(String pickingDate) {
		PickingDate = pickingDate;
	}
	public String getReceivingPlant() {
		return ReceivingPlant;
	}
	public void setReceivingPlant(String receivingPlant) {
		ReceivingPlant = receivingPlant;
	}
	public String getShippingPoint() {
		return ShippingPoint;
	}
	public void setShippingPoint(String shippingPoint) {
		ShippingPoint = shippingPoint;
	}
	public String getShipToParty() {
		return ShipToParty;
	}
	public void setShipToParty(String shipToParty) {
		ShipToParty = shipToParty;
	}
	public String getWarehouseGate() {
		return WarehouseGate;
	}
	public void setWarehouseGate(String warehouseGate) {
		WarehouseGate = warehouseGate;
	}
	public String getVersionIdentifier() {
		return versionIdentifier;
	}
	public void setVersionIdentifier(String versionIdentifier) {
		this.versionIdentifier = versionIdentifier;
	}	

}
