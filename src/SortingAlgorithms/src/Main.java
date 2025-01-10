import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] array = {29, 10, 37, 14, 45, 30};

        System.out.println("Choose a sorting algorithm:");
        System.out.println("1. Quick Sort");
        System.out.println("2. Insert Sort");
        System.out.println("3. Merge Sort");
        System.out.println("4. Selection Sort");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        Sort sorter;

        switch (choice) {
            case 1:
                sorter = new QuickSort();
                break;
            case 2:
                sorter = new InsertSort();
                break;
            case 3:
                sorter = new MergeSort();
                break;
            case 4:
                sorter = new SelectionSort();
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Quick Sort.");
                sorter = new QuickSort();
        }

        System.out.println("\nOriginal array:");
        printArray(array);

        sorter.sort(array);

        System.out.println("\nSorted array:");
        printArray(array);

        scanner.close();
    }

    private static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

