package es.covalco.exemplerecycleview.ExerciciPersones;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;


public class OpenHelperPersones extends SQLiteOpenHelper {

  private static final  String PERSON_TABLE_CREATE =
          "Create Table person(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              "name TEXT, " +
                              "email_id TEXT)";
  private static final  String PASSPORTDETAILS_TABLE_CREATE =
          "Create Table passport_details(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "passport_number TEXT, " +
                                        "person_id INTEGER, " +
                                        "foreign key (person_id) references person(_id) on delete cascade)";
  private static final  String DB_NAME = "persones.sqlite";
  private static final int DB_VERSION = 1;
  private SQLiteDatabase db;

  public OpenHelperPersones(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    Log.d(getClass().getName(), "OpenHelperPersones: entro");
    db = this.getWritableDatabase();
    Log.d(getClass().getName(), "OpenHelperPersones: surto");
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.d(getClass().getName(), "onCreate: entro");
    db.execSQL(PERSON_TABLE_CREATE);
    db.execSQL(PASSPORTDETAILS_TABLE_CREATE);
    Log.d(getClass().getName(), "onCreate: surto");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  /**
   * Insertar una nova persona
   * @param name Nom de la persona
   * @param emailId Email de la persona
   * @param passportNumber DNI de la persona
   */
  public void insertarPerson(String name,
                             String emailId,
                             String passportNumber) {
    ContentValues cv = new ContentValues();
    try {
      db.beginTransaction();
      cv.put("name", name);
      cv.put("email_id", emailId);
      long rowId = db.insert("person", null, cv);
      if (rowId > 0) {
        cv = new ContentValues();
        cv.put("passport_number", passportNumber);
        cv.put("person_id", getPersonByRowId(rowId).getId());
        db.insert("passport_details", null, cv);
        db.setTransactionSuccessful();
        Log.d(getClass().getName(), String.format("registre creat correctament. RowId: %d", rowId));
      }
    }
    catch (Exception e) {
      Log.e(getClass().getName(), "Error en insertarPerson");
      throw e;
    }
    finally {
      db.endTransaction();
    }
  }

  /**
   * Borrar un persona
   * S'elimina de "person" i de "passport_details"
   * @param idPerson id del registre a eliminar
   */
  public void borrarPerson(int idPerson) {
    String[] args = new String[] {String.valueOf(idPerson)};
    try {
      db.beginTransaction();
      db.delete("person", "_id = ?", args);
      db.setTransactionSuccessful();
      Log.d(getClass().getName(), String.format("registre eliminat amb id: %d", idPerson));
    }
    catch (Exception e) {
      Log.e(getClass().getName(), "error en borrarPerson: " + e.getStackTrace().toString());
      throw e;
    }
    finally {
      db.endTransaction();
    }
  }

  /**
   * Obtenim una llista amb tots els registres de la taula comments
   * @return llista amb tots els comentaris
   */
  public Person getPersonByRowId(long fila) {
    // Creamos un cursor
    Person persona = null;
    String select = "Select * From person Where rowid = ?";
    Cursor c = db.rawQuery(select, new String[] {String.valueOf(fila)});
    if (c != null && c.getCount() == 1) {
      c.moveToFirst();
      do {
        int id          = c.getInt(c.getColumnIndex("_id"));
        String name     = c.getString(c.getColumnIndex("name"));
        String emailId  = c.getString(c.getColumnIndex("email_id"));
        persona = new Person(id,name,emailId);
      }
      while (c.moveToNext());
    }
    // Tanquem el cursor
    c.close();
    return(persona);
  }

  /**
   * Obtenim una llista amb tots els registres de la taula comments
   * @return llista amb tots els comentaris
   */
  public ArrayList<Person> getPersons() {
    // Creem un cursor amb la join
    ArrayList<Person> lista = new ArrayList<Person>();
    String select = "select p._id, p.name, p.email_id, pd._id as pd_id, pd.passport_number, pd.person_id " +
                      "from person as p " +
                           "inner join passport_details as pd on pd.person_id = p._id " +
                     "order by 1";
    Cursor c = db.rawQuery(select, null);
    if (c != null && c.getCount() > 0) {
      c.moveToFirst();
      do {
        int id             = c.getInt(c.getColumnIndex("_id"));
        String name        = c.getString(c.getColumnIndex("name"));
        String emailId     = c.getString(c.getColumnIndex("email_id"));
        Person person = new Person(id,name,emailId);

        int pdId           = c.getInt(c.getColumnIndex("pd_id"));
        String passportNum = c.getString(c.getColumnIndex("passport_number"));
        int personId       = c.getInt(c.getColumnIndex("person_id"));
        PassportDetails passportDetails  = new PassportDetails(pdId, passportNum, personId);

        person.setPassportDetails(passportDetails);
        // Afegim la persona a la llista
        lista.add(person);
      } while (c.moveToNext());
    }
    // Tanquem el cursor
    c.close();
    return(lista);
  }

}
