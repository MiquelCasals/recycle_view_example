package es.covalco.exemplerecycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerViewUser;
  private RecyclerView.Adapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //
    Toast toast=Toast.makeText(getApplicationContext(),
            BuildConfig.FLAVOR + " " +
            BuildConfig.ENDPOINT,
            Toast.LENGTH_LONG);
    toast.setMargin(50,50);
    toast.show();
    recyclerViewUser = (RecyclerView) findViewById(R.id.recyclerViewUser);

    // use this setting to improve performance if tou know that changes
    recyclerViewUser.setHasFixedSize(true);
    recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

    // specify an adapter with the list to show
    mAdapter = new UserAdapter(getData());
    recyclerViewUser.setAdapter(mAdapter);
  }

  // Create fake data for this example
  private List<UserModel> getData() {
    List<UserModel> userModels = new ArrayList<>();
    userModels.add(new UserModel("Gustavo"));
    userModels.add(new UserModel("Daniel"));
    userModels.add(new UserModel("Cecilia"));
    userModels.add(new UserModel("Diego"));
    userModels.add(new UserModel("Carlos"));
    userModels.add(new UserModel("Juan"));
    userModels.add(new UserModel("Pedro"));
    userModels.add(new UserModel("Josep"));
    userModels.add(new UserModel("Bonifaci"));
    userModels.add(new UserModel("Susana"));
    userModels.add(new UserModel("Raquel"));
    userModels.add(new UserModel("Noelia"));
    userModels.add(new UserModel("Alberto"));
    userModels.add(new UserModel("Gerard"));
    userModels.add(new UserModel("Jordi"));

    for(int i = 1; i < 15; i++) {
      userModels.add(new UserModel("Name " + i));
    }
    return userModels;
  }
}
