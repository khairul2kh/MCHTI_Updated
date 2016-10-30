<%--
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
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage IPD" otherwise="/login.htm" redirect="index.htm" />
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="includes/js_css.jsp" %>

<style>
    .ui-button { margin-left: -1px; }
    .ui-button-icon-only .ui-button-text { padding: 0.35em; } 
    .ui-autocomplete-input { margin: 0; padding: 0.48em 0 0.47em 0.45em; }
	
	form {
        top:0;
	padding:50px;
     }
	 
	 .button {
        -moz-box-shadow: 0px 0px 0px 2px #9fb4f2;
        -webkit-box-shadow: 0px 0px 0px 2px #9fb4f2;
        box-shadow: 0px 0px 0px 2px #9fb4f2;
        background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #7892c2), color-stop(1, #476e9e));
        background:-moz-linear-gradient(top, #7892c2 5%, #476e9e 100%);
        background:-webkit-linear-gradient(top, #7892c2 5%, #476e9e 100%);
        background:-o-linear-gradient(top, #7892c2 5%, #476e9e 100%);
        background:-ms-linear-gradient(top, #7892c2 5%, #476e9e 100%);
        background:linear-gradient(to bottom, #7892c2 5%, #476e9e 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#7892c2', endColorstr='#476e9e',GradientType=0);
        background-color:#7892c2;
        -moz-border-radius:10px;
        -webkit-border-radius:10px;
        border-radius:10px;
        border:1px solid #4e6096;
        display:inline-block;
        cursor:pointer;
        color:#ffffff;
        font-family:arial;
        font-size:18px;
        padding:8px 28px;
        text-decoration:none;
        text-shadow:0px 1px 0px #283966;
    }
	 
	 
</style>

<input type="hidden" id="pageId" value="dischagePage"/>
<form method="post" id="dischargeForm">
    <input type="hidden" id="id" name="admittedId" value="${admitted.id }" />
    <!-- harsh 14/6/2012: to get patient ID -->
    <input type="hidden" id="patientId" name="patientId" value="${patientId}" />

    <div class="box">
        <c:if test ="${not empty message }">
            <div class="error">
                <ul>
                    <li>${message}</li>   
                </ul>
            </div>
        </c:if>
        <table width="100%">
            <tr>
                <td><spring:message code="ipd.patient.patientName"/>:&nbsp;<b>${admitted.patientName }</b></td>
                <td><spring:message code="ipd.patient.patientId"/>:&nbsp;<b>${admitted.patientIdentifier}</b></td>
                <td><spring:message code="ipd.patient.age"/>:&nbsp;<b>${admitted.age}</b></td>
                <td><spring:message code="ipd.patient.gender"/>:&nbsp;<b>${admitted.gender }</b></td>
            </tr>
            <%-- ghanshyam 27-02-2013 Feedback #966[Billing]Add Paid Bill/Add Free Bill for Bangladesh module(remove category from registration,OPD,IPD,Inventory) --%>
            <%-- ghanshyam 27-02-2013 Support #965[IPD]change Tehsil TO Upazila,reomve monthly income field,remove IST Time for Bangladesh module --%>
            <%--
            <tr>
                    <td colspan="2"><spring:message code="ipd.patient.category"/>: ${patCategory }</td>
                    <td colspan="2"><spring:message code="ipd.patient.monthlyIncome"/>: ${admitted.monthlyIncome}</td>
            </tr>
            --%>
            <%-- ghanshyam 10/07/2012 New Requirement #312 [IPD] Add fields in the Discharge screen and print out --%>
            <tr>
                <td>${relationType }:&nbsp;${relationName }</td>
                <td colspan="2"><spring:message code="ipd.patient.bedNumber"/>: ${admitted.bed }</td>
            </tr>
            <tr>
                <td colspan="2"><spring:message code="ipd.patient.admittedWard"/>: ${admitted.admittedWard.name}</td>
            </tr>
            <%-- ghanshyam 10/07/2012 New Requirement #312 [IPD] Add fields in the Discharge screen and print out --%>
            <tr>
                <!-- ghansham 25-june-2013 issue no # 1924 Change in the address format -->
                <td><spring:message code="ipd.patient.address"/>: ${address } &nbsp;${upazila } &nbsp;${district } </td> 
            </tr>
            <tr>
                <td colspan="4"><spring:message code="ipd.patient.date/time"/>: ${dateTime }</td>
            </tr>
        </table>

    </div>
    <br/>
    <table class="box">
        <tr><td colspan="3">
                <strong><spring:message code="patientdashboard.clinicalSummary.diagnosis"/>:</strong><em>*</em>
                <input class="ui-autocomplete-input ui-widget-content ui-corner-all" id="diagnosis" title="${opd.conceptId}" style="width:290px" name="diagnosis"/>
            </td>
        </tr>
        <tr>
            <td>
                <!-- List of all available DataElements -->
                <div id="divAvailableDiagnosisList">
                    <select size="8" style="width:400px;" id="availableDiagnosisList" name="availableDiagnosisList" multiple="multiple" style="min-width:25em;height:10em" ondblclick="moveSelectedById('availableDiagnosisList', 'selectedDiagnosisList');">
                        <c:forEach items="${listDiagnosis}" var="diagnosis">
                            <option value="${diagnosis.id}" >${diagnosis.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </td>
            <td>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&gt;"  style="width:50px" onclick="moveSelectedById('availableDiagnosisList', 'selectedDiagnosisList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&lt;"  style="width:50px" onclick="moveSelectedById('selectedDiagnosisList', 'availableDiagnosisList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&gt;&gt;"  style="width:50px" onclick="moveAllById('availableDiagnosisList', 'selectedDiagnosisList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&lt;&lt;"  style="width:50px" onclick="moveAllById('selectedDiagnosisList', 'availableDiagnosisList');"/>
            </td>			
            <td>
                <!-- List of all selected DataElements -->
                <select size="8" style="width:400px;" id="selectedDiagnosisList" name="selectedDiagnosisList" multiple="multiple" style="min-width:25em;height:10em" ondblclick="moveSelectedById('selectedDiagnosisList', 'availableDiagnosisList');">
                    <c:forEach items="${sDiagnosisList}" var="ss">
                        <option value="${ss.id}" >${ss.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>





        <tr><td colspan="3">
                <div class="ui-widget">
                    <strong><spring:message code="patientdashboard.procedures"/>:</strong>
                    <input class="ui-autocomplete-input ui-widget-content ui-corner-all"  title="${opd.conceptId }"  id="procedure" style="width:300px" name="procedure"/>
                </div>

            </td></tr>
        <tr>
            <td>
                <!-- List of all available DataElements -->
                <div id="divAvailableProcedureList">
                    <select size="8" style="width:400px;" id="availableProcedureList" name="availableProcedureList" multiple="multiple" style="min-width:25em;height:10em" ondblclick="moveSelectedById('availableProcedureList', 'selectedProcedureList');">
                        <c:forEach items="${listProcedures}" var="procedure">
                            <option value="${procedure.conceptId}" >${procedure.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </td>
            <td>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&gt;"  style="width:50px" onclick="moveSelectedById('availableProcedureList', 'selectedProcedureList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&lt;"  style="width:50px" onclick="moveSelectedById('selectedProcedureList', 'availableProcedureList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&gt;&gt;"  style="width:50px" onclick="moveAllById('availableProcedureList', 'selectedProcedureList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&lt;&lt;"  style="width:50px" onclick="moveAllById('selectedProcedureList', 'availableProcedureList');"/>
            </td>			
            <td>
                <!-- List of all selected DataElements -->
                <select size="8" style="width:400px;" id="selectedProcedureList" name="selectedProcedureList" multiple="multiple" style="min-width:25em;height:5em" ondblclick="moveSelectedById('selectedProcedureList', 'availableProcedureList')">
                    <c:forEach items="${sProcedureList}" var="xx">
                        <option value="${xx.id}" >${xx.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <!-- new Code -->
        <tr><td colspan="3">
                <strong>Investigation :</strong><em>*</em>
                <input class="ui-autocomplete-input ui-widget-content ui-corner-all" id="investigation" title="${opd.conceptId}" style="width:290px" name="investigation"/>
            </td>
        </tr>


        <tr>
            <td>
                <!-- List of all available DataElements -->
                <div id="divAvailableInvestigationList">
                    <select size="8" style="width:400px;" id="availableInvestigationList" name="availableInvestigationList" multiple="multiple" style="min-width:25em;height:10em" ondblclick="moveSelectedById('availableInvestigationList', 'selectedInvestigationList');">
                        <c:forEach items="${listInvestigations}" var="investigation">
                            <option value="${investigation.conceptId}" >${investigation.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </td>
            <td>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&gt;"  style="width:50px" onclick="moveSelectedById('availableInvestigationList', 'selectedInvestigationList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&lt;"  style="width:50px" onclick="moveSelectedById('selectedInvestigationList', 'availableInvestigationList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&gt;&gt;"  style="width:50px" onclick="moveAllById('availableInvestigationList', 'selectedInvestigationList');"/><br/>
                <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="&lt;&lt;"  style="width:50px" onclick="moveAllById('selectedInvestigationList', 'availableInvestigationList');"/>
            </td>			
            <td>
                <!-- List of all selected DataElements -->
                <select size="8" style="width:400px;" id="selectedInvestigationList" name="selectedInvestigationList" multiple="multiple" style="min-width:25em;height:5em" ondblclick="moveSelectedById('selectedInvestigationList', 'availableInvestigationList')">
                    <c:forEach items="${sInvestigationList}" var="aa">
                        <option value="${aa.id}" >${aa.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>


        <!-- end   ----------->


        <tr>
            <td colspan="2">Out come<em>*</em>
                <select  id="outCome" name="outCome" >
                    <option value="">[Please Select]</option>
                    <c:forEach items="${listOutCome}" var="outCome" >
                        <option value="${outCome.answerConcept.id}">${outCome.answerConcept.name }</option>
                    </c:forEach>				  
                </select></td>
            <td></td>
        </tr>
        <!--  Kesavulu loka 26/06/2013 # 1926 One text filed for otherInstructions. -->
        <div class="ui-widget">
            <table>
                <b>Other Instructions:
                    <tr>
                        <td><input id="otherInstructions" class="ui-autocomplete-input ui-widget-content ui-corner-all ac_input" name="otherInstructions" 
                                   style="width:910px; height:50px" title="" autocomplete="off"></td>
                        <!--  <td><TEXTAREA NAME="otherInstructions" id="otherInstructions"  ROWS=3 COLS=130 >		</TEXTAREA></td> -->
                    </tr>
            </table>
        </div>
    </table>

    <table  width="98%">
        <%-- ghanshyam 27-sept-2012 Support #387 [ALL] Small changes in all modules(note:these lines of code written for cancel button) --%>
        <div align="right">
            <input type="submit" class="button" value="Submit" onclick="ADMITTED.submitIpdFinalResult();">
            <input type="button" class="button" value="Cancel" onclick="tb_cancel();">
        </div>	
    </table>
	<br/>

</form>