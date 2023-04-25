package finalwork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
* Итоговое ДЗ
*
* @author Dmitriy Cherenkov
*/

/**
*
*  Варианты заданий:
*  1) 20 % 6 = 2
*  Для каждого товара вывести в файл общее
*  количество товара этого типа в наличии
*  2) 20 % 3 = 2
*  Вывести в файл среднее количество проданных
*  товаров в день
*  3) Форматы файлов:
*  20 % 4 = 0
*  XML -> XML
* 
*/

public class Main {
    private static final String PRODUCTS_PATH = "finalwork\\data\\products.xml";
    private static final String SELLERS_PATH = "finalwork\\data\\sellers.xml";
    private static final String INVENT_PATH = "finalwork\\data\\invent.xml";
    private static final String SALES_PATH = "finalwork\\data\\sales.xml";
    
    private Map<Integer, Integer> stockMap = new HashMap<>();
    private Map<Integer, Integer> salesMap = new HashMap<>();
    
    public static void main(String[] args) {
        Main main = new Main();
        
        main.writeTask1();
        main.writeTask2();
    }
    
    private void writeTask1() {
        // Данный метод считает общее количество товара в наличии и записывает результат в файл out1.xml
        try {
            File productsFile = new File(PRODUCTS_PATH);
            File availabilityFile = new File(INVENT_PATH);
    
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    
            Document productsDoc = dBuilder.parse(productsFile);
            Document availabilityDoc = dBuilder.parse(availabilityFile);
    
            productsDoc.getDocumentElement().normalize();
            availabilityDoc.getDocumentElement().normalize();
    
            NodeList productsList = productsDoc.getElementsByTagName("product");
    
            Document resultDoc = dBuilder.newDocument();
            Element results = resultDoc.createElement("results");
            resultDoc.appendChild(results);
    
            for (int i = 0; i < productsList.getLength(); i++) {
                Element product = (Element) productsList.item(i);
                String productId = product.getAttribute("id");
    
                int totalAvailable = 0;
    
                NodeList availabilityList = availabilityDoc.getElementsByTagName("item");
    
                for (int j = 0; j < availabilityList.getLength(); j++) {
                    Element availability = (Element) availabilityList.item(j);
                    String availabilityProductId = availability.getAttribute("product_id");
    
                    if (productId.equals(availabilityProductId)) {
                        int availableQuantity = Integer.parseInt(availability.getAttribute("quantity"));
                        totalAvailable += availableQuantity;
                    }
                    // Создаем элементы для вывода результата
                    Element result = resultDoc.createElement("result");
                    result.setAttribute("product_id", productId);
                    result.setAttribute("totalAvailable", Integer.toString(totalAvailable));
                    results.appendChild(result);
                }
    
                // Записываем результат в XML-файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(resultDoc);
                StreamResult streamResult = new StreamResult(new FileOutputStream("finalwork\\out1.xml"));
                transformer.transform(source, streamResult);
    
                // Выводим общее количество товара в наличии
                System.out.println("Product " + productId + " total available: " + totalAvailable);
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void writeTask2() {
            // Данный метод считает среднее количество продаж в день и записывает результат в файл out2.xml
            try {
                File salesFile = new File(SALES_PATH);
        
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
                Document salesDoc = dBuilder.parse(salesFile);
        
                salesDoc.getDocumentElement().normalize();
        
                NodeList salesList = salesDoc.getElementsByTagName("sale");
        
                int totalSales = 0;
                java.util.Date startDate = null;
                java.util.Date endDate = null;
        
                for (int i = 0; i < salesList.getLength(); i++) {
                    Element sale = (Element) salesList.item(i);
                    int quantity = Integer.parseInt(sale.getAttribute("quantity"));
                    totalSales += quantity;
        
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date saleDate = dateFormat.parse(sale.getAttribute("date"));
        
                    if (startDate == null || saleDate.before(startDate)) {
                        startDate = saleDate;
                    }
        
                    if (endDate == null || saleDate.after(endDate)) {
                        endDate = saleDate;
                    }
                }
        
                long days = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
        
                double averageSales = (double) totalSales / days;
        
                DecimalFormat df = new DecimalFormat("#.##");
        
                // Создаем новый XML документ и добавляем в него элемент с средним количеством продаж в день
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
        
                Element rootElement = doc.createElement("average_sales");
                rootElement.setTextContent(df.format(averageSales));
                doc.appendChild(rootElement);
        
                // Записываем XML документ в файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                FileWriter writer = new FileWriter("finalwork\\out2.xml");
                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);
        
                System.out.println("Average sales per day: " + df.format(averageSales));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
}