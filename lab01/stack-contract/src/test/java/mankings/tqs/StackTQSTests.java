package mankings.tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackTQSTests {

    private StackTQS<String> stack;
    private StackTQS<String> bound_stack;

    @BeforeEach
    public void start() {
        this.stack = new StackTQS<>();
        this.bound_stack = new StackTQS<>(5);
    }

    @AfterEach
    public void end() {
        this.stack.removeElements();
        this.bound_stack.removeElements();
    }

    @DisplayName("A")
    @Test
    public void emptyStackOnConstruction() {
        assertTrue(stack.isEmpty(), "Stack is not empty on construction.");
    }
    
    @DisplayName("B")
    @Test
    public void sizeZeroOnConstruction() {
        assertEquals(0, stack.size(), "Stack size is not zero on construction.");
    }

    @DisplayName("C")
    @Test
    public void afterNPushes() {
        this.stack.push("super");
        this.stack.push("sagres");
        this.stack.push("cergal");

        assertEquals(3, this.stack.size(), "After n pushes, size is not n.");
        assertFalse(this.stack.isEmpty(), "After n pushes, stack is empty.");
    }

    @DisplayName("D")
    @Test
    public void pushThenPopReturnsPushed() {
        this.stack.push("mancósmico");
        Object popped = this.stack.pop();

        assertEquals(popped, "mancósmico", "Push then pop does not return pushed value.");
    }

    @DisplayName("E")
    @Test
    public void pushThenPeekReturnsPushedButSizeUnchanged() {
        this.stack.push("mancoso");
        int sizeBeforePeek = this.stack.size();
        Object peeked = this.stack.peek();

        assertEquals("mancoso", peeked, "Push then peek does not return peeked value.");
        assertEquals(sizeBeforePeek, this.stack.size(), "Size changes after peek.");
    }

    @DisplayName("F")
    @Test
    public void ifPopEverythingThenStackEmpty() {
        this.stack.push("z");
        this.stack.push("a");

        this.stack.pop();
        this.stack.pop();

        assertTrue(this.stack.isEmpty(), "Stack not empty after popping everything.");
        assertEquals(0, this.stack.size(), "Stack size is not zero after popping everything.");
    }

    @DisplayName("G")
    @Test
    public void popFromEmptyThrowsNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> {this.stack.pop();}, "NoSuchElementException not thrown after pop on empty stack.");
    }

    @DisplayName("H")
    @Test
    public void peekFromEmptyThrowsNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> {this.stack.peek();}, "NoSuchElementException not thrown after peek on empty stack.");
    }

    @DisplayName("I")
    @Test
    public void pushOnFullBoundedThrowsIllegalStateException () {
        for(int i = 0; i < this.bound_stack.getBound(); i++) {
            this.bound_stack.push("salada");
        }

        assertThrows(IllegalStateException.class, () -> {this.bound_stack.push("mais");}, "IllegalStateException was not thrown after push on full bounded stack.");
    }
}

