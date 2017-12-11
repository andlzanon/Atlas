package bug.the.atlas.Disciplinas;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bug.the.atlas.Entidades.Provas;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andre on 11/12/2017.
 */

public class AbaProvasAdapter extends RecyclerView.Adapter<AbaProvasAdapter.AbaProvasViewHolder> {

    public static class AbaProvasViewHolder extends RecyclerView.ViewHolder{

        /**
         * Butterknife para integrar xml e java
         */
        @BindView(R.id.aba_nomeProva)
        TextView aba_nomeDaProva;

        @BindView(R.id.aba_localProva)
        TextView aba_localDaProva;

        @BindView(R.id.aba_horaProva)
        TextView aba_horaDaProva;

        @BindView(R.id.aba_dataProva)
        TextView aba_dataDaProva;

        public AbaProvasViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static ArrayList<Provas> provas;
    FragmentManager fragmentManager;
    Context context;

    public AbaProvasAdapter(Context context, ArrayList<Provas> provas, FragmentManager fragmentManager){
        this.provas = provas;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public AbaProvasAdapter.AbaProvasViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_provas_abas, parent, false);
        AbaProvasAdapter.AbaProvasViewHolder evh = new AbaProvasAdapter.AbaProvasViewHolder(layout);
        return evh;
    }

    /**
     * Seta as abas referentes as provas
     * @param provasViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(final AbaProvasAdapter.AbaProvasViewHolder provasViewHolder, int i) {
        provasViewHolder.aba_nomeDaProva.setText(provas.get(i).getNome());
        provasViewHolder.aba_localDaProva.setText(provas.get(i).getLocal());
        provasViewHolder.aba_horaDaProva.setText(provas.get(i).getHorario());
        provasViewHolder.aba_dataDaProva.setText(provas.get(i).getData());
    }

    /**
     * Retorna quantidade de provas
     * @return
     */
    @Override
    public int getItemCount() {
        return provas.size();
    }
}
