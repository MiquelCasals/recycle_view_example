package es.covalco.exemplerecycleview.ExerciciPersones;

public class Person {
  private int id;
  private String name;
  private String email_id;
  private PassportDetails passportDetails;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail_id() {
    return email_id;
  }

  public void setEmail_id(String email_id) {
    this.email_id = email_id;
  }

  public PassportDetails getPassportDetails() {
    return passportDetails;
  }

  public void setPassportDetails(PassportDetails passportDetails) {
    this.passportDetails = passportDetails;
  }


  public Person (int id, String name, String email_id) {
    this.id = id;
    this.name = name;
    this.email_id = email_id;
  }

  @Override
  public String toString() {
    return name;
  }
}
