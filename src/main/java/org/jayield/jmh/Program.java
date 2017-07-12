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

package org.jayield.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author Miguel Gamboa
 *         created on 29-07-2016
 */
public class Program {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(QueryMaxTemperature.class.getSimpleName())
                .include(QueryNrOfDistinctTemperatures.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}