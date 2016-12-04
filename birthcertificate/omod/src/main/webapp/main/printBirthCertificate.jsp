<%-- 
    Document   : printBirthCertificate
    Created on : Nov 24, 2016, 12:00:44 PM
    Author     : Khairul
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/headerMinimal.jsp"%>
<%@ include file="../includes/js_css.jsp"%> 
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
        border: 1px solid #ddd;
        border-radius: 4px;
        box-shadow: 0 0 10px #A4A4A4;
    }
</style>

<script>
    if (SESSION.checkSession()) {
        jQuery(document).ready(function() {

        });
    }
</script>

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