package swordAndShield_GUI;

import javax.swing.SwingUtilities;

import model.Board;
import resources.ImgResources;

public class Main {
	public static void main(String[] s) {
	    ImgResources.values();// ImgResource class loading
	    SwingUtilities.invokeLater(()->new View(new Board()));
	  }
}
