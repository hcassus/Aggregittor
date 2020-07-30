package org.gsdd.aggregittor.server

import org.gsdd.aggregittor.core.domain.Repository
import org.gsdd.aggregittor.core.gateway.VcsRepositoryGateway

class DummyGitRepositoryGateway : VcsRepositoryGateway {
    override fun getVcsRepositories(): List<Repository> {
        val repo1 = Repository("name1")
        val repo2 = Repository("name2")
        return listOf(repo1, repo2)
    }
}