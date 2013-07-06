/****
 * Mathematical methods for working with thin film stacks.
 * 
 * @author Kent Collins
 * @date 6/19/2013
 */

package us.yesor.thinfilm;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

public class VcselMath {

	// Permittivity in free space, farads per meter
	public static final double PERMITTIVITY = 8.854157 * Math.pow(10, -12);
	// Permeability in free space, Henries per meter
	public static final double PERMEABILITY = 4 * Math.PI * Math.pow(10, -7);
	// EM constant (square root of permittivity times permeability)
	public static final double EM_CONSTANT = Math.sqrt(PERMITTIVITY * PERMEABILITY);

	 //public static final double EM_CONSTANT = 1.0;

	/****
	 * Returns the phase difference due to traveling across one half of a film.
	 * To calculate the overall phase difference, double this value and then add
	 * an additional shift of PI (for cases involving one internal and one
	 * external reflection.)
	 * 
	 * @param source
	 * @param layerA
	 *            The originating medium
	 * @param layerB
	 *            The film
	 * @param layerC
	 *            The substrate medium
	 * @return
	 */
	public static double getPhaseDifference(Source source, Layer layerA,
			Layer layerB, Layer layerC) {

		double n1 = layerA.getRefractiveIndex();
		double n2 = layerB.getRefractiveIndex();
		layerC.getRefractiveIndex();

		double transmissionAngle = getTransmissionAngle(
				source.getAngleOfIncidence(), n1, n2);

		// Optical path distance, from Pedrotti, Eq. 10-33 && Eq. 19-15
		// Note that for transfer matrix, opd is based on a single pass
		// from one side of film to the other.
		double opticalPathDifference = n2 * layerB.getThickness()
				* Math.cos(transmissionAngle);

		// k, the wave propagation constant
		double k = 2 * Math.PI / source.getWavelength();

		// Phase difference due to optical path, from Pedrotti, pg. 214
		double shift = k * opticalPathDifference;

		// Return a value between 0 and 2Pi
		// return shift % (2 * Math.PI);
		return shift;
	}

	public static double getPhaseDifference(Source s, Layer[] layers) {
		return getPhaseDifference(s, layers[0], layers[1], layers[2]);
	}

	/****
	 * In a simple thin film scenario where the refracted wave travels across
	 * the film and back, if it is not the case that n0>n1>n2 or that n0<n1<n2,
	 * then it is the case that we have one internal reflection and one external
	 * reflection and the resulting phase difference between the original
	 * reflection and the returning wave must be corrected by an additional
	 * shift.
	 * 
	 * @param shift
	 * @return The original value plus an additional pi radians.
	 */
	public static double correctInternalExternalReflection(double shift) {
		double adjusted = shift + Math.PI;
		return adjusted;
	}

	/****
	 * Returns the phase difference associated with an incident angle of 0
	 * 
	 * @param s
	 * @param a
	 * @param b
	 * @param c
	 * @return Phase difference for radiation near the normal
	 */
	public static double getNearNormalPhaseDifference(Source s, Layer a,
			Layer b, Layer c) {
		Source s1 = new Source(s.getName(), s.getWavelength(), 0);
		return getPhaseDifference(s1, a, b, c);
	}

	public static double getNearNormalPhaseDifference(Source s, Layer[] layers) {
		return getNearNormalPhaseDifference(s, layers[0], layers[1], layers[2]);
	}

	private static double getTransmissionAngle(double incidentAngle, double n1,
			double n2) {
		// Apply Snell's law
		double transmissionAngle = Math.asin(Math.sin(incidentAngle) * n1 / n2);
		return transmissionAngle;
	}

	/****
	 * Returns the quarter wavelength thickness of a material, based on the
	 * wavelength of the radiation entering.
	 * 
	 * 
	 * @param index
	 *            Refractive index of the material
	 * @param wavelength
	 *            Wavelength, in a vacuum, in microns, of the incident light
	 * @return Thickness, in microns, corresponding to a quarter wavelength
	 */
	public static double getQuarterThickness(double index, double wavelength) {
		double lambdaFilm = wavelength / index; //
		return lambdaFilm / 4;
	}

	/****
	 * Determines the transfer matrix for a thin film system of three layers.
	 * 
	 * @param source
	 * @param layers
	 * @return
	 */
	public static ComplexMatrix calculateTransferMatrix(Source source,
			Layer[] layers) {

		double angleA = source.getAngleOfIncidence();
		double angleB = getTransmissionAngle(angleA,
				layers[0].getRefractiveIndex(), layers[1].getRefractiveIndex());
		double gammaB = layers[1].getRefractiveIndex() * EM_CONSTANT
				* Math.cos(angleB);
		double delta = getPhaseDifference(source, layers[0], layers[1],
				layers[2]);

		// Pedrotti, Eq. 19-24
		Complex m11 = Complex.valueOf(Math.cos(delta), 0);
		Complex m12 = Complex.valueOf(0, Math.sin(delta) / gammaB);
		Complex m21 = Complex.valueOf(0, gammaB * Math.sin(delta));
		Complex m22 = Complex.valueOf(Math.cos(delta), 0);
		Complex[][] elements = { { m11, m12 }, { m21, m22 } };
		ComplexMatrix m = ComplexMatrix.valueOf(elements);
		return m;
	}

	public static double calculateNearNormalReflectance(Source s, Layer[] layers) {
		// Observes simplification afforded by assuming angle of incidence near
		// the surface normal
		double delta = getNearNormalPhaseDifference(s, layers[0], layers[1],
				layers[2]);
		double n0 = layers[0].getRefractiveIndex();
		double n1 = layers[1].getRefractiveIndex();
		double ns = layers[2].getRefractiveIndex();
		// Following Pedrotti, Eq. 19-42
		double num1 = n1 * n1 * (n0 - ns) * (n0 - ns) * Math.cos(delta)
				* Math.cos(delta);
		double num2 = (n0 * ns - n1 * n1) * (n0 * ns - n1 * n1)
				* Math.sin(delta) * Math.sin(delta);
		double den1 = n1 * n1 * (n0 + ns) * (n0 + ns) * Math.cos(delta)
				* Math.cos(delta);
		double den2 = (n0 * ns + n1 * n1) * (n0 * ns + n1 * n1)
				* Math.sin(delta) * Math.sin(delta);
		double reflectance = (num1 + num2) / (den1 + den2);
		return reflectance;
	}

	public static double[] calculateOpticalAngles(Source source, Layer[] layers) {
		double[] angles = new double[layers.length];
		angles[0] = source.getAngleOfIncidence();
		for (int i = 1; i < angles.length; i++) {
			angles[i] = getTransmissionAngle(angles[i - 1],
					layers[i - 1].getRefractiveIndex(),
					layers[i].getRefractiveIndex());
		}
		return angles;
	}

	/****
	 * Returns an array of the gamma coefficients used in calculating the
	 * transfer matrix, the transmission coefficient, and the reflection
	 * coefficient.
	 * 
	 * @param angles
	 * @param layers
	 * @return
	 */
	public static double[] calculateLayerGammas(double[] angles, Layer[] layers) {
		// Pedrotti, Eq. 19-12, etc.
		double[] gammas = new double[angles.length];
		for (int i =0; i<angles.length; i++){
			gammas[i] = layers[i].getRefractiveIndex()*EM_CONSTANT*Math.cos(angles[i]);
		}
		return gammas;
	}
}
