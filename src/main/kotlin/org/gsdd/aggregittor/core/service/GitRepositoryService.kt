package org.gsdd.aggregittor.core.service

import org.gsdd.aggregittor.core.gateway.VcsRepositoryGateway
import org.gsdd.aggregittor.core.domain.Repository

class GitRepositoryService(val repositoryGateway: VcsRepositoryGateway) :
    VcsRepositoryService {

    override fun getVcsRepositories(): List<String> {
        return repositoryGateway.getVcsRepositories()
            .map(Repository::name)
    }
}