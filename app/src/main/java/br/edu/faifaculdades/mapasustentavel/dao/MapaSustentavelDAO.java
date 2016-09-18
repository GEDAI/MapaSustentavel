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
 *
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
//
//    public Livro buscarLivroPorId(Long id) {
//        Cursor cursor = getDb().query(DatabaseHelper.Livro.TABELA,
//                DatabaseHelper.Livro.COLUNAS,
//                DatabaseHelper.Livro._ID + " = ?",
//                new String[]{id.toString()},
//                null, null, null);
//        if (cursor.moveToNext()) {
//            Livro livro = criarLivro(cursor);
//            cursor.close();
//            return livro;
//        }
//
//        return null;
//    }

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

//    public long atualizar(Livro livro, Long id) {
//        ContentValues values = new ContentValues();
//
//        values.put(DatabaseHelper.Livro.TITULO,
//                livro.getTitulo());
//
//        values.put(DatabaseHelper.Livro.AUTOR,
//                livro.getAutor());
//
//        values.put(DatabaseHelper.Livro.RESUMO,
//                livro.getResumo());
//
//        values.put(DatabaseHelper.Livro.CLASSIFICACAO,
//                livro.getClassificacao());
//
//        values.put(DatabaseHelper.Livro.CUTTER,
//                livro.getCutter());
//
//        values.put(DatabaseHelper.Livro.OBSERVACAO,
//                livro.getObservacao());
//
//        values.put(DatabaseHelper.Livro.PATH_IMAGE,
//                livro.getPathImagem());
//
//        values.put(DatabaseHelper.Livro.AVALIACAO,
//                livro.getAvaliacao());
//
//        return getDb().update(DatabaseHelper.Livro.TABELA,
//                values, DatabaseHelper.Livro._ID + " = " + id, null);
//    }

//    public boolean removerLivro(Long id) {
//        String whereClause = DatabaseHelper.Livro._ID + " = ?";
//        String[] whereArgs = new String[]{id.toString()};
//        int removidos = getDb().delete(DatabaseHelper.Livro.TABELA,
//                whereClause, whereArgs);
//        return removidos > 0;
//    }

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
