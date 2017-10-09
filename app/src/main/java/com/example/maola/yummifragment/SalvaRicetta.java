package com.example.maola.yummifragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSalvaRicettaFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SalvaRicetta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalvaRicetta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String persInizio = "param1";
    static final String doseInizio = "param2";
    static final String persFine = "param3";
    static final String doseFine = "param4";
    static final String titolo = "param5";
    static final String note = "param6";
    static final String id = "param7";



    final static String ARG_POSITION = "position";
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.salva_ricette_titolo)
    EditText salvaRicetteTitolo;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.salva_ricette_corpo)
    EditText salvaRicetteCorpo;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.salva_ricette_note)
    EditText salvaRicetteNote;
    @Bind(R.id.textView8)
    TextView textView8;
    @Bind(R.id.salva_ricette_originale)
    TextView salvaRicetteOriginale;


    Button salvaRicetta;
    private String corpoRicettaSalvataString;
    private String personeRicettaSalvataString;
    private String corpoRicettaOriginaleSalvataString;
    private String personeRicettaOriginaleSalvataString;


    private String salvate_ricetta_titolo;
    private EditText editTitoloRicettaSalvata;
    private EditText editCorpoRicettaSalvata;
    private EditText salva_ricette_note;
    private TextView edtCorpo_RicettaOriginale;
    private Button btn_conferma;
    private Button btn_annulla;
    private Long idRicettaDaModificare;
    private boolean RicettaEsistente;

    // TODO: Rename and change types of parameters
    private String getPersInizio;
    private String getDoseInizio;
    private String getPersFine;
    private String getDoseFine;
    private String getNote;
    private String getTitolo;
    private Long getIdRicettaDaModificare;


    private OnSalvaRicettaFragmentInteractionListener mListener;

    public SalvaRicetta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalvaRicetta.
     */
    // TODO: Rename and change types and number of parameters
    public static SalvaRicetta newInstance(String param1, String param2) {
        SalvaRicetta fragment = new SalvaRicetta();
        Bundle args = new Bundle();
        args.putString(persInizio, param1);
        args.putString(persFine, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RicettaEsistente = getArguments().getBoolean("isRicettaEsistente");
        if (getArguments() != null) {
            getPersInizio = getArguments().getString(persInizio);
            getDoseInizio = getArguments().getString(doseInizio);
            getPersFine = getArguments().getString(persFine);
            getDoseFine = getArguments().getString(doseFine);
        }
        if(RicettaEsistente){
            getIdRicettaDaModificare = getArguments().getLong(id);
            getNote = getArguments().getString(note);
            getTitolo = getArguments().getString(titolo);
        }

        Log.i("Parametri SalvaRicetta", getPersInizio + getDoseInizio + getPersFine + getDoseFine);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_salva_ricetta, container, false);

        //-----------REFERENCES------------------//
        editTitoloRicettaSalvata = (EditText) rootView.findViewById(R.id.salva_ricette_titolo);
        editCorpoRicettaSalvata = (EditText) rootView.findViewById(R.id.salva_ricette_corpo);
        edtCorpo_RicettaOriginale = (TextView) rootView.findViewById(R.id.salva_ricette_originale);
        salva_ricette_note = (EditText) rootView.findViewById(R.id.salva_ricette_note);
        btn_annulla = (Button)rootView.findViewById(R.id.annulla);
        btn_conferma = (Button)rootView.findViewById(R.id.conferma);

        //-------------------------------------//

        btn_annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btn_conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });


//        Bundle bundle = this.getArguments();
//        RicettaEsistente = false;

//        if (bundle != null) {
//            RicettaEsistente = bundle.getBoolean("RicettaEsistente");
//            personeRicettaSalvataString = bundle.getString("persFine");
//            personeRicettaOriginaleSalvataString = bundle.getString("persInizio");


//            if (RicettaEsistente) {
//                idRicettaDaModificare = bundle.getLong("idRicettaSalvata");
//                editTitoloRicettaSalvata.setText(bundle.getString("titolo_ricetta_salvata"));
                editCorpoRicettaSalvata.setText(getDoseFine);
                edtCorpo_RicettaOriginale.setText(getDoseInizio);
        if(RicettaEsistente){
            editTitoloRicettaSalvata.setText(getTitolo);
            salva_ricette_note.setText(getNote);
        }
//                salva_ricette_note.setText(bundle.getString("ricetta_note"));

//            } else {
//
//                corpoRicettaSalvataString = bundle.getString("doseFine");
//
//                corpoRicettaOriginaleSalvataString = bundle.getString("doseInizio");

//                edtCorpo_RicettaOriginale.setText(corpoRicettaOriginaleSalvataString);
//            }




            ButterKnife.bind(this, rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            String titoloRicettaSalvata = salvate_ricetta_titolo = editTitoloRicettaSalvata.getText().toString();


            String doseRicettaModificata = editCorpoRicettaSalvata.getText().toString();
            String noteRicettaModificata = salva_ricette_note.getText().toString();
            String doseRicettaOriginale = edtCorpo_RicettaOriginale.getText().toString();
            Long id = 0l;
            if(RicettaEsistente){
                id = getIdRicettaDaModificare;
            }

            mListener.salva(titoloRicettaSalvata, doseRicettaModificata, noteRicettaModificata, doseRicettaOriginale, getPersInizio, getPersFine, id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSalvaRicettaFragmentInteractionListener) {
            mListener = (OnSalvaRicettaFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCalcoloDosiInteractionListener");
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
    }

//    public void onViewClicked() {
//
//        }

    public void sendDatatoDB() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            RicettaEsistente = bundle.getBoolean("RicettaEsistente");
            personeRicettaSalvataString = bundle.getString("persFine");
            personeRicettaOriginaleSalvataString = bundle.getString("persInizio");
        }
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
    public interface OnSalvaRicettaFragmentInteractionListener {
        // TODO: Update argument type and name
        void salva(String titolo, String doseModificata, String note, String doseOriginale, String persInizio, String persFine, Long id);
    }

}

