import java.awt.*;
import javax.swing.*;

public class SudokuVisualization extends JFrame {
    private static final int GRID_SIZE = 9;
    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
    private int[][] board = new int[GRID_SIZE][GRID_SIZE];
    private JButton solveButton;
    private JButton clearButton;
    
    // Thêm JTextArea để in ra phần giải thích thuật toán
    private JTextArea logArea;

    // Thời gian trễ (milliseconds) để mắt người có thể nhìn kịp thuật toán chạy
    private static final int ANIMATION_DELAY = 10; 

    public SudokuVisualization() {
        setTitle("Sudoku DSA - Trực quan hóa Backtracking (Quay lui)");
        setSize(800, 550); // Tăng chiều rộng để chứa bảng Log
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Tạo lưới Sudoku 9x9
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(font);
                
                // Tạo viền đậm để phân chia các ô 3x3
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                gridPanel.add(cells[row][col]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // 2. Tạo bảng Log giải thích thuật toán ở bên phải
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(300, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Giải thích Thuật toán (Log)"));
        add(scrollPane, BorderLayout.EAST);

        // 3. Bảng điều khiển (Nút bấm)
        JPanel controlPanel = new JPanel();
        solveButton = new JButton("Chạy trực quan hóa");
        clearButton = new JButton("Xóa bàn cờ");

        solveButton.addActionListener(e -> startSolving());
        clearButton.addActionListener(e -> clearBoard());

        controlPanel.add(solveButton);
        controlPanel.add(clearButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Tải một bài toán mẫu
        loadSamplePuzzle();
    }

    private void loadSamplePuzzle() {
        // Ma trận Sudoku mẫu (0 là ô trống)
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
        logArea.setText(""); // Xóa log
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                cells[r][c].setText("");
                cells[r][c].setEditable(true);
                cells[r][c].setBackground(Color.WHITE);
                cells[r][c].setForeground(Color.BLACK);
            }
        }
    }

    // Ghi log ra màn hình an toàn với Java Swing
    private void logMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            // Tự động cuộn xuống dòng mới nhất
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private void startSolving() {
        solveButton.setEnabled(false);
        clearButton.setEnabled(false);
        logArea.setText(""); // Xóa log cũ
        logMessage("BẮT ĐẦU GIẢI...\n----------------");

        // Đọc trạng thái từ giao diện vào mảng board
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                String text = cells[r][c].getText().trim();
                if (!text.isEmpty()) {
                    board[r][c] = Integer.parseInt(text);
                } else {
                    board[r][c] = 0;
                }
            }
        }

        // Chạy thuật toán trên luồng nền (Background thread) để không làm đơ UI
        new Thread(() -> {
            boolean success = solveBoard();
            SwingUtilities.invokeLater(() -> {
                solveButton.setEnabled(true);
                clearButton.setEnabled(true);
                if (success) {
                    logMessage("\nHOÀN THÀNH! Đã tìm thấy nghiệm.");
                } else {
                    logMessage("\nTHẤT BẠI! Bài toán không có nghiệm.");
                    JOptionPane.showMessageDialog(this, "Bài toán không thể giải!");
                }
            });
        }).start();
    }

    // DSA Method: Thuật toán Đệ quy Quay lui (Recursive Backtracking)
    private boolean solveBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                
                // Tìm thấy ô trống
                if (board[row][col] == 0) {
                    
                    // Thử điền các số từ 1 đến 9
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isValidPlacement(row, col, num)) {
                            
                            // 1. CHỌN (Choose)
                            logMessage("→ Thử điền số " + num + " vào [" + row + "][" + col + "]");
                            board[row][col] = num;
                            updateCellUI(row, col, num, Color.BLUE);
                            
                            // 2. KHÁM PHÁ (Explore - Đệ quy xuống tầng tiếp theo)
                            if (solveBoard()) {
                                return true; // Đã tìm thấy nghiệm hợp lệ ở các bước sau!
                            }
                            
                            // 3. QUAY LUI (Un-choose / Backtrack)
                            // Nếu chạy đến đây nghĩa là nhánh đệ quy trên đã trả về false (ngõ cụt)
                            logMessage("← QUAY LUI: Xóa [" + row + "][" + col + "] (Số " + num + " sai)");
                            board[row][col] = 0;
                            updateCellUI(row, col, 0, Color.RED);
                        }
                    }
                    // Đã thử từ 1-9 mà không số nào hợp lệ -> Trả về false để báo hiệu ngõ cụt
                    return false; 
                }
            }
        }
        return true; // Không còn ô trống nào -> Đã giải xong toàn bộ
    }

    // DSA Method: Kiểm tra điều kiện (Constraint Checking)
    private boolean isValidPlacement(int row, int col, int num) {
        // Kiểm tra hàng ngang
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == num) return false;
        }

        // Kiểm tra hàng dọc
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == num) return false;
        }

        // Kiểm tra ô 3x3 chứa nó
        int subgridRowStart = row - row % 3;
        int subgridColStart = col - col % 3;
        for (int r = subgridRowStart; r < subgridRowStart + 3; r++) {
            for (int c = subgridColStart; c < subgridColStart + 3; c++) {
                if (board[r][c] == num) return false;
            }
        }

        return true; // Hợp lệ
    }

    // Cập nhật giao diện (UI) một cách an toàn và tạo độ trễ để tạo hiệu ứng chuyển động
    private void updateCellUI(int row, int col, int num, Color color) {
        try {
            Thread.sleep(ANIMATION_DELAY); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            if (num == 0) {
                cells[row][col].setText("");
            } else {
                cells[row][col].setText(String.valueOf(num));
                cells[row][col].setForeground(color);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuVisualization visualizer = new SudokuVisualization();
            visualizer.setVisible(true);
        });
    }
}