package edu.upc.talent.swqa.campus.domain;

import java.util.List;

public interface UsersRepository {
  void createUser(
        final String id,
        final String name,
        final String surname,
        final String email,
        final String role,
        final String groupName
  );

  void createGroup(String id, final String name);

  List<User> getUsersByGroupAndRole(final String groupName, final String onlyRole);

  List<User> getUsersByGroup(final String groupName);
}
