/*
 * Copyright (c) 2017, jasync.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package queries;

import io.vavr.collection.Iterator;
import io.vavr.collection.Stream;

/**
 * @author Miguel Gamboa
 *         created on 24-05-2017
 */
public class VavrQueries<T> implements Iterator<T>{

    public static <T> Stream<T> oddLines(Stream<T> src) {
        return Stream.ofAll(new VavrQueries<T>(src.iterator()));
    }

    private final Iterator<T> source;

    public VavrQueries(Iterator<T> source) {
        this.source = source;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public T next() {
        T curr = source.next();
        curr = source.next();
        return curr;
    }
}
