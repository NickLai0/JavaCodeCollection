package code.java.algorithm.sort;

/**
 * 选择排序
 */
public class SelectionSort {

    public static void sort(int[] arr) {
        int n = arr.length;
        //curLoop是当前第几轮循环
        for (int curLoop = 0; curLoop < n; curLoop++) {
            // 寻找[i, n)区间里的最小值的索引
            int minIndex = curLoop;
            //每轮遍历余下未选中到的数据
            for (int j = curLoop + 1; j < n; j++)
                if (arr[j] < arr[minIndex]) {
                    //记录最小数的索引
                    minIndex = j;
                }
            //数据交换不同索引位置数据
            swap(arr, curLoop, minIndex);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
        int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        SelectionSort.sort(arr);
        SortTestHelper.printArray(arr);
    }
}