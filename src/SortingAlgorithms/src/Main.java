import java.util.Scanner;

// import SortingAlgorithms.src.BubbleSort;
// import SortingAlgorithms.src.HeapSort;
// import SortingAlgorithms.src.MergeSort;

import java.util.Random;
import java.util.Arrays;

public class Main {
    public static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min; 
        }

        return array;
    }

    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false; 
            }
        }
        return true;
    }

    private static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        

        // int[] array = {29, 10, 37, 14, 45, 30};

        // System.out.println("Choose a sorting algorithm:");
        // System.out.println("1. Quick Sort");
        // System.out.println("2. Insert Sort");
        // System.out.println("3. Merge Sort");
        // System.out.println("4. Selection Sort");
        // System.out.println("5. Bubble Sort");
        // System.out.println("6. Heap Sort");

        // Scanner scanner = new Scanner(System.in);
        // int choice = scanner.nextInt();

        // Sort sorter;

        // switch (choice) {
        //     case 1:
        //         sorter = new QuickSort();
        //         break;
        //     case 2:
        //         sorter = new InsertSort();
        //         break;
        //     case 3:
        //         sorter = new MergeSort();
        //         break;
        //     case 4:
        //         sorter = new SelectionSort();
        //         break;
        //     case 5:
        //         sorter = new BubbleSort();
        //         break;
        //     case 6:
        //         sorter = new HeapSort();
        //         break;
        //     default:
        //         System.out.println("Invalid choice! Defaulting to Quick Sort.");
        //         sorter = new QuickSort();
        // }

        // System.out.println("\nOriginal array:");
        // printArray(array);

        // sorter.sort(original);
        // System.out.println("\nSorted array:");
        // printArray(array);

        // scanner.close();



        /* Test 部分 始まり */

        // 0~100までの数値の100個の配列
        int[] original = generateRandomArray(100, 0, 100);
        
        System.out.println("\nOriginal array:");
        printArray(original);

        int[] original_1 = Arrays.copyOf(original, original.length);
        int[] original_2 = Arrays.copyOf(original, original.length);
        int[] original_3 = Arrays.copyOf(original, original.length);
        int[] original_4 = Arrays.copyOf(original, original.length);
        int[] original_5 = Arrays.copyOf(original, original.length);

        Sort quicksorter = new QuickSort();
        Sort insertsorter = new InsertSort();
        Sort mergesorter = new MergeSort();
        Sort selectionsorter = new SelectionSort();
        Sort bubblesorter = new BubbleSort();
        Sort heapsorter = new HeapSort();


        quicksorter.sort(original);
        System.out.println("\nQuick Sorted array:");
        printArray(original);

        if (isSorted(original)) {
            System.out.println("The array is sorted.");
        } else {
            System.out.println("The array is NOT sorted.");
        }

        insertsorter.sort(original_1);
        System.out.println("\nInsert Sorted array:");
        printArray(original_1);

        if (isSorted(original_1)) {
            System.out.println("The array is sorted.");
        } else {
            System.out.println("The array is NOT sorted.");
        }

        mergesorter.sort(original_2);
        System.out.println("\nMerge Sorted array:");
        printArray(original_2);

        if (isSorted(original_2)) {
            System.out.println("The array is sorted.");
        } else {
            System.out.println("The array is NOT sorted.");
        }

        selectionsorter.sort(original_3);
        System.out.println("\nSelection Sorted array:");
        printArray(original_3);

        if (isSorted(original_3)) {
            System.out.println("The array is sorted.");
        } else {
            System.out.println("The array is NOT sorted.");
        }

        bubblesorter.sort(original_4);
        System.out.println("\nBubble Sorted array:");
        printArray(original_4);

        if (isSorted(original_4)) {
            System.out.println("The array is sorted.");
        } else {
            System.out.println("The array is NOT sorted.");
        }

        heapsorter.sort(original_5);
        System.out.println("\nHeap Sorted array:");
        printArray(original_5);

        if (isSorted(original_5)) {
            System.out.println("The array is sorted.");
        } else {
            System.out.println("The array is NOT sorted.");
        }

        /* Test 部分　終わり */

        
    }

    
}

