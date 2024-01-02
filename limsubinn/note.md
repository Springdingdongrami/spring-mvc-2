# 1. 타임리프 - 기본 기능

### 프로젝트 생성

- Packaging: Jar
- Dependencies: Spring Web, Lombok, Thymeleaf

### 타임리프 소개

- 서버 사이드 HTML 렌더링 (SSR)
    - 타임리프는 백엔드 서버에서 HTML을 동적으로 렌더링 하는 용도로 사용된다.

- 네츄럴 템플릿
    - 타임리프는 순수 HTML을 최대한 유지하는 특징이 있다.
    - 순수 HTML을 그대로 유지하면서 뷰 템플릿도 사용할 수 있다.

- 스프링 통합 지원
    - 타임리프는 스프링과 자연스럽게 통합되고, 스프링의 다양한 기능을 편리하게 사용할 수 있게 지원한다.

- 타임리프 사용 선언
    - `<html xmlns:th="<http://www.thymeleaf.org>">`

### 텍스트 - text, utext

- 텍스트 출력 기본
    - `th:text` → `<span th:text="${data}">`
    - HTML 컨텐츠 영역 안에서 직접 출력하기 → `[[${data}]]`

- HTML 엔티티
    - 웹 브라우저는 <를 HTML 태그의 시작으로 인식한다. 따라서 `<` 를 태그의 시작이 아니라 문자로 표현할 수 있는 방법이 필요한데, 이것을 HTML 엔티티라 한다.
    - 이렇게 HTML에서 사용하는 특수 문자를 HTML 엔티티로 변경하는 것을 이스케이프(escape)라 한다.
    - 타임리프가 제공하는 `th:text` , `[[...]]` 는 **기본적으로 이스케이프(escape)를 제공**한다.

- unescape
    - `th:text` → `th:utext`
    - `[[…]]` → `[(…)]`
        - `th:inline="none"` : 타임리프는 `[[...]]` 를 해석하기 때문에, 화면에 `[[...]]` 글자를 보여줄 수 없다. 이 태그 안에서는 타임리프가 해석하지 말라는 옵션이다.

### 변수 - SpringEL

- 변수 표현식: `${…}`
    - 이 변수 표현식에는 스프링 EL 이라는 스프링이 제공하는 표현식을 사용할 수 있다.

- SpringEL 다양한 표현식 사용
    - Object
        - `user.username`
        - `user[’username’]`
        - `user.getUsername()`
    - List
        - `users[0].username`
        - `users[0][’username’]`
        - `users[0].getUsername()`
    - Map
        - `userMap[’userA’].username`
        - `userMap[’userA’][’username’]`
        - `userMap[’userA’].getUsername()`

- 지역 변수 선언: `th:with`
    - 지역 변수는 선언한 태그 안에서만 사용할 수 있다.
        
        ```html
        <h1>지역 변수 - (th:with)</h1>
        <div th:with="first=${users[0]}">
            <p>처음 사람의 이름은 <span th:text="${first.username}"></span></p>
        </div>
        ```
        

### 기본 객체들

- 기본 객체들
    - `${#locale}`
    - 스프링 부터 3.0부터 제공 X → 직접 `model`에 해당 객체를 추가해서 사용해야 한다.
        - `${#request}`
        - `${#response}`
        - `${#session}`
        - `${#servletContext}`

- `#request` 는 `HttpServletRequest` 객체가 그대로 제공되기 때문에 데이터를 조회하려면 `request.getParameter("data")` 처럼 불편하게 접근해야 한다. 이런 점을 해결하기 위해 편의 객체도 제공한다.
    - HTTP 요청 파라미터 접근: `param`
    - HTTP 세션 접근: `session`
    - 스프링 빈 접근: `@`

### 유틸리티 객체와 날짜

- 타임리프 유틸리티 객체들
    - `#message` : 메시지, 국제화 처리
    - `#uris` : URI 이스케이프 지원
    - `#dates` : `java.util.Date` 서식 지원
    - `#calendars` : `java.util.Calendar` 서식 지원
    - `#temporals` : 자바8 날짜 서식 지원
    - `#numbers` : 숫자 서식 지원
    - `#strings` : 문자 관련 편의 기능
    - `#objects` : 객체 관련 기능 제공
    - `#bools` : boolean 관련 기능 제공
    - `#arrays` : 배열 관련 기능 제공
    - `#lists` , `#sets` , `#maps` : 컬렉션 관련 기능 제공
    - `#ids` : 아이디 처리 관련 기능 제공

