package com.bht.com.bht.dao;

import com.bht.com.bht.domain.ReportDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ReportSearchRepository extends ElasticsearchRepository<ReportDetail, Long> {
    List<ReportDetail> findByContentLike(String str);
    List<ReportDetail> findByContentLikeAndContentType(String str,String contentType);
    ReportDetail queryReportDetailByReportDetailId(String detailId);
    List<ReportDetail> queryReportDetailByParentId(String parentId);
    List<ReportDetail> queryReportDetailByXmParentId(String xmParentId);
}
