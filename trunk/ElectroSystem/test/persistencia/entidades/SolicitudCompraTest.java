package persistencia.entidades;

import dto.SolicitudCompraDTO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import persistencia.dao.SolicitudCompraDAO;

public class SolicitudCompraTest extends TestCase {

	private SolicitudCompraDAO solicitudCompraDAO;

	public SolicitudCompraTest(String testName) {
		super(testName);
		try {
			solicitudCompraDAO = new SolicitudCompraDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Test suite() {
		return new TestSuite(SolicitudCompraTest.class);
	}

	public void testInsert() {
		SolicitudCompraDTO solicitudCompra = new SolicitudCompraDTO(0, null, null, null);
		try {
			solicitudCompraDAO.insert(solicitudCompra,1);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
