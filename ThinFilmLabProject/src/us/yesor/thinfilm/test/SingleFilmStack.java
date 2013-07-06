/****
 * 
 * Represents a thin film on a substrate
 * 
 * @author Kent Collins
 * @date 6/20/2013
 * 
 */
package us.yesor.thinfilm.test;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

import us.yesor.thinfilm.Layer;
import us.yesor.thinfilm.Source;
import us.yesor.thinfilm.VcselMath;

public class SingleFilmStack {
	private Source source;
	private Layer [] layers;
	private double[] angles;
	private double[] gammas;
	private ComplexMatrix transfer;
	
	public SingleFilmStack(Source source, Layer[] layers){
		this.source = source;
		this.layers = layers;
		this.updateAngles();
		this.updateGammas();
		this.updateMatrix();
	}
	
	public SingleFilmStack(Source s, Layer a, Layer b, Layer c) {
		Layer[] layers = {a, b, c};
		this.source = s;
		this.layers = layers;
		this.updateAngles();
		this.updateGammas();
		this.updateMatrix();
	}
	
	public Complex getReflectionCoefficient(){
		Complex gamma0 = Complex.valueOf(gammas[0],0);
		Complex gammaS = Complex.valueOf(gammas[2],0);
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
		this.transfer = VcselMath.calculateTransferMatrix(source, layers);
	}
}
