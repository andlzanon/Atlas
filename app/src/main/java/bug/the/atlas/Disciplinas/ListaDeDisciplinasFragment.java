package bug.the.atlas.Disciplinas;


import android.os.Bundle;
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

import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListaDeDisciplinasFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    DisciplinasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList mDisciplinas = new ArrayList();

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

        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));
        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));
        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));
        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));
        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));
        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));
        mDisciplinas.add(new Disciplina("Calculo1", 6, 5, 2, 2));

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fm = getChildFragmentManager();

        mAdapter = new DisciplinasAdapter(getContext(), mDisciplinas, fm);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mDisciplinas.remove(mDisciplinas.get(viewHolder.getAdapterPosition()));
                Log.d("ExcluindoTrabalho", "Excluido " +viewHolder.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }
        });mIth.attachToRecyclerView(mRecyclerView);

        // Inflate the layout for this fragment
        return view;
    }
}
