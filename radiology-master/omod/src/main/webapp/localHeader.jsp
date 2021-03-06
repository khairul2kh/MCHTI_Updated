 <%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Radiology module.
 *
 *  Radiology module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Radiology module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Radiology module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%> 
<div style="border-bottom: 1px solid black; padding-bottom:5px; margin-bottom:10px;">
    <ul id="menu">
        <li class="first">
            <span style="font-weight:bold; font-size:large;">Radiology system</span>
        </li>
        <openmrs:hasPrivilege privilege="Manage Radiology Queue">
            <li id="QueueId">
				<a href="queue.form">Queue</a>
            </li>
        </openmrs:hasPrivilege>
		<openmrs:hasPrivilege privilege="Manage Radiology Worklist">
            <li id="WorkListId">
				<a href="worklist.form">Work List</a>
            </li>
        </openmrs:hasPrivilege>
        <openmrs:hasPrivilege privilege="Edit Radiology Result">
            <li id="EditResultId">
				<a href="editRadiologyResult.form">Edit/Update</a>
            </li>
        </openmrs:hasPrivilege>
		<!--<openmrs:hasPrivilege privilege="Edit Radiology Result">
            <li id="EditResultId">
				<a href="editResult.form">Edit/Update</a>
            </li>
        </openmrs:hasPrivilege>
		<openmrs:hasPrivilege privilege="Print Radiology Worklist">
            <li id="PrintWorkList">
				<a href="printWorklist.form">Print Work List</a>
            </li>
        </openmrs:hasPrivilege>
		<openmrs:hasPrivilege privilege="Manage Radiology Patient Report">
            <li id="PatientReportId">
				<a href="patientReport.form">Patient Report</a>
            </li>
        </openmrs:hasPrivilege>
        <openmrs:hasPrivilege privilege="Manage Radiology Functional Status">
            <li id="FunctionalStatusId">
				<a href="functionalStatus.form">Functional Status</a>
            </li>
        </openmrs:hasPrivilege>  -->
    </ul>
</div>

<script type="text/javascript">
	// activate the <li> with @id by adding the css class named "active"
	function activate(id){
		$("#" + id).addClass("active");		
	}
	
	// return true whether the @str contains @searchText, otherwise return false
	function contain(str, searchText){
		return str.indexOf(searchText)>-1;
	}
	
	// choose which <li> will be activated using @url
	var url = location.href;
	if(contain(url, "queue.form")){
		activate("QueueId");
	} else if(contain(url, "worklist.form")){
		activate("WorkListId");
	} else if(contain(url, "editRadiologyResult.form")){
		activate("EditResultId");
	} else if(contain(url, "printWorklist.form")){
		activate("PrintWorkList");
	} else if(contain(url, "patientReport.form")){
		activate("PatientReportId");
	} else if(contain(url, "functionalStatus.form")){
		activate("FunctionalStatusId");
	}
	
	// get the context path
	function getContextPath() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}
</script>