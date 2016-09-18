package br.edu.faifaculdades.mapasustentavel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "MapaSustentavel";
    private static int VERSAO = 1;

    public static class Marcador {
        public static final String TABELA = "MARCADORES";
        public static final String _ID = "_ID";
        public static final String LATITUDE = "LATITUDE";
        public static final String LONGITUDE = "LONGITUDE";
        public static final String TITULO = "TITULO";
        public static final String DESCRICAO = "DESCRICAO";
        public static final String CATEGORIA = "CATEGORIA";
        public static final String CONTADOR = "CONTADOR";
        public static final String PATH_IMAGE = "PATH_IMAGE";


        public static final String[] COLUNAS = new String[]{
                _ID, LATITUDE, LONGITUDE, TITULO, DESCRICAO, CATEGORIA, CONTADOR, PATH_IMAGE};
    }

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MARCADORES (" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LATITUDE DOUBLE NOT NULL," +
                "LONGITUDE DOUBLE NOT NULL," +
                "TITULO VARCHAR(100)," +
                "DESCRICAO VARCHAR(255)," +
                "CATEGORIA VARCHAR(45)," +
                "CONTADOR INTEGER NOT NULL DEFAULT 0," +
                "PATH_IMAGE VARCHAR(125));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}