package org.gsdd.aggregittor.client

import org.gsdd.aggregittor.core.domain.VcsRepository
import org.gsdd.aggregittor.core.exception.RepositoryConflictException
import org.gsdd.aggregittor.core.service.VcsRepositoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
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

    @RequestMapping(method = [RequestMethod.PUT], path = ["/branch/{branchName}"])
    fun changeBranch(@PathVariable("branchName") branchName : String) {
        return repositoryService.switchToBranch(branchName)
    }

    @RequestMapping(method = [RequestMethod.PUT], path = ["/hardReset"])
    fun hardResetCurrentBranches() {
        return repositoryService.hardResetCurrentBranches()
    }

    @ExceptionHandler(RepositoryConflictException::class)
    fun handleRepositoryConflict(e: RepositoryConflictException) : ResponseEntity<Any> {
        val body = mapOf(
            "message" to e.message
        )
        return ResponseEntity(body, HttpStatus.CONFLICT)
    }


}
