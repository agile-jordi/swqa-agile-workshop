package edu.upc.talent.swqa.campus.e2e;

import edu.upc.talent.swqa.campus.domain.CampusApp;
import edu.upc.talent.swqa.campus.domain.UsersRepository;
import edu.upc.talent.swqa.campus.domain.test.InMemoryEmailService;
import edu.upc.talent.swqa.campus.test.utils.SentEmail;
import edu.upc.talent.swqa.campus.infrastructure.PostgreSqlUsersRepository;
import edu.upc.talent.swqa.campus.infrastructure.test.PostgreSqlUsersRepositoryTestHelper;
import edu.upc.talent.swqa.campus.test.utils.CampusAppState;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.defaultInitialState;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.janeDoe;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.johnDoe;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.mariahHairam;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.swqa;
import edu.upc.talent.swqa.campus.test.utils.UsersRepositoryState;
import edu.upc.talent.swqa.jdbc.test.utils.DatabaseBackedTest;
import edu.upc.talent.swqa.campus.test.utils.Group;
import static edu.upc.talent.swqa.test.utils.Asserts.assertEquals;
import static edu.upc.talent.swqa.util.Utils.plus;
import static edu.upc.talent.swqa.util.Utils.setOf;
import static edu.upc.talent.swqa.util.Utils.union;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public final class CampusAppEndToEndTest extends DatabaseBackedTest {

  private HashSet<SentEmail> emailServiceState = new HashSet<>();

  public CampusApp getApp(final CampusAppState initialState) {
    PostgreSqlUsersRepositoryTestHelper.setInitialState(db, initialState.usersRepositoryState);
    final UsersRepository repo = new PostgreSqlUsersRepository(db);
    emailServiceState = new HashSet<>(initialState.sentEmails);
    return new CampusApp(repo, new InMemoryEmailService(emailServiceState));
  }


  private CampusAppState getFinalState() {
    return new CampusAppState(PostgreSqlUsersRepositoryTestHelper.getFinalState(db), emailServiceState);
  }

  @Test
  public void testCreateGroup() {
    final CampusApp app = getApp(defaultInitialState);
    final Group group = new Group("3", "bigdata");
    app.createGroup(group.id, group.name);
    final CampusAppState expectedState = new CampusAppState(
          new UsersRepositoryState(
                defaultInitialState.usersRepositoryState.users,
                plus(defaultInitialState.usersRepositoryState.groups, group)
          ),
          defaultInitialState.sentEmails
    );
    assertEquals(expectedState, getFinalState());
  }

  @Test
  public void testSendEmailToGroup() {
    final CampusApp app = getApp(defaultInitialState);
    final String subject = "New campus!";
    final String body = "Hello everyone! We just created a new virtual campus!";
    app.sendMailToGroup(swqa.name, subject, body);
    final Set<SentEmail> expectedNewEmails = setOf(
          new SentEmail(johnDoe.email, subject, body),
          new SentEmail(janeDoe.email, subject, body),
          new SentEmail(mariahHairam.email, subject, body)
    );
    final CampusAppState expectedState = new CampusAppState(
          defaultInitialState.usersRepositoryState,
          union(defaultInitialState.sentEmails, expectedNewEmails)
    );
    assertEquals(expectedState, getFinalState());
  }

  @Test
  public void testSendEmailToGroupRole() {
    final CampusApp app = getApp(defaultInitialState);
    final String roleName = "teacher";
    final String subject = "Hey! Teacher!";
    final String body = "Let them students alone!!";
    app.sendMailToGroupRole(swqa.name, roleName, subject, body);
    final Set<SentEmail> expectedNewEmails = setOf(new SentEmail(mariahHairam.email, subject, body));
    final CampusAppState expectedState = new CampusAppState(
          defaultInitialState.usersRepositoryState,
          union(defaultInitialState.sentEmails, expectedNewEmails)
    );
    assertEquals(expectedState, getFinalState());
  }

}
