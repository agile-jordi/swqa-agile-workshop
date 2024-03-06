package edu.upc.talent.swqa.test.utils.test;

import edu.upc.talent.swqa.test.utils.Asserts;
import edu.upc.talent.swqa.test.utils.Diff;
import static edu.upc.talent.swqa.util.Utils.listOf;
import static edu.upc.talent.swqa.util.Utils.mapOf;
import static edu.upc.talent.swqa.util.Utils.pair;
import static edu.upc.talent.swqa.util.Utils.setOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public final class AssertsTest {

  static class Record1 {
    public final int a;
    public final String b;

    public Record1(final int a, final String b) {
      this.a = a;
      this.b = b;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final Record1 record1 = (Record1) o;
      return a == record1.a && Objects.equals(b, record1.b);
    }

    @Override public int hashCode() {
      return Objects.hash(a, b);
    }

    @Override public String toString() {
      return "Record1{" +
             "a=" + a +
             ", b='" + b + '\'' +
             '}';
    }
  }


  @Test
  public void testAtomicDiff() {
    assertEquals(new Diff.AtomicDiff(1, 4), Asserts.diff(1, 4));
    assertNull(Asserts.diff("Hi", "Hi"));
  }

  @Test
  public void testRecordDiff() {
    final Record1 objExpected = new Record1(1, "Hi");
    final Record1 objActual = new Record1(4, "Bye");
    final Diff expected = new Diff.RecordDiff(mapOf(
          pair("a", new Diff.AtomicDiff(1, 4)),
          pair("b", new Diff.AtomicDiff("Hi", "Bye"))
    ));
    assertEquals(expected, Asserts.diff(objExpected, objActual));
    assertNull(Asserts.diff(objExpected, objExpected));
  }

  @Test
  public void testMapDiff() {
    final Map<String, Object> objExpected = mapOf(pair("a", 1), pair("b", "Hi"));
    final Map<String, Object> objActual = mapOf(pair("a", 4), pair("b", "Bye"));
    final Diff expected = new Diff.RecordDiff(mapOf(
          pair("a", new Diff.AtomicDiff(1, 4)),
          pair("b", new Diff.AtomicDiff("Hi", "Bye"))
    ));
    assertEquals(expected, Asserts.diff(objExpected, objActual));
    assertNull(Asserts.diff(objExpected, objExpected));
  }

  @Test
  public void testSequenceDiff() {
    final List<Object> objExpected = listOf(1, "Hi");
    final List<Object> objActual = listOf(4, "Bye");
    final Diff expected = new Diff.SequenceDiff(mapOf(
          pair(0, new Diff.AtomicDiff(1, 4)),
          pair(1, new Diff.AtomicDiff("Hi", "Bye"))
    ));
    assertEquals(expected, Asserts.diff(objExpected, objActual));
    assertNull(Asserts.diff(objExpected, objExpected));
  }

  @Test
  public void testSetDiff() {
    final Diff expected = new Diff.SetDiff(setOf(1, 3), setOf(5, 7));
    final Set<Integer> objExpected = setOf(1, 2, 3, 4);
    final Set<Integer> objActual = setOf(2, 4, 5, 7);
    assertEquals(expected, Asserts.diff(objExpected, objActual));
    assertNull(Asserts.diff(objExpected, objExpected));
  }

  @Test
  public void testNestedDiff() {
    final List<Record1> objExpected = listOf(new Record1(2, "Hi"), new Record1(3, "Hi"));
    final List<Record1> objActual = listOf(new Record1(2, "Hi"), new Record1(5, "Hi"));
    final Diff expected =
          new Diff.SequenceDiff(mapOf(
                pair(1, new Diff.RecordDiff(mapOf(pair("a", new Diff.AtomicDiff(3, 5)))))
          ));
    final Diff actual = Asserts.diff(objExpected, objActual);
    assertEquals(expected, actual);
    assertNull(Asserts.diff(objExpected, objExpected));
  }

}
