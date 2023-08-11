package com.example.destinationslideshow;

//Imports needed
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.destinationslideshow.databinding.FragmentSecondBinding;

//Creating class for 2nd Fragment in App
public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    //Create view for second fragment
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // On Click listener for Island button
        binding.btnIsland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).setCategoryToShow("Island");
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_FirstFragment);

            }
        });

        // On Click listener for Mountain button
        binding.btnMountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setCategoryToShow("Mountain");
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_FirstFragment);
            }
        });

        // On Click listener for Desert button
        binding.btnDesert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setCategoryToShow("Desert");
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_FirstFragment);
            }
        });

        // On Click listener for Countryside button
        binding.btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setCategoryToShow("Countryside");
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_FirstFragment);
            }
        });

        // On Click listener for City button
        binding.btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setCategoryToShow("City");
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_FirstFragment);
            }
        });

        // On Click listener for Random button
        binding.btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setCategoryToShow("Random");
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}