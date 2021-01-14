package udproject.compras.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Realtimepst {

    DatabaseReference mDBReference;

    public void CrearUsuario()
    {
        mDBReference= FirebaseDatabase.getInstance().getReference();
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

}
