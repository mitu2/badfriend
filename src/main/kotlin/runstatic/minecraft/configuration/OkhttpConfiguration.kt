package runstatic.minecraft.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author chenmoand
 */
@Configuration
class OkhttpConfiguration {

    @Bean
    fun okHttpClient() = OkHttpClient()

}