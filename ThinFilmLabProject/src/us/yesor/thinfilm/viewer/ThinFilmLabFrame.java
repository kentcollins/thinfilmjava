package us.yesor.thinfilm.viewer;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ThinFilmLabFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton calculate;
	JButton setAngle;
	Container content;

	public ThinFilmLabFrame() {
		content = getContentPane();
		content.setLayout(null);
		calculate = new JButton("Calculate Reflectance");
		calculate.addActionListener(this);
		calculate.setBounds(10, 10, 200, 50);
		setAngle = new JButton("Set Incident Angle");
		setAngle.addActionListener(this);
		setAngle.setBounds(10, 70, 200, 50);
		content.add(calculate);
		content.add(setAngle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(calculate)) {
			JOptionPane.showMessageDialog(this,
					"Calculated Reflectance:\nNothing Yet");
		}
		else {
			String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Enter the incident angle, in radians",
                    "Customized Dialog",
                    JOptionPane.QUESTION_MESSAGE);
		}
	}

}
