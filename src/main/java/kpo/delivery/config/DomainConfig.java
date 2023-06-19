package kpo.delivery.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("kpo.delivery.domain")
@EnableJpaRepositories("kpo.delivery.repos")
@EnableTransactionManagement
public class DomainConfig {
}
