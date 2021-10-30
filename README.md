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
    <a href=""><strong>with-employee-with-you</strong></a>
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
<br/>

|  |  |
|:--------|:--------:|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/139517376-4271f02c-f83a-4218-96d0-773e06853244.jpg"></br><p><strong>로그인페이지</strong></p><p>소셜로그인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137157056-2cc5a81f-721d-4883-9c0b-e688a862901e.png"></br><p><strong>메인페이지</strong></p><p>사용자 추천과 팔로우한 사용자 포스트 나열.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137157460-fb0101eb-3415-4e4e-8f82-ec30abfe0f44.png"></br><p><strong>프로필페이지</strong></p><p>사용자의 포스트와 북마크한 포스트 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137157693-141efbf6-d61e-465d-8502-d664e71c44a5.png"></br><p><strong>알람</strong></p><p>팔로우한 사용자의 활동을 헤더의 알람으로 확인.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137159185-1a562f96-1c7f-4617-a8e1-d79a7dbe5af0.png"></br><p><strong>프로필페이지</strong></p><p>팔로우 정보 확인하고 팔로우 언팔로우 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137170414-7aaeab5e-255f-401e-bafd-94c83551d9f1.png"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137170615-656f3d74-e0a1-483c-9edf-ab34168643c1.png"></br><p><strong>반응형 웹페이지</strong></p><p>화면 크기에 따라 반응형으로 웹페이지 설계.</p></div>|
|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137158829-5d53f6f4-c29a-4083-8a48-ace30650671d.png"></br><p><strong>디스커버페이지</strong></p><p>팔로우하지 않은 사용자들의 포스트들 랜덤으로 확인 가능.</p></div>|<div align="center"><img height="200px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137158654-e7b2e7be-eff0-4c90-a1fd-5b2259ee404e.png"></br><p><strong>메세지페이지</strong></p><p>실시간 메세지 및 사용자 온라인 정보 확인 가능.</p></div>|

