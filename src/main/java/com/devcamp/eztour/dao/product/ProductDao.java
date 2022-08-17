package com.devcamp.eztour.dao.product;

import com.devcamp.eztour.domain.product.*;

import java.util.List;

public interface ProductDao {

    public int insertProduct(TrvPrdWriteDto trvPrdWriteDto) throws Exception;
    public int insertProductDetail(TrvPrdDtlReadDto trvprdDtlDto) throws Exception;
    public int insertProductPrice(TrvPrdPrcDto trvprdPrcDto) throws Exception;
    public int insertProductSchedule(TrvSchDto trvschDto) throws Exception;
    public int insertProductImg(PrdImgDto prdimgDto) throws Exception;
    public int insertScheduleImage(TrvSchImgDto trvschImgDto) throws Exception;
    public List<TrvPrdReadDto> selectProductAdmin(PageHandlerProduct pageHandlerProduct) throws Exception;
    public int selectProductAdminCnt() throws Exception;
    public List<TrvPrdReadDto> searchSelectProductAdmin(PageHandlerProduct pageHandlerProduct) throws Exception;
    public int searchSelectProductAdminCnt(PageHandlerProduct pageHandlerProduct) throws Exception;
    public TrvPrdReadDto selectProduct(String prd_cd) throws Exception;
    public int updateProduct(TrvPrdWriteDto trvPrdWriteDto) throws Exception;
    public int deleteProduct(String prd_cd) throws Exception;
    public List<TrvPrdDtlReadDto> selectProductAdminDetail(PageHandlerProduct pageHandlerProduct) throws Exception;
    public int selectProductAdminDetailCnt() throws Exception;
    public int searchSelectProductAdminDetailCnt(PageHandlerProduct pageHandlerProduct) throws Exception;
    public List<TrvPrdDtlReadDto> searchSelectProductAdminDetail(PageHandlerProduct pageHandlerProduct) throws Exception;
    public int deleteAll() throws Exception;
    public List<TrvPrdWriteDto> selectAllProduct() throws Exception;
    public TrvPrdDtlReadDto selectProductDetail(String prd_dtl_cd) throws Exception;
    public int updateProductDetail(TrvPrdDtlWriteDto trvPrdDtlWriteDto) throws Exception;
    public int deleteProductDetail(String prd_dtl_cd) throws Exception;
    public List<TrvPrdReadDto> selectProductImage(PageHandlerProduct pageHandlerProduct) throws Exception;
    public int selectProductImageCnt() throws Exception;
    public List<TrvPrdReadDto> searchSelectProductImage(PageHandlerProduct pageHandlerProduct) throws Exception;
    public int searchSelectProductImageCnt(PageHandlerProduct pageHandlerProduct) throws Exception;
}
