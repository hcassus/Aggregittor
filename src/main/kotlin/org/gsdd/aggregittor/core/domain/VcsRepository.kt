package org.gsdd.aggregittor.core.domain

data class VcsRepository(val name: String, val currentBranch: String, val cleanWorkTree: Boolean)