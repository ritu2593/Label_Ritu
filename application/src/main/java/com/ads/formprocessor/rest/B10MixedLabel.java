package com.ads.formprocessor.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author jdhabarw
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class B10MixedLabel {

	
	@XmlElement(name = "OrderID")
	private String OrderID;
	@XmlElement(name = "WarehouseGate")
	private String WarehouseGate;
	@XmlElement(name = "ReceivingPlant")
	private String ReceivingPlant;
	@XmlElement(name = "AccountByCustomer")
	private String AccountByCustomer;
	
        
    public String getOrderID() {
        return OrderID;
    }
    
    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }
    
    public String getWarehouseGate() {
        return WarehouseGate;
    }
    
    public void setWarehouseGate(String WarehouseGate) {
        this.WarehouseGate = WarehouseGate;
    }
    
    
    public String getReceivingPlant() {
        return ReceivingPlant;
    }
    
    public void setReceivingPlant(String ReceivingPlant) {
        this.ReceivingPlant = ReceivingPlant;
    }
   

   public String getAccountByCustomer() {
        return AccountByCustomer;
    }
   
    public void setAccountByCustomer(String AccountByCustomer) {
        this.AccountByCustomer = AccountByCustomer;
    }
      
}