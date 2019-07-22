package com.yantranet.gradle

class DownloadData extends S3Data {
    String name
    String localFilePath

    DownloadData(final String name){
        this.name = name
    }
}
