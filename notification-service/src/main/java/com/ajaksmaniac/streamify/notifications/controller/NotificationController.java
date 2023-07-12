package com.ajaksmaniac.streamify.notifications.controller;

import com.ajaksmaniac.streamify.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    @Qualifier("notificationServiceImplementation")
    private NotificationService notificationService;

    @DeleteMapping
    public ResponseEntity deleteNotification(@RequestHeader("id") Long id){
        notificationService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

}
