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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 *         created on 02-05-2017
 */
public class QueryMaxTemperatureTest {

    @Test
    public void testMaxTempQueriesImperative() {
        int maxTemp = new QueryMaxTemperature().maxTempAgnostic(new DataSource());
        assertEquals(maxTemp, 27);
    }


    @Test
    public void testMaxTempQueriesStream() {
        int maxTemp = new QueryMaxTemperature().maxTempStream(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesGuava() {
        int maxTemp = new QueryMaxTemperature().maxTempGuava(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesStreamEx() {
        int maxTemp  = new QueryMaxTemperature().maxTempStreamEx(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesCyclops() {
        int maxTemp  = new QueryMaxTemperature().maxTempCyclops(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesJool() {
        int maxTemp  = new QueryMaxTemperature().maxTempJool(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesJoolZip() {
        int maxTemp  = new QueryMaxTemperature().maxTempJoolWithZip(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesProtonpack() {
        int maxTemp  = new QueryMaxTemperature().maxTempProtonpack(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesVavr() {
        int maxTemp  = new QueryMaxTemperature().maxTempVavr(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesVavrZip() {
        int maxTemp  = new QueryMaxTemperature().maxTempVavrZip(new DataSource());
        assertEquals(maxTemp, 27);
    }

    @Test
    public void testMaxTempQueriesJayield() {
        int maxTemp  = new QueryMaxTemperature().maxTempJayield(new DataSource());
        assertEquals(maxTemp, 27);
    }
}