package com.sun.booking.firebase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.auth.*;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseService {

  @Value("${firebase.service.account.key.path}")
  private String serviceAccountKeyPath;

  @PostConstruct
  public void initializeFirebase() throws IOException {
    InputStream serviceAccount = new ClassPathResource(serviceAccountKeyPath).getInputStream();

    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();

    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }
  }

  public FirebaseAuth getFirebaseAuth() {
    return FirebaseAuth.getInstance();
  }

  public FirebaseToken verifyToken(String idToken) {
    try {
      FirebaseAuth auth = getFirebaseAuth();
      FirebaseToken decodedToken = auth.verifyIdToken(idToken);
      return decodedToken;
    } catch (FirebaseAuthException e) {
      e.printStackTrace();
      return null;
    }
  }
  
}