** 타임리프 유틸리티 객체

[https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expression-utility-objects](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expression-utility-objects)

** 유틸리티 객체 예시

[https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-b-expression-utility-objects](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-b-expression-utility-objects)

### URL 링크

- URL 생성: `@{…}`
    - 단순한 URL **/hello**
        - `@{/hello}`
    - 쿼리 파라미터 **/hello?param1=data1&param2=data2**
        - `@{/hello(param1=${param1}, param2=${param2})}`
    - 경로 변수 **/hello/data1/data2**
        - `@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}`
    - 경로 변수 + 쿼리 파라미터 **/hello/data1?param2=data2**
        - `@{/hello/{param1}(param1=${param1}, param2=${param2})}`
    - 상대경로, 절대경로, 프로토콜 기준을 표현할 수 있다.
        - 절대경로 `/hello`
        - 상대경로 `hello`

### 리터럴

- 리터럴
    - 소스 코드상에 고정된 값을 말하는 용어
    - 예를 들어 다음 코드에서 “Hello”는 문자 리터럴, 10은 숫자 리터럴
        
        ```java
        String a = "Hello"
        int b = 10
        ```
        
    - 타임리프는 다음과 같은 리터럴이 있다.
        - 문자: ‘hello’
        - 숫자: 10
        - boolean: true, false
        - null: null
    - 타임리프에서 문자 리터럴은 항상 `'` (작은 따옴표)로 감싸야 한다.
        - 공백 없이 쭉 이어진다면 하나의 의미있는 토큰으로 인지해서 다음과 같이 작은 따옴표를 생략할 수 있다.
            
            룰: `A-Z` , `a-z` , `0-9` , `[]` , `.` , `` , `_`
            
        - 예) `<span th:text="hello">`
        - 예) `<span th:text="'hello world!'"></span>`

- 리터럴 대체
    - `<span th:text="|hello ${data}|">`

### 연산

- 타임리프 연산은 자바와 크게 다르지 않다. HTML안에서 사용하기 때문에 HTML 엔티티를 사용하는 부분만 주의하자.
    - **비교연산**: HTML 엔티티를 사용해야 하는 부분을 주의하자.
        - `>` (gt)
        - `<` (lt)
        - `>=` (ge)
        - `<=` (le)
        - `!` (not)
        - `==` (eq)
        - `!=` (neq, ne)
    - **조건식**: 자바의 조건식과 유사하다.
    - **Elvis 연산자**: 조건식의 편의 버전
    - **No-Operation**: `_`인 경우 마치 타임리프가 실행되지 않는 것처럼 동작한다. 이것을 잘 사용하면 HTML의 내용 그대로 활용할 수 있다.

### 속성 값 설정

- 타임리프 태그 속성
    - 타임리프는 주로 HTML 태그에 `th:*` 속성을 지정하는 방식으로 동작한다.
    - `th:*` 로 속성을 적용하면 기존 속성을 대체한다.
    - 기존 속성이 없으면 새로 만든다.

- 속성 설정
    - `th:*` 속성을 지정하면 타임리프는 기존 속성을 `th:*` 로 지정한 속성으로 대체한다. 기존 속성이 없다면 새로 만든다.
    - `<input type="text" name="mock" th:name="userA" />`
    - 타임리프 렌더링 후 `<input type="text" name="userA" />`

- 속성 추가
    - `th:attrappend` : 속성 값의 뒤에 값을 추가한다.
    - `th:attrprepend` : 속성 값의 앞에 값을 추가한다.
    - `th:classappend` : class 속성에 자연스럽게 추가한다.

