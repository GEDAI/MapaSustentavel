package br.edu.faifaculdades.mapasustentavel.model.interfaces;

import com.google.firebase.database.DatabaseError;
import br.edu.faifaculdades.mapasustentavel.model.Marcacao;

/**
 * Created by Guilherme Schenckel on 30/11/2016.
 */

public interface MarcacaoEventListener {
    void OnGetMarcacaoSuccess(Marcacao marcacao);

    void OnGetMarcacaoError(DatabaseError databaseError);
}
