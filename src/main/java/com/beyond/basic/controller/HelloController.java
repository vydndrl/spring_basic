package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

// @Controller : 해당 클래스가 Controller(사용자의 요청을 처리하고 응답하는 편의 기능)임을 명시
@Controller
@RequestMapping("/hello") // 클래스 차원에서 url 매핑시 RequestMapping 사용
// @RestController // Controller + 각 메서드마다 @ResponseBody
public class HelloController {

    // CASE 1 ) 사용자가 서버에게 화면 요청
    // CASE 2 ) @ResponseBody가 붙을 경우 단순 String 데이터 return(get요청)

    // @GetMapping : get 요청을 처리하고 url 패턴을 명시
    @GetMapping("/")
    // @ResponseBody: 화면이 아닌 데이터를 return
    // 만약 여기서 responseBody가 없고 return이 String이면 스프링은 templates 폴더 밑에 helloworld.html 화면을 찾아 리턴
    // @ResponseBody
    public String helloWorld() {
        return "helloworld";
    }

    // CASE 3 ) 사용자가 Json 데이터 요청(get 요청)
    // 실습 )
    // data를 리턴하되, json 형식으로 return
    // method명은 helloJson, url 패턴은 /hello/json
    @GetMapping("/json")
    @RequestMapping(value = "/json", method = RequestMethod.GET)// 메서드 차원에서도 RequestMapping 사용 가능
    @ResponseBody
    // responseBody를 사용하면서 객체를 return시 자동으로 직렬화
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("keem");
        hello.setEmail("ji@naver.com");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello);
//        return value;
        return hello;
    }

    // CASE 4 ) 사용자가 json 데이터를 요청하되, parameter 형식으로 특정 객체 요청(get)
    // get 요청 중에 특정 데이터를 요청
    @GetMapping("/param1")
    @ResponseBody
    // parameter 형식 : ?name=keem
    public Hello param1(@RequestParam(value = "name") String inputName) {
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail("keem@naver.com");
        System.out.println(hello);
        return hello;
    }

    // 실습
    // url 패턴 : model-param2, 메서드:modelParam2
    // parameter 2개: name, email => hello 객체 생성 후 리턴
    // 요청 방식: ?name=xxx&email=xxx@naver.com
    @GetMapping("/param2")
    @ResponseBody
    // parameter 형식 : ?name=keem
    public Hello param2(@RequestParam(value = "name") String inputName,
                        @RequestParam(value = "email") String inputEmail) {
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }


    // CASE 5 ) parameter 형식으로 요청하되, 서버에서 데이터 바인딩 하는 형식(get)
    @GetMapping("/param3")
    @ResponseBody
    // parameter가 많을 경우, 객체로 대체가 가능함
    // 객체에 각 변수가 맞게 알아서 바인딩됨(데이터 바인딩)
    // ?name=xxx&email=xx@naver.com&password=xxx
    // 데이터바인딩의 조건 : 기본생성자, setter
    public Hello param3(Hello hello){
        return hello;
    }


    // CASE 6 ) 서버에서 화면에 데이터를 넣어 사용자에게 return (model 객체 사용)(get)
    // RestController 사용 X. 얘는 데이터를 리턴하기 때문
    // url에서 전달 http://localhost:8080/hello/model-param?name=hongildong
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name") String inputName, Model model){
//        model 객체를 사용하여 화면에 출력 되도록 (스크립트 형태가 아님)
//        model 객체에 name 이라는 키 값에 value를 세팅하면 해당 key:value는 화면으로 전달
        model.addAttribute("name", inputName);
        return "helloworld";
    }
    
//    case7. pathvariable 방식을 통해 사용자로부터 값을 받아 화면 리턴(get)
//    localhost:8080/hello/model-path/hongildong
//    localhost:8080/author/1 또는 author?id=1
//    pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀 더 restful한 형식
    @GetMapping("/model-path/{inputName}")
    public String modelPath(@PathVariable String inputName, Model model) {
        model.addAttribute("name", inputName); // 입력받은 이름을 name으로 전달
        return "helloworld";
    }


    @GetMapping("/form-view")
//    사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
//    메서드명 : formView, 화면명: form-view

    public String formView() {
        return "form-view";
    }

