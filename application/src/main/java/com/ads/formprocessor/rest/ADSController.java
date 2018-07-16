package com.ads.formprocessor.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;

public class ADSController {
	
	
	public String InvokeController(String DelDocNum, String ShipToParty,String SalesOrg, String ShipToPartyName ) {

		//InputStream fConfigProp = null;
		//Properties pConfig = new Properties();
		
		/////////----------------------------------------------------
		//String sXDPTemplatePath = "";
		
		String tmpDeliveryNum = DelDocNum;
		ArrayList<String> PDFJSON = new ArrayList<String>();
		
		OutBoundDelivery oDelivery = new  OutBoundDelivery(tmpDeliveryNum);
		HandlingUnit oOutBHU = CalculateHandlingUnits(oDelivery);
		
		/*-------------------------------------------*/
		/*
		
		Set<Integer> keys = oOutBHU.arrHU.keySet();
		
		ArrayList<String> arrHUJSON = new ArrayList<String>();
		ArrayList<String> arrHUPDF = new ArrayList<String>();
		//ArrayList<OutbDeliveryItem> tmpOutBDel;
		Collection<ArrayList<String>> cHUVal = null;
		Collection<ArrayList<OutbDeliveryItem>> cHUOutBDel=null;
		ArrayList<String> sLabelXMLArr = new  ArrayList<String>();
		ArrayList<String> sLabelXDPArr = new  ArrayList<String>();
		Date dtTodaysDate = new Date();
		DateFormat dfToday = new SimpleDateFormat("ddMMyyyy");
		int iSize;
		String sSize;
		String tmpString="";
	    for (Integer key : keys) 
	    {
	         cHUVal = oOutBHU.arrHU.get(key);
	         cHUOutBDel = oOutBHU.arrOutBItemHU.get(key);
	        iSize = cHUVal.size() ;
	        sSize = new Integer(iSize).toString();
	        tmpString = tmpString + sSize;
	        if(key == 0)
        	{
        		//Master Label
	        	Label label = new Label();
				Iterator<ArrayList<String>> itrHUVal = cHUVal.iterator();
				Iterator<ArrayList<OutbDeliveryItem>> itrHUOutBDel = cHUOutBDel.iterator();
				
				ArrayList<String> listHU = itrHUVal.next();
				ArrayList<OutbDeliveryItem> oHUOutBDel = itrHUOutBDel.next();
				
				
				
				/* ------- Start : Label Node Generation *
				FordMasterLabel oMasterLabel = new FordMasterLabel();
				
				oMasterLabel.AccountByCustomer = "PP03B";
				oMasterLabel.ActualDeliveredQtyInBaseUnit = listHU.get(1);
				oMasterLabel.HeaderGrossWeight = oHUOutBDel.get(0).getItemGrossWeight().toString();
							
				oMasterLabel.CurrentDate = dfToday.format(dtTodaysDate);
							
				oMasterLabel.MaterialByCustomer = oHUOutBDel.get(0).getMaterialByCustomer();
				oMasterLabel.Material = oHUOutBDel.get(0).getMaterial();
				oMasterLabel.DeliveryDocumentItemText = oHUOutBDel.get(0).getDeliveryDocumentItemText();
				oMasterLabel.FullName = "Ford";
				/* ------- End : Label Node Generation * 
				
				label.setFordMasterLabel(oMasterLabel);
				
				sLabelXMLArr.add(GenerateBase64(label));
				sLabelXDPArr.add(GetXDPFile("FordMaster"));
        		
        	}
        	else if(key > 0) {
        		//Mixed Label
        		arrHUJSON = GenerateLabel("Master",cHUVal, cHUOutBDel);
        	}
	        arrHUPDF.addAll(arrHUJSON);
	        //arrHUPDF.addAll(GetOutputLabelPDFJSON(arrHUJSON));
	        
	    }
	    */
	    /*----------------------------------------------------*/
		
		
		
		
		PDFJSON = HandlingUnitProcessor(oDelivery, oOutBHU, ShipToPartyName);
		
		Gson pdfgson = new Gson();  
		String pjson = pdfgson.toJson(PDFJSON);
		
		
		
		//Gson mapgson = new Gson();  
		//Gson oOutBDelGson = new Gson();  
		//Gson oSet = new Gson();
		
		
		//String mapjson = mapgson.toJson(oOutBHU.arrHU.asMap());
		//String outDelJson = oOutBDelGson.toJson(oOutBHU.arrOutBItemHU.asMap());
		
		//String mapjson = mapgson.toJson(cHUVal);
		//String outDelJson = oOutBDelGson.toJson(cHUOutBDel);
		//String sSet = oSet.toJson(keys);
		//String XMLArr = oSet.toJson(sLabelXMLArr);
		//String XDPArr = oSet.toJson(sLabelXDPArr);
		//String hupdf =oSet.toJson(arrHUPDF); 
		//String tmp = tmpString + "------ " + mapjson + outDelJson +sSet + "---Size----"+ cHUVal.size() + "-----XML----"+ XMLArr + "----XDP----"+ XDPArr + "-------"+ hupdf;
		
		return pjson;
		
	}
	
