package com.paige.service.apigateway.testservices;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class HomeService {


    @RestController
    public static class MyController {

        @GetMapping("/news/{id}")
        public ResponseEntity<TestEntity> getTestService1() {

            TestEntity testEntity = new TestEntity();

            DataEntity dataInfo = new DataEntity();
            dataInfo.setCreatetime("");
            dataInfo.setId("1");
            dataInfo.setTeam("nc");
            dataInfo.setType("feed");



            ResultEntity resultEntity = new ResultEntity();

            resultEntity.getData().add(dataInfo);

            testEntity.setError_code("200");
            testEntity.setError_msg("success");
            testEntity.getResult().getData().add(dataInfo);

            return new ResponseEntity<>(testEntity, HttpStatus.OK);
        }

        @PostMapping("/community/posts")
        public ResponseEntity<TestEntity> postTestService2(@RequestBody TestEntity requestEntity) {

            log.info(requestEntity.toString());


            TestEntity testEntity = new TestEntity();

            DataEntity dataInfo = new DataEntity();
            dataInfo.setCreatetime("");
            dataInfo.setId("1");
            dataInfo.setTeam("nc");
            dataInfo.setType("feed");



            ResultEntity resultEntity = new ResultEntity();

            resultEntity.getData().add(dataInfo);

            testEntity.setError_code("200");
            testEntity.setError_msg("success");
            testEntity.getResult().getData().add(dataInfo);

            return new ResponseEntity<>(testEntity, HttpStatus.OK);
        }

        @GetMapping("/community/posts")
        public ResponseEntity<TestEntity> getTestService2() {

            //log.info(requestEntity.toString());


            TestEntity testEntity = new TestEntity();

            DataEntity dataInfo = new DataEntity();
            dataInfo.setCreatetime("");
            dataInfo.setId("1");
            dataInfo.setTeam("nc");
            dataInfo.setType("feed");



            ResultEntity resultEntity = new ResultEntity();

            resultEntity.getData().add(dataInfo);

            testEntity.setError_code("200");
            testEntity.setError_msg("success");
            testEntity.getResult().getData().add(dataInfo);

            return new ResponseEntity<>(testEntity, HttpStatus.OK);
        }

    }


    @Data
    private static class TestEntity {

        String error_code;
        String error_msg;

        ResultEntity result = new ResultEntity();
    }

    @Data
    private static class ResultEntity {

        List<DataEntity> data = new ArrayList<>();
    }

    @Data
    private static class DataEntity {

        String id;
        String team;
        String type;
        String createtime;

        public void setCreatetime(String s) {
            this.createtime = s;
        }

        public void setId(String s) {
            this.id = s;
        }

        public void setTeam(String s) {
            this.team = s;
        }

        public void setType(String s) {
            this.type = s;
        }
    }


    public static void main(String[] args) {

        //TODO : 설정정보로 빼기
        System.setProperty("SERVER.PORT","9000");
        SpringApplication.run(HomeService.class, args);
    }
}



