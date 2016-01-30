/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe;

import adversary_search.folder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ahmed
 */
class GamePanel extends JPanel {

    Image X, O;
    private int[][] rows = new int[3][3];
    int turn;
    Graphics2D g2d;
    folder theFolder = new folder();
    ArrayList<Rectangle> moves = new ArrayList();
    Heuristics h;
    boolean gameOver;
    ArrayList<Point> XMoves = new ArrayList();
    ArrayList<Point> OMoves = new ArrayList();
    JButton[] theButtons = new JButton[2];

    public GamePanel() {
        try {
            X = ImageIO.read(theFolder.getFile("x.jpg")).getScaledInstance(150, 150, 0);
            O = ImageIO.read(theFolder.getFile("o.jpg")).getScaledInstance(150, 150, 0);
        } catch (IOException ex) {
        }

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                playerMove(e.getPoint());

            }
        });
        this.h = new Heuristics();
        this.moves.clear();
        this.gameOver = false;
        this.setVisible(true);
        this.turn = -1;
        this.setBackground(Color.white);
    }

    public void drawGrid(Graphics g) {

        g.drawLine(0, 0, 100, 100);
        g.setColor(Color.red);
        this.paintComponents(g);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(10));
        super.paintComponent(g);

        g2d.drawLine(233, 0, 233, 500);
        g2d.drawLine(466, 0, 466, 500);
        g2d.drawLine(0, 166, 700, 166);
        g2d.drawLine(0, 333, 700, 333);

        int demoTurn = 1;

        for (Rectangle move : this.moves) {
            //System.out.print("here");
            if (demoTurn == 1) {
                g2d.drawImage(X, move.x, move.y, null);
                demoTurn = -1;
            } else {
                g2d.drawImage(O, move.x, move.y, null);
                demoTurn = 1;
            }

        }

    }

    public void drawImage(String coordinates) {
        int y = Integer.valueOf(coordinates.split(":")[0]);
        int x = Integer.valueOf(coordinates.split(":")[1]);

        if (x == 0) {
            x = 200;

        } else if (x == 1) {
            x = 400;

        } else {
            x = 500;
        }

        if (y == 0) {
            y = 150;
        } else if (y == 1) {
            y = 300;
        } else {
            y = 400;
        }

        drawImage(new Point(x, y));

    }

    public void drawImage(Point mousePoint) {
        boolean illegal = false;
        Point thePoint = map(mousePoint);

        for (Rectangle move : this.moves) {
            if (move.contains(thePoint)) {
                //point is inside given image
                illegal = true;
                break;
            }

        }
        if (illegal == false) {
            this.moves.add(new Rectangle(thePoint.x, thePoint.y, X.getWidth(null), X.getWidth(this)));

            int x = thePoint.x;
            int y = thePoint.y;

            if (x < 233) {
                if (y < 166) {
                    rows[0][0] = this.turn;
                } else if (y < 333) {
                    rows[1][0] = this.turn;
                } else {
                    rows[2][0] = this.turn;
                }
            } else if (x < 466) {
                if (y < 166) {
                    rows[0][1] = this.turn;
                } else if (y < 333) {
                    rows[1][1] = this.turn;
                } else {
                    rows[2][1] = this.turn;
                }
            } else {
                if (y < 166) {
                    rows[0][2] = this.turn;
                } else if (y < 333) {
                    rows[1][2] = this.turn;
                } else {
                    rows[2][2] = this.turn;
                }
            }

            if (this.turn == 1) {
                this.turn = -1;
            } else {
                this.turn = 1;
            }
        }
    }

    private Point map(Point mousePoint) {

        int x = mousePoint.x;
        int y = mousePoint.y;
        if (x < 233) {
            if (y < 166) {
                return new Point(40, 5);
            } else if (y < 333) {
                return new Point(40, 171);
            } else {
                return new Point(40, 338);
            }
        } else if (x < 466) {
            if (y < 166) {
                return new Point(273, 5);
            } else if (y < 333) {
                return new Point(273, 171);
            } else {
                return new Point(273, 338);
            }
        } else {
            if (y < 166) {
                return new Point(506, 5);
            } else if (y < 333) {
                return new Point(506, 171);
            } else {
                return new Point(506, 338);
            }
        }
    }

    private void playerMove(Point mousePoint) {
        if (!this.gameOver) {
            drawImage(mousePoint);
            repaint();

            if (this.evaluateGame() == -1 || this.evaluateGame() == 1 || this.evaluateGame() == 0) {
                this.gameOver = true;
                displayGameOver(evaluateGame());
            }
            if (this.turn == 1 && !this.gameOver) {
                h.calculate(rows);
                if (h.getMove().contains(":")) {
                    drawImage(h.getMove());
                } else {
                    this.gameOver = true;
                    displayGameOver(evaluateGame());
                }
            }
            if (this.evaluateGame() == -1 || this.evaluateGame() == 1 || this.evaluateGame() == 0) {
                this.gameOver = true;
                displayGameOver(evaluateGame());
            }

        } else {
            displayGameOver(evaluateGame());
        }
    }

    private int evaluateGame() {
        if (this.moves.size() < 5 || this.moves.isEmpty()) {
            // we didn't reach the minimum number of moves for a game over
            return -2;
        }

        boolean rowMatch = false;

        // looping through the rows
        for (int i = 0; i < 3; i++) {
            rowMatch = false;

            for (int j = 0; j < 3; j++) {

                //System.out.print("  " + rows[i][j]);
                if (rows[i][j] == 0) {
                    // empty cell
                    continue;
                }

                if (j != 0) {
                    if (rows[i][j] == rows[i][j - 1]) {
                        if (rowMatch) {
                            //System.out.println("Player " + rows[i][j]);
                            return rows[i][j];
                        } else {
                            rowMatch = true;
                        }
                    } else {
                        rowMatch = false;
                    }
                }

                if (i == 1) {
                    // middle row
                    if (rows[i][j] == rows[i - 1][j]) {
                        if (rows[i][j] == rows[i + 1][j]) {
                            //System.out.println("Player " + rows[i][j]);
                            return rows[i][j];
                        }
                    }

                    if (j == 1) {
                        // middle cell
                        // checking diagonal axes
                        if (rows[i][j] == rows[i - 1][j - 1] && rows[i][j] == rows[i + 1][j + 1]) {
                            //System.out.println("Player " + rows[i][j]);
                            return rows[i][j];
                        } else if (rows[i][j] == rows[i - 1][j + 1] && rows[i][j] == rows[i + 1][j - 1]) {
                            // System.out.println("Player " + rows[i][j]);
                            return rows[i][j];
                        }
                    }
                }
            }
            //System.out.println();
        }

        if (this.moves.size() == 9) {
            return 0;
        }

        return -2;
    }

    private void displayGameOver(int gameEndStatus) {
        String message;
        theButtons[0] = new JButton();

        theButtons[0].setName("newGame");
        theButtons[0].setText("Play Again");
        theButtons[1].setName("goBack");
        theButtons[1].setText("Exit");

        theButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restart();
            }

        });

        if (gameEndStatus == 0) {
            // draw
            message = "It's a draw!";
        } else if (gameEndStatus == 1) {
            message = "Computer Won!";
        } else {
            message = "You Win!";
        }
        JOptionPane.showMessageDialog(this, message, " GAME OVER!", 1);
        JOptionPane.showOptionDialog(this, "Would you like to Play again?", " Rematch?", 0, 3, null, theButtons, theButtons[0]);

    }

    public void addGoBackButtonActionListener(ActionListener listener) {
        theButtons[1] = new JButton();
        theButtons[1].addActionListener(listener);

    }

    void closeDialog() {
        Window w = SwingUtilities.getWindowAncestor(theButtons[1]);

        if (w != null) {
            w.dispose();
        }
    }

    private void restart() {
        this.rows = new int[3][3];
        this.moves.clear();
        this.gameOver = false;
        this.setVisible(true);
        this.turn = -1;
        this.setBackground(Color.white);
        closeDialog();
        repaint();
    }

}
