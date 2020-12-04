package udproject.compras.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import udproject.compras.CameraScanner;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;

public class HomeFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {


        LocalDB conexion=new LocalDB(getContext());

        if (conexion.ifExist())
        {
            view=inflater.inflate(R.layout.fragment_home, container, false);
        }
        else
        {
            view=inflater.inflate(R.layout.fragment_ingresar__lista, container, false);
        }
       /* FloatingActionButton fab=view.findViewById(R.id.camera_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), CameraScanner.class);
                startActivity(intent);
            }
        });*/
        return view;
    }

}