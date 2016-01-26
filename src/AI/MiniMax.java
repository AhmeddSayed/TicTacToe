/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.util.ArrayList;

public class MiniMax {

    ArrayList<String> cutOffs = new ArrayList();

    public int miniMax(GameTreeNode currentNode, int alpha, int beta, boolean isMaxPlayer) {
        int value;

        if (currentNode.isTerminal()) {
            value = currentNode.getHeuristic();
        } else if (isMaxPlayer) {
            // infinity
            value = alpha;
            for (GameTreeNode aNode : currentNode.getChildren()) { 
                System.out.println(currentNode.getName());
                System.out.println(aNode.getName());
                value = Math.max(value, miniMax(aNode, value, beta, false));
                alpha = Math.max(value, alpha);
                if (beta <= value) {
                    cutOffs.add("Cut off at " + currentNode.getName() + " -> " + aNode.getName());
                    System.out.println("Cut off at " + currentNode.getName() + " -> " + aNode.getName());
                    break;
                }
            }
        } else {
            value = beta;
            for (GameTreeNode aNode : currentNode.getChildren()) {
                value = Math.min(value, miniMax(aNode, alpha, value, true));
                beta = Math.min(value, beta);
                if (value <= alpha) {
                    cutOffs.add("Cut off at " + currentNode.getName() + " -> " + aNode.getName());
                    System.out.println("Cut off at " + currentNode.getName() + " -> " + aNode.getName());

                    break;
                }
            }
        }
        return value;
    }

    public ArrayList<String> getCutOffs() {
        return this.cutOffs;
    }
}
