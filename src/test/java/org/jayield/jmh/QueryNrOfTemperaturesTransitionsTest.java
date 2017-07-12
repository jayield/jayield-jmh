package org.jayield.jmh;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 *         created on 12-07-2017
 */
public class QueryNrOfTemperaturesTransitionsTest {

    @Test
    public void testNrOfTransitionsStream() {
        long count = new QueryNrOfTemperaturesTransitions().nrOfTransitionsStream(new DataSource());
        assertEquals(count, 79);
    }

    @Test
    public void testNrOfTransitionsJayield() {
        long count = new QueryNrOfTemperaturesTransitions().nrOfTransitionsJayield(new DataSource());
        assertEquals(count, 79);
    }
}
