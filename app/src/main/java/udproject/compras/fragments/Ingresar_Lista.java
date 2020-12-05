package udproject.compras.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.CameraScanner;
import udproject.compras.FrDialog.FragmentDialogPresupuesto;
import udproject.compras.FrDialog.IngresarPorTexto;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;
import udproject.compras.recycler.RecyclerProductAdapter;

public class Ingresar_Lista extends Fragment implements RecyclerProductAdapter.OnProductListener {

    private List<Item_Producto> itemProducto=new ArrayList<>();
    private RecyclerView RecyclerItemProductos;
    private RecyclerProductAdapter AdaptadorProducto;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_ingresar__lista, container, false);

        RecyclerItemProductos=view.findViewById(R.id.RecyclerProductos);
        RecyclerItemProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        LocalDB local=new LocalDB(getContext());
        AdaptadorProducto = new RecyclerProductAdapter(local.ListaProducto(),this);
        RecyclerItemProductos.setAdapter(AdaptadorProducto);


        return view;


        /*int ID, String Nombre, int Precio, String Descripcion*/
    }


    @Override
    public void onProductClick(int posicion) {
        Toast.makeText(getContext(), "HOLA "+Integer.toString(posicion), Toast.LENGTH_LONG).show();
    }

}