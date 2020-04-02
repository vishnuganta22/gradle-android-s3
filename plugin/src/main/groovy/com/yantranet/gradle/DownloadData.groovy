package com.yantranet.gradle

class DownloadData extends S3Data {
    String name
    String[] fileLocalPaths

    DownloadData(final String name){
        this.name = name
    }
}
