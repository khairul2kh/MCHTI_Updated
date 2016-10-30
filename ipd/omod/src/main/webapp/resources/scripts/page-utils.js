/*
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of IPD module.
 *
 *  IPD module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  IPD module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with IPD module.  If not, see <http://www.gnu.org/licenses/>.
 *
*/
IPD={
		submit : function(thiz)
		{
			var t = jQuery("#tabs").tabs();
			var selected = t.tabs('option', 'selected');
			jQuery("#tab").val(selected);
			jQuery("#IpdMainForm").submit();
		}
};

BEDSTRENGTH = {
	getBedStrength : function(thiz) {
		if (SESSION.checkSession()) {
			var x = jQuery(thiz).val();
			if (x != null && x != '') {
				if (SESSION.checkSession()) {
					var data = jQuery.ajax({
						type : "GET",
						url : "getBedStrength.htm",
						data : ({
							wardId : x
						}),
						async : false,
						cache : false
					}).responseText;
					if (data != undefined && data != null && data != '') {
						jQuery("#divBedStrength").html(data);

					} else {
						alert('Please refresh page!');
					}

				}
			}
		}
	}
};

FIRSTBEDSTRENGTH = {
	getFirstBedStrength : function(thiz) {
		var x = thiz;
		if (x != null && x != '') {
				var data = jQuery.ajax({
					type : "GET",
					url : "getBedStrength.htm",
					data : ({
						wardId : x
					}),
					async : false,
					cache : false
				}).responseText;
				if (data != undefined && data != null && data != '') {
					jQuery("#divBedStrength").html(data);

				} else {
					alert('Please refresh page!');
				}
			}
	}
};
ADMISSION={
    // add this go function for admit link directly remove popup
     go : function(link)
                {
                    if(opener!=undefined) {
			jQuery(opener.location).attr({href : link});
                    } else {
			jQuery(location).attr({href : link});
                    }
                },
    
		admit : function(id)
		{
			if(SESSION.checkSession())
			{
				url = "admission.htm?admissionId="+id+"&keepThis=false&TB_iframe=true&height=500&width=850";
				tb_show("Admission",url,false);
			}
		},
		removeOrNoBed : function(id,action)
		{
			if(SESSION.checkSession()){
				if(action == 1){
				//ghanshyam 2-july-2013 issue no # 1970 Prompt message to be changed
					if( confirm("Are you sure?"))
					{
						ACT.go("removeOrNoBed.htm?admissionId="+id+"&action="+1);
					}
				}else if(action == 2){
				//ghanshyam 2-july-2013 issue no # 1970 Prompt message to be changed
					if( confirm("Are you sure?"))
					{
						ACT.go("removeOrNoBed.htm?admissionId="+id+"&action="+2);
					}
				}
			}
		}
};
QUEUE={
		load : function(url , container)
		{
			jQuery(container).load(url);
		},
		initTableHover : function()
		{
			jQuery("tr").each(function(){
				var obj = jQuery(this);
				if( obj.hasClass("evenRow") || obj.hasClass("oddRow") )
				{
					obj.hover(
							function(){obj.addClass("hover");},
							function(){obj.removeClass("hover");}
							);
				}
			});
		},
		refreshQueue : function()
		{
			jQuery("#Patients_for_admission").load("patientsForAdmissionAjax.htm?ipdWardString="+jQuery("#ipdWardString").val()+"&doctorString="+jQuery("#doctorString").val()+"&fromDate="+jQuery("#fromDate").val()+"&toDate="+jQuery("#toDate").val()+"&searchPatient="+jQuery("#searchPatient").val(), function(){	QUEUE.initTableHover(); });
		},
		refreshAdmittedQueue : function()
		{
			jQuery("#Admitted_patient_index").load("admittedPatientIndexAjax.htm?ipdWardString="+jQuery("#ipdWardString").val()+"&doctorString="+jQuery("#doctorString").val()+"&fromDate="+jQuery("#fromDate").val()+"&toDate="+jQuery("#toDate").val()+"&searchPatient="+jQuery("#searchPatient").val(), function(){	QUEUE.initTableHover(); });
		}
		
};	

