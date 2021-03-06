package com.alastairappleton.bean;

import com.alastairappleton.entity.Note;
import com.alastairappleton.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

@ManagedBean
@ViewScoped
public class NotesBean implements Serializable {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
  private Note note = new Note();
  private List<Note> noteList;

    @ManagedProperty(value="#{boardBean}")
    BoardBean boardBean;


  public NotesBean() {}

  @PostConstruct
  public void init() {
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("from Note"); // Use the same name as the Entity class (not the database table, if different)
      noteList = query.list();
      transaction.commit();
    } catch (Exception e) {
      noteList = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    if (noteList != null) {
      this.sort();
    }

  }

  public String add() {

    if (this.note.getNoteText().isEmpty()) {
      return "index?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh;
    }

    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.save(this.note);
      transaction.commit();
      this.noteList.add(this.note); // add to the page ONLY IF THE INSERT WORKED
      this.note = new Note(); // blank out the note so that we don't re-insert the same values
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    this.sort();

    return "index?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh

  }

  public String delete(Note note) {

    this.noteList.remove(note); // Remove the note from the page (as well as deleting from the database)

    Session session = null;
    Transaction transaction = null;
    try {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(note);
        transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    return "index?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh

}


  public String update(Note note) {

//    if (note.getNoteText().isEmpty()) {
//      System.out.println("Update method: we found empty text.");
//      return "index?faces-redirect=true";
//    }

    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();

      session.update(note);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }

    return "index?faces-redirect=true"; // Redirect so we keep data when refreshing

  }

  public Note getNote() {
    return note;
  }

  public void setNote(Note note) {
    // Do not allow user to update the note text from content to blank
    //    if (!note.getNoteText().isEmpty()) {
    //      System.out.println("Setter. Note was not empty, therefore doing setting");
    //      this.note = note;
    this.note = note;
//    }
  }

  public List<Note> getNoteList() {
    return noteList;
  }

  public void setNoteList(List<Note> noteList) {
    this.noteList = noteList;
  }

  public BoardBean getBoardBean() {
    return boardBean;
  }

  public void setBoardBean(BoardBean boardBean) {
    this.boardBean = boardBean;
  }

  public void toggleFavourite(Note n) {
    n.setFavourite(!n.getFavourite());
    this.update(n);
  }




  public void sort() {
    if (boardBean.getSelectedOrder() == null) { this.sortDateCreated(); } else {
      switch (boardBean.getSelectedOrder()) {
        case "AZ":
          this.sortAscending();
          break;
        case "ZA":
          this.sortDescending();
          break;
        case "PRIORITY":
          this.sortImportance();
          break;
        case "ADDED":
          this.sortDateCreated();
      }
    }
  }


  public void sortAscending() {
    this.noteList.sort(Comparator.comparing(Note::getNoteText));
  }

  public void sortDescending() {
    // Lambda implementation
    //this.noteList.sort((a, b) -> b.getNoteText().compareTo(a.getNoteText()));
    // Alternate implementation:
    this.noteList.sort(Comparator.comparing(Note::getNoteText));
    Collections.reverse(this.noteList);
  }

  public void sortImportance() {
    // enums are ordered in the order that they are declared
    // nullsLast would deal with null beans, NOT null importances (see https://stackoverflow.com/a/53315210)
    // Wrong:
    /* this.noteList.sort(Comparator.nullsLast(Comparator.comparing(Note::getImportance))); */
    //this.noteList.sort(Comparator.comparing(Note::getImportance, nullsLast(naturalOrder())));
    List<Note> localList = noteList;
    localList.sort(Comparator.comparing(Note::getNoteId));
    localList.sort(Comparator.nullsLast(Comparator.comparing(Note::getImportance, nullsLast(naturalOrder()))));
    this.noteList = localList;
  }

  public void sortDateCreated() {
    // We can use the hibernate sequence ID as a proxy for the order that notes were created
    this.noteList.sort(Comparator.comparing(Note::getNoteId));
  }


}