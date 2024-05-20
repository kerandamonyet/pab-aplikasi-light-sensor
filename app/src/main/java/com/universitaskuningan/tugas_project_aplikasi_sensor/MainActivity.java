package com.universitaskuningan.tugas_project_aplikasi_sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightTextView;
    private TextView warningTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Inisialisasi Light Sensor
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // TextView untuk menampilkan nilai sensor cahaya
        lightTextView = findViewById(R.id.lightTextView);

        // TextView untuk menampilkan warning teks
        warningTextView = findViewById(R.id.warningTextView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Daftarkan listener ketika aplikasi di-resume
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hapus listener ketika aplikasi di-pause
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            // Dapatkan nilai sensor cahaya
            float lightValue = event.values[0];
            // Tampilkan nilai sensor cahaya di TextView
            lightTextView.setText("Nilai Sensor Cahaya: " + lightValue);
            if (lightValue == 0) {
                // Tampilkan pesan peringatan
                warningTextView.setText("Cahaya terlalu rendah, hati-hati agar tidak merusak mata!");
                warningTextView.setVisibility(TextView.VISIBLE);
            } else {
                // Sembunyikan pesan peringatan jika nilai cahaya lebih dari 0
                warningTextView.setVisibility(TextView.GONE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Tidak digunakan dalam contoh ini
    }
}
