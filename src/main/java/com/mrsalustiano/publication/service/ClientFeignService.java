package com.mrsalustiano.publication.service;

import com.mrsalustiano.publication.domain.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ClientFeignService", url = "${client.comments.url}")
public interface ClientFeignService {

    @GetMapping("/comments/{publicationId}")
    List<Comment> getComments(@PathVariable("publicationId")  String publicationId);

}
