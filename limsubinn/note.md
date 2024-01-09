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
        distributionUrl=https\://services.gradle.org/distributions/gradle-7.2-bin.zip
        
        # 수정 후
        distributionUrl=https\://services.gradle.org/distributions/gradle-7.6-bin.zip
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

# 3. 메시지, 국제화

### 프로젝트 설정

- message-start(타임리프 - 스프링 통합과 폼) → message
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
        distributionUrl=https\://services.gradle.org/distributions/gradle-6.8.2-bin.zip
        
        # 수정 후
        distributionUrl=https\://services.gradle.org/distributions/gradle-7.6-bin.zip
        ```
        

### 메시지, 국제화 소개

- 메시지
    - 다양한 메시지를 한 곳에서 관리하도록 하는 기능
    - 예) `messages.properties` 라는 메시지 관리용 파일을 만들고
        
        ```
        item=상품
        item.id=상품 ID
        item.itemName=상품명
        item.price=가격
        item.quantity=수량
        ```
        
        각 HTML들은 다음과 같이 해당 데이터를 key 값으로 불러서 사용하는 것이다.
        
        ```html
        <!-- addForm.html -->
        <label for="itemName" th:text="#{item.itemName}"></label>
        
        <!-- editForm.html -->
        <label for="itemName" th:text="#{item.itemName}"></label>
        ```
        

- 국제화
    - 메시지에서 한 발 더 나가서, 메시지에서 설명한 메시지 파일(`message.properties`)을 각 나라별로 별도로 관리하면 서비스를 국제화 할 수 있다.
    - 예) 다음과 같이 2개의 파일을 만들어서 분류한다.
        
        ```
        <messages_en.properties>
        item=Item
        item.id=Item ID
        item.itemName=Item Name
        item.price=price
        item.quantity=quantity
        
        <messages_ko.properties>
        item=상품
        item.id=상품 ID
        item.itemName=상품명
        item.price=가격
        item.quantity=수량
        ```
        

- 메시지와 국제화 기능을 직접 구현할 수도 있겠지만, 스프링은 기본적인 메시지와 국제화 기능을 모두 제공한다. 그리고 타임리프도 스프링이 제공하는 메시지와 국제화 기능을 편리하게 통합해서 제공한다.

### 스프링 메시지 소스 설정

- 메시지 관리 기능을 사용하려면 스프링이 제공하는 `MessageSource` 를 스프링 빈으로 등록하면 되는데, `MessageSource` 는 인터페이스이다. 따라서 구현체인 `ResourceBundleMessageSource` 를 스프링 빈으로 등록하면 된다.
    
    ```java
    @Bean
    public MessageSource messageSource() {
    		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "errors");
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }
    ```
    
    - `basenames` : 설정 파일의 이름을 지정한다.
        - `messages` 로 지정하면 `messages.properties` 파일을 읽어서 사용한다.
        - 추가로 국제화 기능을 적용하려면 `messages_en.properties` , `messages_ko.properties` 와 같이 파일명 마지막에 언어 정보를 주면 된다. 만약 찾을 수 있는 국제화 파일이 없으면 `messages.properties` (언어정보가 없는 파일명)를 기본으로 사용한다.
        - 파일의 위치는 `/resources/messages.properties` 에 두면 된다.
        - 여러 파일을 한번에 지정할 수 있다. 여기서는 `messages` , `errors` 둘을 지정했다.
    - `defaultEncoding` : 인코딩 정보를 지정한다. `utf-8` 을 사용하면 된다.

- 스프링 부트를 사용하면 스프링 부트가 `MessageSource` 를 자동으로 스프링 빈으로 등록한다.
    - 메시지 소스 설정 (application.properties)
    `spring.messages.basename=messages,config.i18n.messages`
    - 메시지 소스 기본 값
    `spring.messages.basename=messages`

- 메시지 파일 만들기
    - /resources/messages.properties
        
        ```
        hello=안녕
        hello.name=안녕 {0}
        ```
        
    - /resources/messages_en.properties
        
        ```
        hello=hello
        hello.name=hello {0}
        ```
        

### 스프링 메시지 소스 사용

- `MessageSource` 인터페이스를 보면 코드를 포함한 일부 파라미터로 메시지를 읽어오는 기능을 제공한다.
    
    ```java
    public interface MessageSource {
         String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale);
         String getMessage(String code, @Nullable Object[] args, Locale locale)throws NoSuchMessageException;
    }
    ```
    

- `ms.getMessage("hello", null, null)`
    - code: hello, args: null, locale: null
    - `Locale` 정보가 없는 경우 `Locale.getDefault()` 을 호출해서 시스템의 기본 로케일을 사용한다.
    예) locale = null 인 경우 시스템 기본 locale 이 ko_KR 이므로 `messages_ko.properties` 조회 시도 → 조회 실패 → `messages.properties` 조회

- properties 파일 UTF-8 인코딩
    
    ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/8fd3b283-8df7-47a8-9deb-bbfdcba75a81)

    
    - Settings - Editor - File Encodings - Properties Files

### 웹 애플리케이션에 메시지 적용하기

- application.properties
    
    ```
    hello=안녕
    hello.name=안녕 {0}
    
    label.item=상품
    label.item.id=상품 ID
    label.item.itemName=상품명
    label.item.price=가격
    label.item.quantity=수량
    
    page.items=상품 목록
    page.item=상품 상세
    page.addItem=상품 등록
    page.updateItem=상품 수정
    
    button.save=저장
    button.cancel=취소
    ```
    

- 타임리프의 메시지 표현식 `#{…}`를 사용하면 스프링의 메시지를 편리하게 조회할 수 있다.
    - 예) 방금 등록한 상품이라는 이름을 조회하려면 `#{label.item}` 이라고 하면 된다.

### 웹 애플리케이션에 국제화 적용하기

- messages_en.properties - 영어 메시지 추가
    
    ```
    hello=hello
    hello.name=hello {0}
    
    label.item=Item
    label.item.id=Item ID
    label.item.itemName=Item Name
    label.item.price=price
    label.item.quantity=quantity
    
    page.items=Item List
    page.item=Item Detail
    page.addItem=Item Add
    page.updateItem=Item Update
    
    button.save=Save
    button.cancel=Cancel
    ```
    

- 웹으로 확인하기
    - 웹 브라우저의 언어 설정 값을 변경하면서 국제화 적용을 확인해보자.
    - 크롬 브라우저 설정 언어를 검색하고, 우선 순위를 변경하면 된다.
    - 웹 브라우저의 언어 설정 값을 변경하면 요청시 `Accept-Language` 의 값이 변경된다.

- 스프링의 국제화 메시지 선택
    - 메시지 기능은 `Locale` 정보를 알아야 언어를 선택할 수 있다.
    - 스프링도 `Locale` 정보를 알아야 언어를 선택할 수 있는데, 스프링은 언어 선택시 기본으로 `Accept-Language` 헤더의 값을 사용한다.
    - 스프링은 `Locale` 선택 방식을 변경할 수 있도록 `LocaleResolver` 라는 인터페이스를 제공하는데, 스프링 부트는 기본으로 `Accept-Language` 를 활용하는 `AcceptHeaderLocaleResolver` 를 사용한다.
    - 만약 `Locale` 선택 방식을 변경하려면 `LocaleResolver` 의 구현체를 변경해서 쿠키나 세션 기반의 `Locale` 선택 기능을 사용할 수 있다. 예를 들어서 고객이 직접 `Locale` 을 선택하도록 하는 것이다.
 

  # 4. 검증1 - Validation

### 검증 요구사항

- 검증 로직 추가
    - 타입 검증
        - 가격, 수량에 문자가 들어가면 검증 오류 처리
    - 필드 검증
        - 상품명: 필수, 공백 X
        - 가격: 1000원 이상, 1백만원 이하
        - 수량: 최대 9999
    - 특정 필드의 범위를 넘어서는 검증
        - 가격 * 수량의 합은 10,000원 이상

** **컨트롤러의 중요한 역할중 하나는 HTTP 요청이 정상인지 검증하는 것이다.**

- 클라이언트 검증 vs 서버 검증
    - 클라이언트 검증은 조작할 수 있으므로 보안에 취약하다.
    - 서버만으로 검증하면, 즉각적인 고객 사용성이 부족해진다.
    - 둘을 적절히 섞어서 사용하되, 최종적으로 서버 검증은 필수
    - API 방식을 사용하면 API 스펙을 잘 정의해서 검증 오류를 API 응답 결과에 잘 남겨주어야 한다.

### 프로젝트 설정 V1

