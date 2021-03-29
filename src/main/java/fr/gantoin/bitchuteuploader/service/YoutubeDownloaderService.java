package fr.gantoin.bitchuteuploader.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;

import lombok.RequiredArgsConstructor;

import domain.BitchuteVideo;
import fr.gantoin.bitchuteuploader.service.mapper.YoutubeUrlMapper;

@Service
@RequiredArgsConstructor
public class YoutubeDownloaderService {

    private final YoutubeUrlMapper youtubeUrlMapper;

    public BitchuteVideo download(String youtubeUrl) throws YoutubeException, IOException {
        BitchuteVideo bitchuteVideo = new BitchuteVideo();

        YoutubeDownloader downloader = new YoutubeDownloader();
        downloader.setParserRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        downloader.setParserRetryOnFailure(1);
        YoutubeVideo video = downloader.getVideo(youtubeUrlMapper.map(youtubeUrl));

        VideoDetails details = video.details();
        bitchuteVideo.setTitle(details.title());
        String coverUrl = details.thumbnails().stream().findAny().orElseThrow();
        coverUrl = coverUrl.substring(0, coverUrl.indexOf(".jpg")).concat(".jpg");
        URL url = new URL(coverUrl);
        BufferedImage img = ImageIO.read(url);

        File cover = new File("my_videos/cover.jpg");
        ImageIO.write(img, "jpg", cover);
        bitchuteVideo.setCoverPath(cover.getAbsolutePath());
        bitchuteVideo.setDescription(video.details().description());

        File outputDir = new File("my_videos");
        File file = video.download(video.videoWithAudioFormats().get(0), outputDir);
        bitchuteVideo.setVideoPath(file.getAbsolutePath());
        return bitchuteVideo;
    }
}
