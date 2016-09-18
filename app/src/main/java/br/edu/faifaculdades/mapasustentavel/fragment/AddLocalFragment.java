package br.edu.faifaculdades.mapasustentavel.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.edu.faifaculdades.mapasustentavel.R;
import br.edu.faifaculdades.mapasustentavel.dao.MapaSustentavelDAO;
import br.edu.faifaculdades.mapasustentavel.model.Marcador;


/**
 * Formulário para adicionar marcador no mapa.
 */
public class AddLocalFragment extends Fragment {

    final static String ARG_MARCADOR_ID = "marcador_id";

    private long marcador_id = -1;

    private Button btnAdicionar;
    private EditText txtTitulo;
    private EditText txtDescricao;
    private String categoria;
    private MapaSustentavelDAO dao;
    private View view;

    private static GoogleMap mMap;
    private static LatLng localizacao;

    private OnAddLocalListener mListener;

    public AddLocalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mMap Parameter 1.
     * @param localizacao Parameter 2.
     * @return A new instance of fragment AddLocalFragment.
     */
    public static AddLocalFragment newInstance(GoogleMap mMap, LatLng localizacao) {
        AddLocalFragment fragment = new AddLocalFragment();
        AddLocalFragment.mMap = mMap;
        AddLocalFragment.localizacao = localizacao;
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

        View rootView = inflater.inflate(R.layout.fragment_add_local, container, false);

        txtTitulo = (EditText) rootView.findViewById(R.id.txtTitulo);
        txtDescricao = (EditText) rootView.findViewById(R.id.txtDescricao);

        btnAdicionar = (Button) rootView.findViewById(R.id.btnAdicionar);

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
                AddLocalFragment.this.categoria = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Tratar evento de adicionar marcador no mapa
        btnAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Marcador marcador = new Marcador();

                marcador.setLatitude(AddLocalFragment.localizacao.latitude);
                marcador.setLongitude(AddLocalFragment.localizacao.longitude);
                marcador.setTitulo(txtTitulo.getText().toString());
                marcador.setDescricao(txtDescricao.getText().toString());
                marcador.setCategoria(categoria);

                long resultado;

                //if(marcador_id == -1){
                    resultado = dao.inserir(marcador);
                //}else{
                //    resultado = dao.atualizar(livro, livro_id);
                //}

                if(resultado != -1 ){
                    Toast.makeText(AddLocalFragment.this.getContext(), getString(R.string.marcador_salvo), Toast.LENGTH_SHORT).show();

                    // Notify the parent activity of selected item
                    mListener.onMarcadorAdicionarSelected();
                }else{
                    Toast.makeText(AddLocalFragment.this.getContext(), getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddLocalListener) {
            mListener = (OnAddLocalListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddLocalListener");
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
    public interface OnAddLocalListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onMarcadorAdicionarSelected();
    }
}
