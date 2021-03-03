package udproject.compras.FrDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import udproject.compras.BD.LocalDB;
import udproject.compras.R;

import static android.content.Context.MODE_PRIVATE;

public class EditarPresupuesto extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_editar_presupuesto, null))
                // Add action buttons
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText NuevoPresupuesto=getDialog().findViewById(R.id.edit_presupuesto);
                        String val=NuevoPresupuesto.getText().toString();

                        SharedPreferences sharedPref = getContext().getSharedPreferences("CREDENCIALES", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Presupuesto", val);
                        editor.commit();

                        String idl=sharedPref.getString("IDlista", "NO HAY NADA");
                        LocalDB localDB=new LocalDB(getContext());
                        localDB.ActualizarPresupuesto(idl, Integer.parseInt(val));


                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditarPresupuesto.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
