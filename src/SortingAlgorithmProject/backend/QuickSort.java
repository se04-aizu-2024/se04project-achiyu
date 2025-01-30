import java.util.*;

public class QuickSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        String[] splitInput = input.split("[,\\s]+");
        int[] numbers = Arrays.stream(splitInput).mapToInt(Integer::parseInt).toArray();
        List<String> steps = new ArrayList<>();

        QuickSort sorter = new QuickSort();
        sorter.sort(numbers, steps);

        System.out.println(steps.toString());
    }

    public void sort(int[] array, List<String> steps) {
        quickSort(array, 0, array.length - 1, steps);
    }

    private void quickSort(int[] array, int low, int high, List<String> steps) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, steps);
            quickSort(array, low, pivotIndex - 1, steps);
            quickSort(array, pivotIndex + 1, high, steps);
        }
    }

    private int partition(int[] array, int low, int high, List<String> steps) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                int temp = array[++i];
                array[i] = array[j];
                array[j] = temp;
                steps.add(Arrays.toString(array)); // 交換後の状態を記録
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        steps.add(Arrays.toString(array)); // ピボット確定
        return i + 1;
    }
}
