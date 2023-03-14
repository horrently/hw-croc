/**
* Javadoc
*
* @author Dmitriy Cherenkov
*/

public class hw1 {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0 & i % 5 == 0) {
                System.out.printf("FizzBuzz \n");
            }
            else if (i % 3 == 0) {
                System.out.printf("Fizz \n");
            }
            else if (i % 5 == 0) {
                System.out.printf("Buzz \n");
            }
            else {
                System.out.println( i );
            }
        }
    }
}
