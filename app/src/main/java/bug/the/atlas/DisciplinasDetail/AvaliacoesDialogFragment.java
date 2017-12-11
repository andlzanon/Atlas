package bug.the.atlas.DisciplinasDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

import bug.the.atlas.R;
import bug.the.atlas.Entidades.Provas;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andre on 29/03/2017.
 */

public class AvaliacoesDialogFragment extends DialogFragment {

    private static final String EXTRA_PROVA = "evento";
    private static final String DIALOG_TAG_PROVA = "dialog";
    private Provas mProva;

    @BindView(R.id.NomeAv)
    EditText nome;

    @BindView(R.id.LocalAv)
    EditText local;

    @BindView(R.id.HorarioAv)
    EditText horario;

    @BindView(R.id.DataAv)
    EditText data;

    @BindView(R.id.PesoAv)
    EditText peso;

    @BindView(R.id.NotaAv)
    EditText nota;

    DecimalFormat mFormato = new DecimalFormat("00");

    public static AvaliacoesDialogFragment novaInstancia(Provas prova){
        Bundle params = new Bundle();
        params.putSerializable(EXTRA_PROVA, prova);

        AvaliacoesDialogFragment avDialogFragment = new AvaliacoesDialogFragment();
        avDialogFragment.setArguments(params);
        return avDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProva = (Provas) getArguments().getSerializable(EXTRA_PROVA);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.dialog_nova_avaliacao, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        ButterKnife.bind(this, layout);

        if (mProva != null) {
            nome.setText(mProva.getNome());
            local.setText(mProva.getLocal());
            data.setText(mProva.getData());
            horario.setText(mProva.getHorario());
            peso.setText(Double.toString(mProva.getPesoNaMediaFinal()));
            nota.setText(Double.toString(mProva.getNota()));
        }

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar tempoAtual = Calendar.getInstance();
                int hora = tempoAtual.get(Calendar.HOUR_OF_DAY);
                int minuto = tempoAtual.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String tempo = mFormato.format(hourOfDay) + ":" + mFormato.format(minute);
                        horario.setText(tempo);
                    }
                }, hora, minuto, true);
                timePickerDialog.show();
            }
        });


        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dataAtual = Calendar.getInstance();
                int dia = dataAtual.get(Calendar.DAY_OF_MONTH);
                int mes = dataAtual.get(Calendar.MONTH);
                int ano = dataAtual.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        String date = mFormato.format(monthOfYear) + "/" + mFormato.format(dayOfMonth) + "/" + year;
                        data.setText(date);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });

        final Button button = (Button) layout.findViewById(R.id.button_save_evento);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaEdita();
            }
        });

        final Button buttonCancel = (Button) layout.findViewById(R.id.button_cancel_evento);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return layout;
    }

    public void abrir(FragmentManager fm){
        if(fm.findFragmentByTag(DIALOG_TAG_PROVA) == null){
            show(fm, DIALOG_TAG_PROVA);
        }
    }

    public void salvaEdita(){
        Context context = getContext();
        if (mProva == null) {
            if(nome.getText().toString().equals("") || local.getText().toString().equals("")
                    || data.getText().toString().equals("") || horario.getText().toString().equals("") || peso.getText().toString().equals("")){
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_LONG).show();
            }
            else {
                if(nota.getText().toString().equals("")){
                    mProva = new Provas(nome.getText().toString(), local.getText().toString(),
                            horario.getText().toString(), data.getText().toString(), Double.parseDouble(peso.getText().toString()));
                    AoSalvarProva aoSalvarProva = (AoSalvarProva) context;
                    aoSalvarProva.adicionaProva(mProva);
                }
                else{
                    mProva = new Provas(nome.getText().toString(), local.getText().toString(),
                            horario.getText().toString(), data.getText().toString(), Double.parseDouble(peso.getText().toString()),
                            Double.parseDouble(nota.getText().toString()));
                    AoSalvarProva aoSalvarProva = (AoSalvarProva) context;
                    aoSalvarProva.adicionaProva(mProva);
                }
            }
        }

        else{
            if(nome.getText().toString().equals("") || local.getText().toString().equals("")
                    || data.getText().toString().equals("") || horario.getText().toString().equals("")){
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_LONG).show();
            }
            else {
                mProva.setNome(nome.getText().toString());
                mProva.setLocal(local.getText().toString());
                mProva.setHorario(horario.getText().toString());
                mProva.setData(data.getText().toString());
                mProva.setPesoNaMediaFinal(Double.parseDouble(peso.getText().toString()));
                mProva.setNota(Double.parseDouble(nota.getText().toString()));
                AoEditarProva aoEditarProva = (AoEditarProva) context;
                aoEditarProva.editaProva(mProva);
            }
        }

        dismiss();
    }

    public interface AoSalvarProva {
        void adicionaProva(Provas prova);
    }

    public interface AoEditarProva {
        void editaProva(Provas prova);
    }

}
