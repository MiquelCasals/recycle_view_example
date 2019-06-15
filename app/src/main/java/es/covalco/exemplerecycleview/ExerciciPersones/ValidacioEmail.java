package es.covalco.exemplerecycleview.ExerciciPersones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validació d'una adreça de correu electrònic.
 * Utilitzem una regexp
 */
public class ValidacioEmail {

  private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  /**
   * Validació d'una adreça de correu electrònic
   * @param emailStr adreça de correu electrònic
   * @return Si és OK o no.
   */
  static boolean validate(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }
}
