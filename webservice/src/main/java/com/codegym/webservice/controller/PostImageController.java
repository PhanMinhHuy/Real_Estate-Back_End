package com.codegym.webservice.controller;

import com.codegym.dao.model.PostImage;
import com.codegym.service.PostImageService;
import com.codegym.webservice.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/postImages")
public class PostImageController {
    private PostImageService postImageService;

    @Autowired
    public void setPostImageService(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    @GetMapping
    public ResponseEntity<Object> findAllPostImages(Pageable pageable) {
        return new ResponseEntity<>(postImageService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findPostImageById(@PathVariable Long id) {
        PostImage postImage = postImageService.findById(id);
        if (postImage == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this post image!"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(postImage, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPostImage(@RequestBody PostImage postImage) {
        postImageService.save(postImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postImage.getId()).toUri();
        return ResponseEntity.created(location)
                .body(postImage);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updatePostImage(@PathVariable Long id, @RequestBody PostImage postImage) {
        postImage.setId(id);
        if (postImageService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this post image!"), HttpStatus.NOT_FOUND);
        }
        postImageService.save(postImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postImage.getId()).toUri();
        return ResponseEntity.created(location)
                .body(postImage);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePostImage(@PathVariable Long id) {
//        PostImage postImage = postImageService.findById(id);
//        if (postImage == null){
//            return new ResponseEntity<>(new ApiResponse(false, "Can not find post image!"), HttpStatus.NOT_FOUND);
//        } else {
//            postImageService.deleteById(id);
//            return new ResponseEntity<>(new ApiResponse(true, "Delete post image successfully!"), HttpStatus.OK);
//        }
        postImageService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete post image successfully!"), HttpStatus.OK);
    }
}