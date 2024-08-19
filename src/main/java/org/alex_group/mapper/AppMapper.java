package org.alex_group.mapper;

import org.alex_group.dto.CarDTO;
import org.alex_group.model.cars.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppMapper {
    AppMapper INSTANCE = Mappers.getMapper(AppMapper.class);

    Car toCar(CarDTO carDTO);

    CarDTO toCarDto(Car car);
}
