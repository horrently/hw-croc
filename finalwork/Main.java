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
            
            // Создаем фабрику для создания парсера XML-документов
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            // Создаем объекты для представления документов с данными о продуктах и о наличии товаров
            Document productsDoc = dBuilder.parse(productsFile);
            Document availabilityDoc = dBuilder.parse(availabilityFile);
            
            // Нормализуем структуру документов
            productsDoc.getDocumentElement().normalize();
            availabilityDoc.getDocumentElement().normalize();
            
            // Получаем список продуктов
            NodeList productsList = productsDoc.getElementsByTagName("product");
            
            // Создаем новый документ для записи результатов
            Document resultDoc = dBuilder.newDocument();
            Element results = resultDoc.createElement("results");
            resultDoc.appendChild(results);
            
            // Для каждого продукта вычисляем общее количество товара в наличии и записываем результат в документ
            for (int i = 0; i < productsList.getLength(); i++) {
                Element product = (Element) productsList.item(i);
                String productId = product.getAttribute("id");
    
                int totalAvailable = 0;
                
                // Получаем список элементов с информацией о наличии товара
                NodeList availabilityList = availabilityDoc.getElementsByTagName("item");
                
                /**
                * Для каждого элемента проверяем, соответствует ли он текущему продукту, если да,
                * добавляем его количество к общему количеству товара в наличии
                */
                for (int j = 0; j < availabilityList.getLength(); j++) {
                    Element availability = (Element) availabilityList.item(j);
                    String availabilityProductId = availability.getAttribute("product_id");
    
                    if (productId.equals(availabilityProductId)) {
                        int availableQuantity = Integer.parseInt(availability.getAttribute("quantity"));
                        totalAvailable += availableQuantity;
                    }
                }

                // Создаем элементы для вывода результата
                Element result = resultDoc.createElement("result");
                result.setAttribute("product_id", productId);
                result.setAttribute("totalAvailable", Integer.toString(totalAvailable));
                results.appendChild(result);
    
                // Записываем результат в XML-файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(resultDoc);
                StreamResult streamResult = new StreamResult(new FileOutputStream("finalwork\\out1.xml"));
                transformer.transform(source, streamResult);
    
                // Вывод общего количества товара в наличии в консоль
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
                
                // Создаем фабрику для создания парсера XML-документов
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                
                Document salesDoc = dBuilder.parse(salesFile);
        
                salesDoc.getDocumentElement().normalize();
                
                // Получаем список продаж
                NodeList salesList = salesDoc.getElementsByTagName("sale");
                
                // Создаем переменные для хранения общего количества продаж, начальной и конечной дат продаж
                int totalSales = 0;
                java.util.Date startDate = null;
                java.util.Date endDate = null;
                
                // Проходимся по списку продаж и получаем для каждой продажи количество проданных товаров и дату продажи
                for (int i = 0; i < salesList.getLength(); i++) {
                    Element sale = (Element) salesList.item(i);
                    int quantity = Integer.parseInt(sale.getAttribute("quantity")); // Получаем количество проданных товаров
                    totalSales += quantity; // Добавляем количество проданных товаров к общему количеству продаж
                    
                    // Создаем объект DateFormat для парсинга даты
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // Получаем дату продажи из атрибута "date" в элементе продажи и парсим ее в объект
                    java.util.Date saleDate = dateFormat.parse(sale.getAttribute("date"));
                    
                    // Если начальная дата продаж пуста или текущая дата продаж раньше начальной даты, устанавливаем текущую дату как начальную
                    if (startDate == null || saleDate.before(startDate)) {
                        startDate = saleDate;
                    }

                    // Если конечная дата продаж пуста или текущая дата продаж позже конечной даты, устанавливаем текущую дату как конечную
                    if (endDate == null || saleDate.after(endDate)) {
                        endDate = saleDate;
                    }
                }
                
                // Вычисляем количество дней между начальной и конечной датами продаж
                long days = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                /**
                * Вычисляем среднее количество продаж в день путем деления
                * общего количества продаж на количество дней между начальной и конечной датами продаж
                */
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