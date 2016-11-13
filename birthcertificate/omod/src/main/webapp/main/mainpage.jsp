<%-- 
    Document   : main
    Created on : Nov 8, 2016, 12:28:03 AM
    Author     : Khairul
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/headerMinimal.jsp"%>
<%@ include file="../includes/js_css.jsp"%> 

<html>
    <head>  
        <title>Birth Certificate Entry Form</title>  
        <style>
            .username{padding-left:20px;}
            .username.ng-valid {
                background-color: lightgreen;
            }
            .username.ng-dirty.ng-invalid-required {
                background-color: red;
            }
            .username.ng-dirty.ng-invalid-minlength {
                background-color: yellow;
            }
            .email.ng-valid {
                background-color: lightgreen;
            }
            .email.ng-dirty.ng-invalid-required {
                background-color: red;
            }
            .email.ng-dirty.ng-invalid-email {
                background-color: yellow;
            }
            body, #mainWrapper {
                height: 70%;
                background-color:rgb(245, 245, 245);
            }
            body, .form-control{
                font-size:12px!important;
            }
            .floatRight{
                float:right;
                margin-right: 18px;
            }
            .has-error{
                color:red;
            }
            .formcontainer{
                background-color: #DAE8E8;
                padding: 20px;
            }
            .tablecontainer{
                padding-left: 20px;
            }
            .generic-container {
                width:80%;
                margin-left: 20px;
                margin-top: 20px;
                margin-bottom: 20px;
                padding: 20px;
                background-color: #EAE7E7;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-shadow: 0 0 30px black;
            }
            .custom-width {
                width: 80px !important;
            }
        </style>

    </head>
    <center>
        <body ng-app  class="ng-cloak">
            <span class="glyphicon glyphicon-home" ></span>
            <i class="fa fa-address-book" ></i>

            <i class="fa fa-man fa-2x"></i> 

            <div class="generic-container">
                <div class="panel panel-default">
                    <div class="panel-heading"><span class="lead">Birth Certificate Entry Form </span></div>
                    <div class="formcontainer">
                        <form   name="myForm" class="form-horizontal">

                            <div class="row">
                                <div class="form-group col-md-12">
                                    <label class="col-md-2 control-lable" for="name">Name</label>
                                    <div class="col-md-7">
                                        <input type="text"  id="name" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>

                                    </div>
                                </div>
                            </div>



                            <div class="row">
                                <div class="form-actions floatRight">
                                    <input type="submit"  value="Add" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                                    <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
            <script>
                var a = "Khairul";
                console.log(a);
            </script>
        </body>
    </center>
</html>

<%@ include file="/WEB-INF/template/footerMinimal.jsp"%>