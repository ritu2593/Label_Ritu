/**
 * 
 */
package com.ads.formprocessor.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jdhabarw
 *
 */

@XmlRootElement(name="Label")
public class Label {
	@XmlElement(name = "B10MasterLabel")
	private B10MasterLabel b10MasterLabel ;
	
	@XmlElement(name = "B10MixedLabel")
	private B10MixedLabel b10MixedLabel ;
		
	@XmlElement(name = "FordMasterLabel")
	private FordMasterLabel fordMasterLabel ;
	
	@XmlElement(name = "FordMixedLabel")
	private FordMixedLabel fordMixedLabel ;
	
	@XmlElement(name = "FordMixedMasterLabel")
	private FordMixedMasterLabel fordMixedMasterLabel ;
	
	@XmlElement(name = "FordMixedMasterLoadLabel")
	private FordMixedMasterLoadLabel fordMixedMasterLoadLabel ;
	
  
 /* public B10MasterLabel getB10MasterLabel() {
      return b10MasterLabel;
  }*/
	public void setB10MasterLabel(B10MasterLabel b10MasterLabel) {
	    this.b10MasterLabel = b10MasterLabel;
	}
	public void setB10MixedLabel(B10MixedLabel b10MixedLabel) {
	     this.b10MixedLabel = b10MixedLabel;
	}
	public void setFordMasterLabel(FordMasterLabel fordMasterLabel) {
		this.fordMasterLabel = fordMasterLabel;
	}
	public void setFordMixedLabel(FordMixedLabel fordMixedLabel) {
		this.fordMixedLabel = fordMixedLabel;
	}
	public void setFordMixedMasterLabel(FordMixedMasterLabel fordMixedMasterLabel) {
		this.fordMixedMasterLabel = fordMixedMasterLabel;
	}
	public void setFordMixedMasterLoadLabel(FordMixedMasterLoadLabel fordMixedMasterLoadLabel) {
		this.fordMixedMasterLoadLabel = fordMixedMasterLoadLabel;
	}
  
}