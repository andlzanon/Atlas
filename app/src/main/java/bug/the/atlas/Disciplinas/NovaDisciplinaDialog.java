package bug.the.atlas.Disciplinas;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NovaDisciplinaDialog extends DialogFragment {

    @BindView(R.id.NomeDisciplina)
    EditText nome;

    @BindView(R.id.edtTxtMedia)
    EditText media;

    @BindView(R.id.edtTextRec)
    EditText rec;

    @BindView(R.id.edtTxtNroAulas)
    EditText nroaulas;

    @BindView(R.id.edtTextHoras)
    EditText horas;

    private Disciplina mDisciplina;
    private static final String DIALOG_TAG_DISCIPLINA = "dialog";

    public NovaDisciplinaDialog() {
        // Required empty public constructor
    }

    public static NovaDisciplinaDialog newInstance() {
        NovaDisciplinaDialog fragment = new NovaDisciplinaDialog();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nova_disciplina_dialog, container, false);

        ButterKnife.bind(this, view);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        return view;
    }

    public void abrir(FragmentManager fm){
        if(fm.findFragmentByTag(DIALOG_TAG_DISCIPLINA) == null){
            show(fm, DIALOG_TAG_DISCIPLINA);
        }
    }

    @OnClick(R.id.button_cancel)
    public void onClickCancel(){
        dismiss();
    }

    @OnClick(R.id.button_save)
    public void onClickSave(){
        salvaEdita();
    }

    public void salvaEdita(){
        Fragment fragment = getParentFragment();
        if(nome.getText().toString().equals("") || media.getText().toString().equals("")
                || rec.getText().toString().equals("") || nroaulas.getText().toString().equals("")
                || horas.getText().toString().equals("")){
            Toast.makeText(getContext(), "Algum campo nao foi preenchido", Toast.LENGTH_LONG).show();
        }
        else {
            mDisciplina = new Disciplina(nome.getText().toString(), Integer.parseInt(media.getText().toString()),
                    Integer.parseInt(rec.getText().toString()), Integer.parseInt(nroaulas.getText().toString()),
                    Integer.parseInt(horas.getText().toString()));
            AoSalvarEvento aoSalvarEvento = (AoSalvarEvento) fragment;
            aoSalvarEvento.adiciona(mDisciplina);
        }

        dismiss();
    }

    public interface AoSalvarEvento{
        void adiciona(Disciplina disciplina);
    }
}
