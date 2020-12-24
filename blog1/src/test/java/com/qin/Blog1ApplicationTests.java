package com.qin;

import com.qin.entity.MUser;
import com.qin.service.IMUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Blog1ApplicationTests {

    @Autowired
    IMUserService imUserService;
    @Test
    void contextLoads() {
        MUser byId = imUserService.getById(1);

        System.out.println(byId);
    }

}
