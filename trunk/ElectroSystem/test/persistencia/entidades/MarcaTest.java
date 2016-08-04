package persistencia.entidades;

import dto.MarcaDTO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import persistencia.dao.MarcaDAO;

public class MarcaTest extends TestCase {

	private MarcaDAO marcaDAO;

	public MarcaTest(String testName) {
		super(testName);
		try {
			marcaDAO = new MarcaDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Test suite() {
		return new TestSuite(MarcaTest.class);
	}

	public void testReadAll() {
		try {
			marcaDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testDelete() {
		MarcaDTO marca = new MarcaDTO(2, null);
		try {
			marcaDAO.delete(marca);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testInsert() {
		MarcaDTO marca = new MarcaDTO(0, "Asus dupla");
		try {
			marcaDAO.insert(marca);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testUpdate() {
		MarcaDTO marca = new MarcaDTO(0, "Samsung");
		try {
			marcaDAO.update(marca);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
