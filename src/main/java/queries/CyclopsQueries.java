package queries;

import com.aol.cyclops.control.ReactiveSeq;
import queries.StreamQueries.StreamOddLines;

/**
 * @author Miguel Gamboa
 *         created on 29-06-2017
 */
public class CyclopsQueries {
    public static <T> ReactiveSeq<T> oddLines(ReactiveSeq<T> source) {
        // StreamOddLines is a Spliterator
        // ReactiveSeq.fromSpliterator() <=> fromStream(StreamSupport.stream(spliterator, false))
        // The same overhead of StreamSupport.stream wrapping.
        return ReactiveSeq.fromSpliterator(new StreamOddLines<T>(source.spliterator()));
    }
}
