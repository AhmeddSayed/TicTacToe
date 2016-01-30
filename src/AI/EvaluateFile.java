/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class EvaluateFile {

    ArrayList<String> Message = new ArrayList();

    public void evaluate(File theFile) {
        GameTreeNode rootNode = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(theFile));

            try {
                for (String line; (line = br.readLine()) != null;) {
                    if (line.contains("->")) {
                        // normal node
                        GameTreeNode newNode;
                        String nodeName = line.split("->")[1].split("\\(")[0];
                        String parent = line.split("->")[0];
                        newNode = new GameTreeNode(nodeName);
                        newNode.setParentName(parent);

                        if (line.contains("(")) {
                            // leaf node
                            String heuristic = line.split("\\(")[1].split("\\)")[0];
                            newNode.setHeuristic(Integer.valueOf(heuristic));
                        }

                        addNodeToParent(rootNode, newNode);

                    } else {
                        // first node
                        rootNode = new GameTreeNode(line.trim());
                    }
                }
                MiniMax theMiniMax = new MiniMax();
                int Score = theMiniMax.miniMax(rootNode, Integer.MIN_VALUE, Integer.MAX_VALUE, true).value;
                //Message = theMiniMax.getCutOffs();
                Message.add("Score is " + Score);
            } catch (IOException ex) {
            }
        } catch (FileNotFoundException ex) {
        }
    }

    private static void addNodeToParent(GameTreeNode aNode, GameTreeNode newNode) {
        // looping through the nodes and attaching the child to his parent
        if (aNode.getName().equals(newNode.getParentName())) {
            aNode.addChild(newNode);
        } else {
            if (!aNode.isTerminal()) {
                for (GameTreeNode child : aNode.getChildren()) {
                    addNodeToParent(child, newNode);
                }
            }
        }
    }

    public ArrayList<String> getMessage() {
        return this.Message;
    }
}
