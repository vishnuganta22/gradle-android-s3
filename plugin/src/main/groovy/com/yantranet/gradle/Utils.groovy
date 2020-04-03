package com.yantranet.gradle

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.event.ProgressEvent
import com.amazonaws.event.ProgressListener
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.Download
import com.amazonaws.services.s3.transfer.PersistableTransfer
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.Upload
import com.amazonaws.services.s3.transfer.internal.S3ProgressListener
import org.gradle.api.GradleException
import org.gradle.api.provider.ListProperty

import javax.annotation.Nonnull

class Utils {
    static BasicAWSCredentials getCredentials(@Nonnull String accessKey, @Nonnull String secretKey) {
        new BasicAWSCredentials(accessKey, secretKey)
    }

    static void validateAWSCredentials(AWSCredentials awsCredentials) {
        if (awsCredentials.AWSAccessKeyId == null || awsCredentials.AWSAccessKeyId.isEmpty()) throw new IllegalArgumentException("Invalid AWS Credentials")
        if (awsCredentials.AWSSecretKey == null || awsCredentials.AWSSecretKey.isEmpty()) throw new IllegalArgumentException("Invalid AWS Credentials")
    }

    static void validate(ListProperty<?> dataListProperty) {
        if (dataListProperty == null || dataListProperty.get().empty) throw new GradleException('List can\'t be empty or NULL')
    }

    static void validate(Iterator<?> dataListProperty) {
        if (dataListProperty == null || dataListProperty.toList().empty) throw new GradleException('List can\'t be empty or NULL')
    }

    static void validate(DownloadData downloadData) {
        if (downloadData == null) throw new GradleException('downloadData can\'t be NULL')
        if (downloadData.bucketName == null || downloadData.bucketName.isEmpty()) throw new GradleException('bucketName can\'t be empty or NULL')
        if (downloadData.fileLocalPaths.length <= 0) throw new GradleException('No files to download')
        for (String filePath : downloadData.fileLocalPaths) {
            validateFilePath(filePath)
        }
    }

    static void download(String localFilePath, AWSCredentials credentials, String bucketName, String key) {
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

    static void validate(UploadData uploadData) {
        if (uploadData == null) throw new GradleException('uploadData can\'t be NULL')
        if (uploadData.bucketName == null || uploadData.bucketName.isEmpty()) throw new GradleException('bucketName can\'t be empty or NULL')
        if (uploadData.fileLocalPaths.length <= 0) throw new GradleException('No files to upload')
        for (String filePath : uploadData.fileLocalPaths) {
            validateFilePath(filePath)
        }
    }

    static void validateFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) throw new GradleException(" filePath can't be NULL or empty ")
        if (!new File(filePath).exists()) throw new GradleException('file doesn\'t exist at location' + filePath)
        if (new File(filePath).isDirectory()) throw new GradleException(filePath + ' file represents a folder')
    }

    static void upload(AWSCredentials credentials, String bucketName, String key, String uploadPath) {
        upload(credentials, new PutObjectRequest(bucketName, key, new File(uploadPath)))
    }

    static void upload(AWSCredentials credentials, String bucketName, String s3Object, String uploadPath, CannedAccessControlList cannedAccessControlList) {
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, s3Object, new File(uploadPath))
        objectRequest.setCannedAcl(cannedAccessControlList)
        upload(credentials, objectRequest)
    }

    static void upload(AWSCredentials credentials, PutObjectRequest objectRequest) {
        TransferManager manager = new TransferManager(credentials)
        objectRequest.setKey(objectRequest.getKey() + '/' + objectRequest.getFile().name)
        try {
            println("Upload src : " + objectRequest.getFile().absolutePath)
            println(" Uploading to  :: " + 'https://s3.amazonaws.com/' + objectRequest.getBucketName() + '/' + objectRequest.getKey())
            Upload upload = manager.upload(objectRequest)
            upload.addProgressListener(new S3ProgressListener() {
                @Override
                void onPersistableTransfer(PersistableTransfer persistableTransfer) {

                }

                @Override
                void progressChanged(ProgressEvent progressEvent) {
                    print(' .')
                }
            })
            upload.waitForCompletion()
            println "Upload finished .."
        } catch (AmazonServiceException e) {
            throw new GradleException("AMAZON SERVICE ERROR: " + e.getMessage())
        } catch (AmazonClientException e) {
            throw new GradleException("AMAZON CLIENT ERROR: " + e.getMessage())
        } catch (InterruptedException e) {
            throw new GradleException("TRANSFER INTERRUPTED: " + e.getMessage())
        }
        manager.shutdownNow()
    }
}
