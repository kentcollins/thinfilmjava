package us.yesor.thinfilm.test;

import us.yesor.thinfilm.Layer;
import us.yesor.thinfilm.Source;
import us.yesor.thinfilm.VcselMath;
import junit.framework.TestCase;

public class VcselMathTestCase extends TestCase {
	
	Source source;
	Layer layer1;
	Layer layer2;
	Layer layer3;
	Layer[] layers = {layer1, layer2, layer3};

	
	public void testGetPhaseDifferenceSourceLayerLayerLayer() {
		this.assertEquals(1, 1);
	}

	public void testGetPhaseDifferenceSourceLayerArray() {
		fail("Not yet implemented");
	}

	public void testCorrectInternalExternalReflection() {
		fail("Not yet implemented");
	}

	public void testGetNearNormalPhaseDifferenceSourceLayerLayerLayer() {
		fail("Not yet implemented");
	}

	public void testGetNearNormalPhaseDifferenceSourceLayerArray() {
		fail("Not yet implemented");
	}

	public void testGetQuarterThickness() {
		fail("Not yet implemented");
	}

	public void testCalculateTransferMatrix() {
		fail("Not yet implemented");
	}

	public void testCalculateNearNormalReflectance() {
		fail("Not yet implemented");
	}

	public void testCalculateOpticalAngles() {
		source = new Source("No source", 0, 0);
		layer1 = new Layer("Glass", 1.5, 1.0);
		Layer[] single = {layer1};
		assertEquals(0, VcselMath.calculateOpticalAngles(
				new Source("Normal", 0, 0),
				single));
		assertEquals(0, VcselMath.calculateOpticalAngles(
				new Source("Negative", -1.0, 1.0), single));
	}

	public void testCalculateLayerGammas() {
		fail("Not yet implemented");
	}

}
