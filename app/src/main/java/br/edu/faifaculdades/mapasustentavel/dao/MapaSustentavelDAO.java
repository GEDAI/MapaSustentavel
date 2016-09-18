package br.edu.faifaculdades.mapasustentavel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.faifaculdades.mapasustentavel.DatabaseHelper;
import br.edu.faifaculdades.mapasustentavel.model.Marcador;

/**
 * Dao do MapaSustentável
 */
public class MapaSustentavelDAO {

    private DatabaseHelper helper;

    private SQLiteDatabase db;

    public MapaSustentavelDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close() {
        helper.close();
    }

    public List<Marcador> buscarMarcadores() {
        Cursor cursor = getDb().query(DatabaseHelper.Marcador.TABELA,
                DatabaseHelper.Marcador.COLUNAS,
                null, null, null, null, null);
        List<Marcador> marcadores = new ArrayList<Marcador>();
        while (cursor.moveToNext()) {
            Marcador marcador = criarMarcador(cursor);
            marcadores.add(marcador);
        }
        cursor.close();
        return marcadores;
    }

    public long inserir(Marcador marcador) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.Marcador.LATITUDE,
                marcador.getLatitude());

        values.put(DatabaseHelper.Marcador.LONGITUDE,
                marcador.getLongitude());

        values.put(DatabaseHelper.Marcador.TITULO,
                marcador.getTitulo());

        values.put(DatabaseHelper.Marcador.DESCRICAO,
                marcador.getDescricao());

        values.put(DatabaseHelper.Marcador.CATEGORIA,
                marcador.getCategoria());

        values.put(DatabaseHelper.Marcador.CONTADOR,
                marcador.getContador());

        values.put(DatabaseHelper.Marcador.PATH_IMAGE,
                marcador.getPathImagem());

        return getDb().insert(DatabaseHelper.Marcador.TABELA,
                null, values);
    }

    private Marcador criarMarcador(Cursor cursor) {
        Marcador marcador = new Marcador(

                cursor.getLong(cursor.getColumnIndex(
                        DatabaseHelper.Marcador._ID)),

                cursor.getDouble(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.LATITUDE)),

                cursor.getDouble(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.LONGITUDE)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.TITULO)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.DESCRICAO)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.CATEGORIA)),

                cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.CONTADOR)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Marcador.PATH_IMAGE))
        );
        return marcador;
    }

}
