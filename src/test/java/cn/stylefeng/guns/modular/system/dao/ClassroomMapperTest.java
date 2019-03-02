package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.GunsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= GunsApplication.class)
public class ClassroomMapperTest {

    @Resource ClassroomMapper classroomMapper;
    @Test
    public void test1() {
        List<Map<String,Object>> list=classroomMapper.test();
    }
}