- checked 처리
    - HTML에서 `checked` 속성은 `checked` 속성의 값과 상관없이 `checked` 라는 속성만 있어도 체크가 된다.
        - `<input type="checkbox" name="active" checked="false" />` 
        → 이 경우에도 checked 속성이 있기 때문에 checked 처리가 되어버린다.
    - 타임리프의 `th:checked` 는 값이 `false` 인 경우 `checked` 속성 자체를 제거한다.
        - `<input type="checkbox" name="active" th:checked="false" />`
        - 타임리프 렌더링 후: `<input type="checkbox" name="active" />`

### 반복

- 반복 : `th:each`
    - `List` 뿐만 아니라 배열, `java.util.Iterable` , `java.util.Enumeration` 을 구현한 모든 객체를 반복에 사용할 수 있다. `Map` 도 사용할 수 있는데 이 경우 변수에 담기는 값은 `Map.Entry` 이다.
    - 반복 상태 유지 `<tr th:each="user, userStat : ${users}">`
        - 반복의 두 번째 파라미터를 설정해서 반복의 상태를 확인할 수 있다.
            - `index` : 0부터 시작하는 값
            - `count` : 1부터 시작하는 값
            - `size` : 전체 사이즈
            - `even` , `odd` : 홀수, 짝수 여부 (boolean)
            - `first` , `last` :처음, 마지막 여부 (boolean)
            - `current` : 현재 객체
        - 두번째 파라미터는 생략 가능한데, 생략하면 지정한 변수명(`user`) + `Stat` 이 된다.

### 조건부 평가

- 타임리프의 조건식
    - `if` , `unless`
        - 타임리프는 해당 조건이 맞지 않으면 태그 자체를 렌더링하지 않는다.
        - 만약 다음 조건이 `false` 인 경우 `<span>...<span>` 부분 자체가 렌더링 되지 않고 사라진다.
            
            ```html
            <span th:text="'미성년자'" th:if="${user.age lt 20}"></span>
            ```
            
    - `switch` , `case`
        - `*`은 만족하는 조건이 없을 때 사용하는 디폴트이다.

### 주석

- 표준 HTML 주석
    
    ```html
    <!--
    <span th:text="${data}">html data</span>
    -->
    ```
    
    - 자바스크립트의 표준 HTML 주석은 타임리프가 렌더링 하지 않고, 그대로 남겨둔다.

- 타임리프 파서 주석
    
    ```html
    1.
    <!--/* [[${data}]] */-->
    
    2.
    <!--/*-->
    <span th:text="${data}">html data</span>
    <!--*/-->
    ```
    
    - 타임리프 파서 주석은 타임리프의 진짜 주석이다. 렌더링에서 주석 부분을 제거한다.

- 타임리프 프로토타입 주석
    
    ```html
    <!--/*/
    <span th:text="${data}">html data</span> 
    /*/-->
    ```
    
    - HTML 파일을 그대로 열어보면 주석처리가 되지만, 타임리프를 렌더링 한 경우에만 보이는 기능이다.
        - **HTML 파일**을 웹 브라우저에서 그대로 열어보면 HTML 주석이기 때문에 이 부분이 웹 브라우저가 렌더링하지 않는다.
        - **타임리프 렌더링**을 거치면 이 부분이 정상 렌더링 된다.

- 코드 vs 결과
    - 코드 <br>
    
        <img width="400" alt="image" src="https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/32ee8bc6-6b65-40cf-854f-47c284b56de5">

        
    - 결과 <br>
 
        <img width="400" alt="image" src="https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/09eaa632-595e-431b-9463-46ff110f9421">
        

### 블록

- 블록 : `<th:block>`
    - HTML 태그가 아닌 타임리프의 유일한 자체 태그이다.
    - 타임리프의 특성상 HTML 태그안에 속성으로 기능을 정의해서 사용하는데, 이렇게 사용하기 애매한 경우에 사용하면 된다.
    - `<th:block>` 은 렌더링시 제거된다.

### 자바스크립트 인라인

