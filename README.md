# sns 프로젝트로 MySQL을 이해하고 대용량 데이터 처리를 정복하자
<h2>1. st_sns</h2>
<h3>1-1 nickname 변경 히스토리</h3>
  <table>
    <thead>member table</thead>
    <tbody>
      <tr><td>id</td><td>email</td><td>nickname</td><td>...</td></tr>  
    </tbody>
  </table>
  <table>
    <thead>nickname_history</thead>
    <tbody>
      <tr><td>id</td><td>member_id</td><td>nickname</td><td>...</td></tr>  
    </tbody>
  </table>
  처럼 필드의 이름(nickname)이 같다고 중복이 아니다.
  과거의 정보를 가지고 있어야하는 <b>히스토리성 데이터</b>는 정규화의 대상이 아니다.<br>
  정규화의 대상이 <b style="text-decoration: underline">항상 데이터의 최신성을 유지해야하는가</b>를 고려하자
<h3>1-2 팔로우</h3>
정규화 : 팔로우 관계 저장 테이블 분리(데이터 최신성 유지 필요)<br>
경계간 통신 방법<br>
<ul>
  <li>1. 헥사고날 아키텍쳐(포트-어댑터 패턴) - 포트를 통해 도메인 계층과 외부 시스템 연결</li>
  <li>2. DDD(Domain-Driven Design)</li>
  <li>3. 이벤트 드리븐 아키텍쳐</li>
  <li>4. Api Gateway</li>
  <li>5. CQRS(Command Query Responsibility Segregation) 등</li>
</ul>

<h3></h3>



