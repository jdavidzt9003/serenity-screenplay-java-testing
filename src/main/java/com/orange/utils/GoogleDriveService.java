package com.orange.utils;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.orange.exceptions.RuntimeCommonException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleDriveService {

    private GoogleDriveService() {
    }

    private static final String APPLICATION_NAME = "Serenity Automation Reports";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials/client_secret.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    public static Drive getDriveService() {

        try {
            NetHttpTransport httpTransport =
                    GoogleNetHttpTransport.newTrustedTransport();

            InputStream credentialsStream =
                    GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

            if (credentialsStream == null) {
                throw new RuntimeCommonException(
                        "No se encontró client_secret.json en src/test/resources"
                );
            }

            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JSON_FACTORY,
                    new InputStreamReader(credentialsStream)
            );

            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            httpTransport,
                            JSON_FACTORY,
                            clientSecrets,
                            Collections.singletonList(DriveScopes.DRIVE)
                    )
                            .setDataStoreFactory(
                                    new FileDataStoreFactory(
                                            new java.io.File(TOKENS_DIRECTORY_PATH)
                                    )
                            )
                            .setAccessType("offline")
                            .build();

            LocalServerReceiver receiver =
                    new LocalServerReceiver.Builder()
                            .setPort(8888)
                            .build();

            return new Drive.Builder(
                    httpTransport,
                    JSON_FACTORY,
                    new AuthorizationCodeInstalledApp(flow, receiver)
                            .authorize("user")
            )
                    .setApplicationName(APPLICATION_NAME)
                    .build();

        } catch (IOException e) {
            throw new RuntimeCommonException(
                    "Error de I/O durante la autenticación con Google Drive", e
            );

        } catch (GeneralSecurityException e) {
            throw new RuntimeCommonException(
                    "Error de seguridad al inicializar Google Drive", e
            );
        }
    }
}
