package us.yesor.thinfilm.viewer;

import javax.swing.JFrame;

public class ThinFilmLabViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new ThinFilmLabFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
	}

}
