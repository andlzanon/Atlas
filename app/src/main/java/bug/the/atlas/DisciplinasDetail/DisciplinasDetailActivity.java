package bug.the.atlas.DisciplinasDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import bug.the.atlas.Entidades.Disciplina;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bug.the.atlas.Disciplinas.DisciplinasAdapter.EXTRA_DISCIPLINA;

public class DisciplinasDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Disciplina disciplina = intent.getParcelableExtra(EXTRA_DISCIPLINA);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(disciplina.getNome());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