- validation-start → validation
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
        distributionUrl=https\://services.gradle.org/distributions/gradle-6.8.2-bin.zip
        
        # 수정 후
        distributionUrl=https\://services.gradle.org/distributions/gradle-7.6-bin.zip
        ```
        

### 검증 직접 처리

- 상품 저장 성공
    
    ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/10f9898f-aae2-47f3-a564-5241d23b0de3)

    
    - 사용자가 상품 등록 폼에서 정상 범위의 데이터를 입력하면, 서버에서는 검증 로직이 통과하고, 상품을 저장하고, 상품 상세 화면으로 redirect
    
- 상품 저장 검증 실패
    
    ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/f63a3a61-afc8-4449-bc85-ad16712d08c1)
    
    - 고객이 상품 등록 폼에서 상품명을 입력하지 않거나 가격, 수량 등이 너무 작거나 커서 검증 범위를 넘어서면 서버 검증 로직이 실패해야 한다.
    - 이렇게 검증에 실패한 경우 고객에게 다시 상품 등록 폼을 보여주고, 어떤 값을 잘못 입력했는지 친절하게 알려주어야 한다.
    - 검증 오류가 발생해도 고객이 입력한 데이터가 유지된다.

- 검증시 오류가 발생하면 `errors` 에 담아둔다. 이때 어떤 필드에서 오류가 발생했는지 구분하기 위해 오류가 발생한 필드명을 `key` 로 사용한다. 이후 뷰에서 이 데이터를 사용해서 고객에게 친절한 오류 메시지를 출력할 수 있다.
    - 만약 검증에서 오류 메시지가 하나라도 있으면 오류 메시지를 출력하기 위해 `model` 에 `errors` 를 담고, 입력 폼이 있는 뷰 템플릿으로 보낸다.

- 남은 문제점
    - 뷰 템플릿에서 중복 처리가 많다.
    - 타입 오류 처리가 안된다.
    - 타입 오류가 발생해도 고객이 입력한 문자를 화면에 남겨야 한다.
    - 결국 고객이 입력한 값도 어딘가에 별도로 관리가 되어야 한다.

### 프로젝트 준비 V2

- ValidationItemControllerV2 컨트롤러 생성
- 템플릿 파일 복사

### BindingResult1

- 스프링이 제공하는 검증 오류 처리 방법
    - 필드 오류 - `FieldError` 객체를 생성해서 `BindingResult`에 담아둔다.
        
        ```java
        public FieldError(String objectName, String field, String defaultMessage);
        ```
        
        - `objectName`: @ModelAttribute의 이름
        - `field`: 오류가 발생한 필드 이름
        - `defaultMessage`: 오류 기본 메시지
    - 글로벌 오류 - `ObjectError` 객체를 생성해서 `BindingResult`에 담아둔다.
        
        ```java
        public ObjectError(String objectName, String defaultMessage);
        ```
        
        - `objectName` : @ModelAttribute의 이름
        - `defaultMessage` : 오류 기본 메시지

- 타임리프 스프링 검증 오류 통합 기능 - `BindingResult` 활용
    - `#fields`: BindingResult가 제공하는 검증 오류에 접근할 수 있다.
    - `th:errors`: 해당 필드에 오류가 있는 경우에 태그를 출력한다. `th:if`의 편의 버전이다.
    - `th:errorclass`: `th:field`에서 지정한 필드에 오류가 있으면 `class` 정보를 추가한다.

- BindingResult
    - 스프링이 제공하는 검증 오류를 보관하는 객체
    - `BindingResult`가 있으면 `@ModelAttribute`에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다. - 오류 정보(`FieldError`)를 `BindingResult`에 담아서 컨트롤러를 정상 호출한다.
    - `BindingResult`는 검증할 대상 바로 다음에 와야한다. 순서가 중요하다. 예를 들어서 `@ModelAttribute Item item`, 바로 다음에 `BindingResult`가 와야 한다.
    - `BindingResult`는 `Model`에 자동으로 포함된다.

- BindingResult에 검증 오류를 적용하는 방법
    - `@ModelAttribute`의 객체에 타입 오류 등으로 바인딩이 실패하는 경우 스프링이 `FieldError` 생성해서 `BindingResult`에 넣어준다.
    - 개발자가 직접 넣어준다.
    - `Validator` 사용

- BindingResult와 Errors
    - `BindingResult`는 인터페이스이고, `Errors` 인터페이스를 상속받고 있다.
    - `Errors` 인터페이스는 단순한 오류 저장과 조회 기능을 제공한다.
    - `BindingResult` 는 여기에 더해서 추가적인 기능들을 제공한다.

### FieldError, ObjectError

- 사용자 입력 오류 시 입력한 값이 화면에 남도록 하자.

- FieldError, ObjectError - 2가지 생성자
    
    ```java
    public FieldError(String objectName, String field, String defaultMessage);
    ```
    
    ```java
    public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage);
    ```
    
    - `objectName` : 오류가 발생한 객체 이름
    - `field` : 오류 필드
    - `rejectedValue` : 사용자가 입력한 값 (거절된 값)
    - `bindingFailure` : 타입 오류 같은 바인딩 실패인지 검증 실패인지 구분 값
    - `codes` : 메시지 코드
    - `arguments` : 메시지에서 사용하는 인자
    - `defaultMessage` : 기본 오류 메시지

### 오류 코드와 메시지 처리 1

- 오류메시지를 체계적으로 다루어보자.
    - `FieldError` , `ObjectError` 의 생성자는 `codes` , `arguments` 를 제공한다.
    - 이것은 오류 발생시 오류 코드로 메시지를 찾기 위해 사용된다.

- errors 파일 생성 (`errors.properties`)
    - application.properties → 스프링 부트 메시지 설정 추가
    `spring.messages.basename=messages,errors`
    - errors.properties → 오류 메시지 추가
        
        ```
        required.item.itemName=상품 이름은 필수입니다.
        range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
        max.item.quantity=수량은 최대 {0} 까지 허용합니다.
        totalPriceMin=가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}
        ```
        

- addItemV2
    
    ```java
    bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
    ```
    
    - `codes` : `required.item.itemName`를 사용해서 메시지 코드를 지정한다. 메시지 코드는 하나가 아니라 배열로 여러 값을 전달할 수 있는데, 순서대로 매칭해서 처음 매칭되는 메시지가 사용된다.
    - `arguments` : `Object[]{1000, 1000000}`를 사용해서 코드의 `{0}`, `{1}` 로 치환할 값을 전달한다.

### 오류 코드와 메시지 처리 2

- 목표
    - `FieldError`, `ObjectError`는 다루기 너무 번거롭다.
    - 오류 코드도 좀 더 자동화하자.

- 컨트롤러에서 `BindingResult`는 검증해야 할 객체인 `target` 바로 다음에 온다. 따라서 `BindingResult`는 이미 본인이 검증해야 할 객체인 `target`을 알고 있다.
    - `BindingResult`가 제공하는 `rejectValue()`, `reject()`를 사용하면 `FieldError`, `ObjectError`를 직접 생성하지 않고, 깔끔하게 검증 오류를 다룰 수 있다.

- rejectValue
    
    ```java
    void rejectValue(@Nullable String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage);
    ```
    
    - `field` : 오류 필드명
    - `errorCode` : 오류 코드(이 오류 코드는 메시지에 등록된 코드가 아니다. messageResolver를 위한 오류 코드이다.)
    - `errorArgs` : 오류 메시지에서 `{0}`을 치환하기 위한 값
    - `defaultMessage` : 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지
    - BindingResult는 어떤 객체를 대상으로 검증하는지 target을 이미 알고 있다고 했다. 따라서 target(item)에 대한 정보는 없어도 된다.
    

### 오류 코드와 메시지 처리 3

- 오류 코드를 만들 때 자세히 만들 수도 있고,
    
    ```
    required.item.itemName=상품 이름은 필수입니다.
    range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
    max.item.quantity=수량은 최대 {0} 까지 허용합니다.
    totalPriceMin=가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}
    ```
    
    단순하게 만들 수도 있다.
    
    ```
    required=필수 값 입니다.
    range=범위는 {0} ~ {1} 까지 허용합니다.
    max=최대 {0} 까지 허용합니다.
    ```
    
    - 단순하게 만들면 범용성이 좋아 여러곳에서 사용할 수 있지만, 메시지를 세밀하게 작성하기 어렵다.
    - 반대로 너무 자세하게 만들면 범용성이 떨어진다.
    - 가장 좋은 방법은 범용성으로 사용하다가, 세밀하게 작성해야 하는 경우에는 세밀한 내용이 적용되도록 메시지에 단계를 두는 방법이다.
        - 예) `required`라고 오류 코드를 사용
        다음과 같이 `required`라는 메시지만 있으면 이 메시지를 선택해서 사용한다.
            
            ```
            required: 필수 값 입니다.
            ```
            
            그런데 오류 메시지에 `required.item.itemName`와 같이 객체명과 필드명을 조합한 세밀한 메시지 코드가 있으면 이 메시지를 높은 우선순위로 사용하는 것이다.
            
            ```
            #Level1
            required.item.itemName: 상품 이름은 필수 입니다. 
            
            #Level2
            required: 필수 값 입니다.
            ```
            

### 오류 코드와 메시지 처리 4

- MessageCodesResolver
    - 검증 오류 코드로 메시지 코드들을 생성한다.
    - `MessageCodesResolver` 인터페이스이고 `DefaultMessageCodesResolver`는 기본 구현체이다.
    - 주로 다음과 함께 사용 `ObjectError`, `FieldError`

- DefaultMessageCodesResolver의 기본 메시지 생성 규칙
    - 객체 오류
        
        ```
        객체 오류의 경우 다음 순서로 2가지 생성 
        1.: code + "." + object name 
        2.: code
        
        예) 오류 코드: required, object name: item
        1.: required.item
        2.: required
        ```
        
    - 필드 오류
        
        ```
        필드 오류의 경우 다음 순서로4가지 메시지 코드 생성 
        1.: code + "." + object name + "." + field 
        2.: code + "." + field
        3.: code + "." + field type
        4.: code
        
        예) 오류 코드: typeMismatch, object name "user", field "age", field type: int 
        1. "typeMismatch.user.age"
        2. "typeMismatch.age"
        3. "typeMismatch.int"
        4. "typeMismatch"
        ```
        

- 동작 방식
    - `rejectValue()`, `reject()`는 내부에서 `MessageCodesResolver`를 사용한다. 여기에서 메시지 코드들을 생성한다.
    - `FieldError`, `ObjectError` 의 생성자를 보면, 오류 코드를 하나가 아니라 여러 오류 코드를 가질 수 있다. `MessageCodesResolver`를 통해서 생성된 순서대로 오류 코드를 보관한다.

### 오류 코드와 메시지 처리 5

