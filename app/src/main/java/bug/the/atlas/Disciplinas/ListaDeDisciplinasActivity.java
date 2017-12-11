package bug.the.atlas.Disciplinas;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import bug.the.atlas.BaseActivity;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaDeDisciplinasActivity extends BaseActivity {

    /**
     * Butterknife para integrar xml e java
     */
    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_disciplinas);

        ButterKnife.bind(this);

        // Titulo na action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        AbasPagerAdapter pagerAdapter = new AbasPagerAdapter(this, getSupportFragmentManager(), R.array.abas);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
