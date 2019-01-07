cmake_minimum_required(VERSION 3.4.1)
add_library( # 库的名称.
        audio-effect
        # Sets the library as a shared library.
        SHARED
        # c++文件路径
        src/main/cpp/smbPitchShift.cpp
        )
find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)
target_link_libraries( # Specifies the target library.
        audio-effect
        android
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
