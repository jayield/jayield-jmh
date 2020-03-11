package org.jayield.jmh;

import com.aol.cyclops.control.ReactiveSeq;
import com.google.common.collect.FluentIterable;
import one.util.streamex.StreamEx;
import org.jayield.Query;
import org.jooq.lambda.Seq;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.stream.Stream;

import static java.util.stream.Stream.iterate;
import static org.testng.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 *         created on 30-06-2017
 */
public class FlatMapBreakingTest {

    /**
     * BUG on JDK java.util.stream :
     *   Stream.flatMap() causes breaking of short-circuiting of terminal
     *   operations
     * https://bugs.openjdk.java.net/browse/JDK-8075939
     */
    // @Test(timeOut = 1000)
    public void testFlatMapBreakingStream() {
        int first = Stream.of(7)
                .flatMap(e -> iterate(e, i->i+1))
                .findFirst()
                .get();
        assertEquals(first, 7);
    }

    @Test(timeOut = 1000)
    public void testFlatMapBreakingGuava() {
        int first = FluentIterable.of(7)
                .transformAndConcat(init -> () -> new Iterator<Integer>() {
                    int curr = init;
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Integer next() {
                        return curr++;
                    }
                })
                .first()
                .get();
        assertEquals(first, 7);
    }

    /**
     * BUG on JDK java.util.stream :
     *   Stream.flatMap() causes breaking of short-circuiting of terminal
     *   operations
     * https://bugs.openjdk.java.net/browse/JDK-8075939
     */
    // @Test(timeOut = 1000)
    public void testFlatMapBreakingStreamEx() {
        int first = StreamEx.of(7)
                .flatMap(e -> iterate(e, i->i+1))
                .findFirst()
                .get();
        assertEquals(first, 7);
    }

    /**
     * BUG on JDK java.util.stream :
     *   Stream.flatMap() causes breaking of short-circuiting of terminal
     *   operations
     * https://bugs.openjdk.java.net/browse/JDK-8075939
     */
    // @Test(timeOut = 1000)
    public void testFlatMapBreakingJool() {
        int first = Seq.of(7)
                .flatMap(e -> iterate(e, i->i+1))
                .findFirst()
                .get();
        assertEquals(first, 7);
    }
    /**
     * BUG on JDK java.util.stream :
     *   Stream.flatMap() causes breaking of short-circuiting of terminal
     *   operations
     * https://bugs.openjdk.java.net/browse/JDK-8075939
     */
    // @Test(timeOut = 1000)
    public void testFlatMapBreakingCyclops() {
        int first = ReactiveSeq.of(7)
                .flatMap(e -> iterate(e, i->i+1))
                .findFirst()
                .get();
        assertEquals(first, 7);
    }

    @Test(timeOut = 1000)
    public void testFlatMapBreakingVavr() {
        int first = io.vavr.collection.Stream.of(7)
                .flatMap(e -> io.vavr.collection.Stream.iterate(e, i->i+1))
                .head();
        assertEquals(first, 7);
    }
    
    @Test(timeOut = 1000)
    public void testFlatMapBreakingJayield() {
        int first = Query.of(7)
                .flatMap(e -> Query.iterate(e, i->i+1))
                .findFirst()
                .get();
        assertEquals(first, 7);
    }

}
