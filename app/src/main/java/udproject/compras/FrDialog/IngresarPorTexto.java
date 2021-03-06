    package udproject.compras.FrDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import udproject.compras.R;
import udproject.compras.BD.LocalDB;
import udproject.compras.recycler.RecyclerProductAdapter;

    public class IngresarPorTexto extends DialogFragment {

        TextView Nombre, Precio, Cantidad;
        Button Mas, Menos;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.dialog_ingresar_por_texto, container, false);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(inflater.inflate(R.layout.dialog_ingresar_por_texto, null))
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AgregarProducto();

                    }
                });
        return  builder.create();
    }

    private void AgregarProducto()
    {
        Nombre=getDialog().findViewById(R.id.DialoogNombreProducto);
        Precio=getDialog().findViewById(R.id.DialogPrecioProducto);
        //Cantidad=getDialog().findViewById(R.id.DialogCantidadProducto);

        LocalDB localDB=new LocalDB(getContext());
        int IDRandom= (int) (Math.floor(Math.random() * (5000 - 1)) + 1);//Math.floor(Math.random() * (max - min)) + min;




        try {

            //localDB.AgregarProducto(IDRandom, Nombre.getText().toString(), Integer.parseInt(Precio.getText().toString()), Integer.parseInt(Cantidad.getText().toString()));
            localDB.AgregarProducto(IDRandom, Nombre.getText().toString(), Integer.parseInt(Precio.getText().toString()), 1);

        }
        catch (Exception e)
        {
            System.out.println("ERROR EN:"+ e);
        }

    }

    }