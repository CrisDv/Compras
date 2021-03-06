package udproject.compras.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import udproject.compras.BD.LocalDB;
import udproject.compras.R;
import udproject.compras.BD.Realtimepst;

public class CuentaFragment extends Fragment {

    View view;

    SignInButton logg;
    private final int CodeSignIn=5;
    private FirebaseAuth mAuth;
    private GoogleSignInClient SigInClient;
    TextView Mail, Nombre, Compras;
    CircleImageView imgProfile;

    private LineChart lineChart;
    private LineDataSet lineDataSet;
    LinearLayout SuccessfullData, FailData;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_cuenta, container, false);

        logg=view.findViewById(R.id.loguear);
        Mail=view.findViewById(R.id.MailUser);
        Nombre=view.findViewById(R.id.NameUser);
        imgProfile=view.findViewById(R.id.ProfileImg);
        SuccessfullData=view.findViewById(R.id.LoginsuccesfullData);
        FailData=view.findViewById(R.id.LoginFiledfullData);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        SigInClient=GoogleSignIn.getClient(getContext(), gso);
        logg.setSize(SignInButton.SIZE_STANDARD);

        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=SigInClient.getSignInIntent();
                startActivityForResult(i, CodeSignIn);
            }
        });
        /*logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });*/
        mAuth=FirebaseAuth.getInstance();

        lineChart=view.findViewById(R.id.ChartLineLogin);
        ChartData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();;

        if(currentuser==null)
        {
            logg.setVisibility(View.VISIBLE);
            FailData.setVisibility(View.VISIBLE);
            SuccessfullData.setVisibility(View.GONE);
        }
        else
        {
            logg.setVisibility(View.GONE);

            UpdateUI();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CodeSignIn){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            AuthWithGoogle(result);
        }
    }

    private void AuthWithGoogle(GoogleSignInResult result){
        if (result.isSuccess())
        {
            AuthCredential authCredential= GoogleAuthProvider.getCredential((result.getSignInAccount()).getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), task ->
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "LOGUEADO", Toast.LENGTH_LONG).show();
                    UpdateUI();
                }
                else
                {
                    Toast.makeText(getContext(), "NO LOGUEADO", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void UpdateUI()
    {
        FirebaseUser user=mAuth.getCurrentUser();;
        Glide.with(view).load(user.getPhotoUrl()).into(imgProfile);
        Nombre.setText(user.getDisplayName());
        Mail.setText(user.getEmail());
        Realtimepst rs=new Realtimepst(getContext());
        rs.CrearUsuario();
            rs.BackUpListas();

        logg.setVisibility(View.GONE);
        FailData.setVisibility(View.GONE);
        SuccessfullData.setVisibility(View.VISIBLE);
    }

    private void ChartData()
    {
        // Creamos un set de datos
       ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        LocalDB localDB=new LocalDB(getContext());
        String Nombre="";
        ArrayList<String> meses=localDB.Meses();
        ArrayList<String> valor=new ArrayList<String>();

        for (int i=0;i<meses.size();i++){
            valor.add(localDB.Presupuestos(meses.get(i)));
        }


        for (int i = 0; i<meses.size(); i++){

           // float y = (int) (Math.random() * (40000 - 20000 + 1) + 20000);
            lineEntries.add(new Entry((float)i,Float.parseFloat(valor.get(i)) ));
        }

        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(meses));

// Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Dinero en Compras");

// Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.getDescription().setEnabled(false);
        //lineChart.setData(lineData);

        if (lineEntries.isEmpty()) {
            lineChart.clear();
        } else {
            // set data
            lineChart.setData(lineData);
        }
    }
}