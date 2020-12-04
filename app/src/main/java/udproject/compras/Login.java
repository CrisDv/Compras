package udproject.compras;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import udproject.compras.firebase.Realtimepst;

public class Login extends AppCompatActivity  {

    private static final int CodeSignIn=5;
    private FirebaseAuth mAuth;
    private GoogleSignInClient SignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Configurar SING IN GOOGLE
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        SignInClient= GoogleSignIn.getClient(this, gso);

        Button GSign=findViewById(R.id.SignInGoogle);

        GSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=SignInClient.getSignInIntent();
                startActivityForResult(i, CodeSignIn);
            }
        });

        mAuth=FirebaseAuth.getInstance();//Inicializar Firebase Auth


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CodeSignIn)
        {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            AuthWithGoogle(result);
        }
    }

    private void AuthWithGoogle(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            AuthCredential authCredential=GoogleAuthProvider.getCredential((result.getSignInAccount()).getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task ->
            {
               if (task.isSuccessful())
               {
                   Toast.makeText(this, "LOGUEADO", Toast.LENGTH_LONG).show();
                   finish();
               }
               else
               {
                   Toast.makeText(this, "NO LOGUEADO", Toast.LENGTH_LONG).show();
               }
            });
        }
    }
}

