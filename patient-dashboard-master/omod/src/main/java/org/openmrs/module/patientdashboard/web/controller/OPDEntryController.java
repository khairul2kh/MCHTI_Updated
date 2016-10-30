package org.openmrs.module.patientdashboard.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptName;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonName;
import org.openmrs.User;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.InventoryCommonService;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.model.OpddrugorderNew;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.util.ConceptComparator;
import org.openmrs.module.hospitalcore.util.PatientDashboardConstants;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("OPDEntryController")
@RequestMapping({"/module/patientdashboard/opdEntry.htm"})
public class OPDEntryController {

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String firstView(@ModelAttribute("opdCommand") OPDEntryCommand command,
            @RequestParam("patientId") Integer patientId,
            @RequestParam("opdId") Integer opdId,
            @RequestParam(value = "queueId", required = false) Integer queueId,
            @RequestParam("referralId") Integer referralId, Model model) {
        Concept opdWardConcept = Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_OPDWARD));
        model.addAttribute("listInternalReferral", opdWardConcept != null ? new ArrayList(opdWardConcept.getAnswers()) : null);
        Concept hospitalConcept = Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_HOSPITAL));
        model.addAttribute("listExternalReferral", hospitalConcept != null ? new ArrayList(hospitalConcept.getAnswers()) : null);
        model.addAttribute("patientId", patientId);
        IpdService ipds = (IpdService) Context.getService(IpdService.class);
        model.addAttribute("queueId", queueId);
        model.addAttribute("admitted", ipds.getAdmittedByPatientId(patientId));
        
         // Ipd Queue maintain
       //  model.addAttribute("admission", ipds.getAdmissionByPatientId(patientId));
      
        Concept ipdConcept = Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_IPDWARD));
        model.addAttribute("listIpd", ipdConcept != null ? new ArrayList(ipdConcept.getAnswers()) : null);

        /*Sagar Bele 08-08-2012 Support #327 [IPD] (DDU(SDMX)instance) snapshot- age column in IPD admitted patient index */
        PatientDashboardService patientDashboardService = (PatientDashboardService) Context.getService(PatientDashboardService.class);
        InventoryCommonService inventoryCommonService = (InventoryCommonService) Context.getService(InventoryCommonService.class);
        Concept opdConcept = Context.getConceptService().getConcept(opdId);

        List<Concept> diagnosisList = patientDashboardService.listByDepartmentByWard(opdId, Integer.valueOf(org.openmrs.module.hospitalcore.model.DepartmentConcept.TYPES[0]));
        if (CollectionUtils.isNotEmpty(diagnosisList)) {
            Collections.sort(diagnosisList, new ConceptComparator());
        }
        model.addAttribute("diagnosisList", diagnosisList);

        List<Concept> procedures = patientDashboardService.listByDepartmentByWard(opdId, Integer.valueOf(org.openmrs.module.hospitalcore.model.DepartmentConcept.TYPES[1]));
        if (CollectionUtils.isNotEmpty(procedures)) {
            Collections.sort(procedures, new ConceptComparator());
        }
        model.addAttribute("listProcedures", procedures);

        List<Concept> investigations = patientDashboardService.listByDepartmentByWard(opdId, Integer.valueOf(org.openmrs.module.hospitalcore.model.DepartmentConcept.TYPES[2]));
        if (CollectionUtils.isNotEmpty(investigations)) {
            Collections.sort(investigations, new ConceptComparator());
        }

        model.addAttribute("listInvestigations", investigations);

        List<Concept> drugFrequencyConcept = inventoryCommonService.getDrugFrequency();
        model.addAttribute("drugFrequencyList", drugFrequencyConcept);

        model.addAttribute("opd", opdConcept);
        model.addAttribute("referral", Context.getConceptService().getConcept(referralId));
        model.addAttribute("opd1", Context.getConceptService().getConcept(opdId));
        
        /// Add New Code Here 
        
        PatientDashboardService dashboardService = Context.getService(PatientDashboardService.class);
        String orderLocationId = "1";
        Location location = Context.getLocationService().getLocation(Integer.parseInt(orderLocationId));

        Patient patient = Context.getPatientService().getPatient(patientId);

        String gpOPDEncType = Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_OPD_ENCOUTNER_TYPE);
        EncounterType labOPDType = Context.getEncounterService().getEncounterType(gpOPDEncType);

        ConceptService conceptService = Context.getConceptService();
        AdministrationService administrationService = Context.getAdministrationService();
        String gpDiagnosis = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);

        String gpProcedure = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
        //	Sagar Bele Date:29-12-2012 Add field of visit outcome for Bangladesh requirement #552
        String gpVisitOutcome = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_VISIT_OUTCOME);
        String gpInvestigation = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_FOR_INVESTIGATION);//new
        //String gpInternalReferral = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_INTERNAL_REFERRAL);

        Concept conDiagnosis = conceptService.getConcept(gpDiagnosis);
        Concept conInvestigation = conceptService.getConcept(gpInvestigation);//new
        Concept conProcedure = conceptService.getConcept(gpProcedure);
