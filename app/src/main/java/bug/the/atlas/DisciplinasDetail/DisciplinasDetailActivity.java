package bug.the.atlas.DisciplinasDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static bug.the.atlas.Disciplinas.DisciplinasAdapter.EXTRA_DISCIPLINA;

public class DisciplinasDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.faltasAtual)
    TextView faltasAtual;

    @BindView(R.id.notaAtual)
    TextView notalAtual;

    @BindView(R.id.statusFalta)
    ImageView statusFalta;

    @BindView(R.id.statusNota)
    ImageView statusNota;

    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        disciplina = intent.getParcelableExtra(EXTRA_DISCIPLINA);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(disciplina.getNome());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setStatusFaltas();
        setStatusNota();
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.diminuiFalta)
    public void onClickDiminui(){
        if(disciplina.getFaltas() > 0)
            disciplina.decrementaFaltas();

        faltasAtual.setText(Integer.toString(disciplina.getFaltas()));
        setStatusFaltas();
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.aumentaFalta)
    public void onClickAumenta(){
        disciplina.incrementaFaltas();
        faltasAtual.setText(Integer.toString(disciplina.getFaltas()));
        setStatusFaltas();
    }

    //funcao que a cor do status das faltas, ou seja, da imagem circular
    public void setStatusFaltas(){
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

        int horas = disciplina.getAulasPorSemana() * 15;
        double horasAssistidas = horas - disciplina.getFaltas();

        double status = horasAssistidas/horas;
        Log.d("FALTAS", Double.toString(status));
        if(status >= 0.75){
            bitmap.eraseColor(getResources().getColor(R.color.verde));
            statusFalta.setImageBitmap(bitmap);
        }
        else{
            bitmap.eraseColor(getResources().getColor(R.color.vermelho));
            statusFalta.setImageBitmap(bitmap);
        }
    }

    public void setStatusNota(){
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
        bitmap.eraseColor(getResources().getColor(R.color.vermelho));
        if(disciplina.getNotaAtual() < disciplina.getNotaRec())
            statusNota.setImageBitmap(bitmap);
        else if(disciplina.getNotaAtual() > disciplina.getNotaRec() &&
                disciplina.getNotaAtual() < disciplina.getMedia()){
            bitmap.eraseColor(getResources().getColor(R.color.amarelo));
            statusFalta.setImageBitmap(bitmap);
        }
        else{
            bitmap.eraseColor(getResources().getColor(R.color.verde));
            statusFalta.setImageBitmap(bitmap);
        }
    }
}
