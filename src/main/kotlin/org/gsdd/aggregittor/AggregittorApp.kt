package org.gsdd.aggregittor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AggregittorApp

fun main(args:Array<String>) {
    runApplication<AggregittorApp>(*args)
}
