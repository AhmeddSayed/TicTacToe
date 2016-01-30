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
    GameTreeNode headState, bestMove;
    int nodeNumber = 0;

    public Heuristics() {
        theFolder = new folder();
        m = new MiniMax();
    }

    public void calculate(int[][] rows) {

        headState = new GameTreeNode("0");
        headState.setRows(rows);
        headState.setChildren(generateChildren(headState, 1));
        //
        for (GameTreeNode n : headState.getChildren()) {
            //System.out.println("Rows: \n" + Arrays.toString(n.getRows()));
            System.out.println("Move: " + n.getMove());
            System.out.println("H: " + n.getHeuristic());
            System.out.println("N: " + n.getChildren().size());
        }
        System.out.println("=====================");

        this.bestMove = m.miniMax(headState, Integer.MAX_VALUE, Integer.MIN_VALUE, true);
        //System.out.println("best value is" + bestMove.getHeuristic());
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

                aNode.setHeuristic(calculateHeuristic(aNode.getRows()));

            }
        }
        return children;
    }

    public String getMove() {
        return this.bestMove.getMove();
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
         hZrowCount : counts the number of zero's horizontally
        
         vXrowCount : counts the number of X's vertically
         vOrowCount : counts the number of O's vertically
         vZrowCount : counts the number of zero's vertically
         
         d1XrowCount : counts the number of X's on the first diagonal 
         d1OrowCount : counts the number of O's on the first diagonal
         d1ZrowCount : counts the number of zero's on the first diagonal
         
         d2XrowCount : counts the number of X's on the second diagonal
         d2OrowCount : counts the number of O's on the second diagonal
         d2ZrowCount : counts the number of zero's on the second diagonal
        
         hScore    : the horizontal score
         vScore    : the vertical score
         dScore    : the diagonal score
         */
        int hXrowCount = 0, hOrowCount = 0, hZrowCount = 0;
        int vXrowCount = 0, vOrowCount = 0, vZrowCount = 0;
        int d1XrowCount = 0, d1OrowCount = 0, d1ZrowCount = 0;
        int d2XrowCount = 0, d2OrowCount = 0, d2ZrowCount = 0;
        int hScore = 0, vScore = 0, dScore = 0;

        /* Checking Horizontally:
        
         by the number of player tokens P :
         0 ->    1
         1 ->    P * count
         2 ->    P * count
         3 ->    P * count
         Unless there's a different token in the same line: both X and O -> 0
         */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (rows[i][j] != 0) {
                    // Checking Horizontally
                    switch (rows[i][j]) {
                        case 0:
                            hZrowCount++;
                            break;
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
                        case 0:
                            vZrowCount++;
                            break;
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
                            case 0:
                                d1ZrowCount++;
                                break;
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
                            case 0:
                                d2ZrowCount++;
                                break;
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

            // Checking horizontal data
            if (hZrowCount == 3) {
                // an empty row
                hScore = 1;
            } else {
                // No O's in this row
                if (hOrowCount == 0) {
                    hScore = -1 * hXrowCount;
                } else {
                    // No X's in this row
                    if (hXrowCount == 0) {
                        hScore = hOrowCount;
                    }
                }
            }
        }

        // Checking vertical data
        if (vZrowCount == 3) {
            // an empty row
            vScore = 1;
        } else {
            // No O's in this row
            if (vOrowCount == 0) {
                vScore = -1 * vXrowCount;
            } else {
                // No X's in this row
                if (vXrowCount == 0) {
                    vScore = 1 * vOrowCount;
                }
            }
        }

        // Checking data for first diagonal
        if (d1ZrowCount == 3) {
            // an empty row
            vScore = 1;
        } else {
            // No O's in this row
            if (d1OrowCount == 0) {
                dScore = -1 * d1XrowCount;
            } else {
                // No X's in this row
                if (d1XrowCount == 0) {
                    dScore = 1 * d1OrowCount;
                }
            }
        }

        // Checking data for second diagonal
        if (d2ZrowCount == 3) {
            // an empty row
            dScore = 1;
        } else {
            // No O's in this row
            if (d2OrowCount == 0) {
                dScore = -1 * d2XrowCount;
            } else {
                // No X's in this row
                if (d2XrowCount == 0) {
                    dScore = 1 * d2OrowCount;
                }
            }
        }
        return (hScore + vScore + dScore);
    }
}
