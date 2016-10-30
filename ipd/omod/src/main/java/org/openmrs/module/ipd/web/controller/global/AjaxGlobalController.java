/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of IPD module.
 *
 *  IPD module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  IPD module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with IPD module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/


package org.openmrs.module.ipd.web.controller.global;

import static java.lang.Math.log;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.Department;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.patientqueue.util.OPDPatientQueueConstants;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.WardBedStrength;
import org.openmrs.module.ipd.util.IpdUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * Class: AjaxGlobalController
 * </p>
 * <p>
 * Package: org.openmrs.module.ipd.web.controller.global
 * </p>
 * <p>
 * Author: Nguyen manh chuyen
 * </p>
 * <p>
 * Update by: Nguyen manh chuyen
 * </p>
 * <p>
 * Version: $1.0
 * </p>
 * <p>
 * Create date: Mar 15, 2011 12:58:02 PM
 * </p>
 * <p>
 * Update date: Mar 15, 2011 12:58:02 PM
 * </p>
 **/
@Controller("IPDAjaxGlobalController")
public class AjaxGlobalController {

	@RequestMapping(value = "/module/ipd/gotoDashboard.htm", method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value = "id", required = false) Integer id,
			Model model) {
		IpdService is = (IpdService) Context.getService(IpdService.class);
		IpdPatientAdmitted pa = is.getIpdPatientAdmitted(id); 
               	IpdPatientAdmissionLog pal = pa.getPatientAdmissionLog();
		OpdPatientQueueLog pql = pal.getOpdLog();
		Integer patientId = pql.getPatient().getPatientId();
		Integer opdId = pql.getOpdConcept().getConceptId();
		//Integer referralId = pql.getReferralConcept().getConceptId();
                
              ///// New Code Enter here for link OPD Treatment page, Md. khairul islam by Crystal Technology /////
                Concept opdConcept = Context.getConceptService().getConcept(opdId);
                PatientQueueService queueService = Context
				.getService(PatientQueueService.class);
		Patient patient = Context.getPatientService().getPatient(patientId);
                
                OpdPatientQueue queue = new OpdPatientQueue();
		queue.setOpdConcept(opdConcept);
		queue.setOpdConceptName(opdConcept.getName().getName());
		queue.setPatient(patient);
		queue.setCreatedOn(new Date());
		queue.setPatientIdentifier(patient.getPatientIdentifier()
				.getIdentifier());
		queue.setPatientName(patient.getGivenName() + " "
				+ patient.getMiddleName() + " " + patient.getFamilyName());

		// TODO Is this what we want ???
		String gpRevisit = Context.getAdministrationService()
				.getGlobalProperty("registration.revisitConcept");
		Concept conRevisit = Context.getConceptService().getConcept(gpRevisit);
		if (conRevisit == null) {
			ConceptService conceptService = Context.getConceptService();
			ConceptDatatype datatype = Context.getConceptService()
					.getConceptDatatypeByName("N/A");
			ConceptClass conceptClass = conceptService
					.getConceptClassByName("Misc");
			conRevisit = new Concept();
			ConceptName name = new ConceptName(gpRevisit, Context.getLocale());
			conRevisit.addName(name);
			conRevisit.setDatatype(datatype);
			conRevisit.setConceptClass(conceptClass);
			conceptService.saveConcept(conRevisit);
		}
		if (conRevisit != null) {
			queue.setReferralConcept(conRevisit);
			queue.setReferralConceptName(conRevisit.getName().getName());
		}
		queue.setSex(patient.getGender());
		queue.setUser(Context.getAuthenticatedUser());

		Integer referralId = conRevisit != null ? queue.getReferralConcept().getConceptId() : null;
		queue.setBirthDate(patient.getBirthdate());
		queue.setUser(Context.getAuthenticatedUser());
		queue.setStatus(Context.getAuthenticatedUser().getGivenName() + " "+ OPDPatientQueueConstants.STATUS);

		OpdPatientQueue opdPatientQueue = queueService.saveOpdPatientQueue(queue);
		//HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		//hcs.createObsGroup(patient, HospitalCoreConstants.PROPERTY_OBSGROUP);

		 String url = "/module/patientdashboard/main.htm?patientId="
				+ queue.getPatient().getPatientId() + "&opdId="
				+ queue.getOpdConcept().getConceptId() + "&referralId="
				+ referralId + "&queueId=" + opdPatientQueue.getId();
                
                
                return "redirect:" + url;
                
                
                
                ///// New Code End here Md. khairul islam by Crystal Technology 20th October 2014 /////
                
				
            //    String url = "/module/patientdashboard/main.htm?patientId=" + patientId + "&opdId=" + opdId + "&referralId=" + referralId+ "&ipdAdmittedId="+id;		
		//return "redirect:" + url;
                
                //String url = "/module/patientdashboard/main.htm?patientId=" + patientId + "&opdId=" + opdId + "&referralId=" + referralId + "&ipdAdmittedId=" + id;		
		//return "redirect:" + url;
                
}      
       @RequestMapping(value = "/module/ipd/addConceptToWard.htm" , method=RequestMethod.POST)
	public String addConceptToWard(
			@RequestParam(value ="opdId",required=false) Integer opdId, 
			@RequestParam(value ="conceptId",required=false) Integer conceptId,
			@RequestParam(value ="typeConcept",required=false) Integer typeConcept,
			Model model){
		
		if(opdId != null && opdId > 0 && conceptId != null && conceptId > 0 && typeConcept != null && typeConcept > 0){
			PatientDashboardService patientDashboardService = Context.getService(PatientDashboardService.class);
			Department department = patientDashboardService.getDepartmentByWard(opdId);
			Concept concept = Context.getConceptService().getConcept(conceptId);
			if(concept != null && department != null){
				DepartmentConcept departmentConcept = new DepartmentConcept();
				departmentConcept.setConcept(concept);
				departmentConcept.setDepartment(department);
				departmentConcept.setCreatedOn(new Date());
				departmentConcept.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				departmentConcept.setTypeConcept(typeConcept);
				patientDashboardService.createDepartmentConcept(departmentConcept);
			}
		}
		
		return "/module/ipd/ajax/addConceptToWard";
	}
	@RequestMapping(value = "/module/ipd/patientsForAdmissionAjax.htm", method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value = "searchPatient", required = false) String searchPatient,// patient
																							// identifier
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "ipdWardString", required = false) String ipdWardString, // ipdWard
																							// multiselect
			@RequestParam(value = "doctorString", required = false) String doctorString,
			Model model) {

		IpdService ipdService = (IpdService) Context
				.getService(IpdService.class);
		List<IpdPatientAdmission> listPatientAdmission = ipdService
				.searchIpdPatientAdmission(searchPatient,
						IpdUtils.convertStringToList(doctorString),
						fromDate, toDate,
						IpdUtils.convertStringToList(ipdWardString), "");

		model.addAttribute("listPatientAdmission", listPatientAdmission);

		return "module/ipd/ajax/patientsForAdmissionAjax";
	}

	@RequestMapping(value = "/module/ipd/admittedPatientIndexAjax.htm", method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value = "searchPatient", required = false) String searchPatient,// patient
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "ipdWardString", required = false) String ipdWardString, // note
			@RequestParam(value = "tab", required = false) Integer tab, // If
			@RequestParam(value = "doctorString", required = false) String doctorString,// note:
			Model model) {
                                
                        IpdService ipdService = (IpdService) Context
				.getService(IpdService.class);
		
		List<IpdPatientAdmitted> listPatientAdmitted = ipdService
				.searchIpdPatientAdmitted(searchPatient,
						IpdUtils.convertStringToList(doctorString),
						fromDate, toDate,
						IpdUtils.convertStringToList(ipdWardString), "");

		Map<Integer, String> mapRelationName = new HashMap<Integer, String>();
		//ghanshyam 30/07/2012 [IPD - Bug #325] [IPD] Inconsistency in print slip
		Map<Integer, String> mapRelationType = new HashMap<Integer, String>();
		for (IpdPatientAdmitted admit : listPatientAdmitted) {
			PersonAttribute relationNameattr = admit.getPatient().getAttribute("Father/Husband Name");
			PersonAttribute relationTypeattr = admit.getPatient().getAttribute("Relative Name Type");
			if(relationTypeattr!=null){
				mapRelationType.put(admit.getId(), relationTypeattr.getValue());
			}
			else{
				mapRelationType.put(admit.getId(), "Relative Name");
			}
			mapRelationName.put(admit.getId(), relationNameattr.getValue());	
		}
		model.addAttribute("mapRelationName", mapRelationName);
		model.addAttribute("mapRelationType", mapRelationType);
		model.addAttribute("dateTime", new Date().toString());
                
		model.addAttribute("listPatientAdmitted", listPatientAdmitted);

		return "module/ipd/ajax/admittedListAjax";
	}
	
	@RequestMapping(value = "/module/ipd/getBedStrength.htm", method = RequestMethod.GET)
	public String getBedStrength(@RequestParam(value="wardId",required=false) Integer wardId, Model model) {
		
		IpdService ipdService = (IpdService) Context
				.getService(IpdService.class);
		Map<Long,Integer> bedStrengthMap = new HashMap<Long, Integer>();
		WardBedStrength wardBedStrength = ipdService.getWardBedStrengthByWardId(wardId);
//		System.out.println("ward bed strength = " +wardBedStrength + "<<<wardId" + wardId);
		if (wardBedStrength!=null){
		Integer bedStrength = wardBedStrength.getBedStrength();
		List<IpdPatientAdmitted> allAdmittedPatients = ipdService.getAllIpdPatientAdmitted();
		//populate all bed numbers with 0;
//		System.out.println("maxBedStrength=" + wardBedStrength.getBedStrength());
		
                for (Long i =1L ;i<=bedStrength;i++){
			bedStrengthMap.put(i, 0);
			
		}
		
		for (IpdPatientAdmitted ipdAdmittedPatient: allAdmittedPatients)
		{
			if (ipdAdmittedPatient.getAdmittedWard().getId().equals(wardId))
			{
			Long bedNo = Long.parseLong(ipdAdmittedPatient.getBed());
//			System.out.println("bedno="+bedNo+"ward=" + wardId);
			Integer bedCount = bedStrengthMap.get(bedNo);
			if (bedCount==null){
				bedCount = 1;
			}else {
				bedCount = bedCount + 1;
			}
			bedStrengthMap.put(bedNo, bedCount);
			}
		}
		}else{
			model.addAttribute("bedStrengthValueAvailable", "false");
		}
		
		for (Long key : bedStrengthMap.keySet()){
//			System.out.println("bedno=" + key + "bedcount=" + bedStrengthMap.get(key));
		}
		
		
		model.addAttribute("bedStrengthMap", bedStrengthMap);
		model.addAttribute("size", Math.round(Math.sqrt(bedStrengthMap.size())) + 1 );
		//ghanshyam 7-august-2013 code review bug
		float bedStrengthSize= bedStrengthMap.size();
		model.addAttribute("bedMax", Math.round(bedStrengthSize));
		return "module/ipd/ajax/bedStrength";
	}
}
