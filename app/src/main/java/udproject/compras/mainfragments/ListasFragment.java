package udproject.compras.mainfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.item_ListaGuardada;
import udproject.compras.R;
import udproject.compras.BD.LocalDB;
import udproject.compras.recycler.RecyclerListaGuardadaAdapter;

public class ListasFragment extends Fragment implements RecyclerListaGuardadaAdapter.OnItemGuardadoListener{

    private RecyclerView ListasGuardadasView;
    private RecyclerListaGuardadaAdapter AdapterListaGuardada;
    List<item_ListaGuardada> SavedList=new ArrayList<>();


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_listas, container, false);
        ListasGuardadasView=view.findViewById(R.id.RecyclerListasGuardadas);
        ListasGuardadasView.setLayoutManager(new LinearLayoutManager(getContext()));

        LocalDB localDB=new LocalDB(getContext());
        AdapterListaGuardada=new RecyclerListaGuardadaAdapter(localDB.ListaGuardada(), ListasFragment.this);
        ListasGuardadasView.setAdapter(AdapterListaGuardada);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
        AdapterListaGuardada.Valores(position);


    }
}