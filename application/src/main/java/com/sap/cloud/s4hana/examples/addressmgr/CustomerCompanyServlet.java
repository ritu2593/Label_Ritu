package com.sap.cloud.s4hana.examples.addressmgr;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllBusinessPartnersCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleCustomerCompanyCommand;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.CustomerCompany;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

@WebServlet( "/api/GetCustomerCompanyById" )
public class CustomerCompanyServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(CustomerCompanyServlet.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final String id1 = request.getParameter("id1");
        final String id2 = request.getParameter("id2");

        final String jsonResult;
        if( id1 == null  ) {
           logger.info("Retrieving default Customer Comapany");
           final CustomerCompany result = new GetSingleCustomerCompanyCommand(service,"10100001","1010").execute();
          jsonResult = new Gson().toJson(result);
        } else {
            if( !validateInput(id1) ) {
                logger.warn("Invalid request to retrieve a business partner company, id: {}.", id1+id2);
                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    String.format(
                        "Invalid business partnercomapny ID '%s'. "
                            + "Business partner company ID must not be empty or longer than 10 characters.",
                        id1+id2));
                return;
            }
            
            logger.info("Retrieving business partner comapny with id {}.", id1+id2);
            final CustomerCompany result = new GetSingleCustomerCompanyCommand(service, id1,id2).execute();
            jsonResult = new Gson().toJson(result);
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResult);
    }

    private boolean validateInput( final String id )
    {
        return !Strings.isNullOrEmpty(id) && id.length() <= 10;
    }
}
