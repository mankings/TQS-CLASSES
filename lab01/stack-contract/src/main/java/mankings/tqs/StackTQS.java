package mankings.tqs;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StackTQS {
    
    private ArrayList<Object> stack;
    private Integer bound = null;

    public StackTQS() {
        this.stack = new ArrayList<Object>();
    }

    public StackTQS(Integer bound) {
        this.stack = new ArrayList<Object>();
        this.bound = bound;
    }

    public Integer getBound() {
        return bound;
    }

    public void push(Object x) {
        this.stack.add(x);
        if (this.bound != null && this.stack.size() > this.bound) {
            throw new IllegalStateException();
        }
    }

    public Object pop() {
        if (this.stack.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Object last = this.stack.get( this.stack.size() - 1 );
            this.stack.remove( this.stack.size() - 1 );
            return last;
        }
    }

    public Object peek() {
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
