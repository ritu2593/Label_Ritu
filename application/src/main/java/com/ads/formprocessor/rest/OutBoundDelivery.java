package com.ads.formprocessor.rest;

import java.util.List;

import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllOutBoundDeliveryCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleDeliveryItemHeaderCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryHeader;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultOutboundDeliveryService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

public class OutBoundDelivery {
	OutbDeliveryHeader DeliveryHeader;
	List<OutbDeliveryItem> DeliveryItem;
	
	OutBoundDelivery(String sDeliveryDocument){
		
		OutboundDeliveryService service = new DefaultOutboundDeliveryService();
		this.DeliveryHeader = new GetSingleDeliveryItemHeaderCommand(service, sDeliveryDocument).execute();
		this.DeliveryItem = new GetAllOutBoundDeliveryCommand(service,sDeliveryDocument).execute();
	}
	
	
}
