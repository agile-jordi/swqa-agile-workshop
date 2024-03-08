package edu.upc.talent.swqa.campus.test.utils;

import edu.upc.talent.swqa.campus.domain.User;
import static edu.upc.talent.swqa.util.Utils.now;
import static edu.upc.talent.swqa.util.Utils.setOf;

import java.time.temporal.ChronoUnit;
import java.util.Set;

public final class TestFixtures {

  private TestFixtures() {}

  public static final CampusAppState defaultInitialState;
  public static final Group swqa = new Group("1", "swqa");

  public static final User johnDoe = new User(
        "1",
        "John",
        "Doe",
        "john.doe@example.com",
        "student",
        "swqa",
        now().minus(200, ChronoUnit.DAYS)
  );

  public static final User janeDoe = new User(
        "2",
        "Jane",
        "Doe",
        "jane.doe@example.com",
        "student",
        "swqa",
        now().minus(100, ChronoUnit.DAYS)
  );

  public static final User mariahHairam = new User(
        "3",
        "Mariah",
        "Harris",
        "mariah.hairam@example.com",
        "teacher",
        "swqa",
        now().minus(300, ChronoUnit.DAYS)
  );

  static {
    defaultInitialState = new CampusAppState(
          new UsersRepositoryState(
                setOf(
                      johnDoe,
                      janeDoe,
                      mariahHairam
                ),
                setOf(swqa)
          ),
          setOf()
    );
  }


}
