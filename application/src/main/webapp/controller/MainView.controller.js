sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/ui/Device",
], function(Controller, JSONModel, Filter, FilterOperator, Device) {
	"use strict";

	return Controller.extend("labelPrintLabelMasterDetail.controller.MainView", {
		onInit: function() {
			var oViewModel = new JSONModel({
				masterBusy: false,
				detailBusy: false,
			});
			this._oList = this.byId("list");
			this.getView().setModel(oViewModel, "ViewModel");
			this._CallMasterService();
		},
		_CallMasterService: function() {
			var oView = this.getView(),
				oModel = new JSONModel(),
				oViewModel = oView.getModel("ViewModel");
			oView.byId("list").setModel(oModel, "MasterModel");
			oViewModel.setProperty("/masterBusy", true);
			jQuery.ajax({
				type: "GET",
				url: "api/GetDeliveryHeader",
				success: function(response) {
					response.forEach(function(value) { 
					value.DocumentDate = new Date(parseInt(value.DocumentDate));
					value.PickingDate = new Date(parseInt(value.PickingDate)); 
					});
					oModel.setData(response);
					oViewModel.setProperty("/masterBusy", false);
				},
				error: function(response) {
					oViewModel.setProperty("/masterBusy", false);
					sap.m.MessageToast.show("Error in Service. Please try Again")
				}
			});
		},
		onRefresh: function() {
			this._CallMasterService();
		},
		onSearch: function(oEvent) {
			if (oEvent.getParameters().refreshButtonPressed) {
				// Search field's 'refresh' button has been pressed.
				// This is visible if you select any master list item.
				// In this case no new search is triggered, we only
				// refresh the list binding.
				this.onRefresh();
				return;
			}

			var sQuery = oEvent.getParameter("query");

			if (sQuery) {
				this._oListFilterState = [new Filter("DeliveryDocument", FilterOperator.Contains, sQuery),
					new Filter("ShipToParty", FilterOperator.Contains, "1000006")];
			} else {
				this._oListFilterState = [new Filter("ShipToParty", FilterOperator.Contains, "1000006")];
			}
			this._applyFilterSearch();
		},
		_applyFilterSearch: function() {
			var aFilters = this._oListFilterState,
				oViewModel = this.getModel("ViewModel");
			this._oList.getBinding("items").filter(aFilters, "Application");
			// changes the noDataText of the list in case there are no filter results
			if (aFilters.length !== 0) {
				//oViewModel.setProperty("/noDataText", this.getResourceBundle().getText("masterListNoDataWithFilterOrSearchText"));
			} else if (this._oListFilterState.aSearch.length > 0) {
				// only reset the no data text to default when no new search was triggered
				//oViewModel.setProperty("/noDataText", this.getResourceBundle().getText("masterListNoDataText"));
			}
		},
		getModel: function(sName) {
			return this.getView().getModel(sName);
		},
		onSelectionChange: function(oEvent) {
			// get the list item, either from the listItem parameter or from the event's source itself (will depend on the device-dependent mode).
			this._showDetail(oEvent.getParameter("listItem") || oEvent.getSource());
			
		},
		_showDetail: function(oItem) {
			var oView = this.getView(),
				oMasterModel = oItem.getBindingContext("MasterModel").getObject(),
				docNo = oMasterModel.DeliveryDocument,
				oModel = new JSONModel(),
				oModel1 = new JSONModel(),
				oViewModel = oView.getModel("ViewModel");
				oView.byId("detail").setModel(oModel, "detailModel");
				
				oViewModel = oView.getModel("ViewModel");
				oView.byId("detail").setModel(oModel1, "detailBusModel");
			oViewModel.setProperty("/detailBusy", true);
			this.byId("detail").setVisible(true);
			jQuery.ajax({
				type: "GET",
				url: "api/outbounddelsrv?id1=" + docNo,
				success: function(response) {
					oMasterModel.results = response;
					oModel.setData(oMasterModel);
					oViewModel.setProperty("/detailBusy", false);
				}.bind(this),
				error: function(response) {
					oViewModel.setProperty("/detailBusy", false);
					sap.m.MessageToast.show("Error in Service. Please try Again")
				}
			});
			jQuery.ajax({
				type: "GET",
				url: "api/business-partners?id=" + oMasterModel.ShipToParty,
				success: function(response) {
					oModel1.setData(response);
				}.bind(this),
				error: function(response) {
					sap.m.MessageToast.show("Error in Service. Please try Again")
				}
			});
		},
		onHandlingUnit: function(oEvt){
			var oDetailData = this.getView().byId("detail").getModel("detailModel").getData();
			var oParam = {
					"DeliveryDocument": oDetailData.DeliveryDocument,
					"ShipToParty": oDetailData.ShipToParty,
					"SalesOrganization": oDetailData.SalesOrganization
					
			}
			sap.ui.core.BusyIndicator.show();
			var sCustName = this.getView().byId("nameText").getText();
			var sURL = "hello?DelDocNum=" + oDetailData.DeliveryDocument + "&ShipToParty="  + 
			   oDetailData.ShipToParty + "&SalesOrg="  + oDetailData.SalesOrganization + "&Name=" + sCustName;
			jQuery.ajax({
				type: "GET",
				url: sURL,
				success: function(response) {
					var data = JSON.parse(response);
					var dlnk = document.getElementById('dwnldLnk');
					data.forEach(function(value) { 
						var oParsedData = JSON.parse(value);
			            var pdfContent = "data:application/octet-stream;base64," + oParsedData.fileContent
						dlnk.href = pdfContent;
						dlnk.download = oParsedData.fileName;
					    dlnk.click();
					});
					sap.m.MessageToast.show("Labels Generation Complete")
		            sap.ui.core.BusyIndicator.hide();
				},
				error: function(response) {
					sap.m.MessageToast.show("Error in Service. Please try Again")
					sap.ui.core.BusyIndicator.hide();
				}
			});
		}
	});
});