//    post 요청 (사용자 입장에서 서버에 데이터를 주는 상황)
//    case1. url 인코딩 방식 (text만) 전송
//    형식 : key1=value1&key2=value2&key3=value3
    @PostMapping("/form-post1") // GetMapping 과 같은 url 패턴 사용도 가능
    @ResponseBody
    public String formPost1(@RequestParam(value = "name") String inputName, // name 이랑 매핑
                            @RequestParam(value = "email") String inputEmail,
                            @RequestParam(value = "password") String inputPassword) {
//        사용자로부터 받아온 내용 출력
        System.out.println("inputName = " + inputName);
        System.out.println("inputEmail = " + inputEmail);
        System.out.println("inputPassword = " + inputPassword);
        return "ok";
    }

    @PostMapping("/form-post2")
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello) { // ModelAttribute는 생략 가능
        // 데이터 바인딩
//        사용자로부터 받아온 내용 출력
        System.out.println("hello = " + hello);
        return "ok";
    }
    
//    case2. multipart/form-data 방식(text와 파일) 전송
//    url명 : form-file-post , 메서드명 : formFilePost, 화면명: form-file-view
//    form태그 name, email, password, file
    @GetMapping("/form-file-post")
    public String formFilePost() {
        return "form-file-view";
    }

//    Get과 Post는 구분이 되기 때문에 Url 주소를 동일하게 설정해도 됨
    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFileHandle(Hello hello,
                                 @RequestParam(value = "photo") MultipartFile photo) {

        System.out.println("hello = " + hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

//    case3. js를 활용한 form 데이터 전송 (text)
    @GetMapping("/axios-form-view")
    public String axiosFormView() {
//        name, email, password 를 전송
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
//    axios를 통해 넘어오는 형식이 key1=value&key2-value2 등 url 인코딩 방식
    public String axiosFormPost(Hello hello) {
        System.out.println("hello = " + hello);
        return "ok";
    }

//    case4. js를 활용한 from 데이터 전송 (+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView() {
        return "axios-form-file-view";
    }

    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello,
                                        @RequestParam(value = "file")MultipartFile photo) {
        System.out.println("hello = " + hello);
        System.out.println("photo.getOriginalFilename() = "
                + photo.getOriginalFilename());
        return "ok";
    }

//    case5. js를 활용한 json 데이터 전송
//    url 패턴 : axios-json-view, 화면명 : axios-json-view
//    get 요청 메서드 : 동일 / post 요청 메서드 : axiosJsonPost
    @GetMapping("/axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }

    @PostMapping("/axios-json-view")
    @ResponseBody
//    json으로 전송한 데이터를 받을 때에는 @RequestBody 어노테이션 사용
    public String axiosJsonPost(@RequestBody Hello hello) {
        System.out.println("hello = " + hello);
        return "ok";
    }

//    case6. js를 활용한 json 데이터 전송 (+file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView() {
        return "axios-json-file-view";
    }
    
    @PostMapping("/axios-json-file-view")
    @ResponseBody
//    RequestPart는 파일과 Json을 처리할 때 주로 사용하는 어노테이션
    public String axiosJsonFilePost(
//            @RequestParam("hello") String hello,
//            @RequestParam("file") MultipartFile file
//            formdata를 통해 json, file(멀티미디어)을 처리할 때 RequestPart 어노테이션을 많이 사용
            @RequestPart("hello") Hello hello,
            @RequestPart("file") MultipartFile file
    ) {

        System.out.println("hello = " + hello);
////        String으로 받은 뒤 수동으로 객체로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(hello, Hello.class);
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        return "ok";
    }
    
//    case7. js를 활용한 json 데이터 전송 (+여러 file)
@GetMapping("/axios-json-multi-file-view")
public String axiosJsonMultiFileView() {
    return "axios-json-multi-file-view";
}

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody

    public String axiosJsonMultiFilePost(
            @RequestPart("hello") Hello hello,
            @RequestPart("files") List<MultipartFile> files
    ) {

        System.out.println("hello = " + hello);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }

//    case8. 중첩된 JSON 데이터 처리
//    {name:"hongildong", email:"hong@naver.com", scores:[{math:60}, {music:70}, {english:100}]};
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student) {
        System.out.println("student = " + student);
        return "ok";
    }
}