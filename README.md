# SpringBoot-HBase
项目采用SpringBoot作为后端，数据库采用Windows上的单节点伪分布式HBase。

关于如何在Windows上安装Hadoop和HBase的单机节点可以参考 https://www.jianshu.com/p/db33aaa566cd

项目介绍：项目是关于城市广告数据查询服务，前端需求是根据前端地图给出的经纬度的范围，搜索该范围内的广告数据。因此在设计数据库时，采用GeoHash的将广告的经纬度转换成一个Base32的字符串，由该字符串结合广告的id构成一条广告数据的RowKey，查询时，使用前缀过滤器，可以快速地过滤出满足条件的广告数据。

不足之处：在完成该课程项目时，是第一次使用SpringBoot来开发后端，很多分层傻傻分不清，代码管理较为混乱。


![image](https://github.com/shiguri/SpringBoot-HBase/blob/main/SpringBoot_HBase-master/display.jpg)

