package bug.the.atlas.BancoDeDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bug.the.atlas.Entidades.Provas;

/**
 * Created by Andre on 11/12/2017.
 */

public class ProvasRepositorio {
    private ProvasSQLHelper helper;

    public ProvasRepositorio(Context context){
        helper = new ProvasSQLHelper(context);
    }

    /**
     * Insere provas no banco de dados da disciplina
     * @param prova
     * @param id_disciplina
     * @return
     */
    public long inserir(Provas prova, long id_disciplina){
        SQLiteDatabase bd = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ProvasSQLHelper.COLUNA_NOME, prova.getNome());
        cv.put(ProvasSQLHelper.COLUNA_LOCAL, prova.getLocal());
        cv.put(ProvasSQLHelper.COLUNA_HORARIO, prova.getHorario());
        cv.put(ProvasSQLHelper.COLUNA_DATA, prova.getData());
        cv.put(ProvasSQLHelper.COLUNA_NOTA, prova.getNota());
        cv.put(ProvasSQLHelper.COLUNA_PESO, prova.getPesoNaMediaFinal());
        cv.put(ProvasSQLHelper.COLUNA_IDDIS, id_disciplina);

        long id = bd.insert(ProvasSQLHelper.TABELA_PROVAS, null, cv);
        bd.close();

        return id;
    }

    /**
     * Exclui prova do banco de dados
     * @param prova
     * @return
     */
    public int excluir(Provas prova){
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasModificas = db.delete(
                ProvasSQLHelper.TABELA_PROVAS,
                ProvasSQLHelper.COLUNA_ID + " = ?",
                new String[]{ String.valueOf(prova.getId())}
        );

        db.close();
        return linhasModificas;
    }

    /**
     * Atualiza informacoes de prova ja cadastrada no banco de dados
     * @param prova
     * @return
     */
    public int atualizar(Provas prova){
        SQLiteDatabase bd = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ProvasSQLHelper.COLUNA_NOME, prova.getNome());
        cv.put(ProvasSQLHelper.COLUNA_LOCAL, prova.getLocal());
        cv.put(ProvasSQLHelper.COLUNA_HORARIO, prova.getHorario());
        cv.put(ProvasSQLHelper.COLUNA_DATA, prova.getData());
        cv.put(ProvasSQLHelper.COLUNA_NOTA, prova.getNota());
        cv.put(ProvasSQLHelper.COLUNA_PESO, prova.getPesoNaMediaFinal());
        cv.put(ProvasSQLHelper.COLUNA_IDDIS, prova.getId_disciplina());
        int linhasModificadas = bd.update(ProvasSQLHelper.TABELA_PROVAS,
                cv,
                ProvasSQLHelper.COLUNA_ID + " = ?",
                new String[]{String.valueOf(prova.getId())});

        return linhasModificadas;
    }

    /**
     * Apresenta todas as provas salvas no banco de dados
     * @param arrayList
     */
    public void listaTodasProvas(ArrayList<Provas> arrayList){
        SQLiteDatabase bd = helper.getReadableDatabase();

        arrayList.clear();

        try{
            String sql = "SELECT * FROM " + ProvasSQLHelper.TABELA_PROVAS;
            sql += " ORDER BY " + ProvasSQLHelper.COLUNA_DATA;

            Cursor cursor = bd.rawQuery(sql, null);
            if(cursor != null){
                cursor.moveToFirst();
                do{
                    long id = cursor.getLong(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_ID));
                    long id_dis = cursor.getLong(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_IDDIS));
                    String nome = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_NOME));
                    String local = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_LOCAL));
                    String horario = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_HORARIO));
                    String data = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_DATA));
                    Double nota = cursor.getDouble(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_NOTA));
                    Double peso = cursor.getDouble(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_PESO));

                    Provas novaProva = new Provas(id, id_dis, nome, local, horario, data, peso, nota);
                    arrayList.add(novaProva);

                }while (cursor.moveToNext());
            }

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        bd.close();
    }

    /**
     * Apresenta todas as provas salvas no banco de dados referente a certa disciplina
     * @param arrayList
     * @param chave_estrangeira
     */
    public void listaProvasDisciplina(ArrayList<Provas> arrayList, long chave_estrangeira){
        SQLiteDatabase bd = helper.getReadableDatabase();

        arrayList.clear();

        try{
            String sql = "SELECT * FROM " + ProvasSQLHelper.TABELA_PROVAS;
            sql += " WHERE " + ProvasSQLHelper.COLUNA_IDDIS + " = " + chave_estrangeira;
            sql += " ORDER BY " + ProvasSQLHelper.COLUNA_DATA;

            Cursor cursor = bd.rawQuery(sql, null);
            if(cursor != null){
                cursor.moveToFirst();
                do{
                    long id = cursor.getLong(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_ID));
                    long id_dis = cursor.getLong(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_IDDIS));
                    String nome = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_NOME));
                    String local = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_LOCAL));
                    String horario = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_HORARIO));
                    String data = cursor.getString(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_DATA));
                    Double nota = cursor.getDouble(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_NOTA));
                    Double peso = cursor.getDouble(cursor.getColumnIndex(ProvasSQLHelper.COLUNA_PESO));

                    Provas novaProva = new Provas(id, id_dis, nome, local, horario, data, peso, nota);
                    arrayList.add(novaProva);

                }while (cursor.moveToNext());
            }

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        bd.close();
    }
}
