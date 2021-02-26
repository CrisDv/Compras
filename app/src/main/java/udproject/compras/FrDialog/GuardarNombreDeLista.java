package udproject.compras.FrDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.BD.Realtimepst;
import udproject.compras.R;
import udproject.compras.BD.LocalDB;

public class GuardarNombreDeLista extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_nombre_de_la_lista, null))
                // Add action buttons
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText Nombre=getDialog().findViewById(R.id.NombreDeLista);
                        EditText Almacen=getDialog().findViewById(R.id.NombreDelAlmacen);
                        String valNombre=Nombre.getText().toString();
                        String Almacentext=Almacen.getText().toString();
                        if (valNombre.equals("") || Almacentext.equals("")){
                            Toast.makeText(getContext(), "Inserta caracteres validos", Toast.LENGTH_LONG).show();
                        }
                        else{
                            LocalDB localDB=new LocalDB(getContext());
                            SharedPreferences sharedPref = getContext().getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
                            String idl=sharedPref.getString("IDlista", "NO HAY NADA");
                            String Presupuesto=sharedPref.getString("Presupuesto", "nel");
                            String Mes=sharedPref.getString("Mes", "nel");
                            FirebaseAuth ma=FirebaseAuth.getInstance();
                            if (ma.getCurrentUser()!=null){
                                Realtimepst realtimepst=new Realtimepst(getContext());
                                realtimepst.IngresarLista(idl, valNombre, Almacentext, Presupuesto, Mes);
                            }
                            localDB.GuardarLista(idl, valNombre, Almacentext);
                            Toast.makeText(getContext(), "Lista Guardada", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GuardarNombreDeLista.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
