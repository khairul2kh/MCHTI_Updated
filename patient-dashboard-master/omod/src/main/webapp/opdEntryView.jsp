<%-- 
    Document   : OpdEntryView
    Created on : Mar 14, 2014, 4:04:53 PM
    Author     : khairul
--%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="org.openmrs.ConceptName"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>


<style>


    body {
        font-size: 14px;
        line-height: 150%;

    }

    .mytable {
        margin:0px;
        padding:0px;
        border-collapse: collapse;

    }
    .mytable td {
        padding: 4px;
        vertical-align: top;
        border: 0px solid #000;
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
    .absolute {
        -moz-box-shadow: 0px 0px 0px 2px #9fb4f2;
        -webkit-box-shadow: 0px 0px 0px 2px #9fb4f2;
        box-shadow: 0px 0px 0px 2px #9fb4f2;
        background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #6EC17B), color-stop(1, #FFFFFF));
        background:-moz-linear-gradient(top, #6EC17B 5%, #FFFFFF 100%);
        background:-webkit-linear-gradient(top, #6EC17B 5%, #FFFFFF 100%);
        background:-o-linear-gradient(top, #6EC17B 5%, #FFFFFF 100%);
        background:-ms-linear-gradient(top, #6EC17B 5%, #FFFFFF 100%);
        background:linear-gradient(to bottom, #6EC17B 5%, #FFFFFF 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#476e9e',GradientType=0);
        background-color:#ffffff;
        -moz-border-radius:10px;
        -webkit-border-radius:10px;
        border-radius:10px;
        border:1px solid #4e6096;
        display:inline-block;
        cursor:pointer;
        color:#000000;
        font-family:arial;
        font-size:16px;
        padding:10px 30px;
        text-decoration:none;
    }

    @media print {    
        .no-print, .no-print *{
            display: none !important;
            -moz-box-shadow: 0px 0px 0px 0px #ffffff;
            -webkit-box-shadow: 0px 0px 0px 0px #ffffff;
            box-shadow: 0px 0px 0px 0px #ffffff;	
        }	
    }
</style>

<style>
    @media print {    
        .donotprint {
            -moz-box-shadow: 0px 0px 0px 0px #ffffff;
            -webkit-box-shadow: 0px 0px 0px 0px #ffffff;
            box-shadow: 0px 0px 0px 0px #ffffff;
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ffffff), color-stop(1, #FFFFFF));
            background:-moz-linear-gradient(top, #ffffff 5%, #FFFFFF 100%);
            background:-webkit-linear-gradient(top, #ffffff 5%, #FFFFFF 100%);
            background:-o-linear-gradient(top, #ffffff 5%, #FFFFFF 100%);
            background:-ms-linear-gradient(top, #ffffff 5%, #FFFFFF 100%);
            background:linear-gradient(to bottom, #ffffff 5%, #FFFFFF 100%);

        }
    </style>
<html>
<head>
<title>Prescription</title>
</head>
<body>
    <center>
        <div class="absolute donotprint">

            <form>
                <div id="info" >
                    <img src="${pageContext.request.contextPath}/images/hospital_logo.bmp" width="504" height="75" alt="logo"/>
                </div> 
                <div>
                    <table width="115%">
                        <!--Patient Information -->
                        <tr>
                            <td>
                                <b style="font-size:16px;">Patient ID : </b> 
                                <b style="font-size:16px;"> <%= request.getParameter("pid") %> </b> &nbsp;&nbsp;&nbsp;
                                <b style="font-size:16px;">Patient Name :</b> <%= request.getParameter("pname") %>
                                <%= request.getParameter("pnamemiddle") %> <%= request.getParameter("pnamefamily") %>  
                            </td>

                        </tr>

                        <tr>
                            <td><b style="font-size:16px;"> <%= request.getParameter("relationType")%> :</b> &nbsp; <%= request.getParameter("relationName")%> 
                                &nbsp;&nbsp;&nbsp;&nbsp; 

                                <b> Age : </b> <%= request.getParameter("age") %> &nbsp; <b>Unit Name :</b>&nbsp;<%= request.getParameter("opd") %>

                            </td>

                        </tr>

                        <tr>
                            <td>
                                <core:if test="${not empty param.ipd }">
                                    <b>Admitted Ward :</b> <%=request.getParameter("ipd")%>
                                </core:if>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <core:if test="${not empty param.ipdbed}">
                                    <b>Bed No :</b> <%=request.getParameter("ipdbed")%>
                                </core:if>
                            </td>		

                            <td></td>
                        </tr>
                        <!--Patient Information End -->
                        <!--
                        <td width="40%"><c:if test="${not empty admittedStatus }">
                    <span style="background-color: red; color: white; font-size:16px;">Admitted To : &nbsp; ${admittedWard.name} &nbsp; &nbsp; Bed No : &nbsp; ${bed}</span>

                        </c:if>
                    </td>  -->

                    </table>
                    <hr>
                </div>

                <%--
                                *	<div style="margin-top:10px; text-align:left;">
                        *        <b style="font-size:16px;">Patient ID :</b> <%= request.getParameter("pid") %>&nbsp;&nbsp;&nbsp;&nbsp;<b style="font-size:16px;">Age:</b> <%= request.getParameter("age") %><br />
                        *       <b style="font-size:16px;">Patient Name:</b> <%= request.getParameter("pname") %>&nbsp;<%= request.getParameter("pnamemiddle") %>&nbsp;<%= request.getParameter("pnamefamily") %>
                        *    </div> 
                --%>

                <table class="mytable" width="102%">
                    <!--Chief Complaints -->
                    <tr>
                        <core:if test="${not empty param.ccomplain}">
                            <td colspan="2" bgcolor="#FFFFFF" >

   <b style="font-size:16px;">Chief Complaints :</b> <%= request.getParameter("ccomplain") %><br>
                              <!--  <b style="font-size:18px;"></b> <%= request.getParameter("ccomplain") %><br> -->
                            </td>
                        </core:if>
                    </tr>
                    <!--Diagnosis -->
                    <tr >
                        <td colspan="2" >
                            <b style="font-size:18px;">Diagnosis :</b> 
                            <%
                            String param = request.getParameter("diagnosis");
                                                
                            for (String retval: param.split(",")){
                                out.println(" "+retval + "<br />");
                            }
                            %>

                        </td> 
                    </tr>
                    <!--Procedure -->
                    <tr>
                        <core:if test="${not empty param.procedures }">
                            <td colspan="2" bgcolor="#FFFFFF" >

                                <b style="font-size:18px;">Procedure :</b> <br>
                                <%
                                                            param = request.getParameter("procedures");
                                                   
                                                        for (String retval: param.split(",")){
                                                            out.println("&#x2624; "+ retval + "<br/><br/>");
                                                        }
                                %>


                            </td>
                        </core:if>
                    </tr>
                    <!--Investigations-->
                    <tr>
                        <core:if test="${not empty param.investigations }">
                            <td colspan="2">                       

                                <b style="font-size:18px;">Investigations :</b> <br>
                                <%
                                                            param = request.getParameter("investigations");
                                                   
                                                        for (String retval: param.split(",")){
                                                            out.println("&#x2624; "+ retval + "<br/><br/>");
                                                        }
                                %>


                            </td>
                        </core:if>
                    </tr>
                    <!--Drugs -->
                    <tr>
                        <td colspan="2">
                            <core:if test="${not empty param.drugs }">
                                <b style="font-size:18px;">Rx.:</b> <br>
                                <%
                                                        param = request.getParameter("drugs");
                                               
                                                        for (String retval: param.split(",")){
                                                            out.println("&#x2611;  "+retval + "<br/>");
                                                        }
                                %>
                            </core:if>
                            <hr>                      
                        </td>
                        <td></td>
                    </tr>
                    <!--Advice -->
                    <tr>
                        <td bgcolor="#FFFFFF">
                            <core:if test="${not empty param.advice}">
                                <b>Advice:</b> <%= request.getParameter("advice") %>
                            </core:if>
                        </td>

                        <!--Visit Outcome -->

                        <td bgcolor="#FFFFFF">
                            <b>Visit Outcome:</b>
                            <%= request.getParameter("outcome") %> 
                        </td>
                    </tr>

                </table> 

                <!--Internal Referred -->

                <div style="margin-top:10px; text-align:left;">	
                    <core:if test="${not empty param.internal}">
                        <b>Referred To :</b> &nbsp;&nbsp;<%= request.getParameter("internal") %> <br>
                    </core:if>    
                    <b style="font-size:16px;">Treating Doctor :</b> &nbsp;&nbsp; <span style="font-size:16px;"> <%= request.getParameter("doctor") %> </span> <br>										
                </div>
                <div style="margin-top: 30px; text-align: right;">
                    Signature &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />
                    Date: <%= request.getParameter("odate") %> 
                </div>

                <div>
                     <img src="${pageContext.request.contextPath}/moduleResources/patientdashboard/opd_note.jpg" width="300px" height="28px"/>
					  
                </div>
                <br>
                <%--
                <div>
    <a class="button no-print" href="${pageContext.request.contextPath}/module/patientqueue/main.htm">Home</a>&nbsp;&nbsp;&nbsp;
    <a class="button no-print" href="javascript:print()">Print</a>
                </div>
                --%>
            </form>

    </center>
</div>
<center>
    <br/><div>
        <core:if test="${not empty param.ipd }">

            <a class="button no-print" href="${pageContext.request.contextPath}/module/ipd/main.htm?tab=1">Back</a>&nbsp;&nbsp;&nbsp;
        </core:if>

        <core:if test="${empty param.ipd }">
            <a class="button no-print" href="${pageContext.request.contextPath}/module/patientqueue/main.htm">Back</a>&nbsp;&nbsp;&nbsp;
        </core:if>

        <a class="button no-print" href="javascript:print()">Print</a>

    </div>
</center>
</body>
</html>
