package com.devcamp.eztour.controller.product;

import com.devcamp.eztour.domain.product.*;
import com.devcamp.eztour.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    // 관리자 페이지 진입
    @GetMapping("/product/admin")
    public String productAdmin(HttpSession session, RedirectAttributes rattr){
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
            return "redirect:/";
        }else{
            return "product/admin_main.tiles";
        }
    }

    // 관리자 상품 등록 페이지 진입
    @GetMapping("/product/insert")
    public String insertProduct(HttpSession session,RedirectAttributes rattr){
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
            return "redirect:/";
        }else{
            return "product/product_insert.tiles";
        }

    }

    // 관리자 상품 등록(실제 정보 전송)
    @PostMapping("/product/insert")
    public String insertProduct(TrvPrdWriteDto trvPrdWriteDto, RedirectAttributes redirectAttributes) throws Exception{

        int result = productService.insertProduct(trvPrdWriteDto);

        if(result == 1){
            redirectAttributes.addAttribute("prd_cd", trvPrdWriteDto.getPrd_cd());
            return "redirect:/product/detail/insert";
        }else{
            redirectAttributes.addFlashAttribute("error_msg","상품 등록에 실패했습니다.");
            return "redirect:/product/insert";
        }

    }

    // 관리자 상품 상세 등록 페이지 진입
    @GetMapping("/product/detail/insert")
    public String productDetailInsert(HttpSession session,RedirectAttributes rattr){
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
            return "redirect:/";
        }else{
            return "product/product_detail_insert.tiles";
        }

    }

    // 관리자 상품 상세 등록(실제 정보 전송)
    @PostMapping("/product/detail/insert")
    public String productDetailInsert(TrvPrdDtlWriteDto trv_prdDtlDto, RedirectAttributes redirectAttributes) throws Exception{
        int result = productService.insertProductDetail(trv_prdDtlDto);
        if(result==1){
            redirectAttributes.addAttribute("prd_dtl_cd", trv_prdDtlDto.getPrd_dtl_cd());
            redirectAttributes.addAttribute("prd_cd", trv_prdDtlDto.getPrd_cd());
            return "redirect:/product/insert/price";
        }else{
            redirectAttributes.addFlashAttribute("error_msg","상품 상세 등록이 실패하였습니다.");
            return "redirect:/product/detail/insert";
        }
    }

    // 관리자 상품 가격 등록 페이지 진입
    @GetMapping("/product/insert/price")
    public String insertProductPrice(HttpSession session,RedirectAttributes rattr) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
            return "redirect:/";
        }else{
            return "product/product_insert_price.tiles";
        }

    }

    // 관리자 상품 가격 등록(실제 정보 전송)
    @PostMapping("/product/insert/price")
    public String insertProductPrice(TrvPrdPrcDto trv_prdPrcDto, RedirectAttributes redirectAttributes) throws Exception{
        int result = productService.insertProductPrice(trv_prdPrcDto);
        if(result==1){
            redirectAttributes.addAttribute("prd_cd", trv_prdPrcDto.getPrd_cd());
            return "redirect:/product/insert/schedule";
        }else{
            redirectAttributes.addAttribute("error_msg","가격 추가에 실패하였습니다.");
            return "redirect:/product/insert/price";
        }

    }

    // 관리자 상품 일정 등록 페이지 진입
    @GetMapping("/product/insert/schedule")
    public String insertProductSchedule(HttpSession session, RedirectAttributes rattr) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
            return "redirect:/";
        }else{
            return "product/product_insert_sch.tiles";
        }

    }

    // 관리자 상품 일정 등록(상세 정보 전송)
    @PostMapping("/product/insert/schedule")
    public String insertProductSchedule(TrvSchDto trv_schDto, RedirectAttributes redirectAttributes) throws Exception{
        int result = productService.insertProductSchedule(trv_schDto);
        if(result==1){
            redirectAttributes.addAttribute("prd_cd", trv_schDto.getPrd_cd());
            return "redirect:/product/insert/image";
        }else{
            redirectAttributes.addAttribute("error_msg","오류가 발생하였습니다.");
            return "redirect:/product/insert/schedule";
        }
    }

    // 관리 상품 이미지 등록 페이지 진입
    @GetMapping("/product/insert/image")
    public String insertProductImage(HttpSession session,RedirectAttributes rattr){
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
                rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
                return "redirect:/";
            }else{
                return "product/product_img_insert.tiles";
            }
    }

    // 관리 상품 이미지 등록(ajax 사용 정보 전송)
    @PostMapping("/product/insert/image")
    @ResponseBody
    public String insertProductImage(MultipartFile img_file, HttpServletRequest request, String prd_cd,String frs_rgs_no) throws Exception{
        // 원본 파일이 이미지 파일이 맞는지 확장자를 확인
        File checkFile = new File(img_file.getOriginalFilename());
        String type = null;
        try {
            type = Files.probeContentType(checkFile.toPath());
            // 프로젝트 root 경로 확인 -> 이미지 경로 잡기
            HttpSession session = request.getSession();
            String root_path = session.getServletContext().getRealPath("/");
            String uploadPath = root_path+"resources/image/product";
            // 이미지 파일이 아닐경우 실패
            if(!type.startsWith("image")){
                return "fail";
            }else if(type==null){
                return "fail";
            }else{
                String fileName = UUID.
                        randomUUID().toString()+".jpg";
                File uploadFile = new File(uploadPath, fileName);
                img_file.transferTo(uploadFile);
                String finalPath = "/image/product/"+fileName;
                PrdImgDto prd_imgDto = new PrdImgDto(prd_cd,finalPath,frs_rgs_no);
                int result = productService.insertProductImg(prd_imgDto);
                if(result == 1){
                    return "success";
                }else{
                    return "fail";
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 관리 상품 일정 페이지 진입
    @GetMapping("/product/schedule/image/insert")
    public String insertScheduleImage(HttpSession session,RedirectAttributes rattr){
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            rattr.addFlashAttribute("admin_error_msg","권한이 없습니다. 홈페이지로 이동합니다.");
            return "redirect:/";
        }else{
            return "product/product_sch_img_insert.tiles";
        }
    }

    // 관리 상품 일정 등록(ajax 사용 정보 전송)
    @ResponseBody
    @PostMapping("/product/schedule/image/insert")
    public String insertScheduleImage(MultipartHttpServletRequest meq, HttpServletRequest request, int sch_no, String prd_cd,String frs_rgs_no) throws Exception{
        int result = 0;
        String type = null;
        // 프로젝트 root 경로 확인 -> 이미지 경로 잡기
        HttpSession session = request.getSession();
        String root_path = session.getServletContext().getRealPath("/");
        String uploadPath = root_path + "resources/image/product/sights";

        List<MultipartFile> fileList =  meq.getFiles("prd_img");
        for (MultipartFile m : fileList) {
            File checkFile = new File(m.getOriginalFilename());
            try {
                    type = Files.probeContentType(checkFile.toPath());
                    if(!type.startsWith("image")){
                        return "fail";
                    }else if(type == null){
                        return "fail";
                    }else{
                        String fileName = UUID.randomUUID().toString() + ".jpg";
                        File uploadFile = new File(uploadPath, fileName);
                        m.transferTo(uploadFile);
                        String finalPath = "/image/product/sights/" + fileName;
                        TrvSchImgDto trv_schImgDto = new TrvSchImgDto(sch_no, prd_cd,finalPath,frs_rgs_no);
                        productService.insertScheduleImage(trv_schImgDto);
                        result++;
                    }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(result == 3){
            return "success";
        }else{
            return "fail";
        }
    }

    // 상품 관리 페이지 진입(리스트 뿌리기)
    @GetMapping("/product/management")
    public String productManagement(HttpSession session, Model model, @RequestParam(value = "page",defaultValue = "1") int page
                                    ,String search_option,String search_keyword) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            if(search_keyword==null || search_keyword==""){
                int totalCnt = productService.selectProductAdminCnt();
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page);
                List<TrvPrdReadDto> prdDtoList = productService.selectProductAdmin(paging);
                model.addAttribute("prd_list",prdDtoList);
                model.addAttribute("paging",paging);
                return "product/product_management.tiles";
            }else{
                PageHandlerProduct option = new PageHandlerProduct(search_option,search_keyword);
                int totalCnt = productService.searchSelectProductAdminCnt(option);
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,search_option,search_keyword);
                List<TrvPrdReadDto> prdDtoList = productService.searchSelectProductAdmin(paging);
                model.addAttribute("prd_list",prdDtoList);
                model.addAttribute("paging",paging);
                return "product/product_management.tiles";
            }
        }
    }

    // 상품 관리 페이지 상품 정보 읽기
    @GetMapping("/product/read")
    public String productRead(HttpSession session, String prd_cd,Model model) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvPrdReadDto trvPrdDto = productService.selectProduct(prd_cd);
            model.addAttribute("trvPrdDto",trvPrdDto);
            return "product/product_read.tiles";
        }
    }

    // 상품 수정 페이지 진입
    @GetMapping("/product/modify")
    public String productModify(HttpSession session, String prd_cd,Model model) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvPrdReadDto trvPrdDto = productService.selectProduct(prd_cd);
            model.addAttribute("trvPrdDto",trvPrdDto);
            return "product/product_modify.tiles";
        }
    }

    // 상품 수정(실제 정보 전송)
    @PostMapping("/product/modify")
    public String productModify(TrvPrdWriteDto trvPrdWriteDto,RedirectAttributes redirectAttributes) throws Exception{
            int result = productService.updateProduct(trvPrdWriteDto);
            if(result==1){
                redirectAttributes.addFlashAttribute("success_msg","상품 수정에 성공했습니다.");
                return "redirect:/product/management";
            }else{
                redirectAttributes.addFlashAttribute("error_msg","상품 수정에 실패했습니다.");
                return "redirect:/product/modify?prd_cd="+trvPrdWriteDto.getPrd_cd();
            }
    }

    // 상품 삭제(ajax 사용정보 전송)
    @ResponseBody
    @PostMapping("/product/delete")
    public String productDelete(String prd_cd) throws Exception{
        int result = productService.deleteProduct(prd_cd);
        if(result==1){
            return "success";
        }else{
            return "fail";
        }
    }

    // 상품 상세 관리 페이지 진입(리스트 뿌리기)
    @GetMapping("/product/management/detail")
    public String productManagementDetail(HttpSession session, Model model, @RequestParam(value = "page",defaultValue = "1") int page
            ,String search_option,String search_keyword) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            if(search_option==null || search_option==""){
                int totalCnt = productService.selectProductAdminDetailCnt();
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page);
                List<TrvPrdDtlReadDto> prdDetailList = productService.selectProductAdminDetail(paging);
                model.addAttribute("prd_dtl_list",prdDetailList);
                model.addAttribute("paging",paging);
                return "product/product_management_detail.tiles";
            }else{
                PageHandlerProduct option = new PageHandlerProduct(search_option,search_keyword);
                int totalCnt = productService.searchSelectProductAdminDetailCnt(option);
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,search_option,search_keyword);
                List<TrvPrdDtlReadDto> prdDetailList = productService.searchSelectProductAdminDetail(paging);
                model.addAttribute("prd_dtl_list",prdDetailList);
                model.addAttribute("paging",paging);
                return "product/product_management_detail.tiles";
            }
        }
    }

    @GetMapping("/product/detail/read")
    public String productDetailRead(HttpSession session, Model model, String prd_dtl_cd) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvPrdDtlReadDto trvPrdDtlDto = productService.selectProductDetail(prd_dtl_cd);
            model.addAttribute("prd_dtl",trvPrdDtlDto);
            return "product/product_detail_read.tiles";
        }
    }

    @GetMapping("/product/detail/modify")
    public String productDetailModify(HttpSession session, String prd_dtl_cd, Model model) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvPrdDtlReadDto trvPrdDtlDto = productService.selectProductDetail(prd_dtl_cd);
            model.addAttribute("prd_dtl",trvPrdDtlDto);
            return "product/product_detail_modify.tiles";
        }
    }

    @PostMapping("/product/detail/modify")
    public String productDetailModify(TrvPrdDtlWriteDto trvPrdDtlDto,RedirectAttributes rattr) throws Exception{
        int result = productService.updateProductDetail(trvPrdDtlDto);
        if(result==1){
            rattr.addFlashAttribute("success_msg","상품상세 수정이 성공하였습니다.");
            return "redirect:/product/management/detail";
        }else{
            rattr.addFlashAttribute("error_msg","상품상세 수정이 실패하였습니다.");
            rattr.addAttribute("prd_dtl",trvPrdDtlDto);
            return "redirect:/product/detail/modify";
        }
    }

    @ResponseBody
    @PostMapping("/product/detail/delete")
    public String productDetailDelete(String prd_dtl_cd) throws Exception{
        int result = productService.deleteProductDetail(prd_dtl_cd);
        if(result==1){
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/product/management/image")
    public String productImageManagement(HttpSession session,Model model,@RequestParam(value = "page",defaultValue = "1") int page
            ,String search_option,String search_keyword) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            if(search_option == null || search_option == ""){
                int totalCnt = productService.selectProductImageCnt();
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,5);
                List<TrvPrdReadDto> prdDtoList = productService.selectProductImage(paging);
                model.addAttribute("prd_list",prdDtoList);
                model.addAttribute("paging",paging);
                return "product/product_management_img.tiles";
            }else{
                PageHandlerProduct option = new PageHandlerProduct(search_option,search_keyword);
                int totalCnt = productService.searchSelectProductImageCnt(option);
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,search_option,search_keyword,5);
                List<TrvPrdReadDto> prdDtoList = productService.searchSelectProductImage(paging);
                model.addAttribute("prd_list",prdDtoList);
                model.addAttribute("paging",paging);
                return "product/product_management_img.tiles";
            }
        }
    }

    @GetMapping("/product/image/read")
    public String productImageRead(String img_pth,String prd_cd,String prd_nm,Model model,HttpSession session){
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            model.addAttribute("img_pth",img_pth);
            model.addAttribute("prd_cd",prd_cd);
            model.addAttribute("prd_nm",prd_nm);
            return "product/product_image_read.tiles";
        }
    }

    @ResponseBody
    @PostMapping("/product/image/delete")
    public String productImageDelete(int prd_img_no,String img_pth,HttpServletRequest request) throws Exception{
        int result = productService.deleteProductImage(prd_img_no);

        if(result==1){
            // 이미지를 삭제
            boolean flag = deleteImage(request,img_pth);
            if(flag){
                return "success";
            }else{
                return "fail";
            }
        }
        return "fail";
    }

    @GetMapping("/product/image/modify")
    public String productImageModify(HttpSession session,Integer prd_img_no,String img_pth ,String prd_cd,Model model) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            model.addAttribute("prd_img_no",prd_img_no);
            model.addAttribute("img_pth",img_pth);
            model.addAttribute("prd_cd",prd_cd);
            return "product/product_img_modify.tiles";
        }

    }


    @ResponseBody
    @PostMapping("/product/image/modify")
    public String productImageModify(MultipartFile img_file,String img_pth,String prd_cd,Integer prd_img_no,
                                     HttpServletRequest request) throws Exception{
        // 원본 파일이 이미지 파일이 맞는지 확장자를 확인
        File checkFile = new File(img_file.getOriginalFilename());
        String type = null;
        try {
            type = Files.probeContentType(checkFile.toPath());
            // 프로젝트 root 경로 확인 -> 이미지 경로 잡기
            HttpSession session = request.getSession();
            String root_path = session.getServletContext().getRealPath("/");
            String uploadPath = root_path+"resources/image/product";
            boolean flag = deleteImage(request,img_pth);
            if(flag){
                // 이미지 파일이 아닐경우 실패
                if(!type.startsWith("image")){
                    return "fail";
                }else if(type==null){
                    return "fail";
                }else{
                    String fileName = UUID.
                            randomUUID().toString()+".jpg";
                    File uploadFile = new File(uploadPath, fileName);
                    img_file.transferTo(uploadFile);
                    String finalPath = "/image/product/"+fileName;
                    PrdImgDto prdImgDto = new PrdImgDto(finalPath,prd_img_no,prd_cd);
                    int result = productService.updateProductImage(prdImgDto);
                    if(result == 1){
                        return "success";
                    }else{
                        return "fail";
                    }

                }
            }else{
                return "fail";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/product/management/schedule")
    public String productScheduleManagement(HttpSession session,Model model,@RequestParam(value = "page",defaultValue = "1") int page
            ,String search_option,String search_keyword) throws Exception {
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            if(search_option==null || search_option==""){
                int totalCnt = productService.getScheduleCnt();
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page);
                List<TrvSchDto> trvList = productService.getScheduleList(paging);
                model.addAttribute("list",trvList);
                model.addAttribute("paging",paging);
                return "product/product_management_schedule.tiles";
            }else{
                PageHandlerProduct option = new PageHandlerProduct(search_option,search_keyword);
                int totalCnt = productService.getSearchScheduleCnt(option);
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,search_option,search_keyword);
                List<TrvSchDto> trvList = productService.getSearchSchedule(paging);
                model.addAttribute("list",trvList);
                model.addAttribute("paging",paging);
                return "product/product_management_schedule.tiles";
            }

        }

    }

    @GetMapping("/product/schedule/read")
    public String productScheduleRead(HttpSession session, int sch_no,Model model) throws Exception {
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvSchDto trvSchDto = productService.getSchedule(sch_no);
            model.addAttribute("list",trvSchDto);
            return "product/product_sch_read.tiles";
        }
    }

    @ResponseBody
    @PostMapping("/product/schedule/delete")
    public String productScheduleDelete(int sch_no) throws Exception{
        int result = productService.removeSchedule(sch_no);
        if(result == 1){
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/product/schedule/modify")
    public String productScheduleModify(int sch_no,Model model,HttpSession session) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvSchDto trvSchDto = productService.getSchedule(sch_no);
            model.addAttribute("list",trvSchDto);
            return "product/product_sch_modify.tiles";
        }
    }

    @PostMapping("/product/schedule/modify")
    public String productScheduleModify(TrvSchDto trvSchDto,RedirectAttributes rattr) throws Exception{
        int result = productService.updateSchedule(trvSchDto);
        if(result == 1){
            rattr.addFlashAttribute("success_msg","상품상세 수정이 성공하였습니다.");
            return "redirect:/product/management/schedule";
        }else{
            rattr.addFlashAttribute("error_msg","상품상세 수정이 실패하였습니다.");
            rattr.addAttribute("list",trvSchDto);
            return "redirect:/product/schedule/read";
        }
    }

    @GetMapping("/product/management/price")
    public String productPriceManagement(HttpSession session,Model model,@RequestParam(value = "page",defaultValue = "1") int page
            ,String search_option,String search_keyword) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            if(search_keyword == null || search_keyword ==""){
                int totalCnt = productService.getProductPriceCnt();
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page);
                List<PrdPcrDto> prdList = productService.getProductPrice(paging);
                model.addAttribute("paging",paging);
                model.addAttribute("list",prdList);
                return "product/product_management_price.tiles";
            }else{
                PageHandlerProduct option = new PageHandlerProduct(search_option,search_keyword);
                int totalCnt = productService.getSearchProductPriceCnt(option);
                System.out.println(totalCnt);
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,search_option,search_keyword);
                List<TrvPrdPrcDto> prdList = productService.getSearchProductPrice(paging);
                model.addAttribute("list",prdList);
                model.addAttribute("paging",paging);
                return "product/product_management_price.tiles";
            }
        }
    }

    @GetMapping("/product/price/read")
    public String productPriceRead(HttpSession session,Model model,int prd_prc_no) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvPrdPrcDto trvPrdPrcDto = productService.getOneSearchProductPrice(prd_prc_no);
            model.addAttribute("list",trvPrdPrcDto);
            return "product/product_price_read.tiles";
        }
    }

    @ResponseBody
    @PostMapping("/product/price/delete")
    public String productPriceDelete(int prd_prc_no) throws Exception{
        System.out.println(prd_prc_no);
        int result = productService.removeProductPrice(prd_prc_no);
        if(result == 1){
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/product/price/modify")
    public String productPriceModify(int prd_prc_no, HttpSession session, Model model) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            TrvPrdPrcDto trvPrdPrcDto = productService.getOneSearchProductPrice(prd_prc_no);
            model.addAttribute("list",trvPrdPrcDto);
            return "product/product_price_modify.tiles";
        }
    }

    @PostMapping("/product/price/modify")
    public String productPriceModify(TrvPrdPrcDto trvPrdPrcDto,RedirectAttributes rattr) throws Exception{
        int result = productService.modifyProductPrice(trvPrdPrcDto);
        System.out.println(trvPrdPrcDto);
        if(result == 1){
            rattr.addFlashAttribute("success_msg","상품상세 수정이 성공하였습니다.");
            return "redirect:/product/management/price";
        }else{
            rattr.addFlashAttribute("error_msg","상품상세 수정이 실패하였습니다.");
            rattr.addAttribute("list",trvPrdPrcDto);
            return "redirect:/product/price/read";
        }
    }

    @GetMapping("/product/management/schedule/image")
    public String productScheduleImageManagement(HttpSession session,Model model,@RequestParam(value = "page",defaultValue = "1") int page
            ,String search_option,String search_keyword) throws Exception{
        boolean isAdmin =  isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            if(search_keyword == null || search_keyword == ""){
                int totalCnt = productService.getAllScheduleImageCnt();
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page);
                List<TrvSchImgDto> list = productService.getAllScheduleImage(paging);
                model.addAttribute("list",list);
                model.addAttribute("paging",paging);
                return "product/product_management_schedule_img.tiles";
            }else{
                PageHandlerProduct option = new PageHandlerProduct(search_option,search_keyword);
                int totalCnt = productService.getSearchScheduleImageCnt(option);
                PageHandlerProduct paging = new PageHandlerProduct(totalCnt,page,search_option,search_keyword);
                List<TrvSchImgDto> list = productService.getSearchScheduleImage(paging);
                model.addAttribute("list",list);
                model.addAttribute("paging",paging);
                return "product/product_management_schedule_img.tiles";
            }

        }
    }

    @GetMapping("/product/schedule/image/read")
    public String productScheduleImageRead(String prd_cd,HttpSession session,Model model) throws Exception{
        boolean isAdmin = isAdmin(session);
        if(!isAdmin){
            return "redirect:/";
        }else{
            List<TrvSchImgDto> list = productService.getScheduleImage(prd_cd);
            model.addAttribute("list",list);
            return "product/product_sch_img_read.tiles";
        }
    }

    @ResponseBody
    @PostMapping("/product/schedule/image/delete")
    public String productScheduleImageDelete(HttpServletRequest request, String[] img_pth, String prd_cd) throws Exception{
        int ImageDelCnt = 0;
        for(String s : img_pth){
            boolean flag = deleteImage(request,s);
            if(flag){
                ImageDelCnt++;
            }
        }
        int dataDelCnt = productService.removeScheduleImage(prd_cd);
        if(ImageDelCnt == 3 && dataDelCnt == 3){
            return "success";
        }else{
            return "fail";
        }
    }


    private boolean deleteImage(HttpServletRequest request,String img_pth){
        HttpSession session = request.getSession();
        String root_path = session.getServletContext().getRealPath("/");
        String uploadPath = root_path+"resources"+img_pth;
        File file = new File(uploadPath);
        boolean flag = file.delete();
        return flag;
    }

    private boolean isAdmin(HttpSession session){
        String id = (String)session.getAttribute("usr_id");
        if(id.equals("admin")){
            return true;
        }else{
            return false;
        }
    }
}
