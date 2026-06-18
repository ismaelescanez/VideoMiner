package peertubeminer.src.main.java.aiss.peertubeminer.service;

import peertubeminer.src.main.java.aiss.peertubeminer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeerTubeService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String videoMinerUri;

    @Autowired
    public PeerTubeService(
            RestTemplate restTemplate,
            @Value("${peertubeminer.baseuri}") String apiUrl,
            @Value("${videominer.uri}") String videoMinerUri) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.videoMinerUri = videoMinerUri;
    }

    public Channel fetchCompleteChannel(String channelId, int maxVideos, int maxComments) {
        String channelUrl = apiUrl + "/video-channels/" + channelId;

        try {
            Channel minedChannel = restTemplate.getForObject(channelUrl, Channel.class);
            if (minedChannel == null) return null;

            List<Video> channelVideos = fetchVideos(channelId, maxVideos);
            for (Video video : channelVideos) {
                video.setCaptions(fetchCaptions(video.getId()));
                video.setComments(fetchComments(video.getId(), maxComments));
            }

            minedChannel.setVideos(channelVideos);
            return minedChannel;

        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el canal en PeerTube: " + channelId, ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Fallo de conexión con PeerTube", ex);
        }
    }

    private List<Video> fetchVideos(String channelId, int limit) {
        String endpoint = apiUrl + "/video-channels/" + channelId + "/videos?count=" + limit;
        try {
            VideoResponse response = restTemplate.getForObject(endpoint, VideoResponse.class);
            return (response != null && response.getData() != null) ? response.getData() : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Caption> fetchCaptions(String videoId) {
        String endpoint = apiUrl + "/videos/" + videoId + "/captions";
        try {
            CaptionResponse response = restTemplate.getForObject(endpoint, CaptionResponse.class);
            return (response != null && response.getData() != null) ? response.getData() : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Comment> fetchComments(String videoId, int limit) {
        String endpoint = apiUrl + "/videos/" + videoId + "/comment-threads?count=" + limit;
        try {
            // Cambiado a CommentResponse
            CommentResponse response = restTemplate.getForObject(endpoint, CommentResponse.class);
            return (response != null && response.getData() != null) ? response.getData() : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Channel exportToVideoMiner(Channel channelPayload) {
        try {
            return restTemplate.postForObject(videoMinerUri, channelPayload, Channel.class);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error enviando datos a VideoMiner", ex);
        }
    }
}