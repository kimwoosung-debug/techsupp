package techsuppDev.techsupp.DTO;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class NoticeImageFileDTO {

    private String loadId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    private Long seqId;

    private String imagePath;
    private String imageName;

    private Long imageSize;

    private String imageType;
}
