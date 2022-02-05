package com.uraditi.backend.controller;

import com.uraditi.backend.dto.LocationDto;
import com.uraditi.backend.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("Geo location controller")
@RequestMapping("/api/location")
@Tag(name = "Location controller", description = "Public location operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/calculate-closest")
    public ResponseEntity<String> calculateClosest(@RequestBody List<LocationDto> locations) {
        return ResponseEntity.ok(locationService.calculateClosest(locations));
    }

}
