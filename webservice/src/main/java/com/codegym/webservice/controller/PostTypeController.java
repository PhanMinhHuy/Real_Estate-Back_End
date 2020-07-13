package com.codegym.webservice.controller;

import com.codegym.dao.model.PostType;
import com.codegym.service.PostTypeService;
import com.codegym.webservice.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/postTypes")
public class PostTypeController {
    private PostTypeService postTypeService;

    @Autowired
    public void setPostTypeService(PostTypeService postTypeService) {
        this.postTypeService = postTypeService;
    }

    @GetMapping
    public ResponseEntity<Object> findAllPostType() {
        return new ResponseEntity<>(postTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findPostTypeById(@PathVariable Long id) {
        PostType postType = postTypeService.findById(id);
        if (postType == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this post type!"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(postType, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPostType(@RequestBody PostType postType) {
        postTypeService.save(postType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postType.getId()).toUri();
        return ResponseEntity.created(location)
                .body(postType);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updatePostType(@PathVariable Long id, @RequestBody PostType postType) {
        postType.setId(id);
        if (postTypeService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this post type!"), HttpStatus.NOT_FOUND);
        }
        postTypeService.save(postType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postType.getId()).toUri();
        return ResponseEntity.created(location)
                .body(postType);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePostType(@PathVariable Long id) {
        PostType postType = postTypeService.findById(id);
        if (postType == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find post type!"), HttpStatus.NOT_FOUND);
        } else {
            postTypeService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(true, "Delete post type successfully!"), HttpStatus.OK);
        }
    }
}