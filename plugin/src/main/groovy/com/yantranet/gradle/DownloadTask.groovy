package com.yantranet.gradle

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.event.ProgressEvent
import com.amazonaws.event.ProgressListener
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.transfer.Download
import com.amazonaws.services.s3.transfer.TransferManager
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class DownloadTask extends S3Task {
    String localFilePath

    private void validate() {
        if (bucketName == null || bucketName.isEmpty()) throw new GradleException('bucketName can\'t be empty or NULL')
        if (localFilePath == null || localFilePath.isEmpty()) throw new GradleException('localFilePath can\'t be empty or NULL')
    }

    private void download(AWSCredentials credentials, String bucketName, String key) {
        TransferManager manager = new TransferManager(credentials)
        GetObjectRequest request = new GetObjectRequest(bucketName, key)
        try {
            println('Downloading file to ' + new File(localFilePath).absolutePath)
            println('Downloading from ' + 'https://s3.amazonaws.com/' + request.getBucketName() + '/' + request.getKey())
            Download download = manager.download(request, new File(localFilePath))
            download.addProgressListener(new ProgressListener() {
                @Override
                void progressChanged(ProgressEvent progressEvent) {
                    print(' .')
                }
            })
            download.waitForCompletion()
            println "Download finished .."
        } catch (AmazonServiceException e) {
            throw new GradleException("AMAZON SERVICE ERROR: " + e.getMessage())
        } catch (AmazonClientException e) {
            throw new GradleException("AMAZON CLIENT ERROR: " + e.getMessage())
        } catch (InterruptedException e) {
            throw new GradleException("TRANSFER INTERRUPTED: " + e.getMessage())
        }
        manager.shutdownNow()
    }

    @TaskAction
    void run() {
        validate()
        AWSCredentials credentials = getAWSCredentials()
        if (credentials == null) throw new IllegalArgumentException('Invalid AWS Credentials')
        download(credentials, bucketName, key)
    }
}
