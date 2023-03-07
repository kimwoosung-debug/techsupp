package techsuppDev.techsupp.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import techsuppDev.techsupp.domain.QuestionEntity;

import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String questionWriter;
    private String questionCategory;
    private String questionTitle;
    private String questionContents;
//    private Date questionDate;
//    private int questionStatus;
    private String questionAnswer;

    private MultipartFile questionFile;
    private String originalFileName;
    private String storedFileName;
    private int fileAttached;



    public static QuestionDTO toQuestionDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(questionEntity.getQuestionId());
        questionDTO.setQuestionWriter(questionEntity.getQuestionWriter());
        questionDTO.setQuestionCategory(questionEntity.getQuestionCategory());
        questionDTO.setQuestionTitle(questionEntity.getQuestionTitle());
        questionDTO.setQuestionContents(questionEntity.getQuestionContents());
//        questionDTO.setQuestionStatus(questionEntity.getQuestionStatus());
        questionDTO.setQuestionAnswer(questionEntity.getQuestionAnswer());
        if (questionEntity.getFileAttached() == 0) {
            questionDTO.setFileAttached(questionEntity.getFileAttached()); // 0
        } else {
            questionDTO.setFileAttached(questionEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함.
            questionDTO.setOriginalFileName(questionEntity.getQuestionFileEntityList().get(0).getOriginalFileName());
            questionDTO.setStoredFileName(questionEntity.getQuestionFileEntityList().get(0).getStoredFileName());
        }

        return questionDTO;
    }



}
