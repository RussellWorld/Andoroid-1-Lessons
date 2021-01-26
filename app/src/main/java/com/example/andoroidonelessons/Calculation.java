package com.example.andoroidonelessons;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

class Calculation implements Parcelable {
    public final Creator<Calculation> CREATOR = new Creator<Calculation>() {
        @Override
        public Calculation createFromParcel(Parcel in) {
            return new Calculation(in);
        }

        @Override
        public Calculation[] newArray(int size) {
            return new Calculation[size];
        }
    };
    private final int MAX_QUANTITY_AFTER_COMMA = 15;//грубо ограничим количество знаков после запятой, из-за ограничения типа double
    //Для хранения больших числе - надо бы использовать MaxDecimal
    private final char EMPTY_CHAR = Character.MIN_VALUE;
    private final char PLUS = '+';
    private final char MINUS = '-';
    private final char MUPTIPLY = '*';
    private final char DIVIDE = '/';
    private double result;//результат вычисления
    private double firstNumber;//вводимое в настоящий момент число.
    private char sign;//знак вычисления
    private boolean commaAdded;//флаг, указывающий на то, что вводятся числа дробной части
    private boolean equalPressed;//флаг, указывающий на нажатие пробела
    private int quantityAfterComma;//хранит количество знаков после запятой

    public Calculation() {
        this.firstNumber = 0.0;
        this.result = 0.0;
        this.commaAdded = false;
        this.quantityAfterComma = 0;
        setEqualPressed(false);
        clearSing();

    }

    protected Calculation(Parcel in) {
        result = in.readDouble();
        firstNumber = in.readDouble();
        sign = (char) in.readInt();
        commaAdded = in.readByte() != 0;
        equalPressed = in.readByte() != 0;
        quantityAfterComma = in.readInt();
    }

    //Устанавливает значение переменной equalPressed
    private void setEqualPressed(boolean equalPressed) {
        this.equalPressed = equalPressed;
    }

    //обрабатывает переданный знак =, выполняет вычисления
    void setEqual() {
        if (sign == EMPTY_CHAR) {//знак действия не нажат, не выполняем вычисления
            return;
        }
        if (firstNumber == 0.0) {//пользователь не ввел второе число. Делаем вычисление с самим собой
            makeCalculation(result, result);
        } else {
            makeCalculation(result, firstNumber);
        }
        resetNumbers();
        clearSing();
        setEqualPressed(true);
    }

    //очищает переменную, хранящую знак
    private void clearSing() {
        this.sign = EMPTY_CHAR;
    }

    //обнуляет числа и переменные, записывает firstNumber в result
    private void resetNumbers() {
        result = firstNumber;
        firstNumber = 0.0;
        quantityAfterComma = 0;
        setComma(false);
    }

    //выполняет вычисления согласно знака
    private void makeCalculation(double fNumber, double sNumber) {
        switch (sign) {
            case PLUS:
                firstNumber = fNumber + sNumber;
                break;
            case MINUS:
                firstNumber = fNumber - sNumber;
                break;
            case DIVIDE:
                firstNumber = fNumber / sNumber;
                break;
            case MUPTIPLY:
                firstNumber = fNumber * sNumber;
                break;
        }
    }

    //меняет знак числа
    void setChangeSing() {
        firstNumber = firstNumber * -1.0;
    }

    //очищает все
    void cleatAll() {
        quantityAfterComma = 0;
        firstNumber = 0;
        result = 0;
        setComma(false);
        clearSing();
    }

    //очищает последний введенный символ
    void clearOne() {
        if (quantityAfterComma == 0) {
            firstNumber = Math.floor(firstNumber / 10.0);
        } else {
            quantityAfterComma--;
            double scale = Math.pow(10, quantityAfterComma);//множитель для дробной части
            firstNumber = Math.floor(firstNumber * scale);
            firstNumber = firstNumber / scale;
        }
    }

    //устанавливает знак вычисления
    void setSing(String sign) {
        if (this.sign == EMPTY_CHAR) {
            if (!equalPressed) {
                resetNumbers();
                setEqualPressed(false);
            }
        } else {//Пользователь нажал знак вычисления второй раз. Выполним действия, равносильные нажатию =
            setEqual();
        }
        this.sign = sign.charAt(0);
    }

    //устанавливает разделитель
    void setComma(boolean commaValue) {
        commaAdded = commaValue;
    }

    //заполняет переданное число
    void setNumber(String number) {
        int quantityBeforComma = countNumberQuantity(Math.floor(firstNumber));
        if (quantityBeforComma >= MAX_QUANTITY_AFTER_COMMA) {//не даем вводить больше 15 знаков после запятой
            return;
        }
        //Приводим строку к числу
        int intNumber = Integer.parseInt(number);
        if (commaAdded) { //увеличиваем дробную часть
            quantityAfterComma++; //увеличиваем количество цифр дробной части
            double scale = Math.pow(10, quantityAfterComma);//множитель для дробной части
            firstNumber = firstNumber * scale;
            firstNumber = firstNumber + (double) intNumber;
            firstNumber = firstNumber / scale;
        } else { //увеличиваем целую часть
            firstNumber = firstNumber * 10.0 + intNumber;
        }
    }

    //форматирует Result и возвращает отформатированную строку
    String getStringResult() {
        int length = getMinLength(result);
        String stringFormat = String.format("%%.%df%%s", length);
        return String.format(stringFormat, result, sign);
    }

    //форматирует firstNumber и возвращает отформатированную строку
    String getStringFirstNumber() {
        int length = getMinLength(firstNumber);//Если полученное количество знаков после запятой больше максимального количества - берем максимум
        String stringFormat = String.format("%%.%df", length);//отформатируем по количеству знаков после запятой
        return String.format(stringFormat, firstNumber);
    }

    //определяет количество знаков при округлении с учетом ограничений
    private int getMinLength(double numberToSplit) {
        int length = 0;
        length = BigDecimal.valueOf(numberToSplit).scale();
        double flooredNumber = Math.floor(numberToSplit);
        int quantityBeforComma = countNumberQuantity(flooredNumber);
        if (Math.abs(flooredNumber - numberToSplit) == 0.0) {//если после запятой одна цифра и это ноль, исключаем его из формата
            //немного костыль, но лучшего решения не придумал
            length = 0;
        }
        return Math.min(length, MAX_QUANTITY_AFTER_COMMA - Math.min(MAX_QUANTITY_AFTER_COMMA, quantityBeforComma));
    }

    //определяет количество знаков до запятой
    private int countNumberQuantity(double fNumber) {
        int numberQuantity = 0;
        while (fNumber > 1.0) {
            fNumber /= 10.0;
            numberQuantity++;
        }
        return numberQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeDouble(result);
        dest.writeDouble(firstNumber);
        dest.writeInt(sign);
        dest.writeByte((byte) (commaAdded ? 1 : 0));
        dest.writeByte((byte) (equalPressed ? 1 : 0));
        dest.writeInt(quantityAfterComma);
    }
}