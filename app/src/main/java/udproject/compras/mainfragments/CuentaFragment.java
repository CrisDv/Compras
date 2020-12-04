package udproject.compras.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import udproject.compras.CameraScanner;
import udproject.compras.Login;
import udproject.compras.R;

public class CuentaFragment extends Fragment {

    View view;
    Button logg;
    private FirebaseAuth mAuth;
    TextView Cuentatext;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_cuenta, container, false);

        logg=view.findViewById(R.id.loguear);
        Cuentatext=view.findViewById(R.id.text_notifications);
        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        mAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();;

        if(currentuser==null)
        {
            logg.setVisibility(View.VISIBLE);
        }
        else
        {
            logg.setVisibility(View.INVISIBLE);
            Cuentatext.setText(currentuser.getEmail());
        }
    }

    private void UpdateUI()
    {

    }
}