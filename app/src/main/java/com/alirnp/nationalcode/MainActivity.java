package com.alirnp.nationalcode;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NationalCodeApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long codeLong = 1810350832L;
        String codeString = "1810350832";

        Log.i(TAG, "validate: " + NationalCode.validateCode(codeLong));
        Log.i(TAG, "validate: " + NationalCode.validateCode(codeString));
        Log.i(TAG, "generate: " + NationalCode.generateCode());

    }
}
