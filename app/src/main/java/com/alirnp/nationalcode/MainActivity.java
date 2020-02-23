package com.alirnp.nationalcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.hanks.htextview.base.HTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean doubleBackToExitPressedOnce = false;
    private ConstraintLayout mConstraintLayoutRoot;
    private View mViewGenerate;
    private View mViewValidate;
    private HTextView mTextViewCode;
    private EditText mEditTextCode;
    private ImageView mImageViewGenerate;
    private ImageView mImageViewCopy;
    private ImageView mImageViewCheckValidate;

    private State state = State.DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_state_default);

        findViews();

        setListeners();

    }

    private void setListeners() {

        mConstraintLayoutRoot.setOnClickListener(this);

        mViewValidate.setOnClickListener(this);
        mViewGenerate.setOnClickListener(this);

        mImageViewGenerate.setOnClickListener(this);
        mImageViewCopy.setOnClickListener(this);
        mImageViewCheckValidate.setOnClickListener(this);
    }

    private void findViews() {

        mConstraintLayoutRoot = findViewById(R.id.activity_main_state_constraintLayoutRoot);
        mViewGenerate = findViewById(R.id.activity_main_state_view_generate);
        mViewValidate = findViewById(R.id.activity_main_state_view_validation);

        mTextViewCode = findViewById(R.id.textView_code);
        mEditTextCode = findViewById(R.id.editText_code);
        mImageViewGenerate = findViewById(R.id.imageView_generateCode);
        mImageViewCopy = findViewById(R.id.imageView_copyCode);
        mImageViewCheckValidate = findViewById(R.id.imageView_checkValidateCode);
    }

    private void changeLayout() {
        ConstraintSet constraintSetRoot = new ConstraintSet();

        switch (state) {
            case VALIDATE:
                constraintSetRoot.clone(this, R.layout.activity_main_state_validate);
                break;
            case GENERATE:
                constraintSetRoot.clone(this, R.layout.activity_main_state_generate);

                break;
            case DEFAULT:
                constraintSetRoot.clone(this, R.layout.activity_main_state_default);
                break;
        }


        ChangeBounds transition;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            transition = new ChangeBounds();
            transition.setInterpolator(new AnticipateInterpolator(1.0f));
            transition.setDuration(400);

            TransitionManager.beginDelayedTransition(mConstraintLayoutRoot, new AutoTransition());
        }


        constraintSetRoot.applyTo(mConstraintLayoutRoot);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        state = State.DEFAULT;
        changeLayout();

        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_state_view_generate:
                state = State.GENERATE;
                break;

            case R.id.activity_main_state_view_validation:
                state = State.VALIDATE;
                break;

            case R.id.activity_main_state_constraintLayoutRoot:
                state = State.DEFAULT;
                break;


            case R.id.imageView_generateCode:
                mTextViewCode.animateText(spaceText(NationalCode.generateCode()));
                break;

            case R.id.imageView_copyCode:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("national code", mTextViewCode.getText().toString().replaceAll("\\s+", ""));

                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "کد ملی در حافظه موقت ذخیره شد", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.imageView_checkValidateCode:
                if (mEditTextCode.getText() == null) return;


                String text = mEditTextCode.getText().toString();
                if (text.length() != 10) {
                    Toast.makeText(this, "طول کد ملی صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean validate = NationalCode.validateCode(text);

                Toast.makeText(this, "status : " + validate, Toast.LENGTH_SHORT).show();


                break;
        }
        changeLayout();
    }

    private String spaceText(Long generatedCode) {
        String code = String.valueOf(generatedCode);
        StringBuilder spacedCode = new StringBuilder();

        for (int i = 0; i < code.length(); i++) {
            spacedCode.append(code.charAt(i));

            if (i != (code.length() - 1))
                spacedCode.append(" ");
        }
        return spacedCode.toString();
    }

    private enum State {DEFAULT, GENERATE, VALIDATE}
}
