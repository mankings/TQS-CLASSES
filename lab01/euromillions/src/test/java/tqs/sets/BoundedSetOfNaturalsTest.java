/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;
    private BoundedSetOfNaturals setE;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = BoundedSetOfNaturals.fromArray(new int[]{20, 30});
        setE = BoundedSetOfNaturals.fromArray(new int[]{40, 60});

    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = setE = null;
    }

    @DisplayName("tests adding elements to sets")
    @Test
    public void testAddElement() {
        // add element to 

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(11), "add: adding to full set does not throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> setC.add(50), "add: adding duplicate number does not throw IllegalArgumentException");
    }

    @DisplayName("tests adding to a set from a bad array")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @DisplayName("tests if contains works")
    @Test
    public void testContains() {
        assertTrue(setB.contains(20));
        assertFalse(setC.contains(10));
    }

    @DisplayName("tests if intersects works")
    @Test
    public void testIntersects() {
        assertFalse(setB.intersects(setC));
        assertTrue(setB.intersects(setD));
        assertFalse(setB.intersects(setE));

    }
}
