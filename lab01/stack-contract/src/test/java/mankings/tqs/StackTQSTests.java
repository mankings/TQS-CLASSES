package mankings.tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackTQSTests {

    private StackTQS stack;
    private StackTQS bound_stack;

    @BeforeEach
    public void start() {
        this.stack = new StackTQS();
        this.bound_stack = new StackTQS(5);
    }

    @AfterEach
    public void end() {
        this.stack.removeElements();
        this.bound_stack.removeElements();
    }

    @DisplayName("A")
    @Test
    public void emptyStackOnConstruction() {
        assertTrue(stack.isEmpty());
    }
    
    @DisplayName("B")
    @Test
    public void sizeZeroOnConstruction() {
        assertEquals(0, stack.size());
    }

    @DisplayName("C")
    @Test
    public void afterNPushes() {
        this.stack.push("super");
        this.stack.push("sagres");
        this.stack.push("cergal");

        assertEquals(3, this.stack.size());
        assertFalse(this.stack.isEmpty());
    }

    @DisplayName("D")
    @Test
    public void pushThenPopReturnsPushed() {
        this.stack.push("mancósmico");
        Object popped = this.stack.pop();

        assertEquals(popped, "mancósmico");
    }

    @DisplayName("E")
    @Test
    public void pushThenPeekReturnsPushedButSizeUnchanged() {
        this.stack.push("mancoso");
        int sizeBeforePeek = this.stack.size();
        Object peeked = this.stack.peek();

        assertEquals("mancoso", peeked);
        assertEquals(sizeBeforePeek, this.stack.size());
    }

    @DisplayName("F")
    @Test
    public void ifPopEverythingThenStackEmpty() {
        this.stack.push("z");
        this.stack.push("a");

        this.stack.pop();
        this.stack.pop();

        assertTrue(this.stack.isEmpty());
        assertEquals(0, this.stack.size());
    }

    @DisplayName("G")
    @Test
    public void popFromEmptyThrowsNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> {this.stack.pop();});
    }

    @DisplayName("H")
    @Test
    public void peekFromEmptyThrowsNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> {this.stack.peek();});
    }

    @DisplayName("I")
    @Test
    public void pushOnFullBoundedThrowsIllegalStateException () {
        for(int i = 0; i < this.bound_stack.getBound(); i++) {
            this.bound_stack.push("salada");
        }

        assertThrows(IllegalStateException.class, () -> {this.bound_stack.push("mais");});
    }
}

