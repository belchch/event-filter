package ru.acvm.eventfilter.app

import org.springframework.context.ResourceLoaderAware
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.File

@Component
class ResourceReader : ResourceLoaderAware {
    private var resourceLoader: ResourceLoader? = null

    override fun setResourceLoader(resourceLoader: ResourceLoader) {
        this.resourceLoader = resourceLoader
    }

    fun forEachLine(resource: String, fn: (String) -> Unit) {
        resourceLoader?.getResource(resource)?.file?.forEachLine { fn(it) }
    }

    fun lines(resource: String): List<String> {
        return resourceLoader?.getResource(resource)?.file?.readLines() ?: emptyList()
    }

    fun file(resource: String): File? {
        return resourceLoader?.getResource(resource)?.file
    }
}