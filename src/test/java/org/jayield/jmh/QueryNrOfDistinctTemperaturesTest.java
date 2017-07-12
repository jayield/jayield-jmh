package org.jayield.jmh;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 *         created on 12-07-2017
 */
public class QueryNrOfDistinctTemperaturesTest {
    @Test
    public void testNrOfTempsQueriesStream() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsStream(new DataSource());
        assertEquals(count, 18);
    }
    @Test
    public void testNrOfTempsQueriesJayield() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsJayield(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesStreamEx() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsStreamEx(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesCyclops() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsCyclops(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesJool() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsJool(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesJoolZip() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsJoolWithZip(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesVavr() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsVavr(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesVavrZip() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsVavrZip(new DataSource());
        assertEquals(count, 18);
    }

    @Test
    public void testNrOfTempsQueriesProtonpack() {
        long count = new QueryNrOfDistinctTemperatures().nrOfTempsProtonpack(new DataSource());
        assertEquals(count, 18);
    }
}
