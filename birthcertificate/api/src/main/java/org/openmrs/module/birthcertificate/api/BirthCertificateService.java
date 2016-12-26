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
package org.openmrs.module.birthcertificate.api;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> origin/master
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.birthcertificate.model.BirthRegistration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BirthCertificateService extends OpenmrsService {

<<<<<<< HEAD
    public void addbirthCertificate(BirthRegistration birthRegistration) throws APIException;

    public void updatebirthCertificate(BirthRegistration birthRegistration) throws APIException;

    public List<BirthRegistration> listBirthRegistration(Integer id) throws APIException;

    public void removebirthCertificate(BirthRegistration birthRegistration) throws APIException;

   public BirthRegistration getBirthRegById(Integer id) throws APIException;

=======
public BirthRegistration saveBirthRegistration(BirthRegistration birthRegistration) throws APIException;

public BirthRegistration getBirthRegById(Integer id)throws APIException;
>>>>>>> origin/master

}
