cmake_minimum_required(VERSION 3.6.0)

# 项目名称
project(ndklearning)

###########版本号##############
# 主版本号，如2.86中的2
SET(MYLIB_VERSION_MARJOR "0")

# 次版本号，如2.86中的8
SET(MYLIB__VERSION_MINOR "1")

# 补丁等级，如2.86中的6
SET(MYLIB__VERSION_PATCH "0")

# 设置版本
SET(VERSION ${MYLIB_VERSION_MAJOR}.${MYLIB_VERSION_MONOR}.${MYLIB_VERSION_PATCH})

###########版本号##############

# 指定生成库文件的输出路径
SET(CMAKE_LIBRARY_OUTPUT_DIRECTORY   ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}) # so库输出路径
SET(ARCHIVE_OUTPUT_DIRECTORY   ../jniLibs) # .a静态库输出路径

#ADD_SUBDIRECTORY(mylib_a)

add_library(mylib_b SHARED
            mylib_b/mylib_b.cpp
)

# ANDROID_ABI内置变量，会根据当前编译的平台分配一个文件夹名字，比如armeabi-v7a， armeabi，x86等等
 
# 设置变量
SET(LIB_DIRS 
        ../jniLibs/${ANDROID_ABI}
        )

# 添加头文件目录
#INCLUDE_DIRECTORIES("./../jniLibs/include")

# 添加外部库文件目录,同VS使用外部lib目录一样
LINK_DIRECTORIES(${LIB_DIRS})

# 添加需要链接的库文件路径
# LINK_LIBRARIES("./../jniLibs/libmylib_a.so")

add_library(mylib_c SHARED
            mylib_c.cpp)
            
# Include libraries needed for ndklearning lib
target_link_libraries(
        mylib_c
        mylib_a
        android
        log
)