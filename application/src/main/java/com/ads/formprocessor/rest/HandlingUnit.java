package com.ads.formprocessor.rest;

import java.util.ArrayList;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;

public class HandlingUnit {

	Multimap<Integer, ArrayList<String>> arrHU;
	Multimap<Integer, ArrayList<OutbDeliveryItem>> arrOutBItemHU;
	
	public HandlingUnit() {
		this.arrHU = LinkedHashMultimap.create();
		this.arrOutBItemHU = LinkedHashMultimap.create();
	}
	
	
}
