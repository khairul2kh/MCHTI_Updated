<%-- 
    Document   : dischargeCertificate
    Created on : Mar 14, 2015, 4:04:53 PM
    Author     : khairul
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="includes/js_css.jsp"%> 

<script type="text/javascript">

    function printPatientReport() {
        jQuery("#dischargeCertificate table").each(function(index, item) {
            jQuery(item).attr("class", "");
        });
        jQuery("#dischargeCertificate").printArea({
            mode: "popup",
            popClose: true
        });
    }
</script>

<style>
    @media print {    
        .no-print, .no-print *{
            display: none !important;
            -moz-box-shadow: 0px 0px 0px 0px #ffffff;
            -webkit-box-shadow: 0px 0px 0px 0px #ffffff;
            box-shadow: 0px 0px 0px 0px #ffffff;	
        }
    </style>


    <form class="kha-f">
        <img src="${pageContext.request.contextPath}/images/hospital_logo.png" width="504" height="75" alt="logo"/>
        <table >
            <tr >
                <td> Patient ID : </td>
                <td> <%= request.getParameter("pid") %> </td>


            </tr>

            <tr >
                <td> Patient Name : </td>
                <td> <%= request.getParameter("pname") %> </td>
            </tr>

            <tr>
                <td> Age : </td>

                <td> <%= request.getParameter("age") %> </td>

            </tr>

            <tr>
                <td> <%= request.getParameter("relationType") %> : </td>

                <td> <%= request.getParameter("relationName") %> </td>

            </tr>

            <tr>
                <td> Admission Date : </td>

                <td> <%= request.getParameter("admissionDate") %> </td>

            </tr>

        </table>

        <b style="font-size:16px;">Patient ID : </b> 
        <b style="font-size:16px;"> <%= request.getParameter("pid") %> </b><br>

        <b style="font-size:16px;">Patient Name : </b>
        <b style="font-size:16px;"> <%= request.getParameter("pname") %> </b><br> 

        <b style="font-size:16px;">Age : </b>
        <b style="font-size:16px;"> <%= request.getParameter("age") %>  </b><br>

        <b style="font-size:16px;"><%= request.getParameter("relationType") %> :</b>
        <b style="font-size:16px;"> <%= request.getParameter("relationName") %> </b><br>

        <b style="font-size:16px;">Out Come : </b>
        <b style="font-size:16px;"> <%= request.getParameter("outCome") %> </b><br>

        <b style="font-size:16px;">Diagnosis : </b>
        <b style="font-size:16px;"> <%= request.getParameter("diagnosis") %> </b><br>

        <b style="font-size:16px;">Investigation : </b>
        <b style="font-size:16px;"> <%= request.getParameter("investigation") %> </b><br> 

        <b style="font-size:16px;">Procedure : </b>
        <b style="font-size:16px;"> <%= request.getParameter("procedure") %> </b><br> 

        <b style="font-size:16px;">Address : </b>
        <b style="font-size:16px;"> <%= request.getParameter("address") %> </b><br>

        <b style="font-size:16px;">Other Instruction : </b>
        <b style="font-size:16px;"> <%= request.getParameter("otherIns") %> </b><br>



        <a class="button no-print" href="${pageContext.request.contextPath}/module/ipd/main.htm?tab=1">Back</a>
        <input class="button no-print" type="button" value="Print" onclick="javascript:print()"/>

        <input  class="button no-print" type="button" value="Print" onClick="printPatientReport();"/>
    </form>

    <!--Discharge Certificate Print -->

    <div id="dischargeCertificate" style="display: none;">

        <img src="${pageContext.request.contextPath}/images/hospital_logo.png" width="504" height="75" alt="logo"/>
        <table>
            <tr >
                <td> Patient ID : </td>
                <td> <%= request.getParameter("pid") %> </td>


            </tr>

            <tr >
                <td> Patient Name : </td>
                <td> <%= request.getParameter("pname") %> </td>
            </tr>

            <tr>
                <td> Age : </td>

                <td> <%= request.getParameter("age") %> </td>

            </tr>

            <tr>
                <td> Admission Date : </td>

                <td> <%= request.getParameter("admissionDate") %> </td>

            </tr>

            <tr>
                <td> <%= request.getParameter("relationType") %> : </td>

                <td> <%= request.getParameter("relationName") %> </td>

            </tr>

        </table>

    </div>
