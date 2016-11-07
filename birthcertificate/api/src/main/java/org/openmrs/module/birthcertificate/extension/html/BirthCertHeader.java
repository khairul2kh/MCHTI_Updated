/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.birthcertificate.extension.html;

import org.openmrs.module.Extension;

/**
 *
 * @author Khairul
 */
public class BirthCertHeader extends Extension {

    public MEDIA_TYPE getMediaType() {
		return MEDIA_TYPE.html;
	}
	
	public String getRequiredPrivilege() {
		return "View Birth Certificate";
	}

	public String getLabel() {
		return "Birth Certificate";
	}

	public String getUrl() {
            return "/module/birthcertificate/main.form";
	}
    
}
