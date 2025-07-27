package fpt.duantotnghiep.sd28.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("fpt.duantotnghiep.sd28.entity")
@EnableJpaRepositories("fpt.duantotnghiep.sd28.repo")
@EnableTransactionManagement
public class EntityConfig {
}