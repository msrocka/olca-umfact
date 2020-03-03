package tests;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openlca.core.matrix.format.CSCMatrix;
import org.openlca.core.matrix.format.HashPointMatrix;
import org.openlca.umfact.Umfact;
import org.openlca.umfact.UmfactMatrix;

public class UmfactTest {

    @BeforeClass
    public static void setUp() {
        Umfact.load(new File("libs"));
    }

    @Test
    public void testSolveNative() {
        double[] x = new double[5];
        Umfact.solve(5, 
            new int[] { 0, 2, 5, 9, 10, 12 }, 
            new int[] { 0, 1, 0, 2, 4, 1, 2, 3, 4, 2, 1, 4 },
            new double[] { 2., 3., 3., -1., 4., 4., -3., 1., 2., 2., 6., 1. },
            new double[] { 8., 45., -3., 3., 19. },
        x);
        assertArrayEquals(
            new double[] {1d, 2d, 3d, 4d, 5d }, x, 1e-8);
    }

    @Test
    public void testSolveMatrix() {
        HashPointMatrix m = new HashPointMatrix(new double[][] { 
            { 2.0, 3.0, 0.0, 0.0, 0.0 },
            { 3.0, 0.0, 4.0, 0.0, 6.0 },
            { 0.0, -1.0, -3.0, 2.0, 0.0 },
            { 0.0, 0.0, 1.0, 0.0, 0.0 },
            { 0.0, 4.0, 2.0, 0.0, 1.0 } });
        CSCMatrix csc = CSCMatrix.of(m);
        double[] demand = { 8., 45., -3., 3., 19. };
        double[] x = Umfact.solve(csc, demand);
        assertArrayEquals(
            new double[] {1d, 2d, 3d, 4d, 5d }, x, 1e-8);
    }

    @Test
    public void testFactorizeMatrix() {
        HashPointMatrix m = new HashPointMatrix(new double[][] { 
            { 2.0, 3.0, 0.0, 0.0, 0.0 },
            { 3.0, 0.0, 4.0, 0.0, 6.0 },
            { 0.0, -1.0, -3.0, 2.0, 0.0 },
            { 0.0, 0.0, 1.0, 0.0, 0.0 },
            { 0.0, 4.0, 2.0, 0.0, 1.0 } });
        CSCMatrix csc = CSCMatrix.of(m);
        UmfactMatrix ptr = Umfact.factorize(csc);
        
        double[] demand = { 8., 45., -3., 3., 19. };
        double[] x = Umfact.solve(ptr, demand);
        assertArrayEquals(
            new double[] {1d, 2d, 3d, 4d, 5d }, x, 1e-8);
        //factorizedM.dispose(); -> TODO: this currently leads to a crash of the JVM
    }

}
