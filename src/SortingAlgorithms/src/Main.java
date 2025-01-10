public class Main {
    public static void main(String[] args) {
        int[] array = {29, 10, 37, 14, 45, 30};

        System.out.println("Original array:");
        printArray(array);

        // QuickSort のテスト
        QuickSort quickSort = new QuickSort();
        quickSort.sort(array);
        System.out.println("\nSorted array with QuickSort:");
        printArray(array);

        // 配列を元に戻す
        array = new int[]{29, 10, 37, 14, 45, 30};

        // InsertSort のテスト
        InsertSort insertSort = new InsertSort();
        insertSort.sort(array);
        System.out.println("\nSorted array with InsertSort:");
        printArray(array);
    }

    private static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
