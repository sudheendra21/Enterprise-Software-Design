package com.csye6220.Elearning.controller;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
//import software.amazon.awssdk.services.s3.model.S3ObjectSummary;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VideoController {

    private static final String BUCKET = "springboot-s3002";
    private static final Region REGION = Region.US_EAST_2;
    private final S3Client s3Client = S3Client.builder().region(REGION).build();

    @GetMapping("/videos")
    public String listVideos(Model model) {
        // List objects in the bucket
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(BUCKET).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        List<String> videoUrls = response.contents().stream()
                .filter(s -> s.key().endsWith(".mp4"))
                .map(s -> s3Client.utilities().getUrl(builder -> builder.bucket(BUCKET).key(s.key())).toString())
                .collect(Collectors.toList());

        model.addAttribute("videoUrls", videoUrls);

        return "videos";
    }
}