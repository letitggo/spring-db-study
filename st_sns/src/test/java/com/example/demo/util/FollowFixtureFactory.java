package com.example.demo.util;

import com.example.demo.domain.follow.entity.Follow;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class FollowFixtureFactory {

    // 팔로워 만들기
    public static EasyRandom getByToMemberId(Long toMemberId) {
        Predicate<Field> idPredicated = named("id")
                .and(ofType(Long.class))
                .and(inClass(Follow.class));

        Predicate<Field> toMemberIdPredicated = named("toMemberId")
                .and(ofType(Long.class))
                .and(inClass(Follow.class));

        Predicate<Field> fromMemberIdPredicated = named("fromMemberId")
                .and(ofType(Long.class))
                .and(inClass(Follow.class));

        EasyRandomParameters params = new EasyRandomParameters()
                .excludeField(idPredicated)
                .randomize(toMemberIdPredicated, () -> toMemberId)
                // long, int 범위를 지정할 수 없나...?
                .randomize(fromMemberIdPredicated, () -> (long)((Math.random() * 1000000) + 1));

        return new EasyRandom(params);
    }

    // 팔로우하기
    public static EasyRandom getByFromMemberId(Long fromMemberId) {
        Predicate<Field> idPredicated = named("id")
                .and(ofType(Long.class))
                .and(inClass(Follow.class));

        Predicate<Field> fromMemberIdPredicated = named("fromMemberId")
                .and(ofType(Long.class))
                .and(inClass(Follow.class));

        Predicate<Field> toMemberIdPredicated = named("toMemberId")
                .and(ofType(Long.class))
                .and(inClass(Follow.class));

        EasyRandomParameters params = new EasyRandomParameters()
                .excludeField(idPredicated)
                .randomize(fromMemberIdPredicated, () -> fromMemberId)
                // long, int 범위를 지정할 수 없나...?
                .randomize(toMemberIdPredicated, () -> (long)((Math.random() * 1000000) + 1));

        return new EasyRandom(params);
    }
}
