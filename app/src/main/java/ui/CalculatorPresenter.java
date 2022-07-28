package ui;

//import static ui.CalculatorView.showResult;

import android.view.View;

import model.Calculator;
import model.CalculatorImpl;
import model.Operator;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

import model.Operator;
import model.Calculator;


public class CalculatorPresenter {

    private final DecimalFormat formater = new DecimalFormat("#.##");

    private final CalculatorView view;
    private final Calculator calculator;

    //    private double argOne;
//    private Double argTwo;
    private String argTwo = "0";
    private String argOne = "";

    //    private Operator selectedOperator;
    private Operator selectedOperator = Operator.NONE;
    private Object String;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    public void setData(CalculatorPresenter.Data data) {
        if (data != null) {
            argOne = data.getArgOne();
            argTwo = data.getArgTwo();
            selectedOperator = data.getSelectedOperator();
            updateState();
        }
    }

    public CalculatorPresenter.Data getData() {
        return new CalculatorPresenter.Data(argOne, argTwo, selectedOperator);
    }

    private void calc() {
        argOne = java.lang.String.valueOf(calculator.perform(argOne, argTwo, selectedOperator));
//        calculatorView.showResult(argOne);
        view.showResult(argOne);
        argTwo = "";
    }

    public void onDigitPressed(String number) {

        if (selectedOperator != Operator.NONE) {
            argTwo += number;
        } else {
            if (argOne.equals("0")) {
                argOne = number;
            } else {
                argOne += number;
            }
        }
        updateState();

    }

    protected void updateState() {
        if (selectedOperator != Operator.NONE) {
            if (argTwo.equals("")) {
                view.showResult(argOne);
            } else {
                view.showResult(argTwo);
            }
        } else {
            view.showResult(argOne);
        }
    }


    public void onOperatorPressed(Operator operator) {
        if (selectedOperator != Operator.NONE && !argTwo.equals("")) {
            calc();
        }
        selectedOperator = operator;
    }

    class Data implements Parcelable {

        private final String argOne;
        private final String argTwo;
        private final Operator selectedOperator;

        public Data(String argOne, String argTwo, Operator selectedOperator) {
            this.argOne = argOne;
            this.argTwo = argTwo;
            this.selectedOperator = selectedOperator;
        }

        protected Data(Parcel in) {
            argOne = in.readString();
            argTwo = in.readString();
            selectedOperator = Operator.values()[in.readInt()];
        }

        public String getArgOne() {
            return argOne;
        }

        public String getArgTwo() {
            return argTwo;
        }

        public Operator getSelectedOperator() {
            return selectedOperator;
        }

        public final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(argOne);
            parcel.writeString(argTwo);
            parcel.writeInt(selectedOperator.ordinal());
        }
    }

    public void onDotPressed() {

        if (selectedOperator != Operator.NONE && !argTwo.contains(".")) {
            argTwo += ".";
            updateState();
        } else {
            if (!argOne.contains(".")) {
                argOne += ".";
                updateState();
            }
        }
    }

    public void onEqualsPressed() {
        if (selectedOperator != Operator.NONE && !argTwo.equals("")) {
            calc();
        }
    }

}
