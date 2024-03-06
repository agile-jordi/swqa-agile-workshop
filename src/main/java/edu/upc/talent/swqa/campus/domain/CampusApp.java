package edu.upc.talent.swqa.campus.domain;

import edu.upc.talent.swqa.campus.infrastructure.PostgresQlUsersRepository;
import edu.upc.talent.swqa.campus.infrastructure.SmtpEmailService;
import edu.upc.talent.swqa.jdbc.Database;
import static edu.upc.talent.swqa.jdbc.HikariCP.getDataSource;

import java.util.List;

public final class CampusApp {

  private final UsersRepository usersRepository;
  private final EmailService emailService;

  public CampusApp(final UsersRepository usersRepository, final EmailService emailService) {
    this.usersRepository = usersRepository;
    this.emailService = emailService;
  }

  public void sendMailToGroup(final String groupName, final String subject, final String body) {
    final List<User> users = usersRepository.getUsersByGroup(groupName);
    users.forEach(u -> emailService.sendEmail(u, subject, body));
  }


  public void sendBirthdayEmails() {
    usersRepository.getUsersInBirthday().forEach(u ->
                                                       System.out.println("--------\n" +
                                                                          "to: " + u.email + "\n" +
                                                                          "subject: Happy Campus Birthday!\n" +
                                                                          "body:\n" + "Happy campus birthday " +
                                                                          u.name + " " + u.surname + "!\n" +
                                                                          "You have been with us since " +
                                                                          u.createdAt + "\n" +
                                                                          "--------\n")
    );
  }

  public void createUser(
        final String id,
        final String name,
        final String surname,
        final String email,
        final String role,
        final String groupName
  ) {
    usersRepository.createUser(id, name, surname, email, role, groupName);
  }

  public void createGroup(final String id, final String name) {
    usersRepository.createGroup(id, name);
  }

  public static CampusApp buildProductionApp() {
    final String dbHost = System.getenv("DATABASE_HOST");
    final String dbUser = System.getenv("DATABASE_USER");
    final String dbPassword = System.getenv("DATABASE_PASSWORD");
    final Database db = new Database(getDataSource("jdbc:postgresql://" + dbHost + "/", dbUser, dbPassword));
    final EmailService emailService = new SmtpEmailService();
    final UsersRepository usersRepository = new PostgresQlUsersRepository(db);
    return new CampusApp(usersRepository, emailService);
  }

}
