package ipvc.estg.westseatraceability.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "westseatraceapi")
public interface BlockchainControllerContract {
    //FIXME: Swagger

    ResponseEntity<List<ProductLot>> getProductLots();

    ResponseEntity<List<Activity>> getActivities();

    ResponseEntity<List<Activity>> getTraceability(String referenceNumber);

    ResponseEntity<String> createProductLot(CreateProductLotDto productLotDto);

    ResponseEntity<String> createActivity(CreateActivityDto activityDto);
}
