import java.util.*;

public class HeapSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        String[] splitInput = input.split("[,\\s]+");
        int[] numbers = Arrays.stream(splitInput).mapToInt(Integer::parseInt).toArray();
        List<String> steps = new ArrayList<>();

        HeapSort sorter = new HeapSort();
        sorter.sort(numbers, steps);

        // JSON 配列として出力
        System.out.println(steps.toString());
    }

    public void sort(int[] array, List<String> steps) {
        int n = array.length;
        steps.add(Arrays.toString(array)); // 初期状態を記録

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i, steps);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            steps.add(Arrays.toString(array)); // 交換後の状態を記録
            heapify(array, i, 0, steps);
        }
    }

    void heapify(int arr[], int n, int i, List<String> steps) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            steps.add(Arrays.toString(arr)); // 交換後の状態を記録
            heapify(arr, n, largest, steps);
        }
    }
}
