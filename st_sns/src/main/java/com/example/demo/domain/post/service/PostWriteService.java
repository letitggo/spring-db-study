package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;

    public PostDto create(PostCommand command) {
        Post saved = postRepository.save(Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build()
        );
        return PostDto.toDto(saved);
    }
}
