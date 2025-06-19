package com.javarush.kuznetsova.final4.mapper;

import com.javarush.kuznetsova.final4.entity.City;
import com.javarush.kuznetsova.final4.redis.CityCountry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = LanguageMapper.class)
public interface CityCountryMapper {
    CityCountryMapper INSTANCE = Mappers.getMapper(CityCountryMapper.class);

    @Mapping(source = "country.code", target = "countryCode")
    @Mapping(source = "country.alternativeCode", target = "alternativeCountryCode")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.continent", target = "continent")
    @Mapping(source = "country.region", target = "countryRegion")
    @Mapping(source = "country.surfaceArea", target = "countrySurfaceArea")
    @Mapping(source = "country.population", target = "countryPopulation")
    @Mapping(source = "country.languages", target = "languages")
    CityCountry toCityCountry(City city);

    List<CityCountry> toCityCountryList(List<City> cities);
}
