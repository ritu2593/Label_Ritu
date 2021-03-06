package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryHeader;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem.OutbDeliveryItemBuilder;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

public class GetSingleDeliveryItemHeaderCommand extends ErpCommand<OutbDeliveryHeader>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetSingleDeliveryItemHeaderCommand.class);

    private final OutboundDeliveryService service;
    private final String id1;
  

    public GetSingleDeliveryItemHeaderCommand( final OutboundDeliveryService service, final String id1)
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetSingleDeliveryItemHeaderCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.id1 = id1;
       
    }

    @Override
    protected OutbDeliveryHeader run()
        throws Exception
    {
        final OutbDeliveryHeader singleOutbDeliveryHeader =
            service
                .getOutbDeliveryHeaderByKey(id1)
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
                		OutbDeliveryHeader.SHIP_TO_PARTY
                 )
                .execute();

        return singleOutbDeliveryHeader;
    }

//    @Override
//    protected OutbDeliveryItem getFallback()
//    {
//        logger.warn("Fallback called because of exception:", getExecutionException());
//       //return OutbDeliveryItem.builder().(id1+id2).build();
//        return OutbDeliveryItem.builder().
//    }
}
