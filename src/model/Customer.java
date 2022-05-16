package model;

import java.util.regex.Pattern;

public class Customer {
  private final String firstName;
  private final String lastName;
  private final String email;

  public Customer(String firstName, String lastName, String email) {
    Pattern emailPattern = Pattern.compile("^(.+)@(.+).(com|edu)$");
    if (!emailPattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid Input for Email. Format: name@domain.com");
    }
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "Name: " + firstName + " " + lastName + ", email: " + email;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (this == o) return true;
    if (this.getClass() != o.getClass()) return false;
    return this.email.equals(((Customer) o).email);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + ((email == null) ? 0 : email.hashCode());
    return hash;
  }
}
