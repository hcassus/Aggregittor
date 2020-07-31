package org.gsdd.aggregittor.core.service

import org.gsdd.aggregittor.core.domain.VcsRepository

interface VcsRepositoryService {

    fun getVcsRepositories() : List<VcsRepository>

}