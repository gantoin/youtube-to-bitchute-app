package fr.gantoin.bitchuteuploader.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.github.kiulian.downloader.YoutubeException;

import fr.gantoin.bitchuteuploader.service.mapper.YoutubeUrlMapper;

class YoutubeDownloaderServiceIT {

    private YoutubeUrlMapper youtubeUrlMapper = new YoutubeUrlMapper();

    private YoutubeDownloaderService youtubeDownloaderService = new YoutubeDownloaderService(youtubeUrlMapper);

    @Test
    void download() throws IOException, YoutubeException {
        youtubeDownloaderService.download("https://www.youtube.com/watch?v=5AEKD1Vt6No");
    }
}
