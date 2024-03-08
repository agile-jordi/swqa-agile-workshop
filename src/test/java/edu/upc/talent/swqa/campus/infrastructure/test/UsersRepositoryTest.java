package edu.upc.talent.swqa.campus.infrastructure.test;

import edu.upc.talent.swqa.campus.domain.User;
import edu.upc.talent.swqa.campus.domain.UsersRepository;
import edu.upc.talent.swqa.campus.test.utils.TestFixtures;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.janeDoe;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.johnDoe;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.mariahHairam;
import edu.upc.talent.swqa.campus.test.utils.UsersRepositoryState;
import static edu.upc.talent.swqa.test.utils.Asserts.assertEquals;
import static edu.upc.talent.swqa.util.Utils.now;
import static edu.upc.talent.swqa.util.Utils.plus;
import static edu.upc.talent.swqa.util.Utils.setOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashSet;

public interface UsersRepositoryTest {

  UsersRepository getRepository(final UsersRepositoryState initialState);

  UsersRepositoryState getFinalState();


  default void assertExpectedFinalState(final UsersRepositoryState expectedFinalState) {
    assertEquals(expectedFinalState, getFinalState());
  }

  UsersRepositoryState defaultInitialState = TestFixtures.defaultInitialState.usersRepositoryState;

  @Test
  default void testGetUsersByGroup() {
    final var repository = getRepository(defaultInitialState);
    final var actual = repository.getUsersByGroup("swqa");
    assertEquals(setOf(johnDoe, janeDoe, mariahHairam), new HashSet<>(actual));
    assertExpectedFinalState(defaultInitialState);
  }

  @Test
  @Disabled
  default void testCreateUser() {
    // Arrange
    final UsersRepository repository = getRepository(defaultInitialState);
    final String id = "0";
    final String name = "Jack";
    final String surname = "Doe";
    final String email = "jack.doe@example.com";
    final String role = "student";
    final String groupName = "swqa";
    final Instant now = now();
    // Act
    repository.createUser(id, name, surname, email, role, groupName);
    // Assert
    final User expectedNewUser = new User(id, name, surname, email, role, groupName, now);
    final UsersRepositoryState expected =
          new UsersRepositoryState(plus(defaultInitialState.users, expectedNewUser), defaultInitialState.groups);
    assertExpectedFinalState(expected);
  }

  @Test
  @Disabled
  default void testCreateUserFailsIfGroupDoesNotExist() {
    final UsersRepository repository = getRepository(defaultInitialState);
    final String groupName = "non-existent";
    final Exception exception = assertThrows(RuntimeException.class, () ->
          repository.createUser("0", "a", "b", "a.b@example.com", "student", groupName)
    );
    assertEquals("Group " + groupName + " does not exist", exception.getMessage());
    assertExpectedFinalState(defaultInitialState);
  }
}



