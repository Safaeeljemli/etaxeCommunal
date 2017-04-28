/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author somaya
 */
public class DateUtil {

    public static Date convrtString(String date,String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static java.sql.Date convertUtilToSql(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
}
