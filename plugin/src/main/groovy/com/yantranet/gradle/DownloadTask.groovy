package com.yantranet.gradle


import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.s3.model.CannedAccessControlList
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class DownloadTask extends DefaultTask {

    @TaskAction
    void run() {
        NamedDomainObjectContainer<DownloadData> downloads = project.extensions.getByName('downloads') as NamedDomainObjectContainer<DownloadData>
        Utils.validate(downloads.iterator())
        downloads.iterator().each { downloadData ->
            Utils.validate(downloadData)
            AWSCredentials credentials = downloadData.getAWSCredentials()
            if (credentials == null) throw new IllegalArgumentException('Invalid AWS Credentials')
            Utils.download(downloadData.localFilePath, credentials, downloadData.bucketName, downloadData.key)
        }
    }
}
