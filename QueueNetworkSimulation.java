import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.*;

public class QueueNetworkSimulation extends JFrame {

    // =========================================================
    // 1. DATA STRUCTURES & CLASSES
    // =========================================================

    // Lớp đại diện cho Khách hàng
    class Customer implements Comparable<Customer> {
        static int idCounter = 1;
        int id;
        boolean isVIP;
        int serviceTimeTotal; // Tổng thời gian cần để phục vụ (tính bằng frame)
        int serviceTimeLeft;  // Thời gian còn lại
        long arrivalTime;     // Thời điểm đến (để giải quyết hòa giải nếu cùng độ ưu tiên)

        public Customer(boolean isVIP, int durationSeconds) {
            this.id = idCounter++;
            this.isVIP = isVIP;
            this.serviceTimeTotal = durationSeconds * 60; // 60 frames = 1s
            this.serviceTimeLeft = this.serviceTimeTotal;
            this.arrivalTime = System.currentTimeMillis();
        }

        // ĐÂY LÀ TRÁI TIM CỦA PRIORITY QUEUE
        // Thuật toán so sánh: Trả về số âm nếu đối tượng này ưu tiên cao hơn đối tượng kia
        @Override
        public int compareTo(Customer other) {
            if (this.isVIP && !other.isVIP) return -1; // Mình là VIP, kia là thường -> Mình lên trước
            if (!this.isVIP && other.isVIP) return 1;  // Mình là thường, kia là VIP -> Kia lên trước
            // Nếu cùng mức ưu tiên, ai đến trước xếp trước
            return Long.compare(this.arrivalTime, other.arrivalTime);
        }
    }

    // Lớp đại diện cho Quầy phục vụ
    class Teller {
        int id;
        Customer currentCustomer = null;

        public Teller(int id) {
            this.id = id;
        }

        public boolean isAvailable() {
            return currentCustomer == null;
        }

        public void assignCustomer(Customer c) {
            this.currentCustomer = c;
        }
        
        // Cập nhật tiến độ phục vụ (gọi mỗi frame)
        public boolean processTick() {
            if (currentCustomer != null) {
                currentCustomer.serviceTimeLeft--;
                if (currentCustomer.serviceTimeLeft <= 0) {
                    currentCustomer = null; // Phục vụ xong
                    return true; // Trả về true báo hiệu đã xong
                }
            }
            return false;
        }
    }

    // =========================================================
    // 2. BIẾN TOÀN CỤC CỦA MÔ PHỎNG
    // =========================================================

    private PriorityQueue<Customer> waitingQueue = new PriorityQueue<>();
    private List<Teller> tellers = new ArrayList<>();
    
    private SimPanel simPanel;
    private JTextArea logArea;
    private Timer gameLoop;

    private static final int NUM_TELLERS = 3;

    // =========================================================
    // 3. KHỞI TẠO GIAO DIỆN (UI)
    // =========================================================

