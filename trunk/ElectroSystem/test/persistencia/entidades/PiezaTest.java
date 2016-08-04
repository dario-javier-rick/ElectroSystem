package persistencia.entidades;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import persistencia.dao.PiezaDAO;

public class PiezaTest extends TestCase {

	private PiezaDAO piezaDAO;

	public PiezaTest(String testName) {
		super(testName);
		try {
			piezaDAO = new PiezaDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Test suite() {
		return new TestSuite(PiezaTest.class);
	}

	public void testReadAll() {
		try {
			piezaDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
