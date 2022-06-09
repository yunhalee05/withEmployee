<div align="center">
    <br/>
    <h1><strong><em>🏢 With Employee</em></strong></h1>
    <img width="800" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139523620-74b53fa0-853d-4da8-84ec-13ad551a2514.jpg">
    <br/>
    <br/>
    <p>
      With Employee는 <strong><em>직원과의 소통,협력</em></strong> 을 위하여 만들어진 앱입니다.<br/> 
      일상 생활과 구분되는 업무차원의 메세지 전달이 가능하며, 다른 업체에게 손쉽에 협력요청을 하기 위해서 만들어졌습니다.<br/>
      팀을 구성하는 팀 멤버와 실시간 메세지를 주고 받을 수 있고, 다른 팀의 같은 회사 멤버와 대화를 할 수 있습니다.<br/>
      등록된 회사 목록을 검색하고, 원하는 회사 대표계정으로 연락을 취할 수 있습니다. <br/>
    </p>
    <br/>
    <br/>
    <a href="https://with-employee-with-you.herokuapp.com/"><strong>with-employee-with-you</strong></a>
</div>
    <br/>
    <br/>
    <br/>
<div>
    <h2>🚀 Shortcut</h2>
<div> 
        
- [__Improvement__](#improvement)
- [__Challenges__](#challenges)
- [__Tech Stack Used__](#tech)
- [__Implementation__](#implementation)
- [__Features__](#feature)
- [__Entity Relationship Diagram__](#erd)
- [__Structure__](#structure)

</div>
    <br/>
    <br/>
    <br/>
<div>
    <h2 id="improvement">🍕 Improvement</h2>
    <ul>
      <li>
        <h3>✔️ SpringBoot, React, MySQL & MVC pattern </h3> 
        <p>
          전체적인 프로젝트는 <strong><em>SpringBoot, React, MySQL</em></strong> 을 이용하여 구현하였습니다. 서버는 자바 기반의 프레임워크 SpringBoot, 프론트는 자바스크립트 기반의 라이브러리 react를 사용하여 구성하였으며 프로젝트를 통해 실행되는 포트가 다른 서버와 프론트엔드에서 어떻게 소통하는지를 이해하게 되었습니다. 
          또한 MySQL과 같은 관계형 데이터베이스(RDBMS)에서의 데이터 저장 구조를 배웠습니다. noSQL과는 다르게 관계형 데이터베이스에서는 서로 다른 엔티티(모델)을 참조하기 위해서 연관관계를 맺어야 하고, 이를 통해 다대일, 일대다, 다대다, 일대일의 연관관계 매핑을 배우게 되었습니다.</br>
          전체적인 프로젝트의 진행은 프론트엔드에서 allowedOrigin된 서버로 요청을 넘겨주면 서버는 스키마구조에 맞는 데이터 정보처리를 MySQL에게 부탁하여 데이터를 받아 넘겨주게 되었습니다.<br/>
          이때, 클라이언트가 서버와 통신할 떄는 <strong><em>Model, View, Controller</em></strong> 의 구조를 통하여 데이터 생성, 처리, 전달의 역할을 분담하고 사용자 인터페이스로부터 로직을 분리하여 유지보수에 유리하도록 설정하였습니다. 
          이 프로젝트에서의 Controller는 페이지를 넘겨주는 @Controller가 아닌 json형식의 데이터를 넘겨주는 @RestController를 적용하여 프론트엔드와 서버가 데이터를 주고받을 수 있도록 설정하였습니다.
        </p>
      </li>
      <li>
        <h3>✔️ SpringAOP(@Aspect)</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>프로그램이 실행될 때 메소드의 깊이(레벨)와 종료시 거리는 시간등을 로그로 나타내는 공통적으로 동일하게 사용되는 모듈이 발생하였습니다. </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                SpringAOP의 <strong><em>@Aspect</em></strong> 어노테이션을 통하여 발생되는 횡단 관심사를 핵심 비즈니스 로직에서 분리하도록 하고 적용 대상은 <strong><em>@PointCut</em></strong>으로 나타내도록 하여 부가기능을 적용하였습니다. 
              </p>
            </li>
          </ul>       
      </li>
      <li>
        <h3>✔️ 인수테스트(Acceptance Test)</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>프로그램이 원하는 시나리오대로 작동하는지 확인되는 것이 요구되었습니다. </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                객체별로 인수테스트를 통하여 객체가 <strong><em>원하는 시나리오대로 응답을 하는지 확인</em></strong>하고 사업부의 목적에 부합하는 결과를 나타내도록 구현하였습니다. 
              </p>
            </li>
          </ul>       
      </li>
      <li>
        <h3>✔️ TDD(Test Driven Development)</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>프로젝트의 구성이 커지게 될 수록, 안정적인 어플리케이션 개발과 특정 버그를 쉽게 찾아내기 위하여 단위 구성의 테스트 작성이 요구되었습니다. </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                Repository, Service의 테스트를 각기 생성했으며 Repository계층의 테스트 진행 후 성공한다면 Service 계층의 테스트를 실행하는 방식으로 진행하였습니다. 
                Service계층의 Test를 진행할 때에는 <strong><em>FIRST(Fast, Independent, Repeatable, Self Validating, Timely)</em></strong> 에 부합하도록 작성하도록 노력하고, <strong><em>given-when-then</em></strong> 의 방법을 지켜 테스트가 진행되도록 작성하였습니다. 
              </p>
            </li>
          </ul>       
      </li>
      <li>
        <h3>✔️ Authentication</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>
                이전 프로젝트에서와 동일한 이유로(인증관련 정보를 모두 DB에 저장했을 시에는 사용자가 늘어남에 따라 필요한 저장공간이 증가하게 되므로) JWT인증토큰 방법을 사용하고자 하였으나, 스프링 프레임워크에서의 jwt토큰 발급, 검증의 과정 어떻게 구현해낼 것인가에 대한 요구가 있었습니다.
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                스프링프레임워크에서의 JWT토큰 생성부분은 크게 생성부분과 검증 부분을 나누어 처리하도록 설정하였습니다. JwtTokenUtil에 토큰 생성부분과 검증부분을 생성해주었으며,
                유저 정보(이메일과 패스워드)가 들어오는 AuthenticationController단에서 JwtRequest형식에 맞게 토큰 검증을 요청하고 완료되면 JwtResponse형식으로 토큰을 발급해주도록 진행하였습니다.  
              </p>
            </li>
          </ul>
      </li>
      <li>
        <h3>✔️ Authorization</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>
                사용자의 등급,레벨에 따라서(관리자, CEO, LEADER, MEMBER, 로그인하지 않은 유저) 볼 수있는 데이터에 제한을 두고 싶었습니다. 페이지 접속 권한의 제한을 등급에 따라 어떻게 차등할 것이며 서버에서도 등급에 맞지 않는 요구를 어떻게 막아낼 것인 가에 대한 요구가 있었습니다.
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                사용자의 등급에 따라서 볼 수 있는 페이지와 컨텐츠를 제한하기 위하여 프론트엔드 부분에서는 <strong><em>CustomRouter</em></strong> 를 생성하여 서버에서는 <strong><em>Spring Security(spring-boot-starter-security)</em></strong> 를 사용하여 사용자의 토큰에 따른 각기 다른 Authority에 따라서 가능한 요청에 제한을 두는 방식으로 구현했습니다. 
              </p>
            </li>
          </ul>
      </li>
      <li>
        <h3>✔️ Exception Handler</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>프로젝트 진행 중 발생될 수 있는 예외에 이름을 짓고 예외를 custom화(특정화) 하는 것이 필요해졌습니다. 이와 더불어 발생되는 예외를 일괄된 형식으로 처리하는 것이 요구되었습니다. </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                발생될 수 있는 특정한 예외들은 RuntimeException을 상속받는 custom Exception으로 생성해주고 <strong><em>Global Exception Handler</em></strong> 를 생성하여 발생된 예외에 맞는 HTTP Status를 함께 응답하도록 설정하였습니다. 
                또한 발생된 예외들은 모두 Error Response 형식의 형태로 반응하도록 설정하였습니다. 
              </p>
            </li>
          </ul>       
      </li>
      <li>
        <h3>✔️ STOMP(WebSocket)</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>
                프로젝트의 목표인 소통을 위해서 실시간 메세지 채팅을 구현하기 위해서는 websocket의 사용이 요구되었습니다. 
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                실시간 소통 기능을 위해서 프론트엔드(react)에서는 <strong><em>sockjs-client, stompjs</em></strong> 를, 스프링프레임워크에서는 <strong><em>WebSocket(spring-boot-starter-websocket)</em></strong> 을 추가하여 구현하였습니다. 
                스프링프레임워크에서의 websocket은 <strong><em>STOMP</em></strong> 를 사용해 어플리케이션이 Stomp Broker로 작동할 수 있도록 설정하여 웹소켓프레임을 통해서 클라이언트들과 broker들이 서로 다른 언어로 메세지를 주고받을 수 있도록 설정하였습니다 
              </p>
            </li>
          </ul>
      </li>
      <li>
        <h3>✔️ Pagination & Sort</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>
                noSQL과 달리 SQL에서는 단순히 sort, limit을 정하는 것만으로는 pagination, sort기능을 구현하는 것이 어려웠습니다. 쿼리를 설정하여 해당 기능 구현하는 것이 요구되었습니다. 
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                Pagination을 구현하기 위해서는(데이터를 일정량만 가져오도록 설정) Repository에서 데이터를 불러올 때 countQuery 이용하여 페이지네이션이 가능하도록 설정해 주었습니다. 
                Sort기능을 구현하기 위해서는 @Query 어노테이션에서 ORDER BY를 사용하거나 Pageable을 생성시에 Sort.by기능을 사용하여 기준을 정해 정렬된 데이터를 순서대로 가져오도록 설정하였습니다.
              </p>
            </li>
          </ul>
      </li>
      <li>
        <h3>✔️ Frontend-maven-plugins (Heroku Deployment)</h3> 
          <ul>
            <li>
              <h4>Challenge : </h4>
              <p>
                스프링부트와 리액트를 활용하여 각각의 프로젝트를 완성 시킨 후 서비스를 배포하기 위해서는 두 프로젝트를 합친 프로젝트로 변경하는 것이 요구되었습니다. 
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                heroku 서비를 이용하여 앱을 배포하기 위해서 스프링부트로 완성시킨 서버프로젝트 안으로 프론트엔드를 옮겨 frontend-maven-plugins를 이용해 두 프로젝드를 동시에 실행시키도록 하는 .jar 파일을 생성해내도록 하였습니다. 생성된 .jar파일을 해로쿠에 등록하여 앱 실행시 .jar파일을 실행하도록 설정하였습니다. 
              </p>
            </li>
          </ul>
      </li>          
    </ul>
</div>
    <br/>
    <br/>
    <br/>
<div>
    <h2 id="challenges">⚡️ Challenges</h2>
    <ul>
      <li>
        <h3>✔️ N+1 쿼리 발생 문제 </h3> 
          <ul>
            <li>
              <h4>Reason : </h4>
              <p>
                즉시로딩으로 설정된 연관관계의 엔티티를 함께 조회시에 데이터를 한번 로딩할 때마다 모든 연관관계의 데이터를 한번씩 더 불러오게 되어 추가적으로 발생하는 쿼리때문에 생겨나는 문제였습니다. 
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                해당 문제를 해결하기 위해서 모든 연관관계는 지연로딩으로 설정하였으며, 추가적인 연관관계의 데이터가 필요할 경우 레파지토리 계층에서 LEFT JOIN하여 해당 데이터를 함께 불러올 수 있도록 설정하여 해당 문제를 해결하였습니다.
              </p>
            </li>
          </ul>
      </li>
      <li>
        <h3>✔️ No serializer found for class Exception & hibernate lazy initialization exception could not initialize proxy - no session </h3> 
          <ul>
            <li>
              <h4>Reason : </h4>
              <p>
                연관관계 설정된 데이터를 불러오려고 할 때, fetch타입이 지연로딩으로 설정되어 있어 데이터가 아직 넘어오지 않아 발생되는 문제였습니다.  
              </p>
            </li>
            <li>
              <h4>Solution : </h4>
              <p>
                해당 문제를 해결하기 위한 방법에는 1. properties -> jackson.serialization.fail-on-empty-beans = false 설정, 2. 해당 엔티티에 @JsonIgnore 설정, 3. 지연로딩 즉시로딩으로 바꾸는 방법이 존재하였습니다. 
                그러나 해당 설정부부은 엔티티설정을 변경하게 되어 자세히 확인한 결과 해당 클래스 설정에서 @Getter, @Setter, 서비스와 레파지토리계층에서는 @Service, @Transactional 잘 설정해주는 것만으로도 해결이 되었습니다. 
              </p>
            </li>
          </ul>
      </li>
     </ul>
</div>
    <br/>
    <br/>
    <br/><div>
    <h2 id="tech">🛠 Tech Stack Used</h2>
    <ul>
      <li>
        <h4>Frontend</h4> 
        <img src="https://img.shields.io/badge/react-6cc1d9?style=for-the-badge&logo=react&logoColor=white">
        <img src="https://img.shields.io/badge/redux-bb93e6?style=for-the-badge&logo=redux&logoColor=white">
        <img src="https://img.shields.io/badge/stomp.js-e38046?style=for-the-badge&logo=socket.io&logoColor=white">
        <img src="https://img.shields.io/badge/fontawesome-26324f?style=for-the-badge&logo=fontawesome&logoColor=white">
        <img src="https://img.shields.io/badge/bootstrap-574370?style=for-the-badge&logo=bootstrap&logoColor=white">
      </li>
      <li>
        <h4>Backend</h4> 
        <img src="https://img.shields.io/badge/springboot-67a155?style=for-the-badge&logo=springboot&logoColor=white">
        <img src="https://img.shields.io/badge/springsecurity-6aa667?style=for-the-badge&logo=springsecurity&logoColor=white">
        <img src="https://img.shields.io/badge/mysql-e3ba68?style=for-the-badge&logo=mysql&logoColor=black">
        <img src="https://img.shields.io/badge/jwt-2b2b2b?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
        <img src="https://img.shields.io/badge/websocket-e38046?style=for-the-badge&logo=socket.io&logoColor=white">
      </li>
      <li>
        <h4>Deploy</h4> 
        <img src="https://img.shields.io/badge/heroku-ad7dac?style=for-the-badge&logo=heroku&logoColor=white">
      </li>
    </ul>
</div>
    <br/>
    <br/>
    <br/>
<div>
    <h2 id="implementation">🍔 Implementation</h2>
    <ul>
      <li>
        <h3>✔️ Redux</h3> 
        <p>리덕스를 통하여 View에서는 action만을 실행시키면 이 액션은 dispatcher를 통해서만 데이터변경이 가능하도록 설정해주었습니다. 변경된 데이터는 store를 통해서 View로 전달이 되도록 하여 단방향의 데이터 흐름이 이루어지도록 설정하였습니다. 또한 Constant를 지정하여 각각의 액션에 요청, 성공, 실패의 경우를 알아보기 쉽게 정의하였고 각각의 액션은 모두 저장되어 어떻게 데이터가 변경되었는지 확인할 수 있었습니다. </p>
      </li>
     </ul>
</div>
    <br/>
    <br/>
    <br/>
<div id="feature">
</div>
    
🪵 Features
--
<br/>
<h4> 🧀 사용자, 회사, 팀에 대한 CRUD(Create, Read, Update, Delete) 가능</h4>
<h4> 🧀 CEO 이름이나 회사이름을 이용한 회사 검색 기능 </h4>
<h4> 🧀 회사 등록 날짜, 멤버수에 의한 Sort 기능</h4>
<h4> 🧀 Json Web Token을 이용한 인증/인가 기능</h4>
<h4> 🧀 WebSocket(STOMP)를 이용한 실시간 메세지 채팅 기능</h4>
<h4> 🧀 대화창을 다른 회사, 같은 회사, 같은 팀 별로 나눠서 확인 가능</h4>
<br/>

|  |  |
|:--------|:--------:|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536121-ae735053-d2e4-43a0-ad2c-ee60cd2e53ad.jpg"></br><p><strong>로그인페이지</strong></p><p>로그인하지 않은 유저에게는 메인사진만 제공.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536125-dc96fb1b-00fe-460e-a140-359839467727.jpg"></br><p><strong>메인페이지</strong></p><p>회사이름, 창업자이름으로 검색 가능.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536422-73e8e5b8-af40-4872-b94b-648b97f50a3d.jpg"></br><p><strong>메인페이지 메세지</strong></p><p>다른 회사와의 대화창 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536192-7b993bfa-b23b-485e-92ac-43aaf5734c6b.jpg"></br><p><strong>메인페이지 추천</strong></p><p>회사 추천목록.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536351-64937949-7339-4fb4-8047-4fe4b460894a.jpg"></br><p><strong>메인페이지 회사목록</strong></p><p>등록된 모든 회사 원하는 순서대로 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139537108-ac6519f2-360e-41fb-9ce2-ff4c538f964d.jpg"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139537120-1ad88ee4-5d15-4d85-b588-23029829159f.jpg"></br><p><strong>반응형 웹페이지</strong></p><p>화면 크기에 따라 반응형으로 웹페이지 설계.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536455-a982f226-0fb6-46c0-8652-7609b08a0baa.jpg"></br><p><strong>CEO 회사페이지</strong></p><p>CEO는 자신의 회사 목록 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536483-c24ff2e8-21e8-4057-8e21-f1d24db7ee02.jpg"></br><p><strong>회사 프로필페이지</strong></p><p>회사 프로필 확인 가능.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536510-91291146-ef70-4217-bea2-89615c42bd57.jpg"></br><p><strong>회사 프로필 페이지</strong></p><p>회사에 속한 팀, 멤버 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536547-a8eba017-4d02-4d2d-a6c2-13fa357c5c53.jpg"></br><p><strong>회사 프로필페이지</strong></p><p>CEO는 회사내 팀을 추가 가능.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536570-4ca02a17-45b5-496d-b44f-ba3427070703.jpg"></br><p><strong>프로필 페이지</strong></p><p>회원의 프로필 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536584-9d37f25c-ba78-45f4-ba06-9ae4eb329735.jpg"></br><p><strong>회사 프로필페이지</strong></p><p>회원 자신은 프로필 편집 가능.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536803-cb9aa13d-d899-49a7-a4af-0cd739634d84.jpg"></br><p><strong>팀 페이지</strong></p><p>팀 멤버와의 대화창, 멤버 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536834-594a5678-9a4e-40af-b584-31149b65b50e.jpg"></br><p><strong>팀 페이지</strong></p><p>팀에 속한 멤버에게만 메세지보내기 가능.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536861-d0e107cd-78eb-4267-97c0-56bb06413d63.jpg"></br><p><strong>팀 페이지</strong></p><p>CEO과 Leader는 팀에 멤버 추가 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536884-c2690d7c-b9c7-406a-a048-151159e30b47.jpg"></br><p><strong>회사 목록 페이지</strong></p><p>관리자는 리스트 확인 가능.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536926-a9573e41-91b1-421f-98f6-24252f6414cb.jpg"></br><p><strong>사용자 목록 페이지</strong></p><p>관리자는 리스트 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139536957-18134f88-0484-48c1-9155-2b0f1936731a.jpg"></br><p><strong>팀 목록 페이지</strong></p><p>관리자는 리스트 확인 가능.</p></div>|

<div align="center">
  </br>
  <img width="800px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139539286-0206e315-6134-4394-a012-a2e7c724617e.gif">
  </br>
  <p><strong>채팅</strong></p>
  <p>실시간으로 메세지 전달 가능.</p>
  </br>
</div>
    <br/>
    <br/>
    <br/>
<div>
    <h2 id="erd">🪜 ERD(Entity Relationship Diagram)</h2>
    <img width="100%" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139529377-445d18cd-e902-4b3b-8265-099aaaa4b570.jpg">
</div>
    <br/>
    <br/>
    <br/>
    <br/>
<div>
    <h2 id="structure">🧱 Structure</h2>
</div>



```bash
📦SocialMediaApp
├── 🗂backend
│   └── withEmployee
│       ├── src
│       │   ├── main
│       │   │   ├── java
│       │   │   │   └── com
│       │   │   │       └── yunhalee
│       │   │   │           └── withEmployee
│       │   │   │               ├── WithEmployeeApplication.java
│       │   │   │               ├── aop
│       │   │   │               │   ├── LogAop.java
│       │   │   │               │   ├── LogConfig.java
│       │   │   │               │   ├── LogTrace.java
│       │   │   │               │   ├── ThreadLogTrace.java
│       │   │   │               │   ├── TraceId.java
│       │   │   │               │   └── TraceStatus.java
│       │   │   │               ├── common
│       │   │   │               │   ├── controller
│       │   │   │               │   │   ├── MainController.java
│       │   │   │               │   │   └── SocketController.java
│       │   │   │               │   ├── domain
│       │   │   │               │   │   └── BaseTimeEntity.java
│       │   │   │               │   └── exception
│       │   │   │               │       ├── ErrorResponse.java
│       │   │   │               │       ├── GlobalExceptionHandler.java
│       │   │   │               │       └── exceptions
│       │   │   │               │           ├── AuthException.java
│       │   │   │               │           ├── BadRequestException.java
│       │   │   │               │           ├── EntityNotFoundException.java
│       │   │   │               │           └── InvalidValueException.java
│       │   │   │               ├── company
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── CompanyController.java
│       │   │   │               │   ├── domain
│       │   │   │               │   │   ├── Company.java
│       │   │   │               │   │   └── CompanyRepository.java
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── CompanyListResponse.java
│       │   │   │               │   │   ├── CompanyListResponses.java
│       │   │   │               │   │   ├── CompanyRequest.java
│       │   │   │               │   │   ├── CompanyResponse.java
│       │   │   │               │   │   ├── CompanyResponses.java
│       │   │   │               │   │   └── SimpleCompanyResponse.java
│       │   │   │               │   ├── exception
│       │   │   │               │   │   ├── CompanyNameAlreadyInUseException.java
│       │   │   │               │   │   ├── CompanyNameEmptyException.java
│       │   │   │               │   │   └── CompanyNotFoundException.java
│       │   │   │               │   └── service
│       │   │   │               │       └── CompanyService.java
│       │   │   │               ├── config
│       │   │   │               │   ├── CacheConfig.java
│       │   │   │               │   ├── WebConfig.java
│       │   │   │               │   └── WebSocketConfig.java
│       │   │   │               ├── conversation
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── ConversationController.java
│       │   │   │               │   ├── domain
│       │   │   │               │   │   ├── Conversation.java
│       │   │   │               │   │   └── ConversationRepository.java
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── ConversationRequest.java
│       │   │   │               │   │   ├── ConversationResponse.java
│       │   │   │               │   │   └── ConversationResponses.java
│       │   │   │               │   ├── exception
│       │   │   │               │   │   └── ConversationNotFoundException.java
│       │   │   │               │   └── service
│       │   │   │               │       └── ConversationService.java
│       │   │   │               ├── message
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── MessageController.java
│       │   │   │               │   ├── domain
│       │   │   │               │   │   ├── Message.java
│       │   │   │               │   │   └── MessageRepository.java
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── MessageRequest.java
│       │   │   │               │   │   ├── MessageResponse.java
│       │   │   │               │   │   ├── MessageResponses.java
│       │   │   │               │   │   └── MessageUserResponse.java
│       │   │   │               │   ├── exception
│       │   │   │               │   │   └── MessageNotFoundException.java
│       │   │   │               │   └── service
│       │   │   │               │       └── MessageService.java
│       │   │   │               ├── security
│       │   │   │               │   ├── AuthenticationPrincipal.java
│       │   │   │               │   ├── AuthenticationPrincipalArgumentResolver.java
│       │   │   │               │   ├── AuthenticationPrincipalConfig.java
│       │   │   │               │   ├── WebSecurityConfig.java
│       │   │   │               │   └── jwt
│       │   │   │               │       ├── JwtAuthenticationController.java
│       │   │   │               │       ├── JwtAuthenticationEntryPoint.java
│       │   │   │               │       ├── JwtRequest.java
│       │   │   │               │       ├── JwtRequestFilter.java
│       │   │   │               │       ├── JwtResponse.java
│       │   │   │               │       ├── JwtTokenUtil.java
│       │   │   │               │       ├── JwtUserDetails.java
│       │   │   │               │       ├── JwtUserDetailsService.java
│       │   │   │               │       ├── LoginUser.java
│       │   │   │               │       └── UserTokenResponse.java
│       │   │   │               ├── team
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── TeamController.java
│       │   │   │               │   ├── domain
│       │   │   │               │   │   ├── Team.java
│       │   │   │               │   │   └── TeamRepository.java
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── SimpleTeamResponse.java
│       │   │   │               │   │   ├── SimpleTeamResponses.java
│       │   │   │               │   │   ├── TeamRequest.java
│       │   │   │               │   │   ├── TeamResponse.java
│       │   │   │               │   │   └── TeamResponses.java
│       │   │   │               │   ├── exception
│       │   │   │               │   │   ├── TeamNameAlreadyInUseException.java
│       │   │   │               │   │   ├── TeamNameEmptyException.java
│       │   │   │               │   │   └── TeamNotFoundException.java
│       │   │   │               │   └── service
│       │   │   │               │       └── TeamService.java
│       │   │   │               ├── user
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── UserController.java
│       │   │   │               │   ├── domain
│       │   │   │               │   │   ├── Role.java
│       │   │   │               │   │   ├── User.java
│       │   │   │               │   │   └── UserRepository.java
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── CeoResponse.java
│       │   │   │               │   │   ├── MemberResponse.java
│       │   │   │               │   │   ├── SimpleUserResponse.java
│       │   │   │               │   │   ├── UserCompanyResponse.java
│       │   │   │               │   │   ├── UserRequest.java
│       │   │   │               │   │   ├── UserResponse.java
│       │   │   │               │   │   ├── UserResponses.java
│       │   │   │               │   │   └── UserTeamResponse.java
│       │   │   │               │   ├── exception
│       │   │   │               │   │   ├── DuplicatedEmailException.java
│       │   │   │               │   │   └── UserNotFoundException.java
│       │   │   │               │   └── service
│       │   │   │               │       └── UserService.java
│       │   │   │               └── util
│       │   │   │                   └── FileUploadService.java
│       │   │   └── resources
│       │   │       ├── config
│       │   │       │   ├── application-local.properties
│       │   │       │   ├── application-prod.properties
│       │   │       │   ├── application-test.properties
│       │   │       │   └── application.properties
│       │   │       ├── static
│       │   │       └── templates
│       │   └── test
│       │       ├── java
│       │           └── com
│       │               └── yunhalee
│       │                   └── withEmployee
│       │                       ├── AcceptanceTest.java
│       │                       ├── MockBeans.java
│       │                       ├── RepositoryTest.java
│       │                       ├── WithEmployeeApplicationTests.java
│       │                       ├── company
│       │                       │   ├── CompanyAcceptanceTest.java
│       │                       │   ├── domain
│       │                       │   │   ├── CompanyRepositoryTest.java
│       │                       │   │   └── CompanyTest.java
│       │                       │   └── service
│       │                       │       └── CompanyServiceTest.java
│       │                       ├── conversation
│       │                       │   ├── ConversationAcceptanceTest.java
│       │                       │   ├── domain
│       │                       │   │   ├── ConversationRepositoryTest.java
│       │                       │   │   └── ConversationTest.java
│       │                       │   └── service
│       │                       │       └── ConversationServiceTest.java
│       │                       ├── message
│       │                       │   ├── MessageAcceptanceTest.java
│       │                       │   ├── domain
│       │                       │   │   ├── MessageRepositoryTests.java
│       │                       │   │   └── MessageTest.java
│       │                       │   └── service
│       │                       │       └── MessageServiceTest.java
│       │                       ├── security
│       │                       │   ├── AuthAcceptanceTest.java
│       │                       │   └── PasswordEncoderTest.java
│       │                       ├── team
│       │                       │   ├── TeamAcceptanceTest.java
│       │                       │   ├── domain
│       │                       │   │   ├── TeamRepositoryTest.java
│       │                       │   │   └── TeamTest.java
│       │                       │   └── service
│       │                       │       └── TeamServiceTest.java
│       │                       ├── user
│       │                       │   ├── UserAcceptanceTest.java
│       │                       │   ├── domain
│       │                       │   │   ├── UserRepositoryTest.java
│       │                       │   │   └── UserTest.java
│       │                       │   └── service
│       │                       │       └── UserServiceTest.java
│       │                       └── util
│       │                           ├── DatabaseCleanup.java
│       │                           └── FileUploadServiceTest.java
│       ├── messageUploads
│       └── profileUploads
├── 🗂frontend
│   ├── package-lock.json
│   ├── package.json
│   ├── public
│   ├── src
│   │   ├── App.js
│   │   ├── SocketClient.js
│   │   ├── _actions
│   │   │   ├── authActions.js
│   │   │   ├── companyActions.js
│   │   │   ├── conversationActions.js
│   │   │   ├── messageActions.js
│   │   │   ├── teamActions.js
│   │   │   └── userActions.js
│   │   ├── _constants
│   │   │   ├── authConstants.js
│   │   │   ├── companyConstants.js
│   │   │   ├── conversationConstants.js
│   │   │   ├── messageConstants.js
│   │   │   ├── socketConstants.js
│   │   │   ├── teamConstants.js
│   │   │   └── userConstants.js
│   │   ├── _reducers
│   │   │   ├── authReducers.js
│   │   │   ├── companyReducers.js
│   │   │   ├── messageReducers.js
│   │   │   ├── socketReducers.js
│   │   │   ├── store.js
│   │   │   ├── teamReducers.js
│   │   │   └── userReducers.js
│   │   ├── components
│   │   │   ├── AddCompanyModal.js
│   │   │   ├── AddMemberModal.js
│   │   │   ├── AddTeamModal.js
│   │   │   ├── CompanyCard.js
│   │   │   ├── EditProfileModal.js
│   │   │   ├── Error.js
│   │   │   ├── Header.js
│   │   │   ├── Loading.js
│   │   │   ├── SearchCompanyCard.js
│   │   │   ├── SearchUserCard.js
│   │   │   ├── TeamCard.js
│   │   │   ├── UserCard.js
│   │   │   └── messages
│   │   │       ├── CompanyConversationCard.js
│   │   │       ├── ConversationCard.js
│   │   │       ├── ConversationUserCard.js
│   │   │       ├── Display.js
│   │   │       └── MessageCard.js
│   │   ├── customRouter
│   │   │   ├── AdminRouter.js
│   │   │   ├── CeoRouter.js
│   │   │   └── PrivateRouter.js
│   │   ├── images
│   │   │   ├── Ellipse\ 1.png
│   │   │   ├── Ellipse\ 2.png
│   │   │   ├── home.png
│   │   │   ├── message.png
│   │   │   ├── user.svg
│   │   │   └── users.svg
│   │   ├── screens
│   │   │   ├── CeoCompanyScreen.js
│   │   │   ├── CompanyListScreen.js
│   │   │   ├── CompanyScreen.js
│   │   │   ├── EditProfileScreen.js
│   │   │   ├── HomeScreen.js
│   │   │   ├── LoginScreen.js
│   │   │   ├── ProfileScreen.js
│   │   │   ├── RegisterScreen.js
│   │   │   ├── TeamListScreen.js
│   │   │   ├── TeamScreen.js
│   │   │   ├── UserListScreen.js
│   │   │   └── UserTeamScreen.js
│   │   ├── styles
│   │   │   ├── CompanyCard.css
│   │   │   ├── CompanyScreen.css
│   │   │   ├── Error.css
│   │   │   ├── Form.css
│   │   │   ├── HomeScreen.css
│   │   │   ├── List.css
│   │   │   ├── Message.css
│   │   │   ├── ProfileScreen.css
│   │   │   ├── SearchUserCard.css
│   │   │   ├── TeamCard.css
│   │   │   ├── UserCard.css
│   │   │   ├── header.css
│   │   │   └── loading.css
│   │   └── utils.js
│   └── yarn.lock
├── k6
│   ├── main
│   │   ├── load.js
│   │   ├── smoke.js
│   │   └── stress.js
│   └── update
│       ├── load.js
│       ├── smoke.js
│       └── stress.js
└── nginx.conf
```