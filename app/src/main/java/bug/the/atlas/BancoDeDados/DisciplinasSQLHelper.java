package bug.the.atlas.BancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andre on 11/12/2017.
 */

public class DisciplinasSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd_Disciplinas";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_DISCIPLINAS = "disciplinas";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_MEDIA = "media";
    public static final String COLUNA_REC = "notaRec";
    public static final String COLUNA_AULAS = "aulas";
    public static final String COLUNA_HORASSEM = "horassem";
    public static final String COLUNA_FALTAS = "faltas";
    public static final String COLUNA_NOTAATUAL = "notaAtual";

    public DisciplinasSQLHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BANCO);
    }

    /**
     * Cria banco de dados de disciplinas
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_DISCIPLINAS + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY NOT NULL," +
                COLUNA_NOME + " TEXT NOT NULL, " +
                COLUNA_MEDIA + " REAL NOT NULL, " +
                COLUNA_REC + " REAL NOT NULL, " +
                COLUNA_AULAS + " INTEGER NOT NULL, " +
                COLUNA_HORASSEM + " INTEGER NOT NULL, " +
                COLUNA_FALTAS + " INTEGER NOT NULL, " +
                COLUNA_NOTAATUAL + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABELA_DISCIPLINAS);
        onCreate(db);
    }
}
