/*
 * Copyright (c) 2016, jasync.org
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

/**
 * @author Miguel Gamboa
 *         created on 14-09-2016
 */
public class LocationDto {
    private final String country;
    private final String region;
    private final double latitude;
    private final double longitude;

    public LocationDto(String country, String region, double latitude, double longitude) {
        this.country = country;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static LocationDto valueOf(String line) {
        String[] data = line.split("\t");
        return new LocationDto(
                data[1],
                data[2],
                Double.parseDouble(data[3]),
                Double.parseDouble(data[4]));
    }

}
