package techsuppDev.techsupp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import techsuppDev.techsupp.DTO.ProductDTO;
import techsuppDev.techsupp.DTO.ProductImgDTO;
import techsuppDev.techsupp.domain.Image;
import techsuppDev.techsupp.repository.ProductImageRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Log
@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageService {

    @Value("${imgLocation}")
    String imgLocation;
    private final ProductImageRepository productImageRepository;
    private final FileService fileService;

    public void saveImg(Image image, MultipartFile multipartFile) throws Exception {

        String originImgName = multipartFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(originImgName)) {
            imgName = fileService.uploadFile(imgLocation, originImgName, multipartFile.getBytes());
            imgUrl = "/upload/product/" + imgName;
        }

        image.updateProductImg(originImgName, imgName, imgUrl);
        productImageRepository.save(image);
    }

    public void updateImg(Long imgId, MultipartFile multipartFile) throws Exception {
        if (!multipartFile.isEmpty()) {
            Image image = productImageRepository.findById(imgId).orElseThrow(EntityNotFoundException::new);

            if (!StringUtils.isEmpty(image.getImgName())) {
                fileService.deleteFile(image.getOriginImgName());
            }

            String oriImgName = multipartFile.getOriginalFilename();
            String imgName = fileService.uploadFile(imgLocation, oriImgName, multipartFile.getBytes());
            String imgUrl = "/upload/product/" + imgName;
            image.updateProductImg(oriImgName, imgName, imgUrl);
        }
    }

    public void deleteImg(ProductDTO productDTO) throws IOException {

        if (productDTO.getProductImgDTOList().size() != 0) {
            fileService.deleteFile(productDTO.getProductImgDTOList().get(0).getOriginImgName());
            Long imgId = productDTO.getProductImgDTOList().get(0).getId();
            productImageRepository.delete(productImageRepository.findById(imgId).get());
        }
    }
}
