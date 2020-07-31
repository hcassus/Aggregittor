package org.gsdd.aggregittor.config

import org.gsdd.aggregittor.core.gateway.VcsRepositoryGateway
import org.gsdd.aggregittor.core.service.GitRepositoryService
import org.gsdd.aggregittor.core.service.VcsRepositoryService
import org.gsdd.aggregittor.server.JGitRepositoryGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AgregittorConfig {

    @Bean
    fun repositoryService(repositoryGateway: VcsRepositoryGateway): VcsRepositoryService = GitRepositoryService(repositoryGateway)

    @Bean
    fun repositoryGateway() : VcsRepositoryGateway = JGitRepositoryGateway()

}