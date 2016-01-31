/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe;

import AI.GameTreeNode;
import AI.MiniMax;
import adversary_search.folder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Ahmed
 */
public class Heuristics {

    boolean GameOver;
    MiniMax m;
    folder theFolder;
    ArrayList<String> possibleMoves = new ArrayList();
    GameTreeNode headState;
    String bestMove;
    int nodeNumber = 0;

    public Heuristics() {
        theFolder = new folder();
        m = new MiniMax();
    }

    public void calculate(int[][] rows) {

        headState = new GameTreeNode("0");
        headState.setRows(rows);
        headState.setChildren(generateChildren(headState, 1));

        this.bestMove = m.miniMax(headState);
    }

    public ArrayList<String> checkAllPossibleMovements(int[][] rows) {
        ArrayList<String> moves = new ArrayList();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (rows[i][j] != -1 && rows[i][j] != 1) {
                    // empty sqare 
                    moves.add(String.valueOf(i) + ":" + String.valueOf(j));
                }
            }
        }
        return moves;
    }

    public ArrayList<GameTreeNode> generateChildren(GameTreeNode head, int turn) {

        int[][] theRows = copyOf(head.getRows());
        ArrayList<String> allMoves = checkAllPossibleMovements(theRows);
        int[][] childRows;
        ArrayList<GameTreeNode> children = new ArrayList();

        if (allMoves.isEmpty()) {
            // game over
            head.setHeuristic(calculateHeuristic(head.getRows()));
        } else {
            childRows = copyOf(theRows);

            for (String move : allMoves) {
                // an instance of the game state
                int[][] newChildRows = copyOf(childRows);

                // constructing a new game node
                GameTreeNode aNode = new GameTreeNode(String.valueOf(nodeNumber++));
                aNode.setMove(move);

                // getting the exact move
                int i = Integer.valueOf(move.split(":")[0]);
                int j = Integer.valueOf(move.split(":")[1]);

                // playing the move
                newChildRows[i][j] = turn;
                aNode.setRows(newChildRows);
                children.add(aNode);

                if (turn == 1) {
                    aNode.setChildren(generateChildren(aNode, -1));
                } else {
                    aNode.setChildren(generateChildren(aNode, 1));
                }

            }
        }
        return children;
    }

    public String getMove() {
        return this.bestMove;
    }

    private static int[][] copyOf(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }

    private int calculateHeuristic(int[][] rows) {

        /*
         Heuristics is calculated in terms of:
         - How many of the same element in on a horizontal line * playerCode
         - How many of the same element in on a Vertical line * playerCode
         - How many of the same element in on a diagonal line * playerCode
        
         - if one line has both X and O then heuristics of that line is 0
         
         Variables:
         ----------
         hXrowCount : counts the number of X's horizontally 
         hOrowCount : counts the number of O's horizontally
        
         vXrowCount : counts the number of X's vertically
         vOrowCount : counts the number of O's vertically
         
         d1XrowCount : counts the number of X's on the first diagonal 
         d1OrowCount : counts the number of O's on the first diagonal
         
         d2XrowCount : counts the number of X's on the second diagonal
         d2OrowCount : counts the number of O's on the second diagonal
        
         hScore    : the horizontal score
         vScore    : the vertical score
         dScore    : the diagonal score
         */
        int hXrowCount, hOrowCount;
        int vXrowCount, vOrowCount;
        int d1XrowCount = 0, d1OrowCount = 0;
        int d2XrowCount = 0, d2OrowCount = 0;
        int hScore = 0, vScore = 0, dScore = 0;
        boolean gameOver = false;

        /* Checking Horizontally:
        
         by the number of player tokens P :
         0 ->    1
         1 ->    P * count
         2 ->    P * count
         3 ->    P * count
         Unless there's a different token in the same line: both X and O -> 0
         */
        for (int i = 0; i < 3; i++) {
            hOrowCount = 0;
            hXrowCount = 0;
            vOrowCount = 0;
            vXrowCount = 0;

            for (int j = 0; j < 3; j++) {
                if (rows[i][j] != 0 && !gameOver) {
                    // Checking Horizontally
                    switch (rows[i][j]) {
                        case 1:
                            hOrowCount++;
                            break;
                        case -1:
                            hXrowCount++;
                            break;
                        default:
                            break;
                    }
                    // Checking Vertically
                    switch (rows[j][i]) {
                        case 1:
                            vOrowCount++;
                            break;
                        case -1:
                            vXrowCount++;
                            break;
                        default:
                            break;
                    }

                    // checking on the first diagonal
                    if (i == j) {
                        switch (rows[i][j]) {
                            case 1:
                                d1OrowCount++;
                                break;
                            case -1:
                                d1XrowCount++;
                                break;
                            default:
                                break;
                        }
                    }
                    // checking on the second diagonal
                    if (i + j == 2) {
                        switch (rows[i][j]) {
                            case 1:
                                d2OrowCount++;
                                break;
                            case -1:
                                d2XrowCount++;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            if (!gameOver) {
                if (hOrowCount == 3) {
                    gameOver = true;
                    hScore = 1;
                } else if (hXrowCount == 3) {
                    gameOver = true;
                    hScore = -1;
                }
            }
            if (!gameOver) {

                // Checking vertical data
                if (vOrowCount == 3) {
                    gameOver = true;
                    vScore = 1;
                } else if (vXrowCount == 3) {
                    gameOver = true;
                    vScore = -1;
                }
            }
            if (!gameOver) {

                if (d1OrowCount == 3) {
                    gameOver = true;
                    dScore = 1;
                } else if (d1XrowCount == 3) {
                    gameOver = true;
                    dScore = -1;
                }
            }
            if (!gameOver) {

                // Checking data for second diagonal
                if (d2OrowCount == 3) {
                    gameOver = true;
                    dScore = 1;
                } else if (d2XrowCount == 3) {
                    gameOver = true;
                    dScore = -1;
                }

            } else {
                break;
            }
        }

        return (hScore + vScore + dScore);
    }
}
