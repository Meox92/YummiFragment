package com.example.maola.yummifragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maola.yummifragment.Adapter.RicetteSalvateAdapter;
import com.example.maola.yummifragment.Database.DaoSession;
import com.example.maola.yummifragment.Database.SalvateDB;
import com.example.maola.yummifragment.Database.SalvateDBDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaRicetteSalvate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaRicetteSalvate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     static final String ARG_PARAM1 = "param1";
     static final String ARG_PARAM2 = "param2";

    @Bind(R.id.recyclerViewRicette)
    RecyclerView recyclerViewRicette;
    @Bind(R.id.textView_avviso)
    TextView textViewAvviso;

    // TODO: Rename and change types of parameters
    private List mParam1;
    private String mParam2;

    private List lista;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerViewRicetteSalvate;
    private RicetteSalvateAdapter ricetteSalvateAdapter;

    private SQLiteOpenHelper sqLiteOpenHelper;
    private  DaoSession daoSession;

    public ListaRicetteSalvate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaRicetteSalvate.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaRicetteSalvate newInstance(List param1, String param2) {
        ListaRicetteSalvate fragment = new ListaRicetteSalvate();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
            lista = (List)getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_lista_ricette_salvate, container, false);






        MainActivity mainActivity = new MainActivity();
        daoSession = mainActivity.dbConnection(getContext());
//        DaoSession daoSession = ((LeaseApplication) getApplicationContext()).getDaoSession();
        lista = daoSession.getSalvateDBDao().loadAll();
        if(lista.size() == 0){
            TextView avviso = (TextView)rootView.findViewById(R.id.textView_avviso);
            avviso.setVisibility(View.VISIBLE);
        }else{
        recyclerViewRicetteSalvate = (RecyclerView)rootView.findViewById(R.id.recyclerViewRicette);
        ricetteSalvateAdapter = new RicetteSalvateAdapter(lista);
        recyclerViewRicetteSalvate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewRicetteSalvate.setAdapter(ricetteSalvateAdapter);
        }

        ButterKnife.bind(this, rootView);
        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSalvaRicettaFragmentInteractionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        daoSession.clear();
        Log.i("ListaRicette", "onDestroy");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
