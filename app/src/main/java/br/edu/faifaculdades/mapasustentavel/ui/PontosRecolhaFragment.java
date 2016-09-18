package br.edu.faifaculdades.mapasustentavel.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.edu.faifaculdades.mapasustentavel.DatabaseHelper;
import br.edu.faifaculdades.mapasustentavel.PermissionUtils;
import br.edu.faifaculdades.mapasustentavel.R;
import br.edu.faifaculdades.mapasustentavel.dao.MapaSustentavelDAO;
import br.edu.faifaculdades.mapasustentavel.model.Marcador;

/**
 * Tela com o mapa e os pontos para entrega voluntária de resíduos.
 */
public class PontosRecolhaFragment extends Fragment
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private String tipo_residuos = "";

    private GoogleMap mMap;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotificationFragment.
     */
    public static GMapsFragment newInstance() {
        GMapsFragment fragment = new GMapsFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pontos_recolha, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.tipos_residuos);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.tipo_residuos_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PontosRecolhaFragment.this.tipo_residuos = parent.getItemAtPosition(position).toString();

                if(PontosRecolhaFragment.this.mMap!= null) {
                    PontosRecolhaFragment.
                            this.addMarcadores();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.pontos_recolha_map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMyLocationButtonClickListener(this);

        this.setUpMap();
        this.addMarcadores();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((AppCompatActivity) this.getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "Centralizado no local atual.", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


    private void addMarcadores(){

        mMap.clear();
        //Marcador fixo de Vidro para exemplo
        if(this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Vidro")) {
            LatLng posicaoVidro = new LatLng(-27.5887152, -48.5073667);
            String tituloVidro = "Local para entrega voluntária de Vidro";
            String descricaoVidro = "Rua exemplo";
            BitmapDescriptor bitmapDescriptorVidro = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

            mMap.addMarker(new MarkerOptions()
                    .position(posicaoVidro)
                    .title(tituloVidro)
                    .snippet(descricaoVidro)
                    .icon(bitmapDescriptorVidro));
        }

        //Marcador fixo de Metal para exemplo
        if(this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Metal")) {
            LatLng posicaoMetal = new LatLng(-27.5897702, -48.5066697);
            String tituloMetal = "Local para entrega voluntária de Metal";
            String descricaoMetal = "Rua exemplo";
            BitmapDescriptor bitmapDescriptorMetal = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

            mMap.addMarker(new MarkerOptions()
                    .position(posicaoMetal)
                    .title(tituloMetal)
                    .snippet(descricaoMetal)
                    .icon(bitmapDescriptorMetal));
        }


        //Marcador fixo de Papel para exemplo
        if(this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Papel")) {
            LatLng posicaoPapel = new LatLng(-27.5908922, -48.5068197);
            String tituloPapel = "Local para entrega voluntária de Papel";
            String descricaoPapel = "Rua exemplo";
            BitmapDescriptor bitmapDescriptorPapel = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

            mMap.addMarker(new MarkerOptions()
                    .position(posicaoPapel)
                    .title(tituloPapel)
                    .snippet(descricaoPapel)
                    .icon(bitmapDescriptorPapel));
        }

        //Marcador fixo de Plástico para exemplo
        if(this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Plástico")) {
            LatLng posicaoPlastico = new LatLng(-27.5892282, -48.5057147);
            String tituloPlastico = "Local para entrega voluntária de Plástico";
            String descricaoPlastico = "Rua exemplo";
            BitmapDescriptor bitmapDescriptorPlastico = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

            mMap.addMarker(new MarkerOptions()
                    .position(posicaoPlastico)
                    .title(tituloPlastico)
                    .snippet(descricaoPlastico)
                    .icon(bitmapDescriptorPlastico));
        }

    }

    private void setUpMap() throws SecurityException {
        // Enable MyLocation Layer of Google Map
        enableMyLocation();

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
