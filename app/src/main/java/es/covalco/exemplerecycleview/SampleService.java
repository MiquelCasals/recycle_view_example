package es.covalco.exemplerecycleview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

public class SampleService extends Service {
  private MediaPlayer player;

  public SampleService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    return(null);
  }

  @Override
  public void onCreate() {
    Toast.makeText(this, "Service was created.", Toast.LENGTH_LONG).show();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
    // This will play the ringtone continously until we stop the service.
    player.setLooping(true);
    // It will start the player
    player.start();
    Toast.makeText(this, "Service started.", Toast.LENGTH_LONG).show();
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    // Stopping the player when service is destroyed
    player.stop();
    Toast.makeText(this, "Service stopped.", Toast.LENGTH_LONG).show();
  }
}