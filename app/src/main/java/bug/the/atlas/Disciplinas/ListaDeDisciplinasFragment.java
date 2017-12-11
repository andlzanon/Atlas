package bug.the.atlas.Disciplinas;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import bug.the.atlas.BancoDeDados.DisciplinasRepositorio;
import bug.the.atlas.BaseActivity;
import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListaDeDisciplinasFragment extends Fragment implements NovaDisciplinaDialog.AoSalvarEvento {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    DisciplinasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Disciplina> mDisciplinas = new ArrayList();
    private static DisciplinasRepositorio dr;

    public static ListaDeDisciplinasFragment novaInstancia() {
        ListaDeDisciplinasFragment fragment = new ListaDeDisciplinasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_de_disciplinas, container, false);
        ButterKnife.bind(this, view);

        //passa todas os dados do BD para a lista
        dr = ((BaseActivity)getContext()).getDr();
        dr.listaEventos(mDisciplinas);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fm = getChildFragmentManager();

        mAdapter = new DisciplinasAdapter(getContext(), mDisciplinas, fm);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.addDisciplinas);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                NovaDisciplinaDialog disciplinaDialog = NovaDisciplinaDialog.newInstance();
                disciplinaDialog.abrir(getChildFragmentManager());
            }
        });

        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                exclui(mDisciplinas.get(viewHolder.getAdapterPosition()));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }
        });mIth.attachToRecyclerView(mRecyclerView);

        for(Disciplina disciplina : mDisciplinas){
            Log.d("TESTE", Integer.toString(disciplina.getFaltas()));
        }

        // Inflate the layout for this fragment
        return view;
    }

    public void adiciona(Disciplina trabalho){
        mDisciplinas.add(trabalho);
        dr.inserir(trabalho);
        mAdapter.notifyDataSetChanged();
    }


    public void exclui(Disciplina disciplina){
        mDisciplinas.remove(disciplina);
        dr.excluir(disciplina);
        mAdapter.notifyDataSetChanged();
    }

    public static DisciplinasRepositorio getDr(){
        return dr;
    }

}
