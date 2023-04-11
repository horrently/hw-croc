/**
* 
*
* @author Dmitriy Cherenkov
*/

import java.util.ArrayDeque;
import java.util.Deque;

public class normalizePath {
    public static String pathNormalizer (String path) {
        // Проверяем, что задан путь
        if (path.length() == 0) {
            throw new IllegalArgumentException("Путь не задан");
        }

        // Создаем стек для хранения элементов пути
        Deque<String> stack = new ArrayDeque<>();

        // Разделяем путь на элементы по символу "/"
        String[] dirs = path.split("/");

        // Проходим по списку директорий
        for (String i : dirs) {
            if (i.equals(".")) {
                // Ничего не делаем
            }
            else if (i.equals("..")) {
                // Извлекаем последний элемент
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            }
            else if (!i.isEmpty()) {
                // Добавляем элемент, если это имя директории
                stack.push(i);
            }
        }

        // Записываем новый путь из оставшихся в стеке директорий
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append("/");
            builder.append(stack.pollLast());
        }
        if (builder.length() == 0) {
            builder.append(".");
        }
        return builder.toString();
    }
    
    public static void main(String[] args) {

        String path = "КРОК/task_5_2/src/./../../task_5_1/../../../мемы/котики";
        String normalizedPath = pathNormalizer(path);
        System.out.println(normalizedPath);

    }
}
