package com.sap.cloud.s4hana.examples.addressmgr;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllOutBoundDeliveryCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleDeliveryItemCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultOutboundDeliveryService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

@WebServlet( "/api/outbounddelsrv" )
public class OutboundDeliveryServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(OutboundDeliveryServlet.class);
    private final OutboundDeliveryService service = new DefaultOutboundDeliveryService();

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
            throws IOException
        {
            final String id1 = request.getParameter("id1");
            final String id2 = request.getParameter("id2");

            final String jsonResult;
            if(id1 != null & id2 == null ) {
                logger.info("Retrieving all Records of Outboud delivery items");
                 final List<OutbDeliveryItem> result = new GetAllOutBoundDeliveryCommand(service,id1).execute();
                 
                //final OutbDeliveryItem result = new GetSingleDeliveryItemCommand(service,"80000000", "000010").execute();;
                jsonResult = new Gson().toJson(result);
            } else {
                if( !validateInput(id1) ) {
                    logger.warn("Invalid request to retrieve a Outbound delivery item, id: {}.", id1);
                    response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        String.format(
                            "Invalid business partner ID '%s'. "
                                + "Outbound Delivery item ID must not be empty or longer than 10 characters.",
                            id1));
                    return;
                }
                logger.info("Retrieving business partner with id {}.", id1+id2);
                final OutbDeliveryItem result = new GetSingleDeliveryItemCommand(service, id1,id2).execute();
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
