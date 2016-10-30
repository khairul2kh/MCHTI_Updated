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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonName;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.InventoryCommonService;
import org.openmrs.module.hospitalcore.InventoryService;
import org.openmrs.module.ipd.util.IpdConstants;
import org.openmrs.module.ipd.util.IpdUtils;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.model.IpdPatientIntake;
import org.openmrs.module.hospitalcore.model.IpdPatientVitalStatistics;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.util.ConceptComparator;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;
import org.openmrs.module.hospitalcore.util.PatientDashboardConstants;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.openmrs.module.hospitalcore.model.OpdTestOrder;

/**
 * <p>
 * Class: PatientAdmittedController
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
 * Create date: Mar 18, 2011 12:43:28 PM
 * </p>
 * <p>
 * Update date: Mar 18, 2011 12:43:28 PM
 * </p>
 *
 */
@Controller("IPDPatientAdmittedController")
public class PatientAdmittedController {

    Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/module/ipd/admittedPatientIndex.htm", method = RequestMethod.GET)
    public String firstView(@RequestParam(value = "searchPatient", required = false) String searchPatient,//patient name or patient identifier
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "ipdWardString", required = false) String ipdWardString, //note ipdWardString = 1,2,3,4.....
            @RequestParam(value = "tab", required = false) Integer tab, //If that tab is active we will set that tab active when page load.
            @RequestParam(value = "doctorString", required = false) String doctorString,// note: doctorString= 1,2,3,4.....
            Model model) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        List<IpdPatientAdmitted> listPatientAdmitted = ipdService.searchIpdPatientAdmitted(searchPatient,
                IpdUtils.convertStringToList(doctorString), fromDate, toDate, IpdUtils.convertStringToList(ipdWardString), "");

        /*Sagar Bele 08-08-2012 Support #327 [IPD] (DDU(SDMX)instance) snapshot- age column in IPD admitted patient index */
        model.addAttribute("listPatientAdmitted", listPatientAdmitted);
        
        Map<Integer, String> mapRelationName = new HashMap<Integer, String>();
        Map<Integer, String> mapRelationType = new HashMap<Integer, String>();
        for (IpdPatientAdmitted admit : listPatientAdmitted) {
            PersonAttribute relationNameattr = admit.getPatient().getAttribute("Father/Husband Name");
            //ghanshyam 10/07/2012 New Requirement #312 [IPD] Add fields in the Discharge screen and print out
            PersonAddress add = admit.getPatient().getPersonAddress();
            String address1 = add.getAddress1();
            if (address1 != null) {
                String address = " " + add.getAddress1() + " " + add.getCountyDistrict() + " " + add.getCityVillage();
                model.addAttribute("address", address);
            } else {
                String address = " " + add.getCountyDistrict() + " " + add.getCityVillage();
                model.addAttribute("address", address);
            }
            PersonAttribute relationTypeattr = admit.getPatient().getAttribute("Relative Name Type");
            //ghanshyam 30/07/2012 this code modified under feedback of 'New Requirement #313'
            if (relationTypeattr != null) {
                mapRelationType.put(admit.getId(), relationTypeattr.getValue());
            } else {
                mapRelationType.put(admit.getId(), "Relative Name");
            }
            mapRelationName.put(admit.getId(), relationNameattr.getValue());
        }
        model.addAttribute("mapRelationName", mapRelationName);
        model.addAttribute("mapRelationType", mapRelationType);
        model.addAttribute("dateTime", new Date().toString());

