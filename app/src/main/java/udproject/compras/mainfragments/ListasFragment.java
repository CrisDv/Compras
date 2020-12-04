package udproject.compras.mainfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import udproject.compras.R;

public class ListasFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_listas, container, false);
        return view;
    }
}