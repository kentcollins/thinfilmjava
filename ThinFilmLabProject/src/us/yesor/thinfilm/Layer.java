/****
 * Represents a thin layer of optical material.
 * 
 * "Thin" should be interpreted as on the order of wavelength of the 
 * radiation of interest.  (Pedrotti, 1993, pg. 392)
 * 
 * @author Kent Collins
 * @date 6/18/2013
 ***/

package us.yesor.thinfilm;

public class Layer {

	private String name;
	private double refractiveIndex;
	private double thickness; // microns

	public Layer(String name, double refractiveIndex, double thickness) {
		this.name = name;
		this.refractiveIndex = refractiveIndex;
		this.thickness = thickness;
	}
	
	/****
	 * The innermost and outermost layers do not require a specified
	 * thickness.  
	 * 
	 * @param name
	 * @param refractiveIndex
	 */
	public Layer(String name, double refractiveIndex) {
		this.name = name;
		this.refractiveIndex = refractiveIndex;
		this.thickness = Double.MAX_VALUE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRefractiveIndex() {
		return refractiveIndex;
	}

	public void setRefractiveIndex(double refractiveIndex) {
		this.refractiveIndex = refractiveIndex;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}
}
