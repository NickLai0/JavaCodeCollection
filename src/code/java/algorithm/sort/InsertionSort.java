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
public class InsertionSort {

    public static void insertsort(int[] arr) {
        for (int index = 1; index < arr.length; index++) {
            int val = arr[index], j = index;
            while (j > 0 && val < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = val;
            System.out.println("第" + index + "轮排序结果：" + Arrays.toString(arr));
        }
    }

    public static void sort(Comparable[] arr) {
        int n = arr.length;
        for (int index = 0; index < n; index++) {
            // 从index的位置往前找数据
            for (int j = index; j > 0; j--) {
                if (arr[j].compareTo(arr[j - 1]) < 0) {
                    //后面的数据如果小于前面的数据，位置调转
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
            println("第" + index + "轮排序结果：" );
            SortTestHelper.printArray(arr);
        }
    }

    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }


    public static void main(String[] args) {
        int N = 10;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 20);
        InsertionSort.sort(arr);
    }

}