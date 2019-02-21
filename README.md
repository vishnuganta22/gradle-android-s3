# Java S3 Uploader

Upload Files to S3.

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
    classpath "gradle.plugin.com.yantranet.gradle:java-s3:1.0.10"
  }
}

```
## Step 2

Add the following to app's `build.gradle` below `android` block or to the bottom most

```
apply plugin: "com.rambabusaravanan.android-s3"

upload {
    accessKey = 'AKIA****************'
    secretKey = 'TPSi************************************'
    bucketName = "apkbuilder"
    fileLocalPaths = ["app/build/outputs/apk","example.txt"] localFilePaths are need to uploaded to S3

    s3Object = "path/to/key"                     // (Optional) 
    cannedAccessControlList = "Private"          //(Optional) {expected values : 'Private', 'AwsExecRead', 
    'BucketOwnerFullControl', 'BucketOwnerRead', 'LogDeliveryWrite', 'AuthenticatedRead', 'PublicReadWrite', 
    'PublicRead'}
}

```

## Execution

```
$ ./gradlew upload
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
