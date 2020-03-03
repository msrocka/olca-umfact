package org.openlca.umfact;

import java.io.File;

import org.openlca.core.matrix.format.CSCMatrix;
import org.openlca.julia.Julia;

public class Umfact {

    private static boolean loaded;

    public static native void solve(
        int n,
        int[] columnPointers,
        int[] rowIndices,
        double[] values,
        double[] demand,
        double[] result);

    public static double[] solve(CSCMatrix m, double[] demand) {
        double[] result = new double[demand.length];
        solve(m.rows, 
            m.columnPointers,
            m.rowIndices,
            m.values,
            demand,
            result);
        return result;
    }

    public static native long factorize(
        int n,
        int[] columnPointers,
        int[] rowIndices,
        double[] values
    );

    public static UmfactMatrix factorize(CSCMatrix m) {
        long pointer = factorize(
            m.rows, 
            m.columnPointers,
            m.rowIndices,
            m.values);
        return new UmfactMatrix(pointer);
    }

    public static native void dispose(long pointer);

    public static native long solveFactorized(
        long pointer, double[] demand, double[] result);
    
    public static double[] solve(UmfactMatrix m, double[] demand) {
        double[] result = new double[demand.length];
        solveFactorized(m.pointer, demand, result);
        return result;
    }
    
    /**
     * Loads the native library from the given folder. This folder must
     * contain the openLCA native calculation libraries with UMFPACK support.
     */
    public static void load(File dir) {
        if (loaded)
            return;
        if (!Julia.isLoaded()) {
            Julia.loadFromDir(dir);
        }
        if (!Julia.isWithUmfpack()) 
            throw new RuntimeException(
                "Could not load UMFPACK dependencies from " + dir);

        File f = new File(dir, "olca-umfact.dll"); // TODO: currently Windows only
        System.load(f.getAbsolutePath());
        loaded = true;
    }
}
