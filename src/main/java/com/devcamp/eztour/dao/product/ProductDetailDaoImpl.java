package com.devcamp.eztour.dao.product;

import com.devcamp.eztour.domain.product.PrdDtlPageDto;
import com.devcamp.eztour.domain.product.TrvPrdDtlDto;
import com.devcamp.eztour.domain.product.TrvPrdDtlReadDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductDetailDaoImpl implements ProductDetailDao {

    private final SqlSession session;

    String namespace = "com.devcamp.eztour.dao.productDetailMapper.";

    @Override
    public List<TrvPrdDtlReadDto> selectAllProduct() throws Exception {
        return session.selectList(namespace + "selectAllProduct");
    }

    @Override
    public List<TrvPrdDtlDto> selectAllDetailProduct(String prd_cd) throws Exception {
        return session.selectList(namespace + "selectAllDetailProduct", prd_cd);
    }

    @Override
    public List<TrvPrdDtlReadDto> selectAllProductCategory(Map map) throws Exception {
        return session.selectList(namespace+"selectAllProductCategory",map);
    }

    @Override
    public List<TrvPrdDtlReadDto> selectAllProductOrder(Map map) throws Exception {
        return session.selectList(namespace+"selectAllProductOrder",map);
    }

    @Override
    public List<TrvPrdDtlReadDto> selectUserLike() throws Exception {
        return session.selectList(namespace+"selectUserLike");
    }

    @Override
    public PrdDtlPageDto selectProductDetailPage(String prd_dtl_cd) throws Exception {
        return session.selectOne(namespace+"selectProductDetailPage",prd_dtl_cd);
    }

    @Override
    public TrvPrdDtlReadDto selectOneProduct(String prd_cd) throws Exception {
        return session.selectOne(namespace+"selectOneRecentProduct",prd_cd);
    }

}
