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
    int nodeNumber = 0;

    public Heuristics() {
        theFolder = new folder();
        m = new MiniMax();
    }

    public void calculate(int[][] rows) {
        
         System.out.println(rows[0][0] + " " + rows[0][1] + " " + rows[0][2]);
         System.out.println(rows[1][0] + " " + rows[1][1] + " " + rows[1][2]);
         System.out.println(rows[2][0] + " " + rows[2][1] + " " + rows[2][2]);
         System.out.println("-------------------------");
         
        headState = new GameTreeNode("0");
        headState.setRows(rows);
        generateChildren(headState, 1);

        m.miniMax(headState, Integer.MAX_VALUE, Integer.MIN_VALUE, true);
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

    public void generateChildren(GameTreeNode head, int turn) {
        int[][] theRows = head.getRows();
        ArrayList<String> allMoves = checkAllPossibleMovements(theRows);
        int[][] childRows;
        if (allMoves.isEmpty()) {
            // game over
            //head.setHeuristic(gameOver(theRows) * 1000);
            head.setHeuristic(calculateHeuristic(head.getRows()));

            //System.out.println("Here and " + head.getMove());
        } else {
            childRows = theRows;

            for (String move : allMoves) {
                GameTreeNode aNode = new GameTreeNode(String.valueOf(nodeNumber++));
                aNode.setMove(move);

                int i = Integer.valueOf(move.split( ":")[0]);
                int j = Integer.valueOf(move.split(":")[1]);
                childRows[i][j] = turn;
                aNode.setRows(childRows);

                // game still in play
                head.setHeuristic(calculateHeuristic(head.getRows()));
                generateChildren(aNode, -1 * turn);
                childRows[i][j] = theRows[i][j];
                head.addChild(aNode);
                //System.out.println("Score is: " + gameScore);

            }
        }
    }

    private int gameOver(int[][] rows) {
        boolean rowMatch;
        int emptyCells = 0;
        // looping through the rows
        for (int i = 0; i < 3; i++) {
            rowMatch = false;

            for (int j = 0; j < 3; j++) {

                if (rows[i][j] == 0) {
                    // empty cell
                    emptyCells++;
                    continue;
                }

                if (j != 0) {
                    if (rows[i][j] == rows[i][j - 1]) {
                        if (rowMatch) {

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
                            return rows[i][j];
                        }
                    }

                    if (j == 1) {
                        // middle cell
                        // checking diagonal axes
                        if (rows[i][j] == rows[i - 1][j - 1] && rows[i][j] == rows[i + 1][j + 1]) {

                            return rows[i][j];
                        } else if (rows[i][j] == rows[i - 1][j + 1] && rows[i][j] == rows[i + 1][j - 1]) {

                            return rows[i][j];
                        }
                    }
                }
            }
        }

        if (emptyCells == 0) {
            // tie

            return 0;
        } else {
            // game still in play

            return 5;
        }
    }

    public String getMove() {
        GameTreeNode maxValue = null;
        String theMove = ":";

        for (GameTreeNode aNode : this.headState.getChildren()) {
            if (maxValue == null) {
                maxValue = aNode;
                theMove = aNode.getMove();
            } else if (aNode.getHeuristic() > maxValue.getHeuristic()) {
                maxValue = aNode;
                theMove = aNode.getMove();
                System.out.println(maxValue.getMove());
            }
        }

        return theMove;
    }

    private int calculateHeuristic(int[][] rows) {
        System.out.println(rows[0][0] + " " + rows[0][1] + " " + rows[0][2]);
        System.out.println(rows[1][0] + " " + rows[1][1] + " " + rows[1][2]);
        System.out.println(rows[2][0] + " " + rows[2][1] + " " + rows[2][2]);
        System.out.println("-------------------------");
        int score = 0;
        int rowCountX, rowCountO, rowCount0;
        int columnCountX, columnCountO, columnCount0;

        for (int i = 0; i < 3; i++) {
            rowCountX = 0;
            rowCountO = 0;
            rowCount0 = 0;

            for (int j = 0; j < 3; j++) {
                if (rows[i][j] == 1) {
                    rowCountX++;
                } else if (rows[i][j] == -1) {
                    rowCountO++;
                } else {
                    rowCount0++;
                }
            }

            if (rowCountX != 0 && rowCountO != 0) {
                score += 0;
            }

            if (rowCount0 == 3) {
                score += 10;
            }

            if (rowCountX == 0 && rowCountO != 0) {
                if (rowCountO == 3) {
                    score += 1000;
                }
                if (rowCountO == 2) {
                    score += 500;
                }
                if (rowCountO == 1) {
                    score += 100;
                }
            }
            if (rowCountO == 0 && rowCountX != 0) {
                if (rowCountX == 3) {
                    score += 1000;
                }
                if (rowCountX == 2) {
                    score += 500;
                }
                if (rowCountX == 1) {
                    score += 100;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            columnCountX = 0;
            columnCountO = 0;
            columnCount0 = 0;

            for (int j = 0; j < 3; j++) {
                if (rows[j][i] == 1) {
                    columnCountX++;
                } else if (rows[j][i] == -1) {
                    columnCountO++;
                } else {
                    columnCount0++;
                }
            }

            if (columnCountX != 0 && columnCountO != 0) {
                score += 0;
            }

            if (columnCount0 == 3) {
                score += 10;
            }

            if (columnCountX == 0 && columnCountO != 0) {
                if (columnCountO == 3) {
                    score += 1000;
                }
                if (columnCountO == 2) {
                    score += 500;
                }
                if (columnCountO == 1) {
                    score += 100;
                }
            }
            if (columnCountO == 0 && columnCountX != 0) {
                if (columnCountX == 3) {
                    score += 1000;
                }
                if (columnCountX == 2) {
                    score += 500;
                }
                if (columnCountX == 1) {
                    score += 100;
                }
            }
        }

        if (rows[0][0] == rows[1][1] && rows[1][1] == rows[2][2]) {
            if (rows[0][0] != 0) {
                score += 1000 * rows[0][0];
            }
        }

        if (rows[0][0] == rows[1][1]) {
            if (rows[2][2] == rows[0][0] * -1 && rows[0][0] != 0) {

            } else {
                score += rows[0][0] * 500;
            }
        }

        if (rows[0][0] == rows[2][2]) {
            if (rows[1][1] == rows[0][0] * -1 && rows[0][0] != 0) {

            } else {
                score += rows[0][0] * 500;
            }
        }
        if (rows[1][1] == rows[2][2]) {
            if (rows[0][0] == rows[1][1] * -1 && rows[1][1] != 0) {

            } else {
                score += rows[1][1] * 500;
            }
        }

        /*
         =========================================================
         */
        if (rows[0][2] == rows[1][1] && rows[1][1] == rows[2][0]) {
            if (rows[0][2] != 0) {
                score += 1000 * rows[0][2];
            }
        }

        if (rows[0][2] == rows[1][1]) {
            if (rows[0][2] == rows[2][0] * -1 && rows[0][2] != 0) {

            } else {
                score += rows[0][2] * 500;
            }
        }

        if (rows[1][1] == rows[2][0]) {
            if (rows[1][1] == rows[0][2] * -1 && rows[1][1] != 0) {

            } else {
                score += rows[2][0] * 500;
            }
        }
        if (rows[0][2] == rows[2][0]) {
            if (rows[0][2] == rows[1][1] * -1 && rows[1][1] != 0) {

            } else {
                score += rows[0][2] * 500;
            }
        }

        System.out.println("-->" + score);

        return score;
    }

}
