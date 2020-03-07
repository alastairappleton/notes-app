package com.alastairappleton.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Colour {

    @Id
    Integer colourId;
    String colourName;

    public static void Note() {}

    public Integer getColourId() {
        return colourId;
    }

    public void setColourId(Integer colourId) {
        this.colourId = colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }
}
