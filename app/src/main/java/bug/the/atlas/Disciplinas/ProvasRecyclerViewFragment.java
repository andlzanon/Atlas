package bug.the.atlas.Disciplinas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bug.the.atlas.BancoDeDados.ProvasRepositorio;
import bug.the.atlas.BaseActivity;
import bug.the.atlas.DisciplinasDetail.ProvasAdapter;
import bug.the.atlas.Entidades.Provas;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andre on 11/12/2017.
 */

public class ProvasRecyclerViewFragment extends Fragment {

    @BindView(R.id.provasRecyclerView)
    RecyclerView mRecyclerView;

    AbaProvasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Provas> mProvas = new ArrayList();
    private static ProvasRepositorio pr;

    public static ProvasRecyclerViewFragment novaInstancia(){
        ProvasRecyclerViewFragment provasRecyclerViewFragment = new ProvasRecyclerViewFragment();
        return provasRecyclerViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.provas_recycler_view, container, false);
        ButterKnife.bind(this, view);

        pr = ((BaseActivity)getContext()).getPr();
        pr.listaTodasProvas(mProvas);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fm = getFragmentManager();

        mAdapter = new AbaProvasAdapter(getContext(), mProvas, fm);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
