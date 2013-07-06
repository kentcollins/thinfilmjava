/****
 * 
 * Represents multiple thin films deposited on a substrate
 * 
 * @author Kent Collins
 * @date 6/21/2013
 * 
 */
package us.yesor.thinfilm;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

public class MultiFilmStack {
	private Source source;
	private Layer [] layers;
	private double[] angles;
	private double[] gammas;
	private ComplexMatrix transfer;
	
	public MultiFilmStack(Source source, Layer[] layers){
		this.source = source;
		this.layers = layers;
		this.updateAngles();
		this.updateGammas();
		this.updateMatrix();
	}
	
	// Follows Pedrotti Eq. 19-36
	// Eq. 19-44 exclusively for phase difference across quarter thickness 
	public Complex getReflectionCoefficient(){
		Complex gamma0 = Complex.valueOf(gammas[0],0);
		Complex gammaS = Complex.valueOf(gammas[gammas.length-1],0);
		Complex m11 = transfer.get(0, 0);
		Complex m12 = transfer.get(0, 1);
		Complex m21 = transfer.get(1, 0);
		Complex m22 = transfer.get(1, 1);
		
		// Pedrotti, Eq. 19-36
		//  Complex r = (g0m11+g0gSm12-m21-gSm22)/(g0m11+g0gSm12+m21+gSm22)
		Complex n1 = gamma0.times(m11);
		Complex n2 = gamma0.times(gammaS).times(m12);
		Complex n3 = m21;
		Complex n4 = gammaS.times(m22);
		Complex numerator = n1.plus(n2).minus(n3).minus(n4);
		Complex denominator = n1.plus(n2).plus(n3).plus(n4);
		Complex r = numerator.divide(denominator);
		return r;
	}
	
	public double getReflectance() {
		Complex r = this.getReflectionCoefficient();
		Complex reflectance = r.times(r.conjugate());
		return reflectance.getReal();
	}
	
	private void updateAngles() {
		this.angles = VcselMath.calculateOpticalAngles(source, layers);
	}

	private void updateGammas() {
		this.gammas = VcselMath.calculateLayerGammas(angles, layers);
	}

	private void updateMatrix() {
		ComplexMatrix[] matrices = new ComplexMatrix[layers.length-2];
		for (int i = 0; i<matrices.length; i++){
			Layer[] subset = {layers[i], layers[i+1], layers[i+2]};
			matrices[i] = VcselMath.calculateTransferMatrix(source, subset);
		}
		ComplexMatrix overallTransfer = matrices[0];
		for (int i = 1; i<matrices.length; i++){
			overallTransfer = overallTransfer.times(matrices[i]);
		}
		this.transfer = overallTransfer;
	}
}
