package com.yantranet.gradle

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials

import javax.annotation.Nonnull

class Utils {
    static BasicAWSCredentials getCredentials(@Nonnull String accessKey, @Nonnull String secretKey) {
        new BasicAWSCredentials(accessKey, secretKey)
    }

    static void validateAWSCredentials(AWSCredentials awsCredentials) {
        if (awsCredentials.AWSAccessKeyId == null || awsCredentials.AWSAccessKeyId.isEmpty()) throw new IllegalArgumentException("Invalid AWS Credentials")
        if (awsCredentials.AWSSecretKey == null || awsCredentials.AWSSecretKey.isEmpty()) throw new IllegalArgumentException("Invalid AWS Credentials")
    }
}
