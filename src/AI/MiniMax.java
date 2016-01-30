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
                    bestMove = next;
                    value = bestMove.getHeuristic();
                }
            }
        } else {
            value = alpha;
            for (GameTreeNode aNode : currentNode.getChildren()) {
                next = miniMax(aNode, value, beta, true);

                if (next.getHeuristic() < value) {
                    bestMove = next;
                    value = next.getHeuristic();
                }
            }
        }
        return bestMove;
    }
}