- 오류 코드 관리 전략
    - **구체적인 것에서 덜 구체적인 것으로** → 메시지와 관련된 공통 전략을 편리하게 도입할 수 있다.
    - 크게 중요하지 않은 메시지는 범용성 있는 `requried` 같은 메시지로 끝내고, 정말 중요한 메시지는 꼭 필요할 때 구체 적으로 적어서 사용하는 방식이 더 효과적이다.

- errors.properties → 크게 객체 오류와 필드 오류를 나누었다. 그리고 범용성에 따라 레벨을 나누어두었다.
    
    ```
    #required.item.itemName=상품 이름은 필수입니다.
    #range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
    #max.item.quantity=수량은 최대 {0} 까지 허용합니다.
    #totalPriceMin=가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}
    
    #==ObjectError==
    #Level1
    totalPriceMin.item=상품의 가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}
    
    #==FieldError==
    #Level1
    required.item.itemName=상품 이름은 필수입니다.
    range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
    max.item.quantity=수량은 최대 {0} 까지 허용합니다.
    
    #Level2 - 생략
    
    #Level3
    required.java.lang.String = 필수 문자입니다.
    required.java.lang.Integer = 필수 숫자입니다.
    min.java.lang.String = {0} 이상의 문자를 입력해주세요.
    min.java.lang.Integer = {0} 이상의 숫자를 입력해주세요.
    range.java.lang.String = {0} ~ {1} 까지의 문자를 입력해주세요.
    range.java.lang.Integer = {0} ~ {1} 까지의 숫자를 입력해주세요.
    max.java.lang.String = {0} 까지의 문자를 허용합니다.
    max.java.lang.Integer = {0} 까지의 숫자를 허용합니다.
    
    #Level4
    required = 필수 값 입니다.
    min= {0} 이상이어야 합니다.
    range= {0} ~ {1} 범위를 허용합니다.
    max= {0} 까지 허용합니다.
    ```
    
    - 검증 오류 메시지가 발생하면 생성된 메시지 코드를 기반으로 순서대로 `MessageSource`에서 메시지에서 찾는다.
    - 구체적인 것에서 덜 구체적인 순서대로 찾는다. 메시지에 1번이 없으면 2번을 찾고, 2번이 없으면 3번을 찾는다.
    - 이렇게 되면 만약에 크게 중요하지 않은 오류 메시지는 기존에 정의된 것을 그냥 **재활용** 하면 된다!
    - 실행: Level1 주석 → Level2,3 주석 → Level4 주석 (못 찾으면 코드에 작성한 디폴트 메시지를 사용한다.)
        - Object 오류도 Level1, Level2로 재활용 가능하다.

- ValidatiionUtils : Empty, 공백 같은 단순한 기능 제공
    
    ```java
    ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
    ```
    
    ```java
    if (!StringUtils.hasText(item.getItemName())) {
        // field, required
        bindingResult.rejectValue("itemName", "required");
    }
    ```
    
    - 위 두 코드는 똑같은 코드다.

### 오류 코드와 메시지 처리 6

- 검증 오류 코드는 다음과 같이 2가지로 나눌 수 있다.
    - 개발자가 직접 설정한 오류 코드 `rejectValue()`를 직접 호출
    - 스프링이 직접 검증 오류에 추가한 경우 (주로 타입 정보가 맞지 않음)

- 스프링이 직접 만든 오류 메시지 처리
    - 스프링은 타입 오류가 발생하면 `typeMismatch` 라는 오류 코드를 사용한다.
    - 실행
        - `errors.properties`에 메시지 코드 X → 스프링이 생성한 기본 메시지가 출력된다.
        - `error.properties`에 메시지 코드 O → 해당 메시지가 출력된다.
    - 소스코드를 하나도 건들지 않고, 원하는 메시지를 단계별로 설정할 수 있다!

### Validator 분리 1

- 복잡한 검증 로직을 별도로 분리하자.
    - 컨트롤러에서 검증 로직이 차지하는 부분은 매우 크다.
    - 이런 경우 별도의 클래스로 역할을 분리하는 것이 좋다.
    - 그리고 이렇게 분리한 검증 로직을 재사용 할 수도 있다.

- Validator 인터페이스
    
    ```java
    public interface Validator {
         boolean supports(Class<?> clazz);
         void validate(Object target, Errors errors);
    }
    ```
    
    - `supports() {}` : 해당 검증기를 지원하는 여부 확인
    - `validate(Object target, Errors errors)` : 검증 대상 객체와 `BindingResult`

### Validator 분리 2

- 스프링이 `Validator` 인터페이스를 별도로 제공하는 이유는 체계적으로 검증 기능을 도입하기 위해서다.
    - 검증기를 직접 불러서 사용해도 된다.
    - 그런데 `Validator` 인터페이스를 사용해서 검증기를 만들면 스프링의 추가적인 도움을 받을 수 있다.

- `WebDataBinder` : 스프링의 파라미터 바인딩 역할 + 검증 기능
    - 검증기 추가
        
        ```java
        @InitBinder
        public void init(WebDataBinder dataBinder) {
            dataBinder.addValidators(itemValidator);
        }
        ```
        
        - 해당 컨트롤러에서 검증기를 자동으로 적용할 수 있다.
        - `@InitBinder` → 해당 컨트롤러에만 영향을 준다.

- `@Validated` : 검증기를 실행하라는 애노테이션 (스프링 검증 애노테이션)
    - 검증 대상 앞에 붙인다.
    - 이 애노테이션이 붙으면 `WebDataBinder`에 등록한 검증기를 찾아서 실행한다.
    - 그런데 여러 검증기를 등록한다면 그 중에 어떤 검증기가 실행되어야 할지 구분이 필요하다. 이때 `supports()` 가 사용된다.

- `@Valid` : 검증기를 실행하라는 애노테이션 (자바 표준 검증 애노테이션)
    - build.gradle 의존관계 추가가 필요하다.
        
        `implementation 'org.springframework.boot:spring-boot-starter-validation'`
        

- 글로벌 설정 - 모든 컨트롤러에 다 적용
    
    ```java
    package hello.itemservice;
    
    import hello.itemservice.web.validation.ItemValidator;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.validation.Validator;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    
    @SpringBootApplication
    public class ItemServiceApplication implements WebMvcConfigurer {
    
    	public static void main(String[] args) {
    		SpringApplication.run(ItemServiceApplication.class, args);
    	}
    
    	@Override
    	public Validator getValidator() {
    		return new ItemValidator();
    	}
    
    }
    ```
    
    - 글로벌 설정을 하면 BeanValidator가 자동 등록되지 않는다.
 
# 5. 검증2 - Bean Validation

### 소개

- Bean Validation
    - 검증 로직을 모든 프로젝트에 적용할 수 있게 공통화하고 표준화 한 것
    - 애노테이션 하나로 검증 로직을 매우 편리하게 적용할 수 있다.

### Bean Validation - 시작

- 순수한 Bean Validation
    - 의존관계 추가
        
        `implementation 'org.springframework.boot:spring-boot-starter-validation'`
        
    - 검증 애노테이션
        - `@NotBlank` : 빈값 + 공백만 있는 경우 허용 X
        - `@NotNull` : null 허용 X
        - `@Range(min = 1000, max = 1000000)` : 범위 안의 값이어야 한다.
        - `@Max(9999)` : 최대 9999까지만 허용한다.

### Bean Validation - 스프링 적용

- 스프링 부트가 `spring-boot-starter-validation` 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합한다.
    - `LocalValidatorFactoryBean`을 글로벌 Validator로 등록한다.
    - 이 Validator는 `@NotNull` 같은 애노테이션을 보고 검증을 수행한다.
    - 이렇게 글로벌 Validator가 적용되어 있기 때문에, `@Valid`, `@Validated`만 적용하면 된다.
    - 검증 오류가 발생하면 `FieldError`, `ObjectError`를 생성해서 `BindingResult`에 담아준다.

- 검증 순서
    - `@ModelAttribute` 각각의 필드에 타입 변환 시도
        - 성공하면 다음으로
        - 실패하면 `typeMismatch`로 `FieldError` 추가
    - Validator 적용

### Bean Validation - 에러 코드

- Bean Validation을 적용하고 bindingResult 에 등록된 검증 오류 코드를 보자.
    - 오류 코드가 애노테이션 이름으로 등록된다. 마치 `typeMismatch`와 유사하다.

- 오류 코드를 기반으로 MessageCodesResolver를 통해 다양한 메시지 코드가 순서대로 생성된다.
    - @NotBlank
        - NotBlank.item.itemName
        - NotBlank.itemName
        - NotBlank.java.lang.String
        - NotBlank

- BeanValidation 메시지 찾는 순서
    - 생성된 메시지 코드 순서대로 messageSource 에서 메시지 찾기
    - 애노테이션의 message 속성 사용 
    `@NotBlank(message = "공백! {0}")`
    - 라이브러리가 제공하는 기본 값 사용 → 공백일 수 없습니다.

### Bean Validation - 오브젝트 오류

- `@ScriptAssert()`
    - 특정 필드(`FieldError`)가 아닌 해당 오브젝트 관련 오류(`ObjectError`) 처리
        
        ```java
        @Data
        @ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "총합이 10000원 넘게 입력해주세요.")
        public class Item {
        		...
        }
        ```
        
    - 메시지 코드
        - ScriptAssert.item
        - ScriptAssert
    - `@ScriptAssert()`를 쓰는 것보다는 오브젝트 오류 관련 부분만 직접 자바 코드로 작성하는 것을 권장

### Bean Validation - 한계

