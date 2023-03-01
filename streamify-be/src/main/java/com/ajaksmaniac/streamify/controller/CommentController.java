package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.exception.user.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.user.VideoNotFoundException;
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
    public ResponseEntity<String> saveComment(@RequestBody CommentDto comment){
        try {
            commentService.saveComment(comment);
            return ResponseEntity.ok("Comment saved");

        }catch (UserNotExistantException | VideoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id){
            commentService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Comment Deleted");
    }
}
