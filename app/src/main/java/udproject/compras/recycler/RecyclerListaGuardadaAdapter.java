package udproject.compras.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import udproject.compras.Adapters.item_ListaGuardada;
import udproject.compras.ConversionLetras;
import udproject.compras.R;
import udproject.compras.BD.LocalDB;

public class RecyclerListaGuardadaAdapter extends RecyclerView.Adapter<RecyclerListaGuardadaAdapter.ViewHolder> {

    private OnItemGuardadoListener mItemListener;
    private boolean shimmer;
    int SHIMMER_ITEM=10;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView TituloLista, CantPresupuesto, Almacen;

        //ShimmerFrameLayout shimmerFrameLayout;

        OnItemGuardadoListener onItemListener;

        public ViewHolder(View itemView, OnItemGuardadoListener onItemListener)
        {
            super(itemView);
          //  shimmerFrameLayout=itemView.findViewById(R.id.shimerItemHistorial);
            TituloLista=itemView.findViewById(R.id.TituloLista);
            CantPresupuesto=itemView.findViewById(R.id.PresupuestoListaGuardada);
            Almacen=itemView.findViewById(R.id.AlmacenGuardado);
            this.onItemListener=onItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public List<item_ListaGuardada> ItemListaGuardada;

    public RecyclerListaGuardadaAdapter(List<item_ListaGuardada>GuardadoLista, OnItemGuardadoListener onItemguardadoListener)
    {
        this.ItemListaGuardada=GuardadoLista;
        this.mItemListener=onItemguardadoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_guardada, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mItemListener);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConversionLetras conversionLetras=new ConversionLetras();
        holder.TituloLista.setText(ItemListaGuardada.get(position).getNombreLista());
        holder.CantPresupuesto.setText("$"+conversionLetras.SeparadorFormat(String.valueOf(ItemListaGuardada.get(position).getPresupuestoInicial())));
        holder.Almacen.setText(ItemListaGuardada.get(position).getAlmacenDeCompra());

    }

    public void Valores(int posicion){
        LocalDB localDB=new LocalDB(context);
        Toast.makeText(context, ItemListaGuardada.get(posicion).getIDLista(), Toast.LENGTH_LONG).show();
        String id=ItemListaGuardada.get(posicion).getIDLista();
        localDB.Productos(id);
        localDB.close();
    }

    public  interface OnItemGuardadoListener
    {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return ItemListaGuardada.size();
    }
}
