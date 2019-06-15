package es.covalco.exemplerecycleview.ExerciciPersones;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
// Llibreria de les Validacions de camps del formulari
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;

import java.util.ArrayList;

import es.covalco.exemplerecycleview.R;

public class PersonActivity extends Activity
                         implements View.OnClickListener,
                                    AdapterView.OnItemSelectedListener {
  // Declaramos los elementos de la interfaz
  private Button btnCrear;
  private Button btnVer;
  private Button btnEliminar;
  private EditText editNombre;
  private EditText editEmail;
  private EditText editPassport;
  private TextView txtNombre;
  private TextView txtEmail;
  private TextView txtPassport;

  // Declaración del spinner y su Adapter
  private Spinner spinPersonas;
  private ArrayAdapter spinnerAdapter;

  // Lista de comentarios y comentario actual
  private ArrayList<Person> lista;
  private Person persona;

  // Inciamos el controlador de la base de datos
  // Controlador de base de datos
  private OpenHelperPersones db;

  private AwesomeValidation awesomeValidation;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_person);

    awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    // Obtenim tots els controls
    editNombre      = findViewById(R.id.editNombreP);
    editEmail       = findViewById(R.id.editEmailP);
    editPassport    = findViewById(R.id.editPassport);
    txtNombre       = findViewById(R.id.txtNombreP);
    txtEmail        = findViewById(R.id.txtEmailP);
    txtPassport     = findViewById(R.id.txtPassport);

    txtNombre.setEnabled(false);
    txtEmail.setEnabled(false);
    txtPassport.setEnabled(false);

    btnCrear     = findViewById(R.id.btnCrearP);
    btnVer       = findViewById(R.id.btnVerP);
    btnEliminar  = findViewById(R.id.btnEliminarP);
    btnCrear.setOnClickListener(this);
    btnVer.setOnClickListener(this);
    btnEliminar.setOnClickListener(this);

    // Inciamos el controlador de la base de datos
    db = new OpenHelperPersones(this);

    // Iniciamos el spinner y la lista de comentarios
    spinPersonas = (Spinner) findViewById(R.id.spinPersonas);
    lista = db.getPersons();

    // Creamos el adapter y lo asociamos al spinner
    spinnerAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item,
            lista);
    spinPersonas.setAdapter(spinnerAdapter);
    spinPersonas.setOnItemSelectedListener(this);

    // Añadimos la validación al formulario
    addValidationToViews();

  }

  @Override
  public void onClick(View view) {
    // Acciones de cada botón
    switch (view.getId()) {
      // Botó Crear
      case R.id.btnCrearP:
        if (awesomeValidation.validate()) {
          // Insertamos un nuevo elemento en base de datos
          db.insertarPerson(editNombre.getText().toString(),
                            editEmail.getText().toString(),
                            editPassport.getText().toString());
          // Actualitzem el spinner de comentaris
          refrescaSpinner();
          // Here, we are sure that form is successfully validated. So, do your stuffs now...
          Toast.makeText(this,
                  "Formulari validat correctament",
                  Toast.LENGTH_LONG).show();
          // Limpiamos el formulario
          resetTextViews();
        }
        break;
      // Botó Ver
      case R.id.btnVerP:
        awesomeValidation.clear();
        if (persona != null) {
          txtNombre.setText(persona.getName());
          txtEmail.setText(persona.getEmail_id());
          txtPassport.setText(persona.getPassportDetails().getPassportNumber());
        }
        break;
      // Botó Eliminar
      case R.id.btnEliminarP:
        awesomeValidation.clear();
        // Si hay algun comentario seleccionado lo borramos de la base de datos y actualizamos el spinner
        if (persona != null) {
          db.borrarPerson(persona.getId());
          refrescaSpinner();
          // Limpiamos los datos del panel inferior
          resetTextViews();
        }
        break;
    }
  }

  /**
   * afegim les validacions als camps, Nom, Email i DNI de la persona
   * Nota: Sembla que no pots tenir més d'una validació assignada a un camp
   */
  private void addValidationToViews() {
    awesomeValidation.addValidation(this, R.id.editNombreP,
            RegexTemplate.NOT_EMPTY, R.string.invalid_nombre);
    //    awesomeValidation.addValidation(this, R.id.editEmailP,
    //            RegexTemplate.NOT_EMPTY, R.string.email_is_null);
    // Validació pròpia del email
    awesomeValidation.addValidation(this, R.id.editEmailP,
            new SimpleCustomValidation() {
              @Override
              public boolean compare(String email) {
                return(ValidacioEmail.validate(email));
              }
            }, R.string.email_KO);
    //    awesomeValidation.addValidation(this, R.id.editPassport,
    //            RegexTemplate.NOT_EMPTY, R.string.passport_is_null);
    // Validació pròpia del DNI: (passport_number)
    awesomeValidation.addValidation(this, R.id.editPassport,
            new SimpleCustomValidation() {
                @Override
                public boolean compare(String dni) {
                  boolean isOk = false;
                  try {
                    ValidacioDNI vDNI = new ValidacioDNI(dni);
                    isOk = vDNI.validar();
                  }
                  catch (Exception e) {
                    e.printStackTrace();
                  }
                  return(isOk);
                }
              },
            R.string.passport_KO);
  }

  private void refrescaSpinner() {
    // Actualizamos la llista de comentarios
    lista = db.getPersons();
    // Actualizamos el adapter y lo asociamos de nuevo al spinner
    spinnerAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item,
            lista);
    spinPersonas.setAdapter(spinnerAdapter);
  }

  private void resetTextViews() {
    txtNombre.setText(null);
    txtEmail.setText(null);
    txtPassport.setText(null);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
    if (adapterView.getId() == R.id.spinPersonas) {
      // SI hay elementos en la base de datos, establecemos el comentario actual a partir
      // del indice del elemento seleccionado en el spinner.
      if (lista.size() > 0) {
        persona = lista.get(position);
      }
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}
