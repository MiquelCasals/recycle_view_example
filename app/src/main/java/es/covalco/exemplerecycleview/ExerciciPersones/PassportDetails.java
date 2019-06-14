package es.covalco.exemplerecycleview.ExerciciPersones;

public class PassportDetails {
  private int id;
  private String passportNumber;
  private int personId;

  public PassportDetails(int id, String passportNumber, int personId) {
    this.id = id;
    this.passportNumber = passportNumber;
    this.personId = personId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }

  public int getPersonId() {
    return personId;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }
}
