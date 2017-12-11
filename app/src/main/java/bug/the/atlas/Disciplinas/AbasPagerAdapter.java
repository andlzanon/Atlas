package bug.the.atlas.Disciplinas;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import bug.the.atlas.Entidades.Provas;

/**
 * Created by Andre on 04/12/2017.
 */

public class AbasPagerAdapter extends FragmentPagerAdapter {

    private String[] titulosAbas;

    public AbasPagerAdapter(Context ctx, FragmentManager fm, int id) {
        super(fm);
        //id é recebido com sucesso e consegue-se acessar o id é da classe R.
        titulosAbas = ctx.getResources().getStringArray(id);
    }

    @Override
    public Fragment getItem(int position) {
        // os parametros são: posiçao: para se saber qual fragment usar
        if(position == 0)
            return ListaDeDisciplinasFragment.novaInstancia();
        else
            return ProvasRecyclerViewFragment.novaInstancia();
    }

    @Override
    public int getCount() {
        //tamanho do vetor do array
        return titulosAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        Locale l = Locale.getDefault();
        return titulosAbas[position].toUpperCase(l);
    }

}
