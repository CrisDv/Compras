package udproject.compras.FrDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import udproject.compras.R;
import udproject.compras.conversion;

public class FragmentDialogPresupuesto extends DialogFragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.dialog_escribir_presupuesto, container, false);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_escribir_presupuesto, null))
                // Add action buttons
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText Cant=getDialog().findViewById(R.id.edit_presupuesto);
                        TextView TextPresupuesto=getActivity().findViewById(R.id.cantidad_presupuesto);
                        String Valor=Cant.getText().toString();
                        if (Cant.getText().toString().equals(" "))
                        {
                            TextPresupuesto.setText("TU PRESUPUESTO");
                        }
                        else
                        {
                            textChange(TextPresupuesto, Valor);
                        }
                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FragmentDialogPresupuesto.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    private void textChange(TextView txtPresupuesto, String TotalIngresado)
    {
        try {
            conversion con=new conversion();
            txtPresupuesto.setText("Tu presupuesto actual: $"+con.SeparadorFormat(TotalIngresado));
        }
        catch (Exception ex)
        {
            System.out.println("EL ERROR EN: "+ex);
        }
    }
}