package edu.upc.talent.swqa.campus.test.utils;

import edu.upc.talent.swqa.campus.domain.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class UsersRepositoryState {
  public final Set<User> users;
  public final Set<Group> groups;

  public UsersRepositoryState(final Set<User> users, final Set<Group> groups) {
    this.users = users;
    this.groups = groups;
  }

  public UsersRepositoryState copy() {
    return new UsersRepositoryState(new HashSet<>(users), new HashSet<>(groups));
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final UsersRepositoryState that = (UsersRepositoryState) o;
    return Objects.equals(users, that.users) && Objects.equals(groups, that.groups);
  }

  @Override public int hashCode() {
    return Objects.hash(users, groups);
  }
}
