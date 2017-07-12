package queries;

import com.google.common.collect.FluentIterable;

import java.util.Iterator;

/**
 * @author Miguel Gamboa
 *         created on 29-06-2017
 */
public class GuavaQueries<T> implements Iterator<T> {
    public static <T> FluentIterable<T> oddLines(FluentIterable<T> source) {
        return new FluentIterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new GuavaQueries<>(source.iterator());
            }
        };
    }

    private final Iterator<T> source;

    public GuavaQueries(Iterator<T> source) {
        this.source = source;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public T next() {
        T curr = source.next();
        curr = source.next();
        return curr;
    }
}