- 자바스크립트 인라인 기능 : `<script th:inline=”javascript”>`
    - 텍스트 렌더링
        - `var username = [[${user.username}]];`
            - 인라인 사용 전 `var username = userA;`
            - 인라인 사용 후 `var username = "userA";` → 문자 타입인 경우 `"`를 포함, 자바스크립트에서 문제가 될 수 있는 문자가 포함되어 있으면 이스케이프 처리
    - 자바스크립트 내추럴 템플릿
        - `var username2 = /*[[${user.username}]]*/ "test username";`
            - 인라인 사용 전 `var username2 = /*userA*/ "test username";`
            - 인라인 사용 후 `var username2 = "userA";` → 주석 부분이 제거되고, 기대한 "userA"가 정확하게 적용된다.
    - 객체
        - `var user = [[${user}]];`
            - 인라인 사용 전 `var user = BasicController.User(username=userA, age=10);` → 객체의 toString()이 호출된 값
            - 인라인 사용 후 `var user = {"username":"userA","age":10};` → 객체를 JSON으로 변환

- 자바스크립트 인라인 each
    - 코드
        
        ```html
        <script th:inline="javascript">
        
            [# th:each="user, stat : ${users}"]
            var user[[${stat.count}]] = [[${user}]];
            [/]
        
        </script>
        ```
        
    - 결과
        
        ```html
        <script>
         var user1 = {"username":"userA","age":10};
         var user2 = {"username":"userB","age":20};
         var user3 = {"username":"userC","age":30};
        </script>
        ```
        

### 템플릿 조각

- 타임리프의 템플릿 조각과 레이아웃 기능
    - 웹 페이지를 개발할 때는 공통 영역이 많이 있다.
    - 예를 들어서 상단 영역이나 하단 영역, 좌측 카테고리 등등 여러 페이지에서 함께 사용하는 영역들이 있다.
    - 이런 부분을 코드를 복사해서 사용한다면 변경시 여러 페이지를 다 수정해야 하므로 상당히 비효율적이다.

- controller
    
    ```java
    package hello.thymeleaf.basic;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    
    @Controller
    @RequestMapping("/template")
    public class TemplateController {
    
        @GetMapping("/fragment")
        public String template() {
            return "template/fragment/fragmentMain";
        }
    
    }
    ```
    

- footer.html
    
    ```html
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
    
    <body>
    
    <footer th:fragment="copy">
        푸터 자리 입니다.
    </footer>
    
    <footer th:fragment="copyParam (param1, param2)">
        <p>파라미터 자리 입니다.</p>
        <p th:text="${param1}"></p>
        <p th:text="${param2}"></p>
    </footer>
    
    </body>
    
    </html>
    ```
    
    - `th:fragment` 가 있는 태그는 다른곳에 포함되는 코드 조각

- fragmentMain.html
    
    ```html
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
    <h1>부분 포함</h1>
    <h2>부분 포함 insert</h2>
    <div th:insert="~{template/fragment/footer :: copy}"></div>
    
    <h2>부분 포함 replace</h2>
    <div th:replace="~{template/fragment/footer :: copy}"></div>
    
    <h2>부분 포함 단순 표현식</h2>
    <div th:replace="template/fragment/footer :: copy"></div>
    
    <h1>파라미터 사용</h1>
    <div th:replace="~{template/fragment/footer :: copyParam ('데이터1', '데이터2')}"></div>
    </body>
    </html>
    ```
    
    - `template/fragment/footer :: copy`
        - template/fragment/footer.html 템플릿에 있는 `th:fragment="copy"` 라는 부분을 템플릿 조각으로 가져와서 사용한다는 의미
    - 부분 포함 insert `th:insert`
        - `<div th:insert="~{template/fragment/footer :: copy}"></div>`
        - 현재 태그(`div`) 내부에 추가
    - 부분 포함 replace `th:replace`
        - `<div th:replace="~{template/fragment/footer :: copy}"></div>`
        - 현재 태그(`div`)를 대체
        - 부분 포함 단순 표현식: `~{...}` 를 사용하는 것이 원칙이지만 템플릿 조각을 사용하는 코드가 단순하면 이 부분을 생략할 수 있다.
        `<div th:replace="template/fragment/footer :: copy"></div>`
    - 파라미터 사용
        - `<div th:replace="~{template/fragment/footer :: copyParam ('데이터1', '데이터2')}"></div>`
        - 파라미터를 전달해서 동적으로 조각을 렌더링

### 템플릿 레이아웃1

