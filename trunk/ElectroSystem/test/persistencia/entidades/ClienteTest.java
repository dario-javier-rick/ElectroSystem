package persistencia.entidades;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import persistencia.dao.ClienteDAO;

public class ClienteTest extends TestCase {

	private ClienteDAO clienteDAO;

	public ClienteTest(String testName) {
		super(testName);
		try {
			clienteDAO = new ClienteDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Test suite() {
		return new TestSuite(ClienteTest.class);
	}

	public void testReadAll() {
		try {
			clienteDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testDelete() {
//		ClienteDTO cliente = new ClienteDTO(2, null, null, null, null, null, 0, null, null);
		try {
//			clienteDAO.delete(cliente, 1);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testInsert() {
//		ClienteDTO cliente = new ClienteDTO(0, "Fabian", "Caputo", "Rozalinda 254", "Los polvorines", "Buenos Aires", 1613, null, "fabian.caputo@outlok.com");
		try {
//			clienteDAO.insert(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testUpdate() {
//		ClienteDTO cliente = new ClienteDTO(2, "Fabian", "Caputo", "Rozalinda 254", "Los polvorines", "Buenos Aires", 1613, null, "fabian.caputo@outlok.com");
		try {
//			clienteDAO.update(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
