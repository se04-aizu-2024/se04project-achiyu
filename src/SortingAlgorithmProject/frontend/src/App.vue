<script>
import { ref, computed } from "vue";
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
      { name: "Bubble Sort", value: "bubble", com: "O(n²)", url: "https://www.geeksforgeeks.org/bubble-sort-algorithm/" },
      { name: "Heap Sort", value: "heap", com: "O(nlogn)", url: "https://www.geeksforgeeks.org/heap-sort/" },
      { name: "Insert Sort", value: "insert", com: "O(n²)", url: "https://www.geeksforgeeks.org/insertion-sort-algorithm/" },
      { name: "Merge Sort", value: "merge", com: "O(nlogn)", url: "https://www.geeksforgeeks.org/merge-sort/" },
      { name: "Quick Sort", value: "quick", com: "O(n²)", url: "https://www.geeksforgeeks.org/quick-sort-algorithm/" },
      { name: "Selection Sort", value: "selection", com: "O(n²)", url: "https://www.geeksforgeeks.org/selection-sort-algorithm-2/" }
    ];

    const selectedCom = computed(() => {
      const alg = algorithms.find(a => a.value === selectedAlgorithm.value);
      return alg ? alg.com : "";
    })

    const selectedURL = computed(() => {
      const alg = algorithms.find(a => a.value === selectedAlgorithm.value);
      return alg ? alg.url : "";
    })



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

      if (size > 50) {
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
      randomMin, randomMax, randomSize, generateRandomArray, analyzeInput, 
      suggestedAlgorithm, analysis, selectedCom, selectedURL
      
    };
  }
};
</script>

<template>
  <div class="app">
    <h1 class="title">Sorting Visualizer</h1>

    <h2>How to use</h2>
    <p>1. Input array by manual or Generate random array</p>
    <p>2. Select the type of sorting and press the button to perform it.</p>
    <p>※ Press the <strong>analysis</strong> button to suggest the best sort for the array.</p>

    <!-- 手動入力 -->
    <h2>Input array</h2>
    <input class="array_input" v-model="numbers" placeholder="Ex: 2, 5, 3 or 2 5 3" />

    <!-- ランダム配列生成 -->
    <h2>Generate random array</h2>
    <div class="element_input">
      <label>Min:</label>
      <input type="number" v-model.number="randomMin" />
    </div>
    <div class="element_input">
      <label>Max:</label>
      <input type="number" v-model.number="randomMax" />
    </div>
    <div class="element_input">
      <label>Number of elements:</label>
      <input type="number" v-model.number="randomSize" />
    </div>
    <button @click="generateRandomArray">Random generation</button>
    
    <!-- ソートデータ分析 -->
    <h2>Recommend sort</h2>
    <button @click="analyzeInput()">Analysis</button>
    <p v-if="suggestedAlgorithm">🚀 Reccomend sort: {{ suggestedAlgorithm }}</p>


    <!-- ソートアルゴリズム選択 -->
    <h2>Execute sort</h2>
    <div>
      <button v-for="alg in algorithms" :key="alg.value" @click="fetchSortedArray(alg.value)">
        {{ alg.name }}
      </button>
    </div>

    <p v-if="selectedAlgorithm">Time Complexity: {{ selectedCom }}</p>
      <div v-if="selectedAlgorithm">
      <p>URL for more information: </p><a v-if="selectedAlgorithm" href="{{ selectedURL }}">{{ selectedURL }}</a>
    </div>

    
    <!-- Merge Sort の途中経過を可視化 -->
    <h2>Result</h2>
    <div v-if="selectedAlgorithm === 'merge' && steps.length">
      <p v-for="(step, index) in steps" :key="index">
        <span v-if="step.action === 'dividing'">📂 Devide: [{{ step.array.join(", ") }}]</span>
        <span v-else-if="step.action === 'merging'">🔄 Merge: left [{{ step.left.join(", ") }}] / right [{{ step.right.join(", ") }}]</span>
        <span v-else-if="step.action === 'merge_step'">✅ Merging: [{{ step.array.join(", ") }}]</span>
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
  border: 1px solid #ece6ff;
  border-radius: 5px;
}
button {
  margin: 5px;
  padding: 8px 16px;
  background-color: #5a4498;
  color: #ece6ff;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}
button:hover {
  background-color: #3d2e66;
}

.array_input {
  width: 500px;
  height: 30px;
}

.element_input {
  margin-bottom: 10px;
}

.element_input input {
  margin-left: 10px;
}

.app {
  font-family: Verdana, Geneva, Tahoma, sans-serif;
  background-color: #ece6ff;
  height:max-content;
  color: #1e1733;
}

a {
  color: #5a4498;
}

.title {
 font-size: 50px;
 text-align: center;
 color: #ece6ff;
 background-color: #5a4498;
 padding: 30px;
}

</style>
