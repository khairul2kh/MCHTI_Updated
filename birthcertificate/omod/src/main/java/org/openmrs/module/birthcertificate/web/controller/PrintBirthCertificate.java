/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.birthcertificate.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.birthcertificate.api.BirthCertificateService;
import org.openmrs.module.birthcertificate.model.BirthRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Khairul
 */
@Controller("PrintBirthCertificateController")
public class PrintBirthCertificate {
    BirthCertificateService bcs= Context.getService(BirthCertificateService.class);

    @RequestMapping(value = "/module/birthcertificate/birthView.htm", method = RequestMethod.GET)
    public String printView(@RequestParam(value = "id", required = false) Integer id,
            Model model) {
        
        BirthRegistration br=bcs.getBirthRegById(id);
        model.addAttribute("birthReg",br);

        return "module/birthcertificate/main/printBirthCertificate";
    }

}
