package com.sap.cloud.s4hana.examples.addressmgr.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

public class GetAllOutBoundDeliveryCommand extends ErpCommand<List<OutbDeliveryItem>>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllBusinessPartnersCommand.class);

    private  final String DELDOC ;

    private final OutboundDeliveryService service;

    public GetAllOutBoundDeliveryCommand( final OutboundDeliveryService service,final String id1 )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetAllBusinessPartnersCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.DELDOC = id1;
    }

    @Override
    protected List<OutbDeliveryItem> run()
        throws Exception
    {
        final List<OutbDeliveryItem> deliveryItems =
            service
                .getAllOutbDeliveryItem()
                .select(
                		OutbDeliveryItem.MATERIAL_BY_CUSTOMER,
                        OutbDeliveryItem.ORDER_ID,
                        OutbDeliveryItem.ACTUAL_DELIVERY_QUANTITY,
                        OutbDeliveryItem.DELIVERY_DOCUMENT_ITEM_TEXT,
                        OutbDeliveryItem.ACTUAL_DELIVERED_QTY_IN_BASE_UNIT,
                        OutbDeliveryItem.ITEM_GROSS_WEIGHT,
                        OutbDeliveryItem.ITEM_WEIGHT_UNIT,
                        OutbDeliveryItem.MATERIAL,
                        OutbDeliveryItem.PLANT,
                        OutbDeliveryItem.STORAGE_LOCATION,
                        OutbDeliveryItem.DELIVERY_DOCUMENT,
                        OutbDeliveryItem.DELIVERY_DOCUMENT_ITEM,                        
                        OutbDeliveryItem.DELIVERY_QUANTITY_UNIT,
                        OutbDeliveryItem.BATCH
                        )
               
                .orderBy(OutbDeliveryItem.DELIVERY_DOCUMENT_ITEM, Order.ASC).filter(OutbDeliveryItem.DELIVERY_DOCUMENT.eq(this.DELDOC))
                .execute();

        return deliveryItems;
    }

    @Override
    protected List<OutbDeliveryItem> getFallback()
    {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return Collections.emptyList();
    }
}
