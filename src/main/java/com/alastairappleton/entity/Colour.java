package com.alastairappleton.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Colour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer colourId;
    String colourCode;
    String colourName;

    public static void Colour() {}

    public Integer getColourId() {
        return colourId;
    }

    public void setColourId(Integer colourId) {
        this.colourId = colourId;
    }

    public String getColourCode() {
        return colourCode;
    }

    public void setColourCode(String colourCode) {
        this.colourCode = colourCode;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }

    public String toString() {
        // this allows us to use 'c' instead of 'c.colourId' in the XHTML ItemValue
        // and we get
        //      Conversion Error setting value '17' for 'null Converter'
        // instead of
        //      Conversion Error setting value 'com.alastairappleton.entity.Colour@54e1e209' for 'null Converter'
        return colourId.toString();
    }


    // Must implement these otherwise we get j_idt18:j_idt20:1:j_idt23: Validation Error: Value is not valid
    // See: https://stackoverflow.com/questions/6848970/how-to-populate-options-of-hselectonemenu-from-database
    @Override
    public boolean equals(Object object) {
        // Basic checks.
        if (object == this) return true;
        if (!(object instanceof Colour)) return false;

        // Property checks.
        Colour other = (Colour) object;
        return Objects.equals(colourId, other.colourId)
                && Objects.equals(colourCode, other.colourCode)
                && Objects.equals(colourName, other.colourName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colourId, colourCode, colourName);
    }



}
