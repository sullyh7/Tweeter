package com.sulay.tweeter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final S3Service s3Service;
    // Save multipart files to s3 bucket, then retrieve the url and save to database

    // for profile photos, save to the profile picture url column of the user entity
    // for tweets with media, create new entity for media tweets which extends tweet. It will have a extra column with
    // media url.
    // same for media reply entity
}

