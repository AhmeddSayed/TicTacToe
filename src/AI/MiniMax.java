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

    /*
     01 function minimax(node, depth, maximizingPlayer)
     02     if depth = 0 or node is a terminal node
     03         return the heuristic value of node

     04     if maximizingPlayer
     05         bestValue := −∞
     06         for each child of node
     07             v := minimax(child, depth − 1, FALSE)
     08             bestValue := max(bestValue, v)
     09         return bestValue

     10     else    (* minimizing player *)
     11         bestValue := +∞
     12         for each child of node
     13             v := minimax(child, depth − 1, TRUE)
     14             bestValue := min(bestValue, v)
     15         return bestValue
     */

}
