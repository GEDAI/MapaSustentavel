package br.edu.faifaculdades.mapasustentavel.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import java.util.List;

import br.edu.faifaculdades.mapasustentavel.R;
import br.edu.faifaculdades.mapasustentavel.dao.MapaSustentavelDAO;
import br.edu.faifaculdades.mapasustentavel.model.Marcador;


public class GMapsFragment extends MapUtils
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    private List<Marcador> marcadores;
    private MapaSustentavelDAO dao;
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

        this.dao = new MapaSustentavelDAO(this.getContext());

        this.marcadores = this.dao.buscarMarcadores();

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

        this.setUpMap();
        this.addMarcadores();
    }

    @Override
    public void onMapLongClick(LatLng localizacao) {

        final MarcadorFragment addLocalFragment = new MarcadorFragment().newInstance(mMap, localizacao);

        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, addLocalFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "Centralizado no local atual.", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void addMarcadores(){

        for (int i = 0; i < this.marcadores.size(); i++) {

            LatLng posicao = new LatLng(marcadores.get(i).getLatitude(), marcadores.get(i).getLongitude());

            String titulo = marcadores.get(i).getTitulo();

            final BitmapDescriptor bitmapDescriptor;

            if (marcadores.get(i).getCategoria() != null && marcadores.get(i).getCategoria().equals("Entulho")) {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            } else {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            }

            mMap.addMarker(new MarkerOptions()
                    .position(posicao)
                    .draggable(true)
                    .title(titulo)
                    .snippet(marcadores.get(i).getDescricao())
                    .icon(bitmapDescriptor));
        }
    }
}
