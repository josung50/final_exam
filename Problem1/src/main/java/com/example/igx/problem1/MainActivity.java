package com.example.igx.problem1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.LocationListener;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    // 위치정보 읽어오기
    LocationManager mlocationManager;

    double lat = 0.0;
    double lng = 0.0;

    // 권한요청
    int FLAG1 = 0;
    int FLAG2 = 0;

    TextView text_selectedData = null;
    TextView text_selectedType = null;
    EditText edit_phoneNumber = null;

    SensorManager sensorManager;
    Sensor Gravity;
    Sensor Accelator;

    String stringsensor1;
    String stringsensor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
        Button btn_getSensors = (Button) findViewById(R.id.btn_getSensors);
        Button btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        text_selectedData = (TextView) findViewById(R.id.text_selectedData);
        text_selectedType = (TextView) findViewById(R.id.text_selectedType);
        edit_phoneNumber = (EditText) findViewById(R.id.edit_phoneNumber);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Accelator = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // 권한
        if ( (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)){


            // Should we show an explanation?
            if ( (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) && (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) ) {
                FLAG1 = 1;
                FLAG2 = 1;

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
                FLAG1 = 1;
                FLAG2 = 1;
            }
        }

        else {
            FLAG1 = 1;
            FLAG2 = 1;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,Gravity,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this,Accelator,SensorManager.SENSOR_DELAY_GAME);
    }

    // Location 버튼 클릭
    public void getLocation(View v) {
        text_selectedType.setText("Location clicked");
        mlocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (checkPermission()) {
            mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mlocationlistener);
            mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mlocationlistener);
        }
        if(lat == 0.0 || lng == 0.0)
            Toast.makeText(this, "위치를 찾을 수 없습니다. 다시 시도하세요.(3,4번 클릭..)" , Toast.LENGTH_LONG).show();
        else
            text_selectedData.setText("Lat:" + lat + "   Lng:"  + lng );
    }

    // send 버튼 //
    public void sendMessage(View v) {
        String temp = edit_phoneNumber.getText().toString();
        Toast.makeText(this, temp + " 로 전송" , Toast.LENGTH_LONG).show();
    }

    public void gsensor(View v) {
        text_selectedType.setText("Sensors clicked");
        text_selectedData.setText(stringsensor1 + stringsensor2);
    }
        /*btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType
                mlocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if(checkPermission()){
                    mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mlocationlistener);
                    mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1,mlocationlistener);
                }

            }
        });*/

        /*btn_getSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        /*btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    private boolean checkPermission() {
        if(FLAG1 == 1 && FLAG2 == 1)
            return true;
        else return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    FLAG2 = 1;
                    FLAG1 = 1;

                } else {

                }
                return;
            }

        }
    }

    LocationListener mlocationlistener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            stringsensor1 = "GRAVITY\n";
            stringsensor1 += "x:" + event.values[0] + " ";
            stringsensor1 += "y:" + event.values[1] + " ";
            stringsensor1 += "z" + event.values[2] + " ";
            stringsensor1 += "\n";
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            stringsensor2 = "ACCELEROMETER\n";
            stringsensor2 += "x:" + event.values[0] + " ";
            stringsensor2 += "y:" + event.values[1] + " ";
            stringsensor2 += "z" + event.values[2] + " ";
        }
        return;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
