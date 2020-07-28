package com.bht.com.bht.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.annotation.Generated;
import javax.annotation.sql.DataSourceDefinition;
import java.io.Serializable;

@Document(indexName="bht_index",type="report",shards=5,replicas=1,indexStoreType="fs",refreshInterval="-1")
public class ReportDetail implements Serializable {
    private static final long serialVersionUID = 551589397625941750L;

    @Id
    private Long id;
    /*明细表id*/
    @Field(type = FieldType.Keyword)
    private String reportDetailId;
    /*父id*/
    @Field(type = FieldType.Keyword)
    private String parentId;
    /*项目级父id*/
    @Field(type = FieldType.Keyword)
    private String xmParentId;
    /*事件类型*/
    @Field(type = FieldType.Keyword)
    private String contentType;
    /*事件内容*/
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getReportDetailId() {
        return reportDetailId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportDetailId(String reportDetailId) {
        this.reportDetailId = reportDetailId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getXmParentId() {
        return xmParentId;
    }

    public void setXmParentId(String xmParentId) {
        this.xmParentId = xmParentId;
    }

    @Override
    public String toString() {
        return "ReportDetail{" +
                "id=" + id +
                ", reportDetailId='" + reportDetailId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", xmParentId='" + xmParentId + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