ADMITTED = {
       //ghanshyam 10-june-2013 New Requirement #1847 Capture Vital statistics for admitted patient in ipd
		
                go : function(link)
                {
                    if(opener!=undefined) {
			jQuery(opener.location).attr({href : link});
                    } else {
			jQuery(location).attr({href : link});
                    }
                },
                
                
		 treatmentView : function(id,patientAdmissionLogId)
		{
			if(SESSION.checkSession())
			{
				
				var url = "treatmentView.htm?id="+id+"&patientAdmissionLogId="+patientAdmissionLogId+"&keepThis=false&TB_iframe=true&height=500&width=1000";
				tb_show("Treatment View",url,false);
			}
		},
		
		vitalStatistics : function(id,patientAdmissionLogId)
		{
			if(SESSION.checkSession())
			{
				
				var url = "vitalStatistics.htm?id="+id+"&patientAdmissionLogId="+patientAdmissionLogId+"&keepThis=false&TB_iframe=true&height=550&width=1200";
				tb_show("Daily Vital Statistics",url,false);
			}
		},
                // New requrement NIKDU For IPD patient Intake
                intakeOutput : function(id,patientAdmissionLogId)
		{
			if(SESSION.checkSession())
			{
				
				var url = "intakeView.htm?id="+id+"&patientAdmissionLogId="+patientAdmissionLogId+"&keepThis=false&TB_iframe=true&height=550&width=1200";
				tb_show("Intake and Output Chart",url,false);
			}
		},
                
		transfer : function(id)
		{
			if(SESSION.checkSession())
			{
				
				var url = "transfer.htm?id="+id+"&keepThis=false&TB_iframe=true&height=555&width=1000";
				tb_show("Transfer",url,false);
			}
		},
		discharge: function(id)
		{
			if(SESSION.checkSession())
			{
				
			//	var url = "discharge.htm?id="+id+"&keepThis=false&TB_iframe=true&height=655&width=1000";
			
			var url = "discharge.htm?id="+id+"&keepThis=true&TB_iframe=true&height=600&width=1100";
				tb_show("Discharge",url,false);
				
			}
		},
		print : function(id)
		{
			jQuery("#printArea"+id).printArea({mode: "popup", popClose: true, popTitle: "Support by Crystal Technology Bangladesh (www.cTechbd.com)"});
		},
		submitIpdFinalResult : function(){
			jQuery('#selectedDiagnosisList option').each(function(i) {  
				 jQuery(this).attr("selected", "selected");  
			}); 
			jQuery('#selectedProcedureList option').each(function(i) {  
				 jQuery(this).attr("selected", "selected");  
			});
                        jQuery('#selectedInvestigationList option').each(function(i) {  
				 jQuery(this).attr("selected", "selected");  
			});
			jQuery("#finalResultForm").submit();			
			},
		onChangeDiagnosis : function(container, id, name)
		{
			if(container == 'diagnosis'){
				
				var exists = false;
				jQuery('#selectedDiagnosisList option').each(function(){
				    if (this.value == id) {
				        exists = true;
				        return false;
				    }
				});
				if(exists){
					alert('It\'s existed!');
					return false;
				}
				exists = false;
				jQuery('#availableDiagnosisList option').each(function(){
				    if (this.value == id) {
				        exists = true;
				        return false;
				    }
				});
				jQuery("#diagnosis").val("");
				if(exists){
					jQuery("#availableDiagnosisList option[value=" +id+ "]").appendTo("#selectedDiagnosisList");
					jQuery("#availableDiagnosisList option[value=" +id+ "]").remove();
				}else{
					jQuery('#selectedDiagnosisList').append('<option value="' + id + '">' + name + '</option>');
					// March 15 2015 Khairul Remove pop up
					//if(confirm("Do you want also add this diagnosis to ipd diagnosis?"))
					{
						jQuery.ajax({
							  type: 'POST',
							  url: 'addConceptToWard.htm',
							  data: {opdId: jQuery("#"+container).attr("title"), conceptId: id, typeConcept: 1}
							});
					}
				}
			}
			if(container == 'procedure'){
				var exists = false;
				jQuery('#selectedProcedureList option').each(function(){
				    if (this.value == id) {
				        exists = true;
				        return false;
				    }
				});
				if(exists){
					alert('It\'s existed!');
					return false;
				}
				exists = false;
				jQuery('#availableProcedureList option').each(function(){
				    if (this.value == id) {
				        exists = true;
				        return false;
				    }
				});
				jQuery("#procedure").val("");
				if(exists){
					jQuery("#availableProcedureList option[value=" +id+ "]").appendTo("#selectedProcedureList");
					jQuery("#availableProcedureList option[value=" +id+ "]").remove();
				}else{
					jQuery('#selectedProcedureList').append('<option value="' + id + '">' + name + '</option>');
					// June 12th 2012: Thai Chuong Fixed issue #51
					//if(confirm("Do you want also add this procedure to ipd procedure?"))
					
					{
						jQuery.ajax({
							  type: 'POST',
							  url: 'addConceptToWard.htm',
							  data: {opdId: jQuery("#"+container).attr("title"), conceptId: id, typeConcept: 2}
							});
					}
				}
			}
                        
                        //New Code Add for Investigation
                        
                        if(container == 'investigation'){
				var exists = false;
				jQuery('#selectedInvestigationList option').each(function(){
				    if (this.value == id) {
				        exists = true;
				        return false;
				    }
				});
				if(exists){
					alert('It\'s existed!');
					return false;
				}
				exists = false;
				jQuery('#availableInvestigationList option').each(function(){
				    if (this.value == id) {
				        exists = true;
				        return false;
				    }
				});
				jQuery("#investigation").val("");
				if(exists){
					jQuery("#availableInvestigationList option[value=" +id+ "]").appendTo("#selectedInvestigationList");
					jQuery("#availableInvestigationList option[value=" +id+ "]").remove();
				}else{
					jQuery('#selectedInvestigationList').append('<option value="' + id + '">' + name + '</option>');
					// June 12th 2012: Thai Chuong Fixed issue #51
					//if(confirm("Do you want also add this procedure to ipd procedure?"))
                                        if(confirm)
					{
						jQuery.ajax({
							  type: 'POST',
							  url: 'addConceptToWard.htm',
							  data: {opdId: jQuery("#"+container).attr("title"), conceptId: id, typeConcept: 2}
							});
					}
				}
			}
                        
		}		
};