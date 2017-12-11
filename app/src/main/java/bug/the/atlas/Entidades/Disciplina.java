package bug.the.atlas.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Andre on 05/12/2017.
 */

public class Disciplina implements Parcelable{

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

    public void decrementaFaltas(){
        this.faltas--;
    }

    private Disciplina(Parcel from){
        nome = from.readString();
        media = from.readDouble();
        notaRec = from.readDouble();
        aulasPorSemana = from.readInt();
        horasPorAula = from.readInt();
        faltas = from.readInt();
        notaAtual = from.readDouble();
    }

    public static final Parcelable.Creator<Disciplina>
            CREATOR = new Parcelable.Creator<Disciplina>(){

        public Disciplina createFromParcel(Parcel in){
            return new Disciplina(in);
        }

        @Override
        public Disciplina[] newArray(int i) {
            return new Disciplina[i];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(nome);
        dest.writeDouble(media);
        dest.writeDouble(notaRec);
        dest.writeInt(aulasPorSemana);
        dest.writeInt(horasPorAula);
        dest.writeInt(faltas);
        dest.writeDouble(notaAtual);
    }

    @Override
    public int describeContents(){
        return 0;
    }

}
