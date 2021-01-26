 package udproject.compras.recycler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;

 public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {

    OnProductListener mProductListener;
    private Context context;
    /*public boolean showShimmer;
    int SHIMMER_ITEMS=7;*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView Nombre, Precio, Descripcion, Cantidad;
        private Button Sumar, Restar;

        OnProductListener productListener;

        //ShimmerFrameLayout shimmerFrame;
        public ViewHolder(View itemView, OnProductListener productListener)
        {
            super(itemView);
            //shimmerFrame=itemView.findViewById(R.id.ShimmerItemProducto);
            Nombre=itemView.findViewById(R.id.TituloProducto);
            Precio=itemView.findViewById(R.id.PrecioProducto);
            Cantidad=itemView.findViewById(R.id.CantidadProducto);
            Sumar=itemView.findViewById(R.id.SumarCantidad);
            Restar=itemView.findViewById(R.id.RestarCantidad);
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
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // holder.shimmerFrame.stopShimmer();
        holder.Nombre.setText(productoList.get(position).getNombre());
        holder.Cantidad.setText(Integer.toString(productoList.get(position).getCantidad()));
        holder.Precio.setText(Integer.toString(productoList.get(position).getPrecio()));


        holder.Sumar.setOnClickListener(view ->{
            int mas=productoList.get(position).getCantidad();
            mas++;
            productoList.get(position).setCantidad(mas);
            holder.Cantidad.setText(Integer.toString(productoList.get(position).getCantidad()));


            int PrecioTotal =productoList.get(position).getPrecio();
            int Cantidad=productoList.get(position).getCantidad();

            int precioFinal=0;
            if (Cantidad>=2){
                precioFinal= PrecioTotal*Cantidad;
            }else{
                precioFinal=precioFinal*Cantidad;
            }

            productoList.get(position).setPrecio(precioFinal);
            holder.Precio.setText(Integer.toString(productoList.get(position).getPrecio()));

            SetCantidad(productoList.get(position).getID(), mas, precioFinal);
        });

            holder.Restar.setOnClickListener(view ->{
                int menos=productoList.get(position).getCantidad();
                menos--;
                productoList.get(position).setCantidad(menos);
                holder.Cantidad.setText(Integer.toString(productoList.get(position).getCantidad()));

                int total=productoList.get(position).getPrecio();
                int preciou=total/productoList.get(position).getCantidad();

                int preciofinal=total-preciou;
                productoList.get(position).setPrecio(preciofinal);
                holder.Precio.setText(Integer.toString(productoList.get(position).getPrecio()));

                SetCantidad(productoList.get(position).getID(), menos, preciofinal);
            });

    }

    private void SetCantidad( int ID, int Cantidad, int precio){
        LocalDB localDB=new LocalDB(context);

        SQLiteDatabase sqLiteDatabase=localDB.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE Productos SET Cantidad="+Cantidad+", Precio_Producto="+precio+" WHERE Id_Producto="+ID+"");
        System.out.println("CANTIDAD ACTUALIZADA EN "+ID+" CON LA CANTIDAD "+Cantidad+" ******");

        sqLiteDatabase.close();
    }

    public interface OnProductListener
    {
        void onProductClick(int posicion);

    }

    @Override
    public int getItemCount() {

        return productoList.size(); //Primero carga los items (SHIMMER) y luego la lista
    }

   /* private int GetPrecioUnitario(int Cantidad, int PrecioTotal){

        int precioUnitario=PrecioTotal/Cantidad;

        return precioUnitario;
    }*/
}