//package com.gradescope.cs201;
import java.util.Arrays;
public class Sudoku_hw4 {
    public static  int[] find_possible_values(int[][] matrix, int x, int y){
        int[] arr_1 = new int[0];
        int[] arr_2 = new int[0];
        
        // Bắt đầu xét từ số 1
        int[] miss_x = find_row_missing(matrix, 1, arr_1, x);
        int[] miss_y = find_col_missing(matrix, 1, arr_2, y);
        
        int[] r = check_same_value(miss_x, miss_y);
        return r;
    }

    private static int[] find_row_missing(int[][] matrix, int index, int[] current_num, int x){
        if (index > 9){
            return current_num;
        }
        for (int i = 0; i < 9; i++ ){
            if(matrix[x][i] == index){
                // Đã thấy index trong hàng, return đệ quy để chuyển sang số index + 1
                return find_row_missing(matrix, index + 1, current_num, x);
            }
        }
        // Nếu vòng lặp for chạy xong mà không bị return, nghĩa là index bị thiếu
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        
        // Gọi đệ quy và BẮT BUỘC phải return kết quả
        return find_row_missing(matrix, index + 1, new_num, x);
    }

    private static int[] find_col_missing(int[][] matrix, int index, int[] current_num, int y){
        if (index > 9){
            return current_num;
        }
        for (int i = 0; i < 9; i++ ){
            if(matrix[i][y] == index){
                // Đã thấy index trong cột, return đệ quy để chuyển sang số tiếp theo
                return find_col_missing(matrix, index + 1, current_num, y);
            }
        }
        
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        return find_col_missing(matrix, index + 1, new_num, y);
    }

    private static int[] check_same_value(int[] row, int[] col){
        int n = 0;
        // 1. Đếm số lượng phần tử chung
        for(int i = 0; i < row.length; i++){
            for(int j = 0; j < col.length; j++){
                if(col[j] == row[i]){
                    n = n + 1;
                }
            }
        }
        
        // 2. Tạo mảng và nhặt các phần tử chung vào
        int[] same = new int[n];
        int k = 0; // Biến k quản lý vị trí thêm vào mảng same
        
        for(int i = 0; i < row.length; i++){
            for(int j = 0; j < col.length; j++){
                if(col[j] == row[i]){
                    same[k] = col[j];
                    k++; // Chỉ tăng k khi tìm thấy phần tử chung
                }
            }
        }
        return same;
    }

    public static void solve(int[][] matrix){
        for(int i=0; i< 3; i++){
            for(int j=0; j<3; j++){
                solve_3x3(matrix, i*3, j*3);
            }
        }
    }
    private static void solve_3x3(int[][] matrix, int r, int c){
        for (int i=0; i<2; i++){
            for (int j=0; j<2; j++){
                if(!check_exist_val(matrix, r+i, c+j)){
                int point = solve_3x3_apoint(matrix, r+i, c+j);
                matrix[r+i][c+j] = point;

                }
            }
        }
    }
    private static int solve_3x3_apoint(int[][] matrix, int row, int col){
       int[] pos = new int[9];
       for(int i=0; i<2; i++){
        for(int j=0; j<2; j++){
            if(!check_exist_val(matrix, row+j, col+i)){
                int[] pos_val = find_possible_values(matrix, i, j);
                for(int k=0; k< pos_val.length; k++){
                    pos[pos_val[k]-1]+=1;
                }

            }
        }
       }
       int most_pos_val = 1;
       int max_i = 0;
       for (int index=1; index< 9; index++){
            if(pos[max_i] < pos[index]){
                most_pos_val = index+1;
                max_i = index;
            }
       }
       return most_pos_val;
    }

    private static boolean check_exist_val(int[][] matrix, int row, int col){
        return matrix[row][col] !=0;
    }
    public static void test_p2(int[][] matrix, int row, int col){
        int[] pos_val = find_possible_values(matrix, row, col);
        System.out.println(Arrays.toString(pos_val));

    }

    public static void main(String[] args) {
        
        int[][] matrix_1 = {
            {0, 5, 0, 0, 0, 2, 0, 0, 6},
            {0, 8, 0, 0, 0, 6, 7, 0, 3},
            {0, 0, 0, 7, 4, 0, 0, 0, 8},
            {6, 7, 0, 9, 8, 0, 0, 0, 0},
            {0, 3, 1, 0, 5, 0, 6, 0, 9},
            {0, 0, 0, 0, 0, 3, 8, 2, 0},
            {0, 0, 0, 0, 0, 0, 1, 8, 0},
            {0, 0, 0, 3, 0, 0, 0, 0, 2},
            {9, 2, 0, 5, 0, 0, 0, 7, 0}
        };

      solve(matrix_1);
      System.out.println(Arrays.toString(matrix_1));
    }
}