//		model.addAttribute("listPatientAdmitted", listPatientAdmitted);
        return "module/ipd/admittedList";
    }

    ///////// New Code //////////
    @RequestMapping(value = "/module/ipd/treatmentView.htm", method = RequestMethod.GET)
    public String treatmentView(@RequestParam(value = "id", required = false) Integer admittedId,
            @RequestParam(value = "patientId", required = false) Integer patientId,
            @RequestParam(value = "encounterId", required = false) Integer encounterId,
            @RequestParam(value = "patientAdmissionLogId", required = false) Integer patientAdmissionLogId, Model model) {
        
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        Concept ipdConcept = Context.getConceptService().getConceptByName(
                Context.getAdministrationService().getGlobalProperty(IpdConstants.PROPERTY_IPDWARD));
        
        model.addAttribute("listIpd", ipdConcept != null ? new ArrayList<ConceptAnswer>(ipdConcept.getAnswers()) : null);
        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        model.addAttribute("admitted", admitted);
        
        Patient patient = admitted.getPatient();
        PersonAddress add = patient.getPersonAddress();
        String address = add.getAddress1();
        // ghansham 25-june-2013 issue no # 1924 Change in the address format
        String district = add.getCountyDistrict();
        String upazila = add.getCityVillage();
        model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
        model.addAttribute("district", district);
        model.addAttribute("upazila", upazila);

        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        model.addAttribute("relationName", relationNameattr.getValue());

        //Patient category
        model.addAttribute("patCategory", PatientUtils.getPatientCategory(patient));

        // Drug Assign View
        String opdDrug = "";
        List<OpdDrugOrder> listOpdDrug = ipdService.getOpdDrugOrderByPatientEncounterId(patient.getPatientId());
        for (OpdDrugOrder opd : listOpdDrug) {
            opdDrug += "&#9764;  " + opd.getInventoryDrug().getName() + " - "
                    + opd.getInventoryDrugFormulation().getName() + " "
                    + opd.getInventoryDrugFormulation().getDozage() + " - "
                    + opd.getFrequency().getDisplayString().toString() + " - "
                    + opd.getNoOfDays().toString() + "&#2495;&#2470;&#2472;" + "<br/>";
        }
        model.addAttribute("opdDrug", opdDrug);

        model.addAttribute("listDrug", listOpdDrug);
        
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("dat", formatter.format(new Date()));

        //For OPD 
        List<IpdPatientAdmissionLog> currentAdmissionList = ipdService.listIpdPatientAdmissionLog(patient.getPatientId(), null, IpdPatientAdmitted.STATUS_ADMITTED, 0, 1);
        IpdPatientAdmissionLog currentAdmission = CollectionUtils.isEmpty(currentAdmissionList) ? null : currentAdmissionList.get(0);

        if (currentAdmission != null) {
            IpdPatientAdmitted admitted1 = ipdService.getAdmittedByPatientId(patient.getPatientId());
            //admitted.getPatient().getPatientId();
            model.addAttribute("admitted", admitted1);
            String diagnosis = "";
            String procedure = "";
            String investigation = "";
            //String drugList = "";

            List<Obs> listObsByObsGroup = Context.getObsService().findObsByGroupId(currentAdmission.getOpdObsGroup().getId());

            //get diagnosis
            ConceptService conceptService = Context.getConceptService();

            AdministrationService administrationService = Context.getAdministrationService();

            String gpDiagnosis = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
            String gpProcedure = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
            String gpInvestigation = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_FOR_INVESTIGATION);

            Concept conDiagnosis = conceptService.getConcept(gpDiagnosis);
            Concept conProcedure = conceptService.getConcept(gpProcedure);
            Concept conInvestigation = conceptService.getConcept(gpInvestigation);

            if (CollectionUtils.isNotEmpty(listObsByObsGroup)) {
                for (Obs obs : listObsByObsGroup) {
                    //diagnosis
                    //ghanshyam 7-august-2013 code review bug
                    if (obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId()) && obs.getObsGroup() != null && obs.getObsGroup().getId().equals(currentAdmission.getOpdObsGroup().getId())) {
                        if (obs.getValueCoded() != null) {
                            diagnosis += obs.getValueCoded().getName() + "<br/>";
                        }
                        if (StringUtils.isNotBlank(obs.getValueText())) {
                            diagnosis += obs.getValueText() + "<br/>";
                        }
                    }
                    //Sagar Bele Date:31-12-2012 Add procedure done in IPD record block in OPD patient-dashboard #555					
                    //procedure
                    //ghanshyam 7-august-2013 code review bug
                    if (obs.getConcept().getConceptId().equals(conProcedure.getConceptId()) && obs.getObsGroup() != null && obs.getObsGroup().getId().equals(currentAdmission.getOpdObsGroup().getId())) {
                        if (obs.getValueCoded() != null) {
                            procedure += obs.getValueCoded().getName() + "<br/>";
                        }
                        if (StringUtils.isNotBlank(obs.getValueText())) {
                            procedure += obs.getValueText() + "<br/>";
                        }
                    }

                    if (obs.getConcept().getConceptId().equals(conInvestigation.getConceptId()) && obs.getObsGroup() != null && obs.getObsGroup().getId().equals(currentAdmission.getOpdObsGroup().getId())) {
                        if (obs.getValueCoded() != null) {
                            investigation += obs.getValueCoded().getName() + "<br/>";
                        }
                        if (StringUtils.isNotBlank(obs.getValueText())) {
                            investigation += obs.getValueText() + "<br/>";
                        }
                    }

                }
            }
            if (StringUtils.endsWith(diagnosis, "<br/>")) {
                diagnosis = StringUtils.removeEnd(diagnosis, "<br/>");
            }
            //Sagar Bele Date:31-12-2012 Add procedure done in IPD record block in OPD patient-dashboard #555
            if (StringUtils.endsWith(procedure, "<br/>")) {
                procedure = StringUtils.removeEnd(procedure, "<br/>");
            }

            if (StringUtils.endsWith(investigation, "<br/>")) {
                investigation = StringUtils.removeEnd(investigation, "<br/>");
            }

            model.addAttribute("diagnosis", diagnosis);
            model.addAttribute("procedure", procedure);
            model.addAttribute("investigation", investigation);
        }
        return "module/ipd/treatmentView";
    }

    //// End /////////      
    //ghanshyam 10-june-2013 New Requirement #1847 Capture Vital statistics for admitted patient in ipd
    @RequestMapping(value = "/module/ipd/vitalStatistics.htm", method = RequestMethod.GET)
    public String vitalSatatisticsView(@RequestParam(value = "id", required = false) Integer admittedId,
            @RequestParam(value = "patientAdmissionLogId", required = false) Integer patientAdmissionLogId, Model model) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        Concept ipdConcept = Context.getConceptService().getConceptByName(
                Context.getAdministrationService().getGlobalProperty(IpdConstants.PROPERTY_IPDWARD));
        model.addAttribute("listIpd", ipdConcept != null ? new ArrayList<ConceptAnswer>(ipdConcept.getAnswers()) : null);
        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        model.addAttribute("admitted", admitted);

        Patient patient = admitted.getPatient();

        PersonAddress add = patient.getPersonAddress();
        String address = add.getAddress1();
        // ghansham 25-june-2013 issue no # 1924 Change in the address format
        String district = add.getCountyDistrict();
        String upazila = add.getCityVillage();
        model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
        model.addAttribute("district", district);
        model.addAttribute("upazila", upazila);

        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        model.addAttribute("relationName", relationNameattr.getValue());

        //Patient category
        model.addAttribute("patCategory", PatientUtils.getPatientCategory(patient));
        List<IpdPatientVitalStatistics> ipdPatientVitalStatistics = ipdService.getIpdPatientVitalStatistics(patient.getPatientId(), patientAdmissionLogId);
        model.addAttribute("ipdPatientVitalStatistics", ipdPatientVitalStatistics);
        model.addAttribute("sizeOfipdPatientVitalStatistics", ipdPatientVitalStatistics.size() + 1);
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("dat", formatter.format(new Date()));
        List<Concept> dietConcept = ipdService.getDiet();
        model.addAttribute("dietList", dietConcept);
        return "module/ipd/vitalStatisticsForm";
    }

    //ghanshyam 10-june-2013 New Requirement #1847 Capture Vital statistics for admitted patient in ipd
    @RequestMapping(value = "/module/ipd/vitalStatistics.htm", method = RequestMethod.POST)
    public String vitalSatatisticsPost(@RequestParam("admittedId") Integer admittedId,
            @RequestParam("patientId") Integer patientId,
            @RequestParam(value = "bloodPressure", required = false) String bloodPressure,
            @RequestParam(value = "pulseRate", required = false) String pulseRate,
            @RequestParam(value = "temperature", required = false) String temperature,
            @RequestParam(value = "weight", required = false) String weight,
            @RequestParam(value = "dietAdvised", required = false) String dietAdvised,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "respirationRate", required = false) String respirationRate,
            @RequestParam(value = "urineTaken", required = false) String urineTaken,
            @RequestParam(value = "stoolTaken", required = false) String stoolTaken,
            Model model, HttpServletRequest request) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        PatientService patientService = Context.getPatientService();
        Patient patient = patientService.getPatient(patientId);

        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        String dietAdvise = "";
        String select[] = request.getParameterValues("dietAdvised");
        if (select != null && select.length != 0) {
            for (int i = 0; i < select.length; i++) {
                dietAdvise = dietAdvise + select[i] + " ";
            }
        }
        IpdPatientVitalStatistics ipdPatientVitalStatistics = new IpdPatientVitalStatistics();
        ipdPatientVitalStatistics.setPatient(patient);
        ipdPatientVitalStatistics.setIpdPatientAdmissionLog(admitted.getPatientAdmissionLog());
        ipdPatientVitalStatistics.setBloodPressure(bloodPressure);
        ipdPatientVitalStatistics.setPulseRate(pulseRate);
        ipdPatientVitalStatistics.setTemperature(temperature);
        ipdPatientVitalStatistics.setWeight(weight);
        ipdPatientVitalStatistics.setDietAdvised(dietAdvise);
        ipdPatientVitalStatistics.setNote(notes);

        ipdPatientVitalStatistics.setRespirationRate(respirationRate);
        if ("1".equals(urineTaken)) {
            ipdPatientVitalStatistics.setUrineTaken("Yes");
        } else {
            ipdPatientVitalStatistics.setUrineTaken("No");
        }
        if ("1".equals(stoolTaken)) {
            ipdPatientVitalStatistics.setStoolTaken("Yes");
        } else {
            ipdPatientVitalStatistics.setStoolTaken("No");
        }

        //User user =Context.getAuthenticatedUser();
        ipdPatientVitalStatistics.setCreator(Context.getAuthenticatedUser().getUserId());
        ipdPatientVitalStatistics.setCreatedOn(new Date());
        ipdService.saveIpdPatientVitalStatistics(ipdPatientVitalStatistics);
        model.addAttribute("urlS", "main.htm?tab=1");
        model.addAttribute("message", "Succesfully");
        return "/module/ipd/thickbox/success";
    }
    // For NIKDU IPD Patient Intake sign

    @RequestMapping(value = "/module/ipd/intakeView.htm", method = RequestMethod.GET)
    public String intakeView(@RequestParam(value = "id", required = false) Integer admittedId,
            @RequestParam(value = "patientId", required = false) Integer patientId,
            @RequestParam(value = "patientAdmissionLogId", required = false) Integer patientAdmissionLogId, Model model) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        model.addAttribute("admitted", admitted);

        Patient patient = admitted.getPatient();

        PersonAddress add = patient.getPersonAddress();
        String address = add.getAddress1();
        // ghansham 25-june-2013 issue no # 1924 Change in the address format
        String district = add.getCountyDistrict();
        String upazila = add.getCityVillage();
        model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
        model.addAttribute("district", district);
        model.addAttribute("upazila", upazila);

        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        model.addAttribute("relationName", relationNameattr.getValue());

        //Patient category
        model.addAttribute("patCategory", PatientUtils.getPatientCategory(patient));
        List<IpdPatientIntake> ipdPatientIntake = ipdService.getIpdPatientIntake(patient.getPatientId(), patientAdmissionLogId);
        model.addAttribute("ipdPatientIntake", ipdPatientIntake);
        model.addAttribute("sizeOfipdPatientIntake", ipdPatientIntake.size() + 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("dat", formatter.format(new Date()));

        return "/module/ipd/intakeView";
    }

    @RequestMapping(value = "/module/ipd/intakeView.htm", method = RequestMethod.POST)
    public String intakeViewPost(@RequestParam("admittedId") Integer admittedId,
            @RequestParam("patientId") Integer patientId,
            @RequestParam(value = "oral", required = false) String oral,
            @RequestParam(value = "iv", required = false) String iv,
            @RequestParam(value = "ngTube", required = false) String ngTube,
            @RequestParam(value = "spc", required = false) String spc,
            @RequestParam(value = "catheter", required = false) String catheter,
            @RequestParam(value = "stool", required = false) String stool,
            @RequestParam(value = "vomiting", required = false) String vomiting,
            @RequestParam(value = "drain1", required = false) String drain1,
            @RequestParam(value = "drain2", required = false) String drain2,
            @RequestParam(value = "drain3", required = false) String drain3,
            @RequestParam(value = "drain4", required = false) String drain4,
            @RequestParam(value = "drain5", required = false) String drain5,
            @RequestParam(value = "drain6", required = false) String drain6, Model model, HttpServletRequest request) {

        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        PatientService patientService = Context.getPatientService();
        Patient patient = patientService.getPatient(patientId);

        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        IpdPatientIntake ipdPatientIntake = new IpdPatientIntake();
        ipdPatientIntake.setPatient(patient);
        ipdPatientIntake.setIpdPatientAdmissionLog(admitted.getPatientAdmissionLog());
        ipdPatientIntake.setOral(oral);
        ipdPatientIntake.setIv(iv);
        ipdPatientIntake.setNgTube(ngTube);
        ipdPatientIntake.setSpc(spc);
        ipdPatientIntake.setCatheter(catheter);
        ipdPatientIntake.setStool(stool);
        ipdPatientIntake.setVomiting(vomiting);
        ipdPatientIntake.setDrain1(drain1);
        ipdPatientIntake.setDrain2(drain2);
        ipdPatientIntake.setDrain3(drain3);
        ipdPatientIntake.setDrain4(drain4);
        ipdPatientIntake.setDrain5(drain5);
        ipdPatientIntake.setDrain6(drain6);
        ipdPatientIntake.setCreatedOn(new Date());
        ipdService.saveIpdPatientIntake(ipdPatientIntake);
        model.addAttribute("urlS", "main.htm?tab=1");
        model.addAttribute("message", "Succesfully");
        return "/module/ipd/thickbox/success";
    }

