/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

public class MiniMax {

    public GameTreeNode miniMax(GameTreeNode currentNode, int alpha, int beta, boolean isMaxPlayer) {
        int value;
        GameTreeNode bestMove, next;
        bestMove = currentNode;

        if (currentNode.isTerminal()) {
            return currentNode;
        } else if (isMaxPlayer) {
            // infinity
            value = beta;
            for (GameTreeNode aNode : currentNode.getChildren()) {

                next = miniMax(aNode, alpha, value, false);

                if (next.getHeuristic() > value) {
                    bestMove = aNode;
                    value = aNode.getHeuristic();
                }
            }
        } else {
            value = alpha;
            for (GameTreeNode aNode : currentNode.getChildren()) {
                next = miniMax(aNode, value, beta, true);

                if (next.getHeuristic() < value) {
                    bestMove = aNode;
                    value = aNode.getHeuristic();
                }
            }
        }
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
                System.out.println(currentNode.getName());
                System.out.println(aNode.getName());
                value = Math.max(value, minimax(aNode, value, beta, false));
                alpha = Math.max(value, alpha);
                if (beta <= value) {
                    break;
                }
            }
        } else {
            value = beta;
            for (GameTreeNode aNode : currentNode.getChildren()) {
                value = Math.min(value, minimax(aNode, alpha, value, true));
                beta = Math.min(value, beta);
                if (value <= alpha) {
                    break;
                }
            }
        }
        return value;
    }

}
