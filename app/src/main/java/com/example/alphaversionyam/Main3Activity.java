package com.example.alphaversionyam;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Main3Activity extends FragmentActivity implements OnMapReadyCallback
 {
    Intent t;
    Location mlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getApplication().setTheme(R.style.secondTheme);
     fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
    }



     private void GetLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
        return;
    }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mlocation=location;
                    Toast.makeText(getApplicationContext(),mlocation.getLatitude()+""+mlocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(Main3Activity.this);
                }
            }
        });
    }

        @Override
        public void onMapReady (GoogleMap googleMap){
            LatLng latLng = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You Are Here");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6));
            googleMap.addMarker(markerOptions);

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Request_Code:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    GetLastLocation();
        }

    }

     @Override
     public boolean onCreatePanelMenu(int featureId, Menu menu) {
         getMenuInflater().inflate(R.menu.main, menu);
         return super.onCreatePanelMenu(featureId, menu);
     }

    @Override
    public boolean onMenuItemSelected(int featureID, @NonNull MenuItem item) {
        String s= item.getTitle().toString();
        t=new Intent(this, MainActivity.class);
        if (s.equals("Authentication")){
            t=new Intent(this, MainActivity.class);
            startActivity(t);}

        if (s.equals("Real Time Database")){
            t=new Intent(this, Main2Activity.class);
            startActivity(t);
        }
        if (s.equals("maps")){
            t=new Intent(this, Main3Activity.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }



     public void mapBTN(View view) {
        GetLastLocation();

    }
}
