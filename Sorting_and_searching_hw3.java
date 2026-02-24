// package com.gradescope.cs201;
// import com.gradescope.cs201.UnsortedArrayBlackbox;
// import com.gradescope.cs201.SortedArrayBlackbox;

public class Sorting_and_searching_hw3 {
    public Sorting_and_searching_hw3() {
    }

   public int get_median_index(UnsortedArrayBlackbox unsorted_arr_bb) {
        int n = unsorted_arr_bb.get_length();
        
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        return quickselect(unsorted_arr_bb, indices, 0, n - 1, n / 2);
    }
   private static int partition(UnsortedArrayBlackbox bb, int[] indices, int left, int right, int pivot_idx_in_indices) {
        swap(indices, pivot_idx_in_indices, right);
        int store_index = left;
        
        for (int i = left; i < right; i++) {
            int cmp = bb.compare(indices[i], indices[right]);
            
            if (cmp >= 0) { 
                swap(indices, store_index, i);
                store_index++;
            }
        }
        swap(indices, right, store_index);
        return store_index;
     }
    private static int quickselect(UnsortedArrayBlackbox bb, int[] indices, int left, int right, int k) {
        if (left == right) {
            return indices[left];
        }
        
        int pivot_idx_in_indices = left + (right - left) / 2;
        pivot_idx_in_indices = partition(bb, indices, left, right, pivot_idx_in_indices);
        
        if (k == pivot_idx_in_indices) {
            return indices[k];
        } else if (k < pivot_idx_in_indices) {
            return quickselect(bb, indices, left, pivot_idx_in_indices - 1, k);
        } else {
            return quickselect(bb, indices, pivot_idx_in_indices + 1, right, k);
        }
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int get_index(SortedArrayBlackbox sorted_arr_bb, int x) {
        //USING BINARY SEARCH
        int left = 0;
        int right = sorted_arr_bb.get_length() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = sorted_arr_bb.compare(mid, x);
            
            if (cmp == 0) {
                return mid; 
            } else if (cmp == 1) {
                left = mid + 1;
            } else {
                right = mid - 1; 
            }
        }
        return -1; 

    }

    public static void main(String[] args) {
        // Sorting_and_searching_hw3 sort_and_search = new Sorting_and_searching_hw3();
        // /* test the median */
        // //
        // int[] unsorted_arr_1 = {1, 49, 3, 54, 29};
        // UnsortedArrayBlackbox unsorted_arr_bb_1 = new UnsortedArrayBlackbox(unsorted_arr_1);
        // System.out.println(sort_and_search.get_median_index(unsorted_arr_bb_1) + " must be 4");
        // System.out.println(unsorted_arr_bb_1.get_comparison_num() + " must be <= 9"); 
        // //
        // int[] unsorted_arr_2 = {1, 49, 29, 54, 3, 11, 20, 35, 40, 9, 67};
        // UnsortedArrayBlackbox unsorted_arr_bb_2 = new UnsortedArrayBlackbox(unsorted_arr_2);
        // System.out.println(sort_and_search.get_median_index(unsorted_arr_bb_2) + " must be 2");
        // System.out.println(unsorted_arr_bb_2.get_comparison_num() + " must be <= 45");
        // //      
        // /* test the searching */
        // //
        // int[] sorted_arr_1 = {1, 3, 9, 11, 20, 29, 35, 40, 49, 54, 67, 70};
        // SortedArrayBlackbox sorted_arr_bb_1 = new SortedArrayBlackbox(sorted_arr_1);
        // System.out.println(sort_and_search.get_index(sorted_arr_bb_1, 67) + " must be 10");
        // System.out.println(sorted_arr_bb_1.get_comparison_num() + " must be <= 4");
        // //
        // int[] sorted_arr_2 = {1, 3, 9, 11, 20, 29, 35, 40, 49, 54, 67, 70};
        // SortedArrayBlackbox sorted_arr_bb_2 = new SortedArrayBlackbox(sorted_arr_2);
        // System.out.println(sort_and_search.get_index(sorted_arr_bb_2, 35) + " must be 6");
        // System.out.println(sorted_arr_bb_2.get_comparison_num() + " must be <= 4");
        // //
        // //
        // int[] sorted_arr_3 = {1, 3, 9, 11, 20, 29, 35, 40, 49, 54, 67, 70};
        // SortedArrayBlackbox sorted_arr_bb_3 = new SortedArrayBlackbox(sorted_arr_3);
        // System.out.println(sort_and_search.get_index(sorted_arr_bb_3, 12) + " must be -1");
        // System.out.println(sorted_arr_bb_3.get_comparison_num() + " must be <= 4");         
    }
}