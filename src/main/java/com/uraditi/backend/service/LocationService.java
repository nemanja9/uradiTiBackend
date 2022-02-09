package com.uraditi.backend.service;

import com.uraditi.backend.dto.LocationDto;
import com.uraditi.backend.dto.TaskerCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<TaskerCategoryDto> sortTaskers(LocationDto base, List<TaskerCategoryDto> taskers) {
        var locations = taskers.stream().map(x -> new LocationDto(x.getLatitude(), x.getLongitude(), x.getId().toString())).collect(Collectors.toList());
        var distancesFromBase = new LinkedHashMap<TaskerCategoryDto, Double>(locations.size());
        locations.forEach(x -> {
            distancesFromBase.put(giveTaskerFromListById(taskers, x.getName()), Math.sqrt(Math.pow(x.getX() - base.getX(), 2) + Math.pow(x.getY() - base.getY(), 2)));
        });

        List<Map.Entry<TaskerCategoryDto, Double>> entries = new ArrayList<>(distancesFromBase.entrySet());
        Collections.sort(entries, Comparator.comparing(Map.Entry::getValue));
        Map<TaskerCategoryDto, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<TaskerCategoryDto, Double> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap.keySet().stream().collect(Collectors.toList());
    }

    private TaskerCategoryDto giveTaskerFromListById(List<TaskerCategoryDto> taskers, String taskerId) {
        for (TaskerCategoryDto tasker : taskers) {
            if (tasker.getId().toString().equals(taskerId)) {
                return tasker;
            }
        }
        return null;
    }
}
