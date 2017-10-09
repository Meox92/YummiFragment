package com.example.maola.yummifragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maola.yummifragment.Database.DaoMaster;
import com.example.maola.yummifragment.Database.DaoSession;
import com.example.maola.yummifragment.Database.SalvateDB;
import com.example.maola.yummifragment.Database.SalvateDBDao;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CalcolatoreDosi.OnCalcoloDosiInteractionListener,
        SalvaRicetta.OnSalvaRicettaFragmentInteractionListener,
        ListaRicetteSalvate.OnFragmentInteractionListener,
        Ricettario_salvate.OnFragmentInteractionListener {

    private final String DB_NAME = "logs-db";
    private DaoSession daoSession;
    private SQLiteDatabase db;

    private SharedPreferences sharPref;
    private String preferenceFile = "My_preferences";
    private int counter;

    private String mParam1;
    private String mParam2;

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "MainActivity";

    private AdView mAdView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.flContent) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            CalcolatoreDosi firstFragment = new CalcolatoreDosi();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flContent, firstFragment).commit();
        }

        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        sharPref = getApplicationContext().getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);


        //------------Impostazioni Default primo avvio---------------------------//
        counter = sharPref.getInt("contatore", 0);
        counter++;
        SharedPreferences.Editor editor = sharPref.edit();
        editor.putInt("contatore", counter);


        editor.commit();

        handlerAlert(15000);
        Log.i("counter", counter+"");


        dbConnection(getApplicationContext());

    }

    public DaoSession dbConnection(Context context){

            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
//            salvateDao = daoSession.getSalvateDBDao();
        return daoSession;

    }

    public void feedback() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    public void handlerAlert(int x) {
        if (counter == 15 || counter == 50 || counter == 200) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    openAlert();
                }
            }, x);
        }
    }


    private void openAlert() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.ciao)
                .setMessage(R.string.richiesta_feedback)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // alert "Ti andrebbe di lasciare una valutazione nel play store?
                                alertILike();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //inserisci testo "Ti dispiacerebbe dirci dove possiamo migliorare?"
                                alertDontLike();

                            }
                        }).show();

    }


    private void alertILike() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.ciao)
                        // inserisci testo "Ti andrebbe di lasciare una valutazione nel play store?
                .setMessage(R.string.feedback_piace)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                feedback();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        }).show();

    }


    private void alertDontLike() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.ciao)
                //inserisci testo "Ti dispiacerebbe dirci dove possiamo migliorare?"
                .setMessage(R.string.feedback_non_piace)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
//                                feedback();
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"meox92@gmail.com"});
                                i.putExtra(Intent.EXTRA_SUBJECT, "Request info");
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail ..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        }).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = CalcolatoreDosi.class;

        switch (id) {
//            case R.id.icona_frusta:
//                fragmentClass = CalcolatoreDosi.class;
//                break;
            case R.id.Calcolo_Dosi:
                fragmentClass = CalcolatoreDosi.class;
                break;
            case R.id.Ricette_salvate:
                fragmentClass = ListaRicetteSalvate.class;
                break;
            case R.id.cottura_carne:
                fragmentClass = CotturaCarneFragment.class;
                break;
            case R.id.legumi_cereali:
                fragmentClass = LegumiCereali.class;
                break;
            case R.id.about:
                fragmentClass = About.class;
                break;
            case R.id.feedback:
                feedback();
                break;
            default:
                fragmentClass = CalcolatoreDosi.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void salva(String titolo, String doseModificata, String note, String doseOriginale, String persInizio, String persFine, Long id) {

//        salvate_ricetta_titolo = editTitoloRicettaSalvata.getText().toString();
        if(titolo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.titolo_non_valido, Toast.LENGTH_LONG).show();
        } else{
            SalvateDB salvateDB = new SalvateDB();
            Long x = Long.valueOf(1);
            salvateDB.setCategoriaId(x);
            salvateDB.setTitoloRicettaSalvata(titolo);
            salvateDB.setPersoneOriginali(Integer.valueOf(persInizio));
            salvateDB.setPersoneFinali(Integer.valueOf(persFine));


            salvateDB.setCorpoRicettaSalvata(doseModificata);
            salvateDB.setNote(note);
            salvateDB.setCorpoRicettaOriginale(doseOriginale);
//            if (RicettaEsistente) {
//                salvateDB.setIdSalvate(idRicettaDaModificare);
//            }


            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd EEE HH:mm:ss");
            //String currentDateandTime = sdf.format(Calendar.getInstance().getTime());

            Date dataSalvataggio = new Date();
            dataSalvataggio.getTime();
            salvateDB.setDatetime(dataSalvataggio);
            if(id!=0l){
                salvateDB.setIdSalvate(id);
            }

            SalvateDBDao salvateDao = daoSession.getSalvateDBDao();
            salvateDao.insertOrReplace(salvateDB);

            //Reindirizzamento sulla pagina delle ricette salvate
            ListaRicetteSalvate myFragment = new ListaRicetteSalvate();
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, myFragment).commit();
            ////////////////////////////////////////////////////

//            Toast.makeText(getApplicationContext(), "Ricetta salvata con successo", Toast.LENGTH_LONG).show();

    }
    }

    @Override
    public void goToSave(String persInizio, String doseInizio, String persFine, String doseFine) {


    if(!doseFine.equals("")){
            // Create fragment and give it an argument for the selected article
            SalvaRicetta newFragment = new SalvaRicetta();
            Bundle args = new Bundle();
            args.putString(SalvaRicetta.persInizio, persInizio);
            args.putString(SalvaRicetta.persFine, persFine);
            args.putString(SalvaRicetta.doseInizio, doseInizio);
            args.putString(SalvaRicetta.doseFine, doseFine);


        newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.flContent, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
    }    else {
        Toast.makeText(getApplicationContext(), R.string.no_calcolo, Toast.LENGTH_SHORT).show();
    }




    }

    @Override
    public void onFragmentInteraction() {


        SalvateDBDao salvateDao = daoSession.getSalvateDBDao();

        ListaRicetteSalvate newFragment = new ListaRicetteSalvate();
        List listaricette = salvateDao.loadAll();

        Bundle args = new Bundle();
        args.putSerializable(ListaRicetteSalvate.ARG_PARAM1, (Serializable)listaricette);
        args.putString(ListaRicetteSalvate.ARG_PARAM2, "stringaListaRicetteSalvate");
        newFragment.setArguments(args);
//        newFragment.newInstance(listaricette, "bla");


    }

    @Override
    public void onRicettarioSalvateInteraction(Long idRicettaSalvata, String titolo, String doseModificata, String note, String doseOriginale, String persInizio, String persFine) {
        SalvaRicetta newFragment = new SalvaRicetta();
        Bundle args = new Bundle();
        args.putLong(SalvaRicetta.id, idRicettaSalvata);
        args.putBoolean("isRicettaEsistente", true);
        args.putString(SalvaRicetta.titolo, titolo);
        args.putString(SalvaRicetta.doseFine, doseModificata);
        args.putString(SalvaRicetta.note, note);
        args.putString(SalvaRicetta.doseInizio, doseOriginale);
        args.putString(SalvaRicetta.persInizio, persInizio);
        args.putString(SalvaRicetta.persFine, persFine);




        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onRicettarioSalvateElimina(final Long id){

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete")
                .setMessage(R.string.elimina_record)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SalvateDBDao salvateDao = daoSession.getSalvateDBDao();
                                salvateDao.deleteByKey(id);

                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                ListaRicetteSalvate myFragment = new ListaRicetteSalvate();

                                transaction.replace(R.id.flContent, myFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        }).show();




    }

}
