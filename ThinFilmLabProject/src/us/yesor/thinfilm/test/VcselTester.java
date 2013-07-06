/****
 * Test battery for verifying calculations on thin film stack models
 * 
 * @author Kent Collins
 * @date 6/17/2013
 */

package us.yesor.thinfilm.test;

import us.yesor.thinfilm.Layer;
import us.yesor.thinfilm.MultiFilmStack;
import us.yesor.thinfilm.Source;
import us.yesor.thinfilm.VcselMath;

public class VcselTester {

	public static final double REFRACTIVE_INDEX_AIR = 1.0;
	public static final double REFRACTIVE_INDEX_SILICON = 3.42009;
	public static final double REFRACTIVE_INDEX_ITO = 2.35046;
	public static final double REFRACTIVE_INDEX_SILICONDIOXIDE = 1.54427;

	public static final boolean TEST_ONE = true;
	public static final boolean TEST_TWO = false;
	public static final boolean TEST_THREE = false;
	public static final boolean TEST_FOUR = false;
	public static final boolean TEST_FIVE = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// single traversal shift of quarter thickness should be pi/2
		if (TEST_ONE) { 
			System.out.println("TEST ONE --->");
			Layer airLayer = new Layer("Air", REFRACTIVE_INDEX_AIR);
			Layer siliconLayer = new Layer("Silicon Film",
					REFRACTIVE_INDEX_SILICON, 4.0);
			Layer itoLayer = new Layer("ITO", REFRACTIVE_INDEX_ITO, 0.17);

			// Test some 512 nm radiation at a normal incidence:
			Source test = new Source("TestSource", 0.512, Math.toRadians(0));
			siliconLayer.setThickness(VcselMath.getQuarterThickness(
					siliconLayer.getRefractiveIndex(), test.getWavelength()));

			double delta = VcselMath.getPhaseDifference(test, airLayer,
					siliconLayer, itoLayer);
			System.out.println("Angle: " + test.getAngleOfIncidence()
					+ " \tThickness :" + siliconLayer.getThickness()
					+ "\t Phase Shift: " + delta);
		}
		
		//  Results should match Pedrotti, Ch. 19 pg 396. As approach PI/2, R -> 1.0
		if (TEST_TWO) {
			System.out.println("TEST TWO --->");
			Layer airLayer = new Layer("Air", REFRACTIVE_INDEX_AIR);
			Layer zircDioxideLayer = new Layer("ZirconiumDioxide", 2.10, .040);
			Layer glassLayer = new Layer("Glass", 1.50);
			Layer[] stack = {airLayer, zircDioxideLayer, glassLayer};
			Source source = new Source("Normal Sodium Light", .5893, 0);
			SingleFilmStack v = new SingleFilmStack(source, stack);
			System.out.println("Phase difference: "
					+ VcselMath.getPhaseDifference(source, airLayer,
							zircDioxideLayer, glassLayer));
			System.out
					.println("Transformed Reflectance is " + v.getReflectance());
			// Should match near normal value for angles close to 0
			System.out.println("NearNormalReflectance is "
					+ VcselMath.calculateNearNormalReflectance(source, stack));
		}
		
		// Values should match answers to Pedrotti, Problem 19-4.
		if (TEST_THREE) {
			System.out.println("TEST THREE --->");
			Layer airLayer = new Layer("air", 1.0);
			Layer siliconDioxideLayer = new Layer("SiO2", 1.46, .137);
			Layer glassLayer = new Layer("Glass", 1.52);
			Layer[] stack = {airLayer, siliconDioxideLayer, glassLayer};
			Source source = new Source("Unnamed Source", 0.400);
			SingleFilmStack v = new SingleFilmStack(source, stack);
			System.out.println("Phase difference: "
					+ VcselMath.getPhaseDifference(source, airLayer,
							siliconDioxideLayer, glassLayer));
			System.out
					.println("Reflectance for stack is " + v.getReflectance());
		}
		
		// Values should match answers to Pedrotti, Problem 19-5.
		if (TEST_FOUR) {
			System.out.println("TEST FOUR A --->");
			Layer airLayer = new Layer("air", 1.0);
			Layer zns = new Layer("Zinc Sulfide", 2.35, .0596);
			Layer glassLayer = new Layer("Glass", 1.52);
			Layer[] stack = {airLayer, zns, glassLayer};
			Source source = new Source("Unnamed Source", .560);
			SingleFilmStack v = new SingleFilmStack(source, stack);
			System.out.println("Phase difference: "
					+ VcselMath.getPhaseDifference(source, stack));
			System.out
					.println("Reflectance for stack is " + v.getReflectance());
		}
		
		// Values should match answers to Pedrotti, Problem 19-5.
		if (TEST_FOUR) {
			System.out.println("TEST FOUR B --->");
			Layer airLayer = new Layer("air", 1.0);
			Layer zns = new Layer("Zinc Sulfide", 2.35, .0596);
			Layer glassLayer = new Layer("Glass", 1.52);
			Layer[] stack = {airLayer, zns, glassLayer};
			Source source = new Source("Unnamed Source", .560);
			MultiFilmStack v = new MultiFilmStack(source, stack);
			System.out.println("Phase difference: "
					+ VcselMath.getPhaseDifference(source, stack));
			System.out
					.println("Reflectance for stack is " + v.getReflectance());
		}
		
		// Values should match results for Pedrotti, Section 19-3
		if (TEST_FIVE) {
			System.out.println("TEST FIVE --->");
			Source source = new Source("550 nm Source", 0.650);
			Layer air = new Layer("Air", 1);
			Layer cef3 = new Layer("Cerium Trifluoride", 1.65, VcselMath.getQuarterThickness(1.65, .550));
			Layer zro2 = new Layer("Zirconium Dioxide", 2.1, VcselMath.getQuarterThickness(2.1, .550));
			Layer glass = new Layer("Glass Substrate", 1.52);
			Layer[] layers = {air, cef3, zro2, glass};
			MultiFilmStack stack = new MultiFilmStack(source, layers);
			System.out.println("Reflectance: "+stack.getReflectance());
		}
	}

}
