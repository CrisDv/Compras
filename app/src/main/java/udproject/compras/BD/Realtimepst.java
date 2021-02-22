package udproject.compras.BD;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Producto;

public class Realtimepst {

    private Context context;

//save the context recievied via constructor in a local variable

    public Realtimepst(Context context){
        this.context=context;
    }

    public void CrearUsuario()
    {
        DatabaseReference mDBReference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth ma=FirebaseAuth.getInstance();
/*
        mDBReference.child(ma.getCurrentUser().getEmail()).child("user").setValue(ma.getCurrentUser().getEmail());
        mDBReference.child("A").child("xde").setValue(ma.getCurrentUser().getEmail());
        mDBReference.child("A").child("dee").setValue(ma.getCurrentUser().getEmail());
        mDBReference.child("A").child("ee").setValue(ma.getCurrentUser().getEmail());*/

        mDBReference.child("Users").child(ma.getCurrentUser().getUid()).child("nombre").setValue(ma.getCurrentUser().getDisplayName());
        mDBReference.child("Users").child(ma.getCurrentUser().getUid()).child("Mail").setValue(ma.getCurrentUser().getEmail());

        
        //mDBReference.child("Users").child(ma.getCurrentUser().getUid()).child("Cantidad De Listas").setValue(0);
    }

    public void IngresarLista(String IDLista, String NombreLista, String NombreAlmacen, String Presupuesto, String Mes){
        DatabaseReference dbreferebce=FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();



        dbreferebce.child("Users").child(mAuth.getCurrentUser().getUid()).child("Listas").child(IDLista).child("Nombre de la Lista").setValue(NombreLista);
        dbreferebce.child("Users").child(mAuth.getCurrentUser().getUid()).child("Listas").child(IDLista).child("Nombre del Almacen").setValue(NombreAlmacen);
        dbreferebce.child("Users").child(mAuth.getCurrentUser().getUid()).child("Listas").child(IDLista).child("Presupuesto").setValue(Presupuesto);
        dbreferebce.child("Users").child(mAuth.getCurrentUser().getUid()).child("Listas").child(IDLista).child("Mes de la lista").setValue(Mes);

        SharedPreferences sharedPref = context.getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String ListID=sharedPref.getString("IDlista", "NO HAY NADA");
        LocalDB localDB=new LocalDB(context);

        for (int i=0;i<localDB.ListaProducto(ListID).size();i++)
        {
            dbreferebce.child("Users").child(mAuth.getCurrentUser().getUid()).
                    child("Listas").child(IDLista).child("Productos")
                    .child(localDB.ListaProducto(ListID).get(i).getNombre()).setValue(localDB.ListaProducto(ListID).get(i).getPreciou());
        }

    }

}
