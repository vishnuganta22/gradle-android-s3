package com.yantranet.gradle

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider

class AWSUtils {
    static String getAWSAccessId(String profileName){
        if(profileName == null || profileName.isEmpty()) return ""
        AWSCredentials credentials = new ProfileCredentialsProvider(profileName).getCredentials()
        if(credentials == null) return ""
        return credentials.AWSAccessKeyId
    }

    static String getAWSSecretKey(String profileName){
        if(profileName == null || profileName.isEmpty()) return ""
        AWSCredentials credentials = new ProfileCredentialsProvider(profileName).getCredentials()
        if(credentials == null) return ""
        return credentials.AWSSecretKey
    }
}
