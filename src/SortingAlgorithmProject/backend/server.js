const express = require("express");
const cors = require("cors");
const { spawn } = require("child_process");

const app = express();
app.use(cors());
app.use(express.json());

const ALGORITHMS = {
    bubble: "BubbleSort",
    heap: "HeapSort",
    insert: "InsertSort",
    selection: "SelectionSort",
    quick: "QuickSort",
    merge: "MergeSort"
};

app.post("/sort", (req, res) => {
    const { numbers, algorithm } = req.body;

    if (!Array.isArray(numbers) || !ALGORITHMS[algorithm]) {
        return res.status(400).json({ error: "Invalid input or unknown algorithm" });
    }

    const javaClass = ALGORITHMS[algorithm];
    const javaProcess = spawn("java", [javaClass]);

    javaProcess.stdin.write(numbers.join(",") + "\n");
    javaProcess.stdin.end();

    let output = "";

    javaProcess.stdout.on("data", (data) => {
        output += data.toString();
    });

    javaProcess.stderr.on("data", (data) => {
        console.error("Java Error:", data.toString());
    });

    javaProcess.on("close", () => {
        console.log("Java Output:", output.trim());  // **デバッグ用に Java の出力を表示**
        try {
            const steps = JSON.parse(output.trim()); // JSON 配列として解析
            res.json({ steps });
        } catch (error) {
            console.error("JSON Parse Error:", error);
            res.status(500).json({ error: "Failed to parse sorting steps", rawOutput: output.trim() });
        }
    });
});

app.post("/analyze", (req, res) => {
    const { numbers } = req.body;

    if (!numbers || numbers.length === 0) {
        return res.status(400).json({ error: "空の配列を分析できません" });
    }

    // 乱雑度を測る（昇順に並んでいる割合）
    let sortedRatio = numbers.reduce((acc, num, i, arr) => {
        if (i === 0 || arr[i - 1] <= num) acc++;
        return acc;
    }, 0) / numbers.length;

    let suggestedAlgorithm = "QuickSort";  // デフォルト推奨

    if (sortedRatio > 0.9) {
        suggestedAlgorithm = "InsertSort";  // ほぼソート済みなら挿入ソート
    } else if (numbers.length < 10) {
        suggestedAlgorithm = "SelectionSort";  // 小規模データには選択ソート
    } else if (numbers.length > 50) {
        suggestedAlgorithm = "MergeSort";  // 大きなデータにはマージソート
    }
    
    
    res.json({ sortedRatio, suggestedAlgorithm });
});


app.listen(5000, () => {
    console.log("Server is running on http://localhost:5000");
});
