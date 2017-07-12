package queries;

import org.jooq.lambda.Seq;
import queries.StreamQueries.StreamOddLines;

/**
 * @author Miguel Gamboa
 *         created on 29-06-2017
 */
public class JoolQueries {
    public static <T> Seq<T> oddLines(Seq<T> source) {
        // StreamOddLines is a Spliterator
        // Seq.seq() <=> seq(StreamSupport.stream(spliterator, false)
        // The same overhead of StreamSupport.stream wrapping.
        return Seq.seq(new StreamOddLines<T>(source.spliterator()));
    }
}
