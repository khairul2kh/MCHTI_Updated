<%-- 
    Document   : printBirthCertificate
    Created on : Nov 24, 2016, 12:00:44 PM
    Author     : Khairul
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/headerMinimal.jsp"%>
<%@ include file="../includes/js_css.jsp"%> 
<<<<<<< HEAD
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
=======
>>>>>>> origin/master
<style>
    .tdn a{text-decoration: none;
    }
    .generic-container {
        width:80%;
        margin-left: 10%;
        margin-top: 20px;
        margin-bottom: 20px;
        padding: 20px;
        background-color: #EAE7E7;
<<<<<<< HEAD
        border: 15px solid #ddd;
        border-radius: 9px;
        box-shadow: 0 0 20px #A4A4A4;
=======
        border: 1px solid #ddd;
        border-radius: 4px;
        box-shadow: 0 0 10px #A4A4A4;
>>>>>>> origin/master
    }
</style>

<script>
    if (SESSION.checkSession()) {
<<<<<<< HEAD
        jQuery(document).ready(function () {
=======
        jQuery(document).ready(function() {
>>>>>>> origin/master

        });
    }
</script>

<<<<<<< HEAD
<!--<script>
    function deleteCerficate(bc){
        if(confirm('Do You Want to Delete This Cerficate ?')
            ){
                var url='delete/'+bc;
                            window.location.href=url;
                
    }
    }
</script>-->

 <script>
            $(function (){
                $('#myModal').modal(options);
            });
        </script>

=======
>>>>>>> origin/master
<nav class="navbar navbar-default tdn" role="navigation">            
    <div class="container">
        <div class="navbar-collapse" uib-collapse="vm.isNavbarCollapsed" ng-switch="vm.isAuthenticated()">
            <ul class="nav navbar-nav navbar-right">
                <li   class="dropdown pointer">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="" id="account-menu">
                        <span>
                            <span class="glyphicon glyphicon-user"></span>
                            <span class="hidden-sm">
                                Account
                            </span>
                            <b class="caret"></b>
                        </span>
                    </a>
                    <ul class="dropdown-menu" role="menu" >
                        <li> <a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/"> <span class="glyphicon glyphicon-home"></span>
                                <span class="hidden-sm">Home</span></a></li>

                        <li role="presentation" class="divider"></li>
                        <li><a role="menuitem" tabindex="-1" href='${pageContext.request.contextPath}/logout'>
                                <span class="glyphicon glyphicon-log-out"></span>&nbsp; Log Out</a></li>

                    </ul>
                </li>
            </ul> 
        </div>
    </div>
</nav> 
<<<<<<< HEAD
                                
<div class="container">
    <h2><center> Birth Certificate Information</center></h2>
    
    <table class="table table-bordered">
    
            <tr>
                <th>Memo No</th>
                <td>${birthRegistration.memoNo}</td>
            </tr>
     

       
            <tr>
                <th>Issue Date</th>
                <td><fmt:formatDate value="${birthRegistration.date}" pattern="MM/dd/yyyy"/></td>
            </tr>
   

  
            <tr>
                <th>Registration No</th>
                <td>${birthRegistration.registrationNo}</td>
            </tr>


            <tr>
                <th>Patient Name</th>
                <td>${birthRegistration.name}</td>
            </tr>

            <tr>
                <th>Gender</th>
                <td>${birthRegistration.sex}</td>
            </tr>
     
            <tr>
                <th>Birth Date</th>
                <td><fmt:formatDate value="${birthRegistration.dateOfBirth}" pattern="MM/dd/yyyy"/></td>
            </tr>
    
        <tr>
            <th>Birth Time</th>
            <td>${birthRegistration.timeOfBirth}</td>

        </tr>
     
            <tr>
                <th>Mother Name</th>
                <td>${birthRegistration.mothersName}</td>
            </tr>
    
            <tr>
                <th>Mother National ID</th>
                <td>${birthRegistration.nidMoth}</td>
            </tr>
     
            <tr>
                <th>Father Name</th>
                <td>${birthRegistration.fathersName}</td>
            </tr>
      
            <tr>
                <th>Father National ID</th>
                <td>${birthRegistration.nidFath}</td>
            </tr>
    
            <tr>
                <th>Present Address</th>
                <td>${birthRegistration.presentAdd}</td>
            </tr>
    
            <tr>
                <th>Permanent Address</th>
                <td>${birthRegistration.permanentAdd}</td>
            </tr>
     
            <tr>
                <th>Nationality</th>
                <td>Bangladeshi</td>
            </tr>
     
            <tr>
                <td><a href="edit.form?id=${birthRegistration.id}">Edit</a></td>
                
                <td> <a href="remove.form?id=${birthRegistration.id}" id="myModal">Delete</a></td>
                 
            </tr>
     
    </table>

</div>
<input type="button" class="bu" value="Print" onClick="printDiv3();"/>
<script>
    function printDiv3() {
        var printer = window.open('left=0', 'top=0', 'width=300,height=300');
        printer.document.open("text/html");
        printer.document.write(document.getElementById('printDiv').innerHTML);
        printer.print();
        printer.document.close();
        printer.window.close();
        jQuery("#billPrint").submit();
        // window.location = "directbillingqueue.form";
    }
</script>
<div id="printDiv" style="">
   
    <table>
        <tr>
			<td colspan="2" style="cellspacing:0; margin:0;"></td>
            <td colspan="1">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			<b>${birthRegistration.memoNo}</b>
            </td>
            <td colspan="1">
                <b style="font-size:15px;"><fmt:formatDate value="${birthRegistration.date}" pattern="MM/dd/yyyy"/></b>
            </td>
        </tr>
        <br>
        <tr>
		<td colspan="1"></td>
            <td colspan="3">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <b style="font-size:15px;">${birthRegistration.registrationNo}</b>
            </td>
        </tr>
        <br>
        <tr>
		<td colspan="1"></td>
            <td colspan="2">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b>${birthRegistration.name}</b>
            </td>
			
            <td colspan="1">
			&nbsp;&nbsp;&nbsp;
            <b> ${birthRegistration.sex}</b>
            </td>
        </tr>
        <br>
        <tr>
		<td colspan="1"></td>
            <td colspan="1">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <b><fmt:formatDate value="${birthRegistration.dateOfBirth}" pattern="MM/dd/yyyy"/></b>
            </td>
			<td colspan="1"></td>
            <td colspan="1">
                <b> ${birthRegistration.timeOfBirth}</b>
            </td>
        </tr>
        <br>
        <tr>
			<td colspan="1"></td>
            <td colspan="3">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style="font-size:15px; font-weight:bold;"> ${birthRegistration.mothersName}</span>
            </td>
        </tr>
        <br>
        <tr>
			<td colspan="1"></td>
			<td colspan="3">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:15px; font-weight:bold;"> ${birthRegistration.nidMoth}</span>
            </td>
        </tr>
        <br>
        <tr>
		<td colspan="1"></td>
		<td colspan="3">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style="font-size:15px; font-weight:bold;"> ${birthRegistration.fathersName}</span>
            </td>
        </tr>
        <br>
        <tr>
			<td colspan="1"></td>
            <td colspan="3">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style="font-size:15px; font-weight:bold;"> ${birthRegistration.nidFath}</span>
            </td>
        </tr>
        <br>
        <tr>
            <td colspan="1"></td>
            <td colspan="3">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style="font-size:15px; font-weight:bold;"> ${birthRegistration.presentAdd}</span>
            </td>
        </tr>
        <br><br>
        <tr>
          <td colspan="1"></td>
            <td colspan="3">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style="font-size:15px; font-weight:bold;"> ${birthRegistration.permanentAdd}</span>
            </td>
        </tr>
        <br><br>
        <tr>
            <td>
                <span style="font-size:15px; font-weight:bold;"><td>Nationality : Bangladeshi</td></span>
            </td>
        </tr>
    </table>  

</div>


=======
<div class="container">
    <h2>Birth Certificate Print Form </h2>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Memo No</th>
                <td>${birthReg.memoNo}</td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th>Date</th>
                <td><fmt:formatDate value="${birthReg.date}" pattern="dd-MM-yyyy" /></td>
            </tr>
        </tbody>
    </table>
</div>
>>>>>>> origin/master
