package edu.upc.talent.swqa.util.test;

import static edu.upc.talent.swqa.test.utils.Asserts.assertEquals;
import static edu.upc.talent.swqa.util.Utils.setOf;
import static edu.upc.talent.swqa.util.Utils.union;
import org.junit.jupiter.api.Test;

import java.util.Set;

public final class UtilsTest {

  @Test
  public void testSetUnion() {
    // Arrange
    final Set<Integer> set1 = setOf(1, 3, 5);
    final Set<Integer> set2 = setOf(3, 4, 6);
    // Act
    final Set<Integer> result = union(set1, set2);
    // Assert
    final Set<Integer> expected = setOf(1,3,4,5,6);
    assertEquals(expected, result);
  }

}
