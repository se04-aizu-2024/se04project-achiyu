<script>
import { ref } from "vue";
import axios from "axios";

export default {
  setup() {
    const numbers = ref("");
    const steps = ref([]);
    const selectedAlgorithm = ref("");
    const randomMin = ref(1);
    const randomMax = ref(100);
    const randomSize = ref(10);
    const analysis = ref(null);
    const suggestedAlgorithm = ref("");
    
    // ソートアルゴリズムのリスト
    const algorithms = [
      { name: "Bubble Sort", value: "bubble" },
      { name: "Heap Sort", value: "heap" },
      { name: "Insert Sort", value: "insert" },
      { name: "Merge Sort", value: "merge" },
      { name: "Quick Sort", value: "quick" },
      { name: "Selection Sort", value: "selection" }
    ];

    // 入力データを分析し、最適なソートを提案
    const analyzeInput = async () => {
      if (!numbers.value.trim()) {
        console.warn("配列が空です");
        return;
      }
      try {
        const response = await axios.post("http://localhost:5000/analyze", {
          numbers: numbers.value.split(/[, ]+/).map(Number)
        });
        analysis.value = response.data;
        suggestedAlgorithm.value = response.data.suggestedAlgorithm;
      } catch (error) {
        console.error("Error analyzing input:", error);
      }
    };

    // ソートリクエストを送信
    const fetchSortedArray = async (algorithm) => {
      if (!numbers.value.trim()) {
        console.warn("配列が空です");
        return;
      }

      selectedAlgorithm.value = algorithm;
      steps.value = [];

      try {
        const response = await axios.post("http://localhost:5000/sort", {
          numbers: numbers.value.split(/[, ]+/).map(Number), // スペース・カンマ区切り対応
          algorithm
        });

        console.log("API Response:", response.data); // **デバッグ用**
        
        steps.value = response.data.steps.map(step => {
          try {
            return typeof step === "string" ? JSON.parse(step) : step; // **JSON 解析**
          } catch (error) {
            console.error("JSON Parse Error:", step, error);
            return null;
          }
        }).filter(Boolean); // **パースできなかったデータを除外**
        
        console.log("Parsed Steps:", steps.value); // **デバッグ用**
      } catch (error) {
        console.error("Error fetching sorted array:", error);
      }
    };

    // ランダム配列を生成
    const generateRandomArray = () => {
      const min = randomMin.value;
      const max = randomMax.value;
      const size = randomSize.value;

      if (size >= 30) {
        alert("上限は30です");
        return;
      }

      if (min >= max || size <= 0) {
        alert("正しい値を入力してください");
        return;
      }

      numbers.value = Array.from({ length: size }, () => 
        Math.floor(Math.random() * (max - min + 1)) + min
      ).join(", ");
    };

    return { 
      numbers, steps, algorithms, fetchSortedArray, selectedAlgorithm, 
      randomMin, randomMax, randomSize, generateRandomArray, analyzeInput, suggestedAlgorithm, analysis
      
    };
  }
};
</script>

<template>
  <div>
    <h1>Sorting Visualizer</h1>

    <!-- 手動入力 -->
    <h2>手動入力</h2>
    <input v-model="numbers" placeholder="例: 2, 5, 3 または 2 5 3" />

    <!-- ランダム配列生成 -->
    <h2>ランダム配列を生成</h2>
    <div>
      <label>最小値:</label>
      <input type="number" v-model.number="randomMin" />
    </div>
    <div>
      <label>最大値:</label>
      <input type="number" v-model.number="randomMax" />
    </div>
    <div>
      <label>要素数:</label>
      <input type="number" v-model.number="randomSize" />
    </div>
    <button @click="generateRandomArray">ランダム生成</button>
    
    <!-- ソートデータ分析 -->
    <h2>入力データ分析</h2>
    <button @click="analyzeInput()">分析</button>
    <p v-if="suggestedAlgorithm">🚀 推奨ソート: {{ suggestedAlgorithm }}</p>


    <!-- ソートアルゴリズム選択 -->
    <h2>ソートを実行</h2>
    <div>
      <button v-for="alg in algorithms" :key="alg.value" @click="fetchSortedArray(alg.value)">
        {{ alg.name }}
      </button>
    </div>

    <!-- ソート分析情報 -->
    <h2>ソート分析</h2>
    <p v-if="analysis">
      🔍 比較回数: {{ analysis.comparisons }} / 交換回数: {{ analysis.swaps }} / 計算量: {{ analysis.complexity }}
    </p>
    
    <!-- Merge Sort の途中経過を可視化 -->
    <div v-if="selectedAlgorithm === 'merge' && steps.length">
      <p v-for="(step, index) in steps" :key="index">
        <span v-if="step.action === 'dividing'">📂 分割: [{{ step.array.join(", ") }}]</span>
        <span v-else-if="step.action === 'merging'">🔄 マージ: 左 [{{ step.left.join(", ") }}] / 右 [{{ step.right.join(", ") }}]</span>
        <span v-else-if="step.action === 'merge_step'">✅ マージ中: [{{ step.array.join(", ") }}]</span>
      </p>
    </div>

    <!-- その他のソートの途中経過 -->
    <div v-else-if="steps.length">
      <p v-for="(step, index) in steps" :key="index">
        {{ step }}
      </p>
    </div>
  </div>
</template>

<style>
input {
  margin-right: 10px;
  padding: 5px;
  border: 1px solid #ccc;
}
button {
  margin: 5px;
  padding: 8px 16px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
