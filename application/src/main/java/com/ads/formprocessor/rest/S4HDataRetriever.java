/**
 * 
 */
package com.ads.formprocessor.rest;

import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleCustomerCompanyCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleDeliveryItemCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.CustomerCompany;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultOutboundDeliveryService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

/**
 * @author jdhabarw
 *
 */
public class S4HDataRetriever {
	
	String OrderId;
	String ActualDelQuantity;
	String AcctbyCust;
	
	public S4HDataRetriever()
	{
	
		/*final OutboundDeliveryService oDeliveryService = new DefaultOutboundDeliveryService();
		final OutbDeliveryItem oDeliveryServiceResult = new GetSingleDeliveryItemCommand(oDeliveryService,"80000000", "000010").execute();;
		this.OrderId = oDeliveryServiceResult.getOrderID();
		this.ActualDelQuantity = oDeliveryServiceResult.getActualDeliveryQuantity().toString();
		
		
		final BusinessPartnerService oBusinessPartnerService = new DefaultBusinessPartnerService();
		final CustomerCompany oBusinessPartnerServiceResult = new GetSingleCustomerCompanyCommand(oBusinessPartnerService,"10100001","1010").execute();
		this.AcctbyCust = oBusinessPartnerServiceResult.getAccountByCustomer();*/
	}

}
