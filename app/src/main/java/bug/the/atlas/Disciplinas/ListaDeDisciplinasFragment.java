package bug.the.atlas.Disciplinas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bug.the.atlas.R;


public class ListaDeDisciplinasFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_de_disciplinas, container, false);
    }
}
