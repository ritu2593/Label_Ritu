package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.CustomerCompany;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

public class GetSingleCustomerCompanyCommand extends ErpCommand<CustomerCompany>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetSingleBusinessPartnerByIdCommand.class);

    private final BusinessPartnerService service;
    private final String id1;
    private final String id2;

    public GetSingleCustomerCompanyCommand( final BusinessPartnerService service, final String id1,final String id2 )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetSingleCustomerCompanyCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.id1 = id1;
        this.id2 = id2;
        
    }

    @Override
    protected CustomerCompany run()
        throws Exception
    {
        final CustomerCompany customerCompanyRecord =
            service
                .getCustomerCompanyByKey(id1,id2)
                .select(
                		CustomerCompany.ACCOUNT_BY_CUSTOMER
//                    BusinessPartner.LAST_NAME,
//                    BusinessPartner.FIRST_NAME,
//                    BusinessPartner.IS_MALE,
//                    BusinessPartner.IS_FEMALE,
//                    BusinessPartner.CREATION_DATE,
                   /* BusinessPartner.ADDR_LAST_CHECKED_BY_BUS,
                  //  BusinessPartner.ADDR_LAST_CHECKED_ON_BUS,*/
//                    BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS.select(
//                        BusinessPartnerAddress.BUSINESS_PARTNER,
//                        BusinessPartnerAddress.ADDRESS_ID,
//                        BusinessPartnerAddress.COUNTRY,
//                        BusinessPartnerAddress.POSTAL_CODE,
//                        BusinessPartnerAddress.CITY_NAME,
//                        BusinessPartnerAddress.STREET_NAME,
                       // BusinessPartnerAddress.HOUSE_NUMBER
                        )
                .execute();

        return customerCompanyRecord;
    }

    @Override
    protected CustomerCompany getFallback()
    {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return CustomerCompany.builder().customer(id1+id2).build();
    }
}
