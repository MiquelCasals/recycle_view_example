package es.covalco.exemplerecycleview;

public class Comentario {

  int id;
  String nombre;
  String comentario;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Comentario(int id, String nombre, String comentario) {
    this.id         = id;
    this.nombre     = nombre;
    this.comentario = comentario;
  }
}
