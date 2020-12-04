package udproject.compras.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import udproject.compras.Adapters.item_ListaGuardada;
import udproject.compras.R;

public class RecyclerListaGuardadaAdapter extends RecyclerView.Adapter<RecyclerListaGuardadaAdapter.ViewHolder> {

    private OnItemGuardadoListener mItemListener;
    private boolean shimmer;
    int SHIMMER_ITEM=10;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView TituloItem, ListaPrecioTotal, ProductosEnTotal;

        ShimmerFrameLayout shimmerFrameLayout;

        OnItemGuardadoListener onItemListener;

        public ViewHolder(View itemView, OnItemGuardadoListener onItemListener)
        {
            super(itemView);
            shimmerFrameLayout=itemView.findViewById(R.id.shimerItemHistorial);
            TituloItem=itemView.findViewById(R.id.TituloItemHistorial);
            ProductosEnTotal=itemView.findViewById(R.id.ProductosTotal);
            ListaPrecioTotal=itemView.findViewById(R.id.PrecioTotalLista);

            this.onItemListener=onItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public List<item_ListaGuardada> ItemHistorialList;

    public RecyclerListaGuardadaAdapter(List<item_ListaGuardada>GuardadoLista, OnItemGuardadoListener onItemguardadoListener)
    {
        this.ItemHistorialList=GuardadoLista;
        this.mItemListener=onItemguardadoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mItemListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    public  interface OnItemGuardadoListener
    {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
