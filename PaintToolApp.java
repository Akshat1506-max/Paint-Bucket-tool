import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PaintToolApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Paint Bucket Tool");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLayout(new BorderLayout());

            JLabel title = new JLabel("Paint bucket tool", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
            frame.add(title, BorderLayout.NORTH);

            PaintPanel paintPanel = new PaintPanel();
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridBagLayout());
            centerPanel.add(paintPanel);
            frame.add(centerPanel, BorderLayout.CENTER);

            JPanel colorChooser = new JPanel();
            colorChooser.setLayout(new FlowLayout());

            paintPanel.updateColorPalette(colorChooser);

            frame.add(colorChooser, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}

class PaintPanel extends JPanel {
    private final int rows = 3;
    private final int cols = 3;
    private final Color[][] gridColors = new Color[rows][cols];
    private Color currentColor = Color.RED;
    private final int cellSize = 100;
    private final ArrayList<Color> allColors = new ArrayList<>(Arrays.asList(
            Color.RED, Color.BLUE, Color.BLACK, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE
    ));
    private ArrayList<Color> paletteColors = new ArrayList<>();

    public PaintPanel() {
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        setBackground(Color.WHITE);

        Collections.shuffle(allColors);
        paletteColors.addAll(allColors.subList(0, 3));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;
                if (row < rows && col < cols) {
                    gridColors[row][col] = currentColor;
                    repaint();
                    checkAndCycleColor();
                }
            }
        });
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void updateColorPalette(JPanel colorChooser) {
        colorChooser.removeAll();
        for (Color color : paletteColors) {
            JPanel colorBox = new JPanel();
            colorBox.setBackground(color);
            colorBox.setPreferredSize(new Dimension(30, 30));
            colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            colorBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setCurrentColor(color);
                }
            });
            colorChooser.add(colorBox);
        }
        colorChooser.revalidate();
        colorChooser.repaint();
    }

    private void checkAndCycleColor() {
        Color firstColor = gridColors[0][0];
        for (Color[] row : gridColors) {
            for (Color color : row) {
                if (!color.equals(firstColor)) {
                    return;
                }
            }
        }
        // All cells have the same color
        int currentIndex = allColors.indexOf(firstColor);
        int newIndex = (currentIndex + 1) % allColors.size();
        Color newColor = allColors.get(newIndex);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                gridColors[row][col] = newColor;
            }
        }
        // Refresh palette to always show 3 different colors
        Collections.shuffle(allColors);
        paletteColors = new ArrayList<>(allColors.subList(0, 3));
        SwingUtilities.invokeLater(() -> updateColorPalette((JPanel) getParent().getParent().getComponent(2)));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (gridColors[row][col] == null) gridColors[row][col] = Color.WHITE;
                g.setColor(gridColors[row][col]);
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
}