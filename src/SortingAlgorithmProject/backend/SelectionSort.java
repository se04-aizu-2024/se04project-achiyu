import java.util.*;

public class SelectionSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        String[] splitInput = input.split("[,\\s]+");
        int[] numbers = Arrays.stream(splitInput).mapToInt(Integer::parseInt).toArray();
        List<String> steps = new ArrayList<>();

        SelectionSort sorter = new SelectionSort();
        sorter.sort(numbers, steps);

        System.out.println(steps.toString());
    }

    public void sort(int[] array, List<String> steps) {
        int n = array.length;
        steps.add(Arrays.toString(array));

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
            steps.add(Arrays.toString(array));
        }
    }
}
