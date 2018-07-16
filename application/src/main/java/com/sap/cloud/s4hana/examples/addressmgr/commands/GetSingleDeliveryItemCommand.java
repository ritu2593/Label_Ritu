package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem.OutbDeliveryItemBuilder;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

public class GetSingleDeliveryItemCommand extends ErpCommand<OutbDeliveryItem>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetSingleDeliveryItemCommand.class);

    private final OutboundDeliveryService service;
    private final String id1;
    private final String id2;

    public GetSingleDeliveryItemCommand( final OutboundDeliveryService service, final String id1,final String id2 )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetSingleDeliveryItemCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    protected OutbDeliveryItem run()
        throws Exception
    {
        final OutbDeliveryItem singleOutbDeliveryItem =
            service
                .getOutbDeliveryItemByKey(id1, id2)
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
                .execute();

        return singleOutbDeliveryItem;
    }

//    @Override
//    protected OutbDeliveryItem getFallback()
//    {
//        logger.warn("Fallback called because of exception:", getExecutionException());
//       //return OutbDeliveryItem.builder().(id1+id2).build();
//        return OutbDeliveryItem.builder().
//    }
}
