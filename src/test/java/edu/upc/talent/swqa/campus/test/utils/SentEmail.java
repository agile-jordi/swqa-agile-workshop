package edu.upc.talent.swqa.campus.test.utils;

import java.util.Objects;

public final class SentEmail {
  public final String to;
  public final String subject;
  public final String body;

  public SentEmail(final String to, final String subject, final String body) {
    this.to = to;
    this.subject = subject;
    this.body = body;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final SentEmail sentEmail = (SentEmail) o;
    return Objects.equals(to, sentEmail.to) && Objects.equals(subject, sentEmail.subject) &&
           Objects.equals(body, sentEmail.body);
  }

  @Override public int hashCode() {
    return Objects.hash(to, subject, body);
  }

  @Override public String toString() {
    return "SentEmail{" +
           "to='" + to + '\'' +
           ", subject='" + subject + '\'' +
           ", body='" + body + '\'' +
           '}';
  }
}
