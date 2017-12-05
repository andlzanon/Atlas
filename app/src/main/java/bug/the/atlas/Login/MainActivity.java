package bug.the.atlas.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import bug.the.atlas.Disciplinas.ListaDeDisciplinasActivity;
import bug.the.atlas.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //variaveis
    //variavel para acitvity for result
    private static final int RC_SIGN_IN = 9001;
    //tag para teste
    private static final String TAG = "MainActivity";
    //autenticacao do firebase
    private FirebaseAuth mAuth;
    //para o google signin
    private GoogleSignInClient mGoogleSignInClient;

    //faz os bindings dos elementos de layout
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.progressBarFoto)
    ProgressBar progressBarFoto;

    @BindView(R.id.imagemPerfil)
    ImageView imagemPerfil;

    @BindView(R.id.nomePerfil)
    TextView nomePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //inicia o google signin options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        //se ja foi feito um login mostra a imagem e foto do usuario
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String nome = sharedPref.getString("nome", null);
        String foto = sharedPref.getString("foto", null);

        //seta o nome do usuario
        if(nome != null){
            nomePerfil.setText(nome);
        //senao nao pode ficar visivel
        }else{
            nomePerfil.setVisibility(View.INVISIBLE);
        }

        //seta foto do usuario por meio do picasso
        if(foto != null){
            showProgressBarFoto();
            Picasso.with(this)
                    .load(Uri.parse(foto))
                    .centerCrop()
                    .fit()
                    .into(imagemPerfil);
            hideProgressBarFoto();
        }else{
            nomePerfil.setVisibility(View.INVISIBLE);
        }
    }

    //se for clicado inicia o login com sucesso
    @OnClick(R.id.button_login)
    public void fazLogin (){
        loginComSucesso();
    }

    //inicia o activity para resultado para login
    public void loginComSucesso(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Intent intent = new Intent(MainActivity.this, ListaDeDisciplinasActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgressBar();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //salva dados do usuario na sharedPreferences
                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("nome", user.getDisplayName());
                            editor.putString("foto", user.getPhotoUrl().toString());
                            editor.apply();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressBar();
                    }
                });
    }
    // [END auth_with_google]

    //esconde o progressBar do login
    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    //mostra o progressBar do login
    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    //esconde o progressBar da foto
    private void hideProgressBarFoto(){
        progressBarFoto.setVisibility(View.INVISIBLE);
    }

    //mostra o progressBar da foto
    private void showProgressBarFoto(){
        progressBarFoto.setVisibility(View.VISIBLE);
    }

}
