package bug.the.atlas.Entidades;

/**
 * Created by Andre on 05/12/2017.
 */

public class Provas {
    private String nome;
    private String data;
    private double pesoNaMediaFinal;
    private double nota;

    public Provas(String nome, String data, double pesoNaMediaFinal) {
        this.nome = nome;
        this.data = data;
        this.pesoNaMediaFinal = pesoNaMediaFinal;
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

    public void setNota(double nota) {
        this.nota = nota;
    }
}
