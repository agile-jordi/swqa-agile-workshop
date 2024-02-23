package edu.upc.talent.swqa.campus.test;

import edu.upc.talent.swqa.campus.domain.User;
import edu.upc.talent.swqa.campus.test.utils.CampusAppState;
import edu.upc.talent.swqa.campus.test.utils.Group;
import edu.upc.talent.swqa.campus.test.utils.UsersRepositoryState;

import java.util.Set;

public final class TestFixtures {

  private TestFixtures() {}

  public static final CampusAppState defaultInitialState = new CampusAppState(
        new UsersRepositoryState(
              Set.of(
                    new User("1", "John", "Doe", "john.doe@example.com", "student", "swqa"),
                    new User("2", "Jane", "Doe", "jane.doe@example.com", "student", "swqa"),
                    new User("3", "Mariah", "Harris", "mariah.hairam@example.com", "teacher", "swqa")
              ),
              Set.of(new Group("1", "swqa"))
        ),
        Set.of()
  );


}
