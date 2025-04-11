<a name="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/YEASEUL-JANG/Picplace">
    <img src="picplace_front/src/assets/picplacelogo.jpg" alt="Logo" width="400" height="80">
  </a>

  <h3 align="center">PicPlace</h3>

  <p align="center">
    🍛사당역 주변의 맛집과 카페들을 공유하고 찜(Pick)할 수 있는 홈페이지</p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#담당기능">담당기능</a></li>
    <li><a href="#user-pages">User Pages</a></li>
    <li><a href="#admin-pages">Admin Pages</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>
<p></p>


<!-- ABOUT THE PROJECT -->
## About The Project
<p>
💡 'PicPlace' 는 사당역 근처의 맛집들을 소개하는 서비스입니다.
</p>

<img src="picplace_front/src/assets/mainpage.PNG" alt="MainPage"
  width="500" height="400">
<br>
<p>
💡 가입한 유저는 원하는 맛집을 검색하여 ‘찜’ 할 수 있고 지도를 통해 찜한 장소들의 위치를 확인할 수 있습니다.
</p>
<p>
<figure>
    <img src="picplace_front/src/assets/detail.png" alt="DetailPage"
  width="500" height="400" >
  <figcaption>찜목록 조회</figcaption>
</figure>
</p>
<p align="right">(<a href="#readme-top">back to top</a>)</p>



### 🔧 Built With

| Category       | Stack                                                                 |
|----------------|-----------------------------------------------------------------------|
| **Languages**  | Java 11, JavaScript, HTML/CSS                                         |
| **Frontend**   | Vue3, Element-Plus, Bootstrap                                         |
| **Backend**    | Spring Boot, JPA (QueryDSL), JWT, Spring Security, Quartz Batch      |
| **Server**     | AWS, Linux/UNIX, Ubuntu 20.04, Nginx                                  |
| **Tool/DevOps**| Docker, Jenkins, FileZilla, IntelliJ, MySQL Workbench, GitHub        |


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

1. Clone the repo
   ```sh
   git clone https://github.com/YEASEUL-JANG/Picplace.git
   ```
2. Move to Frontend Folder
   ```sh
   cd frontend
   ```   
3. Install NPM packages
   ```sh
   npm install
   ```
