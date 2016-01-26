/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

/**
 *
 * @author Ahmed
 */
import java.util.ArrayList;
import java.util.Arrays;

public class GameTreeNode {

    public int value, depth;
    String Name, move;

    private final ArrayList<GameTreeNode> children;
    private String parentName;
    protected int[][] rows;

    public GameTreeNode(String Name) {
        children = new ArrayList<>();
        this.Name = Name;
        this.move = "";
    }

    public void addChild(GameTreeNode child) {
        this.children.add(child);
    }

    public ArrayList<GameTreeNode> getChildren() {
        return this.children;
    }

    public String getName() {
        return this.Name;
    }

    public boolean isTerminal() {
        if (this.children.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public int getHeuristic() {
        return this.value;
    }

    public void setHeuristic(int H) {
        this.value = H;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String name) {
        this.parentName = name;
    }

    public void setMove(String theMove) {
        this.move = theMove;
    }

    public String getMove() {
        return this.move;
    }

    public void setRows(int[][] newRows) {
        this.rows = newRows;
    }

    public int[][] getRows() {
        return copyOf(this.rows);
    }

    private static int[][] copyOf(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }
}
