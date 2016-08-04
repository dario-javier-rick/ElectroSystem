package persistencia;

import junit.framework.Test;
import junit.framework.TestSuite;
import persistencia.entidades.ClienteTest;
import persistencia.entidades.ElectrodometicoTest;
import persistencia.entidades.MarcaTest;
import persistencia.entidades.PiezaTest;
import persistencia.entidades.ProveedorTest;
import persistencia.entidades.SolicitudCompraTest;

public class PersistenciaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(PersistenciaTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTest(ClienteTest.suite());
		suite.addTest(ElectrodometicoTest.suite());
		suite.addTest(MarcaTest.suite());
//		suite.addTest(OrdenTest.suite());
		suite.addTest(PiezaTest.suite());
		suite.addTest(ProveedorTest.suite());
		suite.addTest(SolicitudCompraTest.suite());
		// $JUnit-END$

		return suite;
	}

}
