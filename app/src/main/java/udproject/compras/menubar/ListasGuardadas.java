package udproject.compras.menubar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.item_ListaGuardada;
import udproject.compras.BD.LocalDB;
import udproject.compras.R;
import udproject.compras.mainfragments.ListasFragment;
import udproject.compras.recycler.RecyclerListaGuardadaAdapter;

public class ListasGuardadas extends AppCompatActivity implements RecyclerListaGuardadaAdapter.OnItemGuardadoListener{

    private RecyclerView ListasGuardadasView;
    private RecyclerListaGuardadaAdapter AdapterListaGuardada;
    List<item_ListaGuardada> SavedList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_guardadas);


        ListasGuardadasView=findViewById(R.id.RecyclerListasGuardadas);
        ListasGuardadasView.setLayoutManager(new LinearLayoutManager(this));

        LocalDB localDB=new LocalDB(this);
        AdapterListaGuardada=new RecyclerListaGuardadaAdapter(localDB.ListaGuardada(), this);
        ListasGuardadasView.setAdapter(AdapterListaGuardada);
    }

    @Override
    public void onItemClick(int position) {

    }
}