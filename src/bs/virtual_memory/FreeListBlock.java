package bs.virtual_memory;

/**
 * FreeListBlock
 * <p>
 * Datenstrukturelement einer Freibereichsliste, das Adresse und Grääe eines freien Blocks angibt
 */
public class FreeListBlock implements Comparable<Object> {
    private int adress;        // Reale Startadresse des freien Blocks
    private int size;        // Länge des freien Blocks in Byte

    /**
     * Konstruktor
     */
    public FreeListBlock(int curAdr, int curSize) {
        adress = curAdr;
        size = curSize;
    }

    /**
     * Vergleichsfunktion fär Sortierung
     */

    public int compareTo(Object otherBlock) {
        // Vergleiche mit anderem FreeListBlock fär die Sortierung
        return this.getAdress() - ((FreeListBlock) otherBlock).getAdress();
    }

    /**
     * @return Adresse des freien Blocks
     */
    public int getAdress() {
        return adress;
    }

    /**
     * @return Grääe des Blocks
     */
    public int getSize() {
        return size;
    }

    /**
     * @param i Adresse zuweisen
     */
    public void setAdress(int i) {
        adress = i;
    }

    /**
     * @param i Grääe zuweisen
     */
    public void setSize(int i) {
        size = i;
    }

    @Override
    public String toString() {
        return "(" + adress + "," + size + ")";
    }
}
