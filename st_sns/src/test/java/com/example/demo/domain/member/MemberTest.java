package com.example.demo.domain.member;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    void changeNickname() {
        Member member = MemberFixtureFactory.create();
        String expected = "hs";

        member.changeNickname(expected);

        Assertions.assertEquals(member.getNickname(), expected);
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    void testNicknameLength() {
        Member member = MemberFixtureFactory.create();
        String over = "hshshshshshs";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> member.changeNickname(over)
        );
    }
}
