package com.example.demo.domain.follow.service;

import com.example.demo.domain.follow.dto.FollowDto;
import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowReadService {

    private final FollowRepository followRepository;

    public List<FollowDto> getFollowings(Long fromMemberId) {
        List<Follow> followings = followRepository.findAllByFromMemberId(fromMemberId);
        return followings
                .stream()
                .map(FollowDto::toDto)
                .toList();
    }

    public List<FollowDto> getFollowers(Long toMemberId) {
        List<Follow> followings = followRepository.findAllByToMemberId(toMemberId);
        return followings
                .stream()
                .map(FollowDto::toDto)
                .toList();
    }
}
