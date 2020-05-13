package com.alastairappleton.bean;

import com.alastairappleton.enums.Importance;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ImportanceBean {

    public Importance[] getImportantList() {
        return Importance.values();
    }

}