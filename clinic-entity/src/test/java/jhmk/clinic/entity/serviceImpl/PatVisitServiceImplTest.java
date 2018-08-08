package jhmk.clinic.entity.serviceImpl;

import jhmk.clinic.entity.pojo.ShiroUser;
import jhmk.clinic.entity.pojo.repository.ShiroUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PatVisitServiceImplTest {
    @Autowired
    ShiroUserRepository repository;

    @Test
    public void testSave() {
        List<ShiroUser> all = repository.findAll();
        all.stream().forEach(shiroUser -> System.out.println(shiroUser.toString()));
    }

}