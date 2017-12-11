package bug.the.atlas.DisciplinasDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import bug.the.atlas.BancoDeDados.DisciplinasRepositorio;
import bug.the.atlas.BancoDeDados.ProvasRepositorio;
import bug.the.atlas.BaseActivity;
import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.Entidades.Provas;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static bug.the.atlas.Disciplinas.DisciplinasAdapter.EXTRA_DISCIPLINA;

public class DisciplinasDetailActivity extends BaseActivity implements AvaliacoesDialogFragment.AoSalvarProva,
        AvaliacoesDialogFragment.AoEditarProva {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.faltasAtual)
    TextView faltasAtual;

    @BindView(R.id.notaAtual)
    TextView notalAtual;

    @BindView(R.id.statusFalta)
    CircleImageView statusFalta;

    @BindView(R.id.statusNota)
    CircleImageView statusNota;

    @BindView(R.id.recyclerViewProvas)
    RecyclerView mRecyclerView;

    private Disciplina disciplina;
    ProvasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Provas> mProvas = new ArrayList<Provas>();
    private DisciplinasRepositorio dr;
    private ProvasRepositorio pr;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas_detail);

        ButterKnife.bind(this);

        dr = getDr();
        pr = getPr();

        Intent intent = getIntent();
        disciplina = intent.getParcelableExtra(EXTRA_DISCIPLINA);

        if(disciplina != null){
            DecimalFormat decimalFormat = new DecimalFormat("00.00");
            String nota = decimalFormat.format(disciplina.getNotaAtual());
            notalAtual.setText(nota);
            faltasAtual.setText(Integer.toString(disciplina.getFaltas()));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(disciplina.getNome());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setStatusFaltas();
        setStatusNota();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pr.listaProvasDisciplina(mProvas, disciplina.getId());
        mAdapter = new ProvasAdapter(this, mProvas, getSupportFragmentManager());
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                exclui(mProvas.get(viewHolder.getAdapterPosition()));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }
        });mIth.attachToRecyclerView(mRecyclerView);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addAv);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AvaliacoesDialogFragment avaliacoesDialogFragment = AvaliacoesDialogFragment.novaInstancia(null);
                avaliacoesDialogFragment.abrir(getSupportFragmentManager());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.diminuiFalta)
    public void onClickDiminui(){
        if(disciplina.getFaltas() > 0){
            disciplina.decrementaFaltas();

            //atualiza novo numerod de faltas no BD
            dr.atualizar(disciplina);

            faltasAtual.setText(Integer.toString(disciplina.getFaltas()));
            setStatusFaltas();
        }
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.aumentaFalta)
    public void onClickAumenta(){
        disciplina.incrementaFaltas();

        //atualiza novo numerod de faltas no BD
        dr.atualizar(disciplina);

        faltasAtual.setText(Integer.toString(disciplina.getFaltas()));
        setStatusFaltas();
    }

    //funcao que a cor do status das faltas, ou seja, da imagem circular
    public void setStatusFaltas(){
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

        int horas = disciplina.getAulasPorSemana() * 15;
        double horasAssistidas = horas - disciplina.getFaltas();

        double status = horasAssistidas/horas;
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
            statusNota.setImageBitmap(bitmap);
        }
        else{
            bitmap.eraseColor(getResources().getColor(R.color.verde));
            statusNota.setImageBitmap(bitmap);
        }
    }

    @SuppressLint("SetTextI18n")
    public void calculaNota(){
        if(mProvas.size() >= 1){
            double totalNota = 0;
            double totalPeso = 0;
            for(Provas prova : mProvas){
                totalNota += prova.getNota();
                totalPeso += prova.getPesoNaMediaFinal();
            }

            double media = totalNota/totalPeso;
            disciplina.setNotaAtual(media);

            //atualiza a nova nota no BD
            dr.atualizar(disciplina);

            setStatusNota();
            notalAtual.setText(Double.toString(disciplina.getNotaAtual()));
        }
        else{
            disciplina.setNotaAtual(0);

            //atualiza a nova nota no BD
            dr.atualizar(disciplina);

            setStatusNota();
            notalAtual.setText(Double.toString(disciplina.getNotaAtual()));
        }


    }

    @Override
    public void editaProva(Provas prova) {
        int pos = mProvas.indexOf(prova);
        mProvas.get(pos).setNome(prova.getNome());
        mProvas.get(pos).setLocal(prova.getLocal());
        mProvas.get(pos).setHorario(prova.getHorario());
        mProvas.get(pos).setData(prova.getData());
        mProvas.get(pos).setPesoNaMediaFinal(prova.getPesoNaMediaFinal());
        mProvas.get(pos).setNota(prova.getNota());
        //atualiza no BD a edicao da prova
        pr.atualizar(mProvas.get(pos));
        calculaNota();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void adicionaProva(Provas prova) {
        mProvas.add(prova);
        pr.inserir(prova, disciplina.getId());
        calculaNota();
        mAdapter.notifyDataSetChanged();
    }

    private void exclui(Provas prova) {
        mProvas.remove(prova);
        pr.excluir(prova);
        calculaNota();
        mAdapter.notifyDataSetChanged();
    }
}
