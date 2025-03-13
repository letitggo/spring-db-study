create table Member
(
    id int auto_increment,
    email varchar(20) not null,
    nickname varchar(20) not null,
    birthday date not null,
    createdAt datetime not null,
    constraint member_id_uindex
        primary key (id)
);

create table MemberNicknameHistory
(
    id int auto_increment,
    memberId int not null,
    nickname varchar(20) not null,
    createdAt datetime not null,
    constraint memberNicknameHistory_id_uindex
        primary key (id)
);

create table Follow
(
    id int auto_increment,
    fromMemberId int not null,
    toMemberId int not null,
    createdAt datetime not null,
    constraint Follow_id_uindex
        primary key (id)
);

create unique index Follow_fromMemberId_toMemberId_uindex
    on Follow (fromMemberId, toMemberId);


create table POST
(
    id int auto_increment,
    memberId int not null,
    contents varchar(100) not null,
    createdDate date not null,
    createdAt datetime not null,
    constraint POST_id_uindex
        primary key (id)
);


# 인덱스를 어떤 것으로 잡느냐에 따라 성능이 좌지우지
# 고려사항
# 데이터 분포, 어떤 컬럼이 GROUP BY, ORDER BY 등에 들어가는지 모두 고려
create index POST__index_member_id
    on POST (member_id);

create index POST__index_created_date
    on POST (created_date);

create index POST__index_member_id_created_date
    on POST (member_id, created_date);

create table Timeline
(
    id int auto_increment,
    memberId int not null,
    posId int not null,
    createdAt datetime not null,
    constraint Timeline_id_uindex
    primary key (id)
);

/*
mysql에서 락은 row가 아니라 인덱스를 잠근다
따라서 인덱스가 없는 조건으로 Locking read시 불필요한 데이터들이 잠길 수 있다.

추가 학습 :
1. java에서의 동시성 이슈 제어 방법
2. 분산환경(msa, 서버나 db가 여러개)에서의 동시성 이슈 제어 방법
3. MySQL의 넥스트 키락이 등장한 배경
4. MySQL 외래키로 인한 잠금
5. MySQL 데드락
*/
start transaction;
select *
from POST
where memberId = 3 and createdAt = '2025-03-10 22:09:09' for update;
commit;

select * from performance_schema.data_locks;
select * from information_schema.innodb_trx;


