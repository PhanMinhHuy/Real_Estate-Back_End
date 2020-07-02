package com.codegym.webservice.controller;

import com.codegym.dao.model.Region;
import com.codegym.service.RegionService;
import com.codegym.webservice.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/regions")
public class RegionController {
    private RegionService regionService;

    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<Object> findAllRegion() {
        return new ResponseEntity<>(regionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findRegionById(@PathVariable Long id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this region!"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(region, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRegion(@RequestBody Region region) {
        regionService.save(region);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(region.getId()).toUri();
        return ResponseEntity.created(location)
                .body(region);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        region.setId(id);
        if (regionService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this region!"), HttpStatus.NOT_FOUND);
        }
        regionService.save(region);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(region.getId()).toUri();
        return ResponseEntity.created(location)
                .body(region);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteRegion(@PathVariable Long id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find region!"), HttpStatus.NOT_FOUND);
        } else {
            regionService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(true, "Delete region successfully!"), HttpStatus.OK);
        }
    }
}
