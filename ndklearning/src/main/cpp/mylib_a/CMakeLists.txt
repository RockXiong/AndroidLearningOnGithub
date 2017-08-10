cmake_minimum_required(VERSION 3.6.0)

project(mylib_a)

# AUX_SOURCE_DIRECTORY 命令会查找指定目录下的所有源文件，然后将结果存进指定变量名。
# 其语法如下：AUX_SOURCE_DIRECTORY(<dir> <variable>)
AUX_SOURCE_DIRECTORY(. SRCS)

#生成静态链接库
add_library(mylib_a SHARED ${SRCS})