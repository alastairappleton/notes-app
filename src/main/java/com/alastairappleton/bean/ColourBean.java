package com.alastairappleton.bean;

import com.alastairappleton.entity.Colour;
import com.alastairappleton.services.ColourService;
import com.alastairappleton.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ColourBean implements Serializable {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
  private Colour colour = new Colour();
  private List<Colour> colourList;
  private ColourService colourService = new ColourService();

  public ColourBean() {
  // Balus C says we should not do any logic in the bean's constructor but should use PostConstruct instead
  //  colourList = colourService.findAll();
  }

  @PostConstruct
  public void init() {
    colourList = colourService.findAll();
  }

  public String add() {

    if (this.colour.getColourCode().isEmpty() | this.colour.getColourName().isEmpty()) {
      return "colours?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh;
    }

    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.save(this.colour);
      transaction.commit();
      this.colourList.add(this.colour); // add to the page ONLY IF THE INSERT WORKED
      this.colour = new Colour(); // blank out the user so that we don't re-insert the same values
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    return "colours?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh

  }

  public String delete(Colour colour) {

    this.colourList.remove(colour); // Remove the note from the page (as well as deleting from the database)

    Session session = null;
    Transaction transaction = null;
    try {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(colour);
        transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    return "colours?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh

}


  public String update(Colour colour) {

    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.update(colour);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    return "colours?faces-redirect=true"; // Redirect so we keep data when refreshing

  }

  public Colour getColour() {
    return colour;
  }

  public void setNote(Colour colour) {
    this.colour = colour;
  }

  public List<Colour> getColourList() {
    return colourList;
  }

  public void setColourList(List<Colour> colourList) {
    this.colourList = colourList;
  }

}