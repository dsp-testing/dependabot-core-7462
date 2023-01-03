package fr.schoolbyhiit.portailformation.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import fr.schoolbyhiit.portailformation.web.dto.ModuleDto;
import fr.schoolbyhiit.portailformation.model.Module;
import org.mapstruct.MappingTarget;

@Mapper(uses = CourseMapper.class, componentModel = "spring")
public interface ModuleMapper {

    ModuleDto toDto(Module module);

    @InheritInverseConfiguration
    Module toEntity(ModuleDto moduleDto);

    List<ModuleDto> toDtoList(List<Module> entityList);

    List<Module> toEntityList(List<ModuleDto> dtoList);

    void updateEntityFromDto(ModuleDto dto, @MappingTarget Module entity);
}
