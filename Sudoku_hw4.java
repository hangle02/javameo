//package com.gradescope.cs201;
import java.util.Arrays;
public class Sudoku_hw4 {
    public static  int[] find_possible_values(int[][] matrix, int x, int y){
        int[] arr_1 = new int[0];
        int[] arr_2 = new int[0];
        
        int startRow = x - (x % 3);
        int startCol = y - (y % 3);
        int[] miss_block = find_block_missing(matrix, 1, arr_1, startRow, startCol);

        int[] miss_x = find_row_missing(matrix, 1, arr_1, x);
        int[] miss_y = find_col_missing(matrix, 1, arr_2, y);
        
        int[] r1 = check_same_value(miss_x, miss_y);
        int[] final_possible = check_same_value(r1, miss_block);
        
        return final_possible;
    }

    private static int[] find_row_missing(int[][] matrix, int index, int[] current_num, int x){
        if (index > 9){
            return current_num;
        }
        for (int i = 0; i < 9; i++ ){
            if(matrix[x][i] == index){
                return find_row_missing(matrix, index + 1, current_num, x);
            }
        }
        
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        
        return find_row_missing(matrix, index + 1, new_num, x);
    }

    private static int[] find_col_missing(int[][] matrix, int index, int[] current_num, int y){
        if (index > 9){
            return current_num;
        }
        for (int i = 0; i < 9; i++ ){
            if(matrix[i][y] == index){
                return find_col_missing(matrix, index + 1, current_num, y);
            }
        }
        
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        return find_col_missing(matrix, index + 1, new_num, y);
    }

    private static int[] find_block_missing(int[][] matrix, int index, int[] current_num, int startRow, int startCol){
        if (index > 9) return current_num;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if(matrix[startRow + i][startCol + j] == index){
                    return find_block_missing(matrix, index + 1, current_num, startRow, startCol);
                }
            }
        }
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        return find_block_missing(matrix, index + 1, new_num, startRow, startCol);
    }
    private static int[] check_same_value(int[] row, int[] col){
        int n = 0;
        for(int i = 0; i < row.length; i++){
            for(int j = 0; j < col.length; j++){
                if(col[j] == row[i]){
                    n = n + 1;
                }
            }
        }
        
        int[] same = new int[n];
        int k = 0; 
        
        for(int i = 0; i < row.length; i++){
            for(int j = 0; j < col.length; j++){
                if(col[j] == row[i]){
                    same[k] = col[j];
                    k++;
                }
            }
        }
        return same;
    }

    private static boolean solve_m(int[][] matrix){
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                if(matrix[r][c] == 0){
                    int[] possible_values = find_possible_values(matrix, r, c);
                    
                    for(int i = 0; i < possible_values.length; i++){
                        matrix[r][c] = possible_values[i];
                        if(solve_m(matrix)){
                            return true; 
                        }
                        matrix[r][c] = 0;
                    }
                    
                    return false; 
                }
            }
        }
        return true;
    }
    
    public static void solve(int[][] matrix){
        solve_m(matrix);
    }

    public static void main(String[] args) {
    }
}
