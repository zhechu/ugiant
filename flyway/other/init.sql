--	建库
drop database if exists flywaydemo ;
create database	flywaydemo default charset utf8;

--	创建用户
create user 'flywaydemo'@'localhost' identified by 'flywaydemo';


--	设置权限
grant all privileges on flywaydemo.* to 'flywaydemo'@'localhost' with grant option;

grant super on *.* to 'flywaydemo'@'localhost' with grant option;

flush privileges; 