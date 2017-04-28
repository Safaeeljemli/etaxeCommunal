package controller.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import controler.util.SessionUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author Khalid
 */
public class PdfUtil {

    private static String absoluteJasperPath;
    private static String absoluteWebPath;

    static {
        absoluteJasperPath = "C:\\Users\\Younes\\Documents\\taxeCommune\\taxe_commune_zouani\\src\\java\\report\\";
        absoluteWebPath = "C:\\Users\\Younes\\Documents\\taxeCommune\\taxe_commune_zouani\\web\\";
    }

//    private static void generateHeaderNotificationPdf(Map<String, Object> params) {
//        params.put("ligne1", SessionUtil.getCurrentCommune().getHeader());
//        params.put("commune_nom", SessionUtil.getCurrentCommune().getVille().getNom());
//        System.out.println("generateHeaderNotification liene1=" + SessionUtil.getCurrentCommune().getHeader());
//    }
//
//    private static void fillLocalAndRedevableInformationsPdf(Map<String, Object> params, Locale locale) {
//        Redevable redevable = locale.getRedevable();
//
//        if (redevable != null && redevable.getId() != null) {
//
//            if (redevable.getEntite() == 0) {
//                params.put("cin", redevable.getCin() + " (CIN)");
//                params.put("nom", redevable.getNom() + " " + redevable.getPrenom() + " (NOM)");
//
//            } else {
//                params.put("cin", redevable.getRc() + " (RC)");
////                params.put("nom", redevable.getRaisonSocial() + " (Raison social)");
//
//            }
//            if (redevable.getNature() == 1) {
//                params.put("exploitant", "Gérant");
//            } else {
//                params.put("exploitant", "Proprietaire");
//            }
//        }
//        if (locale.getId() != null) {
////            params.put("adresse", locale.getAdresse());
//           
//        }
//    }

//    public static void generateXls(List myList, Map<String, Object> params, String outPoutFileName, String bilan, Locale locale) throws JRException {
//        generateHeaderNotificationPdf(params);
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        String jasperPrint = JasperFillManager.fillReportToFile(absoluteJasperPath + "taxeSejour.jasper", params, jrBeanCollectionDataSource);
//        fillLocalAndRedevableInformationsPdf(params, locale);
//
//        JRXlsExporter exporter = new JRXlsExporter();
//
//        exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
//                jasperPrint);
//        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, absoluteWebPath + "sample_report.xls");
//        exporter.exportReport();
//    }

//    public static void generateXls(List myList, Map<String, Object> params, String outPoutFileName, String bilan) throws JRException {
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        String jasperPrint = JasperFillManager.fillReportToFile(absoluteJasperPath + "LocaleParTaxe.jasper", params, jrBeanCollectionDataSource);
//
//        JRXlsExporter exporter = new JRXlsExporter();
//
//        exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
//                jasperPrint);
//        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
//                absoluteWebPath + "sample_report.xls");
//        exporter.exportReport();
//    }

//    public static void generatePdf(List myList, Map<String, Object> params, String outPoutFileName, String bilan, Locale locale) throws JRException, IOException {
//        System.out.println("haniii pdf");
//        generateHeaderNotificationPdf(params);
//        fillLocalAndRedevableInformationsPdf(params, locale);
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        OutputStream outputStream = getResponseOutput(outPoutFileName);
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//    }

//    public static void generatePdf(List myList, Map<String, Object> params, String outPoutFileName, String bilan, Abattoire abattoire) throws JRException, IOException {
//        generateHeaderNotificationPdf(params);
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        OutputStream outputStream = getResponseOutput(outPoutFileName);
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//    }

//    public static void generateSimulationPdf(List dataSource, String outPoutFileName, String bilan) throws JRException, IOException {
//        JRDataSource jRDataSource = new JRBeanCollectionDataSource(dataSource);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), null, jRDataSource);
//        OutputStream outputStream = getResponseOutput(outPoutFileName);
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//
//    }
//
//    public static void generatePdf(List myList, Map<String, Object> params, String outPoutFileName, String bilan, Vehicule vehicule) throws JRException, IOException {
//       generateHeaderNotificationPdf(params);
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        OutputStream outputStream = getResponseOutput(outPoutFileName);
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//    }

//    public static void generatePdf(List myList, Map<String, Object> params, String outPoutFileName, String bilan) throws JRException, IOException {
////        generateHeaderNotificationPdf(params);
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        OutputStream outputStream = getResponseOutput(outPoutFileName);
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//    }
//
//    public static void generateXsl(List myList, Map<String, Object> params, String outPoutFileName, String bilan, Vehicule vehicule) throws JRException, IOException {
////        generateHeaderNotificationPdf(params);
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//        JRXlsExporter exporter = new JRXlsExporter();
//        exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//        exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
//        exporter.exportReport();
//        getResponseXslOutput(bilan);
//    }
//
//    public static void generatePdf(List myList, Map<String, Object> params, String outPoutFileName, String bilan, Loyer loyer) throws JRException, IOException {
////        generateHeaderNotificationPdf(params);
//        if (myList == null || myList.isEmpty()) {
//            myList = new ArrayList();
//            myList.add(new Object());
//        }
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        OutputStream outputStream = getResponseOutput(outPoutFileName);
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//    }
//
//    private static OutputStream getResponseOutput(String fileName) throws IOException {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        httpServletResponse.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//        httpServletResponse.addHeader("Pragma", "no-cache"); // HTTP 1.0.
//        httpServletResponse.addHeader("Expires", "0"); // Proxies.
//        httpServletResponse.addHeader("Content-Type", "application/pdf");
//        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
//        return httpServletResponse.getOutputStream();
//    }
//
//    private static OutputStream getResponseXslOutput(String fileName) throws IOException {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        httpServletResponse.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//        httpServletResponse.addHeader("Pragma", "no-cache"); // HTTP 1.0.
//        httpServletResponse.addHeader("Expires", "0"); // Proxies.
//        httpServletResponse.addHeader("Content-Type", "application/vnd.ms-excel");
//        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
//        return httpServletResponse.getOutputStream();
//    }
//
//    public static JasperPrint generatePdfs(List myList, Map<String, Object> params, String bilan) throws JRException, IOException {
//        System.out.println("haniii pdf");
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(myList);
//        System.out.println("params" + params);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(PdfUtil.class.getResourceAsStream(bilan), params, jrBeanCollectionDataSource);
//        return jasperPrint;
//    }
//
//    public static void generatePdfsNotifaction(List<JasperPrint> myList, String nonFile) throws JRException, IOException {
//        JRExporter exporter = new JRPdfExporter();
//        exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, myList);
//
//        exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, getResponseOutput(nonFile));
//        exporter.exportReport();
//
//    }

}
