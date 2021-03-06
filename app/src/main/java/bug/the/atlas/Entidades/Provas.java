package bug.the.atlas.Entidades;

import java.io.Serializable;

/**
 * Created by Andre on 05/12/2017.
 */

public class Provas implements Serializable {
    private long id;
    private long id_disciplina;
    private String nome;
    private String local;
    private String horario;
    private String data;
    private double pesoNaMediaFinal;
    private double nota;

    // Construtores para provas
    public Provas(String nome, String local, String horario, String data, double pesoNaMediaFinal) {
        this.nome = nome;
        this.local = local;
        this.horario = horario;
        this.data = data;
        this.pesoNaMediaFinal = pesoNaMediaFinal;
        nota = 0;
    }

    public Provas(String nome, String local, String horario, String data, double pesoNaMediaFinal, double nota) {
        this.nome = nome;
        this.local = local;
        this.horario = horario;
        this.data = data;
        this.pesoNaMediaFinal = pesoNaMediaFinal;
        this.nota = nota;
    }

    public Provas(long id, long id_disciplina, String nome, String local, String horario, String data, double pesoNaMediaFinal, double nota) {
        this.id = id;
        this.id_disciplina = id_disciplina;
        this.nome = nome;
        this.local = local;
        this.horario = horario;
        this.data = data;
        this.pesoNaMediaFinal = pesoNaMediaFinal;
        this.nota = nota;
    }

    // Gets e sets referentes a dados das provas

    public long getId() {
        return id;
    }

    public long getId_disciplina() {
        return id_disciplina;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public double getPesoNaMediaFinal() {
        return pesoNaMediaFinal;
    }

    public double getNota() {
        return nota;
    }

    public String getLocal() {
        return local;
    }

    public String getHorario() {
        return horario;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPesoNaMediaFinal(double pesoNaMediaFinal) {
        this.pesoNaMediaFinal = pesoNaMediaFinal;
    }
}
