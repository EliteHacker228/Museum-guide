package com.example.max.mainwindow.museumpackage;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.max.mainwindow.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class ListFragment extends Fragment {
    ArrayList<Museum> museums = new ArrayList<>();
    MuseumAdapter museumAdapter = new MuseumAdapter(museums);
    TreeMap<String, Museum> musemsTree  = new TreeMap<>();
    RecyclerView recyclerView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final SearchView searchView = (SearchView) menu.findItem(
                R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //
                // Toast.makeText(getContext(), "Вам нужен "
                //        + searchView.getQuery() + "?", Toast.LENGTH_SHORT).show();
                return false;


            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    ArrayList<Museum> lstFound = new ArrayList<>();
                    for(Museum item:museums){
                        if(item.getMname().toLowerCase().contains(newText.toLowerCase())||item.getAdress().toLowerCase().contains(newText.toLowerCase()))
                            lstFound.add(item);
                    }
                    lstFound.sort(new Comparator<Museum>() {
                        @Override
                        public int compare(Museum o1, Museum o2) {
                            float r1 = o1.getUsersRatingValue();
                            float r2 = o2.getUsersRatingValue();
                            return -Double.compare(r1, r2);
                        }
                    });

                    MuseumAdapter adapter = new MuseumAdapter(lstFound);
                    recyclerView.setAdapter(adapter);
                }
                else{


                    recyclerView.setAdapter(museumAdapter);
                }
                return true;
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:

                return true;

            case R.id.action_rating_sort:
                museums.sort(new Comparator<Museum>() {
                    @Override
                    public int compare(Museum o1, Museum o2) {
                        float r1 = o1.getUsersRatingValue();
                        float r2 = o2.getUsersRatingValue();
                        return -Double.compare(r1, r2);

                    }
                });
                museumAdapter.notifyDataSetChanged();
                return true;

            case R.id.action_aplhabet_sort_a_z:
                museums.sort(new Comparator<Museum>() {
                    @Override
                    public int compare(Museum o1, Museum o2) {
                        return o1.getMname().compareTo(o2.getMname());
                    }
                });
                museumAdapter.notifyDataSetChanged();

                return true;

            case R.id.action_aplhabet_sort_z_a:
                museums.sort(new Comparator<Museum>() {
                    @Override
                    public int compare(Museum o1, Museum o2) {
                        return -o1.getMname().compareTo(o2.getMname());
                    }
                });
                museumAdapter.notifyDataSetChanged();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.museum_recycler_view);
        setHasOptionsMenu(true);

        if(museums.isEmpty()) {
            initDB();

        }



        recyclerView.setAdapter(museumAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    public ArrayList<Museum> getMuseums(){
        return this.museums;
    }

    public void initDB(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fireapp-12b506.firebaseio.com/museums");

        ref.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Museum m = dataSnapshot.getValue(Museum.class);
                museums.add(m);
                musemsTree.put(m.getUi(), m);

                museumAdapter.notifyDataSetChanged();

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Museum changedMuseum = dataSnapshot.getValue(Museum.class);
                String key = changedMuseum.getUi();
                Museum sourceMuseum = musemsTree.get(key);
                musemsTree.put(key, changedMuseum);
                changedMuseum.setUi(key);
                int index = museums.indexOf(sourceMuseum);
                museums.set(index, changedMuseum);
                museumAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Museum removedMuseum = dataSnapshot.getValue(Museum.class);
                museums.remove(removedMuseum);
                museumAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Не удаётся получить доступ к базе данных. Пожалуйста, проверьте подключение к интернету.", Toast.LENGTH_LONG).show();
            }


        });

    }




}
