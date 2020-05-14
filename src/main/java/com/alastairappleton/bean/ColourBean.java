package com.alastairappleton.bean;

import com.alastairappleton.entity.Colour;
import com.alastairappleton.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.convert.FacesConverter;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ColourBean implements Serializable {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
  private Colour colour = new Colour();
  private List<Colour> colourList;

  public ColourBean() {
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("from Colour"); // Use the same name as the Entity class (not the database table, if different)
      colourList = query.list();
      transaction.commit();
      } catch (Exception e) {
        colourList = null;
        if (transaction != null) {
          transaction.rollback();
        }
      } finally {
      session.close();
    }
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


  public Colour find(Long l) {
    Colour c = new Colour();

    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      String hql = "from Colour where colourId = ?";
      List result = session.createQuery(hql).setParameter(0, l.toString()).list();
      c = (Colour) result.get(0);
      transaction.commit();
    } catch (Exception e) {
      colourList = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }
    return c;
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