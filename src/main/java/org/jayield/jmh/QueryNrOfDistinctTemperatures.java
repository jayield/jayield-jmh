package org.jayield.jmh;

import com.aol.cyclops.control.ReactiveSeq;
import com.codepoetics.protonpack.StreamUtils;
import one.util.streamex.StreamEx;
import org.jayield.Query;
import org.jooq.lambda.Seq;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import queries.CyclopsQueries;
import queries.JayieldQueries;
import queries.JoolQueries;
import queries.StreamExQueries;
import queries.StreamQueries;
import queries.VavrQueries;

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
public class QueryNrOfDistinctTemperatures {

    @Benchmark
    public long nrOfTempsStream(DataSource src) {
        Stream<String> content = Arrays.stream(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1);                       // Skip line: Not available
        return StreamQueries.oddLines(content) // Filter hourly info
                .map(line -> line.substring(14, 16))
                .distinct()
                .count();
    }

    @Benchmark
    public long nrOfTempsStreamEx(DataSource src) {
        return StreamExQueries.oddLines(StreamEx.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1)                        // Skip line: Not available
        )                                       // Filter hourly info
                .map(line -> line.substring(14, 16))
                .distinct()
                .count();
    }

    @Benchmark
    public long nrOfTempsJoolWithZip(DataSource src) {
        return Seq.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1)                        // Skip line: Not available
                .zipWithIndex()
                .filter(l -> l.v2 % 2 != 0)     // Filter hourly info
                .map(line -> line.v1.substring(14, 16))
                .distinct()
                .count();
    }

    @Benchmark
    public long nrOfTempsJool(DataSource src) {
        return JoolQueries.oddLines(Seq.of(src.data)
                .filter(s -> s.charAt(0) != '#') // Filter comments
                .skip(1)  // Skip line: Not available
        )                 // Filter hourly info
                .map(line -> line.substring(14, 16))
                .distinct()
                .count();
    }

    @Benchmark
    public long nrOfTempsCyclops(DataSource src) {
        return CyclopsQueries.oddLines(ReactiveSeq.of(src.data)
                .filter(s -> s.charAt(0) != '#') // Filter comments
                .skip(1) // Skip line: Not available
        )                // Filter hourly info
                .map(line -> line.substring(14, 16))
                .distinct()
                .count();
    }

    @Benchmark
    public int nrOfTempsVavr(DataSource src) {
        return VavrQueries.oddLines(io.vavr.collection.Stream.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .drop(1) // Skip line: Not available
        )                // Filter hourly info
                .map(line -> line.substring(14, 16))
                .distinct()
                .size();
    }

    @Benchmark
    public int nrOfTempsVavrZip(DataSource src) {
        return io.vavr.collection.Stream.of(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .drop(1)
                .zipWithIndex()
                .filter(l -> l._2 % 2 != 0)     // Filter hourly info
                .map(line -> line._1.substring(14, 16))
                .distinct()
                .size();
    }

    @Benchmark
    public long nrOfTempsProtonpack(DataSource src) {
        return StreamUtils.zipWithIndex(Arrays.stream(src.data)
                .filter(s -> s.charAt(0) != '#')// Filter comments
                .skip(1)                        // Skip line: Not available
        )
                .filter(i -> i.getIndex() % 2 != 0)     // Filter hourly info
                .map(line -> line.getValue().substring(14, 16))
                .distinct()
                .count();
    }

    @Benchmark
    public long nrOfTempsJayield(DataSource src) {
        return Query.of(src.data)
                .filter(s -> s.charAt(0) != '#')   // Filter comments
                .skip(1)                           // Skip line: Not available
                .then(JayieldQueries::oddLines) // Filter hourly info
                .map(line -> line.substring(14, 16))
                .distinct()
                .count();
    }
}
