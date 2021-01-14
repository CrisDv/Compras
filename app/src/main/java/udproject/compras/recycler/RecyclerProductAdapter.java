 package udproject.compras.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.R;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {

    OnProductListener mProductListener;
    /*public boolean showShimmer;
    int SHIMMER_ITEMS=7;*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView Nombre, Precio, Descripcion;
        private Button Sumar, Restar;

        OnProductListener productListener;

        //ShimmerFrameLayout shimmerFrame;
        public ViewHolder(View itemView, OnProductListener productListener)
        {
            super(itemView);
            //shimmerFrame=itemView.findViewById(R.id.ShimmerItemProducto);
            Nombre=itemView.findViewById(R.id.TituloProducto);
            Precio=itemView.findViewById(R.id.PrecioProducto);
            Descripcion=itemView.findViewById(R.id.CantidadProducto);
        }
        @Override
        public void onClick(View view) {
            productListener.onProductClick(getAdapterPosition());
        }
    }

    public List<Item_Producto> productoList;
  
    public  RecyclerProductAdapter(List<Item_Producto>productoList, OnProductListener productListener)
    {
        this.productoList=productoList;
        this.mProductListener=productListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mProductListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // holder.shimmerFrame.stopShimmer();
        holder.Nombre.setText(productoList.get(position).getNombre());
        holder.Descripcion.setText(productoList.get(position).getDescripcion());
        holder.Precio.setText(Integer.toString(productoList.get(position).getPrecio()));

    }

    public interface OnProductListener
    {
        void onProductClick(int posicion);
    }

    @Override
    public int getItemCount() {

        return productoList.size(); //Primero carga los items (SHIMMER) y luego la lista
    }
}