package runstatic.minecraft.repository

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import runstatic.minecraft.entity.PlayerEntity

/**
 *
 * @author chenmoand
 */
@Repository
interface PlayerRepository: JpaRepositoryImplementation<PlayerEntity, Long> {

    fun findByUsername(username: String) : PlayerEntity?

}