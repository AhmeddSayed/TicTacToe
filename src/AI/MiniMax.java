/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

public class MiniMax {

    public String miniMax(GameTreeNode currentNode) {
        int value = -100;
        String bestMove = "";

        for (GameTreeNode aNode : currentNode.getChildren()) {

            aNode.setHeuristic(minimax(aNode, Integer.MIN_VALUE, Integer.MAX_VALUE, true));
            if (aNode.getHeuristic() != -100) {
                if (aNode.getHeuristic() >= value) {
                    bestMove = aNode.getMove();
                    value = aNode.getHeuristic();
                }
            }
        }
        System.out.println("Best Move is: " + bestMove);
        System.out.println("Best value is: " + value);
        System.out.println("==================================");

        return bestMove;
    }

    public int minimax(GameTreeNode currentNode, int alpha, int beta, boolean isMaxPlayer) {
        int value;

        if (currentNode.isTerminal()) {
            value = currentNode.getHeuristic();
        } else if (isMaxPlayer) {
            // infinity
            value = alpha;
            for (GameTreeNode aNode : currentNode.getChildren()) {
                value = Math.max(value, minimax(aNode, value, beta, false));
                alpha = Math.max(value, alpha);
                if (beta < value) {
                    break;
                }
            }
        } else {
            value = beta;
            for (GameTreeNode aNode : currentNode.getChildren()) {
                value = Math.min(value, minimax(aNode, alpha, value, true));
                beta = Math.min(value, beta);
                if (value < alpha) {
                    break;
                }
            }
        }
        return value;
    }

}