- 코드 조각을 레이아웃에 넘겨서 사용하는 방법을 알아보자.
    - 예) `<head>` 에 공통으로 사용하는 `css` , `javascript` 같은 정보들이 있는데, 이러한 공통 정보들을 한 곳에 모아두고, 공통으로 사용하지만, 각 페이지마다 필요한 정보를 더 추가해서 사용

- controller
    
    ```java
    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layoutMain";
    }
    ```
    

- base.html
    
    ```html
    <html xmlns:th="http://www.thymeleaf.org">
    <head th:fragment="common_header(title,links)">
    
        <title th:replace="${title}">레이아웃 타이틀</title>
    
        <!-- 공통 -->
        <link rel="stylesheet" type="text/css" media="all" th:href="@{/css/awesomeapp.css}">
        <link rel="shortcut icon" th:href="@{/images/favicon.ico}">
        <script type="text/javascript" th:src="@{/sh/scripts/codebase.js}"></script>
    
        <!-- 추가 -->
        <th:block th:replace="${links}" />
    
    </head>
    ```
    

- layoutMain.html
    
    ```html
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
    <head th:replace="template/layout/base :: common_header(~{::title},~{::link})">
        <title>메인 타이틀</title>
        <link rel="stylesheet" th:href="@{/css/bootstrap.min.css}">
        <link rel="stylesheet" th:href="@{/themes/smoothness/jquery-ui.css}">
    </head>
    <body>
    메인 컨텐츠
    </body>
    </html>
    ```
    
    - `common_header(~{::title},~{::link})`
        - `::title` 은 현재 페이지의 title 태그들을 전달한다.
        - `::link` 는 현재 페이지의 link 태그들을 전달한다.

- 결과
    
    ```html
    <!DOCTYPE html>
    <html>
    <head>
    
        <title>메인 타이틀</title>
    
        <!-- 공통 -->
        <link rel="stylesheet" type="text/css" media="all" href="/css/awesomeapp.css">
        <link rel="shortcut icon" href="/images/favicon.ico">
        <script type="text/javascript" src="/sh/scripts/codebase.js"></script>
    
        <!-- 추가 -->
        <link rel="stylesheet" href="/css/bootstrap.min.css"><link rel="stylesheet" href="/themes/smoothness/jquery-ui.css">
    
    </head>
    <body>
    메인 컨텐츠
    </body>
    </html>
    ```
    
    - 메인 타이틀이 전달한 부분으로 교체되었다.
    - 공통 부분은 그대로 유지되고, 추가 부분에 전달한 `<link>` 들이 포함된 것을 확인할 수 있다.

### 템플릿 레이아웃2

- 템플릿 레이아웃 확장
    - 앞서 이야기한 개념을 `<head>` 정도에만 적용하는게 아니라 `<html>` 전체에 적용할 수도 있다.

- controller
    
    ```java
    @GetMapping("/layoutExtend")
    public String layoutExtends() {
        return "/template/layoutExtend/layoutExtendMain";
    }
    ```
    

- layoutFile.html
    
    ```html
    <!DOCTYPE html>
    <html th:fragment="layout (title, content)" xmlns:th="http://www.thymeleaf.org">
    <head>
        <title th:replace="${title}">레이아웃 타이틀</title>
    </head>
    <body>
    <h1>레이아웃 H1</h1>
    <div th:replace="${content}">
        <p>레이아웃 컨텐츠</p>
    </div>
    <footer>
        레이아웃 푸터
    </footer>
    </body>
    </html>
    ```
    

- layoutExtendMain.html
    
    ```html
    <!DOCTYPE html>
    <html th:replace="~{template/layoutExtend/layoutFile :: layout(~{::title}, ~{::section})}"
          xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>메인 페이지 타이틀</title>
    </head>
    <body>
    <section>
        <p>메인 페이지 컨텐츠</p>
        <div>메인 페이지 포함 내용</div>
    </section>
    </body>
    </html>
    ```
    

