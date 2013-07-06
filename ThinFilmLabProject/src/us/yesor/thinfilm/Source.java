package us.yesor.thinfilm;

/****
 * 
 * A source of radiation
 * 
 * @author Kent Collins
 * @date 6/19/2013
 * 
 ***/

public class Source {
	
	private String name;
	private double wavelength; // microns
	private double angleOfIncidence; // angle in radians from optical axis
	
	public Source(String name, double wavelength, double angleOfIncidence) {
		this.setName(name);
		this.wavelength = wavelength;
		this.angleOfIncidence = angleOfIncidence;
	}
	
	public Source(String name, double wavelength) {
		this.setName(name);
		this.wavelength = wavelength;
		this.angleOfIncidence = 0.0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAngleOfIncidence() {
		return angleOfIncidence;
	}
	public void setAngleOfIncidence(double angleOfIncidence) {
		this.angleOfIncidence = angleOfIncidence;
	}
	public double getWavelength() {
		return wavelength;
	}
	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
	}

}
