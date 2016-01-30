/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adversary_search;

import java.awt.Dimension;
import java.awt.Toolkit;
import tic_tac_toe.Frame;

/**
 *
 * @author Ahmed
 */
public class Adversary_Search {

    public static void main(String args[]) {

        Frame theFrame = new Frame();
        centreWindow(theFrame);
        theFrame.setVisible(true);

    }

    public static void centreWindow(Frame theGame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - theGame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - theGame.getHeight()) / 2);
        theGame.setLocation(x, y);
    }
}
