import java.util.*;

public class MergeSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        scanner.close();

        String[] splitInput = input.split("[,\\s]+");
        int[] numbers = Arrays.stream(splitInput).mapToInt(Integer::parseInt).toArray();
        List<String> steps = new ArrayList<>();

        MergeSort sorter = new MergeSort();
        sorter.sort(numbers, 0, numbers.length - 1, steps);

        // **JSON 配列として出力**
        System.out.println("[" + String.join(",", steps) + "]");
    }

    public void sort(int[] array, int left, int right, List<String> steps) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // **分割の状態を JSON 形式で記録**
            steps.add("{\"action\":\"dividing\",\"array\":" + formatArray(array, left, right) + "}");

            // 再帰的にソート
            sort(array, left, mid, steps);
            sort(array, mid + 1, right, steps);

            steps.add("{\"action\":\"final_dividing\",\"left\":" + formatArray(array, left, mid) +
                      ",\"right\":" + formatArray(array, mid + 1, right) + "}");
                      
            // **マージ処理**
            merge(array, left, mid, right, steps);
            
        }
    }

    private void merge(int[] array, int left, int mid, int right, List<String> steps) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        // **左右の部分配列をコピー**
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // **マージ開始**
        steps.add("{\"action\":\"merging\",\"left\":" + formatArray(leftArray) + ",\"right\":" + formatArray(rightArray) + "}");

        int i = 0, j = 0, k = left;

        // **正しくマージする処理**
        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            // steps.add("{\"action\":\"merge_step\",\"array\":" + formatArray(array) + "}");
            k++;  // `k++` の位置を修正
        }

        // **左側の残りをコピー**
        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
            // steps.add("{\"action\":\"merge_step\",\"array\":" + formatArray(array) + "}");
        }

        // **右側の残りをコピー**
        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
            // steps.add("{\"action\":\"merge_step\",\"array\":" + formatArray(array) + "}");
        }

        steps.add("{\"action\":\"merge_step\",\"array\":" + formatArray(array) + "}");
    }

    // **配列を JSON 形式の文字列に変換**
    private static String formatArray(int[] array, int start, int end) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = start; i <= end; i++) {
            sb.append(array[i]);
            if (i < end) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String formatArray(int[] array) {
        return formatArray(array, 0, array.length - 1);
    }
}
