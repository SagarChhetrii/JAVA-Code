import java.util.Scanner;

public class matrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Matrix Operations: Addition, Subtraction, Multiplication");

        System.out.print("Enter rows and columns for Matrix A (e.g. 2 3): ");
        int r1 = sc.nextInt();
        int c1 = sc.nextInt();

        System.out.print("Enter rows and columns for Matrix B (e.g. 2 3): ");
        int r2 = sc.nextInt();
        int c2 = sc.nextInt();

        int[][] A = new int[r1][c1];
        int[][] B = new int[r2][c2];

        System.out.println("Enter elements of Matrix A:");
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c1; j++) {
                System.out.print("A[" + i + "][" + j + "]: ");
                A[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter elements of Matrix B:");
        for (int i = 0; i < r2; i++) {
            for (int j = 0; j < c2; j++) {
                System.out.print("B[" + i + "][" + j + "]: ");
                B[i][j] = sc.nextInt();
            }
        }

        System.out.println();
        if (r1 == r2 && c1 == c2) {
            System.out.println("Addition result:");
            int[][] add = add(A, B);
            printMatrix(add);

            System.out.println("Subtraction result (A - B):");
            int[][] sub = subtract(A, B);
            printMatrix(sub);
        } else {
            System.out.println("Addition/Subtraction not possible: Matrices must have same dimensions.");
        }

        if (c1 == r2) {
            System.out.println("Multiplication result (A x B):");
            int[][] mul = multiply(A, B);
            printMatrix(mul);
        } else {
            System.out.println("Multiplication not possible: Number of columns of A must equal number of rows of B.");
        }

        sc.close();
    }

    private static int[][] add(int[][] A, int[][] B) {
        int r = A.length;
        int c = A[0].length;
        int[][] R = new int[r][c];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                R[i][j] = A[i][j] + B[i][j];
        return R;
    }

    private static int[][] subtract(int[][] A, int[][] B) {
        int r = A.length;
        int c = A[0].length;
        int[][] R = new int[r][c];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                R[i][j] = A[i][j] - B[i][j];
        return R;
    }

    private static int[][] multiply(int[][] A, int[][] B) {
        int r1 = A.length;
        int c1 = A[0].length;
        int c2 = B[0].length;
        int[][] R = new int[r1][c2];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                int sum = 0;
                for (int k = 0; k < c1; k++) {
                    sum += A[i][k] * B[k][j];
                }
                R[i][j] = sum;
            }
        }
        return R;
    }

    private static void printMatrix(int[][] M) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }
    }
}
