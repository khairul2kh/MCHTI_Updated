/**
 * Copyright 2010 Society for Health Information Systems Programmes, India (HISP
 * India)
 *
 * This file is part of Patient-dashboard module.
 *
 * Patient-dashboard module is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Patient-dashboard module is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Patient-dashboard module. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.openmrs.module.patientdashboard.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.util.PatientDashboardConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("ClinicalSummaryController")
@RequestMapping("/module/patientdashboard/clinicalSummary.htm")
public class ClinicalSummaryController {

    @RequestMapping(method = RequestMethod.GET)
    public String firstView(@RequestParam("patientId") Integer patientId, Model model) {

        PatientDashboardService dashboardService = Context.getService(PatientDashboardService.class);
        String orderLocationId = "1";
        Location location = Context.getLocationService().getLocation(Integer.parseInt(orderLocationId));

        Patient patient = Context.getPatientService().getPatient(patientId);

        String gpOPDEncType = Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_OPD_ENCOUTNER_TYPE);
        EncounterType labOPDType = Context.getEncounterService().getEncounterType(gpOPDEncType);

        ConceptService conceptService = Context.getConceptService();
        AdministrationService administrationService = Context.getAdministrationService();
        String gpDiagnosis = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
        
        // for Investigation
        
        String gpInvestigation = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_FOR_INVESTIGATION);

        String gpProcedure = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
        //	Sagar Bele Date:29-12-2012 Add field of visit outcome for Bangladesh requirement #552
        String gpVisitOutcome = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_VISIT_OUTCOME);

//        String gpChiefComplain = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_CHIEF_COMPLAIN);
		//String gpInternalReferral = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_INTERNAL_REFERRAL);
        Concept conDiagnosis = conceptService.getConcept(gpDiagnosis);
        
        Concept conInvestigation=conceptService.getConcept(gpInvestigation);

        Concept conProcedure = conceptService.getConcept(gpProcedure);
//		Sagar Bele Date:29-12-2012 Add field of visit outcome for Bangladesh requirement #552
        Concept conOutcome = conceptService.getConcept(gpVisitOutcome);
//		Concept conChiefComplain = conceptService.getConceptByName(gpChiefComplain);

        List<Encounter> encounters = dashboardService.getEncounter(patient, location, labOPDType, null);

	 // This add for clinicla summary drug showing 
        IpdService ipdService = Context.getService(IpdService.class);
        List<OpdDrugOrder> listOpdDrug = ipdService.getOpdDrugOrderByPatientEncounterId(patient.getPatientId());
         

        String diagnosis = "";
        String investigation="";
        String procedure = "";
        String outcome = "";
        String chiefComplain = "";
        String oDrug="";
        List<Clinical> clinicalSummaries = new ArrayList<Clinical>();
        for (Encounter enc : encounters) {
            diagnosis = "";
            investigation="";
            procedure = "";
            outcome = "";
            chiefComplain = "";
            oDrug="";
            Clinical clinical = new Clinical();
            
          //// // This add for clinicla summary drug showing 
             for (OpdDrugOrder opd : listOpdDrug) {
                 
                if(opd.getEncounter().getId().equals(enc.getId())){
            oDrug += "&#9764;  " + opd.getInventoryDrug().getName() + " - "
                    + opd.getInventoryDrugFormulation().getName() + " "
                    + opd.getInventoryDrugFormulation().getDozage() + " - "
                    + opd.getFrequency().getDisplayString().toString() + " - "
                    + opd.getNoOfDays().toString() + "&#2495;&#2470;&#2472;" + "<br/>";
        }}
            
         // End drug showing
             
             
            for (Obs obs : enc.getAllObs()) {
                //diagnosis
                if (obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId())) {

                    if (obs.getValueCoded() != null) {
                        diagnosis += obs.getValueCoded().getName() + ", ";
                    }
                    if (StringUtils.isNotBlank(obs.getValueText())) {
                        diagnosis += obs.getValueText() + ", ";
                    }
                   
                }
                // investigation
                if (obs.getConcept().getConceptId().equals(conInvestigation.getConceptId())) {

                    if (obs.getValueCoded() != null) {
                        investigation += obs.getValueCoded().getName() + ", ";
                    }
                    if (StringUtils.isNotBlank(obs.getValueText())) {
                        investigation += obs.getValueText() + ", ";
                    }
                   
                }
                     // procedure
                if (obs.getConcept().getConceptId().equals(conProcedure.getConceptId())) {

                    if (obs.getValueCoded() != null) {
                        procedure += obs.getValueCoded().getName() + ", ";
                    }
                    if (StringUtils.isNotBlank(obs.getValueText())) {
                        procedure += obs.getValueText() + ", ";
                    }
                }
//				Sagar Bele Date:29-12-2012 Add field of visit outcome for Bangladesh requirement #552
                //visit outcome
                if (obs.getConcept().getConceptId().equals(conOutcome.getConceptId())) {
//					obs.getV
                    if (obs.getValueCoded() != null) {
                        outcome += obs.getValueCoded().getName() + ", ";
                    }
                    if (StringUtils.isNotBlank(obs.getValueText())) {
                        outcome += obs.getValueText();
                    }
					//System.out.println(obs.getva);

                }

            }
            // diagnosis += diagnosis; // remove this beause dignosis showing double
            if (StringUtils.endsWith(diagnosis, ", ")) {
                diagnosis = StringUtils.removeEnd(diagnosis, ", ");
            }
            if (StringUtils.endsWith(investigation, ", ")) {
                investigation = StringUtils.removeEnd(investigation, ", ");
            }
            if (StringUtils.endsWith(procedure, ", ")) {
                procedure = StringUtils.removeEnd(procedure, ", ");
            }
//			Sagar Bele Date:29-12-2012 Add field of visit outcome for Bangladesh requirement #552
            if (StringUtils.endsWith(outcome, ", ")) {
                outcome = StringUtils.removeEnd(outcome, ", ");
            }
            


            //// This add for clinicla summary drug showing 
            
            if (StringUtils.endsWith(oDrug, ", ")) {
                oDrug = StringUtils.removeEnd(oDrug, ", ");
            }

			//${patient.givenName}&nbsp;&nbsp;${patient.middleName}&nbsp;&nbsp; ${patient.familyName}
            clinical.setTreatingDoctor(enc.getCreator().getPerson().getGivenName() + " " + enc.getCreator().getPerson().getMiddleName() + " " + enc.getCreator().getPerson().getFamilyName());
            clinical.setDateOfVisit(enc.getDateCreated());
            clinical.setId(enc.getId());
            clinical.setCcomplain(chiefComplain);
            clinical.setDiagnosis(diagnosis);
            clinical.setInvestigation(investigation);
            clinical.setProcedures(procedure);
            clinical.setVisitOutcomes(outcome);
            clinical.setPatientName(patient.getPatientIdentifier().getIdentifier());
            clinical.setDrug(oDrug); // showing drug
            clinicalSummaries.add(clinical);
            

			// set value to command object  
            // add command to list  
        };
        //System.out.println("clinicalSummaries: "+clinicalSummaries);
        model.addAttribute("clinicalSummaries", clinicalSummaries);
        return "module/patientdashboard/clinicalSummary";
    }

    public static void main(String[] args) {
        String a = "asfsf, dsf, ";
        a = StringUtils.removeEnd(a, ", ");
        System.out.println(a);
    }
}
