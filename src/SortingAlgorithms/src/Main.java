//import SortingAlgorithms.src.SortingAlgorithms.src.InsertSort;
//import SortingAlgorithms.src.SortingAlgorithms.src.MergeSort;

//package SortingAlgorithms.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] array = {5, 2, 9, 1, 5, 6};

        System.out.println("Choose a sorting algorithm:");
        System.out.println("1. Merge Sort");
        System.out.println("2. Selection Sort");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        Sort sorter;

        switch (choice) {
            case 1:
                sorter = new MergeSort();
                break;
            case 2:
                sorter = new SelectionSort();
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Selection Sort.");
                sorter = new SelectionSort();
        }

        System.out.println("Before sorting:");
        printArray(array);

        sorter.sort(array);

        System.out.println("After sorting:");
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
