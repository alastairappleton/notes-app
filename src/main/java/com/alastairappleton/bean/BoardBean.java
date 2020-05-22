package com.alastairappleton.bean;

import com.alastairappleton.entity.Colour;
import com.alastairappleton.services.ColourService;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
// keep state when we press F5, but not when we press 'Enter' in address bar (as this is a new GET request)
public class BoardBean implements Serializable {

    // Using a managed property allows us to call a different bean (and it's methods) from inside this bean
    @ManagedProperty(value="#{notesBean}")
    NotesBean notesBean;

    private String selectedOrder;
    private List<SelectItem> availableOrders;

    public void BoardBean() {}

    // Note that we had to add the javax.annotation-api to Maven dependencies
    // We need to do this as Post-Construct and not in the Constructor, else the values will not be available to us
    @PostConstruct
    public void init() {
        availableOrders = new ArrayList<SelectItem>();
        availableOrders.add(new SelectItem("AZ", "Alphabetical (A-Z)"));
        availableOrders.add(new SelectItem("ZA", "Alphabetical (Z-A)"));
        availableOrders.add(new SelectItem("PRIORITY", "Priority"));
        availableOrders.add(new SelectItem("ADDED", "Order created"));
    }

    public String getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(String selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public List<SelectItem> getAvailableOrders() {
        return availableOrders;
    }

    public NotesBean getNotesBean() {return notesBean;}

    public void setNotesBean(NotesBean notesBean) {this.notesBean = notesBean;}

    public void sort() {

        switch(this.selectedOrder) {
            case "AZ":
                notesBean.sortAscending();
                break;
            case "ZA":
                notesBean.sortDescending();
                break;
            case "PRIORITY":
                notesBean.sortImportance();
                break;
            case "ADDED":
                notesBean.sortDateCreated();
        }

        // Refresh the notes area:
        // FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form:cardsForm");
        // (No need for this, since we moved everything into one form and use f:ajax 'render' attribute)

    }

}
