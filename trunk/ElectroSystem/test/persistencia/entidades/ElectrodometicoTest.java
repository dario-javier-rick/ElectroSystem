package persistencia.entidades;

import dto.ElectrodomesticoDTO;
import dto.MarcaDTO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import persistencia.dao.ElectrodomesticoDAO;

public class ElectrodometicoTest extends TestCase {

	private ElectrodomesticoDAO electrodomesticoDAO;

	public ElectrodometicoTest(String testName) {
		super(testName);
		try {
			this.electrodomesticoDAO = new ElectrodomesticoDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Test suite() {
		return new TestSuite(ElectrodometicoTest.class);
	}

	public void testReadAll() {
		try {
			electrodomesticoDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testDelete() {
		ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(2, null, null, null);
		try {
			electrodomesticoDAO.delete(electrodomestico, 1);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testInsert() {
		MarcaDTO marca = new MarcaDTO(1, null);
		ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(0, marca, "XQW", "Heladera master choi");
		try {
			electrodomesticoDAO.insert(electrodomestico);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testUpdate() {
		MarcaDTO marca = new MarcaDTO(1, null);
		ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(4, marca, "XQW", "Heladera master choi");
		try {
			electrodomesticoDAO.update(electrodomestico);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
