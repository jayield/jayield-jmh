package queries;

import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Miguel Gamboa
 *         created on 28-06-2017
 */
public class StreamQueries {

    public static <T> Stream<T> oddLines(Stream<T> source) {
        return StreamSupport.stream(
                new StreamOddLines<>(source.spliterator()), false);
    }

    /**
     * Merges series of adjacent elements.
     * Valid for sequences with non null elements.
     */
    public static <T> Stream<T> collapse(Stream<T> source) {
        return StreamSupport.stream(
                new StreamCollapse<T>(source.spliterator()), false);
    }


    static class StreamOddLines<T> extends AbstractSpliterator<T> {

        private static long odd(long l) {
            return l == Long.MAX_VALUE ? l : (l + 1) / 2;
        }

        final Consumer<T> doNothing = item -> {
        };
        final Spliterator<T> source;
        boolean isOdd;

        StreamOddLines(Spliterator<T> source) {
            super(odd(source.estimateSize()), source.characteristics());
            this.source = source;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (!source.tryAdvance(doNothing)) return false;
            return source.tryAdvance(action);
        }
    }

    /**
     * Merges series of adjacent elements.
     * Valid for sequences with non null elements.
     *
     * @param <T>
     */
    static class StreamCollapse<T> extends AbstractSpliterator<T> implements Consumer<T> {

        private final Spliterator<T> source;
        private T curr = null;

        StreamCollapse(Spliterator<T> source) {
            super(-1, source.characteristics());
            this.source = source;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            T prev = curr;
            boolean hasNext;
            while ((hasNext = source.tryAdvance(this)) && curr.equals(prev)) {
            }
            if(hasNext)
                action.accept(curr);
            return hasNext;
        }

        @Override
        public void accept(T item) {
            curr = item;
        }
    }
}
