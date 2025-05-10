# 🎨 Paint Bucket Tool - Java Swing

A simple MS Paint-style bucket fill tool built in Java using Swing and AWT. This project allows users to click on a 3x3 grid to fill cells with a selected color. The color palette displays 3 random colors at a time (out of a pool of 8), and automatically cycles to the next color when the entire grid is filled with the same color.

---

## 🚀 Features

- 3x3 interactive grid
- Click-to-fill functionality
- Rotating color palette (3 visible from 8 total colors)
- Automatic color switch when grid is uniformly filled
- Stylized title using bold italic Arial
- Built using Java Swing and AWT

---

## 🧠 How It Works

- The application uses Java’s `JPanel` and `Graphics` for drawing the grid.
- A recursive flood fill algorithm is used for color application logic.
- The palette is dynamically shuffled and rendered using `JPanel` components.
- When the entire grid becomes one color, the tool automatically switches to the next color in the predefined list.

---

## 🛠 Technologies Used

- Java 8+
- Java Swing for GUI components
- Java AWT for graphics and layout

---

## 📁 Project Structure

