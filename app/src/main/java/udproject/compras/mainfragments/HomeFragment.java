package udproject.compras.mainfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.FrDialog.FragmentDialogPresupuesto;
import udproject.compras.FrDialog.IngresarPorTexto;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;
import udproject.compras.recycler.RecyclerProductAdapter;

public class HomeFragment extends Fragment implements RecyclerProductAdapter.OnProductListener, View.OnClickListener {

    private RecyclerView RecyclerItemProductos;
    private RecyclerProductAdapter AdaptadorProducto;
    List<Item_Producto> productoList=new ArrayList<>();
    Button ListaYaCreada, CrearLista;
    FloatingActionButton ShowItems, ItemScanner, ItemMic, ItemText;
    Animation HideCircles, ShowCircles;
    boolean isOpen=false;


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerItemProductos=view.findViewById(R.id.RecyclerProductos);
        RecyclerItemProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        ListaYaCreada=view.findViewById(R.id.ListaYaCreada);
        CrearLista=view.findViewById(R.id.CrearLista);


        LocalDB local=new LocalDB(getActivity());
        AdaptadorProducto = new RecyclerProductAdapter(local.ListaProducto(),this);
        RecyclerItemProductos.setAdapter(AdaptadorProducto);

        RelativeLayout f=view.findViewById(R.id.FLOAT);
        if (local.ifExist())
        {
            f.setVisibility(View.VISIBLE);
            RecyclerItemProductos.setVisibility(View.VISIBLE);
            ListaYaCreada.setVisibility(View.GONE);
            CrearLista.setVisibility(View.GONE);
        }
        else
        {
            f.setVisibility(View.GONE );
            RecyclerItemProductos.setVisibility(View.GONE);
            ListaYaCreada.setVisibility(View.VISIBLE);
            CrearLista.setVisibility(View.VISIBLE);
        }

        HideCircles= AnimationUtils.loadAnimation(getContext(), R.anim.hide_animation);
        ShowCircles=AnimationUtils.loadAnimation(getContext(), R.anim.show_animation);

        ShowItems=view.findViewById(R.id.EscogerMetodo);
        ItemMic=view.findViewById(R.id.IngresarPorVoz);
        ItemScanner=view.findViewById(R.id.IngresarPorScanner);
        ItemText=view.findViewById(R.id.IngresarPorTexto);


        CrearLista.setOnClickListener(this);
        ListaYaCreada.setOnClickListener(this);
        ShowItems.setOnClickListener(this);
        ItemText.setOnClickListener(this);
        ItemScanner.setOnClickListener(this);
        ItemMic.setOnClickListener(this);
        return view;
    }


    private void ChooseInput(){

        UpdateFloatingButton(isOpen);

        isOpen= !isOpen;
    }

    private void UpdateFloatingButton(boolean set){
            if (!set){
                ItemScanner.setVisibility(View.VISIBLE);
                ItemMic.setVisibility(View.VISIBLE);
                ItemText.setVisibility(View.VISIBLE);

                ItemScanner.startAnimation(ShowCircles);
                ItemMic.startAnimation(ShowCircles);
                ItemText.startAnimation(ShowCircles);

                ItemScanner.setClickable(true);
                ItemMic.setClickable(true);
                ItemText.setClickable(true);
            }
            else {
                ItemScanner.setVisibility(View.INVISIBLE);
                ItemMic.setVisibility(View.INVISIBLE);
                ItemText.setVisibility(View.INVISIBLE);

                ItemScanner.startAnimation(HideCircles);
                ItemMic.startAnimation(HideCircles);
                ItemText.startAnimation(HideCircles);

                ItemScanner.setClickable(false);
                ItemMic.setClickable(false);
                ItemText.setClickable(false);
            }
    }

    @Override
    public void onProductClick(int posicion) {

    }

    public void CrearLista(String ValorPresupuesto)
    {
        LocalDB local=new LocalDB(getContext());
        String IDh = UUID.randomUUID().toString();
        String IDL=UUID.randomUUID().toString();

        System.out.println(ValorPresupuesto);
        int kha=Integer.parseInt(ValorPresupuesto);
        System.out.println(IDL+" 2 "+IDh+" 3 "+String.valueOf(kha));
        local.AgregarALista(IDL, kha, IDh);

    }
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.CrearLista:
                DialogFragment newFragment=new FragmentDialogPresupuesto();
                newFragment.show(getParentFragmentManager(), "xde");
                break;
            case R.id.EscogerMetodo:
                ChooseInput();
                break;
            case R.id.IngresarPorTexto:
                DialogFragment FragmentTEXT=new IngresarPorTexto();
                FragmentTEXT.show(getParentFragmentManager(), "xde");
        }
    }
}