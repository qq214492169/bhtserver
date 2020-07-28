# bhtserver
springboot集成elsticsearch服务案例
1、项目准备
安装jdk1.8以上版本
安装elasticsearch服务器
以上两步具体细节自行百度安装部署
2、搭建基础的springboot项目，并引入相关依赖
  pom.xml文件主要引入以下两个依赖：
<!-- 构建web项目模块 包括了Tomcat和spring-webmvc -->
<!-- spring-boot-starter-web 默认依赖了tomcat的starter 所以使得项目可以直接运行而不需要部署到tomcat中-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--添加elasticsearch相关数据依赖，方便我们连接和操作elasticsearch服务-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
  application.yml配置elasticsearch：
server:
  port: 8085
spring:
  data:
    elasticsearch:
      cluster-nodes: localhost:9300
      cluster-name: my-application  # 集群名称，和elasticsearch.yml对应
3、创建实体类
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
4、编写elasticsearch调用接口，这里需要继承ElasticsearchRepository类
public interface ReportSearchRepository extends ElasticsearchRepository<ReportDetail, Long> {
    List<ReportDetail> findByContentLike(String str);
    List<ReportDetail> findByContentLikeAndContentType(String str,String contentType);
    ReportDetail queryReportDetailByReportDetailId(String detailId);
    List<ReportDetail> queryReportDetailByParentId(String parentId);
    List<ReportDetail> queryReportDetailByXmParentId(String xmParentId);
}
5、使用上面构建的ReportSearchRepository接口进行elasticsearch的数据访问，具体详看代码
