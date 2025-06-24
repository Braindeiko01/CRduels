package com.crduels.domain.entity.partida;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ModoJuegoListConverter implements AttributeConverter<List<ModoJuego>, String> {

    @Override
    public String convertToDatabaseColumn(List<ModoJuego> modos) {
        return modos != null ? modos.stream().map(Enum::name).collect(Collectors.joining(",")) : "";
    }

    @Override
    public List<ModoJuego> convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isEmpty()
                ? Arrays.stream(dbData.split(","))
                .map(String::trim)
                .map(ModoJuego::valueOf)
                .toList()
                : new ArrayList<>();
    }
}