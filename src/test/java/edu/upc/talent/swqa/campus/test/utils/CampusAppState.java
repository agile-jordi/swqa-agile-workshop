package edu.upc.talent.swqa.campus.test.utils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class CampusAppState {
  public final UsersRepositoryState usersRepositoryState;
  public final Set<SentEmail> sentEmails;

  public CampusAppState(final UsersRepositoryState usersRepositoryState, final Set<SentEmail> sentEmails) {
    this.usersRepositoryState = usersRepositoryState;
    this.sentEmails = sentEmails;
  }

  public CampusAppState copy() {return new CampusAppState(usersRepositoryState.copy(), new HashSet<>(sentEmails));}

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final CampusAppState that = (CampusAppState) o;
    return Objects.equals(usersRepositoryState, that.usersRepositoryState) &&
           Objects.equals(sentEmails, that.sentEmails);
  }

  @Override public int hashCode() {
    return Objects.hash(usersRepositoryState, sentEmails);
  }
}


