package br.edu.faifaculdades.mapasustentavel.model.interfaces;

import com.google.firebase.database.DatabaseError;

import java.util.List;
import br.edu.faifaculdades.mapasustentavel.model.Marcacao;

/**
 * Created by Guilherme Schenckel on 07/12/2016.
 */

public interface MarcacoesEventListener {
    void OnGetMarcacoesSuccess(List<Marcacao> marcacoes);

    void OnGetMarcacoesError(DatabaseError databaseError);
}
