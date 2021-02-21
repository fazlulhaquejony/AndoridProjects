package com.example.fuhad.mycitymyenvironment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap map;
    AutoCompleteTextView autoText;
    ImageButton imageButton;
    TextView airPollution;
    TextView waterPollution;
    TextView garbage;
    private String[] problems = { "Air Pollution", "Drinking Water Pollution and Inaccessibility", "Dissatisfaction with Garbage Disposal", "Dirty and Untidy", "Noise and Light Pollution","Water Pollution","Dissatisfaction to Spend Time in the City","Dissatisfaction with Green and Parks in the City","Deforestation","Indestrial pollution","Show All" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);
//        map.getUiSettings().setMyLocationButtonEnabled(false);
        autoText=(AutoCompleteTextView)v.findViewById(R.id.problemSearch);
        imageButton=(ImageButton) v.findViewById(R.id.problemSearchButton);
        airPollution=(TextView)v.findViewById(R.id.AirPollution);
        airPollution.setBackgroundColor((int) BitmapDescriptorFactory.HUE_AZURE);
        waterPollution=(TextView)v.findViewById(R.id.WaterPollution);
        waterPollution.setBackgroundColor((int)BitmapDescriptorFactory.HUE_BLUE);
        garbage=(TextView)v.findViewById(R.id.Garbage);
        garbage.setBackgroundColor((int)BitmapDescriptorFactory.HUE_GREEN);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(23.7636894,90.4330693), 10);
        map.animateCamera(cameraUpdate);


        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                problems);
        autoText.setAdapter(adapter);
        autoText.setThreshold(1);

        showAll(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAll(false);
                String problemType= String.valueOf(autoText.getText());
                if(problemType=="Air Pollution")
                {
                    pinPoint(23.7443729, 90.3706173, "Air Pollution");
                    pinPoint(23.757130, 90.359877,"Air Pollution");
                    pinPoint(23.757130, 90.359877, "Air Pollution");
                    pinPoint(23.757130, 90.359877,"Air Pollution");
                    pinPoint(23.757130, 90.359877,"Air Pollution");
                }
                if(problemType=="Water Pollution")
                {
                    pinPoint(23.7421888, 90.3709513, "Water Pollution");
                    pinPoint(23.704461, 90.407568,"Water Pollution");
                    pinPoint(23.709871, 90.393279,"Water Pollution");

                }
                if(problemType=="Garbage")
                {
                    pinPoint(23.726795, 90.384293,"Garbage");
                    pinPoint(23.743146, 90.373500,"Garbage");
                }
            }
        });




        return v;
    }

    public void showAll(boolean bool)
    {
        if (bool==true)
        {
            pinPoint(23.7443729, 90.3706173, "Air Pollution");
            pinPoint(23.757130, 90.359877,"Air Pollution");
            pinPoint(23.757130, 90.359877,"Air Pollution");
            pinPoint(23.757130, 90.359877,"Air Pollution");
            pinPoint(23.757130, 90.359877,"Air Pollution");
            pinPoint(23.7421888, 90.3709513, "Water Pollution");
            pinPoint(23.704461, 90.407568,"Water Pollution");
            pinPoint(23.709871, 90.393279,"Water Pollution");
            pinPoint(23.726795, 90.384293,"Garbage");
            pinPoint(23.743146, 90.373500,"Garbage");
        }
        else
        {

        }

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void pinPoint(double latitude , double longitude , String problem)
    {
        if(problem=="Air Pollution")
        {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude,longitude))
                    .title(problem))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if(problem=="Garbage")
        {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude,longitude))
                    .title(problem))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        }
        if(problem=="Water Pollution")
        {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude,longitude))
                    .title(problem))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        /*map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);*/


    }
}
