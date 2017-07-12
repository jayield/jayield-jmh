package org.jayield.jmh;

import org.jayield.Series;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import queries.JayieldQueries;
import queries.StreamQueries;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author Miguel Gamboa
 *         created on 12-07-2017
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 8)
@Warmup(iterations = 12)
@Fork(1)
public class QueryNrOfTemperaturesTransitions {

    @Benchmark
    public long nrOfTransitionsStream(DataSource src) {
        Stream<String> content = Arrays.stream(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1);                       // Skip line: Not available
        Stream<String> temps = StreamQueries.oddLines(content) // Filter hourly info
                .map(line -> line.substring(14, 16));
        return StreamQueries.collapse(temps)
                .count();
    }

    @Benchmark
    public long nrOfTransitionsJayield(DataSource src) {
        return Series.of(src.data)
                .filter(s -> s.charAt(0) != '#')   // Filter comments
                .skip(1)                           // Skip line: Not available
                .traverseWith(JayieldQueries::oddLines) // Filter hourly info
                .map(line -> line.substring(14, 16))
                .traverseWith(JayieldQueries::collapse)
                .count();
    }
}
