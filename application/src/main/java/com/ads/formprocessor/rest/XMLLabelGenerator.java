package com.ads.formprocessor.rest;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLLabelGenerator{
     public String GenerateXML(Label oLabelData) {
    	 String sDataXML = "";

    	 try {
			
            JAXBContext jaxbContext = JAXBContext.newInstance(Label.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter swDataXML = new StringWriter();
			jaxbMarshaller.marshal(oLabelData, swDataXML);
			
			sDataXML = swDataXML.toString();
			
			
			//System.out.println("XML DATA -----------------" + sDataXML);			
			
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sDataXML;
    }
}