package org.gsdd.aggregittor.server

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gsdd.aggregittor.core.domain.VcsRepository
import org.gsdd.aggregittor.core.gateway.VcsRepositoryGateway
import java.io.File

class JGitRepositoryGateway : VcsRepositoryGateway {

    override fun getVcsRepositories(): List<VcsRepository> {
        val gitDirs = getGitDirs()
        val repos = getGitRepos(gitDirs)
        return repos.map(::toDomain)
    }

    private fun getGitDirs() : List<File> {
        val baseDir = File(System.getenv("GIT_BASE_DIR"))
        return baseDir.listFiles().asSequence()
            .filter(File::isDirectory)
            .filter { f -> f.list().contains(".git")}
            .toList()
    }

    private fun getGitRepos(gitDirs: List<File>): List<Repository> {
        return gitDirs.asSequence()
            .map { f ->
                FileRepositoryBuilder()
                    .setGitDir(f.resolve(".git")) // --git-dir if supplied, no-op if null
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .build()
            }
            .toList()
    }

    private fun toDomain(repo : Repository) : VcsRepository{
        return VcsRepository(
            name = resolveRepoName(repo.identifier),
            currentBranch = repo.branch,
            cleanWorkTree = isCleanTree(repo)
        )
    }

    private fun resolveRepoName(identifier: String?): String {
       return identifier?.substringBeforeLast("/")
            ?.substringAfterLast("/").orEmpty()
    }

    private fun isCleanTree(repo: Repository) : Boolean {
        val git = Git(repo)
        val status = git.status().call()
        return status.isClean && !status.hasUncommittedChanges()
    }

}