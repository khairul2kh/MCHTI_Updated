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

import java.util.List;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.module.birthcertificate.api.BirthCertificateService;
import org.openmrs.module.birthcertificate.api.db.BirthCertificateDAO;
import org.openmrs.module.birthcertificate.model.BirthRegistration;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
=======
>>>>>>> origin/master

/**
 * It is a default implementation of {@link BirthCertificateService}.
 */
@Service
public class BirthCertificateServiceImpl extends BaseOpenmrsService implements BirthCertificateService {

    protected final Log log = LogFactory.getLog(this.getClass());

<<<<<<< HEAD
    @Autowired
=======
>>>>>>> origin/master
    private BirthCertificateDAO dao;

    public void setDao(BirthCertificateDAO dao) {
        this.dao = dao;
    }

    public BirthCertificateDAO getDao() {
        return dao;
    }

<<<<<<< HEAD

    @Override
    @Transactional
    public void addbirthCertificate(BirthRegistration birthRegistration) throws APIException {
       dao.addbirthCertificate(birthRegistration);
    }

    @Override
    @Transactional
    public void updatebirthCertificate(BirthRegistration birthRegistration) throws APIException {
        dao.updatebirthCertificate(birthRegistration);
    }


    @Override
    @Transactional
    public void removebirthCertificate(BirthRegistration birthRegistration) throws APIException {
        dao.removebirthCertificate(birthRegistration);
    }

    @Override
    @Transactional
    public BirthRegistration getBirthRegById(Integer id) throws APIException {
        return dao.getBirthRegById(id);
    }

    @Override
    public List<BirthRegistration> listBirthRegistration(Integer id) throws APIException {
       return dao.listBirthRegistration(id);
    }


    
=======
    @Override
    public BirthRegistration saveBirthRegistration(BirthRegistration birthRegistration) throws APIException {
        return dao.saveBirthRegistration(birthRegistration);
    }

    @Override
    public BirthRegistration getBirthRegById(Integer id) throws APIException {
        return dao.getBirthRegById(id);
    }
>>>>>>> origin/master
}
