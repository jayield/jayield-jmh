package queries;

import org.jayield.Series;
import org.jayield.Traversable;

/**
 * @author Miguel Gamboa
 *         created on 12-07-2017
 */
public class JayieldQueries {

    public static <U> Traversable<U> oddLines(Series<U> src) {
        return yield -> {
            final boolean[] isOdd = {false};
            src.traverse(item -> {
                if (isOdd[0]) yield.ret(item);
                isOdd[0] = !isOdd[0];
            });
        };
    }

    public static <U> Traversable<U> collapse(Series<U> src) {
        return yield -> {
            final Object[] prev = {null};
            src.traverse(item -> {
                if (prev[0] == null || !prev[0].equals(item))
                    yield.ret((U) (prev[0] = item));
            });
        };
    }
}
