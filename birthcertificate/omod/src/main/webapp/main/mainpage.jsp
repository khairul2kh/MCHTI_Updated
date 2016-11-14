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
            .icon-success {
                color: #088A08;
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
                margin-left: 10%;
                margin-top: 20px;
                margin-bottom: 20px;
                padding: 20px;
                background-color: #EAE7E7;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-shadow: 0 0 10px #A4A4A4;
            }
            .custom-width {
                width: 80px !important;
            }
            .tdn a{text-decoration: none;}
            .nav-tabs>li.active>a,
            .nav-tabs>li.active>a:hover,
            .nav-tabs>li.active>a:focus {
                color: #555;
                background-color: #F2F2F2;
                border: 1px solid #eee;
                border-bottom-color: transparent;
                cursor: default
            }
        </style>
    </head>

    <body ng-app  class="ng-cloak tdn">

        <!--
            <div class="container container-fluid" >
            <ul class="nav nav-tabs">
            <li class="active"><a href="#">Birth Registration</a></li>
            <li ><a href="#">About</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    Tutorials <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="#">HTML</a></li>
                    <li><a href="#">CSS</a></li>
                    <li><a href="#">JavaScript</a></li>                        
                </ul>
            </li>  
            </ul>
            </div>

            <nav class="navbar navbar-default" role="navigation">
                <button class="btn btn-default dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">
                    Accounts <span class="glyphicon glyphicon-user" ></span> 
                </button>
            <div class="dropdown floatRight" style="padding-top:5px; padding-right:40px;">
                <button class="btn btn-default dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">
                    Accounts <span class="glyphicon glyphicon-user" ></span> 
                </button>
                <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">HTML</a></li>
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">CSS</a></li>
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">JavaScript</a></li>
                    <li role="presentation" class="divider"></li>
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">About Us</a></li>
                </ul>
            </div>  
        </nav>
        --> 

        <nav class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="navbar-collapse" uib-collapse="vm.isNavbarCollapsed" ng-switch="vm.isAuthenticated()">
                    <ul class="nav navbar-nav navbar-right">
                        <li ui-sref-active="active">
                            <a ui-sref="home" href="${pageContext.request.contextPath}/">
                                <span class="glyphicon glyphicon-home"></span>
                                <span class="hidden-sm">Home</span>
                            </a>
                        </li>

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
                                <li><a role="menuitem" tabindex="-1" href="#">HTML</a></li>
                                <li  ><a role="menuitem" tabindex="-1" href="#">CSS</a></li>
                                <li  ><a role="menuitem" tabindex="-1" href="#">JavaScript</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a role="menuitem" tabindex="-1" href='${pageContext.request.contextPath}/logout'>
                                        <span class="glyphicon glyphicon-log-out"></span>&nbsp; Log Out</a></li>

                            </ul>
                        </li>
                    </ul> 
                </div>
            </div>
        </nav>         

        <div class="generic-container">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="lead">Birth Certificate Entry Form </span></div>
                <div class="formcontainer">
                    <form name="myForm" action="main.form" class="form-horizontal" method="POST" >
                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="memo">Memo No: MCHTI/Azim:/Birth/</label>
                                <div class="col-md-7">
                                    <input type="text" style="padding-left:10px;" name="memo" id="memo" class="form-control input-sm" placeholder="Enter memo no"  />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-12 has-feedback">
                                <label class="control-label col-sm-4" >Date : </label>
                                <div class="col-md-3">
                                    <input type="text" style="padding-left:10px;" name="date"  id="date" class="username form-control input-sm" placeholder="Enter date" />                  
                                    <span class="glyphicon glyphicon-calendar form-control-feedback icon-success" ></span>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="birthName">Name : </label>
                                <div class="col-md-7">
                                    <input type="text" style="padding-left:10px;" name="birthName" id="birthName" class="form-control input-sm" placeholder="Enter Name" />
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12 has-feedback">
                                <label class="control-label col-sm-4">Date of Birth : </label>
                                <div class="col-md-3">
                                    <input type="text" style="padding-left:10px;" name="date1"  id="date1" class="username form-control input-sm" placeholder="Enter Date of birth" />                  
                                    <span class="glyphicon glyphicon-calendar form-control-feedback icon-success" ></span>
                                </div>
                                <label class="control-label col-sm-2" for="time">Time of Birth : </label>
                                <div class="col-md-2">
                                    <div class="clearfix">
                                        <div class="input-group clockpicker pull-center" data-placement="left" data-align="top" data-autoclose="true">
                                            <input type="text" name="time" class="form-control" value="00:00">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-time"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="motName">Mother's Name : </label>
                                <div class="col-md-7">
                                    <input type="text" style="padding-left:10px;" name="motName" id="motName" class="form-control input-sm" placeholder="Enter Mother's Name" />
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="nidNoMot">NID No : </label>
                                <div class="col-md-7">
                                    <input type="text" style="padding-left:10px;" name="nidNoMot" id="nidNoMot" class="form-control input-sm" placeholder="Enter Nid no" />
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="fatName">Fother's Name : </label>
                                <div class="col-md-7">
                                    <input type="text" style="padding-left:10px;" name="fatName" id="fatName" class="form-control input-sm" placeholder="Enter Father's Name" />
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="nidNoFat">NID No : </label>
                                <div class="col-md-7">
                                    <input type="text" style="padding-left:10px;" name="nidNoFat" id="nidNoFat" class="form-control input-sm" placeholder="Enter Nid no" />
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" for="preAdd">Present Address : </label>
                                <div class="col-md-7">
                                    <textarea class="form-control" rows="5" name="preAdd" id="preAdd" placeholder="Present Address" ></textarea>
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="control-label col-sm-4" placeholder="Permanent Address" for="permaAdd">Permanent Address : </label>
                                <div class="col-md-7">
                                    <textarea class="form-control" name="permaAdd" placeholder="Permanent Address" rows="5" id="permaAdd"></textarea>
                                </div>
                            </div> 
                        </div>

                        <div class="row">
                            <div class="form-actions floatRight">
                                <button type="button" class="btn btn-primary">
                                    <span class="fa fa-floppy-o fa-lg"></span> Save
                                </button>                           

                                <button type="button" class="btn btn-warning" onclick="$('form')[0].reset()">
                                    <span class="fa fa-refresh"></span> Reset
                                </button>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </nav>
    <script>
        jQuery(document).ready(function() {
            $("#date").css('cursor', 'pointer');
            $('#date').datepicker();
            $("#date1").css('cursor', 'pointer');
            $('#date1').datepicker();
        });

        $('.clockpicker').clockpicker()
                .find('input').change(function() {
            console.log(this.value);
        });
        var input = $('#single-input').clockpicker({
            placement: 'bottom',
            align: 'left',
            autoclose: true,
            'default': 'now'
        });

        $('ul li a').click(function() {
            $('ul li.active').removeClass('active');
            $(this).closest('li').addClass('active');
        });
    </script>
</body>
</html>

<%@ include file="/WEB-INF/template/footerMinimal.jsp"%>