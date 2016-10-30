/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-queue module.
 *
 *  Patient-queue module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-queue module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-queue module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/


package org.openmrs.module.patientqueue.web.controller.queue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.patientqueue.util.OPDPatientQueueConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * Class: OpdPatientQueueController
 * </p>
 * <p>
 * Package: org.openmrs.module.patientqueue.web.controller.queue
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
 * Create date: Feb 17, 2011 10:39:52 AM
 * </p>
 * <p>
 * Update date: Feb 17, 2011 10:39:52 AM
 * </p>
 **/

@Controller("OpdPatientQueueController")
public class OpdPatientQueueController {
	Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/module/patientqueue/opdPatientQueue.htm", method = RequestMethod.GET)
	public String viewOpdPatientQueue(
			@RequestParam(value = "opdId", required = false) Integer opdId,
			Map<String, Object> model, HttpServletRequest request) {

		if (opdId != null && opdId > 0) {
			PatientQueueService patientQueueService = Context
					.getService(PatientQueueService.class);

			List<OpdPatientQueue> patientQueues = patientQueueService
					.listOpdPatientQueue(null,  opdId, "", 0, 0);
			model.put("patientQueues", patientQueues);
		}

		return "/module/patientqueue/queue/opdPatientQueue";
	}

	@RequestMapping(value = "/module/patientqueue/searchPatientInQueue.htm")
	public String searchOpdPatientQueue(
			@RequestParam(value = "opdId", required = false) Integer opdId,
			@RequestParam(value = "text", required = false) String text,
			Map<String, Object> model, HttpServletRequest request) {

		if (StringUtils.isNotBlank(text) && opdId != null && opdId > 0) {
			String prefix = Context.getAdministrationService()
					.getGlobalProperty("registration.identifier_prefix");
			if (text.contains("-") && !text.contains(prefix)) {
				text = prefix + text;
			}
			PatientQueueService patientQueueService = Context
					.getService(PatientQueueService.class);
			List<OpdPatientQueue> patientQueues = patientQueueService
					.listOpdPatientQueue(text.trim(), opdId, "", 0, 0);
			model.put("patientQueues", patientQueues);
			model.put("queueText", text);
		}

		return "/module/patientqueue/queue/searchPatientInQueue";
	}

	@RequestMapping(value = "/module/patientqueue/searchPatientInSystem.htm")
	public String searchOpdPatientSystem(
			@RequestParam(value = "opdId", required = false) Integer referralConceptId,
			@RequestParam(value = "phrase", required = false) String phrase,
			Model model, HttpServletRequest request) {

		if (StringUtils.isNotBlank(phrase)) {
			String prefix = Context.getAdministrationService()
					.getGlobalProperty("registration.identifier_prefix");
			if (phrase.contains("-") && !phrase.contains(prefix)) {
				phrase = prefix + phrase;
			}
			List<Patient> patientsList = Context.getPatientService()
					.getPatients(phrase.trim());
			model.addAttribute("listPatients", patientsList);
			model.addAttribute("phrase", phrase);
		}
		return "/module/patientqueue/queue/searchPatientInSystem";
	}

	@RequestMapping(value = "/module/patientqueue/selectPatientInQueue.htm", method = RequestMethod.GET)
	public String selectPatientInQueue(@RequestParam("id") Integer queueItemId) {
		PatientQueueService queueService = Context
				.getService(PatientQueueService.class);
		OpdPatientQueue queue = queueService
				.getOpdPatientQueueById(queueItemId);
		queue.setStatus(Context.getAuthenticatedUser().getGivenName() + " "
				+ OPDPatientQueueConstants.STATUS);
		queue.setUser(Context.getAuthenticatedUser());
		queueService.saveOpdPatientQueue(queue);
		// change requirement when we come to dashboard if conclu visit will
		// delete
		/*
		 * queue.setUser(Context.getAuthenticatedUser());
		 * 
		 * OpdPatientQueueLog queueLog = new OpdPatientQueueLog();
		 * queueLog.setOpdConcept(queue.getOpdConcept());
		 * queueLog.setOpdConceptName(queue.getOpdConceptName());
		 * queueLog.setPatient(queue.getPatient());
		 * queueLog.setCreatedOn(queue.getCreatedOn());
		 * queueLog.setPatientIdentifier(queue.getPatientIdentifier());
		 * queueLog.setPatientName(queue.getPatientName());
		 * queueLog.setReferralConcept(queue.getReferralConcept());
		 * queueLog.setReferralConceptName(queue.getReferralConceptName());
		 * queueLog.setSex(queue.getSex()); queueLog.setUser(queue.getUser());
		 * 
		 * OpdPatientQueueLog opdPatientLog =
		 * queueService.saveOpdPatientQueueLog(queueLog);
		 * queueService.deleteOpdPatientQueue(queue);
		 */
		return "redirect:/module/patientdashboard/main.htm?patientId="
				+ queue.getPatient().getPatientId() + "&opdId="
				+ queue.getOpdConcept().getConceptId() + "&referralId="
				+ queue.getReferralConcept().getConceptId() + "&queueId="
				+ queue.getId();
	}

	@RequestMapping(value = "/module/patientqueue/selectPatientInSystem.htm", method = RequestMethod.GET)
	public String selectPatientInSystem(@RequestParam("id") Integer patientId,
			@RequestParam("opdId") Integer opdId) {
		PatientQueueService queueService = Context
				.getService(PatientQueueService.class);
		Patient patient = Context.getPatientService().getPatient(patientId);

		Concept opdConcept = Context.getConceptService().getConcept(opdId);

		if (opdConcept == null) {
			log.info("OPD Ward is null");
			return "redirect:/module/patientqueue/main.htm";
		}
		
		OpdPatientQueue opq = queueService.getOpdPatientQueue(patient.getPatientIdentifier().getIdentifier(), opdId);
		
		if(opq!=null){
			return "redirect:/module/patientdashboard/main.htm?patientId="
			+ opq.getPatient().getPatientId() + "&opdId="
			+ opq.getOpdConcept().getConceptId() + "&referralId="
			+ opq.getReferralConcept()
			.getConceptId() + "&queueId=" + opq.getId();
		}

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

		Integer referralId = conRevisit != null ? queue.getReferralConcept()
				.getConceptId() : null;
		queue.setBirthDate(patient.getBirthdate());
		queue.setUser(Context.getAuthenticatedUser());
		queue.setStatus(Context.getAuthenticatedUser().getGivenName() + " "+ OPDPatientQueueConstants.STATUS);

		OpdPatientQueue opdPatientQueue = queueService.saveOpdPatientQueue(queue);
		//HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		//hcs.createObsGroup(patient, HospitalCoreConstants.PROPERTY_OBSGROUP);

		return "redirect:/module/patientdashboard/main.htm?patientId="
				+ queue.getPatient().getPatientId() + "&opdId="
				+ queue.getOpdConcept().getConceptId() + "&referralId="
				+ referralId + "&queueId=" + opdPatientQueue.getId();
	}

}
