import java.util.*;

public class InsertSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        scanner.close();

        String[] splitInput = input.split("[,\\s]+");
        int[] numbers = Arrays.stream(splitInput).mapToInt(Integer::parseInt).toArray();
        List<String> steps = new ArrayList<>();

        InsertSort sorter = new InsertSort();
        sorter.sort(numbers, steps);

        System.out.println(steps.toString());
    }

    public void sort(int[] array, List<String> steps) {
        steps.add(Arrays.toString(array)); // 初期状態を記録

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j]; // 1つ後ろにシフト
                j--;
            }
            array[j + 1] = key; // 挿入位置を確定
            steps.add(Arrays.toString(array)); // 挿入後の状態を記録
        }
    }
}
