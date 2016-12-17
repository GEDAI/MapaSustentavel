package br.edu.faifaculdades.mapasustentavel.model;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import br.edu.faifaculdades.mapasustentavel.model.interfaces.MarcacaoEventListener;

/**
 * Created by Guilherme Schenckel on 15/11/2016.
 */

public class MapaDatabase {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private final String firebaseURL = "https://genuine-octagon-138523.firebaseio.com/";

    public String postarMarcacao(Marcacao marcacao) {
        DatabaseReference dataRef = database.getReferenceFromUrl(firebaseURL);
        String key = dataRef.child("marcacoes").push().getKey();

        Map<String, Object> updateChildren = new HashMap<>();
        updateChildren.put("/marcacoes/" + key, marcacao.toMap());

        dataRef.updateChildren(updateChildren);

        return key;
    }

    public void recuperarMarcacao(String IdMarcacao, final MarcacaoEventListener marcacaoEventListener) {
        DatabaseReference dataRef = database.getReferenceFromUrl(firebaseURL);
        final Marcacao[] result = new Marcacao[1];

        dataRef.child("marcacoes").child(IdMarcacao).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        marcacaoEventListener.OnGetMarcacaoSuccess(
                                dataSnapshot.getValue(Marcacao.class)
                        );
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        marcacaoEventListener.OnGetMarcacaoError(databaseError);
                    }
                }
        );
    }

    public void recuperarMarcacoes(ChildEventListener childEventListener) {
        DatabaseReference databaseReference = database.getReferenceFromUrl(firebaseURL);

        Query query = databaseReference.child("marcacoes");

        query.addChildEventListener(childEventListener);
    }
}
