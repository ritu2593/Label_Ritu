/**
 * 
 */
package com.ads.formprocessor.rest;

/**
 * @author jdhabarw
 *
 */
public class ADSRenderJsonData{
	
	public String xdpTemplate;
	public String xmlData;
	
	ADSRenderJsonData(String sXDPTemplate, String sXMLData){
		this.xdpTemplate = sXDPTemplate;
		this.xmlData = sXMLData;
	}
	
}
