package bug.the.atlas.Entidades;

/**
 * Created by Andre on 05/12/2017.
 */

public class Disciplina {

    private String nome;
    private double media;
    private double notaRec;
    private int aulasPorSemana;
    private int horasPorAula;
    private int faltas;
    private double notaAtual;

    public Disciplina(String nome, double media, double notaRec, int aulasPorSemana, int horasPorAula) {
        this.nome = nome;
        this.media = media;
        this.notaRec = notaRec;
        this.aulasPorSemana = aulasPorSemana;
        this.horasPorAula = horasPorAula;
        faltas = 0;
        notaAtual = 0;
    }

    public String getNome() {
        return nome;
    }

    public double getMedia() {
        return media;
    }

    public double getNotaRec() {
        return notaRec;
    }

    public int getAulasPorSemana() {
        return aulasPorSemana;
    }

    public int getHorasPorAula() {
        return horasPorAula;
    }

    public int getFaltas() {
        return faltas;
    }

    public double getNotaAtual() {
        return notaAtual;
    }

    public void incrementaFaltas(){
        this.faltas++;
    }
}
