package udproject.compras.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import udproject.compras.Login;
import udproject.compras.R;
import udproject.compras.firebase.Realtimepst;

public class CuentaFragment extends Fragment {

    View view;
    Button logg;
    private FirebaseAuth mAuth;
    TextView Mail, Nombre, Compras;
    CircleImageView imgProfile;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_cuenta, container, false);

        logg=view.findViewById(R.id.loguear);
        Mail=view.findViewById(R.id.MailUser);
        Nombre=view.findViewById(R.id.NameUser);
        imgProfile=view.findViewById(R.id.ProfileImg);

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
            UpdateUI(currentuser);
        }
    }

    private void UpdateUI(FirebaseUser user)
    {
        Glide.with(view).load(user.getPhotoUrl()).into(imgProfile);
        Nombre.setText(user.getDisplayName());
        Mail.setText(user.getEmail());
        Realtimepst rs=new Realtimepst();
        rs.CrearUsuario();
    }
}