- 데이터를 등록할 때와 수정할 때 요구사항이 다를 수 있다.
    - 등록 시 기존 요구사항
        - 타입 검증
            - 가격, 수량에 문자 X
        - 필드 검증
            - 상품명: 필수, 공백 X
            - 가격: 1000 ~ 1000000
            - 수량: 최대 9999
        - 특정 필드의 범위를 넘어서는 검증
            - 가격 * 수량 ≥ 10000
    - 수정 시 요구사항
        - 등록 시에는 수량을 최대 9999까지 등록할 수 있지만 수정 시에는 수량을 무제한으로 변경할 수 있다.
        - 등록 시에는 id에 값이 없어도 되지만 수정 시에는 id 값이 필수이다.

- 수정 요구사항 적용
    - id : @NotNull 추가
    - quantity : @Max(9999) 제거

- 실행 시 수정은 잘 동작하지만 등록에서 문제가 발생한다.
    - `item` 은 등록과 수정에서 검증 조건의 충돌이 발생하고, 등록과 수정은 같은 BeanValidation을 적용할 수 없다.

### Bean Validation - groups

- 동일한 모델 객체를 등록할 때와 수정할 때 각각 다르게 검증하는 방법
    - BeanValidation의 groups 기능 사용
    - Item을 직접 사용하지 않고 ItemSaveForm, ItemUpdateForm 같은 폼 전송을 위한 별도의 모델 객체를 만들어서 사용

- groups
    - SaveCheck(저장용), UpdateCheck(수정용) 인터페이스 생성
    - `@NotNull(groups = UpdateCheck.class)`
    `@NotBlank(groups = {SaveCheck.class, UpdateCheck.class})`
    - `@Validated(SaveCheck.class) @ModelAttribute Item item`
    `@Validated(UpdateCheck.class) @ModelAttribute Item item`

** groups를 적용하려면 `@Validated`를 써야 한다. (`@Valid` 지원 X)

** 실무에서는 주로 groups 기능을 실제 잘 사용하지 않고, 등록용 폼 객체와 수정용 폼 객체를 분리해서 사용한다.

### Form 전송 객체 분리

- 폼 데이터 전달에 Item 도메인 객체 사용
    - `HTML Form -> Item -> Controller -> Item -> Repository`
        - 장점: Item 도메인 객체를 컨트롤러, 리포지토리 까지 직접 전달해서 중간에 Item을 만드는 과정이 없어서 간단하다.
        - 단점: 간단한 경우에만 적용할 수 있다. 수정시 검증이 중복될 수 있고, groups를 사용해야 한다.

- 폼 데이터 전달을 위한 별도의 객체 사용
    - `HTML Form -> ItemSaveForm -> Controller -> Item 생성 -> Repository`
        - 장점: 전송하는 폼 데이터가 복잡해도 거기에 맞춘 별도의 폼 객체를 사용해서 데이터를 전달 받을 수 있다. 보통 등록과 수정용으로 별도의 폼 객체를 만들기 때문에 검증이 중복되지 않는다.
        - 단점: 폼 데이터를 기반으로 컨트롤러에서 Item 객체를 생성하는 변환 과정이 추가된다.

### Bean Validation - HTTP 메시지 컨버터

- `@Valid`, `@Validated`는 HttpMessageConverter(`@RequestBody`)에도 적용할 수 있다.
    - 성공
        
        ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/1f38097d-0f77-4b2a-af41-b5918902c0ee)

    - 실패 (타입 오류) → JSON 객체 생성 실패로 컨트롤러 자체가 호출이 안 된다.
        
        ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/9e5bf33d-101f-481b-aefd-ee14bc05ac17)

    - 실패 (값 오류) → 검증 오류
        
        ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/708d8c71-d82d-4397-b86d-84ec13090ddc)


- @ModelAttribute vs @RequestBody
    - `@ModelAttribute`는 필드 단위로 정교하게 바인딩이 적용된다. 특정 필드가 바인딩 되지 않아도 나머지 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용할 수 있다.
    - `@RequestBody`는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생한다. 컨트롤러도 호출되지 않고, Validator도 적용할 수 없다.
 
# 6. 로그인 처리1 - 쿠키, 세션

### 로그인 요구사항

- 홈 화면 - 로그인 전
    - 회원가입
    - 로그인
- 홈 화면 - 로그인 후
    - 본인 이름 (~님 환영합니다.)
    - 상품 관리
    - 로그아웃
- 보안 요구사항
    - 로그인 사용자만 상품에 접근 및 관리 가능
    - 로그인 하지 않은 사용자가 상품 관리에 접근하면 로그인 화면으로 이동

### 회원가입

- 회원 관련 기능
    - save
    - findById
    - findByLoginId
    - findAll
    - clearStore (테스트용)

### 로그인 기능

- 로그인에 성공하면 홈 화면으로 이동하고, 로그인에 실패하면 글로벌 오류 발생 후 정보를 다시 입력

### 로그인 처리하기 - 쿠키 사용

- 로그인 상태 유지 → 쿠키
    - 서버에서 로그인에 성공하면 HTTP 응답에 쿠키를 담아서 브라우저에 전달하자. 그러면 브라우저는 앞으로 해당 쿠키를 지속해서 보내준다.
    - 쿠키에는 영속 쿠키와 세션 쿠키가 있다.
        - 영속 쿠키: 만료 날짜를 입력하면 해당 날짜까지 유지
        - 세션 쿠키: 만료 날짜를 생략하면 브라우저 종료시 까지만 유지

- 로그인 성공 처리
    
    ```java
    @Slf4j
    @Controller
    @RequiredArgsConstructor
    public class LoginController {
    
        ...
    
        @PostMapping("/login")
        public String login(@Valid @ModelAttribute LoginForm form,
                            BindingResult bindingResult,
                            HttpServletResponse response) {
            ...
    
            // 로그인 성공 처리
            Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
            response.addCookie(idCookie); // 쿠키에 시간 정보를 주지 않으면 세션 쿠키 (브라우저 종료시 모두 종료)
            return "redirect:/";
        }
    }
    ```
    
    ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/3216b771-1c5f-49a4-846e-187c49ccb922)
    
    - 이후 페이지를 새로고침 해도 Request Header에 `Cookie: memberId=1` 이 들어있다.

- 로그아웃
    
    ```java
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }
    
    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    ```
    
    - 서버에서 해당 쿠키의 종료 날짜를 0으로 지정한다.

### 쿠키와 보안 문제

- 보안 문제
    - 쿠키 값은 임의로 변경할 수 있다.
    - 쿠키에 보관된 정보는 훔쳐갈 수 있다.
    - 해커가 쿠키를 한 번 훔쳐가면 평생 사용할 수 있다.

- 대안
    - 쿠키에 중요한 값을 노출하지 않고, 사용자 별로 예측 불가능한 임의의 토큰(랜덤 값)을 노출하고, 서버에서 토큰과 사용자 id를 매핑해서 인식한다. 그리고 서버에서 토큰을 관리한다.
    - 해커가 토큰을 털어가도 시간이 지나면 사용할 수 없도록 서버에서 해당 토큰의 만료시간을 짧게 유지한다. 또는 해킹이 의심되는 경우 서버에서 해당 토큰을 강제로 제거하면 된다.

### 로그인 처리하기 - 세션 동작 방식

- 쿠키 문제를 해결하려면 중요한 정보를 모두 서버에 저장해야 한다. 그리고 클라이언트와 서버는 추정 불가능한 임의의 식별자 값으로 연결해야 한다. ⇒ 세션!

- 세션 동작 방식
    - 사용자가 아이디, 비밀번호를 전달하면 서버에서 해당 사용자가 맞는지 확인한다.
    - 서버에서는 세션ID를 생성하는데, 추정 불가능해야 한다.
        - UUID는 추정이 불가능하다. 
        `Cookie: mySessionId=zz0101xx-bab9-4b92-9b32-dadb280f4b61`
        - 생성된 세션ID와 세션에 보관할 값(회원 정보)을 서버의 세션 저장소에 보관한다.
    - 클라이언트와 서버는 결국 쿠키로 연결이 되어야 한다.
        - 서버는 클라이언트에 세션ID만 쿠키에 담아서 전달한다.
        - 클라이언트는 쿠키 저장소에 해당 쿠키를 보관한다.

- 로그인 이후 접근
    - 클라이언트는 요청 시 항상 mySessionId 쿠키를 전달한다.
    - 서버에서는 클라이언트가 전달한 쿠키 정보로 세션 저장소를 조회해서 로그인 시 보관한 세션 정보를 사용한다.

### 로그인 처리하기 - 세션 직접 만들기

- 세션 관리
    - 생성
        - seesionId 생성 (임의의 추정 불가능한 랜덤 값)
        - 세션 저장소에 sessionId와 보관할 값 저장
        - sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
    - 조회
        - 클라이언트가 요청한 sessionid 쿠키의 값으로 세션 저장소에 보관한 값 조회
    - 만료
        - 클라이언트가 요청한 sessionId 쿠키의 값으로 세션 저장소에 보관한 sessionid와 값 제거

### 로그인 처리하기 - 서블릿 HTTP 세션 1

- HttpSession
    - 서블릿이 제공하는 세션 기능
    - 서블릿을 통해 HttpSession을 생성하면 쿠키를 생성한다.
        - 이름: `JSESSIONID`
        - 값: 추정 불가능한 랜덤 값
    - create 옵션
        - `request.getSession(true)` (기본값)
            - 세션이 있으면 기존 세션 반환
            - 없으면 새로운 세션을 생성해서 반환
        - `request.getSession(false)`
            - 세션이 있으면 기존 세션 반환
            - 없으면 새로운 세션을 생성하지 않고 null을 반환

### 로그인 처리하기 - 서블릿 HTTP 세션 2

- `@SessionAttribute`
    - 이미 로그인 된 사용자를 찾을 때 다음과 같이 사용하면 된다.
        
        ```java
        @SessionAttribute(name = "loginMember", required = false) Member loginMember
        ```
        
    - 이 기능은 세션을 생성하지 않는다.
    - 세션을 찾고, 세션에 들어있는 데이터를 찾는 번거로운 과정을 스프링이 한번에 편리하게 처리해준다.

