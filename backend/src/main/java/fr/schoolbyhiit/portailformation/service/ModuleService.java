package fr.schoolbyhiit.portailformation.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import fr.schoolbyhiit.portailformation.mapper.CourseMapper;
import fr.schoolbyhiit.portailformation.model.Course;
import fr.schoolbyhiit.portailformation.web.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.schoolbyhiit.portailformation.exception.EntityNotFoundException;
import fr.schoolbyhiit.portailformation.mapper.ModuleMapper;
import fr.schoolbyhiit.portailformation.model.dao.ModuleRepository;
import fr.schoolbyhiit.portailformation.model.Module;

import fr.schoolbyhiit.portailformation.web.dto.ModuleDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository repository;
    private final ModuleMapper mapper;
    private final CourseMapper courseMapper;


    public List<ModuleDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<ModuleDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public ModuleDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public ModuleDto create(ModuleDto dto) {
        Module entity = mapper.toEntity(dto);
        entity.getCourses().forEach(c -> c.setModule(entity));
        return mapper.toDto(repository.save(entity));
    }

    public ModuleDto update(Long id, ModuleDto dto) {
        Module entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        mapper.updateEntityFromDto(dto, entity);
        entity.getCourses().forEach(c -> c.setModule(entity));
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<CourseDto> findCoursesByModuleId(Long moduleId) {
        List<CourseDto> collect = repository.findById(moduleId)
                .map(Module::getCourses)
                .orElse(Collections.emptyList())
                .stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        return collect;
    }

    public CourseDto addCourseToModule(Long moduleId, CourseDto dto) {
        Module module = repository.findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException(moduleId));
        Course course = courseMapper.toEntity(dto);
        course.setModule(module);
        module.getCourses().add(course);
        repository.save(module);
        return courseMapper.toDto(course);
    }
}
