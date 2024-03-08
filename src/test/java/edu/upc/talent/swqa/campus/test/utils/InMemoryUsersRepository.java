package edu.upc.talent.swqa.campus.test.utils;

import edu.upc.talent.swqa.campus.domain.BirthdayEmailData;
import edu.upc.talent.swqa.campus.domain.User;
import edu.upc.talent.swqa.campus.domain.UsersRepository;
import static edu.upc.talent.swqa.util.Utils.now;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryUsersRepository implements UsersRepository {

  private final UsersRepositoryState state;

  public InMemoryUsersRepository(final UsersRepositoryState state) {
    this.state = state;
  }

  @Override
  public void createUser(
        final String id,
        final String name,
        final String surname,
        final String email,
        final String role,
        final String groupName
  ) {
    final User user = new User(id, name, surname, email, role, groupName, now());
    state.users.add(user);
  }

  @Override
  public void createGroup(final String id, final String name) {
    state.groups.add(new Group(id, name));
  }


  @Override
  public List<User> getUsersByGroup(final String groupName) {
    return state.users.stream()
                      .filter(user -> user.groupName.equals(groupName))
                      .collect(Collectors.toList());
  }

  @Override public List<BirthdayEmailData> getUsersInBirthday() {
    final LocalDate today = LocalDate.now();
    return state.users.stream()
                      .filter(user -> {
                        final LocalDate createdDate = user.createdDate();
                        return createdDate.getDayOfMonth() == today.getDayOfMonth() &&
                               createdDate.getMonth() == today.getMonth();
                      })
                      .map(user -> new BirthdayEmailData(
                            user.email,
                            user.name,
                            user.surname,
                            user.createdAt.toString()
                      ))
                      .collect(Collectors.toList());
  }
}
