zeus-hiveweb主要解决的问题     
1.解决用户在web页面输入sql查询hive；   
2.不用登陆hive客户端服务器，直接在web系统提交查询任务；    
3.客户端提交任务后不用等待任务运行结束，后台运行结束自动保存任务结果， 用户之需要定时看一下任务状态；       
4.可以查询历史任务，可以终止正在运行的任务；     
5.对用户做了权限限制，必须给用户授权后才能查询对应的表；      
6.提供数据下载生存excel表格。   

zeus-hiveweb系统技术架构      
前端:spring + mybatis + mysql + velocity       
后端：hadoop 1.0.3  + hive 0.11  （经过测试 hive 0.8.1 ，hive 0.9.0,hive 0.10.0,hive 0.11.0      
都是可以正常运行）     
系统服务主要是调用hive 提供的hwi接口，所以只要hwi接口名不变 都可以使用，zeus系统自己部署了一套hive客户端     
所以该服务器需要配置hive-site.xml，系统需要调用hadoop 接口kill 任务，所以需要配置mapred-site.xml        
部署安装     

(1) 配置完以上文件后，需要安装mysql数据库，装完数据库后，新建zeus数据库，然后 source   zeus/doc/zeus.sql 导入系统所需的数据表       
需要安装maven    
(2)执行    mvn clean:package -Dmaven.test.skip=true         
(3)打包完的包在：zeus/target/zeus-1.0-SNAPSHOT-release.tar.gz   解压生成文件夹        
zeus-1.0-SNAPSHOT,在zeus-1.0-SNAPSHOT/bin/  执行 bash zeus-daemon.sh start           
(4)打开浏览器  http://localhost:10119/  部署完毕     


该项目属于 ireader   数据分析部
有问题可以联系：weirongneng@zhangyue.com



