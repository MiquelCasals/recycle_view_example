package es.covalco.exemplerecycleview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyOpenHelper extends SQLiteOpenHelper {
  private static final  String COMMENTS_TABLE_CREATE =
          "Create Table comments(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "user TEXT, " +
                                "comment TEXT)";
  private static final  String DB_NAME = "comments.sqlite";
  private static final int DB_VERSION = 1;
  private SQLiteDatabase db;

  public MyOpenHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    db = this.getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(COMMENTS_TABLE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  /**
   * Insertar un nuevo comentario
   * @param nombre
   * @param comentario
   */
  public void insertar(String nombre, String comentario) {
    ContentValues cv = new ContentValues();
    cv.put("user", nombre);
    cv.put("comment", comentario);
    db.insert("comments", null, cv);
  }

  /**
   * Borrar un comentario a partir de su Id
   * @param idComentario id del registre a eliminar
   */
  public void borrar(int idComentario) {
    String[] args = new String[] {String.valueOf(idComentario)};
    db.delete("comments", "_id = ?", args);
  }

  /**
   * Obtenim una llista amb tots els registres de la taula comments
   * @return llista amb tots els comentaris
   */
  public ArrayList<Comentario> getComments() {
    // Creamos un cursor
    ArrayList<Comentario> lista = new ArrayList<Comentario>();
    Cursor c = db.rawQuery("select _id, user, comment from comments", null);
    if (c != null && c.getCount() > 0) {
      c.moveToFirst();
      do {
        int id          = c.getInt(c.getColumnIndex("_id"));
        String user     = c.getString(c.getColumnIndex("user"));
        String comment  = c.getString(c.getColumnIndex("comment"));
        Comentario com  = new Comentario(id, user, comment);
        // Añadimos el comentario a la lista
        lista.add(com);
      } while (c.moveToNext());
    }
    // Tanquem el cursor
    c.close();
    return(lista);
  }
}
