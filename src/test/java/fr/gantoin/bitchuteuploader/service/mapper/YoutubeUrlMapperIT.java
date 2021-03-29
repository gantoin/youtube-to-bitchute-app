package fr.gantoin.bitchuteuploader.service.mapper;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class YoutubeUrlMapperIT {

    private final YoutubeUrlMapper youtubeUrlMapper = new YoutubeUrlMapper();

    @Test
    void map() {
        Assertions.assertThat(youtubeUrlMapper.map("https://www.youtube.com/watch?v=XxOFy5wARCY")) //
                .isEqualTo("XxOFy5wARCY");
    }

}
