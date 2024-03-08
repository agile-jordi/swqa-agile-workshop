package edu.upc.talent.swqa.campus.test.utils;

import java.util.Objects;

public final class Group {
  public final String id;
  public final String name;

  public Group(final String id, final String name) {
    this.id = id;
    this.name = name;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Group group = (Group) o;
    return Objects.equals(id, group.id) && Objects.equals(name, group.name);
  }

  @Override public int hashCode() {
    return Objects.hash(id, name);
  }
}
