package com.yantranet.gradle

class UploadData extends S3Data {
    String name
    String cannedAccessControlList
    String[] fileLocalPaths

    UploadData(final String name){
        this.name = name
    }
}
