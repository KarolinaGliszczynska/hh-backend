package com.codecool.helpinghands.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "imageData")
@Data
@NoArgsConstructor
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @OneToOne
    @JoinColumn(name = "event_event_id")
    private Event event;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    public ImageData(String fileName, String fileContentType, Event event, byte[] compressedImageData) {
        this.name = fileName;
        this.type = fileContentType;
        this.event = event;
        this.imageData = compressedImageData;
    }
}
