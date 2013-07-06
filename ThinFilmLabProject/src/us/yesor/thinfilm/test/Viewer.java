package us.yesor.thinfilm.test;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import us.yesor.thinfilm.MultiFilmStack;

public class Viewer implements Runnable {

	public static final double REFRACTIVE_INDEX_AIR = 1.0;
	public static final double REFRACTIVE_INDEX_SILICON = 3.42009;
	public static final double REFRACTIVE_INDEX_ITO = 2.35046;
	public static final double REFRACTIVE_INDEX_SILICONDIOXIDE = 1.54427;
	
    @Override
    public void run() {
        // Create the window
        JFrame f = new JFrame("Hello, World!");
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add a layout manager so that the button is not placed on top of the label
        JPanel framePane = new JPanel();
        framePane.setLayout(new BoxLayout(framePane, BoxLayout.PAGE_AXIS));
        f.add(framePane);
        JPanel sourcePanel = new JPanel();
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Source Characteristics");
        sourcePanel.setBorder(title);
        sourcePanel.add(new JPanel());
        sourcePanel.add(new JLabel("Source Wave"));
        sourcePanel.add(new JButton("Set Wavelength"));
        framePane.add(sourcePanel);
        JPanel layerPanel1 = new JPanel();
        layerPanel1.add(new JButton("Set Angle of Incidence"));
        layerPanel1.add(new JLabel("Layer"));
        layerPanel1.add(new JLabel("Index"));
        layerPanel1.add(new JLabel("Thickness"));
        layerPanel1.add(new JCheckBox());
        layerPanel1.add(new JTextField("Air"));
        framePane.add(layerPanel1);
        JPanel layerPanel2 = new JPanel();
        layerPanel2.add(new JButton("Set Angle of Incidence"));
        layerPanel2.add(new JLabel("Layer"));
        layerPanel2.add(new JLabel("Index"));
        layerPanel2.add(new JLabel("Thickness"));
        layerPanel2.add(new JCheckBox());
        layerPanel2.add(new JTextField("Air"));
        framePane.add(layerPanel2);
        // Arrange the components inside the window
        f.pack();
        // By default, the window is not visible. Make it visible.
        f.setVisible(true);
    }
 
    public static void main(String[] args) {
        Viewer se = new Viewer();
        // Schedules the application to be run at the correct time in the event queue.
        SwingUtilities.invokeLater(se);
    }
	/**
	 * @param args
	 */
	public static double getReflectance(MultiFilmStack mfs) {
		return mfs.getReflectance();
	}
//	Layer air = new Layer("Air", REFRACTIVE_INDEX_AIR);
//	Layer sio2 = new Layer("SiO2", REFRACTIVE_INDEX_SILICONDIOXIDE, 0.4);
//	Layer ito = new Layer("ITO", REFRACTIVE_INDEX_ITO, .17);
//	Layer si = new Layer("Si", REFRACTIVE_INDEX_SILICON, 4);
//	Layer[] layers = {air, sio2, ito, si, air};
//	Source source = new Source("IR 1500nm", 1.5, Math.toRadians(0));
//	MultiFilmStack stack = new MultiFilmStack(source, layers);
//	System.out.println("Reflectance: "+stack.getReflectance());

}
