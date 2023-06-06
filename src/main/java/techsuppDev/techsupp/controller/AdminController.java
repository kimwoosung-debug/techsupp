package techsuppDev.techsupp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techsuppDev.techsupp.DTO.PageRequestDTO;
import techsuppDev.techsupp.DTO.ProductDTO;
import techsuppDev.techsupp.DTO.ProductImgDTO;
import techsuppDev.techsupp.controller.form.AdminPaymentForm;
import techsuppDev.techsupp.domain.Product;
import techsuppDev.techsupp.repository.AdminProductRepository;
import techsuppDev.techsupp.service.AdminProductService;
import techsuppDev.techsupp.service.ProductImageService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminProductService adminProductService;
    private final ProductImageService productImageService;

    @GetMapping("/")
    public String Home() {
        return "admin/adminhome";
    }
    @GetMapping("/product/list")
    public String list(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", adminProductService.getList(pageRequestDTO));
        return "admin/Product/list";
    }

    @GetMapping("/product/register")
    public String register(Model model) {
        model.addAttribute("productForm", new ProductDTO());
        return "admin/Product/create";
    }

    @PostMapping("/product/register")
    public String registerPost(@ModelAttribute ProductDTO productDTO,
                               @RequestParam("productImgFile") List<MultipartFile> multipartFileList) throws Exception {

        adminProductService.register(productDTO, multipartFileList);

//                               RedirectAttributes redirectAttributes,
//                               @RequestParam MultipartFile file) throws IOException {

//        Long productId = adminProductService.register(productDTO);
//        redirectAttributes.addFlashAttribute("msg", productId);

        return "redirect:/admin/product/list";
    }

    @GetMapping("product/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        ProductDTO productDTO = adminProductService.getProductDetail(id);
        System.out.println(productDTO);
        model.addAttribute("productForm", productDTO);


        return "admin/Product/edit";
    }
    @PostMapping("product/edit/{id}")
    public String editPost(@ModelAttribute("productForm") ProductDTO productDTO,
                           @RequestParam("productImgFile") List<MultipartFile> multipartFileList) throws Exception {

        adminProductService.update(productDTO, multipartFileList);

        return "redirect:/admin/product/list";

    }
    @GetMapping("product/delete/{id}")
    public String remove(@PathVariable("id") Long id) throws IOException {

        ProductDTO productDTO = adminProductService.getProductDetail(id);
        productImageService.deleteImg(productDTO);
        adminProductService.remove(id);

        return "redirect:/admin/product/list";
    }
    @GetMapping("payment/list")
    public String paymentList(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", adminProductService.paymentList(pageRequestDTO));
        return "admin/Payment/paymentlist";
    }

}
