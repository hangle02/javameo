// package com.gradescope.cs201; 
// (Comment lại package nếu bạn chạy file này độc lập ngoài thư mục dự án)

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class SudokuVisualization extends JFrame {
    private static final int GRID_SIZE = 9;
    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
    private int[][] matrix = new int[GRID_SIZE][GRID_SIZE];
    private JButton solveButton;
    private JButton clearButton;

    // Thời gian trễ để quan sát màu sắc chuyển động (giảm xuống nếu muốn chạy nhanh hơn)
    private static final int ANIMATION_DELAY = 30;

    public SudokuVisualization() {
        setTitle("Sudoku Visualization");
        setSize(400, 450); // Đã giảm size của board cho gọn gàng và dễ nhìn
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Tạo lưới UI
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        Font font = new Font("SansSerif", Font.BOLD, 18);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(font);
                
                // Viền đậm cho các khối 3x3
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                gridPanel.add(cells[row][col]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // Bảng điều khiển
        JPanel controlPanel = new JPanel();
        solveButton = new JButton("Giải (Chạy Visual)");
        clearButton = new JButton("Xóa bàn cờ");

        solveButton.addActionListener(e -> startSolving());
        clearButton.addActionListener(e -> clearBoard());

        controlPanel.add(solveButton);
        controlPanel.add(clearButton);
        add(controlPanel, BorderLayout.SOUTH);

        loadSamplePuzzle();
    }

    private void loadSamplePuzzle() {
        int[][] sample = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                if (sample[r][c] != 0) {
                    cells[r][c].setText(String.valueOf(sample[r][c]));
                    cells[r][c].setEditable(false);
                    cells[r][c].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[r][c].setText("");
                    cells[r][c].setEditable(true);
                    cells[r][c].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void clearBoard() {
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                cells[r][c].setText("");
                cells[r][c].setEditable(true);
                cells[r][c].setBackground(Color.WHITE);
                cells[r][c].setForeground(Color.BLACK);
            }
        }
    }

    private void startSolving() {
        solveButton.setEnabled(false);
        clearButton.setEnabled(false);

        // Đọc dữ liệu từ UI vào ma trận của thuật toán
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                String text = cells[r][c].getText().trim();
                matrix[r][c] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }

        // Chạy thuật toán trên luồng nền
        new Thread(() -> {
            boolean success = solve_m(matrix);
            SwingUtilities.invokeLater(() -> {
                solveButton.setEnabled(true);
                clearButton.setEnabled(true);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Hoàn thành!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể giải!");
                }
            });
        }).start();
    }

    // Cập nhật UI với màu sắc để trực quan hóa
    private void updateUI(int r, int c, int val, Color textCol, Color bgCol) {
        try { Thread.sleep(ANIMATION_DELAY); } catch (InterruptedException e) {}
        SwingUtilities.invokeLater(() -> {
            cells[r][c].setBackground(bgCol);
            if (val == 0) {
                cells[r][c].setText("");
            } else {
                cells[r][c].setText(String.valueOf(val));
                cells[r][c].setForeground(textCol);
            }
        });
    }

    // =========================================================================
    // DƯỚI ĐÂY LÀ PHẦN LOGIC THUẬT TOÁN BẠN ĐÃ VIẾT (Được tích hợp UI Update)
    // =========================================================================

    public int[] find_possible_values(int[][] matrix, int x, int y){
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

    private int[] find_row_missing(int[][] matrix, int index, int[] current_num, int x){
        if (index > 9) return current_num;
        for (int i = 0; i < 9; i++ ){
            if(matrix[x][i] == index){
                return find_row_missing(matrix, index + 1, current_num, x);
            }
        }
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        return find_row_missing(matrix, index + 1, new_num, x);
    }

    private int[] find_col_missing(int[][] matrix, int index, int[] current_num, int y){
        if (index > 9) return current_num;
        for (int i = 0; i < 9; i++ ){
            if(matrix[i][y] == index){
                return find_col_missing(matrix, index + 1, current_num, y);
            }
        }
        int[] new_num = Arrays.copyOf(current_num, current_num.length + 1);
        new_num[new_num.length - 1] = index;
        return find_col_missing(matrix, index + 1, new_num, y);
    }

    private int[] find_block_missing(int[][] matrix, int index, int[] current_num, int startRow, int startCol){
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

    private int[] check_same_value(int[] row, int[] col){
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

    private boolean solve_m(int[][] matrix){
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                if(matrix[r][c] == 0){
                    
                    // BẬT MÀU VÀNG: Báo hiệu đang tìm giá trị khả thi tại ô này
                    updateUI(r, c, 0, Color.BLACK, Color.YELLOW);
                    
                    int[] possible_values = find_possible_values(matrix, r, c);
                    
                    for(int i = 0; i < possible_values.length; i++){
                        matrix[r][c] = possible_values[i];
                        
                        // HIỆN SỐ MÀU XANH: Thử điền một giá trị khả thi
                        updateUI(r, c, matrix[r][c], Color.BLUE, Color.WHITE);
                        
                        if(solve_m(matrix)){
                            return true; 
                        }
                        
                        // QUAY LUI (Backtrack) - Nếu bước trước không thành công, xóa số
                        matrix[r][c] = 0;
                    }
                    
                    // BẬT MÀU ĐỎ: Nếu đã thử hết các số khả thi mà vẫn sai, báo ngõ cụt!
                    updateUI(r, c, 0, Color.BLACK, Color.RED);
                    try { Thread.sleep(ANIMATION_DELAY * 2); } catch (Exception e){} // Dừng lâu hơn xíu để nhìn rõ màu đỏ
                    updateUI(r, c, 0, Color.BLACK, Color.WHITE); // Trả về màu trắng để lùi lại ô trước
                    
                    return false; 
                }
            }
        }
        return true;
    }
    
    public void solve(int[][] matrix){
        solve_m(matrix);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuVisualization().setVisible(true);
        });
    }
}