package com.example.avoidthebomb;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class EndGame extends Fragment {

    ObjectAnimator objectAnimator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_end, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        return fragmentLayout;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.bounce);

        final Button button = view.findViewById(R.id.button_restart);
        final ImageView msg = view.findViewById(R.id.end_game);

//      use bounce animation in end game logo
        msg.startAnimation(animation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(EndGame.this)
                        .navigate(R.id.action_EndGame_to_FirstFragment);
            }
        });
    }

}
