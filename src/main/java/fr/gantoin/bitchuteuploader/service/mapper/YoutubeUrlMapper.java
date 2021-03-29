package fr.gantoin.bitchuteuploader.service.mapper;

import org.springframework.stereotype.Service;

@Service
public class YoutubeUrlMapper {

    public String map(String youtubeUrl) {
        return youtubeUrl.substring(youtubeUrl.indexOf("?v=") + 3);
    }

}
