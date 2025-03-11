package com.example.demo.application.usecase;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.service.MemberReadService;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostLikeWriteService;
import com.example.demo.domain.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostLikeUsecase {

    private final PostReadService postReadService;
    private final PostLikeWriteService postLikeWriteService;
    private final MemberReadService memberReadService;

    public void execute(Long memberId, Long postId) {
        Post post = postReadService.getPost(postId);
        MemberDto memberDto = memberReadService.getMember(memberId);
        postLikeWriteService.create(post, memberDto);
    }
}
