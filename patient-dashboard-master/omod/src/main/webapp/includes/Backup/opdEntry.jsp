
<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-dashboard module.
 *
 *  Patient-dashboard module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-dashboard module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-dashboard module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="View PatientDashboard"
                 otherwise="/login.htm" redirect="index.htm" />
<!-- this for NIKDU
<style type="text/css">
    .drug-order {
        width: 125%;
    }

    .drugs {
        width: 190px;
        height: 20%;
        float: left;
    }

    .formulation {
        width: 200px;
        height: 20%;
        float: left;
        font-size:16px;
    }

    .frequency {
        width: 380px;
        height: 20%;
        float: left;
        font-size:16px;
    }

    .no-of-days {
        width: 120px;
        height: 20%;
        float: left;
    }

    .ui-button {
        margin-left: -1px;
    }

    .ui-button-icon-only .ui-button-text {
        padding: 0.35em;
    }

    .ui-autocomplete-input {
        margin: 0;
        padding: 0.48em 0 0.47em 0.45em;

    }
    .ui-widget-content { 
        border: 1px solid #1aac9b; 
        background: #ffffff/*
            }
        </style>
        
        -->
        
        
   <style type="text/css">
    .drug-order {
        width: 125%;
    }

    .drugs {
        width: 190px;
        height: 59px;
        float: left;
    }

    .formulation {
        width: 200px;
        height: 20%;
        float: left;
        font-size:16px;
    }

    .frequency {
        width: 210px;
        height: 20%;
        float: left;
        font-size:16px;
    }

    .no-of-days { 
        width: 120px;
        height: 20%;
        float: left;
    }

    .ui-button {
        margin-left: -1px;
    }

    .ui-button-icon-only .ui-button-text {
        padding: 0.35em;
    }

    .ui-autocomplete-input {
        margin: 0;
        padding: 0.48em 0 0.47em 0.45em;

    }
    .ui-widget-content { 
        border: 1px solid #1aac9b; 
        background: #ffffff/*
            }
        </style>


        <script type="text/javascript">
            function addDrugOrder() {
                var drugName = document.getElementById('drugName').value;
                if (drugName == null || drugName == "") {
                    alert("Please enter drug name");
                    return false;
                } else {
                    var formulation = document.getElementById('formulation').value;
                    if (formulation == null || formulation == "") {
                        alert("Please select formulation");
                        return false;
                    }
                    var formulationArr = formulation.split(",");
                    var frequency = document.getElementById('frequency').value;
                    if (frequency == null || frequency == "") {
                        alert("Please select frequency");
                        return false;
                    }
                    var frequencyArr = frequency.split(".");
                    var noOfDays = document.getElementById('noOfDays').value;
                    if (noOfDays == null || noOfDays == "") {
                        alert("Please enter no of days");
                        return false;
                    }
                    if (noOfDays != null || noOfDays != "") {
                        if (isNaN(noOfDays)) {
                            alert("Please enter no of days in correct format");
                            return false;
                        }
                    }
                    var comments = document.getElementById('comments').value;
                    var deleteString = 'deleteInput(\"' + drugName + '\")';
                    var htmlText = "<div id='com_" + drugName + "_div'>"
                            + "<input id='" + drugName + "_name'  name='drugOrder' type='text' size='20' value='" + drugName + "'  readonly='readonly'/>&nbsp;&nbsp;"
                            + "<input id='" + drugName + "_formulationName'  name='" + drugName + "_formulatioNname' type='text' size='20' value='" + formulationArr[0] + "'  readonly='readonly'/>&nbsp;&nbsp;"
                            + "<input id='" + drugName + "_frequencyName'  name='" + drugName + "_frequencyName' type='text' size='20' value='" + frequencyArr[0] + "'  readonly='readonly'/>&nbsp;&nbsp;"
                            + "<input id='" + drugName + "_noOfDays'  name='" + drugName + "_noOfDays' type='text' size='17' value='" + noOfDays + "'  readonly='readonly'/>&nbsp;&nbsp;"
                            + "<input id='" + drugName + "_comments'  name='" + drugName + "_comments' type='text' size='17' value='" + comments + "'  readonly='readonly'/>&nbsp;&nbsp;"
                            + "<input id='" + drugName + "_formulationId'  name='" + drugName + "_formulationId' type='hidden' value='" + formulationArr[1] + "'/>&nbsp;"
                            + "<input id='" + drugName + "_frequencyId'  name='" + drugName + "_frequencyId' type='hidden' value='" + frequencyArr[1] + "'/>&nbsp;"
                            + "<a style='color:red' href='#' onclick='" + deleteString + "' >[X]</a>"
                            + "</div>";

                    var newElement = document.createElement('div');
                    newElement.setAttribute("id", drugName);
                    newElement.innerHTML = htmlText;
                    var fieldsArea = document.getElementById('headerValue');
                    fieldsArea.appendChild(newElement);
                    jQuery("#drugName").val("");
                    jQuery("#formulation").val("");
                    jQuery("#frequency").val("");
                    jQuery("#noOfDays").val("");
                    jQuery("#comments").val("");
                }
            }

            function deleteInput(drugName) {
                var parentDiv = 'headerValue';
                var child = document.getElementById(drugName);
                var parent = document.getElementById(parentDiv);
                parent.removeChild(child);
            }
        </script>
		
		
	<style>

