package com.yantranet.gradle

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider

class S3Data {
    String accessKey, secretKey, profileName, bucketName, key

    protected AWSCredentials getAWSCredentials() {
        AWSCredentials credentials
        try {
            credentials = Utils.getCredentials(accessKey, secretKey)
            Utils.validateAWSCredentials(credentials)
        } catch (IllegalArgumentException ignore) {
            if (profileName == null || profileName.isEmpty()) {
                credentials = new ProfileCredentialsProvider().getCredentials()
                Utils.validateAWSCredentials(credentials)
            } else {
                try {
                    credentials = new ProfileCredentialsProvider(profileName).getCredentials()
                    Utils.validateAWSCredentials(credentials)
                } catch (IllegalArgumentException ignored) {
                    credentials = new ProfileCredentialsProvider().getCredentials()
                    Utils.validateAWSCredentials(credentials)
                }
            }
        }
        return credentials
    }
}
