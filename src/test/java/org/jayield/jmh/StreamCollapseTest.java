package org.jayield.jmh;

import org.testng.Assert;
import org.testng.annotations.Test;
import queries.StreamQueries;

import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 *         created on 20-07-2017
 */
public class StreamCollapseTest {
    @Test
    public void testStreamCollapseIntSequence() {
        Stream<Integer> nrs = Stream.of(3, 3, 5, 5, 3, 3, 3, 4, 4, 4 ,5 , 5);
        Integer [] expected = {3, 5, 3, 4, 5};
        Object[] actual = StreamQueries.collapse(nrs).toArray();
        assertEquals(actual, expected);
    }
}