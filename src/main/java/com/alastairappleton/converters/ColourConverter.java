package com.alastairappleton.converters;

import com.alastairappleton.bean.ColourBean;
import com.alastairappleton.entity.Colour;
import com.alastairappleton.services.ColourService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Colour.class)
public class ColourConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }
        try {
            return new ColourService().find(Integer.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid colour ID", submittedValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Colour) {
            return ((Colour) modelValue).getColourId().toString();
            // return String.valueOf(((Colour) modelValue).getColourId());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid colour"));
        }
    }

}
