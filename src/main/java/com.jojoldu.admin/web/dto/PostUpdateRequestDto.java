package com.jojoldu.admin.web.dto;

import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    String title;
    String content;

    @Builder
    public PostUpdateRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
