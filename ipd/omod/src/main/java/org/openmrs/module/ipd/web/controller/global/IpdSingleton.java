/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of IPD module.
 *
 *  IPD module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  IPD module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with IPD module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/


package org.openmrs.module.ipd.web.controller.global;

import java.util.HashMap;

/**
 * <p> Class: SubStoreSingleton </p>
 * <p> Package: org.openmrs.module.inventory.web.controller.substore </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Dec 28, 2010 10:11:47 PM </p>
 * <p> Update date: Dec 28, 2010 10:11:47 PM </p>
 **/
public class IpdSingleton {
	private static IpdSingleton instance = null;
	   public static final IpdSingleton getInstance(){
		   if (instance == null) {
		         instance = new IpdSingleton();
		      }
		      return instance;
	   }
	   private static HashMap<String, Object> hash;
	   public static HashMap<String, Object> getHash() {
			if( hash == null )
				hash = new HashMap<String, Object>();
			return hash;
		}
}
