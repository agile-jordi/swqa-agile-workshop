package edu.upc.talent.swqa.campus.domain.test;

import edu.upc.talent.swqa.campus.domain.CampusApp;
import edu.upc.talent.swqa.campus.test.utils.CampusAppState;
import edu.upc.talent.swqa.campus.test.utils.Group;
import edu.upc.talent.swqa.campus.test.utils.InMemoryUsersRepository;
import edu.upc.talent.swqa.campus.test.utils.SentEmail;
import static edu.upc.talent.swqa.campus.test.utils.TestFixtures.defaultInitialState;
import edu.upc.talent.swqa.campus.test.utils.UsersRepositoryState;
import static edu.upc.talent.swqa.test.utils.Asserts.assertEquals;
import static edu.upc.talent.swqa.util.Utils.setOf;
import static edu.upc.talent.swqa.util.Utils.union;
import org.junit.jupiter.api.Test;

public final class CampusAppTest {

  private CampusAppState state;

  private CampusApp getApp(final CampusAppState initialState) {
    state = initialState.copy();
    return new CampusApp(
          new InMemoryUsersRepository(state.usersRepositoryState),
          new InMemoryEmailService(state.sentEmails)
    );
  }

  private CampusAppState getFinalState() {
    return state;
  }

  @Test
  public void testCreateGroup() {
    final CampusApp app = getApp(defaultInitialState);
    final Group newGroup = new Group("0", "bigdata");
    app.createGroup(newGroup.id, newGroup.name);
    final CampusAppState expectedFinalState = new CampusAppState(
          new UsersRepositoryState(
                defaultInitialState.usersRepositoryState.users,
                union(defaultInitialState.usersRepositoryState.groups, setOf(newGroup))
          ),
          setOf()
    );
    assertEquals(expectedFinalState, getFinalState());
  }

  @Test
  public void testSendEmailToGroup() {
    final CampusApp app = getApp(defaultInitialState);
    final String subject = "New campus!";
    final String body = "Hello everyone! We just created a new virtual campus!";
    app.sendMailToGroup("swqa", subject, body);
    final CampusAppState expectedFinalState = new CampusAppState(
          defaultInitialState.usersRepositoryState,
          setOf(
                new SentEmail("john.doe@example.com", subject, body),
                new SentEmail("jane.doe@example.com", subject, body),
                new SentEmail("mariah.hairam@example.com", subject, body)
          )
    );
    assertEquals(expectedFinalState, getFinalState());
  }

  @Test
  public void testSendEmailToGroupRole() {
    final CampusApp app = getApp(defaultInitialState);
    final String subject = "Hey! Teacher!";
    final String body = "Let them students alone!!";
    app.sendMailToGroupRole("swqa", "teacher", subject, body);
    final CampusAppState expectedFinalState = new CampusAppState(
          defaultInitialState.usersRepositoryState,
          setOf(new SentEmail("mariah.hairam@example.com", subject, body))
    );
    assertEquals(expectedFinalState, getFinalState());
  }

}
