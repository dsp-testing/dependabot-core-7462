package fr.schoolbyhiit.portailformation.mapper;

import fr.schoolbyhiit.portailformation.model.Course;
import fr.schoolbyhiit.portailformation.web.dto.CourseDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "module", ignore = true)
    CourseDto toDto(Course course);

    @InheritInverseConfiguration
    Course toEntity(CourseDto dto);

    List<CourseDto> toDtoList(List<Course> entityList);

    List<Course> toEntityList(List<CourseDto> dtoList);
}
