<%-- 
    Document   : intakeView
    Created on : Mar 21, 2015, 11:11:00 AM
    Author     : cTechbd
--%>
<%@ include file="includes/js_css.jsp"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage IPD" otherwise="/login.htm" redirect="index.htm" />
<%@ include file="/WEB-INF/template/headerMinimal.jsp"%>

<input type="hidden" id="pageId" value="intakeViewPage" />
<form method="post" id="intakeViewPage" action="intakeView.htm?patientId=${admitted.patient.patientId}" onsubmit="javascript:return validate();">
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
            <%--
<tr>
    <td colspan="2"><spring:message code="ipd.patient.category"/>: ${patCategory }</td>
    <td colspan="2"><spring:message code="ipd.patient.monthlyIncome"/>: ${admitted.monthlyIncome}</td>
</tr>
            --%>
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
    <table class="box">        
        <thead>
            <tr>
                <td><input type="text" id="hSlNo" name="hSlNo" value="S.No" size="7" readonly="readonly"></td>
                <td><input type="text" id="hDateTime" name="hDateTime" value="Date/Time" size="21" readonly="readonly"></td>
                <td><input type="text" id="hIntake" name="hIntake" value="Intake" size="30" readonly="readonly" align="center"></td>
                <td><input type="text" id="hOutput" name="hOutput" value="Output" size="110" readonly="readonly" align="center"></td>
            </tr>


            <tr>
                <td><input type="text" id="slNo" name="slNo" value="" size="7" readonly="readonly"></td>
                <td><input type="text" id="dateTime" name="dateTime" value="" size="21" readonly="readonly"></td>

                <td><input value="" size="7" readonly="readonly">
                    <input type="text" value="" size="7" readonly="readonly">
                    <input type="text" value="" size="7" readonly="readonly"></td>               

                <td><input type="text" id="urine" name="urine"  value="Urine" size="20" readonly="readonly">
                    <input type="text" value="Stool" size="9" readonly="readonly">
                    <input type="text" value="Vomiting" size="9" readonly="readonly">
                    <input type="text" id="drain" name="drain"  value="Drain" size="55" readonly="readonly" align="center"></td>
            </tr>

            <tr>
                <td><input type="text" id="slNo" name="slNo" value="" size="7" readonly="readonly"></td>
                <td><input type="text" id="dateTime" name="dateTime" value="" size="21" readonly="readonly"></td>
                <td>
                    <input type="text"  value="Oral" size="7" readonly="readonly">
                    <input type="text"  value="IV" size="7" readonly="readonly">
                    <input type="text"  value="NG Tube" size="7" readonly="readonly">
                </td>               

                <td>
                    <input type="text" value="SPC" size="8" readonly="readonly">
                    <input type="text" value="Catheter" size="8" readonly="readonly">
                    <input type="text" value="" size="9" readonly="readonly">
                    <input type="text" value="" size="9" readonly="readonly">
                    <input type="text" value="Drain-1" size="5" readonly="readonly">  
                    <input type="text" value="Drain-2" size="5" readonly="readonly">
                    <input type="text" value="Drain-3" size="5" readonly="readonly"> 
                    <input type="text" value="Drain-4" size="5" readonly="readonly">
                    <input type="text" value="Drain-5" size="5" readonly="readonly"> 
                    <input type="text" value="Drain-6" size="5" readonly="readonly">
                </td> 
            </tr>
        </thead> 
        <tbody>

            <c:forEach var="ipi" items="${ipdPatientIntake}"
                       varStatus="index">
                <c:choose>
                    <c:when test="${index.count mod 2 == 0}">
                        <c:set var="klass" value="odd" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="klass" value="even" />
                    </c:otherwise>
                </c:choose>
                <tr>

                    <td><input type="text" id="rSlNo" name="rSlNo" value="${index.count}" size="7" readonly="readonly"></td>
                    <td><input type="text" id="rDateTime" name="rDateTime" value="${ipi.createdOn}" size="21" readonly="readonly"></td>
                    <td><input type="text" id="rOral" name="rOral"  value="${ipi.oral}" size="7">
                        <input type="text" id="rIv" name="rIv"  value="${ipi.iv}" size="7">
                        <input type="text" id="rNgTube" name="rNgTube"  value="${ipi.ngTube}" size="7"></td>
                    <td><input type="text" id="rSpc" name="rSpc"  value="${ipi.spc}" size="8">
                        <input type="text" id="rCatheter" name="rCatheter"  value="${ipi.catheter}" size="8">
                        <input type="text" id="rStool" name="rStool"  value="${ipi.stool}" size="9">
                        <input type="text" id="rVomiting" name="rVomiting"  value="${ipi.vomiting}" size="9">
                        <input type="text" id="rDrain1" name="rDrain1"  value="${ipi.drain1}" size="5"> 
                        <input type="text" id="rDrain2" name="rDrain2"  value="${ipi.drain2}" size="5">
                        <input type="text" id="rDrain3" name="rDrain3"  value="${ipi.drain3}" size="5"> 
                        <input type="text" id="rDrain4" name="rDrain4"  value="${ipi.drain4}" size="5">
                        <input type="text" id="rDrain5" name="rDrain5"  value="${ipi.drain5}" size="5">
                        <input type="text" id="rDrain6" name="rDrain6"  value="${ipi.drain6}" size="5"></td>
                </tr>
            </c:forEach>       

            
            <tr>
                <td><input type="text" id="slNo" name="slNo" value="" size="7" readonly="readonly"></td>
                <td><input type="text" id="dateTime" name="dateTime" value="" size="21" readonly="readonly"></td>
                <td><input type="text" id="oral" name="oral"  value="" size="7">
                <input type="text" id="iv" name="iv"  value="" size="7">
                <input type="text" id="ngTube" name="ngTube"  value="" size="7"></td>
                <td><input type="text" id="spc" name="spc"  value="" size="8">
                <input type="text" id="catheter" name="catheter"  value="" size="8">
                <input type="text" id="stool" name="stool"  value="" size="9">
                <input type="text" id="vomiting" name="vomiting"  value="" size="9">
                <input type="text" id="drain1" name="drain1"  value="" size="5"> 
                <input type="text" id="drain2" name="drain2"  value="" size="5">
                <input type="text" id="drain3" name="drain3"  value="" size="5"> 
                <input type="text" id="drain4" name="drain4"  value="" size="5">
                <input type="text" id="drain5" name="drain5"  value="" size="5">
                <input type="text" id="drain6" name="drain6"  value="" size="5"></td>
            </tr>
        </tbody>
    </table>
            
            
                <table width="98%">
                    <%-- ghanshyam 27-sept-2012 Support #387 [ALL] Small changes in all modules(note:these lines of code written for cancel button) --%>
                    <div align="right">
                        <input type="submit"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="Submit"> <input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="Cancel" onclick="tb_cancel();">
                    </div>
    </table>
</form>
