package com.alastairappleton;

import javax.faces.bean.*;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class NotesBean implements Serializable {

  private Note note = new Note();
  private List<Note> noteList = new ArrayList<>();
  private Integer maxId;

  public NotesBean() {

    // no need to create a new DatabaseConnection object to use here,
    // since getConnection() method is static
    Connection con = DatabaseConnection.getConnection();

    try {
      Statement s = con.createStatement();
      ResultSet rs = s.executeQuery("SELECT noteId, noteText FROM notes");

      while (rs.next()) {
        note.setNoteId(rs.getInt("noteId"));
        note.setNoteText(rs.getString("noteText"));
        noteList.add(note);
        note = new Note(); // reset the placeholder
        }

      rs = s.executeQuery("SELECT MAX(noteId) FROM notes");
      rs.next(); // get the first (and only) row from this query
      maxId = rs.getInt(1) + 1;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    DatabaseConnection.closeConnection();

  }

  public String add() {

    if (!note.getNoteText().isEmpty()) {
      note.setNoteId(maxId);
      noteList.add(note); // this is the temporary object bound to the "add" fields at the top of the page. No need to create a new Note().

      Connection con = DatabaseConnection.getConnection();

      try {
        PreparedStatement ps = con.prepareStatement("INSERT INTO notes (noteId, noteText) values (?, ?)");
        ps.setInt(1, note.getNoteId());
        ps.setString(2, note.getNoteText());
        ps.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      DatabaseConnection.closeConnection();

      note = new Note(); // reset the placeholder (necessary if we have SessionScoped application and are not calling constructor each time)
    }

    return "index?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh
  }

  public String delete(Note note) {

    noteList.remove(note);

    Connection con = DatabaseConnection.getConnection();

    try {
      PreparedStatement ps = con.prepareStatement("DELETE FROM notes WHERE noteId = ?");
      ps.setInt(1, note.getNoteId());
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    DatabaseConnection.closeConnection();
    return "index?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh
  }


  public String update(Note note) {

    Connection con = DatabaseConnection.getConnection();

    try {
      PreparedStatement ps = con.prepareStatement("UPDATE notes SET noteText = ? WHERE noteId = ?");
      ps.setString(1, note.getNoteText());
      ps.setInt(2, note.getNoteId());
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    DatabaseConnection.closeConnection();
    return "index?faces-redirect=true"; // Redirect so we do not resubmit data when hitting 'F5' to refresh
  }

  public Note getNote() {
    return note;
  }

  public void setNote(Note note) {
    this.note = note;
  }

  public List<Note> getNoteList() {
    return noteList;
  }

  public void setNoteList(List<Note> noteList) {
    this.noteList = noteList;
  }

}