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
/*import com.google.gson.JsonObject;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllOutBoundDeliveryCommand;*/
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllOutBoundDeliveryHeaderCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleDeliveryItemHeaderCommand;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryHeader;
/*import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;*/
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultOutboundDeliveryService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.OutboundDeliveryService;

@WebServlet( "/api/GetDeliveryHeader" )
public class OutboundDeliveryHeaderServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(OutboundDeliveryHeaderServlet.class);

    private final OutboundDeliveryService service = new DefaultOutboundDeliveryService();

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final String id = request.getParameter("id");
        /*JsonObject oJSON = null;*/
        final String jsonResult;
        if( id == null ) {
            logger.info("Retrieving Default Delivery header");
           // final OutbDeliveryHeader result = new GetSingleDeliveryItemHeaderCommand(service,"80000000").execute();
            final List<OutbDeliveryHeader> result = new GetAllOutBoundDeliveryHeaderCommand(service).execute();
            jsonResult = new Gson().toJson(result);
        } else {
            if( !validateInput(id) ) {
                logger.warn("Invalid request to retrieve a Outbound Delivery header, id: {}.", id);
                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    String.format(
                        "Invalid Outbound Delivery header ID '%s'. "
                            + "Delivery header ID must not be empty or longer than 10 characters.",
                        id));
                return;
            }
            logger.info("Retrieving Delivery Header with id {}.", id);
            final OutbDeliveryHeader result = new GetSingleDeliveryItemHeaderCommand(service, id).execute();
            jsonResult = new Gson().toJson(result);
            /*oJSON = new Gson().toJsonTree(result).getAsJsonObject();*/
        }
        /*List<OutbDeliveryItem> resultItem = new GetAllOutBoundDeliveryCommand(service,id).execute();	
        
        String oJsonResultItem =  new Gson().toJson(resultItem);
        
        oJSON.addProperty("DeliveryItem", oJsonResultItem);*/
       
        
        response.setContentType("application/json");
        response.getWriter().write(jsonResult);
        //response.getWriter().write(jsonResult); oJSON.toString();
        /*response.getWriter().write(oJSON.toString()); */
    }

    private boolean validateInput( final String id )
    {
        return !Strings.isNullOrEmpty(id) && id.length() <= 10;
    }
}
