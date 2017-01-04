/**
 * 
 */
package com.Deutsche.geocode.latlang;

import static org.junit.Assert.assertArrayEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.Deutsche.geocode.latlang.google.GoogleLatLang;

/**
 * Latlang test
 * 
 * @author jangid_m
 * 
 */
public class ILatLangTest {

	@Autowired
	ILatLang latlang;

	private GoogleLatLang googleLatLangMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		googleLatLangMock = Mockito.mock(GoogleLatLang.class);
		latlang = new LatLang(googleLatLangMock);
		Mockito.when(googleLatLangMock.getResult(342001)).thenReturn(
				new double[] { 1, 1 });
		Mockito.when(googleLatLangMock.getResult(411021)).thenReturn(
				new double[] { 2, 2 });
		Mockito.when(googleLatLangMock.getResult(411045)).thenReturn(
				new double[] { 3, 3 });
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.Deutsche.geocode.latlang.ILatLang#getLatlangFromPostCode(int, java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testGetLatlangFromPostCode() {
		double[] expected = new double[] { 1, 1 };
		double[] actual = latlang.getLatlangFromPostCode(342001, true);
		assertArrayEquals(expected, actual, 0);
	}

}
