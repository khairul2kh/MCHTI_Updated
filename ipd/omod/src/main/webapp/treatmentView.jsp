<%-- 
    Document   : treatmentView
    Created on : Sep 30, 2014, 10:20:05 AM
    Author     : Khairul
--%>

<%@ include file="includes/js_css.jsp"%>
<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage IPD" otherwise="/login.htm" redirect="index.htm" />
<%@ include file="/WEB-INF/template/headerMinimal.jsp"%>

<input type="hidden" id="pageId" value="treatmentViewPage" />
<form method="post" id="treatmentViewPage"
      action="treatmentView.htm?patientId=${admitted.patient.patientId}"
      onsubmit="javascript:return validate();">
    <input type="hidden" id="admittedId" name="admittedId" value="${admitted.id }" />


    <div class="box">
        <c:if test="${not empty message }">
            <div class="error">
                <ul>
                    <li>${message}</li>
                </ul>
            </div>
        </c:if>
        <table width="100%">
            <tr>
                <td><spring:message code="ipd.patient.patientName" />:&nbsp;<b>${admitted.patientName}</b></td>
                <td><spring:message code="ipd.patient.patientId" />:&nbsp;<b>${admitted.patientIdentifier}</b></td>
                <td><spring:message code="ipd.patient.age" />:&nbsp;<b>${admitted.age}</b></td>
                <td><spring:message code="ipd.patient.gender" />:&nbsp;<b>${admitted.gender}</b></td>
            </tr>
            <%-- ghanshyam 27-02-2013 Feedback #966[Billing]Add Paid Bill/Add Free Bill for Bangladesh module(remove category from registration,OPD,IPD,Inventory) --%>
            <%-- ghanshyam 27-02-2013 Support #965[IPD]change Tehsil TO Upazila,reomve monthly income field,remove IST Time for Bangladesh module --%>
            <!--
            <tr>
                <td colspan="2"><spring:message code="ipd.patient.category"/>: ${patCategory }</td>
                <td colspan="2"><spring:message code="ipd.patient.monthlyIncome"/>: ${admitted.monthlyIncome}</td>
            </tr>
            -->
            <tr>
                <td colspan="4"><spring:message code="ipd.patient.fatherName" />:${relationName }</td>
            </tr>
            <tr>
                <td colspan="2"><spring:message code="ipd.patient.admittedWard" />:${admitted.admittedWard.name}</td>
                <td colspan="2"><spring:message code="ipd.patient.bedNumber" />:${admitted.bed }</td>
            </tr>

            <tr>
                <!-- ghansham 25-june-2013 issue no # 1924 Change in the address format -->
                <td><spring:message code="ipd.patient.address"/>: ${address } &nbsp;${upazila } &nbsp;${district } </td>
            </tr>
        </table>
    </div>
    <br /> 
    <!--Diagnosis -->
    <table class="box">           

        <tr style="background-color:#BDBDBD;">
            <!---<td><input type="text" id="hSlNo" name="hSlNo" value="S.No" size="7" readonly="readonly"></td>-->
            <td colspan="3"><span style="font-size:20px; color:blue;">&#x2624; Diagnosis</span></td>
        </tr>

        <tr>	
            <td>${diagnosis}</td>
        </tr>		
    </table>  
    </br>
    <!--Investigation -->
    <table class="box">           

        <tr style="background-color:#BDBDBD;">
            <!---<td><input type="text" id="hSlNo" name="hSlNo" value="S.No" size="7" readonly="readonly"></td>-->
            <td><span style="font-size:20px; color:blue;">&#x2624; Investigation</span></td>
        </tr>

        <tr>	
            <td>${investigation}</td>                             
        </tr>	
    </table>
    </br>
    <!--Drug -->
    <table class="box">

        <tr style="background-color:#BDBDBD;">
            <td><span style="font-size:20px; color:blue;">Drug information</span></td>
        </tr>

        <tr>
		<td>  
			${opdDrug}
			</td>
	                         
		</tr>
    </table>

</form>