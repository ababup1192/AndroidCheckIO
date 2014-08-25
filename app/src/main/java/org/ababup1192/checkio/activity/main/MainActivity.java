package org.ababup1192.checkio.activity.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.ababup1192.checkio.R;
import org.ababup1192.checkio.activity.main.service.checkio.CheckIOIntentService;
import org.ababup1192.checkio.activity.main.service.location.LocationIntentService;
import org.ababup1192.checkio.activity.main.service.location.LocationNotificationIntentService;
import org.ababup1192.checkio.activity.maps.checkpoint.CheckpointMapsActivity;
import org.ababup1192.checkio.setting.LocationSharedpreferencesSetting;
import org.ababup1192.checkio.util.GooglePlayServicesHelper;
import org.ababup1192.checkio.util.ServiceRepeater;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind layout widgets
        Button checkPointSettingButton = (Button) findViewById(R.id.button_checkpoint);
        Button checkIOButton = (Button) findViewById(R.id.button_check_io);
        ToggleButton locationToggleButton = (ToggleButton) findViewById(R.id.toggle_button_location);
        ToggleButton notificationToggleButton = (ToggleButton) findViewById(R.id.toggle_button_notification);

        // Service Repeater setting
        final ServiceRepeater locationRepeater = new ServiceRepeater(this, new Intent(this, LocationIntentService.class));
        final ServiceRepeater locationNotificationRepeater = new ServiceRepeater(this, new Intent(this, LocationNotificationIntentService.class));

        checkPointSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CheckpointMapsActivity.createIntent(getApplicationContext()));
            }
        });

        checkIOButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckIOIntentService.class);
                startService(intent);
            }
        });

        locationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    locationRepeater.start(5);
                } else {
                    locationRepeater.stop();
                }
            }
        });

        notificationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    locationNotificationRepeater.start(1, 5);
                } else {
                    locationNotificationRepeater.stop();
                }
            }
        });

        // GooglePlayServiceエラー検出
        GooglePlayServicesHelper.checkGooglePlayServicesAvailable(this, this);

        // チェックポイントの半径は、100メートルに設定
        LocationSharedpreferencesSetting.setCheckPointRadius(this, 100.0f);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
