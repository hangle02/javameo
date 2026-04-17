import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;

public class QueueNetworkSimulation extends JFrame {

    // =========================================================
    // 1. DATA STRUCTURES & ALGORITHM STATE (Từ code gốc của bạn)
    // =========================================================
    private int n;
    private String[][] patient_procedures;
    private int[] fix_duration = {10, 10, 4, 8, 7};
    private String[] room_names = {"PrimaryCare 1", "PrimaryCare 2", "X-ray", "Lab test", "Pharmacy"};
    
    private int[] queueArrivalTime;
    private boolean[] isDone;
    private int[] nextFreeTime;
    private int[] step;
    private int[] prevRoom;
    private int[] roomFreeTime;
    private PriorityQueue<Integer>[] roomQueues;
    
    private int time = 0;
    private int doneCount = 0;

    // Các biến phụ trợ cho Giao diện UI
    private Integer[] currentServing = new Integer[5]; // Lưu ID bệnh nhân đang trong phòng (0-4)
    private Color[] patientColors; // Màu cố định cho mỗi bệnh nhân để dễ theo dõi
    
    private Timer timer;
    private SimPanel simPanel;
    private JTextArea logArea;

    // =========================================================
    // 2. KHỞI TẠO GIAO DIỆN
    // =========================================================
    public QueueNetworkSimulation(String[][] patients) {
        this.patient_procedures = patients;
        this.n = patients.length;
        
        setTitle("Hospital Procedure Network (Queue Network Simulation)");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        simPanel = new SimPanel();
        simPanel.setBackground(new Color(245, 245, 250));
        add(simPanel, BorderLayout.CENTER);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(350, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Log"));
        add(scrollPane, BorderLayout.EAST);

        JPanel controlPanel = new JPanel();
        JButton startBtn = new JButton("Bắt đầu Mô phỏng");
        startBtn.addActionListener(e -> {
            startBtn.setEnabled(false);
            initAlgorithmState();
            timer.start();
        });
        controlPanel.add(startBtn);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Khởi tạo màu ngẫu nhiên cho bệnh nhân
        patientColors = new Color[n];
        for(int i = 0; i < n; i++) {
            patientColors[i] = new Color(
                50 + (int)(Math.random() * 150),
                50 + (int)(Math.random() * 150),
                50 + (int)(Math.random() * 150)
            );
        }
    }

    private void logMessage(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    // =========================================================
    // 3. LOGIC THUẬT TOÁN (Tách từ vòng lặp while của bạn)
    // =========================================================
    private void initAlgorithmState() {
        queueArrivalTime = new int[n];
        isDone = new boolean[n];
        nextFreeTime = new int[n];
        step = new int[n];
        prevRoom = new int[n];
        for (int i = 0; i < n; i++) prevRoom[i] = 5;
        
        roomFreeTime = new int[5];
        roomQueues = new PriorityQueue[5];
        
        for (int i = 0; i < 5; i++) {
            roomQueues[i] = new PriorityQueue<>((p1, p2) -> {
                if (queueArrivalTime[p1] != queueArrivalTime[p2]) {
                    return Integer.compare(queueArrivalTime[p1], queueArrivalTime[p2]);
                }
                if (prevRoom[p1] != prevRoom[p2]) {
                    return Integer.compare(prevRoom[p1], prevRoom[p2]); 
                }
                return Integer.compare(p1, p2); 
            });
        }

        time = 0;
        doneCount = 0;
        
        // Timer chạy mỗi 500ms (Nửa giây) tương đương 1 đơn vị 'time'
        timer = new Timer(500, e -> processTick());
    }

    private int getRoomId(String proc) {
        if (proc.equals("PrimaryCare_1")) return 0;
        if (proc.equals("PrimaryCare_2")) return 1;
        if (proc.equals("X-ray"))         return 2;
        if (proc.equals("Lab test"))      return 3;
        if (proc.equals("Pharmacy"))      return 4;
        return -1;
    }

    // Hàm này chạy logic của vòng lặp "while (doneCount < n)" trong 1 bước thời gian
    private void processTick() {
        if (doneCount >= n) {
            timer.stop();
            logMessage("================================");
            logMessage("TẤT CẢ ĐÃ XONG TẠI TIME = " + (time - 1)); // -1 vì time đã cộng dư ở tick cuối
            return;
        }

        // 1. Cập nhật trạng thái bệnh nhân
        for(int patient = 0; patient < n; patient++){
            if (!isDone[patient] && nextFreeTime[patient] == time) {
                
                // Trực quan: Rời khỏi phòng hiện tại
                for (int r = 0; r < 5; r++) {
                    if (currentServing[r] != null && currentServing[r] == patient) {
                        currentServing[r] = null;
                    }
                }

                if(step[patient] >= 6 || patient_procedures[patient][step[patient]].equals("")){
                    isDone[patient] = true;
                    doneCount++;
                    logMessage("[Time " + time + "] ☆ Bệnh nhân " + patient + " đã RA VỀ.");
                } else {
                    String room = patient_procedures[patient][step[patient]];
                    int neededRoom = getRoomId(room);
                    queueArrivalTime[patient] = time;
                    roomQueues[neededRoom].offer(patient);
                    nextFreeTime[patient] = Integer.MAX_VALUE;
                    logMessage("[Time " + time + "] → Bệnh nhân " + patient + " xếp hàng đợi " + room);
                }
            }
        } 

        // 2. Điều phối phòng khám
        for (int r = 0; r < 5; r++) {
            if (roomFreeTime[r] <= time && !roomQueues[r].isEmpty()) {
                int patientToServe = roomQueues[r].poll(); 
                
                roomFreeTime[r] = time + fix_duration[r];        
                nextFreeTime[patientToServe] = time + fix_duration[r];
                prevRoom[patientToServe] = r;                    
                step[patientToServe]++;                          
                
                // Trực quan: Gán bệnh nhân vào phòng
                currentServing[r] = patientToServe;
                logMessage("[Time " + time + "] ✓ " + room_names[r] + " gọi Bệnh nhân " + patientToServe);
            }
        }

        simPanel.repaint();
        time++;
    }

    // =========================================================
    // 4. VẼ ĐỒ HỌA TRỰC QUAN (VISUALIZATION)
    // =========================================================
    class SimPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Vẽ đồng hồ tổng
            g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Thời gian (Time): " + time, 20, 30);
            g2d.drawString("Hoàn thành: " + doneCount + "/" + n, 250, 30);

            if (roomQueues == null) return; // Chưa bắt đầu

            int startX = 30;
            int boxWidth = 120;
            int gap = 20;

            for (int r = 0; r < 5; r++) {
                int x = startX + r * (boxWidth + gap);
                int y = 80;

                // 1. Vẽ Tên Phòng & Khung Phòng
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRoundRect(x, y, boxWidth, 100, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                g2d.drawString(room_names[r], x + 10, y + 25);
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g2d.drawString("Time: " + fix_duration[r] + "s", x + 10, y + 45);

                // 2. Vẽ Bệnh Nhân Đang Khám
                if (currentServing[r] != null) {
                    int pId = currentServing[r];
                    g2d.setColor(patientColors[pId]);
                    g2d.fillRoundRect(x + 10, y + 55, boxWidth - 20, 35, 5, 5);
                    
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                    g2d.drawString("P-" + pId, x + 40, y + 78);
                    
                    // Vẽ thanh tiến trình (Progress bar) cho phòng khám
                    int timeLeft = roomFreeTime[r] - time;
                    if (timeLeft > 0) {
                        g2d.setColor(Color.YELLOW);
                        g2d.fillRect(x + 10, y + 95, (int)((boxWidth - 20) * ((double)timeLeft / fix_duration[r])), 4);
                    }
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawString("[TRỐNG]", x + 30, y + 78);
                }

                // 3. Vẽ Hàng Đợi Ưu Tiên (Priority Queue) bên dưới phòng
                drawQueue(g2d, r, x, y + 120, boxWidth);
            }
        }

        private void drawQueue(Graphics2D g2d, int roomIndex, int x, int startY, int width) {
            PriorityQueue<Integer> pq = roomQueues[roomIndex];
            if (pq.isEmpty()) return;

            // Clone PQ để lấy đúng thứ tự ưu tiên hiển thị mà không phá hủy queue thật
            PriorityQueue<Integer> copyPq = new PriorityQueue<>(pq);
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2d.drawString("Hàng đợi (" + pq.size() + "):", x, startY);

            int currentY = startY + 15;
            while (!copyPq.isEmpty()) {
                int pId = copyPq.poll();
                
                g2d.setColor(patientColors[pId]);
                g2d.fillRoundRect(x, currentY, width, 25, 5, 5);
                
                g2d.setColor(Color.WHITE);
                g2d.drawString("Patient " + pId, x + 30, currentY + 18);
                
                currentY += 30;
                if (currentY > startY + 150 && !copyPq.isEmpty()) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("... và " + copyPq.size() + " ng nữa", x, currentY + 15);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        String[][] patients_4 = {
            {"PrimaryCare_1", "X-ray", "PrimaryCare_1", "Pharmacy", "", ""},
            {"PrimaryCare_2", "Pharmacy", "", "", "", ""},
            {"PrimaryCare_2", "Lab test", "PrimaryCare_2", "Pharmacy", "", ""},
            {"PrimaryCare_2", "Lab test", "X-ray", "PrimaryCare_2", "Pharmacy", ""},
            {"PrimaryCare_1", "Pharmacy", "", "", "", ""},
            {"PrimaryCare_1", "Lab test", "PrimaryCare_1", "Pharmacy", "", ""}
        };

        SwingUtilities.invokeLater(() -> {
            new QueueNetworkSimulation(patients_4).setVisible(true);
        });
    }
}