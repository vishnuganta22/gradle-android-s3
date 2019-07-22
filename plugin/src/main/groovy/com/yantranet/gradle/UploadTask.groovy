package com.yantranet.gradle


import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.s3.model.CannedAccessControlList
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class UploadTask extends DefaultTask {

    @TaskAction
    void run() {
        NamedDomainObjectContainer<UploadData> uploads = project.extensions.getByName('uploads') as NamedDomainObjectContainer<UploadData>
        Utils.validate(uploads.iterator())
        uploads.iterator().each { uploadData ->
            Utils.validate(uploadData)
            AWSCredentials credentials = uploadData.getAWSCredentials()
            if (credentials == null) throw new IllegalArgumentException("Invalid AWS Credentials")
            if (uploadData.cannedAccessControlList != null && !uploadData.cannedAccessControlList.isEmpty()) {
                println('cannedAccessControlList' + CannedAccessControlList.valueOf(uploadData.cannedAccessControlList))
                for (String filePath : uploadData.fileLocalPaths) {
                    Utils.upload(credentials, uploadData.bucketName, uploadData.key, filePath, CannedAccessControlList.valueOf(uploadData.cannedAccessControlList))
                }
            } else {
                for (String filePath : uploadData.fileLocalPaths) {
                    Utils.upload(credentials, uploadData.bucketName, uploadData.key, filePath)
                }
            }
        }
    }
}