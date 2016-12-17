package br.edu.faifaculdades.mapasustentavel.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import android.view.LayoutInflater;

public class GMapsFragment extends MapUtils
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    private List<Marcador> marcadores;
    private MapaSustentavelDAO dao;
    private String tipo_residuos = "";

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

        View rootView = inflater.inflate(R.layout.fragment_pontos_recolha, container, false);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.tipos_residuos);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.tipo_residuos_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GMapsFragment.this.tipo_residuos = parent.getItemAtPosition(position).toString();

                if (GMapsFragment.this.mMap != null) {
                    GMapsFragment.
                            this.addPontosRecolha();
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
        //SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

        super.setUpMap();
        this.addPontosRecolha();
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

    private void addPontosRecolha() {

        mMap.clear();
        //Marcador fixo de Vidro para exemplo
        if (this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Pontos de recolha de Vidro")) {
            LatLng posicaoVidro = new LatLng(-27.165234, -53.704610);
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
        if (this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Pontos de recolha de Metal")) {
            LatLng posicaoMetal = new LatLng(-27.172807, -53.711193);
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
        if (this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Pontos de recolha de Papel")) {
            LatLng posicaoPapel = new LatLng(-27.173560, -53.714720);
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
        if (this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Ponto de recolha de Plástico")) {
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
        //Denúncias marcadas pelos usuários
        if (this.tipo_residuos.equals("Sem Filtros") || this.tipo_residuos.equals("Pontos de Denúncias")) {
            this.addMarcadores();
        }


    }

    private void addMarcadores() {

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
