package com.example.andoroidonelessons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final static int REQUEST_CODE = 99;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private final String KEY_CALCULATOR = "Calculator";
    private Calculation calculator = new Calculation();
    private Button buttonComma;
    private Button buttonEqual;
    private Button buttonChangeSing;
    private Button buttonClearAll;
    private Button buttonClearOne;
    private Button buttonPlus;
    private Button buttonMultiply;
    private Button buttonDivide;
    private Button buttonMinus;
    private Button buttonOpenSettings;
    private TextView textViewShowResult;
    private TextView textViewEnterNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Установим сохранненую тему
        setTheme(getAppTheme(getThemeChoosen()));

        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CALCULATOR, calculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator = savedInstanceState.getParcelable(KEY_CALCULATOR);
        setShowResultText();
    }

    //инициализируем внешний вид
    private void initView() {
        findViews();
        setClickListeners();
    }

    //устанавливает кликлистнеры
    private void setClickListeners() {
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonComma.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonChangeSing.setOnClickListener(this);
        buttonClearAll.setOnClickListener(this);
        buttonClearOne.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonOpenSettings.setOnClickListener(this);
        textViewShowResult.setOnClickListener(this);
        textViewEnterNumbers.setOnClickListener(this);
    }

    //находит вью по ID
    private void findViews() {
        button0 = findViewById(R.id.button_0);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonComma = findViewById(R.id.button_comma);
        buttonEqual = findViewById(R.id.button_equal);
        buttonChangeSing = findViewById(R.id.button_change_sing);
        buttonClearAll = findViewById(R.id.button_clear_all);
        buttonClearOne = findViewById(R.id.button_clear_one);
        buttonMultiply = findViewById(R.id.button_multiply);
        buttonDivide = findViewById(R.id.button_divide);
        buttonPlus = findViewById(R.id.button_plus);
        buttonMinus = findViewById(R.id.button_minus);
        buttonOpenSettings = findViewById(R.id.button_open_settings);
        textViewShowResult = findViewById(R.id.show_result_textview);
        textViewEnterNumbers = findViewById(R.id.enter_numbers_textview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.button_0):
                setNumber(R.id.button_0);
                break;
            case (R.id.button_1):
                setNumber(R.id.button_1);
                break;
            case (R.id.button_2):
                setNumber(R.id.button_2);
                break;
            case (R.id.button_3):
                setNumber(R.id.button_3);
                break;
            case (R.id.button_4):
                setNumber(R.id.button_4);
                break;
            case (R.id.button_5):
                setNumber(R.id.button_5);
                break;
            case (R.id.button_6):
                setNumber(R.id.button_6);
                break;
            case (R.id.button_7):
                setNumber(R.id.button_7);
                break;
            case (R.id.button_8):
                setNumber(R.id.button_8);
                break;
            case (R.id.button_9):
                setNumber(R.id.button_9);
                break;
            case (R.id.button_comma):
                setComma();
                break;
            case (R.id.button_equal):
                setEqual();
                break;
            case (R.id.button_change_sing):
                setChangeSing();
                break;
            case (R.id.button_clear_all):
                cleatAll();
                break;
            case (R.id.button_clear_one):
                clearOne();
                break;
            case (R.id.button_multiply):
                setSing(R.id.button_multiply);
                break;
            case (R.id.button_divide):
                setSing(R.id.button_divide);
                break;
            case (R.id.button_plus):
                setSing(R.id.button_plus);
                break;
            case (R.id.button_minus):
                setSing(R.id.button_minus);
                break;
            case (R.id.button_open_settings):
                openSettings();
                break;
        }
    }
    //Откроем настройки
    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            //Пересоздаем активность
            recreate();
        }
    }

    private void setShowResultText() {
        textViewShowResult.setText(calculator.getStringResult());
        textViewEnterNumbers.setText(calculator.getStringFirstNumber());
    }

    private String getViewText(int id) {
        if (id == R.id.button_0) {
            return (String) button0.getText();
        } else if (id == R.id.button_1) {
            return (String) button1.getText();

        } else if (id == R.id.button_2) {
            return (String) button2.getText();

        } else if (id == R.id.button_3) {
            return (String) button3.getText();

        } else if (id == R.id.button_4) {
            return (String) button4.getText();

        } else if (id == R.id.button_5) {
            return (String) button5.getText();

        } else if (id == R.id.button_6) {
            return (String) button6.getText();

        } else if (id == R.id.button_7) {
            return (String) button7.getText();

        } else if (id == R.id.button_8) {
            return (String) button8.getText();

        } else if (id == R.id.button_9) {
            return (String) button9.getText();

        } else if (id == R.id.button_multiply) {
            return (String) buttonMultiply.getText();

        } else if (id == R.id.button_divide) {

            return (String) buttonDivide.getText();
        } else if (id == R.id.button_plus) {
            return (String) buttonPlus.getText();

        } else if (id == R.id.button_minus) {
            return (String) buttonMinus.getText();
        } else {
            return "";
        }
    }

    private void setNumber(int buttonId) {
        calculator.setNumber(getViewText(buttonId));
        setShowResultText();
    }

    private void setComma() {
        calculator.setComma(true);
        setShowResultText();
    }

    private void setEqual() {
        calculator.setEqual();
        setShowResultText();
    }

    private void setChangeSing() {
        calculator.setChangeSing();
        setShowResultText();
    }

    private void cleatAll() {
        calculator.cleatAll();
        setShowResultText();
    }

    private void clearOne() {
        calculator.clearOne();
        setShowResultText();
    }

    private void setSing(int buttonId) {
        calculator.setSing(getViewText(buttonId));
        setShowResultText();
    }
}