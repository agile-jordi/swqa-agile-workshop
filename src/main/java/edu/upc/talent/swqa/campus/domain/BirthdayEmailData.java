package edu.upc.talent.swqa.campus.domain;

import java.util.Objects;

public final class BirthdayEmailData {
  public final String email;
  public final String name;
  public final String surname;
  public final String createdAt;

  public BirthdayEmailData(final String email, final String name, final String surname, final String createdAt) {
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.createdAt = createdAt;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final BirthdayEmailData that = (BirthdayEmailData) o;
    return Objects.equals(email, that.email) && Objects.equals(name, that.name) &&
           Objects.equals(surname, that.surname) && Objects.equals(createdAt, that.createdAt);
  }

  @Override public int hashCode() {
    return Objects.hash(email, name, surname, createdAt);
  }

  @Override public String toString() {
    return "BirthdayEmailData{" +
           "email='" + email + '\'' +
           ", name='" + name + '\'' +
           ", surname='" + surname + '\'' +
           ", createdAt='" + createdAt + '\'' +
           '}';
  }
}
