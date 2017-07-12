package org.jayield.jmh;

import com.aol.cyclops.control.ReactiveSeq;
import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;
import one.util.streamex.StreamEx;
import org.jayield.Series;
import org.jooq.lambda.Seq;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import queries.CyclopsQueries;
import queries.GuavaQueries;
import queries.JayieldQueries;
import queries.JoolQueries;
import queries.StreamExQueries;
import queries.StreamQueries;
import queries.VavrQueries;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

/**
 * @author Miguel Gamboa
 *         created on 12-07-2017
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 8)
@Warmup(iterations = 12)
@Fork(1)
public class QueryMaxTemperature {
    @Benchmark
    public int maxTempAgnostic(DataSource src) {
        final String[] data = src.data;
        int j = 0, max = Integer.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            final String line = data[i];
            if (line.charAt(0) == '#') continue;
            if (j++ == 0) continue;
            if (j % 2 != 0) {
                int temp = parseInt(line.substring(14, 16));
                if (temp > max) max = temp;
            }
        }
        return max;
    }

    @Benchmark
    public int maxTempStreamEx(DataSource src) {
        return StreamExQueries.oddLines(StreamEx.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1)                        // Skip line: Not available
        )                                       // Filter hourly info
                .mapToInt(line -> parseInt(line.substring(14, 16)))
                .max()
                .getAsInt();
    }

    @Benchmark
    public int maxTempGuava(DataSource src) {
        return Ordering.<Integer>natural()
                .max(
                        GuavaQueries.oddLines(FluentIterable.from(src.data)
                                .filter(s -> s.charAt(0) != '#')// Filter comments
                                .skip(1)                        // Skip line: Not available
                        )                                       // Filter hourly info
                                .transform(line -> parseInt(line.substring(14, 16)))
                );
    }

    @Benchmark
    public int maxTempJoolWithZip(DataSource src) {
        return Seq.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1)                        // Skip line: Not available
                .zipWithIndex()
                .filter(l -> l.v2 % 2 != 0)     // Filter hourly info
                .mapToInt(line -> parseInt(line.v1.substring(14, 16)))
                .max()
                .getAsInt();
    }

    @Benchmark
    public int maxTempJool(DataSource src) {
        return JoolQueries.oddLines(Seq.of(src.data)
                .filter(s -> s.charAt(0) != '#') // Filter comments
                .skip(1)  // Skip line: Not available
        )                 // Filter hourly info
                .mapToInt(line -> parseInt(line.substring(14, 16)))
                .max()
                .getAsInt();
    }

    @Benchmark
    public int maxTempCyclops(DataSource src) {
        return CyclopsQueries.oddLines(ReactiveSeq.of(src.data)
                .filter(s -> s.charAt(0) != '#') // Filter comments
                .skip(1) // Skip line: Not available
        )                // Filter hourly info
                .mapToInt(line -> parseInt(line.substring(14, 16)))
                .max()
                .getAsInt();
    }

    @Benchmark
    public int maxTempVavr(DataSource src) {
        return VavrQueries.oddLines(io.vavr.collection.Stream.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .drop(1) // Skip line: Not available
        )                // Filter hourly info
                .map(line -> parseInt(line.substring(14, 16)))
                .maxBy(Integer::compare)
                .get();
    }

    @Benchmark
    public int maxTempVavrZip(DataSource src) {
        return io.vavr.collection.Stream.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .drop(1)                        // Skip line: Not available
                .zipWithIndex()
                .filter(l -> l._2 % 2 != 0)     // Filter hourly info
                .map(line -> parseInt(line._1.substring(14, 16)))
                .maxBy(Integer::compare)
                .get();
    }

    @Benchmark
    public int maxTempJayield(DataSource src) {
        return Series.of(src.data)
                .filter(s -> s.charAt(0) != '#')   // Filter comments
                .skip(1)                           // Skip line: Not available
                .traverseWith(JayieldQueries::oddLines) // Filter hourly info
                .mapToInt(line -> parseInt(line.substring(14, 16)))
                .max()
                .getAsInt();
    }

    @Benchmark
    public int maxTempStream(DataSource src) {
        Stream<String> content = Arrays.stream(src.data)
                .filter(s -> s.charAt(0) != '#') // Filter comments
                .skip(1);                       // Skip line: Not available
        return StreamQueries.oddLines(content) // Filter hourly info
                .mapToInt(line -> parseInt(line.substring(14, 16)))
                .max()
                .getAsInt();
    }

    @Benchmark
    public int maxTempProtonpack(DataSource src) {
        return StreamUtils.zipWithIndex(Arrays.stream(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1)                        // Skip line: Not available
        )
                .filter(i -> i.getIndex() % 2 != 0)     // Filter hourly info
                .mapToInt(line -> parseInt(line.getValue().substring(14, 16)))
                .max()
                .getAsInt();
    }
}
