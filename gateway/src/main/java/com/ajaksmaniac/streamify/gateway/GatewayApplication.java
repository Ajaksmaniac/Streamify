package com.ajaksmaniac.streamify.gateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.FormHttpMessageReader;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;


@SpringBootApplication
@Slf4j
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()

//				//==========VIDEO===========
//				.route("GET_VIDEO_DETAILS_BY_ID", r -> r.path("/video/details/id/**")
//						.filters(f -> f.rewritePath("/video/details/id/(?<segment>.*)", "/video/details/id/${segment}"))
//						.uri("http://localhost:8081"))
//				.route("POST_VIDEO", r -> r
//						.path("/video")
//						.and()
//						.method(HttpMethod.POST)
//						.filters(f -> f.rewritePath("/video", "/video")
//								.addRequestHeader("Authorization", HttpHeaders.AUTHORIZATION))
//						.uri("http://localhost:8081")
//				)
//				.route("DELETE_VIDEO", r -> r
//						.path("/video/id/**")
//						.and()
//						.method(HttpMethod.DELETE)
//						.filters(f -> f.rewritePath("/video/id/(?<segment>.*)", "/video/id/${segment}")
//								.addRequestHeader("Authorization", HttpHeaders.AUTHORIZATION))
//						.uri("http://localhost:8081")
//				)
				.route("GET_VIDEO_BY_ID", r -> r.path("/video/id/**")
						.and()
						.method(HttpMethod.GET)
						.filters(f -> f.rewritePath("/video/id/(?<segment>.*)", "/video/id/${segment}"))
						.uri("lb://STREAMIFY-VIDEO-SERVICE"))
//				.route("GET_VIDEO_DETAILS_LIST_BY_CHANNEL_ID", r -> r.path("/video/channel/id/**")
//						.filters(f -> f.rewritePath("/video/channel/id/(?<segment>.*)", "/video/channel/id/${segment}"))
//						.uri("http://localhost:8081"))
//				.route("GET_ALL_VIDEO_DETAILS", r -> r.path("/video/details")
////						.filters(f -> f.rewritePath("/video/channel/id/(?<segment>.*)", "/video/channel/id/${segment}"))
//						.uri("http://localhost:8081"))
//
//				//==========CHANNEL===========
//				.route("GET_CHANNEL_BY_ID", r -> r.path("/channel/id/**")
//						.filters(f -> f.rewritePath("/channel/id/(?<segment>.*)", "/channel/id/${segment}"))
//						.uri("http://localhost:8081"))
//				.route("GET_USER_CHANNELS_BY_ID", r -> r.path("/channel/user/id/**")
//						.filters(f -> f.rewritePath("/channel/user/id/(?<segment>.*)", "/channel/user/id/${segment}"))
//						.uri("http://localhost:8081"))
//
//				//==========COMMENT===========
//				.route("GET_VIDEO_COMMENTS_BY_ID", r -> r.path("/comment/video/**")
//						.filters(f -> f.rewritePath("/comment/video/(?<segment>.*)", "/comment/video/${segment}"))
//						.uri("http://localhost:8081"))
//
//				//==========USER===========
//				.route("GET_USER_BY_ID", r -> r.path("/user/**")
//						.filters(f -> f.rewritePath("/user/(?<segment>.*)", "/user/${segment}"))
//						.uri("http://localhost:8081"))
//
//
//				//==========AUTH===========
//				.route("POST_LOGIN", r -> r.path("/auth/login")
//						.and()
//						.method(HttpMethod.POST)
//						.filters(f -> f.rewritePath("/auth/login", "/auth/login")
//								.addRequestHeader("Authorization", HttpHeaders.AUTHORIZATION))
//							.uri("http://localhost:8081"))
				.build();

	}
	@Bean
	public GatewayFilter logRequestFilter() {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			log.info("Received request - URL: {}, Method: {}, Headers: {}", request.getURI(), request.getMethod(), request.getHeaders());
			return chain.filter(exchange);
		};
	}


}
