package fr.schoolbyhiit.portailformation.web;

import fr.schoolbyhiit.portailformation.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fr.schoolbyhiit.portailformation.web.dto.ModuleDto;

import java.util.List;

@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService service;

    @GetMapping
    public Page<ModuleDto> findAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Sort sort) {
        if (pageable.isUnpaged()) {
            List<ModuleDto> modules = service.findAll();
            return new PageImpl<>(modules, pageable, modules.size());
        } else {
            return service.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        }
    }

    @GetMapping("/{id}")
    public ModuleDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModuleDto create(@RequestBody @Valid ModuleDto moduleDto) {
        return service.create(moduleDto);
    }

    @PutMapping("/{id}")
    public ModuleDto update(@PathVariable Long id, @RequestBody @Valid ModuleDto moduleDto) {
        return service.update(id, moduleDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}