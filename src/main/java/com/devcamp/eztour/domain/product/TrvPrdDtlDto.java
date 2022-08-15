package com.devcamp.eztour.domain.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrvPrdDtlDto {

    private String prd_dtl_cd;
    private String prd_cd;
    private int	    prd_str_prc;
    private String	arl_nm;
    private boolean	dpr_yn;
    private boolean rsvt_yn;
    private int		pr_rsvt_cnt;
    private int		min_stt_cnt;
    private int		max_stt_cnt;
    private String	dpr_date;
    private String	frs_reg_date;
    private String	frs_rgs_no;
    private String  fnl_mod_date;
    private String	fnl_mod_no;

    public TrvPrdDtlDto(String prd_dtl_cd, String prd_cd, String arl_nm, int prd_str_prc, String dpr_date) {
        this.prd_dtl_cd = prd_dtl_cd;
        this.prd_cd = prd_cd;
        this.prd_str_prc = prd_str_prc;
        this.arl_nm = arl_nm;
        this.dpr_date = dpr_date;
    }

}
