/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmrs.module.birthcertificate.model;

import java.sql.Time;
import java.util.Date;
import org.openmrs.User;

/**
 *
 * @author Khairul
 */
public class BirthRegistration {
    private int id;
    private String memoNo;
    private Date date;
    private String registrationNo;
    private String name;
    private String sex;
    private Date dateOfBirth;
    private Time timeOfBirth;
    private String mothersName;
    private String nidMoth;
    private String fathersName;
    private String nidFath;
    private String presentAdd;
    private String permanentAdd;
    private User creator;
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemoNo() {
        return memoNo;
    }

    public void setMemoNo(String memoNo) {
        this.memoNo = memoNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Time getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(Time timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getNidMoth() {
        return nidMoth;
    }

    public void setNidMoth(String nidMoth) {
        this.nidMoth = nidMoth;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getNidFath() {
        return nidFath;
    }

    public void setNidFath(String nidFath) {
        this.nidFath = nidFath;
    }

    public String getPresentAdd() {
        return presentAdd;
    }

    public void setPresentAdd(String presentAdd) {
        this.presentAdd = presentAdd;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
