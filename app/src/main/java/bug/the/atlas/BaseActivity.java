package bug.the.atlas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import bug.the.atlas.BancoDeDados.DisciplinasRepositorio;
import bug.the.atlas.BancoDeDados.ProvasRepositorio;

/**
 * Created by Andre on 11/12/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private DisciplinasRepositorio dr;
    private ProvasRepositorio pr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dr = new DisciplinasRepositorio(this);
        pr = new ProvasRepositorio(this);
    }

    public DisciplinasRepositorio getDr(){
        return dr;
    }

    public ProvasRepositorio getPr(){
        return pr;
    }
}
