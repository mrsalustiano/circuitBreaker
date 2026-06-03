package com.mrsalustiano.publication.resilience;

import com.mrsalustiano.publication.domain.Comment;
import com.mrsalustiano.publication.service.ClientFeignService;
import com.mrsalustiano.publication.service.RedisService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentsResilienceClient {

    private final ClientFeignService feignService;

    private final RedisService redisService;

    @CircuitBreaker(name = "comments", fallbackMethod = "getCommentsFallback")
    public List<Comment> getComments(String publicationId) {
        var comments = feignService.getComments(publicationId);
        redisService.save(comments, publicationId);

        return comments;
    }

    public List<Comment> getCommentsFallback(String publicationId, CallNotPermittedException ex) {
        log.warn("Circuit breaker OPEN — publicationId={}", publicationId);
        var comments = redisService.findById(publicationId);
        if (comments != null) {
            log.warn("Recuperando comentarios do cache — publicationId={}", publicationId);
            return comments;
        }

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                ResilienceMessages.COMMENTS_CIRCUIT_OPEN
        );
    }

    public List<Comment> getCommentsFallback(String publicationId, Exception ex) {
        log.warn("Falha ao recuperar comentarios — publicationId={}, causa={}", publicationId, ex.toString());
        var comments = redisService.findById(publicationId);
        if (comments != null) {
            log.warn("Recuperando comentarios do cache — publicationId={}", publicationId);
            return comments;
        }
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ResilienceMessages.COMMENTS_RECOVERY_FAILED
        );
    }
}