/// IPD Patient Intake:: END 
    @RequestMapping(value = "/module/ipd/transfer.htm", method = RequestMethod.POST)
    public String transferPost(@RequestParam("admittedId") Integer id, @RequestParam("toWard") Integer toWardId,
            @RequestParam("doctor") Integer doctorId,
            @RequestParam(value = "bedNumber", required = false) String bed,
            //ghanshyam 11-july-2013 feedback # 1724 Introducing bed availability
            @RequestParam(value = "comments", required = false) String comments, Model model) {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        //ghanshyam 11-july-2013 feedback # 1724 Introducing bed availability
        ipdService.transfer(id, toWardId, doctorId, bed, comments);
        model.addAttribute("urlS", "main.htm?tab=1");
        model.addAttribute("message", "Succesfully");
        return "/module/ipd/thickbox/success";
    }

    @RequestMapping(value = "/module/ipd/transfer.htm", method = RequestMethod.GET)
    public String transferView(@RequestParam(value = "id", required = false) Integer admittedId, Model model) {

        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        Concept ipdConcept = Context.getConceptService().getConceptByName(
                Context.getAdministrationService().getGlobalProperty(IpdConstants.PROPERTY_IPDWARD));
        model.addAttribute("listIpd", ipdConcept != null ? new ArrayList<ConceptAnswer>(ipdConcept.getAnswers()) : null);
        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        model.addAttribute("admitted", admitted);

        String doctorRoleProps = Context.getAdministrationService()
                .getGlobalProperty(IpdConstants.PROPERTY_NAME_DOCTOR_ROLE);
        Role doctorRole = Context.getUserService().getRole(doctorRoleProps);
        if (doctorRole != null) {
            List<User> listDoctor = Context.getUserService().getUsersByRole(doctorRole);
            model.addAttribute("listDoctor", listDoctor);
        }

        Patient patient = admitted.getPatient();

        PersonAddress add = patient.getPersonAddress();
        String address = add.getAddress1();
        // ghansham 25-june-2013 issue no # 1924 Change in the address format
        String district = add.getCountyDistrict();
        String upazila = add.getCityVillage();
        model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
        model.addAttribute("district", district);
        model.addAttribute("upazila", upazila);

        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        model.addAttribute("relationName", relationNameattr.getValue());

        //Patient category
        model.addAttribute("patCategory", PatientUtils.getPatientCategory(patient));

        return "module/ipd/transferForm";
    }

    @RequestMapping(value = "/module/ipd/discharge.htm", method = RequestMethod.POST)
    public String dischargePost(IpdFinalResultCommand command, Model model,
            @RequestParam("otherInstructions") String otherInstructions) throws Exception {
        IpdService ipdService = (IpdService) Context.getService(IpdService.class);

        // harsh 6/14/2012 kill patient when "DEATH" is selected.
        if (Context.getConceptService().getConcept(command.getOutCome()).getName().getName().equalsIgnoreCase("DEATH")) {
            PatientService ps = Context.getPatientService();
            Patient patient = ps.getPatient(command.getPatientId());
            patient.setDead(true);
            patient.setDeathDate(new Date());

            ps.savePatient(patient);
        }

        //start
        //
        AdministrationService administrationService = Context.getAdministrationService();
        GlobalProperty gpDiagnosis = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
        GlobalProperty procedure = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
        GlobalProperty investigationn = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_FOR_INVESTIGATION);
        ConceptService conceptService = Context.getConceptService();

        Concept cDiagnosis = conceptService.getConceptByName(gpDiagnosis.getPropertyValue());
        Concept cProcedure = conceptService.getConceptByName(procedure.getPropertyValue());
        Concept cInvestigation = conceptService.getConceptByName(investigationn.getPropertyValue()); // for Investigation

        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(command.getAdmittedId());
        Encounter ipdEncounter = admitted.getPatientAdmissionLog().getIpdEncounter();
        List<Obs> listObsOfIpdEncounter = new ArrayList<Obs>(ipdEncounter.getAllObs());
        Location location = new Location(1);

        User user = Context.getAuthenticatedUser();
        Date date = new Date();
        //diagnosis

        Set<Obs> obses = new HashSet(ipdEncounter.getAllObs());

        ipdEncounter.setObs(null);
        //Add for Discharge certificate cTechbd 'Md khairul Islam'
        List<String> diagnosisList = new ArrayList();
        List<String> investigationList = new ArrayList();
        List<String> procedureList = new ArrayList();
        
        List<Concept> listConceptDianosisOfIpdEncounter = new ArrayList<Concept>();
        List<Concept> listConceptProcedureOfIpdEncounter = new ArrayList<Concept>();
        List<Concept> listConceptInvestigationOfIpdEncounter = new ArrayList<Concept>();

        if (CollectionUtils.isNotEmpty(listObsOfIpdEncounter)) {
            for (Obs obx : obses) {
                if (obx.getConcept().getConceptId().equals(cDiagnosis.getConceptId())) {
                    listConceptDianosisOfIpdEncounter.add(obx.getValueCoded());
                }

                if (obx.getConcept().getConceptId().equals(cProcedure.getConceptId())) {
                    listConceptProcedureOfIpdEncounter.add(obx.getValueCoded());
                }
                if (obx.getConcept().getConceptId().equals(cInvestigation.getConceptId())) {
                    listConceptInvestigationOfIpdEncounter.add(obx.getValueCoded());
                }
            }
        }

        List<Concept> listConceptDiagnosis = new ArrayList<Concept>();

        if (command.getSelectedDiagnosisList() != null) {
            for (Integer cId : command.getSelectedDiagnosisList()) {
                Concept cons = conceptService.getConcept(cId);
                listConceptDiagnosis.add(cons);
                if (!listConceptDianosisOfIpdEncounter.contains(cons)) {
                    Obs obsDiagnosis = new Obs();
                    //obsDiagnosis.setObsGroup(obsGroup);
                    obsDiagnosis.setConcept(cDiagnosis);
                    obsDiagnosis.setValueCoded(cons);
                    obsDiagnosis.setCreator(user);
                    obsDiagnosis.setObsDatetime(date);
                    obsDiagnosis.setLocation(location);
                    obsDiagnosis.setDateCreated(date);
                    obsDiagnosis.setPatient(ipdEncounter.getPatient());
                    obsDiagnosis.setEncounter(ipdEncounter);
                    obsDiagnosis.setValueCodedName(cons.getFullySpecifiedName(Locale.ENGLISH)); // for certificate
                    obsDiagnosis = Context.getObsService().saveObs(obsDiagnosis, "update obs diagnosis if need");
                    obses.add(obsDiagnosis);
                    diagnosisList.add(obsDiagnosis.getValueCodedName().getName()); // for Discharge certificate
                    
                }
            }
        }
        List<Concept> listConceptProcedure = new ArrayList<Concept>();
        if (!ArrayUtils.isEmpty(command.getSelectedProcedureList())) {

            if (cProcedure == null) {
                try {
                    throw new Exception("Post for procedure concept null");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            for (Integer pId : command.getSelectedProcedureList()) {
                Concept cons = conceptService.getConcept(pId);
                listConceptProcedure.add(cons);
                if (!listConceptProcedureOfIpdEncounter.contains(cons)) {
                    Obs obsProcedure = new Obs();
                    //obsDiagnosis.setObsGroup(obsGroup);
                    obsProcedure.setConcept(cProcedure);
                    obsProcedure.setValueCoded(conceptService.getConcept(pId));
                    obsProcedure.setCreator(user);
                    obsProcedure.setObsDatetime(date);
                    obsProcedure.setLocation(location);
                    obsProcedure.setPatient(ipdEncounter.getPatient());
                    obsProcedure.setDateCreated(date);
                    obsProcedure.setEncounter(ipdEncounter);
                    obsProcedure = Context.getObsService().saveObs(obsProcedure, "update obs diagnosis if need");
                    //ipdEncounter.addObs(obsProcedure);
                    obses.add(obsProcedure);
                    procedureList.add(obsProcedure.getValueCoded().getName().toString());
                }
            }

        }

                // Add Investigation 
        
        List<Concept> listConceptInvestigation = new ArrayList<Concept>();
        if (!ArrayUtils.isEmpty(command.getSelectedInvestigationList())) {

            if (cInvestigation == null) {
                try {
                    throw new Exception("Investigation concept null");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            for (Integer pId : command.getSelectedInvestigationList()) { //selectedInvestigationList
                Concept cons = conceptService.getConcept(pId);
                Concept co=conceptService.getConcept(pId);
                listConceptInvestigation.add(cons);
                if (!listConceptInvestigationOfIpdEncounter.contains(cons)) {
                    Obs obsInvestigation = new Obs();
                    //obsDiagnosis.setObsGroup(obsGroup);
                    obsInvestigation.setConcept(cInvestigation);
                    obsInvestigation.setValueCoded(co);
                    obsInvestigation.setCreator(user);
                    obsInvestigation.setObsDatetime(date);
                    obsInvestigation.setLocation(location);
                    obsInvestigation.setPatient(ipdEncounter.getPatient());
                    obsInvestigation.setDateCreated(date);
                    obsInvestigation.setEncounter(ipdEncounter);
                    obsInvestigation = Context.getObsService().saveObs(obsInvestigation, "update obs investigation if need");
                    //ipdEncounter.addObs(obsProcedure);
                    obses.add(obsInvestigation);
                    investigationList.add(obsInvestigation.getValueCoded().getName().toString());

                }
            }

        }
   
          
        // Remove obs diagnosis and procedure 
        
        for (Concept con : listConceptDianosisOfIpdEncounter) {
            if (!listConceptDiagnosis.contains(con)) {
                for (Obs obx : listObsOfIpdEncounter) {
                    if (obx.getValueCoded().getConceptId().intValue() == con.getConceptId().intValue()) {
                        Context.getObsService().deleteObs(obx);
                        obses.remove(obx);
                    }
                }
            }
        }

        for (Concept con : listConceptProcedureOfIpdEncounter) {
            if (!listConceptProcedure.contains(con)) {
                for (Obs obx : listObsOfIpdEncounter) {
                    if (obx.getValueCoded().getConceptId().intValue() == con.getConceptId().intValue()) {
                        Context.getObsService().deleteObs(obx);
                        obses.remove(obx);
                    }
                }
            }
        }
        
        for (Concept con : listConceptInvestigationOfIpdEncounter) {
            if (!listConceptInvestigation.contains(con)) {
                for (Obs obx : listObsOfIpdEncounter) {
                    if (obx.getValueCoded().getConceptId().intValue() == con.getConceptId().intValue()) {
                        Context.getObsService().deleteObs(obx);
                        obses.remove(obx);
                    }
                }
            }
        }

        ipdEncounter.setObs(obses);

        Context.getEncounterService().saveEncounter(ipdEncounter);
        PatientService ps = Context.getPatientService();
        Patient patient = ps.getPatient(command.getPatientId());
        
         Date date1;  
         date1=admitted.getAdmissionDate();
         
        ipdService.discharge(command.getAdmittedId(), command.getOutCome(), otherInstructions);
        model.addAttribute("urlS", "main.htm?tab=1");
        model.addAttribute("message", "Succesfully");
        
        String d = StringUtils.join(diagnosisList, ',');
        String inv=StringUtils.join(investigationList,',');
        String pro=StringUtils.join(procedureList,',');
        

        // OtuCome Show in discharge certificate
        String outCome = "";
        Concept concept = Context.getConceptService().getConcept(command.getOutCome().toString());
        outCome = concept.getDisplayString();
        
        // Address
        PersonAddress add=admitted.getPatient().getPersonAddress();
        
        String district= add.getCountyDistrict();
        String upazila = add.getCityVillage();
        String address=upazila+" "+district;
        
        //Relation Name and Type
        
        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        String relationName = relationNameattr.getValue();
        
        PersonAttribute relationTypeattr = patient.getAttribute("Relative Name Type");
        String relationType = relationTypeattr.getValue();
        
       // return "/module/ipd/thickbox/dischargeCertificate";
        return "redirect:/module/ipd/dischargeCertificate.htm?pid="+patient.getPatientIdentifier().getIdentifier().toString()+"&pname="+patient.getGivenName()
                +"&age="+ PatientUtils.estimateAge(patient.getBirthdate())+"&relationName="+relationName+"&relationType="+relationType
                +"&diagnosis="+d+"&investigation="+inv+"&procedure="+pro
                +"&outCome="+outCome+"&address="+ address +"&otherIns="+otherInstructions+"&admissionDate="+admitted.getAdmissionDate();
                

        //return "redirect:/module/ipd/DischargeCerficate.htm?pid=" + patient.getPatientIdentifier().getIdentifier().toString() + "&pname=" + patient.getGivenName() + "&pnamemiddle=" + patient.getMiddleName() + "&pnamefamily=" + patient.getFamilyName() + "&age=" + PatientUtils.estimateAge(patient.getBirthdate()) + "&diagnosis=" + d + "&investigations=" + i + "&procedures=" + p + "&drugs=" + dr + "&advice=" + command.getNote() + "&ccomplain=" + command.getCcomplain()+"&relationName="+ relationName +"&relationType="+ relationType + "&doctor=" + doctor+"&date="+date1+ "&odate=" + f.format(cal.getTime())+"&out="+outcome;
    }

    @RequestMapping(value = "/module/ipd/discharge.htm", method = RequestMethod.GET)
    public String dischargeView(@RequestParam(value = "id", required = false) Integer admittedId,
            @ModelAttribute("ipdCommand") IpdFinalResultCommand command, Model model) {

        IpdService ipdService = (IpdService) Context.getService(IpdService.class);
        IpdPatientAdmitted admitted = ipdService.getIpdPatientAdmitted(admittedId);
        
        // Joinig with two tablel
        /*
        IpdPatientAdmissionLog admittedLog=ipdService.getIpdPatientAdmissionLog(admitted.getPatientAdmissionLog().getId());
        Integer lodId=admittedLog.getId();
        model.addAttribute("logId", lodId);
        model.addAttribute("admittedLog", admittedLog);
        */
               
        
        Patient patient = admitted.getPatient();
        model.addAttribute("patientId", patient.getId());

        PersonAddress add = patient.getPersonAddress();
        String address = add.getAddress1();
        // ghansham 25-june-2013 issue no # 1924 Change in the address format
        String district = add.getCountyDistrict();
        String upazila = add.getCityVillage();
        model.addAttribute("address", StringUtils.isNotBlank(address) ? address : "");
        model.addAttribute("district", district);
        model.addAttribute("upazila", upazila);

        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        //ghanshyam 10/07/2012 New Requirement #312 [IPD] Add fields in the Discharge screen and print out
        PersonAttribute relationTypeattr = patient.getAttribute("Relative Name Type");
        model.addAttribute("relationName", relationNameattr.getValue());
        //ghanshyam 30/07/2012 this code modified under feedback of 'New Requirement #312
        if (relationTypeattr != null) {
            model.addAttribute("relationType", relationTypeattr.getValue());
        } else {
            model.addAttribute("relationType", "Relative Name");
        }
        model.addAttribute("dateTime", new Date().toString());

        Concept outComeList = Context.getConceptService().getConceptByName(HospitalCoreConstants.CONCEPT_ADMISSION_OUTCOME);

        model.addAttribute("listOutCome", outComeList.getAnswers());

        model.addAttribute("admitted", admitted);

        //change CHUYEN
        //             
        ConceptService conceptService = Context.getConceptService();
        AdministrationService administrationService = Context.getAdministrationService();
        String gpDiagnosis = administrationService
                .getGlobalProperty(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
        String gpProcedure = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
        String gpInvestigationn = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_FOR_INVESTIGATION);

        List<Obs> obsList = new ArrayList<Obs>(admitted.getPatientAdmissionLog().getIpdEncounter().getAllObs());
        Concept conDiagnosis = conceptService.getConcept(gpDiagnosis);

        Concept conProcedure = conceptService.getConcept(gpProcedure);
        Concept conInvestigation = conceptService.getConcept(gpInvestigationn);
        List<Concept> selectedDiagnosisList = new ArrayList<Concept>();
        List<Concept> selectedProcedureList = new ArrayList<Concept>();
        List<Concept> selectedInvestigationList = new ArrayList<Concept>();
        
        Integer diagnosisValueCoded = null;
        List<String> dia=new ArrayList<String>();
        ArrayList<Integer> valuecodedList = new ArrayList<Integer>();
        if (CollectionUtils.isNotEmpty(obsList)) {
            for (Obs obs : obsList) {
                if(obs.getValueCoded()!=null){
                    String d=obs.getValueCoded().getName().toString();
                    dia.add(d);
                }
                
                if (obs.getValueCoded() != null) {
                        String dName = obs.getValueCoded() + "";
                        diagnosisValueCoded = Integer.parseInt(dName);
                        valuecodedList.add(diagnosisValueCoded);
                    }
                
                
                if (obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId())) {
                    selectedDiagnosisList.add(obs.getValueCoded());
                }
                if (obs.getConcept().getConceptId().equals(conProcedure.getConceptId())) {
                    selectedProcedureList.add(obs.getValueCoded());
                }
                if (obs.getConcept().getConceptId().equals(conInvestigation.getConceptId())) {
                    selectedInvestigationList.add(obs.getValueCoded());
                }
            }
        }

        //
        PatientDashboardService dashboardService = Context.getService(PatientDashboardService.class);
        List<Concept> diagnosis = dashboardService.listByDepartmentByWard(admitted.getAdmittedWard().getId(),
                DepartmentConcept.TYPES[0]);
        if (CollectionUtils.isNotEmpty(diagnosis) && CollectionUtils.isNotEmpty(selectedDiagnosisList)) {
            diagnosis.removeAll(selectedDiagnosisList);
        }
        if (CollectionUtils.isNotEmpty(diagnosis)) {
            Collections.sort(diagnosis, new ConceptComparator());
        }
        model.addAttribute("listDiagnosis", diagnosis);
        List<Concept> procedures = dashboardService.listByDepartmentByWard(admitted.getAdmittedWard().getId(),
                DepartmentConcept.TYPES[1]);
        if (CollectionUtils.isNotEmpty(procedures) && CollectionUtils.isNotEmpty(selectedProcedureList)) {
            procedures.removeAll(selectedProcedureList);
        }
        if (CollectionUtils.isNotEmpty(procedures)) {
            Collections.sort(procedures, new ConceptComparator());
        }
        model.addAttribute("listProcedures", procedures);

        List<Concept> investigation = dashboardService.listByDepartmentByWard(admitted.getAdmittedWard().getId(),
                DepartmentConcept.TYPES[2]);
        if (CollectionUtils.isNotEmpty(investigation) && CollectionUtils.isNotEmpty(selectedInvestigationList)) {
            investigation.removeAll(selectedInvestigationList);
        }
        if (CollectionUtils.isNotEmpty(investigation)) {
            Collections.sort(investigation, new ConceptComparator());
        }
        model.addAttribute("listInvestigations", investigation);

        Collections.sort(selectedDiagnosisList, new ConceptComparator());
        Collections.sort(selectedProcedureList, new ConceptComparator());
        Collections.sort(selectedInvestigationList, new ConceptComparator());
        //Patient category
        model.addAttribute("patCategory", PatientUtils.getPatientCategory(patient));
        model.addAttribute("sDiagnosisList", selectedDiagnosisList);
         model.addAttribute("dia", dia);
        model.addAttribute("sProcedureList", selectedProcedureList);
        model.addAttribute("sInvestigationList", selectedInvestigationList);
        
        return "module/ipd/dischargeForm";
    }

}
