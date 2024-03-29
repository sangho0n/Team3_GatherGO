# Note

[GatherGo](https://github.com/softeerbootcamp/Team3) 팀프로젝트의 개인 레포지토리입니다.

저는 해당 프로젝트에서 벡엔드 포지션을 담당했으며, 모임글 게시판에 대한 ERD를 설계하고 RDBMS를 이용해 DB 서버를 구축하였습니다.

모임글 CRUD 요청에 관련한 Restful api를 설계했으며 이를 처리하는 WAS를 구현하였습니다.

페어프로그래밍을 통해 2명 이상의 사용자가 남은 자리가 하나 뿐인 모임에 동시에 참여하고자 하는 경우를 Redis를 이용해 한명의 사용자만 참여할 수 있도록 처리하였습니다.

현재 AWS ec2에서 내려와있는 상태라 접속은 불가능합니다.

감사합니다.

------------------------------
<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
<img width="394" alt="GatherGoLogo" src="https://user-images.githubusercontent.com/63828202/217709195-4be91489-dc44-4015-94d5-fe04e2cd883e.svg">

  </a>


<h3 align="center">GatherGo</h3>

  <p align="center">
    번개 매칭 플랫폼
    <br />
    <br />
    <a href="">데모 영상 보기(준비중)</a> ·
    <a href="https://quill-bed-0bb.notion.site/Hyundai-Web-Project-2fd7966806c54e418c89e2d38ab22b30">팀 페이지</a> ·
    <a href="https://docs.google.com/presentation/d/101pvz4olysHofRXOB_jt3_sGQii5iGU0m7y7qUaF5jo/edit?usp=sharing">화면 설계</a>
  </p>
</div>

## 프로젝트 관리
진행상황을 비롯한 프로젝트에 대한 전반적인 관리는 모두 상단에 링크되어있는 팀 페이지를 통해 관리하고 있습니다.

## Built With
##### FRONTEND
[![Html][Html]][Html-url]
[![Vite][Vite]][Vite-url]
[![TypeScript][TypeScript]][TypeScript-url]
[![Css][Css]][Css-url]
[![Prettier][Prettier]][Css-url]
![Redux][Redux]
![npm][npm]


##### BACKEND
[![Spring][Spring]][Spring-url]
[![Firebase][Firebase]][Firebase-url]
![java][java]
![junit5][junit5]
![redis][redis]
![mysql][mysql]
![ec2][ec2]
![nginx][nginx]
![docker][docker]

## 기능

### 메인 페이지
![image](https://user-images.githubusercontent.com/63828202/220541084-33d3e8d0-7884-4af3-b302-c2af6e99bbe3.png)
<details>
<summary> 메인 페이지 상세 설명 </summary>
<div markdown="1">       

✨ 검색 옵션에 따라 해당되는 모임 정보들을 열람할 수 있다. </br>
✨ **지역 검색**, **카데고리 검색**, **키워드 검색**을 지원한다. <br/>
✨ 현재 시간 기준으로 마감되거나 시간이 지난 모임들은 보이지 않는다. <br/>
✨ 사용자들은 최신순 혹은 마감 임박순으로 번개 모임들을 확인해 볼 수 있다. </br>
✨ 알람 탭을 누르면 사용자가 참여한 모임에 해당되는 알림 사항들을 확인해 볼 수 있다. </br>

</div>
</details>


### 마이페이지
![image](https://user-images.githubusercontent.com/63828202/220541868-be4947a3-3e7e-4228-b102-d252f85776b1.png)
<details>
<summary> 마이 페이지 상세 설명 </summary>
<div markdown="1">       

✨ 메인 페이지에서 프로필 버튼을 누르면 해당 유저에 해당 되는 마이 페이지로 넘어간다.</br>
✨ 마이 페이지에서 프로필 이미지를 업로드할 수 있다. </br>
✨ 마이 페이지에서 내가 호스트한 만남과 진행 상태를 확인해 볼 수 있다. </br>
✨ 마이 페이지에서 나의 만남 스케줄을 확인해 볼 수 있다. </br>
✨ 마이 페이지에서 내가 호스트한 만남 및 만남 스케줄의 모임을 클릭하면 해당 모임 모달 창으로 연결된다. </br>

</div>
</details>

### 번개 상세 모달
![image](https://user-images.githubusercontent.com/63828202/220543435-d6fbb10b-13da-4492-b30c-3a382e5d36cc.png)
<details>
<summary> 번개 상세 모달 설명 </summary>
<div markdown="1">       

✨ 메인 페이지에서 해당 번개 카드를 누르면 상세 정보를 모달을 통해 보여준다. <br/>
✨ 모달 안에서는 댓글을 생성 및 삭제할 수 있다. <br/>
✨ 호스트는 모달 안에서 톱니 바퀴를 눌러 상세 정보를 수정하거나 모임을 마감할 수 있다. <br/>
✨ 유저는 호스트의 자기 소개를 볼 수 있다. <br/>
✨ 유저는 버튼을 통해 해당 번개 모임에 대해 참여 및 참여 취소를 할 수 있다. <br/>
✨ 해당 번개 모임의 호스트는 반드시 참여하게 된다. </br>
✨ 현재 참여 인원이 다 찬 모임에는 참여할 수 없다. </br>

</div>
</details>

### 알람 기능
![image](https://user-images.githubusercontent.com/63828202/220544601-6d57bfaf-60e4-4ff8-9c21-aa3496da45ea.png)

<details>
<summary> 알람 상세 설명 </summary>
<div markdown="1">       

✨ 현재 유저가 참여하고 있는 글에 대한 알림이 있을 경우, 웹 푸시 알림을 받을 수 있다.</br>
✨ 유저가 받은 알람 내역들은 알림 사이드 바를 통해 확인할 수 있다.</br>
✨ e.g. 모임 상세가 수정되었을 때, 호스트가 마감했을 때 , 댓글이 달렸을 때 <br/>

</div>
</details>

### 번개 만들기
![image](https://user-images.githubusercontent.com/63828202/220545466-8a5404a4-b09b-4352-bb5b-6a2d5d40fa1a.png)
<details>
<summary>  번개 만들기 상세 설명 </summary>
<div markdown="1">       

✨ 번개 모임 글 등록시, 카카오 지도를 통해 만남 장소를 입력한다. </br>
✨ 현재 시간 이전으로는 번개 모임 생성이 불가능하다. </br>
✨ 관심 장소 검색시 나타나는 마커를 선택하여 해당 장소를 지정하게 된다.</br>

</div>
</details>

### 챗봇 
![image](https://user-images.githubusercontent.com/63828202/220545927-66a7da09-f171-4f61-a465-cc7f5fde5a2d.png)
<details>
<summary>  챗봇 상세 설명 </summary>
<div markdown="1">       

✨ OpenAI API를 기반으로 훈련시킨 자연어 처리가 가능한 챗봇 </br>
✨ 웹 사이트에 대한 사용자의 질문에 답변한다 </br>

</div>
</details>


## 아키텍처

### 프로젝트 아키텍처
![image](https://user-images.githubusercontent.com/63828202/220517449-3005347b-0418-4994-97f5-b92653db4a93.png)

## ERD
![image](https://user-images.githubusercontent.com/63828202/220528421-74475654-2968-444f-a05b-521d7f31989b.png)

## API 스펙 관리
<img width="1682" alt="api_spec" src="https://user-images.githubusercontent.com/63828202/220229036-b9a05262-b556-4050-8936-62ba5823865f.png">

## 이슈 관리
ZenHub를 바탕으로 칸반 보드를 통한 이슈 관리를 하고 있습니다.
<img width="1728" alt="image" src="https://user-images.githubusercontent.com/63828202/220507143-38976464-8c37-4cb8-bc56-d882e2266537.png">

## 디렉토리 구조 

### 프론트 디렉토리 구조
```
gathergo
 ┣ public
 ┃ ┗ assets
 ┃   ┣ Icons
 ┃   ┣ Logo
 ┃   ┗ category
 ┃     ┣ icons
 ┃     ┗ thumbnails
 ┣ src
 ┃ ┣ common
 ┃ ┃ ┣ fg.timepicker-master
 ┃ ┃ ┣ kakaoMapAPI
 ┃ ┃ ┗ utils
 ┃ ┣ components
 ┃ ┃ ┣ card
 ┃ ┃ ┣ comment
 ┃ ┃ ┣ dropdown
 ┃ ┃ ┣ fba
 ┃ ┃ ┣ form
 ┃ ┃ ┃ ┗ postingForms
 ┃ ┃ ┣ header
 ┃ ┃ ┣ modals
 ┃ ┃ ┣ profile
 ┃ ┃ ┃ ┣ tabEdit
 ┃ ┃ ┃ ┣ tabProfile
 ┃ ┃ ┗ sidebar
 ┃ ┣ css
 ┃ ┣ pages
 ┃ ┣ server
 ┃ ┗ store
 ┣ index.html
 ┣ index.ts
 ┣ package-lock.json
 ┣ package.json
 ┗ tsconfig.json
 ```

### 백엔드 디렉토리 구조
```
├── main
│   ├── java
│   │   └── lightning
│   │       └── gathergo
│   │           ├── Utils
│   │           ├── config
│   │           ├── controller
│   │           ├── dto
│   │           ├── exception
│   │           ├── filter
│   │           ├── interceptor
│   │           ├── mapper
│   │           ├── model
│   │           ├── repository
│   │           ├── scheduler
│   │           └── service
│   └── resources
│       ├── static
│       └── templates
└── test
    └── java
        └── lightning
            └── gathergo
                ├── controller
                ├── firebase
                ├── mapper
                ├── repository
                └── service
 ```



## 팀원 소개

##### FRONTEND 

| 최승호                                                                        |윤성환|
|----------------------------------------------------------------------------| ---- |
 | <img src="https://avatars.githubusercontent.com/u/76439502?v=4" width=130> |<img src="https://avatars.githubusercontent.com/u/35682200?v=4" width=130>|
 | [@tdmdgh](https://github.com/tdmdghn)                                      |[@SUNGWHANYOON](https://github.com/SUNGWHANYOON)|


##### BACKEND
| 한상훈                                                                        | 신정아                                                                        | 공윤재                                                                        |
|----------------------------------------------------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------|
| <img src="https://avatars.githubusercontent.com/u/54069713?v=4" width=130> | <img src="https://avatars.githubusercontent.com/u/63828202?v=4" width=130> | <img src="https://avatars.githubusercontent.com/u/33480561?v=4" width=130> |
| [@sangho0n](https://github.com/sangho0n)                                   | [@JeongA-Shin](https://github.com/JeongA-Shin)                             | [@yunjaeGong](https://github.com/yunjaeGong)                               |


<!-- MARKDOWN LINKS & IMAGES -->
[Vite]: https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white
[Vite-url]: https://vitejs-kr.github.io/guide/
[Html]: https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=HTML5&logoColor=white
[Html-url]: https://developer.mozilla.org/ko/docs/Web/HTML
[Css]: https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=CSS3&logoColor=white
[Css-url]: https://getbootstrap.com/
[Redux]: https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=Redux&logoColor=white"
[npm]: https://img.shields.io/badge/npm-CB3837?style=for-the-badge&logo=npm&logoColor=white"
[Prettier]: https://img.shields.io/badge/Prettier-F7B93E?style=for-the-badge&logo=Prettier&logoColor=white"
[TypeScript]: https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white
[TypeScript-url]: https://www.typescriptlang.org/
[Spring]: https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[Spring-url]: https://spring.io/
[Firebase]: https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white
[Firebase-url]: https://firebase.google.com/?hl=ko
[java]: https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"
[junit5]: https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white
[redis]: https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"
[mysql]: https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white
[ec2]: https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white
[nginx]: https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white
[docker]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white
