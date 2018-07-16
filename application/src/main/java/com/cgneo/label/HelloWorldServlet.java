package com.cgneo.label;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.ads.formprocessor.rest.ADSController;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(HelloWorldServlet.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
    	String sDelDocNum = request.getParameter("DelDocNum");
    	String sShipToParty = request.getParameter("ShipToParty");
    	String sSalesOrg = request.getParameter("SalesOrg");
    	String sName = request.getParameter("Name");
        logger.info("I am running!");
        
        ADSController restClient = new ADSController();
        String restResponse = restClient.InvokeController(sDelDocNum,sShipToParty,sSalesOrg,sName );
        response.getWriter().write(restResponse);
        
       // response.getWriter().write("Hello World!");
		//AdsRestClient restClient = new AdsRestClient();
		//String restResponse = restClient.callService(sLabelType);
		//response.getWriter().write("Hello World!" + sLabelType);
		//response.getWriter().write(restResponse);
		//logger.info(restResponse);
		System.out.println(restResponse);
    }
}
