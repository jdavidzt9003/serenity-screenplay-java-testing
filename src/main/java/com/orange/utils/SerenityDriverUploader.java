package com.orange.utils;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.FileContent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SerenityDriverUploader {

    private static final String DRIVE_FOLDER_ID = "1tV_pwP7kmlALb2RKG9lbA5Kqf2lazYrP";
    private static final String SERENITY_DIR = "target/site/serenity";

    public static void main(String[] args) throws Exception {

        // 1️⃣ Comprimir Serenity
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

        Path zipPath = Paths.get("target/serenity-report-" + timestamp + ".zip");

        java.io.File zipFile = ZipUtils.zipDirectory(
                Paths.get(SERENITY_DIR),
                zipPath
        );

        System.out.println("Serenity comprimido: " + zipFile.getName());


        Drive driveService = GoogleDriveService.getDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(zipFile.getName());
        fileMetadata.setParents(java.util.List.of(DRIVE_FOLDER_ID));

        FileContent mediaContent =
                new FileContent("application/zip", zipFile);

        driveService.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        System.out.println("✔ ZIP subido a Google Drive");

        // 3️⃣ Finalizar proceso (CRÍTICO)
        System.exit(0);
    }
}
