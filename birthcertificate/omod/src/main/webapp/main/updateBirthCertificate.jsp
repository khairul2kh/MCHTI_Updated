<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Certificate</title>
         <style>
            table, th, td 
            {
                border: 1px solid black;
            }
            table, tr, td 
            {
                border: 1px solid black;
            }
        </style>
    </head>
	
    <body>
        <center>
            <h1>Update Birth Certificate Information</h1>
            <f:form action="update.form" modelAttribute="id" >
                <table style="border: 1px">
                    <input type="hidden" name="id" value="${birthRegistration.id}" />

                    <tr>
                        <th>Memo No</th>
                        <td> <input type="text" name="memoNo" value="${birthRegistration.memoNo}"/></td>
                    </tr>

                  
            <tr>
                <th>Birth Date</th>
                <td><input type="text" name="date" <fmt:formatDate value="${birthRegistration.date}" pattern="MM/dd/yyyy"/></td>
            </tr>
        

                    <tr>
                        <th>Registration No</th>
                        <td><input type="text" name="registrationNo" value="${birthRegistration.registrationNo}"/></td>
                    </tr>

                    <tr>
                         <th>Patient Name</th>
                         <td><input type="text" name="name" value="${birthRegistration.name}"/></td>
                    </tr>

            <tr>
                <th>Birth Date</th>
                <td><input type="text" name="dateOfBirth" <fmt:formatDate value="${birthRegistration.dateOfBirth}" pattern="MM/dd/yyyy"/></td>
            </tr>
        

                    <tr>
                        <th>Birth Time</th>
                        <td><input type="text" name="timeOfBirth" value="${birthRegistration.timeOfBirth}"/></td>
                    </tr>

                    <tr>
                         <th>Mother Name</th>
                        <td><input type="text" name="mothersName" value="${birthRegistration.mothersName}"/></td>
                    </tr>
                      <tr>
                      <th>Mother National ID</th>
                        <td><input type="text" name="nidMoth" value="${birthRegistration.nidMoth}"/></td>
                    </tr>
                   
                    <tr>
                        <td>Gender:</td>
                        <td>
                            <select name="sex">
                                <option selected="">${birthRegistration.sex}</option>
                                <c:choose>
                                    <c:when test="${birthRegistration.sex=='Male'}">
                                        <option value="None">Transgender</option>
                                        <option value="Female">Female</option>
                                    </c:when>
                                    
                                    <c:when test="${birthRegistration.sex=='Female'}">
                                        <option value="None">Transgender</option>
                                        <option value="Male">Male</option>
                                    </c:when> 
                                    
                                    <c:otherwise>
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </td>
                    </tr>

                    <tr>
                           <th>Father Name</th>
                        <td><input type="text" name="fathersName" value="${birthRegistration.fathersName}"/></td>
                    </tr>

                    <tr>
                         <th>Father National ID</th>
                        <td><input type="text" name="nidFath" value="${birthRegistration.nidFath}"/></td>
                    </tr>
                    
                    <tr>
                         <th>Present Address</th>
                        <td><input type="text" name="presentAdd" value="${birthRegistration.presentAdd}"/></td>
                    </tr>
                    
                    <tr>
                          <th>Permanent Address</th>
                        <td><input type="text" name="permanentAdd" value="${birthRegistration.permanentAdd}"/></td>
                    </tr>

                    <tr>
                        <td></td>
                        <td><input type="submit" value="Update"/></td>
                    </tr>
                </table>
            </f:form>
        </center>
    </body>
</html>