package br.edu.faifaculdades.mapasustentavel.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import br.edu.faifaculdades.mapasustentavel.R;
import br.edu.faifaculdades.mapasustentavel.dao.MapaSustentavelDAO;
import br.edu.faifaculdades.mapasustentavel.model.MapaDatabase;
import br.edu.faifaculdades.mapasustentavel.model.Marcacao;
import br.edu.faifaculdades.mapasustentavel.model.Marcador;
import br.edu.faifaculdades.mapasustentavel.utils.PermissionUtils;


/**
 * Formulário para adicionar marcador no mapa.
 */
public class MarcadorFragment extends Fragment {

    final static String ARG_MARCADOR_ID = "marcador_id";

    private long marcador_id = -1;

    private Button btnAdicionar;
    private Button btnCancelar;
    private EditText txtTitulo;
    private EditText txtDescricao;
    private String categoria;
    private MapaSustentavelDAO dao;

    private static GoogleMap mMap;
    private static LatLng localizacao;

    private OnMarcadorListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mMap        Parameter 1.
     * @param localizacao Parameter 2.
     * @return A new instance of fragment MarcadorFragment.
     */
    public static MarcadorFragment newInstance(GoogleMap mMap, LatLng localizacao) {
        MarcadorFragment fragment = new MarcadorFragment();
        MarcadorFragment.mMap = mMap;
        MarcadorFragment.localizacao = localizacao;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dao = new MapaSustentavelDAO(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            marcador_id = savedInstanceState.getLong(ARG_MARCADOR_ID);
        }

        View rootView = inflater.inflate(R.layout.fragment_marcador, container, false);

        txtTitulo = (EditText) rootView.findViewById(R.id.txtTitulo);
        txtDescricao = (EditText) rootView.findViewById(R.id.txtDescricao);

        btnAdicionar = (Button) rootView.findViewById(R.id.btnAdicionar);
        btnCancelar = (Button) rootView.findViewById(R.id.btnCancelar);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.Lixo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.Lixo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MarcadorFragment.this.categoria = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Tratar evento de adicionar marcador no mapa
        btnAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                PermissionUtils.verifyLogin();

                double latitude = MarcadorFragment.localizacao.latitude;
                double longitude = MarcadorFragment.localizacao.longitude;

                Marcacao marcacao = new Marcacao("guilherme", latitude, longitude);
                marcacao.setCategoria(categoria);
                marcacao.setDescricao(txtDescricao.getText().toString());
                marcacao.setTitulo(txtTitulo.getText().toString());
                marcacao.setImagens("img1|img2|img3|img4|img5");

                MapaDatabase database = new MapaDatabase();
                String chave = database.postarMarcacao(marcacao);

                if (chave != null) {
                    Toast.makeText(MarcadorFragment.this.getContext(), getString(R.string.marcador_salvo), Toast.LENGTH_SHORT).show();

                    // Notify the parent activity of selected item
                    mListener.onMarcadorAcaoSelected();
                } else {
                    Toast.makeText(MarcadorFragment.this.getContext(), getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Tratar evento de cancelar adição de marcador no mapa
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Notify the parent activity of selected item
                mListener.onMarcadorAcaoSelected();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMarcadorListener) {
            mListener = (OnMarcadorListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMarcadorListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMarcadorListener {

        void onFragmentInteraction(Uri uri);

        void onMarcadorAcaoSelected();
    }
}
