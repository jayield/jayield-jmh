package queries;

import one.util.streamex.StreamEx;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Miguel Gamboa
 *         created on 29-06-2017
 */
public class StreamExQueries {

    public static <T> StreamEx<T> oddLines(StreamEx<T> source) {
        final Consumer<? super T> doNothing = item -> {};
        final Spliterator<T> iter = source.spliterator();
        Predicate<Consumer<? super T>> oddIter = action -> {
            if(!iter.tryAdvance(doNothing)) return false;
            return iter.tryAdvance(action);
        };
        return StreamEx.produce(oddIter);
    }
}
