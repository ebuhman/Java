package Java_Sprint.Practice;

public class ClassPractice {
    
    public static int Addition(int x, int y) {
        int sum = x + y;
        return sum;
    }

    public static int Subtraction(int x, int y) {
        int difference = x - y;
        return difference;
    }

    public static int Multiplication(int x, int y) {
        int product = x * y;
        return product;
    }

    public static int Division(int x, int y) {
        int quotient = x / y;
        return quotient;
    }

    // Behaviors
    public static void main(String[] args) {
        System.out.println(Addition(5, 4));
        System.out.println(Subtraction(7, 4));
        System.out.println(Multiplication(6, 6));
        System.out.println(Division(4, 2));
        System.out.println("Congratulations on writing your first Java class!");
    }
}
