package com.mrsalustiano.publication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publication {

    private String id;
    private String title;
    private String imageUrl;
    private String text;
    private List<Comment> comments;

}
