package bug.the.atlas.Disciplinas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bug.the.atlas.BancoDeDados.DisciplinasRepositorio;
import bug.the.atlas.DisciplinasDetail.DisciplinasDetailActivity;
import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andre on 05/12/2017.
 */

public class DisciplinasAdapter extends RecyclerView.Adapter<DisciplinasAdapter.DisciplinasViewHolder> {

    public static final String EXTRA_DISCIPLINA = "disciplina";
    private ArrayList<Disciplina> disciplinas;
    FragmentManager fragmentManager;
    Context context;

    public DisciplinasAdapter(Context context, ArrayList<Disciplina> disciplinas, FragmentManager fragmentManager){
        this.context = context;
        this.disciplinas = disciplinas;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public DisciplinasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_disciplinas, parent, false);
        DisciplinasViewHolder dvh = new DisciplinasViewHolder(layout);
        return dvh;
    }

    @Override
    public void onBindViewHolder(DisciplinasViewHolder holder, final int position) {
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
        bitmap.eraseColor(context.getResources().getColor(R.color.vermelho));
        holder.nomeDisciplina.setText(disciplinas.get(position).getNome());
        if(disciplinas.get(position).getNotaAtual() < disciplinas.get(position).getNotaRec())
            holder.status.setImageBitmap(bitmap);
        else if(disciplinas.get(position).getNotaAtual() > disciplinas.get(position).getNotaRec() &&
                disciplinas.get(position).getNotaAtual() < disciplinas.get(position).getMedia()){
            bitmap.eraseColor(context.getResources().getColor(R.color.amarelo));
            holder.status.setImageBitmap(bitmap);
        }
        else{
            bitmap.eraseColor(context.getResources().getColor(R.color.verde));
            holder.status.setImageBitmap(bitmap);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisciplinasDetailActivity.class);
                intent.putExtra(EXTRA_DISCIPLINA, disciplinas.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public static class DisciplinasViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardview)
        CardView cardView;

        @BindView(R.id.nomeDisciplina)
        TextView nomeDisciplina;

        @BindView(R.id.status)
        de.hdodenhof.circleimageview.CircleImageView status;

        public DisciplinasViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
