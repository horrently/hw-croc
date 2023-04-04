/**
* 
*
* @author Dmitriy Cherenkov
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WordCount {
    public static void main(String[] args) throws IOException {

        // Проверяем, задан ли путь к файлу в аргументе
        if (args.length < 1) {
            throw new IllegalArgumentException("Filename not received");
        }
        
        // Получаем путь к файлу по первому аргументу
        String filePath = args[0];
        
        // Открываем файл для чтения
        try (Scanner scanner = new Scanner(new File(filePath))) {
            
            int wordCount = 0;
            
            while (scanner.hasNext()) {
                scanner.next(); // пропускаем слово
                wordCount++; // увеличиваем счетчик слов
            }
            scanner.close();
            
            System.out.println("Word count: " + wordCount);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath); // Ошибка, если не найден файл
        }
    }
}
