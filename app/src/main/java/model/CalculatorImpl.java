package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculatorImpl implements Calculator{

    protected static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    protected static DecimalFormat formatter = new DecimalFormat("0.####", otherSymbols);

    @Override
    public double perform(String arg1, String arg2, Operator operator) {
        double firstArg = Double.parseDouble(arg1);
        double secondArg = Double.parseDouble(arg2);

        switch (operator) {
            case ADD:
                return Double.parseDouble(formatter.format(firstArg + secondArg));

            case MINUS:
                return Double.parseDouble(formatter.format(firstArg - secondArg));

            case MULT:
                return Double.parseDouble(formatter.format(firstArg * secondArg));

            case SUB:
                if (secondArg == 0) {
                    return 0.0;
                }
                return Double.parseDouble(formatter.format(firstArg / secondArg));

            case NONE:
                return 0.0;
        }

        return 0.0;
    }
}
