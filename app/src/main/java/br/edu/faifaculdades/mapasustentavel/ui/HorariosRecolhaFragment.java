package br.edu.faifaculdades.mapasustentavel.ui;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.edu.faifaculdades.mapasustentavel.R;

/**
 * Tela para visualizar caminhões e tempo estimado para recolha próximo a localização do usuário.
 */
public class HorariosRecolhaFragment extends MapUtils
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_horarios_recolha, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.horarios_recolha_map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMyLocationButtonClickListener(this);

        super.setUpMap();
        this.addMarcadores();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "Centralizado no local atual.", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void addMarcadores(){

        LatLng posicaoVidro = new LatLng(-27.5887152, -48.5073667);
        String tituloVidro = "Caminhão de recolha de lixo";
        String descricaoVidro = "Tempo estimado para chegada ao seu local: 7 minutos";
        BitmapDescriptor bitmapDescriptorVidro = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

        mMap.addMarker(new MarkerOptions()
                .position(posicaoVidro)
                .title(tituloVidro)
                .snippet(descricaoVidro)
                .icon(bitmapDescriptorVidro));

    }
}
