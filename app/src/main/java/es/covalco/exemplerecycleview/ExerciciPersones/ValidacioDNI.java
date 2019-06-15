package es.covalco.exemplerecycleview.ExerciciPersones;

/**
 * Validació del DNI d'una persona
 */
public class ValidacioDNI {

  private String dni;

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public ValidacioDNI(String dni) {
    this.dni = dni;
  }


  /**
   * Funcio que ens diu si el DNI està format exclusivament per numeros
   * La substitueixo per String.matches(regexp expression)
   * @return
   */
/*
  private boolean soloNumeros() {
    String numero = "";
    String miDNI = "";
    String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

    for (int i = 0; i < dni.length() - 1; i++) {
      numero = dni.substring(i, i+1).;
      for (int j = 0; j < unoNueve.length; j++) {
        if (numero.equals(unoNueve[j])) {
          miDNI += unoNueve[j];
        }
      }
    }
    return(miDNI.length() == 8);
  }*/

  /**
   * A partir del DNI ens computa la lletra final.
   * @return
   */
  private String letraDNI() {
    int miDNI = Integer.parseInt(dni.substring(0,8));
    int resto = 0;
    String miLetra = "";
    String[] asignacionLetra = {"T","R","W","A","G","M","Y","F","P","D","X",
                                "B","N","J","Z","S","Q","V","H","L","C","K","E"};
    resto = (miDNI % 23);
    miLetra = asignacionLetra[resto];
    return(miLetra);
  }

  /**
   * Realitza la validació del DNI.
   * Ha de tenir la longitut de 9 caracters.
   * Els 8 primers han der ser numeros i l'ultim caràcter la lletra de control.
   * La lletra del DNI ha de conincidir amb la que calculo amb els 8 primers dígits.
   * @return
   */
  public boolean validar() {
    String letraMayuscula = "";
    if (dni.length() != 9 || !Character.isLetter(dni.charAt(8))) {
      return(false);
    }
    letraMayuscula = dni.substring(8).toUpperCase();
    boolean sonNomesNumeros = dni.substring(0,8).matches("\\d+");
    //return(soloNumeros() && letraDNI().equals(letraMayuscula));
    return(sonNomesNumeros && letraDNI().equals(letraMayuscula));
  }
}
