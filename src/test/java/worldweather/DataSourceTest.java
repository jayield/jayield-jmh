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

import org.testng.annotations.Test;
import util.FileRequest;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Miguel Gamboa
 *         created on 01-08-2016
 */
public class DataSourceTest {

    @Test
    public void testFileDataSourcePastWeather() {
        WeatherWebApi dataSrc = new WeatherWebApi(new FileRequest());
        Supplier<Stream<WeatherInfoDto>> weather = () -> dataSrc.pastWeather(
                41.15, -8.6167, of(2017,2,1), of(2017,4,30));
        /*
         * Get weather history for February
         */
        Stream<WeatherInfoDto> february = dataSrc.pastWeather(
                41.15, -8.6167, of(2017,2,1), of(2017,2, 28));
        /*
         * Get weather history for March
         */
        Stream<WeatherInfoDto> march = dataSrc.pastWeather(
                41.15, -8.6167, of(2017,3,1), of(2017,3, 31));
        /*
         * Get weather history for April
         */
        Stream<WeatherInfoDto> april= dataSrc.pastWeather(
                41.15, -8.6167, of(2017,4,1), of(2017,4, 30));
        /*
         * Assert
         */
        assertEquals(weather.get().count(), 89);
        assertEquals(february.mapToInt(WeatherInfoDto::getTempC).max().getAsInt(), 21);
        assertEquals(march.mapToInt(WeatherInfoDto::getTempC).max().getAsInt(), 26);
        assertEquals(april.mapToInt(WeatherInfoDto::getTempC).max().getAsInt(), 27);
        assertEquals(weather.get().mapToInt(WeatherInfoDto::getTempC).max().getAsInt(), 27);
    }
}