- TrackingModes
    - 로그인을 처음 시도하면 URL이 `jsessionid` 를 포함하고 있는 것을 확인할 수 있다.
        
        ```
        http://localhost:8080/;jsessionid=F59911518B921DF62D09F0DF8F83F872
        ```
        
    - 이것은 웹 브라우저가 쿠키를 지원하지 않을 때 쿠키 대신 URL을 통해서 세션을 유지하는 방법이다. 이 방법을 사용하려면 URL에 이 값을 계속 포함해서 전달해야 한다.
    - URL 전달 방식을 끄고 항상 쿠키를 통해서만 세션을 유지하고 싶으면 `application.properties` 파일에 `tracking-modes` 옵션을 넣어주면 된다. 이렇게 하면 URL에 `jsessionid` 가 노출되지 않는다.
        
        ```
        server.servlet.session.tracking-modes=cookie
        ```
        

** URL에 jsessionid가 꼭 필요하다면 `application.properties`에 다음 옵션을 추가

```
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```

### 세션 정보와 타임아웃 설정

- 세션 정보 확인
    - `sessionId` : 세션Id, `JSESSIONID`의 값
    - `maxInactiveInterval` : 세션의 유효 시간 예) 1800초(30분)
    - `creationTime` : 세션 생성일시
    - `lastAccessedTime` : 세션과 연결된 사용자가 최근에 서버에 접근한 시간, 클라이언트에서 서버로 `sessionId`(`JSESSIONID`)를 요청한 경우에 갱신된다.
    - `isNew` : 새로 생성된 세션인지, 아니면 이미 과거에 만들어졌고, 클라이언트에서 서버로 `sessionId`(`JSESSIONID`)를 요청해서 조회된 세션인지 여부

- 세션 타임아웃 설정
    - 세션은 사용자가 로그아웃을 직접 호출해서 `session.invalidate()`가 호출 되는 경우에 삭제된다. 그런데 대부분의 사용자는 로그아웃을 선택하지 않고, 그냥 웹 브라우저를 종료한다.
    - 문제는 HTTP가 비 연결성(ConnectionLess)이므로 서버 입장에서는 해당 사용자가 웹 브라우저를 종료한 것인지 아닌지를 인식할 수 없다. 따라서 서버에서 세션 데이터를 언제 삭제해야 하는지 판단하기가 어렵다.
    - 남아있는 세션을 무한정 보관하면 다음과 같은 문제가 발생할 수 있다.
        - 세션과 관련된 쿠키(`JSESSIONID`)를 탈취 당했을 경우 오랜 시간이 지나도 해당 쿠키로 악의적인 요청을 할 수 있다.
        - 세션은 기본적으로 메모리에 생성된다. 메모리의 크기가 무한하지 않기 때문에 꼭 필요한 경우만 생성해서 사용해야 한다.

- 세션의 종료 시점
    - **세션 생성 시점으로부터 30분** → 30분이 지나면 세션이 삭제되기 때문에, 열심히 사이트를 돌아다니다가 또 로그인을 해서 세션을 생성해야 한다. 따라서 30분 마다 계속 로그인해야 하는 번거로움이 발생한다.
    - **사용자가 서버에 최근에 요청한 시간을 기준으로 30분** → 사용자가 서비스를 사용하고 있으면, 세션의 생존 시간이 30분으로 계속 늘어나게 된다. 따라서 30분 마다 로그인해야 하는 번거로움이 사라진다. `HttpSession` 은 이 방식을 사용한다.

- 세션 타임아웃 설정
    - 스프링 부트로 글로벌 설정 → application.properties
        
        ```
        server.servlet.session.timeout=60
        ```
        
        - 기본 값: 1800 (30분)
        - 글로벌 설정은 분 단위로 설정해야 한다.
    - 특정 세션 단위로 시간 설정
        
        ```java
        session.setMaxInactiveInterval(1800);
        ```
        

** 세션에는 최소한의 데이터만 보관하자.

# 7. 로그인 처리2 - 필터, 인터셉터

### 서블릿 필터 - 소개

- 공통 관심 사항
    - 로그인 한 사용자만 상품 관리 페이지에 들어갈 수 있어야 한다.
    - 그런데 문제는 로그인 하지 않은 사용자도 URL을 직접 호출하면 상품 관리 화면에 들어갈 수 있다.
    `http://localhost:8080/items`
    - 상품 관리 컨트롤러에서 로그인 여부를 체크하는 로직을 하나하나 작성하면 되겠지만, 모든 로직에 공통으로 로그인 여부를 확인해야 하고, 더 큰 문제는 향후 로그인과 관련된 로직이 수정되면 작성한 모든 로직을 다 수정해야 한다.
    - 이렇게 애플리케이션 여러 로직에서 공통으로 관심이 있는 것을 **공통 관심사(cross-cutting concern)**라고 한다.
    - 공통 관심사는 스프링의 AOP로도 해결할 수 있지만, 웹과 관련된 공통 관심사는 지금부터 설명할 **서블릿 필터** 또는 **스프링 인터셉터**를 사용하는 것이 좋다.

- 서블릿 필터 (서블릿이 제공하는 기술)
    - 흐름
        - HTTP 요청 → WAS → 필터 → 서블릿 → 컨트롤러
    - 제한
        - HTTP 요청 → WAS → 필터 → 서블릿 → 컨트롤러 (로그인 사용자)
        - HTTP 요청 → WAS → 필터 (비 로그인 사용자 - 적절하지 않은 요청이라 판단하고 서블릿을 호출하지 않는다.)
    - 체인
        - HTTP 요청 → WAS → 필터1 → 필터2 → 필터3 → 서블릿 → 컨트롤러
    - 인터페이스
        
        ```java
        public interface Filter {
        
        	public default void init(FilterConfig filterConfig) throws ServletException;
        
        	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)
        			throws IOException, ServletException;
        
        	public default void destroy();
        
        }
        ```
        
        - 필터 인터페이스를 구현하고 등록하면 서블릿 컨테이너가 필터를 싱글톤 객체로 생성하고 관리한다.
        - `init()` : 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출된다.
        - `doFilter()` : 고객의 요청이 올 때마다 해당 메서드가 호출된다. 필터의 로직을 구현하면 된다.
        - `destroy()` : 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출된다.

### 서블릿 필터 - 요청 로그

- 모든 요청을 로그로 남기는 필터를 개발하고 적용해보자.
    - `public class LogFilter implements Filter` : 필터를 사용하려면 필터 인터페이스를 구현해야 한다.
    - `ServletRequest request`는 HTTP 요청이 아닌 경우까지 고려해서 만든 인터페이스이다. HTTP를 사용하면 `HttpServletRequest httpRequest = (HttpServletRequest) request;` 와 같이 다운 캐스팅 하면 된다.
    - `chain.doFilter(request, response);` : 다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출한다. 만약 이 로직을 호출하지 않으면 다음 단계로 진행되지 않는다.

- WebConfig - 필터 설정
    - 스프링 부트를 사용한다면 `FilterRegistrationBean`을 사용해서 등록하면 된다.
    - `setFilter(new LogFilter())` : 등록할 필터를 지정한다.
    - `setOrder(1)` : 필터는 체인으로 동작하기 때문에 순서가 필요하다. 낮을 수록 먼저 동작한다.
    - `addUrlPatterns("/*")` : 필터를 적용할 URL 패턴을 지정한다. 한번에 여러 패턴을 지정할 수 있다.

### 서블릿 필터 - 인증 체크

- 인증 필터를 적용해도 홈, 회원가입, 로그인 화면, css 같은 리소스에는 접근할 수 있어야 한다. → 화이트 리스트를 만든 후, 화이트 리스트를 제외한 모든 경우에 인증 체크 로직을 적용한다.

- RedirectURL 처리
    - 로그인에 성공하면 처음 요청한 URL로 이동하는 기능을 개발해보자.
    - 로그인 체크 필터에서, 미인증 사용자는 요청 경로를 포함해서 `/login` 에 `redirectURL` 요청 파라미터를 추가해서 요청했다. 이 값을 사용해서 로그인 성공시 해당 경로로 고객을 `redirect` 한다.

### 스프링 인터셉터 - 소개

- 스프링 인터셉터 (스프링 MVC가 제공하는 기술)
    - 흐름
        - HTTP 요청 → WAS → 필터 → 서블릿 → 스프링 인터셉터 → 컨트롤러
    - 제한
        - HTTP 요청 → WAS → 필터 → 서블릿 → 스프링 인터셉터 → 컨트롤러 (로그인 사용자)
        - HTTP 요청 → WAS → 필터 → 서블릿 → 스프링 인터셉터 (비 로그인 사용자)
    - 체인
        - HTTP 요청 → WAS → 필터 → 서블릿 → 인터셉터1 → 인터셉터2 → 컨트롤러
    - 인터페이스
        
        ```java
        public interface HandlerInterceptor {
        
        	default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
        
        	default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        			@Nullable ModelAndView modelAndView) throws Exception;
        
        	default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        			@Nullable Exception ex) throws Exception;
        
        }
        ```
        
        - 스프링의 인터셉터를 사용하려면 `HandlerInterceptor` 인터페이스를 구현하면 된다.
        - `preHandle` : 컨트롤러 호출 전 (핸들러 어댑터 호출 전)
            - 응답값 `true` → 다음으로 진행 / `false` → 진행 X
        - `postHandle` : 컨트롤러 호출 후 (핸들러 어댑터 호출 후)
        - `afterCompletion` : 요청 완료 이후 (뷰가 렌더링 된 이후)
        - 인터셉터는 어떤 컨트롤러(`handler`)가 호출되는지 호출 정보를 받을 수 있다. 그리고 어떤 `modelAndView`가 반환되는지 응답 정보도 받을 수 있다.
    - 호출 흐름
        
        ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/0063261d-cdd0-41eb-bde8-e0af38e3634e)
        
    - 예외 상황
        
        ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/bd10fed3-5f2d-42e1-b4b5-c0ea780a847c)
 
        - 컨트롤러에서 예외가 발생하면 `postHandle`은 호출되지 않는다.

