import java.awt.*;
import java.util.TreeSet;
import javax.swing.*;

public class ElevatorSimulation extends JFrame {
    private static final int NUM_FLOORS = 10;
    private static final int FLOOR_HEIGHT = 50;
    
    // DSA: TreeSets to maintain sorted unique requests
    private TreeSet<Integer> upRequests = new TreeSet<>();
    private TreeSet<Integer> downRequests = new TreeSet<>();
    
    private int currentY = (NUM_FLOORS - 1) * FLOOR_HEIGHT; // Start at floor 0 (bottom)
    private int targetFloor = 0;
    private int currentFloor = 0;
    
    private enum Direction { UP, DOWN, IDLE }
    private Direction currentDirection = Direction.IDLE;
    private boolean doorsOpen = false;
    private int doorTimer = 0;

    private BuildingPanel buildingPanel;

    public ElevatorSimulation() {
        setTitle("Elevator DSA Visualization (LOOK Algorithm)");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buildingPanel = new BuildingPanel();
        add(buildingPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2));
        
        // Add buttons for floors 0 to 9
        for (int i = NUM_FLOORS - 1; i >= 0; i--) {
            int floorNumber = i;
            JButton btn = new JButton("Floor " + floorNumber);
            btn.addActionListener(e -> addRequest(floorNumber));
            controlPanel.add(btn);
        }
        
        add(controlPanel, BorderLayout.EAST);

        // Animation Timer (Runs every 16ms for ~60fps)
        Timer timer = new Timer(16, e -> updateElevator());
        timer.start();
    }

    // DSA Method: Adding a request
    private void addRequest(int floor) {
        if (floor == currentFloor && currentDirection == Direction.IDLE) {
            return; // Already here
        }
        
        // Determine which queue to put the request in based on current position
        if (floor > currentFloor) {
            upRequests.add(floor);
        } else if (floor < currentFloor) {
            downRequests.add(floor);
        } else {
            // If requested floor is current floor but elevator is moving
            if (currentDirection == Direction.UP) upRequests.add(floor);
            else downRequests.add(floor);
        }

        // Kickstart the elevator if it was sleeping
        if (currentDirection == Direction.IDLE) {
            if (!upRequests.isEmpty()) currentDirection = Direction.UP;
            else if (!downRequests.isEmpty()) currentDirection = Direction.DOWN;
        }
    }

    // DSA Method: The LOOK Algorithm step
    private void updateElevator() {
        if (doorsOpen) {
            doorTimer++;
            if (doorTimer > 60) { // Keep doors open for ~1 second
                doorsOpen = false;
                doorTimer = 0;
            }
            buildingPanel.repaint();
            return;
        }

        if (currentDirection == Direction.IDLE) return;

        // Calculate exact pixel position of the current target floor
        currentFloor = (NUM_FLOORS - 1) - (currentY / FLOOR_HEIGHT);
        
        // Check if we arrived at a requested floor
        checkArrivals();

        // Move the elevator smoothly
        moveElevator();
        
        buildingPanel.repaint();
    }
    
    private void checkArrivals() {
        boolean arrived = false;
        
        // Precise alignment check to ensure it stops exactly on the floor line
        if (currentY % FLOOR_HEIGHT == 0) {
            if (currentDirection == Direction.UP && upRequests.contains(currentFloor)) {
                upRequests.remove(currentFloor);
                arrived = true;
            } else if (currentDirection == Direction.DOWN && downRequests.contains(currentFloor)) {
                downRequests.remove(currentFloor);
                arrived = true;
            }
        }

        if (arrived) {
            doorsOpen = true;
            determineNextDirection();
        }
    }
    
    private void determineNextDirection() {
        if (currentDirection == Direction.UP) {
            // Are there any requests higher than current floor?
            Integer nextUp = upRequests.higher(currentFloor);
            if (nextUp == null) {
                // No more requests up, check if we need to go down
                if (!downRequests.isEmpty()) currentDirection = Direction.DOWN;
                else currentDirection = Direction.IDLE;
            }
        } else if (currentDirection == Direction.DOWN) {
            // Are there any requests lower than current floor?
            Integer nextDown = downRequests.lower(currentFloor);
            if (nextDown == null) {
                // No more requests down, check if we need to go up
                if (!upRequests.isEmpty()) currentDirection = Direction.UP;
                else currentDirection = Direction.IDLE;
            }
        }
    }

    private void moveElevator() {
        if (currentDirection == Direction.UP) {
            currentY -= 2; // Move UP (pixels decrease)
            if (currentY <= 0) currentY = 0; // Boundary check
        } else if (currentDirection == Direction.DOWN) {
            currentY += 2; // Move DOWN (pixels increase)
            int maxY = (NUM_FLOORS - 1) * FLOOR_HEIGHT;
            if (currentY >= maxY) currentY = maxY; // Boundary check
        }
    }

    // Custom JPanel to draw the building and elevator
    class BuildingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Draw floors
            g2d.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < NUM_FLOORS; i++) {
                int y = i * FLOOR_HEIGHT;
                g2d.drawLine(50, y + FLOOR_HEIGHT, 200, y + FLOOR_HEIGHT);
                g2d.drawString("Floor " + (NUM_FLOORS - 1 - i), 10, y + FLOOR_HEIGHT - 5);
            }

            // Draw elevator shaft
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawRect(100, 0, 60, NUM_FLOORS * FLOOR_HEIGHT);

            // Draw Elevator Car
            if (doorsOpen) {
                g2d.setColor(Color.GREEN); // Green signifies doors open
            } else {
                g2d.setColor(Color.BLUE);  // Blue signifies moving/closed
            }
            g2d.fillRect(101, currentY, 58, FLOOR_HEIGHT);
            
            // Draw Elevator UI details
            g2d.setColor(Color.WHITE);
            g2d.drawString(currentDirection.toString(), 110, currentY + 25);
            
            // Draw queues for DSA visualization
            g2d.setColor(Color.BLACK);
            g2d.drawString("DSA State:", 10, NUM_FLOORS * FLOOR_HEIGHT + 30);
            g2d.drawString("Up Queue (TreeSet): " + upRequests.toString(), 10, NUM_FLOORS * FLOOR_HEIGHT + 50);
            g2d.drawString("Down Queue (TreeSet): " + downRequests.toString(), 10, NUM_FLOORS * FLOOR_HEIGHT + 70);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ElevatorSimulation sim = new ElevatorSimulation();
            sim.setVisible(true);
        });
    }
}