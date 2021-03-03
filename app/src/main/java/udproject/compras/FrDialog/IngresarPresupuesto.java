package udproject.compras.FrDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import udproject.compras.ConversionLetras;
import udproject.compras.MainActivity;
import udproject.compras.R;
import udproject.compras.BD.LocalDB;

import static android.content.Context.MODE_PRIVATE;

public class IngresarPresupuesto extends DialogFragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.dialog_escribir_presupuesto, container, false);
        LocalDB local=new LocalDB(getActivity());

        System.out.println(String.valueOf(local.Sugerido())+"yoasobi");
       /* TextView sugerido=view.findViewById(R.id.PresupuestoSugerido);
        sugerido.setText("De acuerdo a tus listas, te sugerimos este presupuesto"+String.valueOf(local.Sugerido()));*/
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layou

        builder.setView(inflater.inflate(R.layout.dialog_escribir_presupuesto, null))
                // Add action buttons
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText Cant=getDialog().findViewById(R.id.create_presupuesto);

                        //TextView TextPresupuesto=getActivity().findViewById(R.id.cantidad_presupuesto);
                        String Valor=Cant.getText().toString();


                        if (Cant.getText().toString().equals(" "))
                        {
                            //Toast.makeText(getActivity(), "INGRESA UN PRESUPUESTO VALIDO", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            //textChange(TextPresupuesto, Valor);
                            LocalDB local=new LocalDB(getActivity());
                            SharedPreferences shared = getActivity().getSharedPreferences("CREDENCIALES", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("Presupuesto", Valor);
                            editor.commit();



                            local.AgregarALista(Integer.parseInt(Valor));

local.close();
                            Intent intent=new Intent(getContext(), MainActivity.class);
                            startActivity(intent);

                        }

                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        IngresarPresupuesto.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    private void textChange(TextView txtPresupuesto, String TotalIngresado)
    {
        try {
            ConversionLetras con=new ConversionLetras();
            txtPresupuesto.setText("Presupuesto: $"+con.SeparadorFormat(TotalIngresado));
        }
        catch (Exception ex)
        {
            System.out.println("EL ERROR EN: "+ex);
        }
    }
}