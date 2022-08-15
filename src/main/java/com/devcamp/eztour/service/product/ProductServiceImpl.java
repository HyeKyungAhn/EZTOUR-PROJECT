package com.devcamp.eztour.service.product;

import com.devcamp.eztour.dao.product.ProductDao;
import com.devcamp.eztour.domain.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDao productDao;

    @Override
    public int insertProduct(TrvPrdWriteDto trvPrdWriteDto) throws Exception {
        return productDao.insertProduct(trvPrdWriteDto);
    }

    @Override
    public int insertProductDetail(TrvPrdDtlDto trv_prdDtlDto) {
        return productDao.insertProductDetail(trv_prdDtlDto);
    }

    @Override
    public int insertProductPrice(TrvPrdPrcDto trv_prdPrcDto) {
        return productDao.insertProductPrice(trv_prdPrcDto);
    }

    @Override
    public int insertProductSchedule(TrvSchDto trv_schDto) {
        return productDao.insertProductSchedule(trv_schDto);
    }

    @Override
    public int insertProductImg(PrdImgDto prd_imgDto) {
        return productDao.insertProductImg(prd_imgDto);
    }

    @Override
    public int insertScheduleImage(TrvSchImgDto trv_schImgDto) {
        return productDao.insertScheduleImage(trv_schImgDto);
    }

    @Override
    public List<TrvPrdReadDto> selectProductAdmin(PageHandlerProduct pageHandlerProduct) {
        return productDao.selectProductAdmin(pageHandlerProduct);
    }

    @Override
    public int selectProductAdminCnt() {
        return productDao.selectProductAdminCnt();
    }

    @Override
    public List<TrvPrdReadDto> searchSelectProductAdmin(PageHandlerProduct pageHandlerProduct) {
        return productDao.searchSelectProductAdmin(pageHandlerProduct);
    }

    @Override
    public int searchSelectProductAdminCnt(PageHandlerProduct pageHandlerProduct) {
        return productDao.searchSelectProductAdminCnt(pageHandlerProduct);
    }

    @Override
    public TrvPrdReadDto selectProduct(String prd_cd) {
        return productDao.selectProduct(prd_cd);
    }

    @Override
    public int updateProduct(TrvPrdWriteDto trvPrdWriteDto) {
        return productDao.updateProduct(trvPrdWriteDto);
    }

    @Override
    public int deleteProduct(String prd_cd) {
        return productDao.deleteProduct(prd_cd);
    }

    @Override
    public List<TrvPrdDtlDto> selectProductAdminDetail(PageHandlerProduct pageHandlerProduct) {
        return productDao.selectProductAdminDetail(pageHandlerProduct);
    }

    @Override
    public int selectProductAdminDetailCnt() {
        return productDao.selectProductAdminDetailCnt();
    }

    @Override
    public int searchSelectProductAdminDetailCnt(PageHandlerProduct pageHandlerProduct) {
        return productDao.searchSelectProductAdminDetailCnt(pageHandlerProduct);
    }

    @Override
    public List<TrvPrdDtlDto> searchSelectProductAdminDetail(PageHandlerProduct pageHandlerProduct) {
        return productDao.searchSelectProductAdminDetail(pageHandlerProduct);
    }

    @Override
    public int deleteAll() throws Exception {
        return productDao.deleteAll();
    }

    @Override
    public List<TrvPrdWriteDto> selectAllProduct() throws Exception {
        return productDao.selectAllProduct();
    }
}
