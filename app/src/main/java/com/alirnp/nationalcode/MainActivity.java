package com.alirnp.nationalcode;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout mConstraintLayoutRoot;
    ConstraintLayout mConstraintLayoutValidate;

    private static final String TAG = "NationalCodeApp";
    ConstraintLayout mConstraintLayoutGenerate;
    private State state = State.DEFAULT;
    private boolean showAll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long codeLong = 1810350832L;
        String codeString = "1810350832";

        Log.i(TAG, "validate: " + NationalCode.validateCode(codeLong));
        Log.i(TAG, "validate: " + NationalCode.validateCode(codeString));
        Log.i(TAG, "generate: " + NationalCode.generateCode());

        findViews();


    }

    private void findViews() {
        mConstraintLayoutRoot = findViewById(R.id.activity_main_constraintLayoutRoot);
        mConstraintLayoutValidate = findViewById(R.id.activity_main_constraintLayoutValidate);
        mConstraintLayoutGenerate = findViewById(R.id.activity_main_constraintLayoutGenerate);

        mConstraintLayoutRoot.setOnClickListener(this);
        mConstraintLayoutValidate.setOnClickListener(this);
        mConstraintLayoutGenerate.setOnClickListener(this);
    }

    private void invalidateLayouts() {

        invalidateRoot();
        invalidateValidate();
    }

    private void invalidateRoot() {

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mConstraintLayoutRoot);
        float height;

        switch (state) {
            case DEFAULT:
                height = getResources().getDimension(R.dimen._200sdp);

                constraintSet.constrainHeight(R.id.activity_main_constraintLayoutGenerate, (int) height);
                constraintSet.constrainHeight(R.id.activity_main_constraintLayoutValidate, (int) height);
                break;

            case GENERATE:
                height = getResources().getDimension(R.dimen._335sdp);

                constraintSet.constrainHeight(R.id.activity_main_constraintLayoutGenerate, (int) height);
                constraintSet.constrainHeight(R.id.activity_main_constraintLayoutValidate, ConstraintSet.WRAP_CONTENT);

                break;

            case VALIDATE:
                height = getResources().getDimension(R.dimen._335sdp);

                constraintSet.constrainHeight(R.id.activity_main_constraintLayoutGenerate, ConstraintSet.WRAP_CONTENT);
                constraintSet.constrainHeight(R.id.activity_main_constraintLayoutValidate, (int) height);

                break;

        }


        ChangeBounds transition;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            transition = new ChangeBounds();
            transition.setInterpolator(new AnticipateInterpolator(1.0f));
            transition.setDuration(400);

            TransitionManager.beginDelayedTransition(mConstraintLayoutValidate, transition);
        }


        constraintSet.applyTo(mConstraintLayoutRoot);


    }

    private void invalidateValidate() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mConstraintLayoutValidate);

        switch (state) {
            case DEFAULT:
                float height = getResources().getDimension(R.dimen._40sdp);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.setVerticalBias(R.id.activity_main_textView_validate, 0.8f);
                constraintSet.setVerticalBias(R.id.activity_main_imageView_validate, 0.4f);
                constraintSet.setHorizontalBias(R.id.activity_main_imageView_validate, 0.5f);

                constraintSet.constrainHeight(R.id.activity_main_imageView_validate, (int) height);
                break;

            case GENERATE:
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.TOP, R.id.activity_main_textView_validate, ConstraintSet.TOP);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.BOTTOM, R.id.activity_main_textView_validate, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.connect(R.id.activity_main_imageView_validate, ConstraintSet.START, R.id.activity_main_textView_validate, ConstraintSet.END);
                constraintSet.setVerticalBias(R.id.activity_main_textView_validate, 0.5f);
                constraintSet.setHorizontalBias(R.id.activity_main_imageView_validate, 0.3f);
                constraintSet.constrainHeight(R.id.activity_main_imageView_validate, 0);
                break;

            case VALIDATE:
                break;

        }


        ChangeBounds transition;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            transition = new ChangeBounds();
            transition.setInterpolator(new AnticipateInterpolator(1.0f));
            transition.setDuration(400);

            TransitionManager.beginDelayedTransition(mConstraintLayoutValidate, transition);
        }


        constraintSet.applyTo(mConstraintLayoutValidate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_constraintLayoutRoot:
                state = State.DEFAULT;
                break;

            case R.id.activity_main_constraintLayoutValidate:
                state = State.VALIDATE;
                break;

            case R.id.activity_main_constraintLayoutGenerate:
                state = State.GENERATE;
                break;
        }

        invalidateLayouts();
    }


    private enum State {DEFAULT, GENERATE, VALIDATE}
}
