package bug.the.atlas.DisciplinasDetail;

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
 * Created by Andre on 10/12/2017.
 */

public class ProvasAdapter extends RecyclerView.Adapter<ProvasAdapter.ProvasViewHolder> {

    public static class ProvasViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.nomeProva)
        TextView nomeDaProva;

        @BindView(R.id.localProva)
        TextView localDaProva;

        @BindView(R.id.horaProva)
        TextView horaDaProva;

        @BindView(R.id.dataProva)
        TextView dataDaProva;

        public ProvasViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static ArrayList<Provas> provas;
    FragmentManager fragmentManager;
    Context context;
    public static final String EXTRA_PROVAS = "provas";

    public ProvasAdapter(Context context, ArrayList<Provas> provas, FragmentManager fragmentManager){
        this.provas = provas;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public ProvasAdapter.ProvasViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_provas, parent, false);
        ProvasViewHolder evh = new ProvasViewHolder(layout);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ProvasAdapter.ProvasViewHolder provasViewHolder, int i) {
        provasViewHolder.nomeDaProva.setText(provas.get(i).getNome());
        provasViewHolder.localDaProva.setText(provas.get(i).getLocal());
        provasViewHolder.horaDaProva.setText(provas.get(i).getHorario());
        provasViewHolder.dataDaProva.setText(provas.get(i).getData());

        provasViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AvaliacoesDialogFragment eventosDialogFragment = AvaliacoesDialogFragment.
                        novaInstancia(provas.get(provasViewHolder.getAdapterPosition()));
                eventosDialogFragment.abrir(fragmentManager);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provas.size();
    }
}
