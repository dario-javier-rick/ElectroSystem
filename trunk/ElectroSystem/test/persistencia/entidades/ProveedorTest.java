package persistencia.entidades;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import persistencia.dao.ProveedorDAO;

public class ProveedorTest extends TestCase {

	private ProveedorDAO proveedorDAO;

	public ProveedorTest(String testName) {
		super(testName);
		try {
			proveedorDAO = new ProveedorDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Test suite() {
		return new TestSuite(ProveedorTest.class);
	}

	public void testReadAll() {
		try {
			proveedorDAO.readAll2();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

//	public void testInsert() {
//		ProveedorDTO marca = new ProveedorDTO(0, "Darius's industry", "20365604044");
//		try {
//			proveedorDAO.insert(marca);
//		} catch (Exception e) {
//			e.printStackTrace();
//			assertTrue(false);
//		}
//	}

}
