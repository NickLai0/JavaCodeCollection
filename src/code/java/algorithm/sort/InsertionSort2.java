package code.java.algorithm.sort;

import java.util.Arrays;

import static code.java.utils.LU.println;

/**
 * 插入排序思想：
 * 1、有n个数据，就要对比n-1轮
 * 2、每轮排序后，当前i个数据都是按从小到大（或从大到小）排列
 * 3、第n-1轮拍完序后，全部都是有序的数据。
 *
 *按：其实就是从前往后找，或者从后往前找，找到合适自己的位置就插入进去。
 *
 */
public class InsertionSort2 {

    public static void insertSort(int[] arr) {
        //循环n-1轮，第一轮从2个元素开始比较，每比较一轮后，
        // 比较过的数据都有序了，增加一个元素继续下一轮。
        for (int index = 1; index < arr.length; index++) {
            int value = arr[index];//将最后一个作为比较的基准数。
            int j = index;
            while (j > 0 && value < arr[j - 1]/*前面数据大于后面数据*/) {
                //将前后数据调转
                arr[j] = arr[j - 1];
                j--;
            }
            //找到了合适自己的位置，插入进去。
            arr[j] = value;
            System.out.println("第" + index + "轮排序结果：" + Arrays.toString(arr));
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        insertSort(arr);
        SortTestHelper.printArray(arr);
    }

}