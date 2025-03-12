package com.example.demo.util;

import com.example.demo.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class MemberFixtureFactory {

    public static Member create() {
        EasyRandomParameters param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static Member create(Long seed) {
        EasyRandomParameters param = new EasyRandomParameters()
                .seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static EasyRandom get(LocalDate firstDateTime, LocalDate lastDateTime) {
        Predicate<Field> idPredicated = named("id")
                .and(ofType(Long.class))
                .and(inClass(Member.class));

        EasyRandomParameters params = new EasyRandomParameters()
                .excludeField(idPredicated)
                .dateRange(firstDateTime, lastDateTime)
                .stringLengthRange(5, 10);

        return new EasyRandom(params);
    }
}