### 스프링 인터셉터 - 요청 로그

- 요청 로그 인터셉터
    - `request.setAttribute(LOG_ID, uuid)` : 서블릿 필터의 경우 지역변수로 해결이 가능하지만, 스프링 인터셉터는 호출 시점이 완전히 분리되어 있다. 따라서 `request`에 담아두었다. 이 값은 `afterCompletion`에서 `request.getAttribute(LOG_ID)`로 찾아서 사용한다.
    - 핸들러 정보는 어떤 핸들러 매핑을 사용하는가에 따라 달라진다.
        - 스프링을 사용하면 일반적으로 `@Controller`, `@RequestMapping`을 활용한 핸들러 매핑을 사용하는데, 이 경우 핸들러 정보로 `HandlerMethod` 가 넘어온다.
        - `/resources/static` 와 같은 정적 리소스가 호출 되는 경우 `ResourceHttpRequestHandler`가 핸들러 정보로 넘어온다.

- 인터셉터 등록
    - `WebMvcConfigurer`가 제공하는 `addInterceptors()`를 사용해서 인터셉터를 등록할 수 있다.
    - `registry.addInterceptor(new LogInterceptor())` : 인터셉터를 등록한다.
    - `order(1)` : 인터셉터의 호출 순서를 지정한다. 낮을 수록 먼저 호출된다.
    - `addPathPatterns("/**")` : 인터셉터를 적용할 URL 패턴을 지정한다.
    - `excludePathPatterns("/css/**", "/*.ico", "/error")` : 인터셉터에서 제외할 패턴을 지정한다.

** 스프링의 URL 경로 : [https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html)

### 스프링 인터셉터 - 인증 체크

- 인터셉터를 적용하거나 하지 않을 부분은 `addPathPatterns` 와 `excludePathPatterns` 에 작성하면 된다.

### ArgumentResolver 활용

- `ArgumentResolver`를 이용해서 로그인 회원을 편리하게 찾아보자.
    - HomeController
        
        ```java
        @GetMapping("/")
        public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {
            // 로그인 X
            if (loginMember == null) {
                return "home";
            }
            // 로그인 O
            model.addAttribute("member", loginMember);
            return "loginHome";
        }
        ```
        
    - `@Login` 애노테이션 생성
        
        ```java
        package hello.login.web.argumentresolver;
        
        import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;
        
        @Target(ElementType.PARAMETER) // 파라미터에만 사용
        @Retention(RetentionPolicy.RUNTIME) // 런타임까지 애노테이션 정보가 남아있음
        public @interface Login {
        }
        ```
        
    - LoginMemberArgumentResolver
        
        ```java
        package hello.login.web.argumentresolver;
        
        import hello.login.web.SessionConst;
        import hello.login.web.member.Member;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpSession;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.core.MethodParameter;
        import org.springframework.web.bind.support.WebDataBinderFactory;
        import org.springframework.web.context.request.NativeWebRequest;
        import org.springframework.web.method.support.HandlerMethodArgumentResolver;
        import org.springframework.web.method.support.ModelAndViewContainer;
        
        @Slf4j
        public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
        
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                log.info("supportsParamter 실행");
        
                boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
                boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        
                return hasLoginAnnotation && hasMemberType;
            }
        
            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                log.info("resolveArgument 실행");
        
                HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
                HttpSession session = request.getSession(false);
        
                if (session == null) {
                    return null;
                }
        
                return session.getAttribute(SessionConst.LOGIN_MEMBER);
            }
        
        }
        ```
        
        - supportsParamter() : `@Login` 애노테이션이 있으면서 `Member` 타입이면 해당 `ArgumentResolver`가 사용된다.
        - resolveArgument() : 컨트롤러 호출 직전에 호출 되어서 필요한 파라미터 정보를 생성해준다. 여기서는 세션에 있는 로그인 회원 정보인 `member` 객체를 찾아서 반환해준다. 이후 스프링MVC는 컨트롤러의 메서드를 호출하면서 여기에서 반환된 `member` 객체를 파라미터에 전달해준다.
    - WebMvcConfigurer에 설정 추가
        
        ```java
        @Configuration
        public class WebConfig implements WebMvcConfigurer {
        
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new LoginMemberArgumentResolver());
            }
        
        }
        ```

# 8. 예외 처리와 오류 페이지

### 프로젝트 생성

- Gradle Project
- Packaging: Jar
- Dependencies: Spring Web, Lombok, Thymeleaf, Validation

### 서블릿 예외 처리 - 시작

- 서블릿은 2가지 방식으로 예외 처리를 지원한다.
    - Exception
    - response.sendError(HTTP 상태 코드, 오류 메시지)

- Exception
    - 자바 직접 실행
        - 자바의 메인 메서드를 직접 실행하는 경우 main이라는 이름의 스레드가 실행된다.
        - 실행 도중에 예외를 잡지 못하고 처음 실행한 main() 메서드를 넘어서 예외가 던져지면, 예외 정보를 남기고 해당 스레드는 종료된다.
    - 웹 애플리케이션
        - 웹 애플리케이션은 사용자 요청 별로 별도의 스레드가 할당되고, 서블릿 컨테이너 안에서 실행된다.
        - 애플리케이션에서 예외가 발생했는데 try ~ catch 로 예외를 잡아서 처리하면 문제가 없다.
        - 그런데 애플리케이션에서 예외를 잡지 못하고, 서블릿 밖으로 까지 예외가 전달되면 WAS까지 예외가 전달된다.
            
            ```
            WAS <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외 발생)
            ```
            
    - `Exception`의 경우 서버 내부에서 처리할 수 없는 오류가 발생한 것으로 생각해서 HTTP 상태 코드 500을 반환한다.
    - 아무사이트나 호출해보면 톰캣이 기본으로 제공하는 404 오류 화면을 볼 수 있다.

- response.sendError(HTTP 상태 코드, 오류 메시지)
    - 이것을 호출한다고 당장 예외가 발생하는 것은 아니지만, 서블릿 컨테이너에게 오류가 발생했다는 점을 전달할 수 있다.
    - 이 메서드를 사용하면 HTTP 상태 코드와 오류 메시지도 추가할 수 있다.
        
        ```java
        response.sendError(HTTP 상태 코드);
        response.sendError(HTTP 상태 코드, 오류 메시지);
        ```
        
    - 흐름
        
        ```
        WAS(sendError 호출 기록 확인) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러 (response.sendError())
        ```
        
        - `response.sendError()`를 호출하면 `response` 내부에는 오류가 발생했다는 상태를 저장해둔다.
        - 그리고 서블릿 컨테이너는 고객에게 응답 전에 `response` 에 `sendError()`가 호출되었는지 확인한다.
        - 호출 되었다면 설정한 오류 코드에 맞추어 기본 오류 페이지를 보여준다.

### 서블릿 예외 처리 - 오류 화면 제공

- 서블릿은 `Exception`이 발생해서 서블릿 밖으로 전달되거나 `response.sendError()`가 호출 되었을 때, 각각의 상황에 맞춘 오류 처리 기능을 제공한다.
    - 서블릿 오류 페이지 등록
        
        ```java
        package hello.exception;
        
        import org.springframework.boot.web.server.ConfigurableWebServerFactory;
        import org.springframework.boot.web.server.ErrorPage;
        import org.springframework.boot.web.server.WebServerFactoryCustomizer;
        import org.springframework.http.HttpStatus;
        import org.springframework.stereotype.Component;
        
        @Component
        public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
        
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                // HTTP 상태 코드
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
                // Exception
                ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        
                factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
            }
        
        }
        ```
        
    - 해당 오류를 처리할 컨트롤러가 필요하다.
        
        ```java
        package hello.exception.servlet;
        
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        
        @Slf4j
        @Controller
        public class ErrorPageController {
        
            @RequestMapping("/error-page/404")
            public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
                log.info("errorPage 404");
                return "error-page/404";
            }
        
            @RequestMapping("/error-page/500")
            public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
                log.info("errorPage 500");
                return "error-page/500";
            }
        
        }
        ```
        
    - 오류를 처리할 View 만들어주기
    - 실행 결과
        
        ![image](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/83a1babd-19f7-409a-9656-9212069047e4)

        

### 서블릿 예외 처리 - 오류 페이지 작동 원리

- 서블릿은 `Exception`이 발생해서 서블릿 밖으로 전달되거나 `response.sendError()`가 호출 되었을 때, 설정한 오류 페이지를 찾는다.
    - WAS는 해당 예외를 처리하는 오류 페이지 경로를 찾아 내부에서 오류 페이지를 다시 요청한다. 이때 오류 페이지 경로로 필터, 서블릿, 인터셉터, 컨트롤러가 다시 호출된다.
        - 예외 발생 흐름
            
            ```
            WAS <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러 (response.sendError())
            ```
            
        - 오류 페이지 요청 흐름
            
            ```
            WAS "/error-page/500" 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(/error-page/500) -> View
            ```
            
    - 웹 브라우저(클라이언트)는 서버 내부에서 이런 일이 일어나는지 전혀 모른다. 오직 서버 내부에서 오류 페이지를 찾기 위해 추가적인 호출을 한다.
- 오류 정보 추가
    - WAS는 오류 페이지를 단순히 요청만 하는 것이 아니라, 오류 정보를 `request`의 `attribute`에 추가해서 넘겨준다.

