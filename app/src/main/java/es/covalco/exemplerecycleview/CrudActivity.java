package es.covalco.exemplerecycleview;

import android.app.Activity;
import android.os.Bundle;
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

import java.util.ArrayList;

/**
 * Exemple de manteniment de dades en formulari i gravació en BD SQLite
 */
public class CrudActivity extends Activity
                       implements View.OnClickListener,
                                  AdapterView.OnItemSelectedListener {
  // Declaramos los elementos de la interfaz
  private Button btnCrear;
  private Button btnVer;
  private Button btnEliminar;
  private EditText editNombre;
  private EditText editComentario;
  private TextView txtNombre;
  private TextView txtComentario;

  // Declaración del spinner y su Adapter
  private Spinner spinComentarios;
  private ArrayAdapter spinnerAdapter;

  // Lista de comentarios y comentario actual
  private ArrayList<Comentario> lista;
  private Comentario comentario;

  // Controlador de base de datos
  private MyOpenHelper db;

  private AwesomeValidation awesomeValidation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crud);

    awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    // Obtenim tots els controls
    editNombre      = (EditText) findViewById(R.id.editNombre);
    editComentario  = (EditText) findViewById(R.id.editComentario);
    txtNombre       = (TextView) findViewById(R.id.txtNombre);
    txtComentario   = (TextView) findViewById(R.id.txtComentario);

    txtNombre.setEnabled(false);
    txtComentario.setEnabled(false);

    btnCrear     = (Button) findViewById(R.id.btnCrear);
    btnVer       = (Button) findViewById(R.id.btnVer);
    btnEliminar  = (Button) findViewById(R.id.btnEliminar);
    btnCrear.setOnClickListener(this);
    btnVer.setOnClickListener(this);
    btnEliminar.setOnClickListener(this);

    // Inciamos el controlador de la base de datos
    db = new MyOpenHelper(this);

    // Iniciamos el spinner y la lista de comentarios
    spinComentarios = (Spinner) findViewById(R.id.spinComentarios);
    lista = db.getComments();

    // Creamos el adapter y lo asociamos al spinner
    spinnerAdapter = new ArrayAdapter<Comentario>(this,
                                       android.R.layout.simple_spinner_dropdown_item,
                                      lista);
    spinComentarios.setAdapter(spinnerAdapter);
    spinComentarios.setOnItemSelectedListener(this);

    // Añadimos la validación al formulario
    addValidationToViews();
  }


  @Override
  public void onClick(View view) {
    // Acciones de cada botón
    switch (view.getId()) {
      // Botó Crear
      case R.id.btnCrear:
        if (awesomeValidation.validate()) {
          // Insertamos un nuevo elemento en base de datos
          db.insertar(editNombre.getText().toString(),
                      editComentario.getText().toString());
          // Actualitzem el spinner de comentaris
          refrescaSpinner();
          // Here, we are sure that form is successfully validated. So, do your stuffs now...
          Toast.makeText(this,
                        "Form validated Successfully",
                        Toast.LENGTH_LONG).show();
          // Limpiamos el formulario
          editNombre.setText("");
          editComentario.setText("");
        }
        break;
      // Botó Ver
      case R.id.btnVer:
        awesomeValidation.clear();
        if (comentario != null) {
          txtNombre.setText(comentario.getNombre());
          txtComentario.setText(comentario.getComentario());
        }
        break;
      // Botó Eliminar
      case R.id.btnEliminar:
        awesomeValidation.clear();
        // Si hay algun comentario seleccionado lo borramos de la base de datos y actualizamos el spinner
        if (comentario != null) {
          db.borrar(comentario.getId());
          refrescaSpinner();
          // Limpiamos los datos del panel inferior
          txtNombre.setText("");
          txtComentario.setText("");
          comentario = null;
        }
        break;
    }
  }

  private void addValidationToViews() {
    awesomeValidation.addValidation(this, R.id.editNombre,
                                      RegexTemplate.NOT_EMPTY, R.string.invalid_nombre);
    awesomeValidation.addValidation(this, R.id.editComentario,
                                      RegexTemplate.NOT_EMPTY, R.string.invalid_comentario);
  }

  private void refrescaSpinner() {
    // Actualizamos la llista de comentarios
    lista = db.getComments();
    // Actualizamos el adapter y lo asociamos de nuevo al spinner
    spinnerAdapter = new ArrayAdapter<Comentario>(this,
            android.R.layout.simple_spinner_dropdown_item,
            lista);
    spinComentarios.setAdapter(spinnerAdapter);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
    if (adapterView.getId() == R.id.spinComentarios) {
      // SI hay elementos en la base de datos, establecemos el comentario actual a partir
      // del indice del elemento seleccionado en el spinner.
      if (lista.size() > 0) {
        comentario = lista.get(position);
      }
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}
