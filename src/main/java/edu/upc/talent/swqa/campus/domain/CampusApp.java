package edu.upc.talent.swqa.campus.domain;

import java.util.UUID;

public final class CampusApp {

  private final UsersRepository usersRepository;
  private final EmailSender emailSender;

  public CampusApp(final UsersRepository usersRepository, final EmailSender emailSender) {
    this.usersRepository = usersRepository;
    this.emailSender = emailSender;
  }

  public String createGroup(final String name) {
    final var id = UUID.randomUUID().toString();
    usersRepository.createGroup(id, name);
    return id;
  }

  public String createUser(
        final String name,
        final String surname,
        final String email,
        final String role,
        final String groupName
  ) {
    final var id = UUID.randomUUID().toString();
    usersRepository.createUser(id, name, surname, email, role, groupName);
    return id;
  }

  public void sendMailToGroupRole(
        final String groupName,
        final String onlyRole,
        final String subject,
        final String body
  ) {
    final var users = usersRepository.getUsersByGroupAndRole(groupName, onlyRole);
    users.forEach(u -> emailSender.sendEmail(u.email(), subject, body));
  }

  public void sendMailToGroup(final String groupName, final String subject, final String body) {
    final var users = usersRepository.getUsersByGroup(groupName);
    users.forEach(u -> emailSender.sendEmail(u.email(), subject, body));
  }

}
