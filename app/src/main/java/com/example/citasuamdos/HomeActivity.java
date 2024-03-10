package com.example.citasuamdos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.SearchView;

import com.example.citasuamdos.adapter.ServiceAdapter;

import com.example.citasuamdos.model.Service;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity {

    private static final String KEY_RECYCLER_STATE = "recycler_state";
    private Parcelable recyclerViewState;

    RecyclerView mRecycler;
    ServiceAdapter mAdapter;
    FirebaseFirestore mFirestore;
    SearchView search_view;
    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recycleViewPeople);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        search_view = findViewById(R.id.search);

        query = mFirestore.collection("services");

        // creación del recycler y enlace con el adapter
        FirestoreRecyclerOptions<Service> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Service>().setQuery(query, Service.class).build();

        mAdapter = new ServiceAdapter(this, firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        setUpRecyclerView();
        search_view();
    }


    //Carga del recycler con todos los elementos de la clase
    private void setUpRecyclerView() {
        FirestoreRecyclerOptions<Service> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Service>().setQuery(query, Service.class).build();

        mAdapter = new ServiceAdapter(this, firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
    }

    // Uso del searchview enviando el texto a textSeacrch
    private void search_view() {
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }

    // Búsqueda del texto específico en la propiedad lowerTitle de cada objeto de la clase
    public void textSearch(String s) {
        String searchText = s.toLowerCase();

        FirestoreRecyclerOptions<Service> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Service>()
                        .setQuery(query.orderBy("lowerTitle")
                                .startAt(searchText).endAt(searchText + "\uf8ff"), Service.class).build();

        mAdapter.updateOptions(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    // Código para conservar la app de la misma manera que queda al pasar a otra app, evita caidas por paso a otra app

    // Guarla la instancia del recycler view
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guardar el estado del RecyclerView
        recyclerViewState = mRecycler.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(KEY_RECYCLER_STATE, recyclerViewState);
    }


    //  Restaura el estado guardado en el onSaveInstanceState, para ser llamado en el onResume, cuando vuelve de otra app
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restaurar el estado del RecyclerView
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Restaurar el estado del RecyclerView cuando la actividad se reanuda
        if (recyclerViewState != null) {
            mRecycler.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }
}