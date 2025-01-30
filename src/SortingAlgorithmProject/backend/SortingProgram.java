import java.util.Arrays;
import java.util.Scanner;

public class SortingProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); // Node.js からのデータを受け取る
        scanner.close();

        // 文字列を数値配列に変換
        String[] strNumbers = input.split(",");
        int[] numbers = Arrays.stream(strNumbers).mapToInt(Integer::parseInt).toArray();

        // ソート
        Arrays.sort(numbers);

        // JSON 形式で結果を出力
        System.out.println(Arrays.toString(numbers));
    }
}