    public QueueNetworkSimulation() {
        setTitle("Queue Network - Phân luồng ưu tiên (Priority Queue)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Khởi tạo các quầy phục vụ
        for (int i = 1; i <= NUM_TELLERS; i++) {
            tellers.add(new Teller(i));
        }

        // Panel chứa đồ họa mô phỏng
        simPanel = new SimPanel();
        simPanel.setBackground(new Color(240, 248, 255)); // Màu nền xanh nhạt
        add(simPanel, BorderLayout.CENTER);

        // Panel chứa Log (Bên phải)
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(300, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Nhật ký Hệ thống"));
        add(scrollPane, BorderLayout.EAST);

        // Bảng điều khiển (Dưới cùng)
        JPanel controlPanel = new JPanel();
        JButton btnAddRegular = new JButton("Thêm Khách Thường");
        JButton btnAddVIP = new JButton("Thêm Khách VIP (Độ ưu tiên cao)");
        
        btnAddRegular.setBackground(new Color(173, 216, 230)); // Xanh dương nhạt
        btnAddVIP.setBackground(new Color(255, 215, 0)); // Vàng (VIP)

        btnAddRegular.addActionListener(e -> addCustomer(false));
        btnAddVIP.addActionListener(e -> addCustomer(true));

        controlPanel.add(btnAddRegular);
        controlPanel.add(btnAddVIP);
        add(controlPanel, BorderLayout.SOUTH);

        // Bắt đầu vòng lặp mô phỏng (60 FPS)
        startSimulation();
    }

    // =========================================================
    // 4. LOGIC MÔ PHỎNG (CORE)
    // =========================================================

    private void logMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private void addCustomer(boolean isVIP) {
        // Random thời gian phục vụ từ 3 đến 8 giây
        int serviceDuration = 3 + (int)(Math.random() * 6); 
        Customer c = new Customer(isVIP, serviceDuration);
        
        // THÊM VÀO HÀNG ĐỢI ƯU TIÊN (Thời gian O(log N))
        waitingQueue.add(c);
        
        String type = isVIP ? "[VIP]" : "[Thường]";
        logMessage("→ " + type + " Khách #" + c.id + " vào hàng đợi (" + serviceDuration + "s)");
    }

    private void startSimulation() {
        // Timer chạy mỗi 16ms (~60 lần/giây)
        gameLoop = new Timer(16, e -> updateSimulationState());
        gameLoop.start();
    }

    private void updateSimulationState() {
        // 1. Điều phối khách hàng từ Hàng đợi vào các Quầy đang rảnh
        for (Teller teller : tellers) {
            if (teller.isAvailable() && !waitingQueue.isEmpty()) {
                // LẤY KHÁCH ƯU TIÊN CAO NHẤT RA KHỎI HÀNG (Thời gian O(log N))
                Customer nextCustomer = waitingQueue.poll();
                teller.assignCustomer(nextCustomer);
                
                String type = nextCustomer.isVIP ? "[VIP]" : "[Thường]";
                logMessage("✓ Quầy " + teller.id + " gọi " + type + " Khách #" + nextCustomer.id);
            }
        }

        // 2. Xử lý tiến độ công việc tại các quầy
        for (Teller teller : tellers) {
            if (!teller.isAvailable()) {
                int cusId = teller.currentCustomer.id;
                boolean isDone = teller.processTick();
                if (isDone) {
                    logMessage("★ Quầy " + teller.id + " hoàn thành phục vụ Khách #" + cusId);
                }
            }
        }

        // 3. Cập nhật lại đồ họa
        simPanel.repaint();
    }

    // =========================================================
    // 5. VẼ ĐỒ HỌA TRỰC QUAN (VISUALIZATION)
    // =========================================================

    class SimPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Khử răng cưa cho nét vẽ mịn màng hơn
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawTellers(g2d);
            drawWaitingQueue(g2d);
        }

        private void drawTellers(Graphics2D g2d) {
            int startX = 50;
            int yPos = 350; // Vị trí Y của quầy
            int width = 150;
            int height = 100;
            int gap = 50;

            for (int i = 0; i < tellers.size(); i++) {
                Teller t = tellers.get(i);
                int xPos = startX + i * (width + gap);

                // Vẽ Bàn giao dịch
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRoundRect(xPos, yPos, width, height, 15, 15);
                g2d.setColor(Color.WHITE);
                g2d.drawString("QUẦY " + t.id, xPos + 50, yPos + 25);

                // Vẽ Khách hàng đang được phục vụ
                if (!t.isAvailable()) {
                    Customer c = t.currentCustomer;
                    
                    // Màu hộp khách hàng
                    g2d.setColor(c.isVIP ? new Color(255, 215, 0) : new Color(173, 216, 230));
                    g2d.fillRoundRect(xPos + 25, yPos + 40, 100, 40, 10, 10);
                    
                    // Chữ ID khách hàng
                    g2d.setColor(Color.BLACK);
                    g2d.drawString((c.isVIP ? "VIP" : "Thường") + " #" + c.id, xPos + 40, yPos + 65);

                    // Vẽ Thanh tiến trình (Progress Bar)
                    int maxBarWidth = 130;
                    double progressRatio = 1.0 - ((double) c.serviceTimeLeft / c.serviceTimeTotal);
                    int currentBarWidth = (int) (maxBarWidth * progressRatio);

                    g2d.setColor(Color.BLACK); // Viền ngoài
                    g2d.drawRect(xPos + 10, yPos - 30, maxBarWidth, 15);
                    g2d.setColor(Color.GREEN); // Thanh màu đầy dần
                    g2d.fillRect(xPos + 10, yPos - 30, currentBarWidth, 15);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawString("[Đang Trống]", xPos + 40, yPos + 65);
                }
            }
        }

        private void drawWaitingQueue(Graphics2D g2d) {
            // Vẽ tiêu đề khu vực chờ
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2d.drawString("Hàng đợi Ưu tiên (Priority Queue): Lọc từ Min-Heap", 50, 40);

            // Thuật toán: Để trực quan hóa cấu trúc Heap theo ĐÚNG thứ tự ưu tiên
            // ta tạo một bản sao, và poll() liên tục để lấy thứ tự xuất ra.
            PriorityQueue<Customer> copyQueue = new PriorityQueue<>(waitingQueue);
            
            int xPos = 50;
            int yPos = 70;
            int boxWidth = 80;
            int boxHeight = 80;
            int gap = 20;

            int count = 0;
            while (!copyQueue.isEmpty()) {
                Customer c = copyQueue.poll();
                
                // Giới hạn chỉ vẽ tối đa 7 khách hàng trên hàng đầu để tránh tràn màn hình
                if (count < 7) {
                    g2d.setColor(c.isVIP ? new Color(255, 215, 0) : new Color(173, 216, 230));
                    g2d.fillRoundRect(xPos, yPos, boxWidth, boxHeight, 15, 15);
                    
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                    g2d.drawString("#" + c.id, xPos + 25, yPos + 35);
                    g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
                    g2d.drawString(c.isVIP ? "VIP" : "Thường", xPos + 20, yPos + 55);

                    xPos += boxWidth + gap;
                }
                count++;
            }
            
            if (count >= 7) {
                g2d.setColor(Color.RED);
                g2d.drawString("+ " + (count - 7) + " khách nữa...", xPos, yPos + 45);
            }
            
            // Vẽ mũi tên chỉ đường đi từ Hàng Đợi xuống Quầy
            g2d.setColor(Color.GRAY);
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(50, 170, 50, 320);
            g2d.drawLine(50, 320, 450, 320); // Đường ngang phân phối tới các quầy
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QueueNetworkSimulation().setVisible(true);
        });
    }
}