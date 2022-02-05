package com.uraditi.backend.service;

import com.uraditi.backend.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationService {

    public String calculateClosest(List<LocationDto> locations) {
        var base = locations.get(0);
        locations.remove(0);

        var distancesFromBase = new LinkedHashMap<String, Double>(locations.size());
        locations.forEach(x -> {
            distancesFromBase.put(x.getName(), Math.sqrt(Math.pow(x.getX() - base.getX(), 2) + Math.pow(x.getY() - base.getY(), 2)));
        });

        var lowestDistance = Collections.min(distancesFromBase.values());

        for (String s : distancesFromBase.keySet()) {
            if (distancesFromBase.get(s).equals(lowestDistance))
                return s;
        }

        return "N/A";
    }
}
