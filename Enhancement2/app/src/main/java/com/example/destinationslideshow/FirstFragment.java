package com.example.destinationslideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.concurrent.ThreadLocalRandom;

import com.example.destinationslideshow.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private List<Destination> destinations = new ArrayList<>();

    private int currentDestination;

    public FirstFragment(){
        currentDestination = 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d("First Fragment", "onCreateView");
         binding = FragmentFirstBinding.inflate(inflater, container, false);

        DatabaseHelper h = new DatabaseHelper(getContext());
        SQLiteDatabase db = h.getReadableDatabase();

        String category = ((MainActivity) getActivity()).categoryToShow();

        //Code for selecting the random destination
        if (category == "Random") {
            String[] idxCols = {"id"};
            Cursor getIds = db.query("Destinations", idxCols, null, null, null, null, null, null);

            //Get the number of destinations in database
            List<Integer> ids = new ArrayList<>();
            while (!getIds.isLast()) {
                getIds.moveToNext();
                ids.add(getIds.getInt(0));
            }
            //pick random number between 0 and the largest number
            int randomIdx = ThreadLocalRandom.current().nextInt(0 , ids.size());

            String[] cols = {"name", "description", "category", "picture"};
            String[] where = {Integer.toString(ids.get(randomIdx))};
            Cursor cursor = db.query("Destinations", cols, "id = ?", where, null, null, null, null);

            //move cursor to next entry
            while (!cursor.isLast()) {
                cursor.moveToNext();

                Log.d("QUERY", cursor.getString(0) + ", " + cursor.getString(1));

                destinations.add(new Destination(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3)));
            }

        } else {
            //database query based on category
            String[] cols = {"name", "description", "category", "picture"};
            String[] where = {category};
            Cursor cursor = db.query("Destinations", cols, "category = ?", where, null, null, null, null);

            while (!cursor.isLast()) {
                cursor.moveToNext();

                Log.d("QUERY", cursor.getString(0) + ", " + cursor.getString(1));
                //cursor to get needed aspects for the slideshow part of app
                destinations.add(new Destination(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3)));
            }
        }



        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDestination(currentDestination);
        //on click listener for Next button
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","hello");
                if (currentDestination +1 < destinations.size()){
                    currentDestination++;
                    setDestination(currentDestination);
                }
            }
        });
        //On click listener for previous button
        binding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","hello");
                if (currentDestination >= 0) {
                    currentDestination = currentDestination - 1;
                    setDestination(currentDestination);
                }


            }
        });
        //On Click listener for back button
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_secondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //Setting attributes for slideshow
    private void setDestination(int index){

        if (index >= 0 && index < destinations.size()){
            Destination d = destinations.get(index);

            binding.txtTitle.setText(d.getTitle());
            binding.txtDesc.setText(d.getDescription());

            binding.imageView.setImageBitmap(d.getImage());
        }
        //Setting when next and previous buttons are available
        if (index == 0) {
            binding.btnPrev.setVisibility(View.GONE);
        }
        else {
            binding.btnPrev.setVisibility(View.VISIBLE);
        }

        if (index == destinations.size() - 1){
            binding.btnNext.setVisibility(View.GONE);
        } else {
            binding.btnNext.setVisibility(View.VISIBLE);
        }

    }

}
