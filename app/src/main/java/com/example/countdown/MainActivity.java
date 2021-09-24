package com.example.countdown;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.countdown.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TimeViewModel model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.countText.setText("0");

        model = new ViewModelProvider(this).get(TimeViewModel.class);

        final Observer<Integer> timeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer timevalue) {
                binding.countText.setText(timevalue.toString());
            }
        };

        final Observer<Boolean> finishObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean timevalue) {
                binding.countText.setText("Completed");
            }
        };

        binding.startButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (binding.editAmount.getText().toString().equals("")) {
                            binding.countText.setText("Enter a Time");
                        } else {
                            Long timeMilli = Long.parseLong(binding.editAmount.getText().toString()) * 1000;
                            model.setTime(timeMilli);
                            model.startTimer();
                        }


                    }});

        binding.stopButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        model.stopTimer();
                    }});



        model.returnSeconds().observe(this,timeObserver);
        model.getFinished().observe(this,finishObserver);


    }
}