package es.covalco.exemplerecycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PoiService extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_poi_service);
  }

  // Start Service
  // Cridat en el onclick del butó de la activity
  public void startService(View view) {
    startService(new Intent(this, SampleService.class));
  }
  // Stop the service
  // Cridat en el onclick del butó de la activity
  public void stopService(View view) {
    stopService(new Intent(this, SampleService.class));
  }
}