### 서블릿 예외 처리 - 필터

- DispatcherType
    - 오류가 발생하면 오류 페이지를 출력하기 위해 WAS 내부에서 다시 한번 호출이 발생한다. 이때 필터, 서블릿, 인터셉터도 모두 다시 호출되는데 로그인 인증 체크 같은 경우를 생각해보면 이미 한번 필터나 인터셉터에서 로그인 체크를 완료했다. 따라서 서버 내부에서 오류 페이지를 호출한다고 해서 해당 필터나 인터셉터가 한번 더 호출되는 것은 매우 비효율적이다.
    - 클라이언트로부터 발생한 정상 요청인지, 아니면 오류 페이지를 출력하기 위한 내부 요청인지 구분하기 위해 서블릿은 `DispatcherType`이라는 추가 정보를 제공한다.
    - `jakarta.servlet.DispatcherType`
        
        ```java
        public enum DispatcherType {
             FORWARD, // 서블릿에서 다른 서블릿이나 JSP를 호출할 때
             INCLUDE, // 서블릿에서 다른 서블릿이나 JSP의 결과를 포함할 때
             REQUEST, // 클라이언트 요청 
             ASYNC, // 서블릿 비동기 호출
             ERROR // 오류 요청 
        }
        ```
        

- 필터와 DispatcherType
    - WebConfig
        
        ```java
        // 두 가지를 모두 넣으면 클라이언트 요청은 물론이고, 오류 페이지 요청에서도 필터가 호출된다.
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        
        // 기본값 (클라이언트의 요청이 있는 경우에만 필터가 적용)
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        
        // 오류 페이지 요청 전용 필터를 적용하고 싶으면
        filterRegistrationBean.setDispatcherTypes(DispatcherType.ERROR);
        ```
        
        - 필터를 등록할 때 어떤 `DispatcherType`인 경우에 필터를 적용할 지 선택

### 서블릿 예외 처리 - 인터셉터

- 인터셉터는 서블릿이 제공하는 기능이 아니라 스프링이 제공하는 기능이기 때문에 `DispatcherType`과 무관하게 항상 호출된다.
    - 인터셉터는 요청 경로에 따라서 추가하거나 제외하기 쉽게 되어 있기 때문에, 이러한 설정을 사용해서 오류 페이지 경로를 `excludePathPatterns` 를 사용해서 빼주면 된다.

- 전체 흐름 정리
    - `/hello` 정상 요청
        
        ```
        WAS(/hello, dispatchType=REQUEST) -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러 -> View
        ```
        
    - `/error-ex` 오류 요청
        
        ```
        1. WAS(/error-ex, dispatchType=REQUEST) -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러
        2. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
        3. WAS 오류 페이지 확인
        4. WAS(/error-page/500, dispatchType=ERROR) -> 필터(x) -> 서블릿 -> 인터셉터(x) -> 컨트롤러(/error-page/500) -> View
        ```
        
        - 필터는 DispatcherType으로 중복 호출 제거
        `dispatchType=REQUEST`
        - 인터셉터는 경로 정보로 중복 호출 제거
        `excludePathPatterns("/error-page/**")`

### 스프링 부트 - 오류 페이지 1

- 지금까지 예외 처리 페이지를 만들기 위해 다음과 같은 과정을 거쳤다.
    - `WebServerCustomizer` 만들기
    - 예외 종류에 따라 `ErrorPage` 추가
        - 예외 처리용 컨트롤러 `ErrorPageController` 만들기

- 스프링 부트는 이런 과정을 모두 기본으로 제공한다.
    - `ErrorPage`를 자동으로 등록한다.
        - `/error`라는 경로로 기본 오류 페이지를 설정
        - `new ErrorPage("/error")`, 상태 코드와 예외를 설정하지 않으면 기본 오류 페이지로 사용된다.
        - 서블릿 밖으로 예외가 발생하거나 `response.sendError()`가 호출되면 모든 오류는 `/error`를 호출하게 된다.
    - `BasicErrorController`라는 스프링 컨트롤러를 자동으로 등록한다.
        - `ErrorPage`에서 등록한 `/error`를 매핑해서 처리하는 컨트롤러다.

** `ErrorMvcAutoConfiguration` 클래스가 오류 페이지를 자동으로 등록하는 역할을 한다.

- 개발자는 오류 페이지 화면만 `BasicErrorController`가 제공하는 룰과 우선순위에 따라 등록하면 된다.
    - 뷰 선택 우선 순위 (뷰 템플릿 > 정적 리소스, 구체적인 것 > 덜 구체적인 것)
        1. 뷰 템플릿
            - `resources/templates/error/500.html`
            - `resources/templates/error/5xx.html`
        2. 정적 리소스 (`static`, `public`)
            - `resources/static/error/400.html`
            - `resources/static/error/4xx.html`
        3. 적용 대상이 없을 때 뷰 이름 (`error`)
            - `resources/templates/error.html`

### 스프링 부트 - 오류 페이지 2

- BasicController가 제공하는 기본 정보들 - model에 담아서 뷰에 전달한다.
    
    ```
    * timestamp: Fri Feb 05 00:00:00 KST 2021
    * status: 400
    * error: Bad Request
    * exception: org.springframework.validation.BindException 
    * trace: 예외 trace
    * message: Validation failed for object='data'. Error count: 1
    * errors: Errors(BindingResult)
    * path: 클라이언트 요청 경로 ("/hello")
    ```
    
- 오류 관련 내부 정보들을 고객에게 노출하는 것은 좋지 않다. 그래서 `BasicErrorController` 오류 컨트롤러에서 다음 오류 정보를 `model` 에 포함할지 여부 선택할 수 있다. (`application.properties`)
    
    ```
    # exception 포함 여부 (true, false)
    server.error.include-exception=false
    
    # message 포함 여부 (never, always, on_param)
    server.error.include-message=never
    
    # trace 포함 여부 (never, always, on_param)
    server.error.include-stacktrace=never
    
    # errors 포함 여부 (never, always, on_param)
    server.error.include-binding-errors=never
    ```
    

- 스프링 부트 오류 관련 옵션
    - `server.error.whitelabel.enabled=true` : 오류 처리 화면을 못 찾을 시, 스프링 whitelabel 오류 페이지 적용
    - `server.error.path=/error` : 오류 페이지 경로, 스프링이 자동 등록하는 서블릿 글로벌 오류 페이지 경로와 `BasicErrorController` 오류 컨트롤러 경로에 함께 사용된다.

** 에러 공통 처리 컨트롤러의 기능을 변경하고 싶으면 `ErrorController` 인터페이스를 상속 받아서 구현하거나 `BasicErrorController` 상속 받아서 기능을 추가하면 된다.

# 9. API 예외 처리

### 시작

- 오류 페이지는 단순히 고객에게 오류 화면을 보여주고 끝이지만, API는 각 오류 상황에 맞는 오류 응답 스펙을 정하고 JSON으로 데이터를 내려주어야 한다.
    - 단순히 회원을 조회하는 기능을 하나 만들었다. 예외 테스트를 위해 URL에 전달된 `id`의 값이 `ex`이면 예외가 발생하도록 코드를 심어두었다.
        - 성공
            
            ```
            {
                "memberId": "spring",
                "name": "hello spring"
            }
            ```
            
        - 예외 발생
            
            ```
            <!DOCTYPE HTML>
            <html>
                <head>
                    <meta charset="utf-8">
                </head>
                <body>
                    <div class="container" style="max-width: 600px">
                        <div class="py-5 text-center">
                            <h2>500 오류 화면</h2>
                        </div>
                        <div>
                            <p>오류 화면 입니다.</p>
                        </div>
                        <hr class="my-4">
            
                    </div>
                    <!-- /container -->
            
                </body>
            </html>
            ```
            
    - API를 요청했는데, 정상의 경우 API로 JSON 형식으로 데이터가 정상 반환된다. 그런데 오류가 발생하면 우리가 미리 만들어둔 오류 페이지 HTML이 반환된다.
    - 이것은 기대하는 바가 아니다. 클라이언트는 정상 요청이든, 오류 요청이든 JSON이 반환되기를 기대한다. 문제를 해결하려면 오류 페이지 컨트롤러도 JSON 응답을 할 수 있도록 수정해야 한다.

- ErrorPageController - API 응답 추가
    
    ```java
    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(
            HttpServletRequest request, HttpServletResponse response) {
        log.info("API errorPage 500");
    
        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());
    
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }
    ```
    
    - `produces = MediaType.APPLICATION_JSON_VALUE` : 클라이언트가 요청하는 HTTP Header의
    `Accept`의 값이 `application/json`일 때 해당 메서드가 호출된다는 것
    - 포스트맨 테스트 → HTTP Header에 Accept가 application/json인 것을 꼭 확인하자.
        - 결과
            
            ```
            {
                "message": "잘못된 사용자",
                "status": 500
            }
            ```
            

### 스프링 부트 기본 오류 처리

- 스프링 부트가 제공하는 `BasicErrorController`
    
    ```java
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response);
    
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request);
    ```
    
    - `errorHtml()` : `produces = MediaType.TEXT_HTML_VALUE` : 클라이언트 요청의 Accept 해더 값이 `text/html`인 경우에는 `errorHtml()`을 호출해서 view를 제공한다.
    - `error()` : 그 외 경우에 호출되고 `ResponseEntity`로 HTTP Body에 JSON 데이터를 반환한다.

- 포스트맨 테스트 → WebServerCustomizer의 @Component 주석 처리
    
    ```
    {
        "timestamp": "2024-01-08T17:19:14.674+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "exception": "java.lang.RuntimeException",
        "path": "/api/members/ex"
    }
    ```
    

### HandlerExceptionResolver

