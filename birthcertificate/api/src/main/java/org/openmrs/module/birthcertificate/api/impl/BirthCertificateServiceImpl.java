/**
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.birthcertificate.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.module.birthcertificate.api.BirthCertificateService;
import org.openmrs.module.birthcertificate.api.db.BirthCertificateDAO;
import org.openmrs.module.birthcertificate.model.BirthRegistration;

/**
 * It is a default implementation of {@link BirthCertificateService}.
 */
public class BirthCertificateServiceImpl extends BaseOpenmrsService implements BirthCertificateService {

    protected final Log log = LogFactory.getLog(this.getClass());

    private BirthCertificateDAO dao;

    public void setDao(BirthCertificateDAO dao) {
        this.dao = dao;
    }

    public BirthCertificateDAO getDao() {
        return dao;
    }

    @Override
    public BirthRegistration saveBirthRegistration(BirthRegistration birthRegistration) throws APIException {
        return dao.saveBirthRegistration(birthRegistration);
    }

    @Override
    public BirthRegistration getBirthRegById(Integer id) throws APIException {
        return dao.getBirthRegById(id);
    }
}
