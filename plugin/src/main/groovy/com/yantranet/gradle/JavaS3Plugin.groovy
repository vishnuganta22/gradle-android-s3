package com.yantranet.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaS3Plugin implements Plugin<Project> {
    void apply(Project target) {
        target.tasks.create('upload', UploadTask)
    }
}