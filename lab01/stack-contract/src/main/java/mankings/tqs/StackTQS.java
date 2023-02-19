package mankings.tqs;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class StackTQS <T> {
    
    private LinkedList<T> stack;
    private Integer bound = null;

    public StackTQS() {
        this.stack = new LinkedList<T>();
    }

    public StackTQS(Integer bound) {
        this.stack = new LinkedList<T>();
        this.bound = bound;
    }

    public Integer getBound() {
        return bound;
    }

    public void push(T x) {
        this.stack.add(x);
        if (this.bound != null && this.stack.size() > this.bound) {
            throw new IllegalStateException();
        }
    }

    public T pop() {
        if (this.stack.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T last = this.stack.get( this.stack.size() - 1 );
            this.stack.remove( this.stack.size() - 1 );
            return last;
        }
    }

    public T peek() {
        if (this.stack.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return this.stack.get( this.stack.size() - 1 );
        }
    }

    public int size() {
        return this.stack.size();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public boolean removeElements() {
        return this.stack.removeAll( this.stack );
    }
}
