package com.example.maola.yummifragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LegumiCereali extends Fragment {
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;


    @Bind(R.id.legumi_cereali_e_lv)
    ExpandableListView legumiCerealiELv;

    public LegumiCereali() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_legumi_cereali, container, false);
        ButterKnife.bind(this, rootView);

        prepareListData();
        ExpandableListAdapter adapter = new com.example.maola.yummifragment.Adapter.ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        legumiCerealiELv.setAdapter(adapter);
        legumiCerealiELv.expandGroup(0);



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        Spanned text = Html.fromHtml(getText((R.string.introduzione)).toString());
        listDataHeader.add(text.toString());
        listDataHeader.add(getString(R.string.amaranto));
        listDataHeader.add(getString(R.string.avena));
        listDataHeader.add(getString(R.string.bulghur));
        listDataHeader.add(getString(R.string.cous_cous));
        listDataHeader.add(getString(R.string.farro_decorticato));
        listDataHeader.add(getString(R.string.farro_perlato));
        listDataHeader.add(getString(R.string.grano_saraceno));
        listDataHeader.add(getString(R.string.grano_tenero));
        listDataHeader.add(getString(R.string.miglio));
        listDataHeader.add(getString(R.string.orzo_decorticato));
        listDataHeader.add(getString(R.string.orzo_perlato));
        listDataHeader.add(getString(R.string.quinoa));
        listDataHeader.add(getString(R.string.riso_integrale));
        listDataHeader.add(getString(R.string.riso));
        listDataHeader.add(getString(R.string.segale));






        // Adding child data
        List<String> intro = new ArrayList<String>();
        intro.add(getText(R.string.legumi_cereali).toString());


        List<String> amaranto = new ArrayList<String>();
        amaranto.add(getString(R.string.amarant_metodo));

        List<String> avena = new ArrayList<String>();
        avena.add(getString(R.string.avena_metodo));

        List<String> bulghur = new ArrayList<String>();
        bulghur.add(getString(R.string.bulghur__metodo));

        List<String> cous_cous = new ArrayList<String>();
        cous_cous.add(getString(R.string.cous_cous_metodo));

        List<String> farro_decorticato = new ArrayList<String>();
        farro_decorticato.add(getString(R.string.farro_decorticato_metodo));

        List<String> farro_perlato = new ArrayList<String>();
        farro_perlato.add(getString(R.string.farro_perlato_metodo));

        List<String> grano_saraceno = new ArrayList<String>();
        grano_saraceno.add(getString(R.string.grano_saraceno_metodo));

        List<String> grano_tenero = new ArrayList<String>();
        grano_tenero.add(getString(R.string.grano_tenero_metodo));

        List<String> miglio = new ArrayList<String>();
        miglio.add(getString(R.string.miglio_metodo));

        List<String> orzo_decorticato = new ArrayList<String>();
        orzo_decorticato.add(getString(R.string.orzo_decorticato_metodo));

        List<String> orzo_perlato = new ArrayList<String>();
        orzo_perlato.add(getString(R.string.orzo_perlato_metodo));

        List<String> quinoa = new ArrayList<String>();
        quinoa.add(getString(R.string.quinoa_metodo));

        List<String> riso_integrale = new ArrayList<String>();
        riso_integrale.add(getString(R.string.riso_integrale_metodo));

        List<String> riso = new ArrayList<String>();
        riso.add(getString(R.string.riso_metodo));

        List<String> segale = new ArrayList<String>();
        segale.add(getString(R.string.segale_metodo));



        listDataChild.put(listDataHeader.get(0), intro); // Header, Child data
        listDataChild.put(listDataHeader.get(1), amaranto);
        listDataChild.put(listDataHeader.get(2), avena);
        listDataChild.put(listDataHeader.get(3), bulghur);
        listDataChild.put(listDataHeader.get(4), cous_cous);
        listDataChild.put(listDataHeader.get(5), farro_decorticato);
        listDataChild.put(listDataHeader.get(6), farro_perlato);
        listDataChild.put(listDataHeader.get(7), grano_saraceno);
        listDataChild.put(listDataHeader.get(8), grano_tenero);
        listDataChild.put(listDataHeader.get(9), miglio);
        listDataChild.put(listDataHeader.get(10), orzo_decorticato);
        listDataChild.put(listDataHeader.get(11), orzo_perlato);
        listDataChild.put(listDataHeader.get(12), quinoa);
        listDataChild.put(listDataHeader.get(13), riso_integrale);
        listDataChild.put(listDataHeader.get(14), riso);
        listDataChild.put(listDataHeader.get(15), segale);



    }
}
