package com.sampledomain.bank.repository;

import com.sampledomain.bank.BankServiceApplication;
import com.sampledomain.bank.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest(classes = BankServiceApplication.class)
public class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

	
    @Test
    @Transactional
    public void userEntityRepository_whenSaveAndRetrieveEntity_thenOK() {
        UserEntity userEntity = userEntityRepository.save(
                new UserEntity(10L, "2", "Ali", "ahmadi", "0912", "12345", null));

        Optional<UserEntity> foundEntity = userEntityRepository.findUserEntityById(userEntity.getId());
        Assert.notNull(foundEntity);
        System.out.println(foundEntity.get());
        Assert.isTrue(userEntity.equals(foundEntity.get()));
    }
}
