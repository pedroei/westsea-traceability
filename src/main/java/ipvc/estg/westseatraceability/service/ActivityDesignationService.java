package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.dto.ActivityDesignationDto;
import ipvc.estg.westseatraceability.dto.CreateActivityDesignationDto;
import ipvc.estg.westseatraceability.exception.BadRequestException;
import ipvc.estg.westseatraceability.exception.NotFoundException;
import ipvc.estg.westseatraceability.mapper.ActivityDesignationMapper;
import ipvc.estg.westseatraceability.model.ActivityDesignation;
import ipvc.estg.westseatraceability.repository.ActivityDesignationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityDesignationService {

    private final ActivityDesignationRepository activityDesignationRepository;
    private final ActivityDesignationMapper activityDesignationMapper;

    public List<ActivityDesignationDto> getAllActivityDesignations() {
        log.info("Fetching all activity designations");
        List<ActivityDesignation> activityDesignations = activityDesignationRepository.findAll();

        return activityDesignationMapper.activityDesignationListToDto(activityDesignations);
    }

    public ActivityDesignationDto getActivityDesignation(String id) {
        log.info("Fetching activity designation with id: " + id);

        return activityDesignationRepository
                .findById(id)
                .map(activityDesignationMapper::activityDesignationToDto)
                .orElseThrow(() -> new NotFoundException("Activity designation with id: " + id + " was not found"));
    }

    public ActivityDesignationDto createActivityDesignation(CreateActivityDesignationDto dto) {
        log.info("Creating new activity designation: {} ", dto.getDesignation());

        activityDesignationRepository.findByDesignation(dto.getDesignation())
                .ifPresent(user -> {
                    throw new BadRequestException("Activity designation with designation: " + user.getDesignation() + " already exists");
                });

        ActivityDesignation activityDesignation = activityDesignationMapper.createDtoToActivityDesignation(dto);
        ActivityDesignation newActivityDesignation = activityDesignationRepository.save(activityDesignation);

        return activityDesignationMapper.activityDesignationToDto(newActivityDesignation);
    }

    public ActivityDesignationDto updateActivityDesignation(String id, CreateActivityDesignationDto dto) {
        log.info("Updating activity designation with id: {} ", id);

        activityDesignationRepository.findByDesignation(dto.getDesignation())
                .ifPresent(user -> {
                    throw new BadRequestException("Activity designation with designation: " + user.getDesignation() + " already exists");
                });

        ActivityDesignation activityDesignation = activityDesignationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity designation with id: " + id + " was not found"));

        activityDesignation.setDesignation(dto.getDesignation());
        ActivityDesignation updatedActivityDesignation = activityDesignationRepository.save(activityDesignation);

        return activityDesignationMapper.activityDesignationToDto(updatedActivityDesignation);
    }

    public void deleteActivityDesignation(String id) {
        log.info("Deleting activity designation with id: {}", id);

        ActivityDesignation activityDesignation = activityDesignationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Activity designation with id: " + id + " was not found"));

        activityDesignationRepository.delete(activityDesignation);
    }
}