.kha-aero {
    margin-left:auto;
    margin-right:auto;

    max-width: 95%;
    background: #D2E9FF;
    padding: 20px 20px 20px 20px;
    font: 16px Arial, Tahoma, Helvetica, sans-serif;
    color: #000000;
	line-height: 150%;
}
.kha-aero h1 {
    font: 24px "Tahoma", Arial, Helvetica, sans-serif;
    padding: 10px 10px 10px 20px;
    display: block;
    background: #80C2FF;
    border-bottom: 1px solid #B8DDFF;
    margin: -20px -20px 15px;
}
.kha-aero h1>span {
    display: block;
    font-size: 11px;
}

.kha-aero label>span {
    float: left;
    margin-top: 10px;
    color: #5E5E5E;
}
.kha-aero label {
    display: block;
    margin: 0px 0px 5px;
}
.kha-aero label>span {
    float: left;
    width: 20%;
    text-align: right;
    padding-right: 15px;
    margin-top: 10px;
    font-weight: bold;
}
.kha-aero input[type="text"], .kha-aero input[type="email"],  .kha-aero select {
    color: #888;
    
    padding: 0px 0px 0px 5px;
    border: 1px solid #C5E2FF;
    background: #FBFBFB;
    outline: 0;
    -webkit-box-shadow:inset 0px 1px 6px #ECF3F5;
    box-shadow: inset 0px 1px 6px #ECF3F5;
    font: 200 12px/25px Arial, Helvetica, sans-serif;
    height: 30px;
    line-height:15px;
    margin: 2px 6px 16px 0px;
}


.kha-aero textarea{
    height:50px;
    padding: 5px 0px 0px 5px;
    width: 200px;
}
.kha-aero select {
    background: #fbfbfb url('down-arrow.png') no-repeat right;
    background: #fbfbfb url('down-arrow.png') no-repeat right;
   appearance:none;
    -webkit-appearance:none; 
   -moz-appearance: none;
    text-indent: 0.01px;
    text-overflow: '';
    width: 70%;
}
.kha-aero .button{
    padding: 10px 30px 10px 30px;
    background: #66C1E4;
    border: none;
    color: #FFF;
    box-shadow: 1px 1px 1px #4C6E91;
    -webkit-box-shadow: 1px 1px 1px #4C6E91;
    -moz-box-shadow: 1px 1px 1px #4C6E91;
    text-shadow: 1px 1px 1px #5079A3;
    
}
.kha-aero .button:hover{
    background: #3EB1DD;
}

