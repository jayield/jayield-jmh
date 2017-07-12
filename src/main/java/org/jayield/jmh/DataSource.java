package org.jayield.jmh;

/**
 * @author Miguel Gamboa
 *         created on 12-07-2017
 */

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import util.IteratorFromInputStream;
import util.FileRequest;
import util.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;

@State(Scope.Benchmark)
public class DataSource {
    static final String path = "past-weather.ashx-q-41.15--8.6167-date-2017-02-01-enddate-2017-04-30";
    static final Request req = new FileRequest();

    final String[] data;

    public DataSource() {
        try (InputStream in = req.getBody(path)) {
            ArrayList<String> lines = new ArrayList<>();
            new IteratorFromInputStream(req.getBody(path))
                    .forEachRemaining(lines::add);
            data = lines.toArray(new String[lines.size()]);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
