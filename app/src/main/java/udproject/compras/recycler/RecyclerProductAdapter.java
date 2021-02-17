 package udproject.compras.recycler;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.DetallesProductos;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;
import udproject.compras.mainfragments.HomeFragment;

 public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {

    OnProductListener mProductListener;
    private Context context;
    /*public boolean showShimmer;
    int SHIMMER_ITEMS=7;*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView Nombre, Precio, Descripcion, Cantidad;
        private Button Sumar, Restar;

        OnProductListener mProductListener;

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

            mProductListener=productListener;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mProductListener.onProductClick(getAdapterPosition());
        }
    }

    public List<Item_Producto> productoList;
  
    public  RecyclerProductAdapter(List<Item_Producto>productoList, OnProductListener Listener)
    {
        this.productoList=productoList;
        mProductListener=Listener;
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

        LocalDB localDB=new LocalDB(context);
        holder.Sumar.setOnClickListener(view ->{
            int mas=productoList.get(position).getCantidad();
            mas++;
            productoList.get(position).setCantidad(mas);
            holder.Cantidad.setText(Integer.toString(productoList.get(position).getCantidad()));

            int Cantidad=productoList.get(position).getCantidad();

            int precioFinal=localDB.PrecioUnitario(productoList.get(position).getID())*Cantidad;


            productoList.get(position).setPrecio(precioFinal);
            holder.Precio.setText(Integer.toString(productoList.get(position).getPrecio()));

            SetCantidad(productoList.get(position).getID(), mas, precioFinal);

        });


            holder.Restar.setOnClickListener(view ->{
                int menos=productoList.get(position).getCantidad();
                menos--;
                productoList.get(position).setCantidad(menos);
                holder.Cantidad.setText(Integer.toString(productoList.get(position).getCantidad()));


                int Cantidad=productoList.get(position).getCantidad();
                int precioFinal=localDB.PrecioUnitario(productoList.get(position).getID())*Cantidad;

                productoList.get(position).setPrecio(precioFinal);
                holder.Precio.setText(Integer.toString(productoList.get(position).getPrecio()));

                SetCantidad(productoList.get(position).getID(), menos, precioFinal);

            });
        localDB.close();
    }

    public void Elimina(int position)
    {
        productoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productoList.size());

        LocalDB localDB=new LocalDB(context);
        int ig=productoList.get(position).getID();
        localDB.EliminarProductoDeLista(ig);

    }
    private void SetCantidad( int ID, int Cantidad, int precio){
        LocalDB localDB=new LocalDB(context);

        SQLiteDatabase sqLiteDatabase=localDB.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE Productos SET Cantidad="+Cantidad+", Precio_Producto="+precio+" WHERE Id_Producto="+ID+"");
        System.out.println("CANTIDAD ACTUALIZADA EN "+ID+" CON LA CANTIDAD "+Cantidad+" ******");

        sqLiteDatabase.close();
    }

    public void GetNamePosition(int posicion){

        String Name=productoList.get(posicion).getNombre();
        System.out.println(Name);
        String[] Palabras=Name.split("\\s+");
        String[] Productos={"Aceite", "Arroz", "Azucar", "Blanqueador", "Cafe", "Cereal", "Cerveza",
                "Chocolate", "Desodorante", "Galletas", "Harina", "Leche", "Lentejas", "Mantequilla",
                "Gelatina", "Shampoo"};
        String NombreFinal="";
        for (int i=0;i<Palabras.length;i++)
        {
            for(int j=0;j<Productos.length;j++){
                if (Palabras[i].equals(Productos[j])){
                    System.out.println("ENCONTRADO: "+Palabras[i]+" - "+Productos[j]);
                    NombreFinal=Productos[j];
                    break;
                }
                else {
                    System.out.println("NOASDADAD");
                }
            }
        }
        Intent intent=new Intent(context, DetallesProductos.class);
        intent.putExtra("NombreProducto", NombreFinal);
        context.startActivity(intent);
    }

    public interface OnProductListener
    {
        void onProductClick(int position);

    }

    @Override
    public int getItemCount() {

        return productoList.size();
    }
}