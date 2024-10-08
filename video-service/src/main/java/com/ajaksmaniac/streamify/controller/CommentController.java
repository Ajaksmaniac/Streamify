package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
@AllArgsConstructor
@CrossOrigin
public class CommentController {

    @Autowired
    @Qualifier(value = "commentServiceImplementation")
    private CommentService commentService;


    @GetMapping("{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForVideo(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.getCommentsForVideo(id));
    }

    @PostMapping
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto comment, @RequestHeader("x-auth-user-id") Long authenticatedUser){


            return ResponseEntity.ok(commentService.saveComment(comment,authenticatedUser));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id, @RequestHeader("x-auth-user-id") Long authenticatedUser){
            commentService.deleteById(id,authenticatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Comment with id %d successfully deleted", id));
    }
}
