@echo off

rem add mingw to the path
set orig_path=%PATH%
set PATH=%PATH%;C:\tools\mingw64\bin

set JNI_FLAGS=-D_JNI_IMPLEMENTATION_ -Wl,--kill-at
set JNI="-IC:\tools\jdk8\include" "-IC:\tools\jdk8\include\win32"

echo compile it
g++ %JNI_FLAGS% -O3 -DNDEBUG %JNI% -L.\libs -shared -o libs\olca-umfact.dll src\main\c\lib.c -llibumfpack

echo all done

rem reset the path
set PATH=%orig_path%