package com.example.maola.yummifragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCalcoloDosiInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalcolatoreDosi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcolatoreDosi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnCalcoloDosiInteractionListener mListener;



    @Bind(R.id.calcola)
    Button calcola;
    @Bind(R.id.reset)
    Button reset;
    @Bind(R.id.editText3)
    TextView editText3;
    @Bind(R.id.salva)
    Button salva;


    private int valoreSpinner;
    private EditText edt_nPersInit;
    private EditText edt_nPersFine;

    private EditText edt_doseInit;
    private EditText edt_doseFine;
    private ImageView iconaReset;
    private ImageView iconaSalva;
    private RadioGroup radioGroup;
    private RadioButton rb_qnt_ingr;
    private RadioButton rb_ingr_qnt;

    private String[] args = {};
    private String stringI;
    private String substring;
    private String str;

    private double intPersFine;
    private double intPersOrigine;
    private SharedPreferences sharedPreferences;
    private String preferenceFile = "My_preferences";
    private String valorePersFine;
    private String valorePersInizio;
    private int counter;

    private String edt_doseFineString;
    private String edt_persFine;
    private String edt_doseInizioString;
    private String edt_persInizio;



    public CalcolatoreDosi() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcolatoreDosi.
     */
    // TODO: Rename and change types and number of parameters
    public static CalcolatoreDosi newInstance(String param1, String param2) {
        CalcolatoreDosi fragment = new CalcolatoreDosi();
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
        View rootView = inflater.inflate(R.layout.fragment_calcolatore_dosi, container, false);

        //--------------References-------------------------------------------//

        edt_nPersInit = (EditText) rootView.findViewById(R.id.nPersoneInit);
        edt_nPersFine = (EditText) rootView.findViewById(R.id.nPersoneFine);
        edt_doseInit = (EditText) rootView.findViewById(R.id.edit_init);
        edt_doseFine = (EditText) rootView.findViewById(R.id.edit_fine);
        iconaReset = (ImageView) rootView.findViewById(R.id.icona_reset);
        iconaSalva = (ImageView) rootView.findViewById(R.id.icona_cuore);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
        rb_ingr_qnt = (RadioButton)rootView.findViewById(R.id.rbingrqnt);
        rb_qnt_ingr = (RadioButton)rootView.findViewById(R.id.rbqntingr);

        TextView titolo = (TextView)rootView.findViewById(R.id.titolo);

        sharedPreferences = this.getActivity().getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

        //------End References-------------------------------//

        //------------Impostazioni Default primo avvio---------------------------//
        String sharPrefDoseInit = sharedPreferences.getString("pref_inizio", "4");
        String sharPrefDoseFin = sharedPreferences.getString("pref_fine", "3");
        String sharRBposizione = sharedPreferences.getString("pref_posizione", "rb_qnt_ingr");


        edt_nPersInit.setText(sharPrefDoseInit);
        edt_nPersFine.setText(sharPrefDoseFin);
        radioGroup.check(R.id.rbqntingr);


        if(sharRBposizione.equals("rb_qnt_ingr")){
            rb_qnt_ingr.setChecked(true);
        }else{
            rb_ingr_qnt.setChecked(true);
        }


        //-----Fa comparire l'icona x per cancellare i campi se il campo non è vuoto ----
        edt_doseInit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                iconaReset.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iconaReset.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                Log.d("TAG", "afterTextChanged: ");
            }
        });


        //--------------------------------------------------//


        //Cliccando sull'icona reset vengono puliti i campi della schermata
        iconaReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        //Cliccando sull'icona viene salvata la ricetta in preferiti
        iconaSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onButtonPressed();
            }
        });

        //--------------------------------------------------//
        //-----Set il font del titolo a inizio pagina.
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Bitter-Regular.ttf");
        titolo.setTypeface(face);


        Button btn_salva = (Button) rootView.findViewById(R.id.salva);
        btn_salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });
        ButterKnife.bind(this, rootView);

        return rootView;
    }


    //--------Custom Methods----------------//
    public void setSharedPreferences() {
        //---------Salva automaticamente l'ultimo valore impostato negli edtText persone inizio e fine
        SharedPreferences.Editor editor = sharedPreferences.edit();
        valorePersInizio = edt_nPersInit.getText().toString();
        valorePersFine = edt_nPersFine.getText().toString();
        editor.putString("pref_inizio", valorePersInizio);
        editor.putString("pref_fine", valorePersFine);

        //TODO Modificare il valore con un boolean invede di String
        String posizioneRB = "rb_ing_qnt";
        if(rb_qnt_ingr.isChecked())
        {
            posizioneRB = "rb_qnt_ingr";
        }


        editor.putString("pref_posizione", posizioneRB);

        editor.commit();

    }

    /*
    * Funzione portante dell'app.
    * L'utente inserisce il nr di persone per cui è stata scritta la ricetta e il nr di persone per cui vuole ottenere la ricetta.
    * Inserisce la ricetta che viene calcolata nel seguente modo:
    * -Dividi la stringa in substinghe ad ogni " " (spazio) o enter("\\r?\\n"))
    * -Per ogni substringa(quindi ogni parola) prova a convertirla in tipo double.
    * Se è possibile, controlla che il nr in questione non sia collegato a tempi di cottura
    * o temperatura, calcola la proporzione e poi aggiungila nell'array ricettaFinale, se non è possibile
    * aggiungile in ricettaFinale come normale parola.
    *
    * */
    public void split() {

        if (!edt_nPersInit.getText().toString().equals("") && !edt_nPersFine.getText().toString().equals("")) {

            intPersFine = Double.parseDouble(edt_nPersFine.getText().toString());
            intPersOrigine = Double.parseDouble(edt_nPersInit.getText().toString());


            String finale = "";
            //str = edt_doseInit.getText().toString() + " \\|";//agggiungi un carattere per rendere effettivo split con nr finale
            str = edt_doseInit.getText().toString();//agggiungi un carattere per rendere effettivo split con nr finale

            //String[] parts = str.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            //String[] parts = str.split("\\W+"); \\W+ indica un carattere non alfanumerico, non va bene per 1/2


            String[] parts = str.split("\\n|\\ |\\|");

            //Se la lingua del device è cinese l'array di stringhe verrà diviso dopo ogni singolo carattere
//            Log.i("language", Locale.getDefault().getDisplayLanguage());
            if(Locale.getDefault().getDisplayLanguage().equals("中文") || Locale.getDefault().getDisplayLanguage().equals("Chinese")){
                parts = str.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            }

//            int x;
//            int ciclo = 0;


            ArrayList<String> ricettaFinale = new ArrayList<String>();

            for (int i = 0; i < parts.length; i++) {
//                ciclo++;
                if (parts[i].equals("1/2")) {
                    String unMezzo = str.replace(str, "0.5");
                    parts[i] = unMezzo;
                }


                try {

                    double doubleValoreSplit = Double.parseDouble(parts[i]);
                    //               str = parts[i];
                    if (i + 1 < parts.length) {
                        //evita il crash se l'ultima parola è un numero, però non viene calcolato a causa del controllo sulla temp. del forno

                        //controllo per non modificare la temperatura del forno. Se è presente la parola "gradi" o il simbolo,
                        //non modifica il valore della temperatura
                        if (!"gradi".equals(parts[i + 1]) && !"°".equals(parts[i + 1]) && !"ore".equals(parts[i + 1]) && !"minuti".equals(parts[i + 1]) && !"ora".equals(parts[i + 1])) {

                            double valoreSplitCalcolatonotRounded = calcolaDosi(doubleValoreSplit, intPersFine, intPersOrigine);
                            String valoreSplitCalcolato = String.format("%.2f", valoreSplitCalcolatonotRounded);
                            //Formattazione del testo in base se la ricetta è in formato quantità ingrediente o viceversa
                            if(rb_qnt_ingr.isChecked()){

                                finale = str.replace(str, "\n" + valoreSplitCalcolato);

                            }else{

                                finale = str.replace(str, valoreSplitCalcolato + "\n");

                            }

                            ricettaFinale.add(finale);
                        } else {
                            ricettaFinale.add("" + doubleValoreSplit);
                        }
                    } else {
                        //Formattazione del testo in base se la ricetta è in formato quantità ingrediente o viceversa
                        if(rb_qnt_ingr.isChecked())
                            ricettaFinale.add("\n" + doubleValoreSplit);
                        else
                            doubleValoreSplit = Double.parseDouble(parts[i]);
                        String valoreSplitCalcolato = calcolaDosi(doubleValoreSplit, intPersFine, intPersOrigine) + "";
                         ricettaFinale.add(valoreSplitCalcolato+"\n");


                    }

                } catch (NumberFormatException e) {

                    //for (x = i; x < ciclo; x++) {//in ricordo di come mi sono inutilmente complicata la vita!!!

                    //se la substring non è calcolabile, inserisci il valore nell'array nella prima posizione libera.
                    ricettaFinale.add(parts[i]);
                    //}

                }
            }
            //-----Serve per creare una string dall'array
            StringBuilder sb = new StringBuilder();
            for (String s : ricettaFinale) {
                sb.append(s);
//                sb.append("\t");
            }


            edt_doseFine.setText(sb);

            //Istruzione uso app
            if(sb.toString().equals(str)) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.ciao)
                        .setMessage(R.string.non_funziona)
                        .setNeutralButton("Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                }).show();
            }


        } else {
            Toast.makeText(getContext(), "Inserisci il numero di persone", Toast.LENGTH_SHORT).show();
        }



    }


    public double calcolaDosi(double qntRicetta, double persFine, double persOrigine) {
        return qntRicetta * persFine / persOrigine;
    }

    public void reset() {
        edt_doseInit.setText("");
        edt_doseFine.setText("");
        iconaReset.setVisibility(View.INVISIBLE);
        iconaSalva.setVisibility(View.INVISIBLE);
    }

    public void getData() {
        Bundle bundle = new Bundle();
        edt_doseFineString = edt_doseFine.getText().toString();
//        if (!edt_doseFineString.equals("")) {
            edt_persFine = edt_nPersFine.getText().toString();
//            bundle.putString("persFine", edt_persFine);
//            bundle.putString("doseFine", edt_doseFineString); // Put anything what you want


            edt_doseInizioString = edt_doseInit.getText().toString();
            edt_persInizio = edt_nPersInit.getText().toString();

//            bundle.putString("persInizio", edt_persInizio);
//            bundle.putString("doseInizio", edt_doseInizioString);

//            SalvaRicetta fragment2 = new SalvaRicetta();
//            fragment2.setArguments(bundle);
//
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.flContent, fragment2)
//                    .addToBackStack(null)
//                    .commit();

//        }
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            getData();
            mListener.goToSave(edt_persInizio, edt_doseInizioString, edt_persFine, edt_doseFineString);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCalcoloDosiInteractionListener) {
            mListener = (OnCalcoloDosiInteractionListener) context;
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
    public interface OnCalcoloDosiInteractionListener {
        void goToSave(String persInizio, String doseInizio, String persFine, String doseFine);
//        public void goToSave();
    }

    @OnClick({R.id.calcola, R.id.reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calcola:
                split();
                setSharedPreferences();
                iconaSalva.setVisibility(View.VISIBLE);
                break;
            case R.id.reset:
                reset();
                break;
//            case R.id.goToSave:
//                goToSave(view);
//                break;
        }


}
}

