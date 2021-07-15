#windows目录
packageaddr=E:\\NeedEncoding\\Packages\\
svnaddr=E:\\NeedEncoding\\SVNCheckOut\\
tooladdr=E:\\NeedEncoding\\tool\\
########################################################################################################
#linux目录
packageaddr=/opt/Packages/
svnaddr=/opt/SVNCheckOut/
tooladdr=/opt/tool/
########################################################################################################
#linux部署需要删除下面配置的值  http://192.168.186.12:8088/addsvnlinux
otherurl=http://192.168.186.12:8088/addsvnlinux
################################################################################################################################################
操作步骤：
1.注意linux部署和windows部署不一致，configuration.properties中的otherurl参数在linux中不需要填
2.windows中目录结构形如：
  E:\NeedEncoding\SpringBootSVNCheck\bin             放置configuration.properties
  E:\NeedEncoding\SpringBootSVNCheck\lib             放置所有jar包
  E:\NeedEncoding\SpringBootSVNCheck\static          放置js css文件夹下面放相关文件  并且需要将E:\\NeedEncoding\\Packages\\软连接到改目录下
  E:\NeedEncoding\SpringBootSVNCheck\templates       放置index.html svn.html页面
  E:\NeedEncoding\SpringBootSVNCheck\readme.txt      说明文件
3.linux中目录结构形如：
  /opt/SpringBootSVNCheck/bin             放置configuration.properties
  /opt/SpringBootSVNCheck/lib             放置所有jar包
  /opt/SpringBootSVNCheck/static          放置js css文件夹下面放相关文件
  /opt/SpringBootSVNCheck/templates       放置index.html svn.html页面
  /opt/SpringBootSVNCheck/readme.txt      说明文件
4.启动
  windows：打开cmd进入到E:\NeedEncoding\SpringBootSVNCheck\目录中，输入如下命令
  java -Djava.ext.dirs=lib com.epoint.bigdata.MainApplication
  java  -Djava.ext.dirs=lib com.epoint.bigdata.svncheck.SVNPackageByDirWindows

  linux:进入到/opt/SpringBootSVNCheck目录中，输入如下命令
  nohup java -Djava.ext.dirs=lib com.epoint.bigdata.MainApplication >/opt/SpringBootSVNCheck/springbootlog.txt &
  nohup java  -Djava.ext.dirs=lib com.epoint.bigdata.svncheck.SVNPackageByDirLinux >/opt/SpringBootSVNCheck/svnchecklog.txt &
5.访问地址：http://192.168.186.87:8088
6.注意：如果目录有所变换，请将上面操作步骤做响应的更换