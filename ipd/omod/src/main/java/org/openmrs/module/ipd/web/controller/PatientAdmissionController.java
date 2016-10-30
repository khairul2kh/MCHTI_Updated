/**
 * Copyright 2010 Society for Health Information Systems Programmes, India (HISP
 * India)
 *
 * This file is part of IPD module.
 *
 * IPD module is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * IPD module is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * IPD module. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.openmrs.module.ipd.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.openmrs.module.ipd.util.IpdConstants;
import org.openmrs.module.ipd.util.IpdUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedQueueLog;

/**
 * <p>
 * Class: PatientAdmissionController
 * </p>
 * <p>
 * Package: org.openmrs.module.ipd.web.controller
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
 * Create date: Mar 18, 2011 12:43:04 PM
 * </p>
 * <p>
 * Update date: Mar 18, 2011 12:43:04 PM
 * </p>
 *
 */
@Controller("IPDPatientAdmissionController")
public class PatientAdmissionController {

    @RequestMapping(value = "/module/ipd/patientsForAdmission.htm", method = RequestMethod.GET)
    public String firstView(@RequestParam(value = "searchPatient", required = false) String searchPatient,//patient name or patient identifier
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "ipdWardString", required = false) String ipdWardString, //ipdWard multiselect
            @RequestParam(value = "tab", required = false) Integer tab, //If that tab is active we will set that tab active when page load.
            @RequestParam(value = "doctorString", required = false) String doctorString, Model model) {

        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        List<IpdPatientAdmission> listPatientAdmission = ipdService.searchIpdPatientAdmission(searchPatient,
                IpdUtils.convertStringToList(doctorString), fromDate, toDate, IpdUtils.convertStringToList(ipdWardString), "");

        model.addAttribute("listPatientAdmission", listPatientAdmission);

        return "module/ipd/patientsForAdmission";
    }

    @RequestMapping(value = "/module/ipd/admission.htm", method = RequestMethod.GET)
    public String admission(@RequestParam(value = "admissionId", required = false) Integer admissionId, //If that tab is active we will set that tab active when page load.
            Model model) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        Concept ipdConcept = Context.getConceptService().getConceptByName(
                Context.getAdministrationService().getGlobalProperty(IpdConstants.PROPERTY_IPDWARD));
        model.addAttribute("listIpd", ipdConcept != null ? new ArrayList<ConceptAnswer>(ipdConcept.getAnswers()) : null);
        IpdPatientAdmission admission = ipdService.getIpdPatientAdmission(admissionId);
        if (admission != null) {
            PersonAddress add = admission.getPatient().getPersonAddress();
            String address = add.getAddress1();
            //ghansham 25-june-2013 issue no # 1924 Change in the address format
            String district = add.getCountyDistrict();
            String upazila = add.getCityVillage();

            String doctorRoleProps = Context.getAdministrationService().getGlobalProperty(
                    IpdConstants.PROPERTY_NAME_DOCTOR_ROLE);
            Role doctorRole = Context.getUserService().getRole(doctorRoleProps);
            if (doctorRole != null) {
                List<User> listDoctor = Context.getUserService().getUsersByRole(doctorRole);
                model.addAttribute("listDoctor", listDoctor);
            }

            PersonAttribute relationNameattr = admission.getPatient().getAttribute("Father/Husband Name");

            PersonAttribute relationTypeattr = admission.getPatient().getAttribute("Relative Name Type");

            model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
            // ghansham 25-june-2013 issue no # 1924 Change in the address format
            model.addAttribute("district", district);
            model.addAttribute("upazila", upazila);
            model.addAttribute("relationName", relationNameattr.getValue());

            /*ghanshyam 30/07/2012 this code modified under feedback of #290 for new patient it is working fine but for old patient it is giving null pointer 
             exception.*/
            if (relationTypeattr != null) {
                model.addAttribute("relationType", relationTypeattr.getValue());
            } else {
                model.addAttribute("relationType", "Relative Name");
            }

            model.addAttribute("admission", admission);

            //ghanshyam 25-oct-2012 Bug #425 [IPD Module] Admission Date fetched incorrectly.
            model.addAttribute("dateAdmission", new Date());

            // patient category
            model.addAttribute("patCategory", PatientUtils.getPatientCategory(admission.getPatient()));

            return "module/ipd/admissionForm";
        }
        System.out.println("admission is NULL");
        return "redirect:/module/ipd/main.htm";
    }

    @RequestMapping(value = "/module/ipd/admission.htm", method = RequestMethod.POST)
    public String admissionSubmit(
            HttpServletRequest request, Model model) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        int id = NumberUtils.toInt(request.getParameter("id"));
        IpdPatientAdmission admission = ipdService.getIpdPatientAdmission(id);

        
        String caste = request.getParameter("caste");
        
        String basicPay = request.getParameter("basicPay");
        int admittedWard = NumberUtils.toInt(request.getParameter("admittedWard"), 0);
        String bedNumber = request.getParameter("bedNumber");
        
        String comments = request.getParameter("comments");

        int treatingDoctor = NumberUtils.toInt(request.getParameter("treatingDoctor"), 0);

        String fathername = "";
        String relationshipType = "";

        User treatingD = null;
        try {

            Date date = new Date();

            //ghanshyam 25-oct-2012 Bug #425 [IPD Module] Admission Date fetched incorrectly.(note:commented below line date = admission.getAdmissionDate())
            //date = admission.getAdmissionDate();
            //copy admission to log
            IpdPatientAdmissionLog patientAdmissionLog = new IpdPatientAdmissionLog();
            patientAdmissionLog.setAdmissionDate(date);
            patientAdmissionLog.setAdmissionWard(Context.getConceptService().getConcept(admittedWard));
            patientAdmissionLog.setBirthDate(admission.getPatient().getBirthdate());
            patientAdmissionLog.setGender(admission.getPatient().getGender());
            patientAdmissionLog.setOpdAmittedUser(Context.getAuthenticatedUser());
            patientAdmissionLog.setOpdLog(admission.getOpdLog());
            patientAdmissionLog.setPatient(admission.getPatient());
            patientAdmissionLog.setPatientIdentifier(admission.getPatientIdentifier());
            patientAdmissionLog.setPatientName(admission.getPatientName());
            patientAdmissionLog.setStatus(IpdConstants.STATUS[0]);

            //save ipd encounter
            User user = Context.getAuthenticatedUser();
            EncounterType encounterType = Context.getService(HospitalCoreService.class).insertEncounterTypeByKey(
                    HospitalCoreConstants.PROPERTY_IPDENCOUNTER);

            Encounter encounter = new Encounter();
            Location location = new Location(1);
            encounter.setPatient(admission.getPatient());
            encounter.setCreator(user);
            encounter.setProvider(user);
            encounter.setEncounterDatetime(date);
            encounter.setEncounterType(encounterType);
            encounter.setLocation(location);
            encounter = Context.getEncounterService().saveEncounter(encounter);
            //done save ipd encounter
            patientAdmissionLog.setIpdEncounter(encounter);
            //Get Opd Obs Group
            Obs obsGroup = Context.getService(HospitalCoreService.class).getObsGroup(admission.getPatient());
            patientAdmissionLog.setOpdObsGroup(obsGroup);

            patientAdmissionLog = ipdService.saveIpdPatientAdmissionLog(patientAdmissionLog);

            //delete admission
            if (patientAdmissionLog != null && patientAdmissionLog.getId() != null) {
                ipdService.removeIpdPatientAdmission(admission);
            }

            PersonAddress add = admission.getPatient().getPersonAddress();

            String address = add.getAddress1();
            // ghansham 25-june-2013 issue no # 1924 Change in the address format
            String district = add.getCountyDistrict();
            String upazila = add.getCityVillage();
            model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
            model.addAttribute("district", district);
            model.addAttribute("upazila", upazila);

            PersonAttribute relationNameattr = admission.getPatient().getAttribute("Father/Husband Name");
            PersonAttribute relationTypeattr = admission.getPatient().getAttribute("Relative Name Type");
            model.addAttribute("relationName", relationNameattr.getValue());
            //ghanshyam 02/08/2012 [IPD - Bug #331] (New) DDU-SDMX-IPD-0.9.7SNAP SHOT,Error in ipd admission form
            if (relationTypeattr != null) {
                model.addAttribute("relationType", relationTypeattr.getValue());
            } else {
                model.addAttribute("relationType", "Relative Name");
            }
            //model.addAttribute("relationType", relationTypeattr.getValue());

            model.addAttribute("dateAdmission", date);

            //save in admitted
             /*  /// IpdPatientAdmitted remove and save in the " ipd_patient_admitted_queue_log " for before billing 
            
             IpdPatientAdmitted admitted = new IpdPatientAdmitted();
             admitted.setAdmissionDate(date);
             admitted.setAdmittedWard(Context.getConceptService().getConcept(admittedWard));
             admitted.setBasicPay(basicPay);
             admitted.setBed(bedNumber);
             //ghanshyam 11-july-2013 feedback # 1724 Introducing bed availability
             admitted.setComments(comments);
             admitted.setBirthDate(admission.getPatient().getBirthdate());
             admitted.setCaste(caste);
             if (relationNameattr != null) {
             fathername = relationNameattr.getValue();
             }
             admitted.setFatherName(fathername);
             if (relationTypeattr != null) {
             relationshipType = relationTypeattr.getValue();
				
             }
             //ghanshyam 02/08/2012 [IPD - Bug #331] (New) DDU-SDMX-IPD-0.9.7SNAP SHOT,Error in ipd admission form
             else{
             relationshipType = "Relative Name";
             }
			
             admitted.setRelationshipType(relationshipType);
             admitted.setGender(admission.getPatient().getGender());
			
             treatingD = Context.getUserService().getUser(treatingDoctor);
			
             admitted.setIpdAdmittedUser(treatingD);
             //ghanshyam 27-02-2013 Support #965[IPD]change Tehsil TO Upazila,reomve monthly income field,remove IST Time for Bangladesh module
             //admitted.setMonthlyIncome(monthlyIncome);
             //ghanshyam 7-august-2013 code review bug
             if(patientAdmissionLog!=null){
             admitted.setPatient(patientAdmissionLog.getPatient());
             }
             admitted.setPatientAddress(StringUtils.isNotBlank(address) ? address : "");
             admitted.setPatientAdmissionLog(patientAdmissionLog);
             admitted.setPatientIdentifier(admission.getPatientIdentifier());
             admitted.setPatientName(admission.getPatientName());
             admitted.setStatus(IpdConstants.STATUS[0]);
             admitted.setUser(Context.getAuthenticatedUser());
             admitted = ipdService.saveIpdPatientAdmitted(admitted);
             model.addAttribute("admitted", admitted);
             */
            
            // New code Enter Here for billing queue  
            // Save in Ipd Patient Admitted Queue Log
            treatingD = Context.getUserService().getUser(treatingDoctor);
            
            IpdPatientAdmittedQueueLog admittedqueuelog = new IpdPatientAdmittedQueueLog();

            admittedqueuelog.setAdmissionDate(date);
            admittedqueuelog.setBed(bedNumber);
            admittedqueuelog.setBirthDate(admission.getBirthDate());
            admittedqueuelog.setGender(admission.getPatient().getGender());
            admittedqueuelog.setPatient(admission.getPatient());
            admittedqueuelog.setAdmittedWard(Context.getConceptService().getConcept(admittedWard));
            admittedqueuelog.setUser(user);
            admittedqueuelog.setPatientIdentifier(admission.getPatientIdentifier());
            admittedqueuelog.setFatherName(fathername);
            admittedqueuelog.setPatientAddress(address);
            admittedqueuelog.setPatientName(admission.getPatientName());
            admittedqueuelog.setStatus("admitted pending");
            admittedqueuelog.setIpdEncounter(encounter);
            admittedqueuelog.setMonthlyIncome(BigDecimal.ZERO);
            admittedqueuelog.setCaste(caste);
            
            if (relationNameattr != null) {
             fathername = relationNameattr.getValue();
             }
             admittedqueuelog.setFatherName(fathername);
             if (relationTypeattr != null) {
             relationshipType = relationTypeattr.getValue();
				
             }
             //ghanshyam 02/08/2012 [IPD - Bug #331] (New) DDU-SDMX-IPD-0.9.7SNAP SHOT,Error in ipd admission form
             else{
             relationshipType = "Relative Name";
             }
		
            
            
            admittedqueuelog.setComments(comments);
            admittedqueuelog.setBasicPay(basicPay);
            admittedqueuelog.setAdmittedWard(Context.getConceptService().getConcept(admittedWard));
            admittedqueuelog.setPatientAdmissionLog(patientAdmissionLog);
            admittedqueuelog.setIpdAdmittedUser(treatingD);
            ipdService.saveIpdPatientAdmittedQueueLog(admittedqueuelog);
            model.addAttribute("admitted", admittedqueuelog);
            /// End 
            
            String type = "5";

            PatientDashboardService patientDashboardService = (PatientDashboardService) Context.getService(PatientDashboardService.class);
            ConceptService conceptService = Context.getConceptService();
            BillingService billingService = (BillingService) Context.getService(BillingService.class);
            //BillableService billableService = billingService.getServiceByConceptId(command.getIpdWard());
            BillableService billableService = billingService.getServiceByConceptName("ADMISSION CHARGES");

            OpdTestOrder opdTestOrder = new OpdTestOrder();
            opdTestOrder.setPatient(admission.getPatient());
            opdTestOrder.setEncounter(encounter);
            opdTestOrder.setConcept(conceptService.getConceptByName("ADMISSION CHARGES"));
            opdTestOrder.setTypeConcept(Integer.valueOf(type));
            opdTestOrder.setValueCoded(conceptService.getConceptByName("ADMISSION CHARGES"));
            opdTestOrder.setCreator(user);
            opdTestOrder.setCreatedOn(date);
            opdTestOrder.setBillableService(billableService);
            patientDashboardService.saveOrUpdateOpdOrder(opdTestOrder);

            /// End Here
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "module/ipd/admissionForm";
        }
        model.addAttribute("treatingDoctor", treatingD);
        model.addAttribute("relationName", fathername);
        model.addAttribute("relationType", relationshipType);
        model.addAttribute("message", "Succesfully");
        model.addAttribute("urlS", "main.htm");

        // patient category
        model.addAttribute("patCategory", PatientUtils.getPatientCategory(admission.getPatient()));

        return "module/ipd/thickbox/admissionPrint";
    }

    @RequestMapping(value = "/module/ipd/removeOrNoBed.htm", method = RequestMethod.GET)
    public String removeOrNoBed(@RequestParam(value = "admissionId", required = false) Integer admissionId, //If that tab is active we will set that tab active when page load.
            @RequestParam(value = "action", required = false) Integer action, Model model) {

        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        IpdPatientAdmission admission = ipdService.getIpdPatientAdmission(admissionId);

        User user = Context.getAuthenticatedUser();
        EncounterType encounterType = Context.getService(HospitalCoreService.class).insertEncounterTypeByKey(
                HospitalCoreConstants.PROPERTY_IPDENCOUNTER);

        if (admission != null && (action == 1 || action == 2)) {

            //remove
            Date date = new Date();
            //copy admission to log
            IpdPatientAdmissionLog patientAdmissionLog = new IpdPatientAdmissionLog();
            patientAdmissionLog.setAdmissionDate(date);
            patientAdmissionLog.setAdmissionWard(admission.getAdmissionWard());
            patientAdmissionLog.setBirthDate(admission.getBirthDate());
            patientAdmissionLog.setGender(admission.getGender());
            patientAdmissionLog.setOpdAmittedUser(user);
            patientAdmissionLog.setOpdLog(admission.getOpdLog());
            patientAdmissionLog.setPatient(admission.getPatient());
            patientAdmissionLog.setPatientIdentifier(admission.getPatientIdentifier());
            patientAdmissionLog.setPatientName(admission.getPatientName());
            patientAdmissionLog.setStatus(IpdConstants.STATUS[action]);

            //save ipd encounter
            Encounter encounter = new Encounter();
            Location location = new Location(1);
            encounter.setPatient(admission.getPatient());
            encounter.setCreator(user);
            encounter.setProvider(user);
            encounter.setEncounterDatetime(date);
            encounter.setEncounterType(encounterType);
            encounter.setLocation(location);
            encounter = Context.getEncounterService().saveEncounter(encounter);
            //done save ipd encounter
            patientAdmissionLog.setIpdEncounter(encounter);
            //Get Opd Obs Group
            Obs obsGroup = Context.getService(HospitalCoreService.class).getObsGroup(admission.getPatient());
            patientAdmissionLog.setOpdObsGroup(obsGroup);

            patientAdmissionLog = ipdService.saveIpdPatientAdmissionLog(patientAdmissionLog);

            if (patientAdmissionLog != null && patientAdmissionLog.getId() != null) {
                ipdService.removeIpdPatientAdmission(admission);
            }
            /*}else if(action == 2){
             //no bed
             //remove
             Date date = new Date();
             //copy admission to log
             IpdPatientAdmissionLog patientAdmissionLog = new IpdPatientAdmissionLog();
             patientAdmissionLog.setAdmissionDate(date);
             patientAdmissionLog.setAdmissionWard(admission.getAdmissionWard());
             patientAdmissionLog.setBirthDate(admission.getBirthDate());
             patientAdmissionLog.setGender(admission.getGender());
             patientAdmissionLog.setOpdAmittedUser(user);
             patientAdmissionLog.setOpdLog(admission.getOpdLog());
             patientAdmissionLog.setPatient(admission.getPatient());
             patientAdmissionLog.setPatientIdentifier(admission.getPatientIdentifier());
             patientAdmissionLog.setPatientName(admission.getPatientName());
             patientAdmissionLog.setStatus(IpdConstants.STATUS[2]);
				
             //save ipd encounter
				
				
             Encounter encounter = new Encounter();
             Location location = new Location( 1 );
             encounter.setPatient(admission.getPatient());
             encounter.setCreator( user);
             encounter.setProvider(user );
             encounter.setEncounterDatetime( new Date() );
             encounter.setEncounterType(encounterType);
             encounter.setLocation( location );
             encounter = Context.getEncounterService().saveEncounter(encounter);
             //done save ipd encounter
             patientAdmissionLog.setIpdEncounter(encounter);
             //Get Opd Obs Group
             Obs obsGroup = Context.getService(HospitalCoreService.class).getObsGroup(admission.getPatient());
             patientAdmissionLog.setOpdObsGroup(obsGroup);
				
             patientAdmissionLog = ipdService.saveIpdPatientAdmissionLog(patientAdmissionLog);
				
             if(patientAdmissionLog != null  && patientAdmissionLog.getId() != null){
             ipdService.removeIpdPatientAdmission(admission);
             }
             }*/
        }
        return "redirect:/module/ipd/main.htm";
    }
}
