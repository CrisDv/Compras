    package udproject.compras.FrDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import udproject.compras.R;
import udproject.compras.firebase.LocalDB;
import udproject.compras.fragments.Ingresar_Lista;

    public class IngresarPorTexto extends DialogFragment {

        TextView Nombre, Precio, Cantidad;

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
        Cantidad=getDialog().findViewById(R.id.DialogCantidadProducto);

        LocalDB localDB=new LocalDB(getContext());
        int IDRandom= (int) (Math.floor(Math.random() * (500 - 1)) + 1);//Math.floor(Math.random() * (max - min)) + min;

        try {

            localDB.AgregarProducto(IDRandom, Nombre.getText().toString(), Integer.parseInt(Precio.getText().toString()), Cantidad.getText().toString());
            Toast.makeText(getContext(), Nombre.getText().toString()+" bruh "+Precio.getText().toString()+" "+Cantidad.getText().toString(), Toast.LENGTH_LONG).show();
            Ingresar_Lista ig=new Ingresar_Lista();
            ig.crearItem();
        }
        catch (Exception e)
        {
            System.out.println("ERROR EN:"+ e);
        }
//        localDB.AgregarProducto();

        Nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("Escribiendo "+Nombre.getText().toString());
            }
        });
    }
}