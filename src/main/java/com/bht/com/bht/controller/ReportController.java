package com.bht.com.bht.controller;

import com.bht.com.bht.dao.ReportSearchRepository;
import com.bht.com.bht.domain.ReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportSearchRepository reportSearchRepository;

    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }

    /*通过主键查询数据*/
    @GetMapping("getreport/{id}")
    public ReportDetail getReport(@PathVariable Long id){
        Optional<ReportDetail> optional =  reportSearchRepository.findById(id);
        ReportDetail rd = new ReportDetail();
        if(optional.isPresent()){
            rd = optional.get();
        }
        return rd;
    }
    /*通过明细表ID查询数据*/
    @GetMapping("getreportbydetailid")
    public ReportDetail getReportByDetailId(@RequestParam("detailId") String detailId){
        ReportDetail rd = null;
        rd = reportSearchRepository.queryReportDetailByReportDetailId(detailId);
        return rd;
    }
    /*通过主表parentId查询数据*/
    @GetMapping("getreportbyparentid")
    public List<ReportDetail> getReportByParentId(@RequestParam("parentId") String parentId){
        List<ReportDetail> rd = null;
        rd = reportSearchRepository.queryReportDetailByParentId(parentId);
        return rd;
    }
    /*通过主表xmParentId查询数据*/
    @GetMapping("getreportbyxmparentid")
    public List<ReportDetail> getReportByXmParentId(@RequestParam("xmParentId") String xmParentId){
        List<ReportDetail> rd = null;
        rd = reportSearchRepository.queryReportDetailByXmParentId(xmParentId);
        return rd;
    }

    /*通过主表parentId删除该主表下的所有数据*/
    @DeleteMapping("deletereportbyparentid")
    public Boolean deleteReportByParentId(@RequestParam("parentId") String parentId){
        List<ReportDetail> rdList = null;
        rdList = reportSearchRepository.queryReportDetailByParentId(parentId);
        for(ReportDetail rd:rdList){
            reportSearchRepository.delete(rd);
        }
        return true;
    }
    /*通过主表xmParentId删除该主表下的所有数据*/
    @DeleteMapping("deletereportbyxmparentid")
    public Boolean deleteReportByXmParentId(@RequestParam("xmParentId") String xmParentId){
        List<ReportDetail> rdList = null;
        rdList = reportSearchRepository.queryReportDetailByXmParentId(xmParentId);
        for(ReportDetail rd:rdList){
            reportSearchRepository.delete(rd);
        }
        return true;
    }
    /*添加记录*/
    @PostMapping("addreport")
    public Boolean addReport(@RequestBody ReportDetail reportDetail){
       reportSearchRepository.save(reportDetail);
       return true;
    }

    /*批量添加记录*/
    @PostMapping("batchaddreport")
    public Boolean batchInsertReport(@RequestBody List<ReportDetail> reportDetailList){
        reportSearchRepository.saveAll(reportDetailList);
        return true;
    }

    /*通过明细表ID删除记录*/
    @DeleteMapping("deletereport")
    public Boolean deleteReportByDetailId(@RequestParam("detailId") String detailId){
        ReportDetail rd = null;
        rd = reportSearchRepository.queryReportDetailByReportDetailId(detailId);
        reportSearchRepository.delete(rd);
        return true;
    }

    @DeleteMapping("batchdeletereport")
    public Boolean batchDeleteReport(@RequestBody List<ReportDetail> reportDetailList){
        for(ReportDetail rd:reportDetailList){
            ReportDetail rddlt = reportSearchRepository.queryReportDetailByReportDetailId(rd.getReportDetailId());
            reportSearchRepository.delete(rddlt);
        }
        return true;
    }

    /*修改记录*/
    @PutMapping("modifyreport")
    public Boolean modifyReport(@RequestBody ReportDetail reportDetail){
        ReportDetail rd = reportSearchRepository.queryReportDetailByReportDetailId(reportDetail.getReportDetailId());
        reportDetail.setId(rd.getId());
        reportSearchRepository.save(reportDetail);
        return true;
    }

    /*模糊查询记录*/
    @GetMapping("getreportbykeyword")
    public List<ReportDetail> getReportDetailData(@RequestParam("keyword") String keyword){
        return reportSearchRepository.findByContentLike(keyword);
    }

    /*通过类型及关键字模糊查询记录*/
    @GetMapping("getreportbykeywordandtype")
    public List<ReportDetail> getReportDetailDataByTypeAndContent(@RequestParam("keyword") String keyword, @RequestParam("contentType") String contentType){
        return reportSearchRepository.findByContentLikeAndContentType(keyword,contentType);
    }

    /*获取所有记录*/
    @GetMapping("getreportall")
    public Iterable<ReportDetail> getReportDetailAllData(){
        return reportSearchRepository.findAll();
    }
}