<div align="center">
  </br>
  <img width="800px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137169143-53d803ff-b566-4acb-9e67-211562e0167b.gif">
  </br>
  <p><strong>좋아요</strong></p>
  <p>좋아요 정보를 실시간으로 업데이트.</p>
    </br>
  <img width="800px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137169421-a7a47496-7178-4224-9493-917b90cf2d7d.gif">
  </br>
  <p><strong>채팅</strong></p>
  <p>실시간으로 메세지 전달 가능.</p>
    </br>
  <img width="800px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137166587-9bc106d4-7ad6-40f5-8210-96fb64fbf20f.gif">
  </br>
  <p><strong>음성통화</strong></p>
  <p>실시간으로 음성통화 가능.</p>
    </br>
  <img width="800px" alt="스크린샷 2021-10-02 오후 6 48 23" src="https://user-images.githubusercontent.com/63947424/137168566-eb87e7ef-d44e-479a-987a-17be1f2a880d.gif">
  </br>
  <p><strong>영상통화</strong></p>
  <p>실시간으로  가능.</p>
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
│       │   │   │               ├── FileUploadUtils.java
│       │   │   │               ├── Repository
│       │   │   │               │   ├── CompanyRepository.java
│       │   │   │               │   ├── ConversationRepository.java
│       │   │   │               │   ├── MessageRepository.java
│       │   │   │               │   ├── RoleRepository.java
│       │   │   │               │   ├── TeamRepository.java
│       │   │   │               │   └── UserRepository.java
│       │   │   │               ├── WithEmployeeApplication.java
│       │   │   │               ├── config
│       │   │   │               │   ├── WebConfig.java
│       │   │   │               │   └── WebSocketConfig.java
│       │   │   │               ├── controller
│       │   │   │               │   ├── CompanyController.java
│       │   │   │               │   ├── ConversationController.java
│       │   │   │               │   ├── MainController.java
│       │   │   │               │   ├── MessageController.java
│       │   │   │               │   ├── SocketController.java
│       │   │   │               │   ├── TeamController.java
│       │   │   │               │   └── UserController.java
│       │   │   │               ├── dto
│       │   │   │               │   ├── CompanyCreateDTO.java
│       │   │   │               │   ├── CompanyDTO.java
│       │   │   │               │   ├── CompanyListByPageDTO.java
│       │   │   │               │   ├── CompanyListDTO.java
│       │   │   │               │   ├── CompanyTestDTO.java
│       │   │   │               │   ├── ConversationListDTO.java
│       │   │   │               │   ├── MessageDTO.java
│       │   │   │               │   ├── TeamDTO.java
│       │   │   │               │   ├── TeamListByPageDTO.java
│       │   │   │               │   ├── TeamListDTO.java
│       │   │   │               │   ├── UserDTO.java
│       │   │   │               │   └── UserListByPageDTO.java
│       │   │   │               ├── entity
│       │   │   │               │   ├── BaseTimeEntity.java
│       │   │   │               │   ├── Company.java
│       │   │   │               │   ├── Conversation.java
│       │   │   │               │   ├── Message.java
│       │   │   │               │   ├── Role.java
│       │   │   │               │   ├── Team.java
│       │   │   │               │   └── User.java
│       │   │   │               ├── exception
│       │   │   │               │   ├── ErrorResponse.java
│       │   │   │               │   ├── GlobalExceptionHandler.java
│       │   │   │               │   └── UserNotFoundException.java
│       │   │   │               ├── security
│       │   │   │               │   ├── JwtAuthenticaationController.java
│       │   │   │               │   ├── JwtAuthenticationEntryPoint.java
│       │   │   │               │   ├── JwtRequest.java
│       │   │   │               │   ├── JwtRequestFilter.java
│       │   │   │               │   ├── JwtResponse.java
│       │   │   │               │   ├── JwtTokenUtil.java
│       │   │   │               │   ├── JwtUserDetails.java
│       │   │   │               │   ├── JwtUserDetailsService.java
│       │   │   │               │   └── WebSecurityConfig.java
│       │   │   │               └── service
│       │   │   │                   ├── CompanyService.java
│       │   │   │                   ├── ConversationService.java
│       │   │   │                   ├── MessageService.java
│       │   │   │                   ├── TeamService.java
│       │   │   │                   └── UserService.java
│       │   │   └── resources
│       │   │       ├── application.properties
│       │   │       ├── static
│       │   │       └── templates
│       │   └── test
│       │       └── java
│       │           └── com
│       │               └── yunhalee
│       │                   └── withEmployee
│       │                       ├── CompanyRepositoryTests.java
│       │                       ├── CompanyServiceTests.java
│       │                       ├── ConversationRepositoryTests.java
│       │                       ├── ConversationServiceTests.java
│       │                       ├── MessageRepositoryTests.java
│       │                       ├── MessageServiceTests.java
│       │                       ├── PasswordEncoderTest.java
│       │                       ├── RoleRepositoryTests.java
│       │                       ├── TeamRepositoryTests.java
│       │                       ├── TeamServiceTests.java
│       │                       ├── UserRepositoryTests.java
│       │                       ├── UserServiceTests.java
│       │                       └── WithEmployeeApplicationTests.java
│       ├── messageUploads
│       ├── pom.xml
│       ├── profileUploads
├── 🗂client
│   ├── src
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
│   │   │   ├── CreateCompany.js
│   │   │   ├── EditProfileModal.js
│   │   │   ├── Header.js
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
│   │   │   └── PrivateRouter.js
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
│   │   │   ├── Form.css
│   │   │   ├── HomeScreen.css
│   │   │   ├── List.css
│   │   │   ├── Message.css
│   │   │   ├── ProfileScreen.css
│   │   │   ├── SearchUserCard.css
│   │   │   ├── TeamCard.css
│   │   │   ├── UserCard.css
│   │   │   └── header.css
│   │   ├── images
│   │   ├── index.css
│   │   ├── index.js
│   │   ├── App.js
│   │   ├── SocketClient.js
│   │   └── utils.js
└── profileUploads
```