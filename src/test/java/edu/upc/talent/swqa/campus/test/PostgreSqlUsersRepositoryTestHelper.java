package edu.upc.talent.swqa.campus.test;

import edu.upc.talent.swqa.campus.domain.User;
import edu.upc.talent.swqa.campus.infrastructure.UsersDb;
import edu.upc.talent.swqa.campus.test.utils.Group;
import edu.upc.talent.swqa.campus.test.utils.UsersRepositoryState;
import edu.upc.talent.swqa.jdbc.Database;
import static edu.upc.talent.swqa.jdbc.Param.p;

public final class PostgreSqlUsersRepositoryTestHelper {

  private PostgreSqlUsersRepositoryTestHelper() {
  }

  public static void setInitialState(final Database db, final UsersRepositoryState state) {
    db.update(UsersDb.groupsTableDml);
    db.update(UsersDb.usersTableDml);
    state.groups().forEach(g -> db.update("insert into groups (id, name) values (?, ?)", p(g.id()), p(g.name())));
    state.users().forEach(g -> db.update(
          "insert into users (id, name, surname, email, role, group_id) " +
          "values (?, ?, ?, ?, ?, (select id from groups where name = ?))",
          p(g.id()),
          p(g.name()),
          p(g.surname()),
          p(g.email()),
          p(g.role()),
          p(g.groupName())
    ));
  }


  public static UsersRepositoryState getFinalState(final Database db) {
    return new UsersRepositoryState(
          db.selectToSet(
                """
                select u.id, u.name, u.surname, u.email, u.role, g.name
                from users u join groups g on u.group_id = g.id""",
                (rs) -> new User(
                      rs.getString(1),
                      rs.getString(2),
                      rs.getString(3),
                      rs.getString(4),
                      rs.getString(5),
                      rs.getString(6)
                )
          ),
          db.selectToSet(
                "SELECT id, name FROM groups",
                (rs) -> new Group(rs.getString(1), rs.getString(2))
          )
    );
  }

}
