package bug.the.atlas.BancoDeDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bug.the.atlas.Entidades.Disciplina;;

/**
 * Created by Andre on 11/12/2017.
 */

public class DisciplinasRepositorio {

    private DisciplinasSQLHelper helper;

    public DisciplinasRepositorio(Context context){
        helper = new DisciplinasSQLHelper(context);
    }


    public long inserir(Disciplina disciplina){
        SQLiteDatabase bd = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DisciplinasSQLHelper.COLUNA_NOME, disciplina.getNome());
        cv.put(DisciplinasSQLHelper.COLUNA_AULAS, disciplina.getAulasPorSemana());
        cv.put(DisciplinasSQLHelper.COLUNA_FALTAS, disciplina.getFaltas());
        cv.put(DisciplinasSQLHelper.COLUNA_HORASSEM, disciplina.getHorasPorAula());
        cv.put(DisciplinasSQLHelper.COLUNA_MEDIA, disciplina.getMedia());
        cv.put(DisciplinasSQLHelper.COLUNA_NOTAATUAL, disciplina.getNotaAtual());
        cv.put(DisciplinasSQLHelper.COLUNA_REC, disciplina.getNotaRec());

        long id = bd.insert(DisciplinasSQLHelper.TABELA_DISCIPLINAS, null, cv);
        bd.close();

        return id;
    }

    public int excluir(Disciplina disciplina){
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasModificas = db.delete(
                DisciplinasSQLHelper.TABELA_DISCIPLINAS,
                DisciplinasSQLHelper.COLUNA_ID + " = ?",
                new String[]{ String.valueOf(disciplina.getId())}
        );

        db.close();
        return linhasModificas;
    }

    public int atualizar(Disciplina disciplina){
        SQLiteDatabase bd = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DisciplinasSQLHelper.COLUNA_NOME, disciplina.getNome());
        cv.put(DisciplinasSQLHelper.COLUNA_AULAS, disciplina.getAulasPorSemana());
        cv.put(DisciplinasSQLHelper.COLUNA_FALTAS, disciplina.getFaltas());
        cv.put(DisciplinasSQLHelper.COLUNA_HORASSEM, disciplina.getHorasPorAula());
        cv.put(DisciplinasSQLHelper.COLUNA_MEDIA, disciplina.getMedia());
        cv.put(DisciplinasSQLHelper.COLUNA_NOTAATUAL, disciplina.getNotaAtual());
        cv.put(DisciplinasSQLHelper.COLUNA_REC, disciplina.getNotaRec());

        int linhasModificadas = bd.update(DisciplinasSQLHelper.TABELA_DISCIPLINAS,
                cv,
                DisciplinasSQLHelper.COLUNA_ID + " = ?",
                new String[]{String.valueOf(disciplina.getId())});

        return linhasModificadas;
    }

    public void listaDisciplinas(ArrayList<Disciplina> arrayList){
        SQLiteDatabase bd = helper.getReadableDatabase();

        arrayList.clear();

        try{
            String sql = "SELECT * FROM " + DisciplinasSQLHelper.TABELA_DISCIPLINAS;
            sql += " ORDER BY " + DisciplinasSQLHelper.COLUNA_NOME;

            Cursor cursor = bd.rawQuery(sql, null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    long id = cursor.getLong(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_ID));
                    String nome = cursor.getString(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_NOME));
                    Double media = cursor.getDouble(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_MEDIA));
                    Double notaRec = cursor.getDouble(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_REC));
                    int aulasPorSemana = cursor.getInt(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_AULAS));
                    int horasPorAula = cursor.getInt(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_HORASSEM));
                    int faltas = cursor.getInt(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_FALTAS));
                    Double notaAtual = cursor.getDouble(cursor.getColumnIndex(DisciplinasSQLHelper.COLUNA_NOTAATUAL));

                    Disciplina novaDisciplina = new Disciplina(id, nome, media, notaRec, aulasPorSemana, horasPorAula, faltas, notaAtual);
                    arrayList.add(novaDisciplina);

                }while (cursor.moveToNext());
            }

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        bd.close();
    }
}
