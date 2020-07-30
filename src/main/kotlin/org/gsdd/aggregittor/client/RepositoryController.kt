package org.gsdd.aggregittor.client

import org.gsdd.aggregittor.core.service.VcsRepositoryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class RepositoryController (private val repositoryService: VcsRepositoryService) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/repository"])
    fun getRepositoryList() : List<String> {
        return repositoryService.getVcsRepositories()
    }

}