- 결과
    
    ```html
    <!DOCTYPE html>
    <html>
    <head>
        <title>메인 페이지 타이틀</title>
    </head>
    <body>
    <h1>레이아웃 H1</h1>
    <section>
        <p>메인 페이지 컨텐츠</p>
        <div>메인 페이지 포함 내용</div>
    </section>
    <footer>
        레이아웃 푸터
    </footer>
    </body>
    </html>
    ```
    
    - **layoutFile.html** 을 보면 기본 레이아웃을 가지고 있는데, `<html>` 에 `th:fragment` 속성이 정의되어 있다. 이 레이아웃 파일을 기본으로 하고 여기에 필요한 내용을 전달해서 부분부분 변경하는 것으로 이해하면 된다.
    - **layoutExtendMain.html** 는 현재 페이지인데, `<html>` 자체를 `th:replace` 를 사용해서 변경하는 것을 확인 할 수 있다. 결국 **layoutFile.html** 에 필요한 내용을 전달하면서 `<html>` 자체를 **layoutFile.html** 로 변경 한다.

# 2. 타임리프 - 스프링 통합과 폼

### 프로젝트 설정

- form-start(mvc 1편) → form
- 스프링 부트 & 자바 버전 바꾸기
    - Settings → Build, Execution, Deployment
        - Build Tools → Gradle → JVM 버전 변경
        - Compiler → Java Compiler → Project bytecode version 변경
    - Project Structure → Project Settings → Project → SDK 버전 변경
    - build.gradle
        
        ```
        plugins {
        	id 'org.springframework.boot' version '3.2.1'
        	id 'io.spring.dependency-management' version '1.1.4'
        	id 'java'
        }
        
        group = 'hello'
        version = '0.0.1-SNAPSHOT'
        
        java {
        	sourceCompatibility = '17'
        }
        
        configurations {
        	compileOnly {
        		extendsFrom annotationProcessor
        	}
        }
        
        repositories {
        	mavenCentral()
        }
        
        dependencies {
        	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
        	implementation 'org.springframework.boot:spring-boot-starter-web'
        	compileOnly 'org.projectlombok:lombok'
        	annotationProcessor 'org.projectlombok:lombok'
        	testImplementation 'org.springframework.boot:spring-boot-starter-test'
        }
        
        tasks.named('test') {
        	useJUnitPlatform()
        }
        ```
        
- PermittedSubclasses requires ASM9 에러
    - gradle/wrapper/gradle-wrapper.properties
        
        ```
        # 수정 전
        distributionUrl=https\:*//services.gradle.org/distributions/gradle-7.2-bin.zip
        
        # 수정 후
        distributionUrl=https\:*//services.gradle.org/distributions/gradle-7.6-bin.zip
        ```
        

### 타임리프 스프링 통합

- 타임리프는 스프링 없이도 동작하지만, 스프링과 통합을 위한 다양한 기능을 편리하게 제공한다.
    - 스프링의 SpringEL 문법 통합
    - `${@myBean.doSomething()}` 처럼 스프링 빈 호출 지원
    - 편리한 폼 관리를 위한 추가 속성
        - `th:object` (기능 강화, 폼 커맨드 객체 선택)
        - `th:field` , `th:errors` , `th:errorclass`
    - 폼 컴포넌트 기능
        - checkbox, radio button, List 등을 편리하게 사용할 수 있는 기능 지원
        - 스프링의 메시지, 국제화 기능의 편리한 통합
        - 스프링의 검증, 오류 처리 통합
        - 스프링의 변환 서비스 통합(ConversionService)

- 스프링 부트는 타임리프 템플릿 엔진을 스프링 빈에 등록하고, 타임리프용 뷰 리졸버를 스프링 빈으로 등록하는 부분을 모두 자동화해준다.
    - `implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'`
    - Gradle은 타임리프와 관련된 라이브러리를 다운로드 받고, 스프링 부트는 타임리프와 관련된 설정용 스프링 빈을 자동으로 등록해준다.

