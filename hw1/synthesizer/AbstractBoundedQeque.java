package synthesizer;

public abstract class AbstractBoundedQeque<T> implements BoundedQeque<T> {
    protected int fillCount;
    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    };

    @Override
    public int fillCount() {
        return fillCount;
    }


}