4. Run a application and vue3 page 
   ```sh
   npm run serve
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- Function -->
## 담당기능

- [x] Spring Security+JWT 를 활용한 토큰 인증방식 로그인 구현
- [x] Quartz Batch를 활용한 주기적인 찜목록 삭제기능 
- [x] DB 구조 설계 (JPA)
- [x] QuaryDSL을 활용한 다중 검색기능
- [x] Kakao Map API 를 활용한 다중 마커 지도 구현 
- [x] Jenkins 와 깃헙을 연동하여 push 시 자동 빌드 구현
- [x] Front,Back,DB 각각의 컨테이너를 연동한 Docker 배포

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- Pages -->
## User Pages

### MainPage
<figure>
    <img src="picplace_front/src/assets/mainpage.PNG" alt="MainPage"
  width="500" height="400" >
  <figcaption>카페와 식당 카테고리별 '찜' 많은 베스트 식당 소개</figcaption>
</figure>

### SearchPage
<figure>
<img src="picplace_front/src/assets/searchpage.png" alt="ListPage1"
  width="500" height="400" >
<figcaption>지역명, 메뉴명, 가게명, PlaceType 별로 다중 검색기능 포함, Kakao Map API 활용 </figcaption>
</figure>

### LikeListPage
<figure>
    <img src="picplace_front/src/assets/likepage.png" alt="LikeListPage"
  width="500" height="400" >
  <figcaption>'찜' 한 장소들에 대한 목록확인, 추가한 날짜로부터 한달이 지난 데이터는 Quartz Job 에 의해 DB에서 삭제됨</figcaption>
</figure>

### DetailPage
<figure>
    <img src="picplace_front/src/assets/placedetail.png" alt="DetailPage"
  width="500" height="400" >
  <figcaption>장소 상세소개 페이지, Element-Plus 라이브러리를 통한 사진 뷰 기능</figcaption>
</figure>

### ProfilePage
<figure>
    <img src="picplace_front/src/assets/profile.png" alt="ProfilePage"
  width="500" height="200" >
  <figcaption>수정하기 버튼 클릭 시 input 창이 활성화 됨.</figcaption>
</figure>
    


## Admin Pages

### PlaceListPage
<figure>
    <img src="picplace_front/src/assets/placelist.png" alt="PlaceListPage"
  width="500" height="400" >
  <figcaption>element-plus 라이브러리를 활용한 테이블, 툴팁, 페이징, 검색바 등 구현</figcaption>
</figure>

### SearchPage
<figure>
<img src="picplace_front/src/assets/placeform.png" alt="ListPage2"
  width="500" height="400" >
<figcaption>element-plus 라이브러리를 통해 사진업로드를 포함한 폼 등록화면 구현</figcaption>
</figure>

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

- YouTube Link: [https://youtu.be/Lq1Fb9qqc5Q](https://youtu.be/Lq1Fb9qqc5Q) 
- Project Link: [https://github.com/YEASEUL-JANG/Picplace](https://github.com/YEASEUL-JANG/Picplace)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## 💡 알게된 점

- [JPA 사용 시 QueryDSL을 활용하면 다중 검색 기능을 훨씬 쉽게 구현할 수 있다.](https://www.notion.so/56a072cf4f2d421ebb183091c096b78a?pvs=21)
- REST API의 응답값은 엔티티를 그대로 노출하기보단 별도의 DTO를 사용하는 것이 안정성과 확장성 측면에서 유리하다.
- JPA에서 컬렉션이 포함된 엔티티 조회 시 Fetch Join을 활용하면 N+1 문제 없이 한번의 쿼리로 데이터를 조회할 수 있다.
- 준영속 상태의 엔티티를 갱신할 땐 `merge()`보다는 **변경 감지(Dirty Checking)** 를 사용하는 것이 예기치 않은 데이터 손실을 방지할 수 있다.
- [직접 Dockerfile을 작성하고 이미지를 빌드하여 리눅스 서버에 배포하는 과정을 경험할 수 있었다.](https://www.notion.so/564331da6e154b6bb0bc8525b70bc738?pvs=21)
- [Spring Security와 JWT를 결합한 인증 과정의 로직 흐름을 이해할 수 있었다.](https://www.notion.so/Spring-Security-JWT-62baec985e8543d39e0d7ef131ddca46?pvs=21)
- [Vue에서 Nginx 배포 시 발생하는 경로 404 오류의 원인을 파악하고, try_files 설정을 통해 해결할 수 있었다.](https://www.notion.so/nginx-vue-404-80d64ec5ab4245afb7f0a1a70e726ca0?pvs=21)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
[Vue3]: https://img.shields.io/badge/Vue3-20B2AA?style=for-the-badge
[ElementPlus]: https://img.shields.io/badge/ElementPlus-skyblue?style=for-the-badge
[JAVA]: https://img.shields.io/badge/JAVA-orange?style=for-the-badge
[SpringBoot]: https://img.shields.io/badge/SpringBoot-navy?style=for-the-badge
[JPA]: https://img.shields.io/badge/JPA-red?style=for-the-badge
[Quartz]: https://img.shields.io/badge/Quartz-pink?style=for-the-badge
[AWS]: https://img.shields.io/badge/AWS-blue?style=for-the-badge
[Docker]: https://img.shields.io/badge/Docker-Docker?style=for-the-badge
[Jenkins]: https://img.shields.io/badge/Jenkins-purple?style=for-the-badge
[JavaScript]: https://img.shields.io/badge/JavaScript-yellow?style=for-the-badge
