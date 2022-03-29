package com.example.avoidthebomb;

import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Random;

public class fragment_game_page extends Fragment {

    TextView showCurrentCountText;
    ObjectAnimator objectAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_game_page, container, false);
        // Get the count text view
        showCurrentCountText = fragmentLayout.findViewById(R.id.count_display);
        showCurrentCountText.setText("0");
        return fragmentLayout;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.bounce);
        // create Toast for not bomb click
        Toast roundmsg=DynamicToast.makeSuccess(getActivity(), "Not Bomb !", Toast.LENGTH_SHORT );
        // getting max allowed click value set by user
        int maxValue=getArguments().getInt("max");
        int target = createRandomNumber(maxValue);

        //main game button to increase count and check bomb
        final Button button = view.findViewById(R.id.button_gameplay);

        // hint fab button for notify how far bomb is
        final FloatingActionButton fab = view.findViewById(R.id.fab);

        // mimic line animation for each click
        final View line_conect= view.findViewById(R.id.divider);
        final View lineR= view.findViewById(R.id.divider2);
        final View lineL= view.findViewById(R.id.divider3);
        final View lineT= view.findViewById(R.id.divider4);
        final View lineB_l= view.findViewById(R.id.divider5_left);
        final View lineB_r= view.findViewById(R.id.divider5_right);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundmsg.cancel();
                Integer currentCount=addCount(view);
                if (currentCount < target){
                    TransitionDrawable transition = (TransitionDrawable) line_conect.getBackground();
                    TransitionDrawable transition2 = (TransitionDrawable) lineB_l.getBackground();
                    TransitionDrawable transition3 = (TransitionDrawable) lineL.getBackground();
                    TransitionDrawable transition4 = (TransitionDrawable) lineT.getBackground();
                    TransitionDrawable transition5 = (TransitionDrawable) lineR.getBackground();
                    TransitionDrawable transition6 = (TransitionDrawable) lineB_r.getBackground();

                    Handler handler = new Handler();
                    // each transition will run one by one
                    waitToComplete(handler,transition, 0);
                    waitToComplete(handler,transition2, 230);
                    waitToComplete(handler,transition3, 460);
                    waitToComplete(handler,transition4, 690);
                    waitToComplete(handler,transition5, 920);
                    waitToComplete(handler,transition6, 1150);

                    // ensure each line transtion completed before run
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
                            roundmsg.show();
                            updateCount(view, currentCount);
//                            transition.reverseTransition(50);
//                            transition2.reverseTransition(100);
//                            transition3.reverseTransition(150);
//                            transition4.reverseTransition(200);
//                            transition5.reverseTransition(250);
//                            transition6.reverseTransition(300);
//                        }
//                    }, 1150);
                }
                else if(currentCount == target) {
                    NavHostFragment.findNavController(fragment_game_page.this)
                            .navigate(R.id.action_MainGameFragment_to_EndGame);
                }


            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current= currentCount(view);
                String str= hintMsg(target,current);
                DynamicToast.make(getActivity(), str, getResources().getColor(R.color.white), getResources().getColor(R.color.purple_200)).show();
            }
        });
    }

    // each transition need to wait for the previous one to complete
    private void waitToComplete(Handler handler, TransitionDrawable transition, Integer time){
        handler.postDelayed(new Runnable() {
            public void run() {
                transition.startTransition(230);
            }
        }, time);
    }

        // produce hint msg
    private String hintMsg(Integer target, Integer current){
        int diff= target-current;
        if (diff<5){
            return "The Bomb is getting very closed!";
        }
        else if (diff<10){
            return "The Bomb is within 10 clicks";
        }
        else{
            return "The Bomb is still far away!";
        }
    }

    // generate random bomb number
    private Integer createRandomNumber(int max) {
        Random random = new java.util.Random();
        Integer randomNumber = 1;
        if (max > 0) {
            // create a random number between 1-max
            randomNumber = random.nextInt(max)+1;
        }
        else {
            randomNumber = random.nextInt(30)+1;
        }
        return randomNumber;
    }

    // increase count when click button
    private Integer addCount(View view) {
        int count=currentCount(view);
        count++;
        return count;
    }

    // update count on current view
    private void updateCount(View view, Integer count) {
        showCurrentCountText.setText(count.toString());
    }

    // getting current count in the view
    private Integer currentCount(View view) {
        // Get the value of the text view
        String countString = showCurrentCountText.getText().toString();
        // Convert value to a number and increment it
        Integer count = Integer.parseInt(countString);
        return count;
    }



}
