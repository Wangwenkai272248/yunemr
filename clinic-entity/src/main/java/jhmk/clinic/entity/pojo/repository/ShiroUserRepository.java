package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.ShiroUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 16:27
 */

public interface ShiroUserRepository extends JpaRepository<ShiroUser, String>,JpaSpecificationExecutor<ShiroUser> {
}
