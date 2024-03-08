package edu.upc.talent.swqa.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Utils {

  private Utils() {}

  public static boolean eq(final Object o1, final Object o2) {
    return Objects.equals(o1, o2);
  }

  public static <A> List<A> join(final List<A> list1, final List<A> list2) {
    final List<A> res = new ArrayList<>(list1);
    res.addAll(list2);
    return res;
  }

  public static <A> Set<A> union(final Set<A> set1, final Set<A> set2) {
    final Set<A> res = new HashSet<>(set1);
    res.addAll(set2);
    return res;
  }

  public static <A> Set<A> plus(final Set<A> set1, final A elem) {
    final Set<A> res = new HashSet<>(set1);
    res.add(elem);
    return res;
  }

  public static <A> Set<A> minus(final Set<A> set1, final Set<A> elem) {
    final Set<A> res = new HashSet<>(set1);
    res.removeAll(elem);
    return res;
  }

  @SafeVarargs public static <A> Set<A> setOf(final A... elem) {
    final Set<A> res = new HashSet<>();
    Collections.addAll(res, elem);
    return res;
  }

  @SafeVarargs public static <A> List<A> listOf(final A... elem) {
    final List<A> res = new LinkedList<>();
    Collections.addAll(res, elem);
    return res;
  }

  public static String stringRepeat(final String s, final int size) {
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < size; i++) {
      result.append(s);
    }
    return result.toString();
  }

  public static <A, B> Map.Entry<A, B> pair(final A key, final B value) {
    return new Map.Entry<A, B>() {

      @Override public A getKey() {
        return key;
      }

      @Override public B getValue() {
        return value;
      }

      @Override public B setValue(final B value) {
        throw new UnsupportedOperationException("Not implemented");
      }
    };
  }

  @SafeVarargs public static <A, B> Map<A, B> mapOf(final Map.Entry<A, B>... values) {
    final Map<A, B> result = new HashMap<>();
    for (final Map.Entry<A, B> value : values) {
      result.put(value.getKey(), value.getValue());
    }
    return result;
  }

  public static Instant now() {
    return Instant.now().truncatedTo(ChronoUnit.MILLIS);
  }
}