- HandlerExceptionResolver
    - 컨트롤러 밖으로 던져진 예외를 해결하고 동작 방식을 변경하고 싶을 때 사용한다.
    - 적용 전
        
        ![적용 전](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/d5a027fc-10b9-40b6-8c45-bb6f73c138b5)

    - 적용 후
        
        ![적용 후](https://github.com/Springdingdongrami/spring-mvc-2/assets/66028419/ef5b1411-d144-4b44-8d0c-49f5abd6035d)

    
    ** `ExceptionResolver`로 예외를 해결해도 `postHandle()`은 호출되지 않는다.
    
    - 인터페이스
        
        ```java
        public interface HandlerExceptionResolver {
           ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
        }
        ```
        
        - `handler` : 핸들러(컨트롤러) 정보
        - `Exception ex` : 핸들러(컨트롤러)에서 발생한 발생한 예외

- IllegalArgumentException → 400 에러로 바꾸기
    - MyHandlerExceptionResolver
        
        ```java
        package hello.exception.resolver;
        
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.web.servlet.HandlerExceptionResolver;
        import org.springframework.web.servlet.ModelAndView;
        
        import java.io.IOException;
        
        @Slf4j
        public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
        
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                try {
                    if (ex instanceof IllegalArgumentException) {
                        log.info("IllegalArgumentException resolver to 400");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                        return new ModelAndView();
                    }
                } catch (IOException e) {
                    log.error("resolver ex", e);
                }
        
                return null;
            }
        
        }
        ```
        
        - `IllegalArgumentException`이 발생하면 `response.sendError(400)`를 호출해서 HTTP 상태 코드를 400으로 지정하고, 빈 `ModelAndView`를 반환한다.
        - `response.getWriter().println("hello");`처럼 HTTP 응답 바디에 직접 데이터를 넣어주는 것도 가능하다. 여기에 JSON 으로 응답하면 API 응답 처리를 할 수 있다.
    - WebConfig - 등록
        
        ```java
        @Override
        public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
            resolvers.add(new MyHandlerExceptionResolver());
        }
        ```
        
    - 결과
        - ExceptionResolver 활용 전
            
            ```
            {
                "timestamp": "2024-01-08T17:27:52.715+00:00",
                "status": 500,
                "error": "Internal Server Error",
                "exception": "java.lang.IllegalArgumentException",
                "path": "/api/members/bad"
            }
            ```
            
        - ExceptionResolver 활용 후
            
            ```
            {
                "timestamp": "2024-01-08T17:40:47.321+00:00",
                "status": 400,
                "error": "Bad Request",
                "exception": "java.lang.IllegalArgumentException",
                "path": "/api/members/bad"
            }
            ```
            

- `HandlerExceptionResolver`의 반환 값에 따른 `DispatcherServlet`의 동작 방식
    - **빈 ModelAndView**: `new ModelAndView()`처럼 빈 `ModelAndView`를 반환하면 뷰를 렌더링 하지 않고, 정상 흐름으로 서블릿이 리턴된다.
    - **ModelAndView 지정**: `ModelAndView`에 `View`, `Model` 등의 정보를 지정해서 반환하면 뷰를 렌더링 한다.
    - **null**: `null`을 반환하면, 다음 `ExceptionResolver`를 찾아서 실행한다. 만약 처리할 수 있는
    `ExceptionResolver`가 없으면 예외 처리가 안되고, 기존에 발생한 예외를 서블릿 밖으로 던진다.

### 스프링이 제공하는 ExceptionResolver 1

- 스프링 부트가 기본으로 제공하는 `ExceptionResolver` - `HandlerExceptionResolverComposite`에 다음 순서로 등록
    1. `ExceptionHandlerExceptionResolver`
    2. `ResponseStatusExceptionResolver`
    3. `DefaultHandlerExceptionResolver`

- ResponseStatusExceptionResolver
    - 예외에 따라 HTTP 상태 코드를 지정해주는 역할을 한다.
    - 다음 두 가지 경우를 처리한다.
        - `@ResponseStatus`가 달려있는 예외 → HTTP 상태 코드를 변경해주고, `reason`을 `MessageSource`에서 찾는다.
            
            ```java
            @GetMapping("/api/response-status-ex1")
            public String responseStatusEx1() {
                throw new BadRequestException();
            }
            ```
            
            ```java
            package hello.exception.exception;
            
            import org.springframework.http.HttpStatus;
            import org.springframework.web.bind.annotation.ResponseStatus;
            
            @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
            public class BadRequestException extends RuntimeException {
            }
            ```
            
            ```
            {
                "timestamp": "2024-01-08T18:27:46.296+00:00",
                "status": 400,
                "error": "Bad Request",
                "exception": "hello.exception.exception.BadRequestException",
                "message": "잘못된 요청 오류입니다. 메시지 사용",
                "path": "/api/response-status-ex1"
            }
            ```
            
        - `ResponseStatusException` 예외 → 개발자가 직접 변경할 수 없는 예외에 적용 및 조건에 따라 동적으로 변경 가능
            
            ```java
            @GetMapping("/api/response-status-ex2")
            public String responseStatusEx2() {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
            }
            ```
            
            ```
            {
                "timestamp": "2024-01-08T18:31:40.916+00:00",
                "status": 404,
                "error": "Not Found",
                "exception": "org.springframework.web.server.ResponseStatusException",
                "message": "잘못된 요청 오류입니다. 메시지 사용",
                "path": "/api/response-status-ex2"
            }
            ```
            

### 스프링이 제공하는 ExceptionResolver 2

- DefualtHandlerExceptionResolver
    - 스프링 내부에서 발생하는 스프링 예외를 해결한다.
    - 파라미터 바인딩 시점에 타입이 맞지 않으면 내부에서 `TypeMismatchException`이 발생하는데, 이 경우 예외가 발생했기 때문에 그냥 두면 서블릿 컨테이너까지 오류가 올라가고, 결과적으로 500 오류가 발생한다. 그런데 파라미터 바인딩은 대부분 클라이언트가 HTTP 요청 정보를 잘못 호출해서 발생하는 문제이다. HTTP 에서는 이런 경우 HTTP 상태 코드 400을 사용하도록 되어 있다. `DefaultHandlerExceptionResolver`는 이것을 500 오류가 아니라 HTTP 상태 코드 400 오류로 변경한다.
    - 예시
        
        ```java
        @GetMapping("/api/default-handler-ex")
        public String defaultException(@RequestParam Integer data) {
            return "ok";
        }
        ```
        
        ```
        {
            "timestamp": "2024-01-08T18:38:04.772+00:00",
            "status": 400,
            "error": "Bad Request",
            "exception": "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException",
            "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: \"qqq\"",
            "path": "/api/default-handler-ex"
        }
        ```
        
        실행 결과를 보면 HTTP 상태 코드가 400인 것을 확인할 수 있다.
        
    

### @ExceptionHandler

- API 예외 처리의 어려운 점
    - `HandlerExceptionResolver`는 `ModelAndView`를 반환해야 한다. 이것은 API 응답에 필요하지 않다.
    - API 응답을 위해 `HttpServletResponse`에 직접 응답 데이터를 넣어줬다. 이것은 매우 불편하다.
    - 특정 컨트롤러에서만 발생하는 예외를 별도로 처리하기 어렵다.

- ExceptionHandlerExceptionResolver
    - `@ExceptionHandler` 을 처리한다. API 예외 처리는 대부분 이 기능으로 해결한다.
    - 예외 처리 방법
        - `@ExceptionHandler` 애노테이션을 선언하고, 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다. 해당 컨트롤러에서 예외가 발생하면 이 메서드가 호출된다.
        - 다양한 예외를 한번에 처리할 수 있다.
            
            ```java
            @ExceptionHandler({AException.class, BException.class})
            public String ex(Exception e) {
            		log.info("exception e", e);
            }
            ```
            
        - 예외 생략 → 메서드 파라미터의 예외가 지정된다.
            
            ```java
            @ExceptionHandler
            public ResponseEntity<ErrorResult> userExHandle(UserException e) {
            }
            ```
            
        - IllegalArgumentException 처리
            
            ```java
            @ResponseStatus(HttpStatus.BAD_REQUEST)
            @ExceptionHandler(IllegalArgumentException.class)
            public ErrorResult illegalExHandler(IllegalArgumentException e) {
                log.error("[exceptionHandler] ex", e);
                return new ErrorResult("BAD", e.getMessage());
            }
            ```
            
            ```
            {
                "code": "BAD",
                "message": "잘못된 입력값"
            }
            ```
            
        - `ModelAndView`를 사용해서 오류 화면(HTML)을 응답하는데 사용할 수도 있다.

### @ControllerAdvice

- `@ControllerAdvice` 또는 `@RestControllerAdvice`를 사용하면 정상 코드와 예외 처리 코드를 분리할 수 있다.
    - `@ControllerAdvice`는 대상으로 지정한 여러 컨트롤러에 `@ExceptionHandler`, `@InitBinder` 기능을 부여해주는 역할을 한다.
    - `@ControllerAdvice`에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다. (글로벌 적용)
    - `@RestControllerAdvice`는 `@ControllerAdvice`와 같고, `@ResponseBody`가 추가되어 있다.

- 대상 컨트롤러 지정 방법
    
    ```java
    // Target all Controllers annotated with @RestController
    @ControllerAdvice(annotations = RestController.class)
    public class ExampleAdvice1 {}
    
    // Target all Controllers within specific packages
    @ControllerAdvice("org.example.controllers")
    public class ExampleAdvice2 {}
    
    // Target all Controllers assignable to specific classes
    @ControllerAdvice(assignableTypes = {ControllerInterface.class, 
    AbstractController.class})
    public class ExampleAdvice3 {}
    ```
