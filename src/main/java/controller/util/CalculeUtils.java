/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Lmarbouh Mhamùed
 */
public class CalculeUtils {

    public static double formatAndRoundNumber(Double number, RoundingMode mode, int dicimalPlaces) {
        String numberFormat = "#.";
        for (int i = 0; i < dicimalPlaces; i++) {
            numberFormat += "#";
        }
        DecimalFormat df = new DecimalFormat(numberFormat);
        df.setRoundingMode(mode);
        return Double.parseDouble(df.format(number));
    }

}
