package udproject.compras.mainfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.FrDialog.IngresarPresupuesto;
import udproject.compras.FrDialog.IngresarPorTexto;
import udproject.compras.R;
import udproject.compras.Recognition.Voice;
import udproject.compras.firebase.LocalDB;
import udproject.compras.recycler.RecyclerProductAdapter;

public class HomeFragment extends Fragment implements RecyclerProductAdapter.OnProductListener, View.OnClickListener{

    private RecyclerView RecyclerItemProductos;
    private RecyclerProductAdapter AdaptadorProducto;
    List<Item_Producto> productoList=new ArrayList<>();
    FloatingActionButton ShowItems, ItemScanner, ItemMic, ItemText;
    Animation HideCircles, ShowCircles;
    boolean isOpen=false;
    RelativeLayout f;
    IngresarPresupuesto frg;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerItemProductos=view.findViewById(R.id.RecyclerProductos);
        RecyclerItemProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences sharedPref = getContext().getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String vid=sharedPref.getString("IDlista", "NO HAY NADA");


        LocalDB local=new LocalDB(getContext());
        AdaptadorProducto = new RecyclerProductAdapter(local.ListaProducto(vid),this);
        RecyclerItemProductos.setAdapter(AdaptadorProducto);

        f=view.findViewById(R.id.FLOAT);

        HideCircles= AnimationUtils.loadAnimation(getContext(), R.anim.hide_animation);
        ShowCircles=AnimationUtils.loadAnimation(getContext(), R.anim.show_animation);

        ShowItems=view.findViewById(R.id.EscogerMetodo);
        ItemMic=view.findViewById(R.id.IngresarPorVoz);
        ItemScanner=view.findViewById(R.id.IngresarPorScanner);
        ItemText=view.findViewById(R.id.IngresarPorTexto);

        ShowItems.setOnClickListener(this);
        ItemText.setOnClickListener(this);
        ItemScanner.setOnClickListener(this);
        ItemMic.setOnClickListener(this);
        local.close();

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


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.EscogerMetodo:
                ChooseInput();
                break;
            case R.id.IngresarPorTexto:
                DialogFragment FragmentTEXT=new IngresarPorTexto();
                FragmentTEXT.show(getParentFragmentManager(), "xde");
                RecyclerItemProductos.getAdapter().notifyDataSetChanged();
                break;
            case R.id.IngresarPorVoz:
                Intent intent=new Intent(getContext(), Voice.class);
                startActivity(intent);
                break;
        }
    }
}