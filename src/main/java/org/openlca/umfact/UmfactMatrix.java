package org.openlca.umfact;

/**
 * Stores a pointer to a factorized matrix in native land.
 */
public class UmfactMatrix {
    final long pointer;

    UmfactMatrix(long pointer) {
        this.pointer = pointer;
    }

    public void dispose() {
        Umfact.dispose(pointer);
    }

}