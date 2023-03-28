/**
* 
*
* @author Dmitriy Cherenkov
*/

public class ChessCoordinate {
    private int x;
    private int y;
    
    // конструктор
    public ChessCoordinate(int x, int y) {
        if (!isValidCoord(x) || !isValidCoord(y)) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    // методы для установки координат
    public void setX(int x) {
        if (!isValidCoord(x)) {
            throw new IllegalArgumentException("Invalid coordinate X");
        }
        this.x = x;
    }
    
    public void setY(int y) {
        if (!isValidCoord(y)) {
            throw new IllegalArgumentException("Invalid coordinate Y");
        }
        this.y = y;
    }

    // проверка координат
    private boolean isValidCoord(int coordinate) {
        return coordinate >= 0 && coordinate <= 7;
    }
    
    // метод для получения координат в формате строки
    @Override
    public String toString() {
        char column = (char) ('a' + x); // буквенные координаты
        int row = y + 1; // числовые координаты
        return "" + column + row;
    }

    public static void main(String[] args) {
        //задаем координаты
        ChessCoordinate cell1 = new ChessCoordinate(1, 1);
        ChessCoordinate cell2 = new ChessCoordinate(7, 7);
        ChessCoordinate cell3 = new ChessCoordinate(0, 0);
        ChessCoordinate cell4 = new ChessCoordinate(5, 8); // IllegalArgumentException

        System.out.println(cell1); // b2
        System.out.println(cell2);
        System.out.println(cell3);
        System.out.println(cell4);
    }
    
}
