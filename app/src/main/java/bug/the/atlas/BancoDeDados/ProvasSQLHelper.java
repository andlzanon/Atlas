package bug.the.atlas.BancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static bug.the.atlas.BancoDeDados.DisciplinasSQLHelper.TABELA_DISCIPLINAS;

/**
 * Created by Andre on 11/12/2017.
 */

public class ProvasSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd_Provas";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_PROVAS = "provas";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_IDDIS = "id_dis";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LOCAL = "local";
    public static final String COLUNA_HORARIO = "horario";
    public static final String COLUNA_DATA = "data";
    public static final String COLUNA_PESO = "peso";
    public static final String COLUNA_NOTA = "nota";

    public ProvasSQLHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_PROVAS + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                COLUNA_IDDIS + " INTEGER NOT NULL, " +
                COLUNA_PESO + " REAL NOT NULL, " +
                COLUNA_NOTA + " REAL, " +
                COLUNA_NOME + " TEXT NOT NULL, " +
                COLUNA_LOCAL + " TEXT NOT NULL, " +
                COLUNA_HORARIO + " TEXT NOT NULL, " +
                COLUNA_DATA + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + COLUNA_IDDIS + ") REFERENCES " + TABELA_DISCIPLINAS + "(" + COLUNA_ID + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROVAS);
        onCreate(db);
    }
}
