package com.example.destinationslideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import java.util.List;

import com.example.destinationslideshow.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private Destination[] destinations = {
            new Destination("#1 The Grand Canyon", "Spectacular canyon views and hiking where you can detox from technology and focus on wellness.", R.drawable.grandcanyonn),
            new Destination("#2 Jamestown, RI", "Calming seaside trip that will help soak up the sun and catch up on some you time!", R.drawable.jamestown),
            new Destination("#3 Myrtle Beach, SC", "Enjoy a relaxing vacation focusing on your wellness while soaking up the sun in South Carolina!", R.drawable.mbeach),
            new Destination("#4 Honolulu, HI", "Beautiful beaches, sunny paradise! Take a step back from life and enjoy this trip to focus on your wellness.", R.drawable.honolulu),
            new Destination("#5 Las Vegas, NV", "Enjoy a tranquil vacation at Las Vegas with some of the best spas in the country!", R.drawable.lasvegas),
    };
    private int currentDestination;

    public FirstFragment(){
        currentDestination = 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
         binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","hello");
                if (currentDestination +1 < destinations.length){
                    currentDestination++;
                    setDestination(currentDestination);
                }
            }
        });
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setDestination(int index){

        if (index >= 0 && index < destinations.length){
            Destination d = destinations[index];

            binding.txtTitle.setText(d.getTitle());
            binding.txtDesc.setText(d.getDescription());

            binding.imageView.setImageResource(d.getImageResource());

        }

        if (index == 0) {
            binding.btnPrev.setVisibility(View.GONE);
        }
        else {
            binding.btnPrev.setVisibility(View.VISIBLE);
        }

        if (index == destinations.length - 1){
            binding.btnNext.setVisibility(View.GONE);
        } else {
            binding.btnNext.setVisibility(View.VISIBLE);
        }

    }

}