//		Sagar Bele Date:29-12-2012 Add field of visit outcome for Bangladesh requirement #552
       // Concept conOutcome = conceptService.getConcept(gpVisitOutcome);

        List<Encounter> encounters = dashboardService.getEncounter(patient, location, labOPDType, null);
        //  condiagnosis
        //
        Integer diagnosisValueCoded = null;
        String diagnosisName = "";
        String procedure = "";
        String investigation = "";

        ArrayList<String> diagnosisNameList = new ArrayList<String>();
        ArrayList<String> procedureNameList = new ArrayList<String>();
        ArrayList<String> investigationNameList = new ArrayList<String>();

        ArrayList<Integer> valuecodedList = new ArrayList<Integer>();
        ArrayList<Integer> procedureValuecodedList = new ArrayList<Integer>();
        ArrayList<Integer> investigationValuecodedList = new ArrayList<Integer>();
        Integer en = null;
        for (Encounter enc : encounters) {

            for (Obs obs : enc.getAllObs()) {
                //diagnosis
                if (obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId())) {
 
                    if (obs.getValueCoded() != null) {
                        diagnosisName = obs.getValueCoded().getName().toString();
                        diagnosisNameList.add(diagnosisName);
                        model.addAttribute("dia", diagnosisNameList);
                    }
                    if (obs.getValueCoded() != null) {
                        String dName = obs.getValueCoded() + "";
                        diagnosisValueCoded = Integer.parseInt(dName);
                        valuecodedList.add(diagnosisValueCoded);
                    }

                }

                //postForProcedure
                if (obs.getConcept().getConceptId().equals(conProcedure.getConceptId())) {
//					obs.getV
                    if (obs.getValueCoded() != null) {
                        procedure = obs.getValueCoded().getName().toString();
                        procedureNameList.add(procedure);
                    }

                    if (obs.getValueCoded() != null) {
                        String pName = obs.getValueCoded() + "";
                        Integer pcoded = Integer.parseInt(pName);
                        procedureValuecodedList.add(pcoded);

                    }

                }

                //Investigation
                if (obs.getConcept().getConceptId().equals(conInvestigation.getConceptId())) {
//					obs.getV
                    if (obs.getValueCoded() != null) {
                        investigation = obs.getValueCoded().getName().toString();
                        investigationNameList.add(investigation);
                    }

                    if (obs.getValueCoded() != null) {
                        String iName = obs.getValueCoded() + "";
                        Integer iCoded = Integer.parseInt(iName);
                        investigationValuecodedList.add(iCoded);
                    }

                }
                en = enc.getEncounterId();//to get last encounter id for retrieving drug
            }

            break;

        };

        //Diagnosis
        
        ArrayList diagnosisListForModel = new ArrayList();
        for (int i = 0; i < diagnosisNameList.size(); i++) {
            HashMap map = new HashMap();
            map.put("name", diagnosisNameList.get(i));
            map.put("id", valuecodedList.get(i));
            diagnosisListForModel.add(map);
        }
        model.addAttribute("diagnosis", diagnosisListForModel);

        // diagnosis end
        //procedure start
        ArrayList procedureListForModel = new ArrayList();
        for (int i = 0; i < procedureNameList.size(); i++) {
            HashMap pmap = new HashMap();
            pmap.put("name", procedureNameList.get(i));
            pmap.put("id", procedureValuecodedList.get(i));
            procedureListForModel.add(pmap);
        }
        model.addAttribute("procedure", procedureListForModel);

        //procedure end
        //investigation start here
        ArrayList investigationListForModel = new ArrayList();
        for (int i = 0; i < investigationValuecodedList.size(); i++) {
            HashMap imap = new HashMap();
            imap.put("name", investigationNameList.get(i));
            imap.put("id", investigationValuecodedList.get(i));
            investigationListForModel.add(imap);

        }
        model.addAttribute("investigation", investigationListForModel);
         
        /// End herer
        
        
        return "module/patientdashboard/opdEntry";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String formSummit(OPDEntryCommand command, HttpServletRequest request,
            @RequestParam("patientId") Integer patientId,
            @RequestParam(value = "drugOrder", required = false) String[] drugOrder)
            throws Exception {
        User user = Context.getAuthenticatedUser();
        PatientService ps = Context.getPatientService();
        Patient patient = ps.getPatient(command.getPatientId());

        List<String> diagnosisList = new ArrayList();
        List<String> investigationList = new ArrayList();
        List<String> procedureList = new ArrayList();
        List<String> drugList = new ArrayList();
        String doctor = user.getPersonName().getFullName();
        if (StringUtils.equalsIgnoreCase(command.getRadio_f(), "died")) {
            patient.setDead(Boolean.valueOf(true));
            patient.setDeathDate(new Date());

            ps.savePatient(patient);
        }
        Date date = new Date();

        Obs obsGroup = null;
        HospitalCoreService hcs = (HospitalCoreService) Context.getService(HospitalCoreService.class);
        obsGroup = hcs.getObsGroupCurrentDate(patient.getPersonId());
        if (((StringUtils.equalsIgnoreCase(command.getRadio_f(), "admit")) || ((command.getInternalReferral() != null) && (command.getInternalReferral().intValue() > 0)))
                && (obsGroup == null)) {
            obsGroup = hcs.createObsGroup(patient, "hospitalcore.obsGroup");
        }
        AdministrationService administrationService = Context.getAdministrationService();

        GlobalProperty gpOPDEncounterType = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_OPD_ENCOUTNER_TYPE);
        EncounterType encounterType = Context.getEncounterService().getEncounterType(gpOPDEncounterType.getPropertyValue());
        Encounter encounter = new Encounter();
        Location location = new Location(Integer.valueOf(1));
        encounter.setPatient(patient);
        encounter.setCreator(user);
        encounter.setProvider(user);
        encounter.setEncounterDatetime(date);
        encounter.setEncounterType(encounterType);
        encounter.setLocation(location);

        ConceptService conceptService = Context.getConceptService();
        GlobalProperty gpDiagnosis = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
        GlobalProperty procedure = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);

        GlobalProperty investigationn = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_FOR_INVESTIGATION);
        GlobalProperty internalReferral = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_INTERNAL_REFERRAL);
        GlobalProperty externalReferral = administrationService.getGlobalPropertyObject(PatientDashboardConstants.PROPERTY_EXTERNAL_REFERRAL);

        Concept cDiagnosis = conceptService.getConceptByName(gpDiagnosis.getPropertyValue());

        Concept cOtherInstructions = conceptService.getConceptByName("OTHER INSTRUCTIONS");
        Concept cChiefComplain = conceptService.getConceptByName("CHIEF COMPLAIN");
        Concept ipdd = conceptService.getConceptByName("IPD WARD");
     //   Concept ac = conceptService.getConceptByName("ADMISSION CHARGES");

        if (cDiagnosis == null) {
            throw new Exception("Diagnosis concept null");
        }
        for (Integer cId : command.getSelectedDiagnosisList()) {
            Obs obsDiagnosis = new Obs();
            obsDiagnosis.setObsGroup(obsGroup);
            obsDiagnosis.setConcept(cDiagnosis);
            obsDiagnosis.setValueCoded(conceptService.getConcept(cId));
            obsDiagnosis.setCreator(user);
            obsDiagnosis.setDateCreated(date);
            obsDiagnosis.setEncounter(encounter);
            obsDiagnosis.setPatient(patient);
            encounter.addObs(obsDiagnosis);
            diagnosisList.add(obsDiagnosis.getValueCoded().getName().toString());
        }
        if (StringUtils.isNotBlank(command.getNote())) {
            Obs obsDiagnosis = new Obs();
            obsDiagnosis.setObsGroup(obsGroup);

            obsDiagnosis.setConcept(cOtherInstructions);
            obsDiagnosis.setValueText(command.getNote());
            obsDiagnosis.setCreator(user);
            obsDiagnosis.setDateCreated(date);
            obsDiagnosis.setEncounter(encounter);
            obsDiagnosis.setPatient(patient);
            encounter.addObs(obsDiagnosis);
        }

        if (StringUtils.isNotBlank(command.getCcomplain())) {
            Obs obsDiagnosis = new Obs();
            obsDiagnosis.setObsGroup(obsGroup);

            obsDiagnosis.setConcept(cChiefComplain);
            obsDiagnosis.setValueText(command.getCcomplain());
            obsDiagnosis.setCreator(user);
            obsDiagnosis.setDateCreated(date);
            obsDiagnosis.setEncounter(encounter);
            obsDiagnosis.setPatient(patient);
            encounter.addObs(obsDiagnosis);
        }
        if (!ArrayUtils.isEmpty(command.getSelectedProcedureList())) {
            Concept pDiagnosis = conceptService.getConceptByName(procedure.getPropertyValue());
            if (pDiagnosis == null) {
                throw new Exception("Post for procedure concept null");
            }
            for (Integer pId : command.getSelectedProcedureList()) {
                Obs obsDiagnosis = new Obs();
                obsDiagnosis.setObsGroup(obsGroup);
                obsDiagnosis.setConcept(pDiagnosis);
                obsDiagnosis.setValueCoded(conceptService.getConcept(pId));
                obsDiagnosis.setCreator(user);
                obsDiagnosis.setDateCreated(date);
                obsDiagnosis.setEncounter(encounter);
                obsDiagnosis.setPatient(patient);
                encounter.addObs(obsDiagnosis);
                procedureList.add(obsDiagnosis.getValueCoded().getName().toString());
            }
        }
        if (!ArrayUtils.isEmpty(command.getSelectedInvestigationList())) {
            Concept coninvt = conceptService.getConceptByName(investigationn.getPropertyValue());
            if (coninvt == null) {
                throw new Exception("Investigation concept null");
            }
            for (Integer pId : command.getSelectedInvestigationList()) {
                Obs obsInvestigation = new Obs();
                obsInvestigation.setObsGroup(obsGroup);
                obsInvestigation.setConcept(coninvt);
                obsInvestigation.setValueCoded(conceptService.getConcept(pId));
                obsInvestigation.setCreator(user);
                obsInvestigation.setDateCreated(date);
                obsInvestigation.setEncounter(encounter);
                obsInvestigation.setPatient(patient);
                encounter.addObs(obsInvestigation);
                investigationList.add(obsInvestigation.getValueCoded().getName().toString());
            }
        }
        if ((command.getInternalReferral() != null) && (command.getInternalReferral().intValue() > 0)) {
            Concept cInternalReferral = conceptService.getConceptByName(internalReferral.getPropertyValue());
            if (cInternalReferral == null) {
                throw new Exception("InternalReferral concept null");
            }
            Concept internalReferralConcept = conceptService.getConcept(command.getInternalReferral());
            Obs obsInternalReferral = new Obs();
            obsInternalReferral.setObsGroup(obsGroup);
            obsInternalReferral.setConcept(cInternalReferral);
            obsInternalReferral.setValueCoded(internalReferralConcept);
            obsInternalReferral.setCreator(user);
            obsInternalReferral.setDateCreated(date);
            obsInternalReferral.setEncounter(encounter);
            obsInternalReferral.setPatient(patient);
            encounter.addObs(obsInternalReferral);

            Concept currentOpd = conceptService.getConcept(command.getOpdId());

            OpdPatientQueue queue = new OpdPatientQueue();
            queue.setPatient(patient);
            queue.setCreatedOn(date);
            queue.setBirthDate(patient.getBirthdate());
            queue.setPatientIdentifier(patient.getPatientIdentifier().getIdentifier());
            queue.setOpdConcept(internalReferralConcept);
            queue.setOpdConceptName(internalReferralConcept.getName().getName());
            queue.setPatientName(patient.getGivenName() + " " + patient.getMiddleName() + " " + patient.getFamilyName());
            queue.setReferralConcept(currentOpd);
            queue.setReferralConceptName(currentOpd.getName().getName());
            queue.setSex(patient.getGender());
            PatientQueueService queueService = (PatientQueueService) Context.getService(PatientQueueService.class);
            queueService.saveOpdPatientQueue(queue);
        }
        if ((command.getExternalReferral() != null) && (command.getExternalReferral().intValue() > 0)) {
            Concept cExternalReferral = conceptService.getConceptByName(externalReferral.getPropertyValue());
            if (cExternalReferral == null) {
                throw new Exception("ExternalReferral concept null");
            }
            Obs obsExternalReferral = new Obs();
            obsExternalReferral.setObsGroup(obsGroup);
            obsExternalReferral.setConcept(cExternalReferral);
            obsExternalReferral.setValueCoded(conceptService.getConcept(command.getExternalReferral()));
            obsExternalReferral.setCreator(user);
            obsExternalReferral.setDateCreated(date);
            obsExternalReferral.setEncounter(encounter);
            obsExternalReferral.setPatient(patient);
            encounter.addObs(obsExternalReferral);
        }
        Concept cOutcome = conceptService.getConceptByName(administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_VISIT_OUTCOME));
        if (cOutcome == null) {
            throw new Exception("Visit Outcome concept =  null");
        }
        Obs obsOutcome = new Obs();
        obsOutcome.setObsGroup(obsGroup);
        obsOutcome.setConcept(cOutcome);
        try {
            obsOutcome.setValueText(command.getRadio_f());
            if (StringUtils.equalsIgnoreCase(command.getRadio_f(), "follow")) {
                obsOutcome.setValueDatetime(Context.getDateFormat().parse(command.getDateFollowUp()));
            }

            /* If admit select automaticaly get ipd ward name, It's remove for NIKDU requirements */
           // if (StringUtils.equalsIgnoreCase(command.getRadio_f(), "admit")) {
            //     obsOutcome.setValueCoded(conceptService.getConcept(command.getIpdWard()));
            // }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        obsOutcome.setCreator(user);
        obsOutcome.setDateCreated(date);
        obsOutcome.setPatient(patient);
        obsOutcome.setEncounter(encounter);
        encounter.addObs(obsOutcome);
        Context.getEncounterService().saveEncounter(encounter);

        PatientQueueService queueService = (PatientQueueService) Context.getService(PatientQueueService.class);
        OpdPatientQueue queue = queueService.getOpdPatientQueueById(command.getQueueId());
        OpdPatientQueueLog queueLog = new OpdPatientQueueLog();
        queueLog.setOpdConcept(queue.getOpdConcept());
        queueLog.setOpdConceptName(queue.getOpdConceptName());
        queueLog.setPatient(queue.getPatient());
        queueLog.setCreatedOn(queue.getCreatedOn());
        queueLog.setPatientIdentifier(queue.getPatientIdentifier());
        queueLog.setPatientName(queue.getPatientName());
        queueLog.setReferralConcept(queue.getReferralConcept());
        queueLog.setReferralConceptName(queue.getReferralConceptName());
        queueLog.setSex(queue.getSex());
        queueLog.setUser(Context.getAuthenticatedUser());
        queueLog.setStatus("processed");
        queueLog.setBirthDate(patient.getBirthdate());
        queueLog.setEncounter(encounter);
        OpdPatientQueueLog opdPatientLog = queueService.saveOpdPatientQueueLog(queueLog);
        queueService.deleteOpdPatientQueue(queue);

        // IPD Admission Queue   (it's add to 6B 68 61)
        String adStatus = null;
        adStatus = "Wating";

        if (StringUtils.equalsIgnoreCase(command.getRadio_f(), "admit")) {
            IpdService ipdService = (IpdService) Context.getService(IpdService.class);
            IpdPatientAdmission patientAdmission = new IpdPatientAdmission();
            patientAdmission.setAdmissionDate(date);
            //  patientAdmission.setAdmissionWard(conceptService.getConcept(command.getIpdWard()));  // It's request for IPD Admission only no need ward select (6B 68 61)
            patientAdmission.setBirthDate(patient.getBirthdate());
            patientAdmission.setGender(patient.getGender());
            patientAdmission.setOpdAmittedUser(user);
            patientAdmission.setOpdLog(opdPatientLog);
            patientAdmission.setPatient(patient);
            patientAdmission.setStatus(adStatus);
            patientAdmission.setPatientIdentifier(patient.getPatientIdentifier().getIdentifier());
            patientAdmission.setPatientName(patient.getGivenName() + " " + patient.getMiddleName() + " " + patient.getFamilyName());
            patientAdmission = ipdService.saveIpdPatientAdmission(patientAdmission);
        }

        PatientDashboardService patientDashboardService = (PatientDashboardService) Context.getService(PatientDashboardService.class);
        if (!ArrayUtils.isEmpty(command.getSelectedProcedureList())) {
            Concept conpro = conceptService.getConceptByName(procedure.getPropertyValue());
            if (conpro == null) {
                throw new Exception("Post for procedure concept null");
            }
            for (Integer pId : command.getSelectedProcedureList()) {
                BillingService billingService = (BillingService) Context.getService(BillingService.class);
                BillableService billableService = billingService.getServiceByConceptId(pId);
                OpdTestOrder opdTestOrder = new OpdTestOrder();
                opdTestOrder.setPatient(patient);
                opdTestOrder.setEncounter(encounter);
                opdTestOrder.setConcept(conpro);
                opdTestOrder.setTypeConcept(Integer.valueOf(org.openmrs.module.hospitalcore.model.DepartmentConcept.TYPES[1]));
                opdTestOrder.setValueCoded(conceptService.getConcept(pId));
                opdTestOrder.setCreator(user);
                opdTestOrder.setCreatedOn(date);
                opdTestOrder.setBillableService(billableService);
                patientDashboardService.saveOrUpdateOpdOrder(opdTestOrder);
            }
        }
        if (!ArrayUtils.isEmpty(command.getSelectedInvestigationList())) {
            Concept coninvt = conceptService.getConceptByName(investigationn.getPropertyValue());
            if (coninvt == null) {
                throw new Exception("Investigation concept null");
            }
            for (Integer iId : command.getSelectedInvestigationList()) {
                BillingService billingService = (BillingService) Context.getService(BillingService.class);
                BillableService billableService = billingService.getServiceByConceptId(iId);
                OpdTestOrder opdTestOrder = new OpdTestOrder();
                opdTestOrder.setPatient(patient);
                opdTestOrder.setEncounter(encounter);
                opdTestOrder.setConcept(coninvt);
                opdTestOrder.setTypeConcept(Integer.valueOf(org.openmrs.module.hospitalcore.model.DepartmentConcept.TYPES[2]));
                opdTestOrder.setValueCoded(conceptService.getConcept(iId));
                opdTestOrder.setCreator(user);
                opdTestOrder.setCreatedOn(date);
                opdTestOrder.setBillableService(billableService);
                patientDashboardService.saveOrUpdateOpdOrder(opdTestOrder);
            }
        }

       // 6B 68 61 It's changed by
        /// New Code //// Enter for billing queue maintain for admission request*** Adding this cTech Md. Khairul Islam
           /*  
         if (StringUtils.equalsIgnoreCase(command.getRadio_f(), "admit")) {
         Concept ip=conceptService.getConceptByName(ipdd.getDisplayString());
         if (ip == null) {
         throw new Exception("Ipd concept null");
         }
           
                  
         String type = null;
         type ="5";
            
         String bStatus=null;
         bStatus="0K";
         BillingService billingService = (BillingService) Context.getService(BillingService.class);
         //BillableService billableService = billingService.getServiceByConceptId(command.getIpdWard());
         BillableService billableService = billingService.getServiceByConceptName("ADMISSION CHARGES");
                
         OpdTestOrder opdTestOrder = new OpdTestOrder();
         opdTestOrder.setPatient(patient);
         opdTestOrder.setEncounter(encounter);
         opdTestOrder.setConcept(ip);
         opdTestOrder.setTypeConcept(Integer.valueOf(type));
         //opdTestOrder.setTypeConcept(Integer.valueOf(org.openmrs.module.hospitalcore.model.DepartmentConcept.TYPES[2]));  // this for investigation default order type
         //opdTestOrder.setValueCoded(conceptService.getConcept(command.getIpdWard()));
         opdTestOrder.setValueCoded(conceptService.getConceptByName("ADMISSION CHARGES"));
         opdTestOrder.setCreator(user);
         opdTestOrder.setCreatedOn(date);
         //  opdTestOrder.setBillingStatus(Integer.valueOf(bStatus));
         opdTestOrder.setBillableService(billableService);
         opdTestOrder.setOpdLog(opdPatientLog);
         patientDashboardService.saveOrUpdateOpdOrder(opdTestOrder);
            
         }  
                   */
        //// End Here billing queue
        if (drugOrder != null) {
            for (String drugName : drugOrder) {
                InventoryCommonService inventoryCommonService = (InventoryCommonService) Context.getService(InventoryCommonService.class);
                InventoryDrug inventoryDrug = inventoryCommonService.getDrugByName(drugName);
                if (inventoryDrug != null) {
                    Integer formulationId = Integer.valueOf(Integer.parseInt(request.getParameter(drugName + "_formulationId")));

                    Integer frequencyId = Integer.valueOf(Integer.parseInt(request.getParameter(drugName + "_frequencyId")));

                    Integer noOfDays = Integer.valueOf(Integer.parseInt(request.getParameter(drugName + "_noOfDays")));

                    String comments = request.getParameter(drugName + "_comments");
                    InventoryDrugFormulation inventoryDrugFormulation = inventoryCommonService.getDrugFormulationById(formulationId);
                    Concept freCon = conceptService.getConcept(frequencyId);

                    OpdDrugOrder opdDrugOrder = new OpdDrugOrder();
                    opdDrugOrder.setPatient(patient);
                    opdDrugOrder.setEncounter(encounter);
                    opdDrugOrder.setInventoryDrug(inventoryDrug);
                    opdDrugOrder.setInventoryDrugFormulation(inventoryDrugFormulation);
                    opdDrugOrder.setFrequency(freCon);
                    opdDrugOrder.setNoOfDays(noOfDays);
                    opdDrugOrder.setComments(comments);
                    opdDrugOrder.setCreator(user);
                    opdDrugOrder.setCreatedOn(date);
                    patientDashboardService.saveOrUpdateOpdDrugOrder(opdDrugOrder);
                    drugList.add(drugName + " :  " + inventoryDrugFormulation.getDozage() + " - " + freCon.getName().getName() + " = " + noOfDays.toString() + " Days " + " - " + comments);
                }
            }
        }
        
        String drug = "";
         /*
        // Single Drug Insert into table (new)
        if (drugOrder != null) {
            for (String drugName : drugOrder) {
                InventoryCommonService inventoryCommonService = (InventoryCommonService) Context.getService(InventoryCommonService.class);
                InventoryDrug inventoryDrug = inventoryCommonService.getDrugByName(drugName);

                if (inventoryDrug != null) {
                    Integer formulationId = Integer.valueOf(Integer.parseInt(request.getParameter(drugName + "_formulationId")));

                    Integer frequencyId = Integer.valueOf(Integer.parseInt(request.getParameter(drugName + "_frequencyId")));

                    Integer noOfDays = Integer.valueOf(Integer.parseInt(request.getParameter(drugName + "_noOfDays")));

                  //  String comments = request.getParameter(drugName + "_comments");
                    //  InventoryDrugFormulation inventoryDrugFormulation = inventoryCommonService.getDrugFormulationById(formulationId);
                    //  Concept freCon = conceptService.getConcept(frequencyId);
                    // pacs.setFullname(patient.getGivenName()+" "+patient.getMiddleName()+" "+patient.getFamilyName());
                    String formulation = inventoryCommonService.getDrugFormulationById(formulationId).getDozage().toString();
                    String formulationName = inventoryCommonService.getDrugFormulationById(formulationId).getName().toString();
                    String frequency = conceptService.getConcept(frequencyId).getName().getName().toString();

                    OpddrugorderNew drg = new OpddrugorderNew();

                    drg.setPatientId(patientId);
                    //drg.setDrugName(patient.getPatientIdentifier().getIdentifier().toString());

                    drg.setDrugName(drugName);
                    drg.setDrugId(inventoryDrug.getId());
                    drg.setFrequency(frequency);
                    drg.setFormulation(formulationName + " " + formulation);
                    drg.setNoOfDays(noOfDays);
                    drg.setCreatedDate(date);
                    drg.setCreator(user);
                    drg.setEncounter(encounter);

                    patientDashboardService.saveOpddrugorderNew(drg);
                }
                
            }
        }
*/
        PersonAttribute relationNameattr = patient.getAttribute("Father/Husband Name");
        //       model.addAttribute("relationName", relationNameattr.getValue());             
        String relationName = relationNameattr.getValue();

        PersonAttribute relationTypeattr = patient.getAttribute("Relative Name Type");
        String relationType = relationTypeattr.getValue();

        String opd1 = queue.getOpdConceptName();

        if ("Urology OPD (White-118)".equals(opd1)) {
            opd1 = "White, (Saturday and Tuesday)";
        } else if ("Urology OPD (Blue-117)".equals(opd1)) {
            opd1 = "Blue, (Sunday and Wednesday)";
        } else if ("Urology OPD (Orange-117)".equals(opd1)) {
            opd1 = "Orange, (Monday and Thursday)";
        } else if ("Urology OPD (Yellow-117)".equals(opd1)) {
            opd1 = "Yellow, (Saturday and Tuesday)";
        } else if ("Urology OPD (Red-118)".equals(opd1)) {
            opd1 = "Yellow, (Sunday and Thursday)";
        } else if ("Urology OPD (Green-118)".equals(opd1)) {
            opd1 = "Green, (Monday and Wednesday)";
        } else {
            opd1 = queue.getOpdConceptName();

        }

        IpdService ipdService = Context.getService(IpdService.class);

        String ipdbed = "";

        //HospitalCoreService hospitalCore = Context.getService(HospitalCoreService.class);
        List<IpdPatientAdmissionLog> currentAdmissionList = ipdService.listIpdPatientAdmissionLog(patientId, null, IpdPatientAdmitted.STATUS_ADMITTED, 0, 1);
        IpdPatientAdmissionLog currentAdmission = CollectionUtils.isEmpty(currentAdmissionList) ? null : currentAdmissionList.get(0);
        if (currentAdmission != null) {
            IpdPatientAdmitted admitted = ipdService.getAdmittedByPatientId(patientId);
            ipdbed = admitted.getBed();
        }

        String ipd = "";
        //HospitalCoreService hospitalCore = Context.getService(HospitalCoreService.class);
        if (currentAdmission != null) {
            IpdPatientAdmitted admitted = ipdService.getAdmittedByPatientId(patientId);
            ipd = admitted.getAdmittedWard().getName().toString();

        }

      //  ConceptName ip=admitted.getAdmittedWard().getName();
        String d = StringUtils.join(diagnosisList, ',');
        String p = StringUtils.join(procedureList, ',');
        String i = StringUtils.join(investigationList, ',');
        String dr = StringUtils.join(drugList, ',');

        String internal = "";
        Concept conInternal = Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_INTERNAL_REFERRAL));
        for (Obs obs : encounter.getAllObs()) {
            if (obs.getConcept().getConceptId().equals(conInternal.getConceptId())) {
                internal = obs.getValueCoded().getName() + "";
            }
        }
        // internal = obs.getValueCoded().getName()+"";
        //  if("internalReferral".equals(internal)){internal=conceptService.getConcept(command.getInternalReferral()).getDisplayString();}
        //  else if("internalReferral".equals("")){internal="";}
        String outcome = command.getRadio_f();
        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        if ("follow".equals(outcome)) {
            Date sdate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(command.getDateFollowUp());
            outcome = "Follow Up: " + f.format(sdate);
        } else if ("cured".equals(outcome)) {
            outcome = "Cured";
        } else if ("died".equals(outcome)) {
            outcome = "Died";
        } else if ("reviewed".equals(outcome)) {
            outcome = "Reviewed";
        }
        //else if ("admit".equals(outcome)) {
        //     outcome = "Admit to " + conceptService.getConcept(command.getIpdWard()).getDisplayString();
        //  }  // for nikdu requirements it's hide
        Calendar cal = Calendar.getInstance();
        return "redirect:/module/patientdashboard/opdEntryView.htm?pid=" + patient.getPatientIdentifier().getIdentifier().toString() + "&pname=" + patient.getGivenName() + "&pnamemiddle=" + patient.getMiddleName() + "&pnamefamily=" + patient.getFamilyName() + "&age=" + PatientUtils.estimateAge(patient.getBirthdate()) + "&diagnosis=" + d + "&investigations=" + i + "&procedures=" + p + "&drugs=" + dr + "&advice=" + command.getNote() + "&ccomplain=" + command.getCcomplain() + "&ipd=" + ipd + "&ipdbed=" + ipdbed + "&internal=" + internal + "&outcome=" + outcome + "&opd=" + opd1 + "&relationName=" + relationName + "&relationType=" + relationType + "&doctor=" + doctor + "&odate=" + f.format(cal.getTime());
//       return "redirect:/module/patientdashboard/opdEntryView.htm?pid=" + patient.getPatientIdentifier().getIdentifier().toString() + "&pname=" + patient.getGivenName() + "&pnamemiddle=" + patient.getMiddleName() + "&pnamefamily=" + patient.getFamilyName() + "&age=" + PatientUtils.estimateAge(patient.getBirthdate()) + "&diagnosis=" + d + "&investigations=" + i + "&procedures=" + p + "&drugs=" + dr + "&advice=" + command.getNote() + "&ccomplain=" + command.getCcomplain() + "&ipd=" + ipd + "&ipdbed=" + ipdbed + "&internal=" + internal + "&outcome=" + outcome + "&opd=" + opd1 +"&relationName="+ relationName +"&relationType="+ relationType + "&doctor=" + doctor + "&odate=" + f.format(cal.getTime());   // Ipw ward 

    }
}
