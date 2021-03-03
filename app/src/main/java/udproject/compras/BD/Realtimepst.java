package udproject.compras.BD;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Producto;

public class Realtimepst {

    private Context context;

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

    public void BackUpListas()
    {
        System.out.println("INICIO");
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        LocalDB localDB=new LocalDB(context);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        //traer datos de las listas
        reference.child("Users").child(mAuth.getUid()).child("Listas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataS : snapshot.getChildren()){
                      /* System.out.println(dataS.getKey()+"**"+
                                dataS.child("Presupuesto").getValue().toString()+"A"+
                                dataS.child("Mes de la lista").getValue().toString()+"B"+
                                dataS.child("Nombre del Almacen").getValue().toString()+"C"+
                                dataS.child("Nombre de la Lista").getValue().toString());*/
                       BackUpProductos(dataS.getKey());
                        //for (DataSnapshot thisSnapshot: dataS.child(dataS.getKey()).getChildren()){
                            try {
                                localDB.GuardarListaFirebase(dataS.getKey(),
                                        dataS.child("Presupuesto").getValue().toString(),
                                        dataS.child("Mes de la lista").getValue().toString(),
                                        dataS.child("Nombre del Almacen").getValue().toString(),
                                        dataS.child("Nombre de la Lista").getValue().toString());
                            }
                            catch (Exception e){
                                System.out.println("REALTIME PST EXCEPTION: "+e);
                            }
                            System.out.println("yaxd");

                        //}

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("FINAL");
    }

    public void BackUpProductos(String IDLista){
        System.out.println("INICIO Productos");
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        LocalDB localDB=new LocalDB(context);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(mAuth.getUid()).child("Listas").child(IDLista).child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    System.out.println("PRODUCTOS REALTIMEST "+IDLista+" "+dataSnapshot.getKey()+" "+dataSnapshot.getValue().toString());
                    try {
                        localDB.GuardarProductosFirebase(IDLista, dataSnapshot.getKey(), dataSnapshot.getValue().toString());
                    }
                    catch (Exception r){
                        System.out.println("REALTIME BACKUPO PRODUCTOS "+r);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void BorrarUsuario()
    {
        DatabaseReference dbreferebce=FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();

        dbreferebce.child("Users").child(mAuth.getUid()).removeValue();
    }
}
