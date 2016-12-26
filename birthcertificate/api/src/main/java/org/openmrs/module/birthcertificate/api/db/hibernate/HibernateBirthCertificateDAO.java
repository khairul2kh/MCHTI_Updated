/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.birthcertificate.api.db.hibernate;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.birthcertificate.api.db.BirthCertificateDAO;
import org.openmrs.module.birthcertificate.model.BirthRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * It is a default implementation of  {@link BirthCertificateDAO}.
 */
@Repository
public class HibernateBirthCertificateDAO implements BirthCertificateDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
        @Autowired
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
   
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

    @Override
    public BirthRegistration getBirthRegById(Integer id) throws DAOException {
           Criteria criteria=sessionFactory.getCurrentSession().createCriteria(BirthRegistration.class);
         criteria.add(Restrictions.eq("id", id));
        return (BirthRegistration) criteria.uniqueResult();
    }

    @Override
    public void addbirthCertificate(BirthRegistration birthRegistration) throws DAOException {
        sessionFactory.getCurrentSession().saveOrUpdate(birthRegistration);
    }

    @Override
    public void updatebirthCertificate(BirthRegistration birthRegistration) throws DAOException {
       sessionFactory.getCurrentSession().update(birthRegistration);
    }

    @Override
    public List<BirthRegistration> listBirthRegistration(Integer id) throws DAOException {
        return sessionFactory.getCurrentSession().createQuery("from BirthRegistration "
                +"where id= '" + id + "'").list();
                
    }

    @Override
    public void removebirthCertificate(BirthRegistration birthRegistration) throws DAOException {
        sessionFactory.getCurrentSession().delete(birthRegistration);
    }


}