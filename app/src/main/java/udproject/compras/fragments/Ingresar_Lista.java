package udproject.compras.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import udproject.compras.Recognition.CameraScanner;
import udproject.compras.FrDialog.IngresarPorTexto;
import udproject.compras.R;

public class Ingresar_Lista extends Fragment implements View.OnClickListener {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_ingresar__lista, container, false);

        Button xde=view.findViewById(R.id.IngresarTexto);
        xde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment=new IngresarPorTexto();
                newFragment.show(getFragmentManager(), "xde");
            }
        });

        Button Scanner=view.findViewById(R.id.IngresarScanner);
        Scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CameraScanner.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {

    }
}