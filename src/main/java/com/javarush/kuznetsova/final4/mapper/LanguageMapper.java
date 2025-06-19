package com.javarush.kuznetsova.final4.mapper;

import com.javarush.kuznetsova.final4.entity.CountryLanguage;
import com.javarush.kuznetsova.final4.redis.Language;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface LanguageMapper {

    Language toLanguage(CountryLanguage countryLanguage);

    Set<Language> map(Set<CountryLanguage> languages);
}
