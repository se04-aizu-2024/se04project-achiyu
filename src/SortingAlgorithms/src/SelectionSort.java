//package SortingAlgorithms.src;

public class SelectionSort extends Sort {

    // コンストラクタ
    public SelectionSort() {
        super();
    }

    // 配列を選択ソートでソート
    @Override
    void sort(int[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            // 残りの部分の最小値を見つける
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            // 最小値を現在の位置にスワップ
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    // ソートの過程を表示しながらソート
    @Override
    void sort_print(int[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            // 残りの部分の最小値を見つける
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            // 最小値を現在の位置にスワップ
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;

            // ソートの過程を出力
            printArray(array);
        }
    }

    // 配列を出力するためのヘルパーメソッド
    private void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
