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

package worldweather;

import util.IteratorFromInputStream;
import util.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.stream.StreamSupport.stream;

/**
 * Provides Weather information in a String format that is compromised with
 * the CSV and Tabular format provided by the api.worldweatheronline.com.
 *
 * @author Miguel Gamboa
 *         created on 01-08-2016
 */
public class WeatherWebApi {

    private static final String WEATHER_TOKEN;
    private static final String WEATHER_HOST = "http://api.worldweatheronline.com";
    private static final String WEATHER_PAST = "/premium/v1/past-weather.ashx";
    private static final String WEATHER_PAST_ARGS =
            "?q=%s&date=%s&enddate=%s&tp=24&format=csv&key=%s";
    private static final String WEATHER_SEARCH="/premium/v1/search.ashx?query=%s";
    private static final String WEATHER_SEARCH_ARGS="&format=tab&key=%s";

    static {
        try {
            URL keyFile = getSystemResource("worldweatheronline-app-key.txt");
            if(keyFile == null) {
                /**
                 * If you GOT a KEY from developer.worldweatheronline.com,
                 * then place it in src/main/resources/worldweatheronline-app-key.txt"
                 */
                WEATHER_TOKEN = "";
            } else {
                InputStream keyStream = keyFile.openStream();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(keyStream))) {
                    WEATHER_TOKEN = reader.readLine();
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private final Request req;


    public WeatherWebApi(Request req) {
        this.req = req;
    }

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/search.ashx?query=oporto&format=tab&key=*****
     *
     * Reads the locations matching a given query. The response is in Tabular format.
     * Thus, the data items fields are separated by tabs.
     * For instance, for a query oporto it should return, at least:
     *
     * <pre> {@code
     *     Oporto	Cuba	Santiago de Cuba	20.233	-76.167	0
     *     Oporto	Portugal	Porto	41.150	-8.617	0
     * }</pre>
     *
     * Corresponding to the following data fields: AreaName, Country,
     * Region (if available), Latitude and Longitude.
     */

    public Stream<LocationDto> search(String query) {
        String uri = WEATHER_HOST + WEATHER_SEARCH + WEATHER_SEARCH_ARGS;
        String path = String.format(uri, query, WEATHER_TOKEN);
        Spliterator<String> body = new IteratorFromInputStream(req.getBody(path));
        return stream(body, false)
                .filter(s -> !s.startsWith("#"))
                .map(LocationDto::valueOf);

    }

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=41.15,-8.6167&date=2017-02-01&enddate=2017-02-28&tp=24&format=csv&key=*****
     *
     * Reads the weather history for the location specified by the latitude and
     * longitude values of arguments lat and log.
     * It selects an interval corresponding to all days of the previous month.
     * The response is in CSV format, corresponding to the following rules:
     * <ul>
     *     <li>Lines beginning with # are comments</li>
     *     <li>Data lines are interleaved between Hourly and Daily info.</li>
     * </ul>
     */
    public Stream<WeatherInfoDto> pastWeather(
            double lat,
            double log,
            LocalDate from,
            LocalDate to
    ) {
        String query = lat + "," + log;
        String args = String.format(WEATHER_PAST_ARGS, query, from, to, WEATHER_TOKEN);
        String path = WEATHER_HOST + WEATHER_PAST + args;
        Spliterator<String> body = new IteratorFromInputStream(req.getBody(path));
        final boolean [] isOddLine = { true };
        final Predicate<String> oddLine = item -> {
            isOddLine[0] = !isOddLine[0];
            return isOddLine[0];
        };
        return stream(body, false)
                .filter(s->!s.startsWith("#"))
                .skip(1)	                   // Skip line: Not Available
                .filter(oddLine)               //even lines filter
                .map(WeatherInfoDto::valueOf); //to weatherinfo objects
    }
}
