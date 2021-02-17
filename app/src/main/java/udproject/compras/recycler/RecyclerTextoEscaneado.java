package udproject.compras.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Texto_Scanner;
import udproject.compras.R;
import udproject.compras.Recognition.Camara;
import udproject.compras.firebase.LocalDB;
import udproject.compras.mainfragments.ListasFragment;

public class RecyclerTextoEscaneado extends RecyclerView.Adapter<RecyclerTextoEscaneado.ViewHolder> {

     OnItemCheckedListener checkedListener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Texto;
        private CheckBox checkBox;

        OnItemCheckedListener oncheckedListener;
        public ViewHolder(@NonNull View itemView, OnItemCheckedListener checkedListener) {
            super(itemView);
            Texto=itemView.findViewById(R.id.TextoDeCamara);
            checkBox=itemView.findViewById(R.id.check);

            oncheckedListener=checkedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            checkedListener.onItemCheckedListener(getAdapterPosition());
        }
    }

    public List<Item_Texto_Scanner> texto_scannersList;

    public RecyclerTextoEscaneado(List<Item_Texto_Scanner>texto_scannersList, OnItemCheckedListener Listener){
        this.texto_scannersList=texto_scannersList;
        checkedListener=Listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_texto_escaneado, parent, false);
       ViewHolder viewHolder=new ViewHolder(view, checkedListener);
       context=parent.getContext();
       return viewHolder;
    }
    int counter=0;
    String Nombre, Precio;
    @Override
    public void onBindViewHolder(@NonNull RecyclerTextoEscaneado.ViewHolder holder, int position) {
        holder.Texto.setText(texto_scannersList.get(position).getTexto());

        if (holder.checkBox.isChecked()){
            texto_scannersList.get(position).setCheck(true);
        }
        else {
            texto_scannersList.get(position).setCheck(false);
        }



        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (holder.checkBox.isChecked()){
                    counter++;

                    System.out.println(counter);
                }else{
                    counter--;
                    System.out.println(counter);
                }

                if (counter==1){
                    Nombre=texto_scannersList.get(position).getTexto();
                }else if(counter==2){
                    Precio=texto_scannersList.get(position).getTexto();
                    System.out.println("asjdjadhb "+Nombre+"16546"+Precio+" ");
                    Camara kca=new Camara();
                    Add(Nombre, Precio);

                }

                if (counter>=3){
                    holder.checkBox.setChecked(false);
                    Toast.makeText(context, "Solo puedes Elegir 2 opciones,  el nombre y precio del producto", Toast.LENGTH_LONG).show();
                    counter--;
                }
            }
        });
    }


    private void Add(String Nombre, String Precio){
        int IDRandom= (int) (Math.floor(Math.random() * (5000 - 1)) + 1);//Math.floor(Math.random() * (max - min)) + min;
        LocalDB localDB=new LocalDB(context);
        String a= Precio.replace("$", " ").replace(".", " ").replace(" ", "").replace(",", "").replace("S", " ").
                replace("D", "");
        int PrecioU=Integer.parseInt(a);
        localDB.AgregarProducto(IDRandom, Nombre, PrecioU, 1);
        localDB.close();
    }
    public interface OnItemCheckedListener
    {
        void onItemCheckedListener(int position);

    }

    @Override
    public int getItemCount() {
        return (texto_scannersList.size()==0)?0:texto_scannersList.size();
    }
}
