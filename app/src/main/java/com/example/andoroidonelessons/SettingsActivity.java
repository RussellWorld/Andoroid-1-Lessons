package com.example.andoroidonelessons;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(getThemeChoosen()));
        setContentView(R.layout.activity_settings);
        initThemeChooser();
        initButtons();
    }

    private void initButtons() {
        Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> {
            setThemeSaved(getThemeChoosen());
            setAppTheme();
            setResult(RESULT_OK);
            finish();
        });
        Button buttonCancel = findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(v -> {
                    setThemeChoosen(getThemeSaved());
                    setAppTheme();
                    setResult(RESULT_CANCELED);
                    finish();
                }
        );
    }

    private void initThemeChooser() {
        initRadioButtonListener(findViewById(R.id.radio_button_main_theme), MAIN_THEME);
        initRadioButtonListener(findViewById(R.id.radio_button_psychedelic_theme), PSYCHEDELIC_THEME);
        initRadioButtonListener(findViewById(R.id.radio_button_red_theme), RED_THEME);
        RadioGroup rg = findViewById(R.id.radio_button_group);
        ((MaterialRadioButton) rg.getChildAt(getThemeChoosen())).setChecked(true);
    }

    private void initRadioButtonListener(View viewById, int theme) {
        viewById.setOnClickListener(v -> {
            setThemeChoosen(theme);
            setAppTheme();
            recreate();
        });
    }
}