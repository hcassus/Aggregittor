package org.gsdd.aggregittor.core.gateway

import org.gsdd.aggregittor.core.domain.Repository

interface VcsRepositoryGateway {

    fun getVcsRepositories() : List<Repository>

}