package edu.upc.talent.swqa.campus.test.utils;

import edu.upc.talent.swqa.campus.domain.EmailSender;

public class ConsoleEmailSender implements EmailSender {
  @Override public void sendEmail(final String email, final String subject, final String body) {
    System.out.println("--------\n" +
                       "to: " + email + "\n" +
                       "subject: " + subject + "\n" +
                       "body:\n" + body + "\n" +
                       "--------\n");
  }
}
