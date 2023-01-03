package fr.schoolbyhiit.portailformation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {

    private Long id;

    private String name;

    private List<CourseDto> courses;
}
