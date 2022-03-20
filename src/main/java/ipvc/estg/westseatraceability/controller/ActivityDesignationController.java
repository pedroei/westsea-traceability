package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.dto.ActivityDesignationDto;
import ipvc.estg.westseatraceability.dto.CreateActivityDesignationDto;
import ipvc.estg.westseatraceability.service.ActivityDesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-designation")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ActivityDesignationController implements ActivityDesignationControllerContract {

    private final ActivityDesignationService activityDesignationService;

    @Override
    @PostMapping
    public ResponseEntity<ActivityDesignationDto> create(@RequestBody CreateActivityDesignationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityDesignationService.createActivityDesignation(dto));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ActivityDesignationDto> get(@PathVariable String id) {
        return ResponseEntity.ok().body(activityDesignationService.getActivityDesignation(id));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<ActivityDesignationDto>> getAll() {
        return ResponseEntity.ok().body(activityDesignationService.getAllActivityDesignations());
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<ActivityDesignationDto> update(@PathVariable String id, @RequestBody CreateActivityDesignationDto dto) {
        return ResponseEntity.ok().body(activityDesignationService.updateActivityDesignation(id, dto));
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        activityDesignationService.deleteActivityDesignation(id);
    }
}
