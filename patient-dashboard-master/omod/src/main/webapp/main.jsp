
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
<%@page import="org.openmrs.ConceptName"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="View PatientDashboard"
                 otherwise="/login.htm" redirect="index.htm" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="includes/js_css.jsp"%>
<input type="hidden" id="pageId" value="patientDashboard" />

<b class="boxHeader">Dashboard</b>
<div class="box">
    <table cellspacing="10" width="100%">
        <tr>
            <td width="20%"><b>Patient ID:</b>
                ${patient.patientIdentifier.identifier}</td>
            <td width="40%"><c:if test="${not empty admittedStatus }">
                    <span style="background-color: red; color: white; font-size:16px;">Admitted To : &nbsp; ${admittedWard.name} &nbsp; &nbsp; Bed No : &nbsp; ${bed}</span>

                </c:if>
            </td>
            <td width="20%"><b>Location:</b> ${opd.name }</td>
        </tr>
        <tr>
            <td width="20%"><b>Name:</b>
                <span style="font-weight:bold; font-size:18px">${patient.givenName}&nbsp;&nbsp;${patient.middleName}&nbsp;&nbsp;
                    ${patient.familyName}</span></td>
            <td width="20%"><b>Age:</b> ${age }</td>
            <td width="30%"><b>Gender:</b> ${patient.gender }</td>
        </tr>
        <%-- ghanshyam 16-06-2012 Bug #44 OPD Dashboard/ Patient category,PatientTemporary category is not being displayed --%>
        <%-- ghanshyam 27-02-2013 Feedback #966[Billing]Add Paid Bill/Add Free Bill for Bangladesh module(remove category from registration,OPD,IPD,Inventory) --%>
        <tr>
            <%--
            <td width="40%"><b>Patient category:</b> ${patientCategory} - <c:forEach
                            items="${observation}" var="observation">
            ${observation.valueText} 
    </c:forEach>
            </td>
            --%>
            <td width="30%"><b>Age category:</b> ${ageCategory }</td>
            <td width="30%"><b>Referral:</b> <!-- June 20th 2012 - Thai Chuong supported for issue #45 -->
                <c:choose>
                    <c:when
                        test="${referredType.class.name == 'org.openmrs.ConceptName'}">
                        ${referredType}
                    </c:when>
                    <c:otherwise>
                        ${referral.name }	
                    </c:otherwise>
                </c:choose>
            </td>

        </tr>
    </table>
    <div id="tabs">
        <ul>
            <li><a
                    href="opdEntry.htm?patientId=${patient.patientId }&opdId=${opd.conceptId }&referralId=${referral.conceptId }&queueId=${queueId}"
                    title="OPD entry"><span> OPD entry</span> </a></li>
            <li><a
                    href="clinicalSummary.htm?patientId=${patient.patientId }"
                    title="Clinical summary"><span>Clinical summary</span> </a></li>
                <%-- ghanshyam,date:31-12-2012 New Requirement #553 [OPD] Add Laboratory report of patient in OPD for all Bangladesh Hospitals --%>
                <%-- ghanshyam,date:26-02-2013 Support #964[Patient Dashboard]change in the Dashboard Tab 'Laboratory report' to 'Laboratory record' for Bangladesh module
                --%>
            <li><a
                    href="investigationReport.htm?patientId=${patient.patientId }"
                    title="Laboratory record"><span>Laboratory record</span> </a></li>
                <%-- ghanshyam,date:8-01-2013 New Requirement #554 [OPD] Add Radiology report of patient in OPD for all Bangladesh Hospitals --%>
            <li><a
                    href="radiologyRecord.htm?patientId=${patient.patientId }"
                    title="Radiology record"><span>Radiology record</span> </a></li>
            <li><a
                    href="ipdRecord.htm?patientId=${patient.patientId }&opdLog=${opdLog}"
                    title="IPD record"><span>IPD record</span> </a></li>
                <%-- ghanshyam,date:8-01-2013 New Requirement #556 [OPD] Add Pharmacy record of patient in OPD for all Bangladesh Hospitals --%>
            <li><a href="pharmacyRecord.htm?patientId=${patient.patientId }"
                   title="Pharmacy record"><span>Pharmacy record</span> </a></li>
        </ul>

        <div id="OPD_entry"></div>
        <div id="Clinical_summary"></div>
        <%-- ghanshyam,date:31-12-2012 New Requirement #553 [OPD] Add Laboratory report of patient in OPD for all Bangladesh Hospitals --%>
        <%-- ghanshyam,date:26-02-2013 Support #964[Patient Dashboard]change in the Dashboard Tab 'Laboratory report' to 'Laboratory record' for Bangladesh module
        --%>
        <div id="Laboratory_record"></div>
        <%-- ghanshyam,date:8-01-2013 New Requirement #554 [OPD] Add Radiology report of patient in OPD for all Bangladesh Hospitals --%>
        <div id="Radiology_record"></div>
        <div id="IPD_record"></div>
        <%-- ghanshyam,date:8-01-2013 New Requirement #556 [OPD] Add Pharmacy record of patient in OPD for all Bangladesh Hospitals --%>
        <div id="Pharmacy_record"></div>
    </div>

</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>
