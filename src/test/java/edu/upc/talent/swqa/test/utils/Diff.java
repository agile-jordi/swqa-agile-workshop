package edu.upc.talent.swqa.test.utils;

import static edu.upc.talent.swqa.util.Utils.stringRepeat;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface Diff {
  String toString(final boolean newLine, final int indent);

  final class RecordDiff implements Diff {

    public final Map<String, Diff> diffs;

    public RecordDiff(final Map<String, Diff> diffs) {
      this.diffs = diffs;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final RecordDiff that = (RecordDiff) o;
      return Objects.equals(diffs, that.diffs);
    }

    @Override public int hashCode() {
      return Objects.hash(diffs);
    }

    @Override
    public String toString() {
      return toString(false, 0);
    }

    public String toString(final boolean newLine, final int indent) {
      final String spaces = stringRepeat(" ",indent);
      final String sep = (diffs.size() != 1 || newLine) ? "\n" + spaces : "" ;
      return sep + diffs.keySet()
                        .stream()
                        .sorted()
                        .map(k -> k + ": " + diffs.get(k).toString(false, indent + 2))
                        .reduce((a, b) -> a + "\n" + spaces + b)
                        .orElse("");
    }
  }

  final class SequenceDiff implements Diff {

    public final Map<Integer, Diff> diffs;

    public SequenceDiff(final Map<Integer, Diff> diffs) {
      Objects.requireNonNull(diffs);
      assert (!diffs.isEmpty());
      this.diffs = diffs;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final SequenceDiff that = (SequenceDiff) o;
      return Objects.equals(diffs, that.diffs);
    }

    @Override public int hashCode() {
      return Objects.hash(diffs);
    }


    @Override
    public String toString() {
      return toString(false, 0);
    }

    public String toString(final boolean newLine, final int indent) {
      final String spaces = stringRepeat(" ",indent);
      final String sep = (diffs.size() != 1 || newLine) ? "\n" + spaces : "" ;
      return sep + diffs.keySet()
                        .stream()
                        .sorted()
                        .map(k -> k + ": " + diffs.get(k).toString(false, indent + 2))
                        .reduce((a, b) -> a + "\n" + spaces + b)
                        .orElse("");
    }
  }

  final class SetDiff implements Diff {

    public final Set<Object> missing;
    public final Set<Object> unexpected;


    public SetDiff(final Set<Object> missing, final Set<Object> unexpected) {
      this.missing = missing;
      this.unexpected = unexpected;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final SetDiff setDiff = (SetDiff) o;
      return Objects.equals(missing, setDiff.missing) && Objects.equals(unexpected, setDiff.unexpected);
    }

    @Override public int hashCode() {
      return Objects.hash(missing, unexpected);
    }

    @Override
    public String toString(final boolean newLine, final int indent) {
      final String sMissing = missing.toString();
      final String sUnexpected = unexpected.toString();
      if (newLine || sMissing.length() + sUnexpected.length() > 100) {
        final String spaces = stringRepeat(" ",indent);
        return "\n" + spaces + "missing: " + sMissing + "\n" + spaces + "unexpected: " + sUnexpected;
      } else {
        return "missing: " + missing + ", unexpected: " + unexpected;
      }
    }
  }

  final class AtomicDiff implements Diff {

    public final Object expected;
    public final Object actual;

    public AtomicDiff(final Object expected, final Object actual) {
      this.expected = expected;
      this.actual = actual;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final AtomicDiff that = (AtomicDiff) o;
      return Objects.equals(expected, that.expected) && Objects.equals(actual, that.actual);
    }

    @Override public int hashCode() {
      return Objects.hash(expected, actual);
    }

    @Override
    public String toString() {
      return toString(false, 0);
    }

    @Override
    public String toString(final boolean newLine, final int indent) {
      final String sExpected = Objects.toString(expected);
      final String sActual = Objects.toString(actual);
      if (newLine || sExpected.length() + sActual.length() > 100) {
        final String spaces = stringRepeat(" ",indent);
        return "\n" + spaces + "expected: " + sExpected + "\n" + spaces + "actual: " + sActual;
      } else {
        return "expected: " + expected + ", actual: " + actual;
      }
    }

  }

}