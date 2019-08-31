CREATE DATABASE IF NOT EXISTS framework DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE framework;

DROP TABLE IF EXISTS t01_sys_user;
CREATE TABLE T01_SYS_USER(
	id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
	username VARCHAR(20) NOT NULL,
	nickname VARCHAR(20) NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	salt VARCHAR(20) NOT NULL,
	pids VARCHAR(25) COMMENT '上级领导 以,分隔',
	dept_id INT COMMENT '所属部门-外键',
	STATUS INT DEFAULT 0 COMMENT '状态 0 未激活 1 正常 2 锁定',
	add_by VARCHAR(25) DEFAULT 'sys',
	update_by VARCHAR(25) DEFAULT 'sys',
	add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP	
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS T02_SYS_ROLE;
CREATE TABLE T02_SYS_ROLE(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20) NOT NULL,
	TYPE INT COMMENT '角色类型，1：基础员工（可查看自己名下数据），2：经理（可查看本组织内人员数据），3：管理员（可查看所有数据）',
	STATUS INT DEFAULT 0 COMMENT '状态 0 未激活 1 正常',
	add_by VARCHAR(25) DEFAULT 'sys',
	update_by VARCHAR(25) DEFAULT 'sys',
	add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' 
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS T03_SYS_RESOURCE;
CREATE TABLE T03_SYS_RESOURCE(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20) NOT NULL,
	pid INT DEFAULT 0 COMMENT '父结点id',
	pids VARCHAR(20) DEFAULT '' COMMENT '父结点id列表串',
	TYPE INT DEFAULT 0 COMMENT '资源类型 0 菜单 1 按钮 2 接口',
	url VARCHAR(50) NOT NULL COMMENT '访问地址',
	perm_code VARCHAR(25) NOT NULL COMMENT '权限代码字符串',
	color VARCHAR(20) COMMENT '颜色',
	icon VARCHAR(20) COMMENT '图标',
	sort INT DEFAULT 0 COMMENT '排序',
	STATUS INT DEFAULT 0 COMMENT '状态 0 未激活 1 正常',
	valid INT DEFAULT 0 COMMENT '是否需要权限验证 0 否 1 是',
	add_by VARCHAR(25) DEFAULT 'sys',
	update_by VARCHAR(25) DEFAULT 'sys',
	add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	update_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' 
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS T04_SYS_USER_ROLE;
CREATE TABLE T04_SYS_USER_ROLE(
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	add_by VARCHAR(25) DEFAULT 'sys',
	update_by VARCHAR(25) DEFAULT 'sys',
	add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	update_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' 
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS T05_SYS_ROLE_RESOURCE;
CREATE TABLE T05_SYS_ROLE_RESOURCE(
	id INT PRIMARY KEY AUTO_INCREMENT,
	role_id INT NOT NULL,
	res_id INT NOT NULL,
	add_by VARCHAR(25) DEFAULT 'sys',
	update_by VARCHAR(25) DEFAULT 'sys',
	add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	update_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' 
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS T00_SYS_LOG;
CREATE TABLE T00_SYS_LOG(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL,
	user_id VARCHAR(20) NOT NULL,
	ip VARCHAR(20) NOT NULL,
	ajax INT,
	uri VARCHAR(50) NOT NULL,
	params TEXT,
	http_method VARCHAR(20) NOT NULL,
	class_method VARCHAR(100) NOT NULL,
	response TEXT COMMENT '响应内容',
	action_name VARCHAR(100) NOT NULL COMMENT '具体操作内容',
	add_by VARCHAR(25) DEFAULT 'sys',
	update_by VARCHAR(25) DEFAULT 'sys',
	add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	update_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' 
)ENGINE=INNODB DEFAULT CHARSET=utf8;


insert  into `t01_sys_user`(`id`,`username`,`nickname`,`password`,`salt`,`pids`,`dept_id`,`status`,`add_by`,`update_by`,`add_time`,`update_time`) values (1,'admin','管理员','2b3fcc58ef54ec965a541b6bfa66510f','mpdrT1',NULL,NULL,0,'sys','sys','2019-08-29 22:39:19','2019-08-31 19:42:31');


insert  into `t02_sys_role`(`id`,`name`,`type`,`status`,`add_by`,`update_by`,`add_time`,`update_time`) values (41,'管理员',3,0,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20');


insert  into `t03_sys_resource`(`id`,`name`,`pid`,`pids`,`type`,`url`,`perm_code`,`color`,`icon`,`sort`,`status`,`valid`,`add_by`,`update_by`,`add_time`,`update_time`) values (1,'系统管理',0,'',0,'#','#','#19BE6B','',0,0,0,'sys','sys','2019-08-31 21:43:41','2019-08-31 21:43:41'),(27,'用户管理',1,'',0,'#','user','#19BE6B','',1,0,0,'sys','sys','2019-08-31 21:38:28','2019-08-31 21:38:28'),(28,'角色管理',1,'',0,'#','role','#19BE6B','',2,0,0,'sys','sys','2019-08-31 21:38:56','2019-08-31 21:38:56'),(29,'资源管理',1,'',0,'#','resource','#19BE6B','',3,0,0,'sys','sys','2019-08-31 21:39:14','2019-08-31 21:39:14'),(30,'添加/更新用户',27,NULL,1,'/user/addUser','user:add','#19BE6B','',2,0,NULL,NULL,NULL,'2019-08-31 22:12:18','2019-08-31 22:12:19'),(32,'变更用户状态',27,NULL,1,'/user/alterStatus','user:alter','#1D35EA','',3,0,NULL,NULL,NULL,'2019-08-31 22:12:36','2019-08-31 22:12:36'),(34,'分配用户角色',27,'',1,'/user/bindRoles','user:bind','#19BE6B','',4,0,0,'sys','sys','2019-08-31 21:52:40','2019-08-31 21:52:41'),(35,'修改密码',27,'',1,'/user/resetPassword','user:reset','#19BE6B','',5,0,0,'sys','sys','2019-08-31 21:54:43','2019-08-31 21:54:43'),(36,'删除用户',27,'',1,'/user/delete','user:delete','#ED4014','',6,0,0,'sys','sys','2019-08-31 21:55:29','2019-08-31 21:55:29'),(37,'添加/更新角色',28,'',1,'/role/addOrUpdate','role:add','#19BE6B','',2,0,0,'sys','sys','2019-08-31 21:58:27','2019-08-31 21:58:27'),(38,'变更角色状态',28,'',1,'/role/alterStatus','role:alter','#19BE6B','',3,0,0,'sys','sys','2019-08-31 21:59:31','2019-08-31 21:59:31'),(39,'删除角色',28,'',1,'/role/delete','role:delete','#19BE6B','',4,0,0,'sys','sys','2019-08-31 22:00:59','2019-08-31 22:00:59'),(40,'添加/更新资源',29,'',1,'/res/addOrUpdate','resource:add','#19BE6B','',2,0,0,'sys','sys','2019-08-31 22:02:32','2019-08-31 22:02:32'),(41,'删除资源',29,'',1,'/res/delete','resource:delete','#19BE6B','',3,0,0,'sys','sys','2019-08-31 22:03:30','2019-08-31 22:03:30'),(42,'列表用户',27,'',1,'/user/list','user:list','#19BE6B','',1,0,0,'sys','sys','2019-08-31 22:12:10','2019-08-31 22:12:10'),(43,'列表角色',28,'',1,'/role/list','role:list','#19BE6B','',1,0,0,'sys','sys','2019-08-31 22:13:37','2019-08-31 22:13:37'),(44,'列表资源',29,'',1,'/res/list','resource:list','#19BE6B','',1,0,0,'sys','sys','2019-08-31 22:15:13','2019-08-31 22:15:13');


insert  into `t04_sys_user_role`(`id`,`user_id`,`role_id`,`add_by`,`update_by`,`add_time`,`update_time`) values (49,1,41,'sys','sys','2019-08-31 22:05:47','2019-08-31 22:05:47');


insert  into `t05_sys_role_resource`(`id`,`role_id`,`res_id`,`add_by`,`update_by`,`add_time`,`update_time`) values (23,41,1,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(24,41,27,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(25,41,30,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(26,41,32,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(27,41,34,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(28,41,35,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(29,41,36,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(30,41,28,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(31,41,37,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(32,41,38,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(33,41,39,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(34,41,29,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(35,41,40,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20'),(36,41,41,'sys','sys','2019-08-31 22:05:20','2019-08-31 22:05:20');

