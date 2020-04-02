# Java S3 API

Upload Files to S3 and Download Files from S3.

## Step 1

Add the following to project's root `build.gradle` 

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.yantranet.gradle:java-s3:1.0.15"
  }
}

```
## Step 2

Add the following to modules's `build.gradle`

```
apply plugin: "com.yantranet.java-s3"

Example 1

uploads {
    devUpload{
        accessKey = 'AKIA****************'                      (Optional)
        secretKey = 'TPSi************************************'  (Optional)
        bucketName = "apkbuilder"
        fileLocalPaths = ["app/build/outputs/apk","example.txt"] localFilePaths are need to uploaded to S3

        key = "path/to/key"                           // (Optional) 
        cannedAccessControlList = "Private"          //(Optional) [expected values : 'Private', 'AwsExecRead', 
        'BucketOwnerFullControl', 'BucketOwnerRead', 'LogDeliveryWrite', 'AuthenticatedRead', 'PublicReadWrite', 
        'PublicRead']
    }
    stagingUpload{
        accessKey = 'AKIA****************'                      (Optional)
        secretKey = 'TPSi************************************'  (Optional)
        bucketName = "apkbuilder"
        fileLocalPaths = ["app/build/outputs/apk","example.txt"] localFilePaths are need to uploaded to S3
        
        key = "path/to/key"                           // (Optional) 
        cannedAccessControlList = "Private"          //(Optional) [expected values : 'Private', 'AwsExecRead', 
        'BucketOwnerFullControl', 'BucketOwnerRead', 'LogDeliveryWrite', 'AuthenticatedRead', 'PublicReadWrite', 
        'PublicRead']
    }    
    prodUpload{
        accessKey = 'AKIA****************'                      (Optional)
        secretKey = 'TPSi************************************'  (Optional)
        bucketName = "apkbuilder"
        fileLocalPaths = ["app/build/outputs/apk","example.txt"] localFilePaths are need to uploaded to S3
    
        key = "path/to/key"                           // (Optional) 
        cannedAccessControlList = "Private"          //(Optional) [expected values : 'Private', 'AwsExecRead', 
        'BucketOwnerFullControl', 'BucketOwnerRead', 'LogDeliveryWrite', 'AuthenticatedRead', 'PublicReadWrite', 
        'PublicRead']
    } 
}

Example 2

uploads.create('devUpload', {
    profileName = 'test-profile'                // (Optional) name of the aws-profile
    bucketName = "apkbuilder"
    fileLocalPaths = ["app/build/outputs/apk","example.txt"] 
    
    key = "path/to/key"                     
    cannedAccessControlList = "Private"
})
uploads.create('stagingUpload', {
    profileName = 'test-profile'                // (Optional) name of the aws-profile
    bucketName = "apkbuilder"
    fileLocalPaths = ["app/build/outputs/apk","example.txt"] 
    
    key = "path/to/key"                     
    cannedAccessControlList = "Private"
})
uploads.create('prodUpload', {
    profileName = 'test-profile'                // (Optional) name of the aws-profile
    bucketName = "apkbuilder"
    fileLocalPaths = ["app/build/outputs/apk","example.txt"] 
    
    key = "path/to/key"                     
    cannedAccessControlList = "Private"
})

Example 3

downloads {
    devDownloads{
        accessKey = 'AKIA****************'
        secretKey = 'TPSi************************************'
        bucketName = 'apkbuilder'
        key = 'java/testing/test.txt'
        localFilePath = new File(rootDir, 'text.txt')
    }
}

Example 4

downloads.create('devDownloads', {
        accessKey = 'AKIA****************'
        secretKey = 'TPSi************************************'
        bucketName = 'apkbuilder'
        key = 'java/testing/test.txt'
        localFilePath = new File(rootDir, 'text.txt')
})
downloads.create('stagingDownloads', {
        accessKey = 'AKIA****************'
        secretKey = 'TPSi************************************'
        bucketName = 'apkbuilder'
        key = 'java/testing/test.txt'
        localFilePath = new File(rootDir, 'text.txt')
})

```

## Execution

```
$ ./gradlew upload
```

```
$ ./gradlew download
```


## Sample Log 

```
andro@thiyagab:~/workspace/gradle-plugin/android-example$ ./gradlew upload
...
...
...
> Task :app:uploadApk
Upload src : /home/andro/workspace/gradle-plugin/android-example/app/build/outputs/apk
Uploading to  :: s3://apkbuilder/com.rambabusaravanan.gradlepluginconsumer/1.0
Upload finished ..

BUILD SUCCESSFUL

Total time: 6.548 secs
```
