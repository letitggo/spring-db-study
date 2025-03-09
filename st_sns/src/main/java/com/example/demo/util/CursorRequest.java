package com.example.demo.util;

public record CursorRequest(
        // 커서 키는 유니크함이 보장되어야 한다.
        // 중복 키가 발생하면 데이터를 읽다가 끊길 수 있다.
        Long key,
        int size
) {
    // 데이터가 더 이상 없다는 것을 알려주기 위함. 계속해서 서버에 요청을 보내는 것을 방지
    public static final Long NONE_KEY = -1L;

    public Boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
