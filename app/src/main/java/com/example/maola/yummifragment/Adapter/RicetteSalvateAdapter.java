package com.example.maola.yummifragment.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maola.yummifragment.Database.SalvateDB;
import com.example.maola.yummifragment.R;
import com.example.maola.yummifragment.Ricettario_salvate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Maola on 16/05/2017.
 */

public class RicetteSalvateAdapter extends RecyclerView.Adapter<RicetteSalvateAdapter.ViewHolder>{
    private List<SalvateDB> mData;
    private Context ctx;

    public RicetteSalvateAdapter(List<SalvateDB> data) {
        this.mData = data;
    }

    @Override
    public RicetteSalvateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ctx = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ricette_default_salvate_recview, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new RicetteSalvateAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat df = DateFormat.getDateTimeInstance();



        viewHolder.textViewItem.setText(mData
                .get(position)
                .getTitoloRicettaSalvata());
//        viewHolder.textViewDataSalvataggio.setText("Salvato il "+ format1.format(mData.get(position).getDatetime()));
        viewHolder.textViewDataSalvataggio.setText(ctx.getResources().getString(R.string.salvata_il)+ df.format(mData.get(position).getDatetime()));

        viewHolder.idRicettaSalvata= mData.get(position).getIdSalvate();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewItem;
        public TextView textViewDataSalvataggio;
        public Long idRicettaSalvata;
        public View viewParent;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewItem = (TextView) itemLayoutView.findViewById(R.id.ricette_default_titolo);
            textViewDataSalvataggio = (TextView) itemLayoutView.findViewById(R.id.ricette_default_tCottura);

            viewParent = (View) itemLayoutView.findViewById(R.id.ricette_default_parent);
            viewParent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();


            Bundle bundle = new Bundle();
            Ricettario_salvate myFragment = new Ricettario_salvate();
            bundle.putLong("idRicettaSalvata", idRicettaSalvata); // Put anything what you want

            //Toast.makeText(v.getContext(), "You have clicked " + v.getId() + "id " + categoriaIdLong, Toast.LENGTH_SHORT).show();
            //you can add data to the tag of your cardview in onBind... and retrieve it here with with.getTag().toString()..
            myFragment.setArguments(bundle);



            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, myFragment).addToBackStack(null).commit();

        }

        public void setRicettaId(long id) {
            this.idRicettaSalvata = id;
        }

    }
}

