# jayield-jmh

Follow the procedure to run JMH tests:

    $ mvn clean install
    $ java -jar target/benchmarks.jar


[JAYield](https://github.com/jayield/jayield) provides an extensible lazy sequence
API, which reduces verbosity, keeps fluent query invocation and is faster than
Java
[Stream](https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html)
API for sequential traversing with user-defined custom operations (e.g. `oddLines`
or `collapse`).

In this work, we compare the performance of [JAYield](https://github.com/jayield/jayield)
with the Java
[Stream](https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html)
API and with some of the most popular functional style Java libraries,
such as [Guava](https://github.com/google/guava),
[StreamEx](https://github.com/amaembo/streamex),
[JOOL](https://github.com/jOOQ/jOOL),
[Cyclops](https://github.com/aol/cyclops-react/),
[Vavr](https://github.com/vavr-io/vavr), and
[Protonpack](https://github.com/poetix/protonpack).

We evaluated these libraries in a realistic use case based on data from the
[World Weather Online API](https://developer.worldweatheronline.com/api/) for a
small data set with past weather of Lisbon in February, March and April,
corresponding to 93 days of weather information and performing 3 different queries:
1. [QueryMaxTemperature](src/main/java/org/jayield/jmh/QueryMaxTemperature.java) - maximum temperature;
2. [QueryNrOfDistinctTemperatures](src/main/java/org/jayield/jmh/QueryNrOfDistinctTemperatures.java) - count the number of distinct temperatures;
3. [QueryNrOfTemperaturesTransitions](src/main/java/org/jayield/jmh/QueryNrOfTemperaturesTransitions.java) - count temperature transitions.

Each query results in a pipeline of:
1. `filter`-`skip`-`oddLines`-`mapToInt`-`max`;
2. `filter`-`skip`-`oddLines`-`map`-`distinct`-`count`;
3. `filter`-`skip`-`oddLines`-`map`-`collapse`-`count`.

Note that `oddLines` and `collapse` are user-defined custom operations according
to each library extension API.

The JAYield approach is is 2 fold faster than Java Stream on `QueryMaxTemperature`
and 3 fold faster on `QueryNrOfTemperaturesTransitions`, which combines two
custom operations (`oddLines()` and `collapse()`).

All other libraries also show worse performance than JAYield and furthermore,
none of them provides an extensible fluent API as JAYield.
