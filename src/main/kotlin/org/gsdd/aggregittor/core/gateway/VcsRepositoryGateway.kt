package org.gsdd.aggregittor.core.gateway

import org.gsdd.aggregittor.core.domain.VcsRepository

interface VcsRepositoryGateway {

    fun getVcsRepositories() : List<VcsRepository>

}