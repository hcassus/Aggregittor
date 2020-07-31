package org.gsdd.aggregittor.client

import org.gsdd.aggregittor.core.domain.VcsRepository
import org.gsdd.aggregittor.core.service.VcsRepositoryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["repository"])
class RepositoryController (private val repositoryService: VcsRepositoryService) {

    @RequestMapping(method = [RequestMethod.GET])
    fun getRepositoryList() : List<VcsRepository> {
        return repositoryService.getVcsRepositories()
    }


}