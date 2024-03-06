package edu.upc.talent.swqa.campus.domain;

import java.util.Objects;

public final class User {
  public final String id;
  public final String name;
  public final String surname;
  public final String email;
  public final String role;
  public final String groupName;


  public User(final String id, final String name, final String surname, final String email, final String role, final String groupName) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.role = role;
    this.groupName = groupName;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(name, user.name) &&
           Objects.equals(surname, user.surname) && Objects.equals(email, user.email) &&
           Objects.equals(role, user.role) && Objects.equals(groupName, user.groupName);
  }

  @Override public int hashCode() {
    return Objects.hash(id, name, surname, email, role, groupName);
  }

  @Override public String toString() {
    return "User{" +
           "id='" + id + '\'' +
           ", name='" + name + '\'' +
           ", surname='" + surname + '\'' +
           ", email='" + email + '\'' +
           ", role='" + role + '\'' +
           ", groupName='" + groupName + '\'' +
           '}';
  }
}
