package com.ajaksmaniac.streamify.exception.channel;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus
//@NoArgsConstructor
public class ChannelNotFoundException extends RuntimeException{


}
