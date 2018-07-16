package com.sap.cloud.s4hana.examples.addressmgr.commands;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryHeader;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

public class GetAllOutBoundDeliveryHeaderCommand extends ErpCommand<List<OutbDeliveryHeader>>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllBusinessPartnersCommand.class);

   // private static final String CATEGORY_PERSON = "1";

    private final OutboundDeliveryService service;

    public GetAllOutBoundDeliveryHeaderCommand( final OutboundDeliveryService service )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetAllBusinessPartnersCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
    }

    @Override
    protected List<OutbDeliveryHeader> run()
        throws Exception
    {
        final List<OutbDeliveryHeader> deliveryHeaderItems =
            service
                .getAllOutbDeliveryHeader()
                .select(
                		OutbDeliveryHeader.DELIVERY_DOCUMENT,
                		OutbDeliveryHeader.OVERALL_PICKING_STATUS,
                		OutbDeliveryHeader.WAREHOUSE_GATE,
                		OutbDeliveryHeader.HEADER_GROSS_WEIGHT,
                		OutbDeliveryHeader.HEADER_WEIGHT_UNIT,
                		OutbDeliveryHeader.RECEIVING_PLANT,
                		OutbDeliveryHeader.SHIPPING_POINT,
                		OutbDeliveryHeader.PICKING_DATE,
                		OutbDeliveryHeader.DOCUMENT_DATE,
                		OutbDeliveryHeader.OVERALL_PACKING_STATUS,
                		OutbDeliveryHeader.OVERALL_WAREHOUSE_ACTIVITY_STATUS,
                		OutbDeliveryHeader.SHIP_TO_PARTY,
                		OutbDeliveryHeader.TOTAL_NUMBER_OF_PACKAGE
                		
               )
                .orderBy(OutbDeliveryHeader.DELIVERY_DOCUMENT, Order.ASC).filter(OutbDeliveryHeader.OVERALL_PICKING_STATUS.eq("C"))
                .execute();

        return deliveryHeaderItems;
    }

    @Override
    protected List<OutbDeliveryHeader> getFallback()
    {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return Collections.emptyList();
    }
}
