import java.util.*;

public class BubbleSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        scanner.close();

        String[] splitInput = input.split("[,\\s]+");
        int[] numbers = Arrays.stream(splitInput).mapToInt(Integer::parseInt).toArray();
        List<String> steps = new ArrayList<>();
    
        BubbleSort sorter = new BubbleSort();
        sorter.sort(numbers, steps);


        System.out.println(steps.toString());
    }

    public void sort(int[] array, List<String> steps) {
        int n = array.length;
        steps.add(Arrays.toString(array));

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                
                if (array[j] > array[j + 1]) {
                    
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    steps.add(Arrays.toString(array));
                }
            }
        }
    }
}
