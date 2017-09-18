# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)


add_library(
             lib-andfix
             SHARED
             src/main/cpp/dalvik_method.cpp
             src/main/cpp/art_method.cpp
             src/main/cpp/art_7_0.cpp
             )


find_library(
              log-lib
              log )


target_link_libraries(
                       lib-andfix
                       ${log-lib} )