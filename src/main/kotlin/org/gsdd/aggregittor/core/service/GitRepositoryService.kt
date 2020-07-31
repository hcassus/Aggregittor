package org.gsdd.aggregittor.core.service

import org.gsdd.aggregittor.core.gateway.VcsRepositoryGateway
import org.gsdd.aggregittor.core.domain.VcsRepository

class GitRepositoryService(val repositoryGateway: VcsRepositoryGateway) :
    VcsRepositoryService {

    override fun getVcsRepositories(): List<VcsRepository> {
        return repositoryGateway.getVcsRepositories()
    }

    override fun switchToBranch(branchName: String) {
        repositoryGateway.switchRepositoriesToBranch(branchName)

    }

    override fun hardResetCurrentBranches() {
        repositoryGateway.hardResetCurrentBranches()
    }
}