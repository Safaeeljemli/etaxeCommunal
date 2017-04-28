/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.sql.Date;


/**
 *
 * @author ACER
 */
public class SearchUtil {

    public static String addConstraint(String beanAbrev, String atributeName, String operator, Object value) {
        if (value != null) {
            return " AND " + beanAbrev + "." + atributeName + " " + operator + " '" + value + "'";
        }
        return "";
    }

    public static String addConstraintMinMax(String beanAbrev, String atributeName, Object valueMin, Object valueMax) {
        String requette = "";
        if (valueMin != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " >= '" + valueMin + "'";
        }
        if (valueMax != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " <= '" + valueMax + "'";
        }
        System.out.println(requette);
        return requette;
    }

    public static String addConstraintDate(String beanAbrev, String atributeName, String operator, Date value) {
        return addConstraint(beanAbrev, atributeName, operator, DateUtil.convertUtilToSql(value));
    }

    public static String addConstraintMinMaxDate(String beanAbrev, String atributeName, java.util.Date valueMin, java.util.Date valueMax) {
        System.out.println(valueMin+"   "+valueMax);
        Date dateMin = null;
        Date dateMax = null;
        if (valueMin != null) {
            dateMin=DateUtil.convertUtilToSql(valueMin);
        }
        if (valueMax != null) {
            dateMax=DateUtil.convertUtilToSql(valueMin);
        }
        return addConstraintMinMax(beanAbrev, atributeName,dateMin, dateMax);
    }

}