</style>	
		
       <!-- <b class="boxHeader" style="font-size:18px">Doctor Treatment Form</b> -->
        <form class="kha-aero" method="post" action="opdEntry.htm" id="opdEntryForm">
		 <h1>Doctor Treatment Form</h1>
            <input type="hidden" name="patientId" value="${patientId }" /> <input
                type="hidden" name="opdId" value="${opd.conceptId }" /> <input
                type="hidden" name="queueId" id="queueId" value="${queueId }" /> <input
                type="hidden" name="referralId" value="${referral.conceptId }" />


            <table cellspacing="8">
                <!--  
                <tr align="right">
                        <td colspan="3"><c:if test="${not empty queueId }">
                                        <input type="submit" value="Conclude visit"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                onclick="DASHBOARD.submitOpdEntry();" />
                                        <input type="submit"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Back" onclick="DASHBOARD.backToQueue('${queueId}');" />
                </c:if></td>
        </tr>
                -->


                <script>
                    $(document).ready(function() {



                        $("input").focus(function() {
                            $(this).css("background-color", "#cccccc");
                        });
                        $("input").blur(function() {
                            $(this).css("background-color", "#F7F778");
                        });

                        $("select").focus(function() {
                            $(this).css("background-color", "#cccccc");
                        });

                        $("select").blur(function() {
                            $(this).css("background-color", "#F7F778");
                        });

                    });

                </script>



                <tr>
                    <td colspan="3" style="font-size:14px"><strong></strong> &nbsp; &nbsp; 
                        <input placeholder=" Click/Enter Here....." 
                               type="text" id="ccomplain" name="ccomplain" size="200"
                               style="width: 1145px; height: 40px; font-size:16px" 
                               class="ui-autocomplete-input  ui-corner-all ac_input" />
                    </td>
                </tr>

                <!--
                <tr>
                        <td colspan="3" style="font-size:16px"><strong>Chief Complain:</strong> <textarea
                    type="text" id="note" name="note" size="200"
                    style="width: 1035px; height: 50px; font-size:16px"
                    />
            </td>
                </tr>
                -->

                <tr>
                    <td colspan="3" style="font-size:15px"><strong>Provisional Diagnosis:</strong><em>*</em>
                        <input placeholder="Click/Enter Here....." class="ui-autocomplete-input ui-widget-content ui-corner-all"
                               id="diagnosis" title="${opd.conceptId}" style="width: 350px; font-size:16px"
                               name="diagnosis" />
                        <span style="font-size:20px; font-weight:bold;">&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Selected Service</span>
                    </td>

                </tr>
                <tr>
                    <td width="50%">
                        <!-- List of all available DataElements -->
                        <div id="divAvailableDiagnosisList">
                            <select size="4" style="width: 560px" id="availableDiagnosisList"
                                    name="availableDiagnosisList" multiple="multiple"
                                    style="min-width:25em;height:10em"
                                    ondblclick="moveSelectedById('availableDiagnosisList', 'selectedDiagnosisList');">
                                <c:forEach items="${diagnosisList}" var="diagnosis">
                                    <option value="${diagnosis.id}">${diagnosis.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                    <td width="5%"><input type="button" value="&gt;"
                                          class="ui-button ui-widget ui-state-default ui-corner-all"
                                          style="width: 50px"
                                          onclick="moveSelectedById('availableDiagnosisList', 'selectedDiagnosisList');" /><br />
                        <input type="button" value="&lt;"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               style="width: 50px"
                               onclick="moveSelectedById('selectedDiagnosisList', 'availableDiagnosisList');" />
                        <!-- 
                        <input type="button" value="&gt;&gt;"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        style="width: 50px"
                        onclick="moveAllById( 'availableDiagnosisList', 'selectedDiagnosisList' );" /><br />
                        <input type="button" value="&lt;&lt;"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        style="width: 50px"
                        onclick="moveAllById( 'selectedDiagnosisList', 'availableDiagnosisList' );" />
                        -->
                    </td>
                    <td>
                        <!-- List of all selected DataElements --> <select
                            id="selectedDiagnosisList" size="4" style="width: 550px"
                            name="selectedDiagnosisList" multiple="multiple"
                            style="min-width:25em;height:10em"
                            ondblclick="moveSelectedById('selectedDiagnosisList', 'availableDiagnosisList');">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="font-size:15px">
                        <div class="ui-widget">
                            <strong>Post for procedure: &nbsp;</strong> <input placeholder="Click/Enter Here....."
                                                                               class="ui-autocomplete-input ui-widget-content ui-corner-all"
                                                                               title="${opd.conceptId }" id="procedure" style="width: 390px; font-size:16px"
                                                                               name="procedure" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <!-- List of all available DataElements -->
                        <div id="divAvailableProcedureList">
                            <select size="4" style="width: 560px" id="availableProcedureList"
                                    name="availableProcedureList" multiple="multiple"
                                    style="min-width:25em;height:5em"
                                    ondblclick="moveSelectedById('availableProcedureList', 'selectedProcedureList');">
                                <c:forEach items="${listProcedures}" var="procedure">
                                    <option value="${procedure.conceptId}">${procedure.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                    <td><input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="&gt;" style="width: 50px"
                               onclick="moveSelectedById('availableProcedureList', 'selectedProcedureList');" /><br />
                        <input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="&lt;" style="width: 50px"
                               onclick="moveSelectedById('selectedProcedureList', 'availableProcedureList');" />
                        <!--  
                        <input type="button"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        value="&gt;&gt;" style="width: 50px"
                        onclick="moveAllById( 'availableProcedureList', 'selectedProcedureList' );" /><br />
                        <input type="button"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        value="&lt;&lt;" style="width: 50px"
                        onclick="moveAllById( 'selectedProcedureList', 'availableProcedureList' );" />
                        --></td>
                    <td>
                        <!-- List of all selected DataElements --> <select size="4"
                                                                           style="width: 550px" id="selectedProcedureList"
                                                                           name="selectedProcedureList" multiple="multiple"
                                                                           style="min-width:25em;height:5em"
                                                                           ondblclick="moveSelectedById('selectedProcedureList', 'availableProcedureList')">
                        </select>
                    </td>
                </tr>
                <!-- ghanshyam 1-june-2013 New Requirement #1633 User must be able to send investigation orders from dashboard to billing -->
                <tr>
                    <td colspan="4" style="font-size:15px">
                        <div class="ui-widget">
                            <strong>Investigation:</strong> &nbsp; <input placeholder="Click/Enter Here....."
                                                                          class="ui-autocomplete-input ui-widget-content ui-corner-all"
                                                                          title="${opd.conceptId}" id="investigation" style="width: 435px; font-size:16px"
                                                                          name="investigation" />
                        </div></td>
                </tr>
                <tr>
                    <td>
                        <!-- List of all available Tests -->
                        <div id="divAvailableInvestigationList">
                            <select size="4" style="width: 560px"
                                    id="availableInvestigationList" name="availableInvestigationList"
                                    multiple="multiple" style="min-width:25em;height:5em"
                                    ondblclick="moveSelectedById('availableInvestigationList', 'selectedInvestigationList');">
                                <c:forEach items="${listInvestigations}" var="investigation">
                                    <option value="${investigation.conceptId}">${investigation.name}</option>
                                </c:forEach>
                            </select>
                        </div></td>
                    <td><input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="&gt;" style="width: 50px"
                               onclick="moveSelectedById('availableInvestigationList', 'selectedInvestigationList');" /><br />
                        <input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="&lt;" style="width: 50px"
                               onclick="moveSelectedById('selectedInvestigationList', 'availableInvestigationList');" />
                        <!--  
                        <input type="button"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        value="&gt;&gt;" style="width: 50px"
                        onclick="moveAllById( 'availableInvestigationList', 'selectedInvestigationList' );" /><br />
                        <input type="button"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        value="&lt;&lt;" style="width: 50px"
                        onclick="moveAllById( 'selectedInvestigationList', 'availableInvestigationList' );" />
                        --></td>
                    <td>
                        <!-- List of all selected DataElements --> <select size="4"
                                                                           style="width: 550px" id="selectedInvestigationList"
                                                                           name="selectedInvestigationList" multiple="multiple"
                                                                           style="min-width:25em;height:5em;"
                                                                           ondblclick="moveSelectedById('selectedInvestigationList', 'availableInvestigationList')">
                        </select></td>
                </tr>
                <!-- ghanshyam 12-june-2013 New Requirement #1635 User should be able to send pharmacy orders to issue drugs to a patient from dashboard -->

            </table>


            <table>	

                <!--Drug List -->


                <tr>
                    <td colspan="3" style="font-size:18px">
                        <div class="ui-widget">
                            <strong>Drug:</strong>
                        </div></td>
                </tr>
                <tr>
                    <td width="80%">
                        <div class="" id="drugOrder"
                             style="background: #FFFFFF; border: 1px #808080 solid; padding: 0.3em; margin: 0.3em 0em; min-width: 20em; height: 3em;">
                            <div class="drugs">
                                <input style="font-size:16px" title="${opd.conceptId}" id="drugName" name="drugName"
                                       placeholder="Search for drugs" onblur="ISSUE.onBlur(this);" />
                            </div>

                            <div class="formulation" id="divFormulation">
                                <select id="formulation" name="formulation">
                                    <option value="" style="font-size:16px">
                                        Select Formulation
                                    </option>
                                </select>
                            </div>

                            <div class="frequency">
                                <select id="frequency" name="frequency">
                                    <option value="" style="font-size:16px;">Select Frequency</option>
                                    <c:forEach items="${drugFrequencyList}" var="dfl">
                                        <option value="${dfl.name}.${dfl.conceptId}">${dfl.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="no-of-days">
                                <input type="text" id="noOfDays" name="noOfDays"
                                       placeholder="No Of Days" size="10" style="font-size:16px">
                            </div>
                            <!--
                            <div class="comments">
                                    <TEXTAREA style="font-size:16px" id="comments" name="comments" placeholder="Comments"
                                            rows=1 cols=10></TEXTAREA>
                            </div>  -->
                            <div class="comments">
                                <input type="text"  id="comments" name="comments" placeholder="Comments" />
						    </div>
                            

                        </div>


                    </td>

                    <td>
                        <div class="add"> &nbsp;&nbsp;&nbsp;
                            <input type="button"
                                   class="ui-button ui-widget ui-state-default ui-corner-all"
                                   value="Add" onClick="addDrugOrder();" />
                        </div>

                    </td>
                </tr>

                <tr>	
                    <td>
					
                        <div id="headerValue"
                             style="background: #FFFFFF; border: 0px #808080 solid; padding: 0.3em; margin: 0.3em 0em; width: 100%;">


                            <input type='hidden' id="drug" name="drug" value='Drugs' size="20"
                                   readonly="readonly" />&nbsp; 
                            <input type='hidden' id="formulation"
                                   name='formulation' value="Formulation" size="20"
                                   readonly="readonly" />&nbsp;&nbsp;&nbsp;&nbsp; 
                            <input type='hidden' id='frequency'
                                   name='frequency' value='Frequency' size="20" readonly="readonly" />&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type='hidden' id='noOfDays' name='noOfDays' value='No Of Days'
                                   size="17" readonly="readonly" />&nbsp;&nbsp;&nbsp; 
                            <input type='hidden'
                                   id='comments' name='comments' value='Comments' size="32"
                                   readonly="readonly" />&nbsp;


                        </div></td>  


                </tr>


                <!-- ghanshyam 8-july-2013 New Requirement #1963 Redesign patient dashboard -->
                <tr>
                    <td colspan="3" style="font-size:14px"><strong>Other Instructions:</strong> <input
                            type="text" id="note" name="note" size="200"
                            style="width: 500px; height: 40px"
                            class="ui-autocomplete-input ui-widget-content ui-corner-all ac_input" />


                        <strong>&nbsp;&nbsp;&nbsp; Internal referral:</strong> <select id="internalReferral" name="internalReferral">
                            <option value="-1" style="font-size:16px">--Select--</option>
                            <c:forEach items="${listInternalReferral}" var="internalReferral">
                                <option value="${internalReferral.answerConcept.id}">${internalReferral.answerConcept.name}</option>
                            </c:forEach>
                        </select> External referral: <select id="externalReferral"
                                                             name="externalReferral">
                            <option value="-1">--Select--</option>
                            <c:forEach items="${listExternalReferral}" var="externalReferral">
                                <option value="${externalReferral.answerConcept.id}">${externalReferral.answerConcept.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="font-size:14px"><strong>OPD Visit Outcome:</strong><em>*</em>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="font-size:14px"><input type="radio" name="radio_f"
                                                                  id="input_follow" value="follow"
                                                                  onclick="DASHBOARD.onChangeRadio(this);">Follow up <input
                                                                  type="text" class="date-pick left" readonly="readonly"
                                                                  ondblclick="this.value = '';" name="dateFollowUp" id="dateFollowUp"
                                                                  onclick="DASHBOARD.onClickFollowDate(this);"> <input
                                                                  type="radio" name="radio_f" value="cured"
                                                                  onclick="DASHBOARD.onChangeRadio(this);">Cured <input
                                                                  type="radio" name="radio_f" value="died"
                                                                  onclick="DASHBOARD.onChangeRadio(this);">Died <input
                                                                  type="radio" name="radio_f" value="reviewed"
                                                                  onclick="DASHBOARD.onChangeRadio(this);">Reviewed 
																  
															
							<c:if test="${empty admitted}">
                            <input type="radio" name="radio_f" value="admit" title="Admission Request"
                                   onclick="DASHBOARD.onChangeRadio(this);">Request For Admission
                        </c:if> 
                    </td>
					<!--
                    <td align="left" class="tdIpdWard" style='display: none; font-size:14px;'><select
                            id="ipdWard" name="ipdWard">
                            <option value="-1" style="font-size:16px">--Select--</option>
                            <c:if test="${not empty listIpd }">
                                <c:forEach items="${listIpd}" var="ipd">
                                    <option value="${ipd.answerConcept.id}">${ipd.answerConcept.name}</option>

                                </c:forEach>
                            </c:if>
                        </select>
                    </td>  -->
                </tr>
                <tr>
                    <td colspan="3" ><c:if test="${not empty queueId }">
                            <input type="submit" value="Conclude visit"
                                   class="ui-button ui-widget ui-state-default ui-corner-all"
                                   onclick="DASHBOARD.submitOpdEntry();" />
                            <input type="submit"
                                   class="ui-button ui-widget ui-state-default ui-corner-all"
                                   value="Back" onclick="DASHBOARD.backToQueue('${queueId}');" />
								   
								   <input type="submit"
                                   class="ui-button ui-widget ui-state-default ui-corner-all"
                                   value="New Back" onclick="getConfirmation();" />
                        </c:if>
                    </td>
                </tr>
            </table>
			
			<script>
			function getConfirmation()
{
    var retVal = confirm("Do you want to continue ?");
    if (retVal == true)
    {
        window.location.href = ('backToOpdQueue.htm?queueId=');
		<!--('backToOpdQueue.htm?queueId='+queueId);               "DASHBOARD.backToQueue('${queueId}');"   main.htm?opdId=4147 -->
        
    } 
    else
    {
        
        return false;
    }
}
			</script>
			
			
        </form>
		

