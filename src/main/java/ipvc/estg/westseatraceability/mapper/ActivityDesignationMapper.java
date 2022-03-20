package ipvc.estg.westseatraceability.mapper;

import ipvc.estg.westseatraceability.dto.ActivityDesignationDto;
import ipvc.estg.westseatraceability.dto.CreateActivityDesignationDto;
import ipvc.estg.westseatraceability.model.ActivityDesignation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityDesignationMapper {

    public ActivityDesignation createDtoToActivityDesignation(CreateActivityDesignationDto dto) {
        ActivityDesignation activityDesignation = new ActivityDesignation();
        activityDesignation.setDesignation(dto.getDesignation());

        return activityDesignation;
    }

    public ActivityDesignationDto activityDesignationToDto(ActivityDesignation activityDesignation) {
        return ActivityDesignationDto.builder()
                .id(activityDesignation.getId())
                .designation(activityDesignation.getDesignation())
                .build();
    }

    public List<ActivityDesignationDto> activityDesignationListToDto(List<ActivityDesignation> activityDesignations) {
        return activityDesignations.stream().map(this::activityDesignationToDto).collect(Collectors.toList());
    }
}
