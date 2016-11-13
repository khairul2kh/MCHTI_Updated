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
                box-shadow: 0 0 30px black;
            }
            .custom-width {
                width: 80px !important;
            }
        </style>
    </head>

    <body ng-app  class="ng-cloak">
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
                                <label class="control-label col-sm-4" for="date">Date : </label>
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
                                <label class="control-label col-sm-4" for="date1">Date of Birth : </label>
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

                                <button type="button" class="btn btn-warning">
                                    <span class="fa fa-refresh"></span> Reset
                                </button>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


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
        </script>


    </body>
</html>

<%@ include file="/WEB-INF/template/footerMinimal.jsp"%>