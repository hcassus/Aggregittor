package org.gsdd.aggregittor.server

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.api.errors.CheckoutConflictException
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gsdd.aggregittor.core.domain.VcsRepository
import org.gsdd.aggregittor.core.exception.RepositoryConflictException
import org.gsdd.aggregittor.core.gateway.VcsRepositoryGateway

import java.io.File

class JGitRepositoryGateway : VcsRepositoryGateway {

    override fun getVcsRepositories(): List<VcsRepository> {
        val gitDirs = getGitDirs()
        val repos = getGitRepos(gitDirs)
        return repos.map(::toDomain)
    }

    override fun switchRepositoriesToBranch(branchName: String) {
        val repos = getGitRepos(getGitDirs())
        repos.forEach { r -> switchToBranch(r, branchName) }
    }

    override fun hardResetCurrentBranches() {
        val repos = getGitRepos(getGitDirs())
        repos.forEach { r -> hardResetBranch(r) }
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

    private fun switchToBranch(repo: Repository, branchName: String) {
        val git = Git(repo)
        val result = try {
            git.checkout().setName(branchName).call()
        } catch (e: CheckoutConflictException){
            throw RepositoryConflictException("Unable to switch repository ${resolveRepoName(repo.identifier)} to " +
                "branch $branchName. Work tree clean: ${isCleanTree(repo)}")
        }
        result.name
    }

    private fun hardResetBranch(repo: Repository) {
        val git = Git(repo)
        git.add().addFilepattern(".").call()
        git.reset().setMode(ResetCommand.ResetType.HARD).call()
    }

}