** 타임리프 기본 메뉴얼: [https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

** 타임리프 스프링 통합 메뉴얼: [https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html) 

### 입력 폼 처리

- 입력 폼 처리
    - `th:object` : 커맨드 객체 지정
    - `*{…}` : 선택 변수 식 → **th:object**에서 선택한 객체에 접근
    - `th:field` : HTML 태그의 **id**, **name**, **value** 속성을 자동으로 처리

- 등록 폼
    - `th:object` 를 적용하려면 먼저 해당 오브젝트 정보를 넘겨주어야 한다. 등록 폼이기 때문에 데이터가 비어있는 빈 오브젝트를 만들어서 뷰에 전달하자.
        
        ```java
        @GetMapping("/add")
        public String addForm(Model model) {
            model.addAttribute("item", new Item());
            return "form/addForm";
        }
        ```
        
    - 타임리프 등록 폼
        
        ```html
        <form action="item.html" th:action th:object="${item}" method="post">
            <div>
                <label for="itemName">상품명</label>
                <input type="text" id="itemName" th:field="*{itemName}" class="form-control" placeholder="이름을 입력하세요">
            </div>
            <div>
                <label for="price">가격</label>
                <input type="text" id="price" th:field="*{price}" class="form-control" placeholder="가격을 입력하세요">
            </div>
            <div>
                <label for="quantity">수량</label>
                <input type="text" id="quantity" th:field="*{quantity}" class="form-control" placeholder="수량을 입력하세요">
            </div>
        		...
        </form>
        ```
        

- 수정 폼
    
    ```html
    <form action="item.html" th:action th:object="${item}" method="post">
        <div>
            <label for="id">상품 ID</label>
            <input type="text" id="id" class="form-control" th:field="*{id}" readonly>
        </div>
        <div>
            <label for="itemName">상품명</label>
            <input type="text" id="itemName" class="form-control" th:field="*{itemName}">
        </div>
        <div>
            <label for="price">가격</label>
            <input type="text" id="price" class="form-control" th:field="*{price}">
        </div>
        <div>
            <label for="quantity">수량</label>
            <input type="text" id="quantity" class="form-control" th:field="*{quantity}">
        </div>
    		...
    </form>
    ```
    

### 요구사항 추가

- 요구사항 추가
    - 판매 여부
        - 판매 오픈 여부
        - 체크 박스로 선택
    - 등록 지역
        - 서울, 부산, 제주
        - 체크 박스로 다중 선택
    - 상품 종류
        - 도서, 식품, 기타
        - 라디오 버튼으로 하나만 선택
    - 배송 방식
        - 빠른/일반/느린
        - 셀렉트 박스로 하나만 선택

- ItemType (상품 종류)
    - enum class
    - 설명을 위해 description 필드 추가

- DeliveryCode (배송 방식)
    - code: `FAST` 같은 시스템에서 전달하는 값
    - displayName: `빠른 배송` 같은 고객에게 보여주는 값

- Item (상품)
    - ENUM , 클래스, String 같은 다양한 상황을 준비

### 체크 박스 - 단일 1

- 히든 필드
    - HTML checkbox는 선택이 안되면 클라이언트에서 서버로 값 자체를 보내지 않는다.
        
        ```java
        // 실행 로그
        FormItemController     : item.open=true // 체크 박스 선택 O
        FormItemController     : item.open=null // 체크 박스 선택 X
        ```
        
    - 이런 문제를 해결하기 위해서 스프링 MVC는 약간의 트릭을 사용하는데, 히든 필드를 하나 만들어서, `_open` 처럼 기존 체크 박스 이름 앞에 언더스코어(`_`)를 붙여서 전송하면 체크를 해제했다고 인식할 수 있다.
    - 히든 필드는 항상 전송된다.
    - 따라서 체크를 해제한 경우 여기에서 `open` 은 전송되지 않고, `_open` 만 전송되는데, 이 경우 스프링 MVC는 체크를 해제했다고 판단한다.
        
        ```java
        // 실행 로그
        FormItemController     : item.open=true // 체크 박스 선택 O
        FormItemController     : item.open=false // 체크 박스 선택 X
        ```
        
        - 체크 박스를 체크하면 스프링 MVC가 `open` 에 값이 있는 것을 확인하고 사용한다. 이때 `_open` 은 무시한다.
        - 체크 박스를 체크하지 않으면 스프링 MVC가 `_open` 만 있는 것을 확인하고, `open` 의 값이 체크되지 않았다고 인식한다.

### 체크 박스 - 단일 2

- 개발할 때마다 이렇게 히든 필드를 추가하는 것은 상당히 번거롭다. 타임리프가 제공하는 폼 기능을 사용하면 이런 부분을 자동으로 처리할 수 있다.
    - 상품 추가, 상품 상세, 상품 수정 html 코드에 적용
    - ItemRepository → update 수정

### 체크 박스 - 멀티

- FormItemController에 추가
    
    ```java
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }
    ```
    
    - 등록 폼, 상세화면, 수정 폼에서 모두 <서울, 부산, 제주>라는 체크 박스를 반복해서 보여주어야 한다.
    - 이렇게 하려면 각각의 컨트롤러에서 `model.addAttribute(...)`을 사용해서 체크 박스를 구성하는 데이터를 반복해서 넣어주어야 한다.
    - `@ModelAttribute`는 이렇게 컨트롤러에 있는 별도의 메서드에 적용할 수 있다.
    - 이렇게하면 해당 컨트롤러를 요청할 때 `regions`에서 반환한 값이 자동으로 모델(`model`)에 담기게 된다.

- 멀티 체크박스
    
    ```html
    <!-- multi checkbox -->
    <div>
        <div>등록 지역</div>
        <div th:each="region : ${regions}" class="form-check form-check-inline">
            <input type="checkbox" th:field="*{regions}" th:value="${region.key}" class="form-check-input">
            <label th:for="${#ids.prev('regions')}"
                   th:text="${region.value}" class="form-check-label">서울</label>
        </div>
    </div>
    ```
    
    - `th:for="${#ids.prev('regions')}"`
    - 멀티 체크박스는 같은 이름의 여러 체크박스를 만들 수 있다.
    - 이렇게 반복해서 HTML 태그를 생성할 때, 생성된 HTML 태그 속성에서 `name`은 같아도 되지만, `id`는 모두 달라야 한다.
    - 따라서 타임리프는 체크박스를 `each` 루프 안에서 반복해서 만들 때 임의로 `1`, `2`, `3` 숫자를 뒤에 붙여준다.
        
        ```html
        <input type="checkbox" value="SEOUL" class="form-check-input" id="regions1" name="regions">
        <input type="checkbox" value="BUSAN" class="form-check-input" id="regions2" name="regions">
        <input type="checkbox" value="JEJU" class="form-check-input" id="regions3" name="regions">
        ```
        
    - 타임리프는 `ids.prev(...)`, `ids.next(...)` 을 제공해서 동적으로
    생성되는 `id` 값을 사용할 수 있도록 한다.
    - 타임리프 HTML 생성 결과
        
        ```html
        <!-- multi checkbox -->
        <div>
            <div>등록 지역</div>
            <div class="form-check form-check-inline">
                <input type="checkbox" value="SEOUL" class="form-check-input" id="regions1" name="regions"><input type="hidden" name="_regions" value="on"/>
                <label for="regions1"
                       class="form-check-label">서울</label>
            </div>
            <div class="form-check form-check-inline">
                <input type="checkbox" value="BUSAN" class="form-check-input" id="regions2" name="regions"><input type="hidden" name="_regions" value="on"/>
                <label for="regions2"
                       class="form-check-label">부산</label>
            </div>
            <div class="form-check form-check-inline">
                <input type="checkbox" value="JEJU" class="form-check-input" id="regions3" name="regions"><input type="hidden" name="_regions" value="on"/>
                <label for="regions3"
                       class="form-check-label">제주</label>
            </div>
        </div>
        ```
        

### 라디오 버튼

- FormItemController에 추가
    
    ```java
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }
    ```
    
    - `ItemType.values()` 를 사용하면 해당 ENUM의 모든 정보를 배열로 반환한다. 예) `[BOOK, FOOD, ETC]`

** 라디오 버튼은 이미 선택이 되어 있다면, 수정시에도 항상 하나를 선택하도록 되어 있으므로 체크 박스와 달리 별도의 히든 필드를 사용할 필요가 없다.

### 셀렉트 박스

- FormItemController에 추가
    
    ```java
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }
    ```
    
    ** `@ModelAttribute` 가 있는 `deliveryCodes()` 메서드는 컨트롤러가 호출 될 때 마다 사용되므로 `deliveryCodes` 객체도 계속 생성된다. 이런 부분은 미리 생성해두고 재사용하는 것이 더 효율적이다.