	public String GetXDPFile(String sFileType) {
		String sXDPTemplatePath = "";
		InputStream fConfigProp = null;
		String sFileXDPTemplate ="";
		Properties pConfig = new Properties();
		
		try {
					
			fConfigProp = this.getClass().getClassLoader().getResourceAsStream("config.properties");
			pConfig.load(fConfigProp);
			sXDPTemplatePath = pConfig.getProperty(sFileType+"Template");
			
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		InputStream fTemplate = this.getClass().getClassLoader().getResourceAsStream(sXDPTemplatePath); 
		sFileXDPTemplate = getStringFromInputStream(fTemplate);	
		String sBase64XDPTemplate =  Base64.getEncoder().encodeToString((sFileXDPTemplate).getBytes());
		
		return sBase64XDPTemplate;
		
	}
	
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	
	private ArrayList<String> HandlingUnitProcessor(OutBoundDelivery oOutBoundDelivery, HandlingUnit oHU, String ShipToParty) {
		
		Set<Integer> keys = oHU.arrHU.keySet();
		
		ArrayList<String> arrHUJSON = new ArrayList<String>();
		ArrayList<String> arrHUPDF = new ArrayList<String>();
		//ArrayList<OutbDeliveryItem> tmpOutBDel;
		
	    for (Integer key : keys) 
	    {
	        Collection<ArrayList<String>> cHUVal = oHU.arrHU.get(key);
	        Collection<ArrayList<OutbDeliveryItem>> cHUOutBDel = oHU.arrOutBItemHU.get(key);
	        int iSize = cHUVal.size() ;
	        if(iSize == 1)
        	{
        		//Master Label
	        	arrHUJSON  = GenerateLabel("Master",cHUVal, cHUOutBDel, ShipToParty);
        		
        	}
        	else if(iSize > 1) 
        	{
        		//Mixed Label
        		arrHUJSON = GenerateLabel("Mixed",cHUVal, cHUOutBDel, ShipToParty);
        	}
	        arrHUPDF.addAll(GetOutputLabelPDFJSON(arrHUJSON));
	        
	    }
	    
	    return arrHUPDF;
	}
	public ArrayList<String> GetOutputLabelPDFJSON(ArrayList<String> arrHUJSON) {
		
		ArrayList<String> arrPDFJSON = new ArrayList<String>();
		
		Iterator<String> itrArrHUJSON = arrHUJSON.iterator();
		
		while(itrArrHUJSON.hasNext()) {
			
			String HUJson = itrArrHUJSON.next();
			arrPDFJSON.add(GeneratePDF(HUJson));
			
		}
		
		return  arrPDFJSON;
		
	}
	public String GeneratePDF(String Json){
		
		
		String url = "https://adsrestapiformsprocessing-a7762c010.hana.ondemand.com/ads.restapi/v1/adsRender/pdf";
		/* HTTPCLIENT AND HTTPPOST OOBJECT */
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);

		/* ADD HEADER INFO */
		request.addHeader("TraceLevel", "2");
		request.addHeader("Authorization", "Bearer 74950538c14501f8917cd45c563db11");
		request.addHeader("Content-Type", "application/json");
		

		/* PROXY CONFIG 
		HttpHost target = new HttpHost("adsrestapiformsprocessing-a7762c010.hana.ondemand.com",443,"HTTPS");
		RequestConfig config = RequestConfig.custom().setProxy(target).build();
		request.setConfig(config);
		 */
		/* JSON AS STRINGENTITY */
		StringEntity input = null;
		try 
		{
			
			input = new StringEntity(Json);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		request.setEntity(input);
				
		/* SEND AND RETRIEVE RESPONSE */
		HttpResponse response = null;
		try 
		{
			response = httpClient.execute(request);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

		/* RESPONSE AS JSON STRING */
		String result = null;
		try {
			result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ArrayList<String> GenerateLabel(String sLabelCat, Collection<ArrayList<String>> cHUVal , Collection<ArrayList<OutbDeliveryItem>> cHUOutBDel, String ShipToParty ) {
		
		
		Date dtTodaysDate = new Date();
		DateFormat dfToday = new SimpleDateFormat("ddMMMyyyy");
		String sLabelXMLStr = "";
		ArrayList<String> sLabelXMLArr = new  ArrayList<String>();
		ArrayList<String> sLabelXDPArr = new  ArrayList<String>();
		
		if(sLabelCat == "Master") 
		{	
			Label label = new Label();
			Iterator<ArrayList<String>> itrHUVal = cHUVal.iterator();
			Iterator<ArrayList<OutbDeliveryItem>> itrHUOutBDel = cHUOutBDel.iterator();
			
			ArrayList<String> listHU = itrHUVal.next();
			ArrayList<OutbDeliveryItem> oHUOutBDel = itrHUOutBDel.next();
			
			
			
			/* ------- Start : Label Node Generation */ 
			FordMasterLabel oMasterLabel = new FordMasterLabel();
			
			oMasterLabel.ActualDeliveryQuantity = listHU.get(1);
			oMasterLabel.DeliveryQuantityUnit = oHUOutBDel.get(0).getDeliveryQuantityUnit();
			oMasterLabel.ItemGrossWeight = oHUOutBDel.get(0).getItemGrossWeight().toString();
						
			oMasterLabel.LoadingDate = dfToday.format(dtTodaysDate);
						
			oMasterLabel.Batch = oHUOutBDel.get(0).getBatch();
			oMasterLabel.MaterialbyCustomer = oHUOutBDel.get(0).getMaterialByCustomer();
			oMasterLabel.ShipToParty = ShipToParty;
			
			/* ------- End : Label Node Generation */ 
			
			label.setFordMasterLabel(oMasterLabel);
			
			sLabelXMLArr.add(GenerateBase64(label));
			sLabelXDPArr.add(GetXDPFile("FordMaster"));
			
			
		}
		else if(sLabelCat == "Mixed") 
		{	float iItemGrossW = 0;
			int itmp = 0;
			Iterator<ArrayList<String>> itrHUVal = cHUVal.iterator();
			Iterator<ArrayList<OutbDeliveryItem>> itrHUOutBDel = cHUOutBDel.iterator();
			
			FordMixedMasterLoadLabel oMixedMasterLoadLabel = new FordMixedMasterLoadLabel();
			
			while(itrHUVal.hasNext())
			{
				
				ArrayList<String> tmpHU = itrHUVal.next();
				ArrayList<OutbDeliveryItem> tmpHUOutBDel = itrHUOutBDel.next();
				
				FordMixedMasterLabel oMixedMasterLabel = new FordMixedMasterLabel();
				oMixedMasterLabel.ActualDeliveryQuantity = tmpHU.get(1);
				oMixedMasterLabel.DeliveryQuantityUnit = tmpHUOutBDel.get(0).getDeliveryQuantityUnit();
				oMixedMasterLabel.ItemGrossWeight = tmpHUOutBDel.get(0).getItemGrossWeight().toString();
				oMixedMasterLabel.LoadingDate = dfToday.format(dtTodaysDate);
				oMixedMasterLabel.Batch = tmpHUOutBDel.get(0).getBatch();
				oMixedMasterLabel.MaterialbyCustomer = tmpHUOutBDel.get(0).getMaterialByCustomer();
				oMixedMasterLabel.DeliveryDocument = tmpHUOutBDel.get(0).getDeliveryDocument();
				oMixedMasterLabel.ShipToParty = ShipToParty;
				
				iItemGrossW = iItemGrossW + tmpHUOutBDel.get(0).getItemGrossWeight().floatValue();
				Label label = new Label();
				label.setFordMixedMasterLabel(oMixedMasterLabel);
				
				
				sLabelXMLArr.add(GenerateBase64(label));
				sLabelXDPArr.add(GetXDPFile("FordMixedMaster"));
				
				if(itmp == 0) 
				{
					oMixedMasterLoadLabel.DeliveryDocument = tmpHUOutBDel.get(0).getDeliveryDocument();
					oMixedMasterLoadLabel.MaterialbyCustomer = tmpHUOutBDel.get(0).getMaterialByCustomer();
					oMixedMasterLoadLabel.ActualDeliveryQuantity = tmpHU.get(1);
					
				}
				else if(itmp == 1)
				{
					
					oMixedMasterLoadLabel.MaterialbyCustomerA = tmpHUOutBDel.get(0).getMaterialByCustomer();
					oMixedMasterLoadLabel.ActualDeliveryQuantityA = tmpHU.get(1);
				}
				itmp = itmp +1;
			}
			
			FordMixedLabel oMixedLabel = new FordMixedLabel();
			
			oMixedLabel.HeaderGrossWeight = (new Float(iItemGrossW)).toString();
			oMixedLabel.LoadingDate = dfToday.format(dtTodaysDate);
			oMixedLabel.ShipToParty = ShipToParty;
			
			Label label = new Label();
			label.setFordMixedLabel(oMixedLabel);
			
			sLabelXMLArr.add(GenerateBase64(label));
			sLabelXDPArr.add(GetXDPFile("FordMixed"));
			
			
			Label oMMLlabel = new Label();
			oMMLlabel.setFordMixedMasterLoadLabel(oMixedMasterLoadLabel);
			
			sLabelXMLArr.add(GenerateBase64(oMMLlabel));
			sLabelXDPArr.add(GetXDPFile("FordMixedMasterLoad"));
			
			
		}
		
		return GenerateJson(sLabelXMLArr,sLabelXDPArr);
		
	}
	
	public ArrayList<String> GenerateJson(ArrayList<String> LabelXMLArr,ArrayList<String> LabelXDPArr ) {
		
		Iterator<String> itrLabelXMLArr = LabelXMLArr.iterator();
		Iterator<String> itrLabelXDPArr = LabelXDPArr.iterator();
		String LabelXML;
		String LabelXDP;
		ArrayList<String> JsonArr = new ArrayList<String>();
		
		while(itrLabelXMLArr.hasNext()) {
			
			LabelXML = itrLabelXMLArr.next();
			LabelXDP = itrLabelXDPArr.next();
			
			ADSRenderJsonData oJsonData = new ADSRenderJsonData(LabelXDP, LabelXML);
			Gson oADSGSON = new Gson();
			JsonArr.add(oADSGSON.toJson(oJsonData));
			
		}
				
		return JsonArr;
	}
	
	public String GenerateBase64(Label label) {
		String sLabelXMLStr = "";
		
		XMLLabelGenerator oMixedLabelXML = new XMLLabelGenerator();
		sLabelXMLStr = oMixedLabelXML.GenerateXML(label);
		String sBase64MixedLabel = Base64.getEncoder().encodeToString((sLabelXMLStr).getBytes());
		
		return sBase64MixedLabel;
	}
	
	
	private HandlingUnit CalculateHandlingUnits(OutBoundDelivery oOutBoundDelivery) {
		
		int iHUCapacity = 4;
		//int iHUQty = 0;
		
		Multimap<Integer, ArrayList<String>> arrPrdHU = LinkedHashMultimap.create();
		HandlingUnit oHU = new HandlingUnit();
		
		Iterator<OutbDeliveryItem> itrOBDI = oOutBoundDelivery.DeliveryItem.iterator();
		int iRemainingHUCap = 0;
		int iHUNum = 0;
		ArrayList<String> tmpHUArray = new ArrayList<String>();
		String sMaterialName;
		int itmpQty;
		Float iQty;
		OutbDeliveryItem oOutBoundDeliveryItem;
		while(itrOBDI.hasNext())
		{
			
			oOutBoundDeliveryItem = itrOBDI.next();
			iQty = oOutBoundDeliveryItem.getActualDeliveryQuantity().floatValue();
			sMaterialName = oOutBoundDeliveryItem.getMaterial();
			itmpQty = iQty.intValue();
			
			while(itmpQty > 0) 
			{
					if(iRemainingHUCap>0)
					{
						if(iRemainingHUCap < itmpQty ) 
						{
							itmpQty = itmpQty - iRemainingHUCap;
							
							ArrayList<String> tmpHUArray1= new ArrayList<String>();
							tmpHUArray1.add(0, sMaterialName);
							tmpHUArray1.add(1, "" + iRemainingHUCap);						
							tmpHUArray1.add(2, "itmpQty "+itmpQty);
							tmpHUArray1.add(3, "iRemainingHUCap "+iRemainingHUCap);
							tmpHUArray1.add(4, "iRemainingHUCap less than itmpQty");
							
							oHU.arrHU.put(iHUNum, tmpHUArray1);
							
							ArrayList<OutbDeliveryItem> tmpOutBHUArray1= new ArrayList<OutbDeliveryItem>();
							tmpOutBHUArray1.add(0, oOutBoundDeliveryItem);
							oHU.arrOutBItemHU.put(iHUNum,tmpOutBHUArray1);
							
							iHUNum = iHUNum + 1;
							iRemainingHUCap = 0;
							
						}else {
							iRemainingHUCap = iRemainingHUCap - itmpQty;
							
							ArrayList<String> tmpHUArray2= new ArrayList<String>();
							tmpHUArray2.add(0, sMaterialName);
							tmpHUArray2.add(1, "" + itmpQty);
							tmpHUArray2.add(2, "itmpQty "+itmpQty);
							tmpHUArray2.add(3, "iRemainingHUCap "+iRemainingHUCap);
							tmpHUArray2.add(4, "iRemainingHUCap greater than itmpQty");
							
							oHU.arrHU.put(iHUNum, tmpHUArray2);
							
							ArrayList<OutbDeliveryItem> tmpOutBHUArray2= new ArrayList<OutbDeliveryItem>();
							tmpOutBHUArray2.add(0, oOutBoundDeliveryItem);
							oHU.arrOutBItemHU.put(iHUNum,tmpOutBHUArray2);
							
							itmpQty = 0;	
						}
						
					}
					else if(itmpQty < iHUCapacity ) 
					{
						iRemainingHUCap = iHUCapacity - itmpQty;
												
						ArrayList<String> tmpHUArray3= new ArrayList<String>();
						tmpHUArray3.add(0, sMaterialName);
						tmpHUArray3.add(1, "" + itmpQty);
						tmpHUArray3.add(2, "itmpQty "+itmpQty);
						tmpHUArray3.add(3, "iRemainingHUCap "+iRemainingHUCap);
						tmpHUArray3.add(4, "ItemQty Less than iHUCapacity");
						
						oHU.arrHU.put(iHUNum, tmpHUArray3);
						
						ArrayList<OutbDeliveryItem> tmpOutBHUArray3= new ArrayList<OutbDeliveryItem>();
						tmpOutBHUArray3.add(0, oOutBoundDeliveryItem);
						oHU.arrOutBItemHU.put(iHUNum,tmpOutBHUArray3);
						
						
						itmpQty = 0;
					}
					else
					{
						
						itmpQty = itmpQty - iHUCapacity;
						
						ArrayList<String> tmpHUArray4= new ArrayList<String>();
						tmpHUArray4.add(0, sMaterialName);
						tmpHUArray4.add(1, "" + iHUCapacity);
						tmpHUArray4.add(2, "itmpQty "+itmpQty);
						tmpHUArray4.add(3, "iRemainingHUCap "+iRemainingHUCap);
						tmpHUArray4.add(4, "Default");
						
						
						oHU.arrHU.put(iHUNum, tmpHUArray4);
						
						ArrayList<OutbDeliveryItem> tmpOutBHUArray4= new ArrayList<OutbDeliveryItem>();
						tmpOutBHUArray4.add(0, oOutBoundDeliveryItem);
						oHU.arrOutBItemHU.put(iHUNum,tmpOutBHUArray4);
						
						iRemainingHUCap =0;
						iHUNum = iHUNum+1;
						
					}
				
				
				}
			}
			
			
		
		
		return oHU;
		}

}
