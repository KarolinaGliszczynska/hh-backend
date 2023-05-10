package com.codecool.helpinghands.service;

import com.codecool.helpinghands.dto.EventWithSlotsDTO;
import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.ImageData;
import com.codecool.helpinghands.repository.ImageDataRepository;
import com.codecool.helpinghands.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;

    @Autowired
    public ImageDataService (ImageDataRepository imageDataRepository){
        this.imageDataRepository = imageDataRepository;
    }


    public void addPictureToEvent (MultipartFile file, Event event) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileContentType = file.getContentType();
        byte[] compressedImageData = ImageUtil.compressImage(file.getBytes());
        imageDataRepository.save(new ImageData(fileName, fileContentType, event, compressedImageData));
    }


    @Transactional
    public byte[] getImageByEventId(Event event) {
        ImageData dbImage = imageDataRepository.findByEvent(event).orElse(null);
        if (dbImage == null){
            return null;
        };
        return ImageUtil.decompressImage(dbImage.getImageData());
    }


}
