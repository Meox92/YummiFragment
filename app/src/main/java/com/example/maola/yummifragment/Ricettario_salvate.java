package com.example.maola.yummifragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.maola.yummifragment.Database.DaoSession;
import com.example.maola.yummifragment.Database.SalvateDB;
import com.example.maola.yummifragment.Database.SalvateDBDao;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ricettario_salvate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ricettario_salvate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView ricetteSalvateTestoDaModificare;
    private TextView ricettarioPersone;
    private TextView ricetteSalvateTitle;
    private TextView ricettePeople;
    private TextView ricetteSalvateTestoStandardNote;
    private TextView ricetteSalvateTwNote;
    private Button ricetteSalvateBtnModifica;
    private Button ricetteSalvateBtnElimina;
    private Button ricette_salvate_textSalvata;
    private Button ricette_salvate_textOriginale;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Long idRicettaSalvata;
    private SalvateDB salvateDB;

    private OnFragmentInteractionListener mListener;
    private DaoSession daoSession;

    public Ricettario_salvate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ricettario_salvate.
     */
    // TODO: Rename and change types and number of parameters
    public static Ricettario_salvate newInstance(String param1, String param2) {
        Ricettario_salvate fragment = new Ricettario_salvate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ricettario_salvate, container, false);

//---------------References------------------
        ricetteSalvateTitle = (TextView)view.findViewById(R.id.ricette_salvate_title);
        ricetteSalvateTestoDaModificare = (TextView)view.findViewById(R.id.ricette_salvate_Testo_da_modificare);
        ricettePeople = (TextView)view.findViewById(R.id.ricette_people);
        ricettarioPersone = (TextView)view.findViewById(R.id.ricettario_persone);
        ricetteSalvateTestoStandardNote = (TextView)view.findViewById(R.id.ricette_salvate_testo_standard_note);
        ricetteSalvateTwNote = (TextView)view.findViewById(R.id.ricette_salvate_tw_note);
        ricetteSalvateBtnModifica = (Button)view.findViewById(R.id.ricette_salvate_btn_modifica);
        ricetteSalvateBtnElimina = (Button)view.findViewById(R.id.ricette_salvate_btn_elimina);
        ricette_salvate_textSalvata = (Button)view.findViewById(R.id.ricette_salvate_textSalvata);
        ricette_salvate_textOriginale = (Button)view.findViewById(R.id.ricette_salvate_textOriginale);


        MainActivity mainActivity = new MainActivity();
        daoSession = mainActivity.dbConnection(getContext());

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            idRicettaSalvata = bundle.getLong("idRicettaSalvata");
        }

//        dbUtils dbUtils = new dbUtils();

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Bitter-Regular.ttf");
        ricetteSalvateTitle.setTypeface(face);

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        SalvateDBDao salvateDBDao = daoSession.getSalvateDBDao();

        salvateDB = salvateDBDao.load(idRicettaSalvata);
        ricetteSalvateTitle.setText(salvateDB.getTitoloRicettaSalvata());
        ricettePeople.setText(R.string.salvata_il + format1.format(salvateDB.getDatetime()));
        ricetteSalvateTestoDaModificare.setText(salvateDB.getCorpoRicettaSalvata());
        ricettarioPersone.setText(String.valueOf(salvateDB.getPersoneFinali()));
        String note = salvateDB.getNote().toString();
        if (note.length() != 0 && !note.equals("")) {
            ricetteSalvateTestoStandardNote.setVisibility(View.VISIBLE);
            ricetteSalvateTwNote.setVisibility(View.VISIBLE);
            ricetteSalvateTwNote.setText(salvateDB.getNote());
        }

        ricette_salvate_textSalvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ricetteSalvateTestoDaModificare.setText(salvateDB.getCorpoRicettaSalvata());
                ricettarioPersone.setText(String.valueOf(salvateDB.getPersoneFinali()));
            }
        });

        ricette_salvate_textOriginale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ricetteSalvateTestoDaModificare.setText(salvateDB.getCorpoRicettaOriginale());
                ricettarioPersone.setText(String.valueOf(salvateDB.getPersoneOriginali()));
            }
        });






        ricetteSalvateBtnModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifica(v);
            }
        });
        ricetteSalvateBtnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elimina(v);
            }
        });

        ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void modifica(View view) {
        if (mListener != null) {
            mListener.onRicettarioSalvateInteraction(idRicettaSalvata, salvateDB.getTitoloRicettaSalvata(), salvateDB.getCorpoRicettaSalvata(), salvateDB.getNote(),
                    salvateDB.getCorpoRicettaOriginale(), String.valueOf(salvateDB.getPersoneOriginali()), String.valueOf(salvateDB.getPersoneFinali()));
        }
    }

    public void elimina(View view) {
        if (mListener != null) {
            mListener.onRicettarioSalvateElimina(idRicettaSalvata);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.i("Ricettario", "onDetach");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        try {
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Ricettario", "onDestroy");

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
        void onRicettarioSalvateInteraction(Long idRicettaSalvata, String titolo, String doseModificata, String note, String doseOriginale, String persInizio, String persFine);
        void onRicettarioSalvateElimina(Long id);
    }


}
