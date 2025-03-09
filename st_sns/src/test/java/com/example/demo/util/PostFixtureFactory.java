package com.example.demo.util;

import com.example.demo.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {

    public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {
        Predicate<Field> idPredicated = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        Predicate<Field> memberIdPredicated = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        EasyRandomParameters params = new EasyRandomParameters()
                .excludeField(idPredicated)
                .dateRange(firstDate, lastDate)
                .randomize(memberIdPredicated, () -> memberId);

        return new EasyRandom(params);
    }

}
