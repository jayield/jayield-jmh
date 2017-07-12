# jayield-jmh

Follow the procedure to run JMH tests:

    $ mvn clean install
    $ java -jar /target/benchmarks.jar

We evaluate our JAYield proposal with a realistic use case based on data from the
[World Weather Online API](https://developer.worldweatheronline.com/api/),
where our approach is 2 fold faster than a `Spliterator<T>` implementation for sequential
traversing for maximum temperature query and 3 fold faster for number of temperatures transitions
query, which combines two custom operations (`oddLines()` and `collapse()`).

In this work, we analyze some of the most popular functional style Java libraries, such as Guava,
StreamEx, JOOL, Cyclops, Vavr, and Protonpack.
All of them show worse performance than JAYield and furthermore, none of them provides a fluent and 
extensible API as JAYield.
